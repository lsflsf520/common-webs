package com.yisi.stiku.passport.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.rpc.service.StudentRpcService;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.common.bean.UserType;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.EncryptTools;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.common.utils.ThreadUtil;
import com.yisi.stiku.msg.rpc.service.EmailSenderRpcService;
import com.yisi.stiku.passport.service.impl.UserLoginHelper;
import com.yisi.stiku.wallet.rpc.service.AccountSecurityRpcService;
import com.yisi.stiku.web.constant.WebConstant;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.LoginUtils;
import com.yisi.stiku.web.util.WebUtils;

@Controller
@RequestMapping("/user")
public class PassportController {

	private final static Logger LOG = LoggerFactory.getLogger(PassportController.class);

	@Resource
	private UserRpcService userRpcService;

	@Resource
	private UserLoginHelper userHelper;

	@Resource
	private EmailSenderRpcService emailSenderRpcService;

	@Resource
	private StudentRpcService studentRpcService;

	@Resource
	private AccountSecurityRpcService accountSecurityRpcService;

	@RequestMapping("login/loginPage")
	public String loginPage(HttpServletRequest request) {

		try {
			if (LoginSesionUtil.hasLogon()) {
				return "redirect:"
						+ WebConstant.getLogonUrl(LoginSesionUtil.getUserTypeProjectName(),
								WebConstant.getSchoolIdsForCurrUser(LoginSesionUtil.getUserType()));
			}
		} catch (Exception e) {
			LOG.debug(LOG.isDebugEnabled() ? "session info is not exists." : null);
		}
		if (!"true".equals(request.getParameter("noreferer"))) {
			String referer = StringUtils.isNotBlank(request.getParameter("referer")) ? request.getParameter("referer")
					: request.getHeader("referer");
			request.setAttribute("referer", referer);
		}
		request.setAttribute("errorMsg", request.getParameter("errorMsg"));
		LOG.debug(LOG.isDebugEnabled() ? request.getHeader("user-agent") : "");
		return WebUtils.isPad(request) ? "user/padLoginPage" : "user/loginPage";
		// return "user/padLoginPage";
	}

	@RequestMapping("login/appLoginPage")
	public String appLoginPage(HttpServletRequest request) {

		try {
			if (LoginSesionUtil.hasLogon()) {
				return "redirect:"
						+ WebConstant.getLogonUrl(LoginSesionUtil.getUserTypeProjectName(),
								WebConstant.getSchoolIdsForCurrUser(LoginSesionUtil.getUserType()));
			}
		} catch (Exception e) {
			LOG.debug(LOG.isDebugEnabled() ? "session info is not exists." : null);
		}
		if (!"true".equals(request.getParameter("noreferer"))) {
			String referer = StringUtils.isNotBlank(request.getParameter("referer")) ? request.getParameter("referer")
					: request.getHeader("referer");
			request.setAttribute("referer", referer);
		}
		request.setAttribute("errorMsg", request.getParameter("errorMsg"));
		LOG.debug(LOG.isDebugEnabled() ? request.getHeader("user-agent") : "");
		return "user/appLoginPage";
		// return "user/padLoginPage";
	}

	@RequestMapping("login/teacherLoginPage")
	@Deprecated
	public String teacherLoginPage(HttpServletRequest request) {

		try {
			if (LoginSesionUtil.hasLogon()) {
				return "redirect:"
						+ WebConstant.getLogonUrl(LoginSesionUtil.getUserTypeProjectName(),
								WebConstant.getSchoolIdsForCurrUser(LoginSesionUtil.getUserType()));
			}
		} catch (Exception e) {
			LOG.debug(LOG.isDebugEnabled() ? "session info is not exists." : null);
		}
		if (!"true".equals(request.getParameter("noreferer"))) {
			String referer = StringUtils.isNotBlank(request.getParameter("referer")) ? request.getParameter("referer")
					: request.getHeader("referer");
			request.setAttribute("referer", referer);
		}
		request.setAttribute("errorMsg", request.getParameter("errorMsg"));
		// return "user/teacherLoginPage";
		return WebUtils.isPad(request) ? "user/padLoginPage" : "user/loginPage";
	}

