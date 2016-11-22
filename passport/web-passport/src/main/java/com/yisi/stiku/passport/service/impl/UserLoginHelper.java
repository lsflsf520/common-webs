package com.yisi.stiku.passport.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.entity.TbClass;
import com.yisi.stiku.basedata.entity.TbSchool;
import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.entity.TbtTeacher;
import com.yisi.stiku.basedata.rpc.service.ClassRpcService;
import com.yisi.stiku.basedata.rpc.service.DataPrivRpcService;
import com.yisi.stiku.basedata.rpc.service.SchoolRpcService;
import com.yisi.stiku.basedata.rpc.service.StudentRpcService;
import com.yisi.stiku.basedata.rpc.service.TbCardRpcService;
import com.yisi.stiku.basedata.rpc.service.TeacherRpcService;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.common.bean.GlobalResultCode;
import com.yisi.stiku.common.bean.UserType;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.RandomUtil;
import com.yisi.stiku.common.utils.ThreadUtil;
import com.yisi.stiku.common.utils.UserInfoUtil;
import com.yisi.stiku.priv.entity.Role;
import com.yisi.stiku.priv.rpc.service.RoleMgrRpcService;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.WebUtils;

@Service
public class UserLoginHelper {

	private final static Logger LOG = LoggerFactory.getLogger(UserLoginHelper.class);

	@Resource
	private UserRpcService userRpcService;

	@Resource
	private StudentRpcService studentRpcService;

	@Resource
	private SchoolRpcService schoolRpcService;

	@Resource
	private ClassRpcService classRpcService;

	@Resource
	private TbCardRpcService tbCardRpcService;

	@Resource
	private TeacherRpcService teacherRpcService;

	@Resource
	private DataPrivRpcService dataPrivRpcService;

	@Resource
	private RoleMgrRpcService roleMgrRpcService;

	public TblAuthUser createUser(String nickOrRealName, String password) {

		String signName = null;
		boolean result = false;
		for (int i = 0; i < 3; i++) {
			try {
				signName = RandomUtil.randomNumCode(8);
				TblAuthUser authUser = new TblAuthUser();
				authUser.setSignName(signName);
				authUser.setNick(nickOrRealName);
				authUser.setRealName(nickOrRealName);
				authUser.setType(UserType.REG_STUDENT);
				if (StringUtils.isNotBlank(password)) {
					authUser.setPassword(password);
				}

				result = userRpcService.save(authUser);
				if (result) {
					break;
				}

			} catch (BaseRuntimeException e) {
				if (!UserRpcService.EXIST_ERROR_CODE.equals(e.getResultCode()
						.getCode())) {
					throw e;
				}
				LOG.debug("signName '" + signName
						+ "' already exists, then retry another");
			}
		}

		if (result && StringUtils.isNotBlank(signName)) {
			TblAuthUser authUser = userRpcService.getUserInfoBySignName(signName);

			try {
				tbCardRpcService.insert(authUser.getId(), signName);
			} catch (Exception e) {
				LOG.warn(e.getMessage() + ",cardNo:" + signName + ",userId:" + authUser.getId());
			}

			return authUser;
		}

		throw new BaseRuntimeException(GlobalResultCode.DB_OPER_ERROR);
	}

	/**
	 * 处理登录信息，将登录信息存入缓存，会写cookie以及更新登录记录等
	 * 
	 * @param request
	 * @param response
	 * @param userRpcService
	 * @param user
	 * @return 返回一个token
	 */
	public String doLogon(HttpServletRequest request, HttpServletResponse response, TblAuthUser user) {

		if (user.isStudent() && !UserInfoUtil.isTYStudent(user.getSignName())) {// TY开头的学生账号为体验账号，登陆不做限制
			LoginSesionUtil.checkLoginState(user.getId());
		}

		Map<String, Object> sessionMap = parseSessionMap(user, request);

		String token = LoginSesionUtil.storeSession(sessionMap, response);

		ThreadUtil.clear();
		ThreadUtil.setToken(token);

		try {
			userRpcService.updateLoginInfo(user.getId(), user.getType(), token, WebUtils.getClientIp(request));
		} catch (Exception ex) {
			LOG.warn("error occured in update user login info,msg:" + ex.getMessage());
		}

		return token;
	}

