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

import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.rpc.service.StudentRpcService;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.msg.rpc.service.EmailSenderRpcService;
import com.yisi.stiku.passport.service.impl.UserLoginHelper;
import com.yisi.stiku.wallet.rpc.service.CigmaRpcService;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

@Controller
@RequestMapping("/app/user")
public class AppPassportController {
	
	private final static Logger LOG = LoggerFactory.getLogger(AppPassportController.class);

	@Resource
	private UserRpcService userRpcService;
	
	@Resource
	private UserLoginHelper userHelper;
	
	@Resource
	private EmailSenderRpcService emailSenderRpcService;
	
	@Resource
	private StudentRpcService studentRpcService;
	
	@Resource
	private CigmaRpcService cigmaRpcService;
	
	@RequestMapping("login/doLogon")
	public void appDoLogon(HttpServletRequest request, HttpServletResponse response){
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		int type = 0;
		if(request.getParameter("type") != null){
			 type = Integer.parseInt(request.getParameter("type").toString());
		}
		Map<String, Object> rtnMessageMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(password)){
			WebUtils.writeJson(OperationResult.buildFailureResult("用户名或密码不能为空"), request, response);
			return;
		}
		try{
			    TblAuthUser user = null;
				user = userRpcService.checkPasswd(loginName, password);
				String token = userHelper.doLogon(request, response, user);
				TbStudent tbStudent = studentRpcService.findByStudentId(user.getId());
				if(tbStudent != null){
					rtnMessageMap.put("sex", tbStudent.getSex());
					rtnMessageMap.put("serverStudentId", tbStudent.getId());
					rtnMessageMap.put("grade", tbStudent.getGradeYear());
					long studentId = tbStudent.getId();
					if(type == 1){//西格玛 登陆
						boolean isCigmaStudent = cigmaRpcService.isCigmaStudent(studentId);
						if(isCigmaStudent == false){
							WebUtils.writeJson(OperationResult.buildFailureResult("此用户不是西格玛账号用户！"), request, response);
						}
					}
				}else{
					WebUtils.writeJson(OperationResult.buildFailureResult("未找到此用户的学生信息"), request, response);
					return;
				}
				rtnMessageMap.put("name", user.getRealName());
				rtnMessageMap.put("status", 1);
				rtnMessageMap.put("userId", loginName);  
				rtnMessageMap.put("token", token);
		}catch(BaseRuntimeException e){
			WebUtils.writeJson(OperationResult.buildFailureResult(e.getFriendlyMsg()), request, response);
			return;
		} 
		WebUtils.writeJson(OperationResult.buildSuccessResult("登录成功",rtnMessageMap), request, response);
	}
	
	
	
}
