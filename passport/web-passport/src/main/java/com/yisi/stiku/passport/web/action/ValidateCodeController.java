package com.yisi.stiku.passport.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.RandomUtil;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.msg.rpc.service.EmailSenderRpcService;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

/**
 * 
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/valid")
public class ValidateCodeController {
	
	private final static Logger LOG = LoggerFactory.getLogger(ValidateCodeController.class);

	@Resource
	private UserRpcService userRpcService;
	
	@Resource
	private EmailSenderRpcService emailSenderRpcService;

	@RequestMapping("emailcode/send")
	public void sendEmailCode(HttpServletRequest request,
			HttpServletResponse response, String email) {
		String existCheck = request.getParameter("existCheck");
		if(StringUtils.isNotBlank(existCheck) && Boolean.valueOf(existCheck)){
			if(StringUtils.isBlank(email)){
				WebUtils.writeJson(
						OperationResult.buildFailureResult("邮箱不能为空！"), request,
						response);
				return;
			}
			
			if(userRpcService.getUserInfoByEmail(email) != null){
				WebUtils.writeJson(
						OperationResult.buildFailureResult("邮箱已被使用，请更换邮箱！"), request,
						response);
				return;
			}
		}
		
		if(StringUtils.isBlank(email)){
			try{
				Long userId = LoginSesionUtil.getUserId();
				TblAuthUser currUser = userRpcService.getUserInfoById(userId);
				if(currUser != null){
					email = currUser.getEmail();
				}
			}catch(BaseRuntimeException e){
				LOG.warn("get login user info error.");
			}
		}
		
		if(StringUtils.isBlank(email) || !RegexUtil.isEmail(email)){
			WebUtils.writeJson(
					OperationResult.buildFailureResult("请输入正确的邮箱号！"), request,
					response);
			return;
		}
		
		String code = RandomUtil.randomAlphaAndNumStr(6);
		LoginSesionUtil.saveTmpSessionInfo(request, code, email);

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(EmailSenderRpcService.TO, email);
		paramMap.put("code", code);
		
		
		boolean result = emailSenderRpcService.sendEmail(Integer.valueOf(ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "passport.reg.email.code")), paramMap);
		if (result) {
			WebUtils.writeJson(
					OperationResult.buildSuccessResult("验证码邮件发送成功！"), request,
					response);
			return;
		}

		WebUtils.writeJson(
				OperationResult.buildFailureResult("验证码邮件发送失败，请重试！"), request,
				response);
	}
	
	@RequestMapping("emailcode/check")
	public void checkEmailCode(HttpServletRequest request,
			HttpServletResponse response, String code){
		if(StringUtils.isBlank(code) || !LoginSesionUtil.validateCode(request, code)){
			WebUtils.writeJson(
					OperationResult.buildFailureResult("验证码有误，请重新输入！"), request,
					response);
			return;
		}
		
		WebUtils.writeJson(
				OperationResult.buildSuccessResult("验证成功！"), request,
				response);
	}
	
	@RequestMapping("emailcode/checksync")
	public String checkEmailCodeSync(HttpServletRequest request,
			HttpServletResponse response, String code){
		if(StringUtils.isBlank(code) || !LoginSesionUtil.validateCode(request, code)){
			return "redirect:" + request.getHeader("referer").split("\\?")[0] + "?code=" + code + "&errorMsg=" + "验证码有误，请重新输入！";
		}
		
		return "redirect:" + request.getParameter("successUrl") + "?code=" + code;
	}

}