	private Map<String, Object> parseSessionMap(TblAuthUser user, HttpServletRequest request) {

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put(ThreadUtil.USER_ID, user.getId());
		// sessionMap.put(LoginSesionUtil.EMAIL, user.getEmail());
		// sessionMap.put(LoginSesionUtil.MOBILE, user.getMobile());
		// sessionMap.put(LoginSesionUtil.QQ, user.get);
		sessionMap.put(ThreadUtil.REAL_NAME, user.getRealName());
		sessionMap.put(ThreadUtil.SIGN_NAME, user.getSignName());
		sessionMap.put(ThreadUtil.USER_SHOW_NAME, user.getShowName());
		sessionMap.put(ThreadUtil.USER_TYPE, user.getType());
		sessionMap.put(ThreadUtil.NICK, user.getNick());

		sessionMap.put(ThreadUtil.EQUIP_TYPE, WebUtils.getEquipType(request));
		sessionMap.put(ThreadUtil.LOGIN_IP, WebUtils.getClientIp(request));

		Role role = roleMgrRpcService.findById(user.getType());
		if (role != null) {
			sessionMap.put(ThreadUtil.USER_TYPE_LOGON_PROJECT, role.getProjectName());
		} else {
			LOG.warn("not find role with id " + user.getType() + " for user " + user.getId());
		}

		if (user.isStudent()) {
			TbStudent student = studentRpcService.findByStudentId(user.getId());
			sessionMap.putAll(parseStudentSessionInfo(student));
		} else {
			String aclCodeStr = dataPrivRpcService.getAclCodeStr(user.getId());
			if (StringUtils.isNotBlank(aclCodeStr)) {
				sessionMap.put(ThreadUtil.ACL_CODE, aclCodeStr);
			}
			if (user.isTeacher()) {
				TbtTeacher teacher = teacherRpcService.findByUserId(user.getId());
				sessionMap.put(ThreadUtil.TEACHER_ID, teacher.getId());
				if (!user.getShowName().endsWith("老师")) {
					sessionMap.put(ThreadUtil.USER_SHOW_NAME, user.getShowName() + "老师");
				}
			}
		}

		return sessionMap;
	}

	public Map<String, Object> parseStudentSessionInfo(TbStudent student) {

		Map<String, Object> sessionMap = new HashMap<String, Object>();
		if (student != null) {
			if (StringUtils.isNotBlank(student.getRealName())) {
				sessionMap.put(ThreadUtil.REAL_NAME, student.getRealName());
				sessionMap.put(ThreadUtil.USER_SHOW_NAME, student.getRealName());
			}
			if (StringUtils.isNotBlank(student.getUserIcon())) {
				sessionMap.put(ThreadUtil.USER_ICON, student.getUserIcon());
			}

			if (student.getClassId() != null) {
				sessionMap.put(ThreadUtil.CLASS_ID, student.getClassId());
				TbClass tbClass = classRpcService.findById(student.getClassId());
				if (tbClass != null) {
					sessionMap.put(ThreadUtil.CLASS_NAME, tbClass.getName());
				}
			}

			if (student.getSchoolId() != null) {
				sessionMap.put(ThreadUtil.SCHOOL_ID, student.getSchoolId());
				TbSchool school = schoolRpcService.findTbSchoolById(student.getSchoolId());
				if (school != null) {
					sessionMap.put(ThreadUtil.SCHOOL_NAME, school.getName());
				}
			}
			if (student.getStudentSection() != null) {
				sessionMap.put(ThreadUtil.SECTION, student.getStudentSection());
			}
			if (student.getGradeYear() != null) {
				sessionMap.put(ThreadUtil.GRADE_YEAR, student.getGradeYear());
			}
			if (student.getsType() != null) {
				sessionMap.put(ThreadUtil.STYPE, student.getsType());
			}
		}

		return sessionMap;
	}

}
