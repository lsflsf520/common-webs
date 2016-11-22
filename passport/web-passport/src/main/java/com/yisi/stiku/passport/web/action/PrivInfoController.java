package com.yisi.stiku.passport.web.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.basedata.entity.TbArea;
import com.yisi.stiku.basedata.entity.TbClass;
import com.yisi.stiku.basedata.entity.TbSchool;
import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.entity.TblConnectUser;
import com.yisi.stiku.basedata.rpc.constant.ThirdLoginType;
import com.yisi.stiku.basedata.rpc.service.AreaRpcService;
import com.yisi.stiku.basedata.rpc.service.ClassRpcService;
import com.yisi.stiku.basedata.rpc.service.ConnectRpcService;
import com.yisi.stiku.basedata.rpc.service.SchoolRpcService;
import com.yisi.stiku.basedata.rpc.service.StudentRpcService;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.cache.constant.DefaultJedisKeyNS;
import com.yisi.stiku.cache.redis.ShardJedisTool;
import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.common.utils.ScoreSagmentUtils;
import com.yisi.stiku.common.utils.StringUtil;
import com.yisi.stiku.common.utils.UserInfoUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.passport.service.impl.UserLoginHelper;
import com.yisi.stiku.wallet.entity.Account;
import com.yisi.stiku.wallet.entity.FuncCard;
import com.yisi.stiku.wallet.rpc.service.AccountRpcService;
import com.yisi.stiku.wallet.rpc.service.AccountSecurityRpcService;
import com.yisi.stiku.wallet.rpc.service.FeeModeService;
import com.yisi.stiku.wallet.rpc.service.FuncCardRpcService;
import com.yisi.stiku.wallet.rpc.service.PayCheckRpcService;
import com.yisi.stiku.web.constant.WebConstant;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

