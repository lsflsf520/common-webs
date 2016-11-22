package com.yisi.stiku.passport.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.cache.constant.DefaultJedisKeyNS;
import com.yisi.stiku.cache.redis.ShardJedisTool;
import com.yisi.stiku.common.exception.BaseException;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.passport.WebConstants;
import com.yisi.stiku.passport.util.TransactionContextUtils;
import com.yisi.stiku.wallet.entity.Account;
import com.yisi.stiku.wallet.entity.RechargeCard;
import com.yisi.stiku.wallet.entity.TransactionContext;
import com.yisi.stiku.wallet.rpc.service.AccountRpcService;
import com.yisi.stiku.wallet.rpc.service.AccountSecurityRpcService;
import com.yisi.stiku.wallet.rpc.service.RechargeCardBatchRpcChekcer;
import com.yisi.stiku.wallet.rpc.service.RechargeCardBatchRpcService;
import com.yisi.stiku.wallet.rpc.service.RechargeCardRpcService;
import com.yisi.stiku.wallet.rpc.service.TransactionRpcService;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

@Controller
@RequestMapping("account")
public class AccountController {

	private final static Logger LOG = org.slf4j.LoggerFactory.getLogger(AccountController.class);
	@Resource
	private TransactionRpcService transactionRpcService;

	@Resource
	private AccountRpcService accountRpcService;
	@Resource
	private AccountSecurityRpcService accountSecurityRpcService;
	@Resource
	private UserRpcService userRpcService;

	@Resource
	private RechargeCardBatchRpcService rechargeCardBatchRpcService;
	@Resource
	private RechargeCardRpcService rechargeCardRpcService;
	@Resource
	private RechargeCardBatchRpcChekcer rechargeCardBatchRpcChecker;

	@RequestMapping("/hasAccount")
	public void hasAccount(HttpServletRequest request, HttpServletResponse response) {

		Long userid = LoginSesionUtil.getUserId();

		Account account = accountRpcService.queryAccountByUserId(userid);

		boolean result = accountSecurityRpcService.hasAccountSecurityByUserId(userid);

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("balance", account != null ? account.getAvailableBalance() : 0);
		if (result) {
			paramMap.put("code", WebConstants.HAD_ACCOUNT);
		} else {
			paramMap.put("code", WebConstants.NO_ACCOUNT);
		}
		com.yisi.stiku.web.util.WebUtils.writeJson(paramMap, request, response);
		return;
	}

	@RequestMapping("/updateAccountBlanceTemp")
	public void updateAccountBlanceTemp(HttpServletRequest request, HttpServletResponse response) {

		String school_id = (String) request.getParameter("school_id");
		String money = (String) request.getParameter("money");
		accountRpcService.updateByBalanceBySchool(Long.parseLong(school_id), Long.parseLong(money) * 100L);
	}