	@RequestMapping("login/msLoginPage")
	@Deprecated
	public String msLoginPage(HttpServletRequest request) {

		try {
			if (LoginSesionUtil.hasLogon()) {
				return "redirect:"
						+ WebConstant.getLogonUrl(LoginSesionUtil.getUserTypeProjectName(),
								WebConstant.getSchoolIdsForCurrUser(LoginSesionUtil.getUserType()));
			}
		} catch (Exception e) {
			LOG.debug(LOG.isDebugEnabled() ? "session info is not exists." : null);
		}
		if (!"true".equals(request.getParameter("noreferer"))) {
			String referer = StringUtils.isNotBlank(request.getParameter("referer")) ? request.getParameter("referer")
					: request.getHeader("referer");
			request.setAttribute("referer", referer);
		}
		request.setAttribute("errorMsg", request.getParameter("errorMsg"));
		// return "user/msLoginPage";
		return WebUtils.isPad(request) ? "user/padLoginPage" : "user/loginPage";
	}

	@RequestMapping("login/doLogon")
	public String doLogon(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {

		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");

		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
			attr.addAttribute("errorMsg", "用户名和密码均不能为空！");
			return "redirect:"
					+ (StringUtils.isBlank(request.getHeader("referer")) ? WebConstant.getLoginPageUrl(null) : request
							.getHeader("referer").split("\\?")[0]);
		}

		String errorMsg = "登录错误，请重试！";
		try {
			TblAuthUser user = null;
			try {
				user = userRpcService.checkPasswd(loginName, password);

				userHelper.doLogon(request, response, user);

				// 如果是第一次登录，则验证成功后跳转到 用户资料修改 页面
				if (userRpcService.isFirstLogon(user.getId()) && user.isStudent()) {
					return "redirect:" + WebConstant.getFirstLogonUrl(LoginSesionUtil.getUserTypeProjectName());
				}

				return "redirect:"
						+ LoginUtils.getLogonRedirectUrl(request, LoginSesionUtil.getUserTypeProjectName(),
								WebConstant.getSchoolIdsForCurrUser(user.getType()));
			} catch (BaseRuntimeException be) {
				if (RegexUtil.isEmail(loginName) || "REPEAT_LOGIN".equals(be.getErrorCode())) {
					throw be;
				}
				user = userRpcService.getUserInfoBySignName(loginName);
				// 如果当前用户不是学生，或者该学生用户的密码不为空，则不再继续进行下边的老用户密码验证
				if (user == null || (user.getType() != UserType.STUDENT) || StringUtils.isNotBlank(user.getPassword())) {
					throw be;
				}
				TbStudent student = studentRpcService.findByStudentId(user.getId());
				if (student == null || !EncryptTools.to_MD5(password).equals(student.getPassword())) {
					throw be;
				}

				userHelper.doLogon(request, response, user);

				return "redirect:/user/pwd/toresetpage";
			}

		} catch (BaseRuntimeException e) {
			errorMsg = e.getFriendlyMsg();
		}

		attr.addAttribute("errorMsg", errorMsg);
		return "redirect:" + request.getHeader("referer").split("\\?")[0];
	}