/**
 * 
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/priv/info")
public class PrivInfoController {

	private final static Logger LOG = LoggerFactory.getLogger(PrivInfoController.class);

	@Resource
	private UserRpcService userRpcService;

	@Resource
	private StudentRpcService studentRpcService;

	@Resource
	private SchoolRpcService schoolRpcService;

	@Resource
	private AreaRpcService areaRpcService;

	@Resource
	private ClassRpcService classRpcService;

	@Resource
	private ConnectRpcService connectRpcService;

	@Resource
	private UserLoginHelper userLoginHelper;

	@Resource
	private FuncCardRpcService funcCardRpcService;

	@Resource
	private PayCheckRpcService payCheckRpcService;

	@Resource
	private AccountRpcService accountRpcService;

	@Resource
	private AccountSecurityRpcService accountSecurityRpcService;

	@Resource
	private FeeModeService feeModeService;

	@RequestMapping("edit")
	public String editInfo(HttpServletRequest request, HttpServletResponse response) {

		long userId = LoginSesionUtil.getUserId();

		TbStudent student = studentRpcService.findByStudentId(userId);

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		if (student != null) {
			Long schoolId = student.getSchoolId();
			int gradeYear = student.getGradeYear();

			TbSchool tbSchool = schoolRpcService.findTbSchoolById(schoolId);
			TbClass tbClass = classRpcService.findById(student.getClassId());
			long provinceId = tbSchool.getProvinceId();
			long cityId = tbSchool.getCityId();
			Long countyId = tbSchool.getCountyId();

			TbArea province = areaRpcService.findById(provinceId);
			TbArea city = areaRpcService.findById(cityId);

			TbArea county = null;
			if (countyId != null) {
				county = areaRpcService.findById(countyId);
			}

			List<TbSchool> schoolList = null;
			boolean hasCounty = areaRpcService.hasChild(cityId);
			if (hasCounty && countyId != null) {
				schoolList = schoolRpcService.findByCountyId(countyId);
			} else {
				schoolList = schoolRpcService.findByCityId(cityId);
			}

			List<TbClass> classList = classRpcService
					.getBySchoolIdAndGradeYear(schoolId, gradeYear);

			String[] scoreSegList = ScoreSagmentUtils.getScoreSagmentArrayStr(
					provinceId, student.getsType());
			if (student.getStudentSection() != null) {
				request.setAttribute("userScoreSegment",
						scoreSegList[student.getStudentSection()]);
				request.setAttribute("scoreSegment",
						student.getStudentSection());
			}

			request.setAttribute("gradeId", getGradeId(gradeYear));
			request.setAttribute("scoreSegList", scoreSegList);
			request.setAttribute("classList", classList);
			request.setAttribute("province", province);
			request.setAttribute("city", city);
			request.setAttribute("county", county);
			request.setAttribute("school", tbSchool);
			request.setAttribute("tbClass", tbClass);
			request.setAttribute("cityList",
					areaRpcService.findByPId(provinceId));
			request.setAttribute("countyList", areaRpcService.findByPId(cityId));
			request.setAttribute("schoolList", schoolList);
		}

		request.setAttribute("title", "修改资料");
		request.setAttribute("preview", request.getParameter("preview"));
		request.setAttribute("tbStudent", student);
		request.setAttribute("provinceList", areaRpcService.findAllProvinces());
		request.setAttribute("SCORE_SAGMENT_COMMON_STR",
				ScoreSagmentUtils.SCORE_SAGMENT_COMMON_STR);
		request.setAttribute("SCORE_SAGMENT_BASE_STR",
				ScoreSagmentUtils.SCORE_SAGMENT_BASE_STR);
		request.setAttribute("SCORE_SAGMENT_JIANGSU_1_STR",
				ScoreSagmentUtils.SCORE_SAGMENT_JIANGSU_1_STR);
		request.setAttribute("SCORE_SAGMENT_JIANGSU_2_STR",
				ScoreSagmentUtils.SCORE_SAGMENT_JIANGSU_2_STR);
		request.setAttribute("currentYear", year);
		request.setAttribute("currentMonth", cal.get(Calendar.MONTH) + 1);

		TblAuthUser userInfo = userRpcService.getUserInfoById(userId);
		request.setAttribute("signName", userInfo.getSignName());
		if (StringUtils.isNotBlank(userInfo.getEmail())) {
			request.setAttribute("email", StringUtil.stringHide(userInfo.getEmail()));
		}
		if (StringUtils.isNotBlank(userInfo.getMobile())) {
			request.setAttribute("mobile",
					StringUtil.stringHide(userInfo.getMobile()));
		}

		String taocanLevelStr = "普通会员";
		try {
			FuncCard funcCard = funcCardRpcService.loadByUserId(userId);
			if (funcCard != null) {
				long endTime = payCheckRpcService.getResEndTime(userId,
						funcCard.getResInfoId());
				if (endTime > new Date().getTime()) {
					request.setAttribute("endTime",
							DateUtil.getDateStr(endTime));
					request.setAttribute("cardCode", funcCard.getDbType()
							.getDbCode());

					taocanLevelStr = funcCard.getDbType().getDesc();
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		List<TblConnectUser> connUsers = connectRpcService
				.loadConnectUsers(userId);// 查询第三方绑定的用户信息
		if (connUsers != null && !connUsers.isEmpty()) {
			for (TblConnectUser connUser : connUsers) {
				if (ThirdLoginType.qq.equals(connUser.getLoginType())) {
					request.setAttribute("bindQQ", true);
					request.setAttribute("qqAvatar", connUser.getAvatar());
					request.setAttribute("nick", connUser.getNick());
				}
			}
		}

		request.setAttribute("taocanLevelStr", taocanLevelStr);
		request.setAttribute("baseDataDomain", WebConstant.getBaseDataDomain());
		request.setAttribute("backUrl", request.getAttribute("studentIndex"));

		// 过了2015-11-23 18:00:00时间之后，此段代码可以删掉
		String closeEditTime = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "priv.info.edit.close.time",
				"2015-11-27 18:00:00");

		request.setAttribute("backUrl", request.getAttribute("studentIndex"));
		request.setAttribute("canEditClass",
				student == null || isCanEditSchool(student.getSchoolId(), "can.edit.class.schoolIds"));
		request.setAttribute(
				"canClose",
				new Date().getTime() > DateUtil.parseDateTime(closeEditTime).getTime()
						&& student != null && !UserInfoUtil.isTYStudent(student.getCardNum()) && !isCanEditSchool(student
								.getSchoolId(), "can.edit.schoolIds"));

		return "user/edit_info";
	}

	private boolean isCanEditSchool(long schoolId, String zkPropKey) {

		String[] valArr = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, zkPropKey);

		if (valArr != null) {
			for (String val : valArr) {
				if (StringUtils.isNotBlank(val) && val.trim().equals(schoolId + "")) {
					return true;
				}
			}
		}

		return false;
	}

	@RequestMapping("account")
	public String account(HttpServletRequest request,
			HttpServletResponse response) {

		long userId = LoginSesionUtil.getUserId();

		Account account = accountRpcService.queryAccountByUserId(userId);
		boolean alreadySetTranPwd = accountSecurityRpcService.hasAccountSecurityByUserId(userId);

		request.setAttribute("account", account);
		request.setAttribute("alreadySetTranPwd", alreadySetTranPwd);
		request.setAttribute("title", "账号管理");

		TbStudent student = studentRpcService.findByStudentId(userId);
		if (student != null) {
			request.setAttribute("isZizhuPrint", feeModeService.isZizhuPrintMode(student));
		}

		// // 过了2015-11-23 18:00:00时间之后，此段代码可以删掉
		// String closeEditTime = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH,
		// "priv.info.edit.close.time", "2015-11-27 18:00:00");
		// request.setAttribute(
		// "canClose",
		// new Date().getTime() > DateUtil.parseDateTime(closeEditTime)
		// .getTime()
		// && (student == null || !UserInfoUtil
		// .isTYStudent(student.getCardNum())));

		return "user/account-manager";
	}

	@RequestMapping("save")
	public void saveInfo(HttpServletRequest request,
			HttpServletResponse response, TbStudent student) {

		try {
			if (student == null) {
				WebUtils.writeJson(
						OperationResult.buildFailureResult("对不起，参数错误！"),
						request, response);
				return;
			}
			student.setId(LoginSesionUtil.getUserId());
			student.setCardNum(LoginSesionUtil.getSignName());
			if (student.getsType() == null
					|| getGradeId(student.getGradeYear()) == 1) {
				student.setsType(2);
			}
			boolean success = studentRpcService.save(student);

			if (StringUtils.isNotBlank(student.getRealName())) {
				TblAuthUser user = new TblAuthUser();
				user.setId(student.getId());
				user.setRealName(student.getRealName());
				success = userRpcService.update(user);
			}

			if (success) {
				// 在这更新一下session中的学生信息
				Map<String, Object> studentSessionInfo = userLoginHelper
						.parseStudentSessionInfo(student);
				LoginSesionUtil.updateSessionInfo(studentSessionInfo);
				ShardJedisTool.del(DefaultJedisKeyNS.index, student.getId());

				WebUtils.writeJson(OperationResult.buildSuccessResult("操作成功！"),
						request, response);
				return;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		WebUtils.writeJson(
				OperationResult.buildFailureResult("对不起，服务器发生错误，请重试！"),
				request, response);
	}

	@RequestMapping("bindEmail")
	public void bindEmail(HttpServletRequest request,
			HttpServletResponse response, String email, String code) {

		if (StringUtils.isBlank(email) || !RegexUtil.isEmail(email)
				|| StringUtils.isBlank(code)) {
			WebUtils.writeJson(
					OperationResult.buildFailureResult("验证码或邮箱为空，或邮箱格式不正确！"),
					request, response);
			return;
		}

		if (!LoginSesionUtil.validateCode(request, code)) {
			WebUtils.writeJson(
					OperationResult.buildFailureResult("验证码和邮箱均不能为空！"),
					request, response);
			return;
		}

		TblAuthUser existUser = userRpcService.getUserInfoByEmail(email);
		if (existUser != null) {
			if (existUser.getId().equals(LoginSesionUtil.getUserId())) {
				WebUtils.writeJson(
						OperationResult.buildFailureResult("新邮箱和原邮箱一致，无须修改！"),
						request, response);
			} else {
				WebUtils.writeJson(
						OperationResult.buildFailureResult("邮箱已被使用，请更换邮箱！"),
						request, response);
			}
			return;
		}

		TblAuthUser currUser = new TblAuthUser();
		currUser.setId(LoginSesionUtil.getUserId());
		currUser.setEmail(email);

		userRpcService.update(currUser);

		WebUtils.writeJson(
				OperationResult.buildSuccessResult("操作成功！",
						StringUtil.stringHide(email)), request, response);
	}

	public static final int SPLIT_MONTH = 7;
	public static final int SPLIT_DAY = 1;

	private static int getGradeId(int gradeYear) {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		if (month >= SPLIT_MONTH && day >= SPLIT_DAY) {
			year += 1;
		}
		year -= gradeYear;
		return year;
	}

}