	/**
	 * 激活卡充值
	 * 
	 * @param request
	 * @param response
	 * @param actCode
	 */
	@RequestMapping("/rechargeCard")
	public void rechargeCard(HttpServletRequest request, HttpServletResponse response, String actCode) {

		if (StringUtils.isBlank(actCode)) {
			WebUtils.writeJson(OperationResult.buildFailureResult("激活码不能为空!"), request, response);
			return;
		}

		try {
			String countStr = ShardJedisTool.get(DefaultJedisKeyNS.ei, LoginSesionUtil.getToken(request) + "actCode");
			if (RegexUtil.isInt(countStr) && Integer.valueOf(countStr) > Integer
					.valueOf(ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "error.input.actcode.maxcount", "10"))) {
				WebUtils.writeJson(OperationResult.buildFailureResult("错误次数过多，请明天再试!"), request, response);
				return;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		TransactionContext context = TransactionContextUtils.newRechargeContext(LoginSesionUtil.getUserId(), actCode);
		// TransactionContext context =
		// TransactionContextUtils.newFrozonContext(LoginSesionUtil.getUserId(),
		// 10L);
		transactionRpcService.transaction(context);

		RechargeCard funcCard = rechargeCardRpcService.findOneByActNo(actCode);

		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("cardCode", funcCard.getDbType().getDbCode());
		dataMap.put("desc", funcCard.getDbType().getDesc());
		dataMap.put("animationImg", funcCard.getDbType().getAnimationImg());
		WebUtils.writeJson(OperationResult.buildSuccessResult("操作成功!", dataMap), request, response);
	}

	@RequestMapping("setPwdForTrans")
	public String setPwdForTrans(HttpServletRequest request) {

		request.setAttribute("errorMsg", request.getParameter("errorMsg"));
		request.setAttribute("title", "设置支付密码");

		request.setAttribute(WebConstants.TRANS_PWD_TYPE, WebConstants.SET_TRANS_PWD);
		return "user/reset_transpwd";
	}

	@RequestMapping("checkTrnPwdSameLoginPwd")
	public void checkTrnPwdSameLoginPwd(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("errorMsg", request.getParameter("errorMsg"));
		String transPwd = request.getParameter("password");
		Long userid = LoginSesionUtil.getUserId();
		Boolean isSameToLoginPwd = userRpcService.checkPasswd(userid, transPwd);
		if (isSameToLoginPwd) {
			WebUtils.writeJson(WebConstants.SAME_TO_LOGINPWD, request, response);
		}

		else {
			WebUtils.writeJson(WebConstants.NOTSAME_TO_LOGINPWD, request, response);
		}

	}

	@RequestMapping("resetPwdForTrans")
	public String resetPwdForTrans(HttpServletRequest request) {

		Long userid = LoginSesionUtil.getUserId();

		boolean result = accountSecurityRpcService.hasAccountSecurityByUserId(userid);

		if (result) {
			request.setAttribute(WebConstants.TRANS_PWD_TYPE, WebConstants.RESET_TRANS_PWD);
			request.setAttribute("title", "修改支付密码");
		} else {
			request.setAttribute(WebConstants.TRANS_PWD_TYPE, WebConstants.SET_TRANS_PWD);
			request.setAttribute("title", "设置支付密码");
		}

		request.setAttribute("errorMsg", request.getParameter("errorMsg"));

		return "user/reset_transpwd";
	}

	@RequestMapping("checkLoginPwdSameTrnPwd")
	public void checkLoginPwdSameTrnPwd(HttpServletRequest request, HttpServletResponse response) {

		Long userid = LoginSesionUtil.getUserId();
		String password = request.getParameter("password");
		boolean result = false;
		try {
			result = accountSecurityRpcService.validateTransSecurity(userid, password);
		} catch (BaseException e) {
			LOG.error("验证登录密码与支付密码是否一致失败", e.toString());
		}

		if (result) {
			WebUtils.writeJson(WebConstants.SAME_TO_LOGINPWD, request, response);

		} else {
			WebUtils.writeJson(WebConstants.NOTSAME_TO_LOGINPWD, request, response);

		}
	}

	@RequestMapping("initAccount")
	public String initAccount(HttpServletRequest request, String code, RedirectAttributes attr) throws BaseException {

		Long userid = LoginSesionUtil.getUserId();

		String oldpwd = request.getParameter("oldpwd");
		String password = request.getParameter("password");
		String pwdTransType = (String) request.getParameter(WebConstants.TRANS_PWD_TYPE);

		String retype = request.getParameter("retype");
		if (StringUtils.isBlank(password) || !password.equals(retype)) {
			attr.addAttribute("code", code);
			attr.addAttribute("errorMsg", "密码为空或两次密码输入不一致！");
			return "redirect:" + (StringUtils.isBlank(request.getHeader("referer")) ? "/account/resetPwdForTrans"
					: request.getHeader("referer").split("\\?")[0]);
		}

		if (pwdTransType.equals(WebConstants.RESET_TRANS_PWD)) {
			if (StringUtils.isBlank(oldpwd) || oldpwd.equals(password)) {
				attr.addAttribute("code", code);
				attr.addAttribute("errorMsg", "旧密码为空或旧密码不能与新密码相同");
				return "redirect:" + (StringUtils.isBlank(request.getHeader("referer")) ? "/account/resetPwdForTrans"
						: request.getHeader("referer").split("\\?")[0]);
			}

			boolean passValidate = accountSecurityRpcService.validateTransSecurity(userid, oldpwd);
			// 旧密码输入错误
			if (!passValidate) {
				attr.addAttribute("code", code);
				attr.addAttribute("errorMsg", "旧密码错误");
				return "redirect:" + (StringUtils.isBlank(request.getHeader("referer")) ? "/account/resetPwdForTrans"
						: request.getHeader("referer").split("\\?")[0]);
			}
			try {
				accountSecurityRpcService.changeTransSecurity(userid, password);
			} catch (Exception e) {
				throw new BaseException("修改支付密码失败");
			}

		} else if (pwdTransType.equals(WebConstants.SET_TRANS_PWD)) {
			// 是否已经创建账户，没有就创建一个
			Account account = accountRpcService.queryAccountByUserId(userid);
			if (null == account) {
				account = accountRpcService.createAccount(userid);
			}

			accountSecurityRpcService.createAccountSecurity(userid, account, password);
		}
		request.setAttribute("errorMsg", request.getParameter("errorMsg"));

		return "user/transpwd_reset_ok";
	}
}