	@RequestMapping("login/doLogout")
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {

		LoginSesionUtil.removeSession(request, response);

		try {
			userRpcService.logout(LoginSesionUtil.getUserId(), ThreadUtil.getToken());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		String loginPage = "/user/login/loginPage";
		// String referer = request.getHeader("referer");
		// if (StringUtils.isNotBlank(referer)) {
		// if (referer.contains("/web-teacher/") ||
		// referer.contains("/web-teacher")) {
		// loginPage = "/user/login/teacherLoginPage";
		// } else if (referer.contains("/web-ms/") ||
		// referer.contains("/web-ms")) {
		// loginPage = "/user/login/msLoginPage";
		// }
		// }

		return "redirect:" + loginPage + "?noreferer=true";
	}

	@RequestMapping("reg/regPage")
	public String regPage(HttpServletRequest request) {

		try {
			if (LoginSesionUtil.hasLogon()) {
				return "redirect:"
						+ WebConstant.getLogonUrl(LoginSesionUtil.getUserTypeProjectName(),
								WebConstant.getSchoolIdsForCurrUser(LoginSesionUtil.getUserType()));
			}
		} catch (Exception e) {
			LOG.debug(LOG.isDebugEnabled() ? "session info is not exists." : null);
		}

		return WebUtils.isPad(request) ? "user/padRegPage" : "user/regPage";
	}

	@RequestMapping("reg/doReg")
	public String doReg(HttpServletRequest request, HttpServletResponse response) {

		// String email = request.getParameter("email");
		// String emailPrefix = request.getParameter("emailPrefix");
		String realName = request.getParameter("realName");
		String password = request.getParameter("password");
		String retype = request.getParameter("retype");
		// String code = request.getParameter("code");

		// request.setAttribute("emailPrefix", emailPrefix);
		// request.setAttribute("code", code);
		if (StringUtils.isBlank(realName)) {
			request.setAttribute("errorMsg", "真实姓名不能为空");
			return "user/regPage";
		}

		if (StringUtils.isBlank(password) || !password.equals(retype)) {
			request.setAttribute("errorMsg", "密码不能为空，且两次密码输入必须一致");
			return "user/regPage";
		}

		// if(!LoginSesionUtil.validateTmpToken(request, code)){
		// request.setAttribute("codeError", "验证码不正确");
		// return "user/regPage";
		// }

		try {
			TblAuthUser user = userHelper.createUser(realName, password);

			// userHelper.doLogon(request, response, user);

			return "redirect:/user/reg/success?acc=" + user.getSignName();
		} catch (BaseRuntimeException e) {
			request.setAttribute("errorMsg", e.getFriendlyMsg());
		}

		return "user/regPage";
	}

	@RequestMapping("reg/success")
	public String tobindOK(HttpServletRequest request) {

		request.setAttribute("acc", request.getParameter("acc"));
		request.setAttribute("pw", request.getParameter("pw"));

		return WebUtils.isPad(request) ? "user/pad_reg_success" : "user/reg_success";
	}

	@RequestMapping("findpw/step1")
	public String findPasswd1(HttpServletRequest request) {

		return "user/find_passwd_1";
	}

	@RequestMapping("findpw/tostep2")
	public String toFindPasswd2(HttpServletRequest request) {

		request.setAttribute("code", request.getParameter("code"));
		request.setAttribute("errorMsg", request.getParameter("errorMsg"));
		return "user/find_passwd_2";
	}

	@RequestMapping("findpw/step2")
	public String resetPwd(HttpServletRequest request, String code, RedirectAttributes attr) {

		String email = LoginSesionUtil.getMobileOrEmail(request);
		if (StringUtils.isBlank(code) || StringUtils.isBlank(email) || !LoginSesionUtil.validateCode(request, code)) {
			attr.addAttribute("code", code);
			attr.addAttribute("errorMsg", "对不起，页面已过期  ！");
			return "redirect:"
					+ (StringUtils.isBlank(request.getHeader("referer")) ? "/user/findpw/tostep2" : request.getHeader(
							"referer").split("\\?")[0]);
		}

		String password = request.getParameter("password");
		String retype = request.getParameter("retype");
		if (StringUtils.isBlank(password) || !password.equals(retype)) {
			attr.addAttribute("code", code);
			attr.addAttribute("errorMsg", "密码为空或两次密码输入不一致！");
			return "redirect:"
					+ (StringUtils.isBlank(request.getHeader("referer")) ? "/user/findpw/tostep2" : request.getHeader(
							"referer").split("\\?")[0]);
		}

		TblAuthUser pUser = userRpcService.getUserInfoByEmail(email);
		if (pUser == null) {
			attr.addAttribute("code", code);
			attr.addAttribute("errorMsg", "邮箱对应的用户不存在！");
			return "redirect:"
					+ (StringUtils.isBlank(request.getHeader("referer")) ? "/user/findpw/tostep2" : request.getHeader(
							"referer").split("\\?")[0]);
		}
		TblAuthUser user = new TblAuthUser();
		user.setId(pUser.getId());
		user.setPassword(password);

		userRpcService.update(user);

		return "redirect:/user/findpw/step3";
	}

	@RequestMapping("findpw/step3")
	public String resetPwdSuccess(HttpServletRequest request) {

		return "user/find_passwd_3";
	}

	@RequestMapping("pwd/toresetpage")
	public String toResetInitPwdPage(HttpServletRequest request) {

		// if(StringUtils.isBlank(code) ||
		// !LoginSesionUtil.validateCode(request, code)){
		// request.setAttribute("errorMsg", "对不起，页面已过期！");
		// }
		// String code = RandomUtil.randomAlphaAndNumStr(6);
		// LoginSesionUtil.saveTmpSessionInfo(request, code, "");

		// request.setAttribute("code", code);
		request.setAttribute("errorMsg", request.getParameter("errorMsg"));
		request.setAttribute("title", "修改登录密码");

		return "user/reset_pwd";
	}

	@RequestMapping("pwd/reset")
	public String resetMyPwd(HttpServletRequest request, String code, RedirectAttributes attr) {

		// if(StringUtils.isBlank(code) ||
		// !LoginSesionUtil.validateCode(request, code)){
		// attr.addAttribute("code", code);
		// attr.addAttribute("errorMsg", "对不起，页面已过期，请从修改密码 入口重新！");
		// return "redirect:" +
		// (StringUtils.isBlank(request.getHeader("referer")) ?
		// "/user/pwd/toresetpage" :
		// request.getHeader("referer").split("\\?")[0]);
		// }

		String oldpwd = request.getParameter("oldpwd");
		if (StringUtils.isBlank(oldpwd)) {
			attr.addAttribute("code", code);
			attr.addAttribute("errorMsg", "旧密码不能为空！");
			return "redirect:"
					+ (StringUtils.isBlank(request.getHeader("referer")) ? "/user/pwd/toresetpage" : request.getHeader(
							"referer").split("\\?")[0]);
		}

		String password = request.getParameter("password");
		String retype = request.getParameter("retype");
		if (StringUtils.isBlank(password) || !password.equals(retype)) {
			attr.addAttribute("code", code);
			attr.addAttribute("errorMsg", "密码为空或两次密码输入不一致！");
			return "redirect:"
					+ (StringUtils.isBlank(request.getHeader("referer")) ? "/user/pwd/toresetpage" : request.getHeader(
							"referer").split("\\?")[0]);
		}

		boolean result = userRpcService.checkPasswd(LoginSesionUtil.getUserId(), oldpwd);

		if (!result) {
			TblAuthUser pUser = userRpcService.getUserInfoById(LoginSesionUtil.getUserId());
			if (StringUtils.isNotBlank(pUser.getPassword())) { // 如果用户user表中的密码不为空，则直接返回错误信息
				attr.addAttribute("code", code);
				attr.addAttribute("errorMsg", "旧密码不正确！");
				return "redirect:"
						+ (StringUtils.isBlank(request.getHeader("referer")) ? "/user/pwd/toresetpage" : request.getHeader(
								"referer").split("\\?")[0]);
			}

			TbStudent std = studentRpcService.findByStudentId(LoginSesionUtil.getUserId());
			if (!EncryptTools.to_MD5(oldpwd).equals(std.getPassword())) {
				attr.addAttribute("code", code);
				attr.addAttribute("errorMsg", "旧密码不正确！");
				return "redirect:"
						+ (StringUtils.isBlank(request.getHeader("referer")) ? "/user/pwd/toresetpage" : request.getHeader(
								"referer").split("\\?")[0]);
			}

		}

		TblAuthUser user = new TblAuthUser();
		user.setId(LoginSesionUtil.getUserId());
		user.setPassword(password);

		userRpcService.update(user);

		return "redirect:/user/pwd/resetok";
	}

	@RequestMapping("pwd/resetok")
	public String pwdResetOk(HttpServletRequest request) {

		return "user/pwd_reset_ok";
	}
}
