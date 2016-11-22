package com.yisi.stiku.basedata.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esotericsoftware.minlog.Log;
import com.yisi.stiku.auditlog.rpc.AuditLogFacade;
import com.yisi.stiku.basedata.dao.TbLoginLogDao;
import com.yisi.stiku.basedata.dao.impl.TblAuthUserDaoImpl;
import com.yisi.stiku.basedata.dao.impl.TblUserDataPrivDaoImpl;
import com.yisi.stiku.basedata.entity.TbLoginLog;
import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.entity.TblUserDataPriv;
import com.yisi.stiku.basedata.entity.TbtTeacher;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.common.bean.PageInfo.OrderDirection;
import com.yisi.stiku.common.bean.UserType;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.common.utils.ThreadUtil;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.priv.entity.UserR2Role;
import com.yisi.stiku.priv.service.impl.UserR2RoleServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(UserRpcService.class)
public class TblAuthUserServiceImpl extends BaseServiceImpl<Long, TblAuthUser>
		implements UserRpcService {

	@Resource
	private TblAuthUserDaoImpl tblAuthUserDaoImpl;

	@Resource
	private TbtTeacherServiceImpl tbtTeacherServiceImpl;

	@Resource
	private TblUserDataPrivDaoImpl tblUserDataPrivDaoImpl;

	@Resource
	private TblUserDataPrivServiceImpl tblUserDataPrivServiceImpl;

	@Resource
	private UserR2RoleServiceImpl userR2RoleServiceImpl;

	@Resource
	private TbLoginLogDao tbLoginLogDao;

	@Resource
	private AuditLogFacade auditLogFacade;

	// @Resource
	// private TbStudentServiceImpl tbStudentServiceImpl;

	private Md5PasswordEncoder encoder = new Md5PasswordEncoder();

	@Override
	protected BaseDaoImpl<Long, TblAuthUser> getBaseDaoImpl() {

		return tblAuthUserDaoImpl;
	}

	@Override
	@Transactional
	public boolean save(TblAuthUser userInfo) {

		if (userInfo == null) {
			return false;
		}
		TblAuthUser pUser = tblAuthUserDaoImpl.exists(userInfo);
		if (pUser != null && pUser.getId() != null && pUser.getId() > 0 && // 这里判断是否存在signName、email
																			// 或者mobile
																			// 相同的用户
				(userInfo.getId() == null || userInfo.getId() <= 0l // 这里判断是否为新增用户，新增用户不允许有已存在的signName、email或者mobile
				|| userInfo.getId().longValue() != pUser.getId().longValue() // 这里判断如果为修改用户信息，则userInfo的id必须要与数据库中的id一致，否则也抛出异常
				)) {
			// String msg = "";
			String account = "";
			if (StringUtils.isNotBlank(userInfo.getMobile())
					&& userInfo.getMobile().equals(pUser.getMobile())) {
				// msg += "手机号";
				account += userInfo.getMobile();
			}
			if (StringUtils.isNotBlank(userInfo.getEmail())
					&& userInfo.getEmail().equals(pUser.getEmail())) {
				// msg += StringUtils.isBlank(msg) ? "邮箱" : "和邮箱";
				account += StringUtils.isBlank(account) ? userInfo.getEmail()
						: " and " + userInfo.getEmail();
			}
			if (StringUtils.isNotBlank(userInfo.getSignName())
					&& userInfo.getSignName().equals(pUser.getSignName())) {
				// msg += StringUtils.isBlank(msg) ? "用户名" : "和用户名";
				account += StringUtils.isBlank(account) ? userInfo
						.getSignName() : " and " + userInfo.getSignName();
			}
			throw new BaseRuntimeException(EXIST_ERROR_CODE, account
					+ "已存在，请修改后重试！");
		}
		if (userInfo.getId() == null || userInfo.getId() <= 0l) {
			userInfo.setDbState(EntityState.NORMAL);
			userInfo.setCreateTime(new Date());
			userInfo.setLastUptime(userInfo.getCreateTime());

			this.insertReturnPK(userInfo);
			// 插入新增的数据之后，才能设置初始密码，否则userInfo.getId()为空，会导致密码不一致
			// String initPasswd =
			// encoder.encodePassword(StringUtils.isBlank(userInfo.getPassword())
			// ? "123456" : userInfo.getPassword(), userInfo.getId());
			if (StringUtils.isBlank(userInfo.getPassword())) {
				userInfo.setPassword("123456");
			}
		} else if (UserType.STUDENT != userInfo.getType()
				&& UserType.REG_STUDENT != userInfo.getType()) {
			TblAuthUser preUser = this.findById(userInfo.getId());
			// 如果当前操作改变了用户的类型，则需要将原有的用户对应的角色关系删除掉
			if (preUser.getType() != userInfo.getType()) {
				userR2RoleServiceImpl.deleteByUserAndRole(userInfo.getId(),
						preUser.getType());
			}
		}

		if (UserType.STUDENT != userInfo.getType()
				&& UserType.REG_STUDENT != userInfo.getType()) {
			boolean exists = userR2RoleServiceImpl.exists(userInfo.getId(),
					userInfo.getType());
			if (!exists) {
				UserR2Role urr = new UserR2Role();
				urr.setRoleId(userInfo.getType().intValue());
				urr.setUserId(userInfo.getId());
				userR2RoleServiceImpl.insert(urr);
			}
		}

		if (userInfo.getId() != null) {
			if (UserType.TEACHER == userInfo.getType()) {
				TbtTeacher teacher = new TbtTeacher();
				teacher.setUserId(userInfo.getId());

				TbtTeacher pTeacher = tbtTeacherServiceImpl
						.findOneByEntity(teacher);
				if (pTeacher == null) {

					Integer schoolId = handleDataPriv(userInfo); // 如果是新增的老师用户，则需要系统自动将当前操作用户的第一个学校作为其数据权限

					if (schoolId != null) {
						teacher.setSchoolId(schoolId.longValue());
					}

					teacher.setName(userInfo.getShowName());
					teacher.setVersion(0);
					tbtTeacherServiceImpl.insert(teacher);

				}
			}
			/*
			 * else if(UserType.STUDENT == userInfo.getType()){ //插入一条学生的信息
			 * TbStudent student = new TbStudent();
			 * student.setId(userInfo.getId());
			 * student.setEmail(userInfo.getEmail());
			 * student.setPhoneNum(userInfo.getMobile()); student.setStatus(0);
			 * 
			 * tbStudentServiceImpl.insert(t); }
			 */
		}

		// if(StringUtils.isNotBlank(userInfo.getPassword())){//如果用户对象中包含密码，则先将密码加密
		// String encryptPasswd = encoder.encodePassword(userInfo.getPassword(),
		// userInfo.getId());
		// userInfo.setPassword(encryptPasswd);
		// }
		if (StringUtils.isNotBlank(userInfo.getPassword())) {// 如果用户对象中包含密码，则先将密码加密
			String encryptPasswd = encoder.encodePassword(
					userInfo.getPassword(), userInfo.getId());
			userInfo.setPassword(encryptPasswd);
		}
		auditLogFacade.saveAuditLog(null, userInfo, "添加用户");
		return updateByPKForAdmin(userInfo);
	}

	private boolean updateByPKForAdmin(TblAuthUser userInfo) {

		return tblAuthUserDaoImpl.updateByPKForAdmin(userInfo);
	}

	/**
	 * 
	 * @param userInfo
	 *            如果是老师，则需要将当前用户的第一个学校级别的数据权限赋予给该老师
	 */
	private Integer handleDataPriv(TblAuthUser userInfo) {

		Integer schoolId = null;
		Long operUserId = ThreadUtil.getUserId();

		Long schoolIdForNewTeacher = ThreadUtil.get("schoolIdForNewTeacher");
		if (operUserId != null) {
			List<TblUserDataPriv> dataPrivList = tblUserDataPrivServiceImpl
					.loadAllUserDataPriv(operUserId);
			if (dataPrivList != null && !dataPrivList.isEmpty()) {
				TblUserDataPriv dataPriv = null;
				for (TblUserDataPriv currPriv : dataPrivList) {
					if (currPriv.getSchoolId() != null) {
						dataPriv = currPriv;
						schoolId = currPriv.getSchoolId();

						break;
					}
				}

				if (dataPriv != null) {
					dataPriv.setId(null);
					// 如果是新增老师则需要将老师管理页面下拉的学校关联进去
					if (schoolIdForNewTeacher != null)
					{
						schoolId = schoolIdForNewTeacher.intValue();
						dataPriv.setSchoolId(schoolIdForNewTeacher.intValue());
						List<TblUserDataPriv> cuttUserDataPrivs = tblUserDataPrivServiceImpl
								.loadAllUserDataPriv(operUserId);

						for (TblUserDataPriv tblUserDataPriv : cuttUserDataPrivs) {
							if (tblUserDataPriv.getSchoolId().equals(schoolId))
							{
								dataPriv.setSchoolName(tblUserDataPriv.getSchoolName());
								dataPriv.setCityId(tblUserDataPriv.getCityId());
								dataPriv.setCityName(tblUserDataPriv.getCityName());
								dataPriv.setCountyId(tblUserDataPriv.getCountyId());
								dataPriv.setCountyName(tblUserDataPriv.getCountyName());
								dataPriv.setProvinceId(tblUserDataPriv.getProvinceId());
								dataPriv.setProvinceName(tblUserDataPriv.getProvinceName());
							}

						}

					}

					dataPriv.setUserId(userInfo.getId());
					dataPriv.setUserName(userInfo.getShowName());
					dataPriv.setUserType(UserType.TEACHER);
					try {
						tblUserDataPrivServiceImpl.insert(dataPriv);
					} catch (Exception e) {
						// 这里如果出现异常，不用把异常抛出，因为有可能是因为该用户已经拥有该数据权限，导致唯一键冲突引起的，只需简单打印日志，便于查找问题即可
						Log.warn(e.getMessage() + ", userId:"
								+ userInfo.getId() + ",schoolId:"
								+ dataPriv.getSchoolId());
					}
				}
			}

		}

		return schoolId;
	}

	@Override
	public boolean update(TblAuthUser t) {

		if (EntityState.INVALID.equals(t.getDbState())) {
			// 只有代理才需要删除关系 for 老师关联学生需求
			if (t.isAgent())
			{

				tblUserDataPrivDaoImpl.deleteByUserId(t.getId());
			}

			auditLogFacade.saveAuditLog(null, t, "注销用户");
		}
		if (EntityState.NORMAL.equals(t.getDbState())) {
			auditLogFacade.saveAuditLog(null, t, "启用用户");
		}
		if (StringUtils.isNotBlank(t.getPassword())) {// 如果用户对象中包含密码，则先将密码加密
			String encryptPasswd = encoder.encodePassword(t.getPassword(),
					t.getId());
			t.setPassword(encryptPasswd);
		}
		return super.update(t);
	}

	/**
	 * 
	 * @param userId
	 * @return 如果指定的用户可以被启用，则返回true，否则返回false
	 */
	public boolean canResetNormal(long userId) {

		List<TblAuthUser> userList = tblAuthUserDaoImpl
				.existEmailOrMobileForUserId(userId);
		return userList == null || userList.isEmpty();
	}

	@Override
	public boolean updateLoginInfo(long userId, int userType, String token, String loginIP) {

		// TblAuthUser user = new TblAuthUser();
		// user.setId(userId);
		// user.setLastLogonIp(loginIP);
		// user.setLastLogonTime(new Date());
		// user.setLastUptime(user.getLastLogonTime());

		TbLoginLog loginLog = new TbLoginLog();
		loginLog.setLogonTime(new Date());
		loginLog.setUserType(userType);
		loginLog.setUserId(userId);
		loginLog.setLogonIp(loginIP);
		loginLog.setToken(token);

		tbLoginLogDao.insert(loginLog);

		// return super.update(user);
		return true;
	}

	@Override
	public boolean isFirstLogon(long userId) {

		return tbLoginLogDao.getLogonCnt(userId) < 2;
	}

	@Override
	public boolean logout(long userId, String token) {

		TbLoginLog loginLog = new TbLoginLog();
		loginLog.setLogoutTime(new Date());
		loginLog.setToken(token);

		return tbLoginLogDao.updateByPK(loginLog) >= 0;
	}

	@Override
	public TblAuthUser getUserInfoById(long userId) {

		return findById(userId);
	}

	@Override
	public TblAuthUser getUserInfoByEmail(String email) {

		TblAuthUser tmpAuthUser = new TblAuthUser();
		tmpAuthUser.setEmail(email);
		return findOneByEntity(tmpAuthUser);
	}

	@Override
	public TblAuthUser getUserInfoByMobile(String mobile) {

		TblAuthUser tmpAuthUser = new TblAuthUser();
		tmpAuthUser.setMobile(mobile);
		return findOneByEntity(tmpAuthUser);
	}

	@Override
	public TblAuthUser getUserInfoBySignName(String signName) {

		TblAuthUser tmpAuthUser = new TblAuthUser();
		tmpAuthUser.setSignName(signName);
		return findOneByEntity(tmpAuthUser);
	}

	@Override
	public boolean resetPasswd(long userId, String password) {

		// String encPasswd = encoder.encodePassword(password, userId);
		TblAuthUser user = new TblAuthUser();
		user.setId(userId);
		user.setPassword(password);

		auditLogFacade.saveAuditLog(user, user, "重置密码");

		return this.update(user);
	}

	@Override
	public boolean checkPasswd(long userId, String password) {

		TblAuthUser user = this.findById(userId);

		if (user == null) {
			return false;
		}

		String encPasswd = encoder.encodePassword(password, userId);

		return StringUtils.isNotBlank(encPasswd)
				&& encPasswd.equals(user.getPassword());
	}

	@Override
	public TblAuthUser checkPasswd(String loginName, String password) {

		if (StringUtils.isBlank(password) || StringUtils.isBlank(loginName)) {
			throw new BaseRuntimeException("ILLEGAL_PARAM", "用户名和密码均不能为空");
		}
		TblAuthUser user = null;
		if (RegexUtil.isPhone(loginName)) {
			user = this.getUserInfoByMobile(loginName);
		} else if (RegexUtil.isEmail(loginName)) {
			user = this.getUserInfoByEmail(loginName);
		} else {
			user = this.getUserInfoBySignName(loginName);
		}

		if (user == null) {
			throw new BaseRuntimeException("NOT_EXIST", "用户名 '" + loginName
					+ "' 不存在");
		}

		if (!EntityState.NORMAL.equals(user.getDbState())) {
			throw new BaseRuntimeException("NOT_FORBIDDEN", "用户名 '" + loginName
					+ "' 已被禁用，请联系管理员！");
		}

		String encPasswd = encoder.encodePassword(password, user.getId());

		if (encPasswd.equals(user.getPassword())) {
			return user;
		}

		throw new BaseRuntimeException("ILLEGAL_PARAM", "密码不正确",
				"password invalid for loginName " + loginName);
	}

	@Override
	public Page<TblAuthUser> searchUser(TblAuthUser authUser, String keyword,
			int currPage, int maxRows) {

		String dynamicSql = null;
		if (StringUtils.isNotBlank(keyword)) {
			dynamicSql = " (sign_name like '%" + keyword
					+ "%' or real_name like '%" + keyword
					+ "%' or nick like '%" + keyword + "%' or email like '%"
					+ keyword + "%' or mobile like '%" + keyword + "%')";
		}
		return this.findByPage(authUser, currPage, maxRows, "last_uptime",
				OrderDirection.desc, dynamicSql);
	}

	@Override
	public Page<TblAuthUser> searchUserByFilterUsers(TblAuthUser authUser,
			List<Long> users, String keyword, int currPage, int maxRows) {

		String sqlFilte = org.apache.commons.lang.StringUtils.join(users, ',');

		String dynamicSql = null;
		if (StringUtils.isNotBlank(keyword)) {
			dynamicSql = " (sign_name like '%" + keyword
					+ "%' or real_name like '%" + keyword
					+ "%' or nick like '%" + keyword + "%' or email like '%"
					+ keyword + "%' or mobile like '%" + keyword + "%')";
		}
		if (StringUtils.isNotBlank(sqlFilte)) {
			if (StringUtils.isNotBlank(dynamicSql)) {
				dynamicSql += "and id in(" + sqlFilte + ")";
			} else {
				dynamicSql = "  id in(" + sqlFilte + ")";
			}
		}

		return this.findByPage(authUser, currPage, maxRows, "last_uptime",
				OrderDirection.desc, dynamicSql);
	}

	@Override
	@Transactional
	public boolean updateUser2Role(long userId, String[] roleIds) {

		tblAuthUserDaoImpl.clearRoleByUserId(userId);

		tblAuthUserDaoImpl.insertUser2Roles(userId, roleIds);
		auditLogFacade.saveAuditLog(userId, roleIds, "修改用户角色");
		return true;
	}

	@Override
	public List<TblAuthUser> findUsers(Long... userIds) {

		if (userIds == null || userIds.length <= 0) {
			return null;
		}

		return tblAuthUserDaoImpl.findUsers(userIds);
	}

	@Override
	public String findMaxIdByPrefix(String cardPrefix) {

		return tblAuthUserDaoImpl.findMaxIdByPrefix(cardPrefix);
	}

}
