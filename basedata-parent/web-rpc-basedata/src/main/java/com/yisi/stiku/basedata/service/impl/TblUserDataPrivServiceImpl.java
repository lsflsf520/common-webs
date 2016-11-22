package com.yisi.stiku.basedata.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.yisi.stiku.auditlog.rpc.AuditLogFacade;
import com.yisi.stiku.basedata.dao.impl.TblUserDataPrivDaoImpl;
import com.yisi.stiku.basedata.entity.TbArea;
import com.yisi.stiku.basedata.entity.TbSchool;
import com.yisi.stiku.basedata.entity.TbTeacherLinkCls;
import com.yisi.stiku.basedata.entity.TblAuthUser;
import com.yisi.stiku.basedata.entity.TblUserDataPriv;
import com.yisi.stiku.basedata.entity.TbtTeacher;
import com.yisi.stiku.basedata.rpc.service.DataPrivRpcService;
import com.yisi.stiku.cache.constant.DefaultJedisKeyNS;
import com.yisi.stiku.cache.redis.ShardJedisTool;
import com.yisi.stiku.common.bean.UserType;
import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(DataPrivRpcService.class)
public class TblUserDataPrivServiceImpl extends
		BaseServiceImpl<Integer, TblUserDataPriv> implements DataPrivRpcService {

	private final static Logger LOG = LoggerFactory
			.getLogger(TblUserDataPrivServiceImpl.class);

	@Resource
	private TblUserDataPrivDaoImpl tblUserDataPrivDaoImpl;

	@Resource
	private TbAreaServiceImpl tbAreaServiceImpl;

	@Resource
	private TbSchoolServiceImpl tbSchoolServiceImpl;

	@Resource
	private TblAuthUserServiceImpl tblAuthUserServiceImpl;

	@Resource
	private TbtTeacherServiceImpl tbtTeacherServiceImpl;

	@Resource
	private AuditLogFacade auditLogFacade;

	@Override
	protected BaseDaoImpl<Integer, TblUserDataPriv> getBaseDaoImpl() {

		return tblUserDataPrivDaoImpl;
	}

	@Override
	@Transactional(value = "basedataTransactionManager")
	public boolean saveAclCode(long userId, String aclCodeStr) {

		// List<TblUserDataPriv> dataPrivs = new ArrayList<TblUserDataPriv>();
		TblAuthUser userInfo = tblAuthUserServiceImpl.getUserInfoById(userId);

		// EhCacheTool.remove(DefaultCacheNS.DATA_PRIV, "u"+userId);
		// //数据权限有更新，优先删除缓存
		ShardJedisTool.hdel(DefaultJedisKeyNS.dp, "u" + userId); // 数据权限有更新，优先删除缓存
		tblUserDataPrivDaoImpl.deleteByUserId(userId);
		List<TblUserDataPriv> dataPrivList = new ArrayList<TblUserDataPriv>();
		if (StringUtils.isNotBlank(aclCodeStr)) {
			String[] aclCodeArr = aclCodeStr.split(",");
			for (String aclCode : aclCodeArr) {
				TblUserDataPriv privData = new TblUserDataPriv();
				privData.setUserId(userId);
				String[] parts = aclCode.split("_");
				String areaStr = aclCode;
				if (UserType.AGENT == userInfo.getType()
						&& parts.length != 2) {
					throw new BaseRuntimeException("ILLEGAL_PARAM",
							"代理商的数据权限必须要到学校一级");
				}

				if (parts.length == 2) {
					areaStr = parts[0];
					privData.setSchoolId(Integer.valueOf(parts[1]));

					TbSchool tbSchool = tbSchoolServiceImpl.findById(Long
							.valueOf(privData.getSchoolId()));
					privData.setSchoolName(tbSchool.getName());

					if (UserType.AGENT == userInfo.getType()) {
						TblUserDataPriv existQry = new TblUserDataPriv();
						existQry.setUserType(UserType.AGENT);
						existQry.setSchoolId(privData.getSchoolId());

						List<TblUserDataPriv> existList = tblUserDataPrivDaoImpl
								.findByEntity(existQry);
						if (existList != null && !existList.isEmpty()) {
							// 如果该学校已经被其它代理商绑定了，就抛出异常
							for (TblUserDataPriv dataPriv : existList) {
								if (dataPriv.getUserId() != null
										&& dataPriv.getUserId() != userId) {
									throw new BaseRuntimeException(
											"DATA_EXISTS", "学校‘"
													+ privData.getSchoolName()
													+ "’已经有代理商了，请重新选择！", "学校‘"
													+ privData.getSchoolName()
													+ "’已经有代理商了，请重新选择！");
								}
							}
						}
					}
				}

				if (areaStr.length() == 2) {
					privData.setProvinceId(Integer.valueOf(areaStr));
					TbArea area = tbAreaServiceImpl.findById(Long
							.valueOf(privData.getProvinceId()));
					privData.setProvinceName(area.getName());
				} else if (areaStr.length() == 4) {
					privData.setCityId(Integer.valueOf(areaStr));
					privData.setProvinceId(Integer.valueOf(areaStr.substring(0,
							2)));

					TbArea area = tbAreaServiceImpl.findById(Long
							.valueOf(privData.getProvinceId()));
					privData.setProvinceName(area.getName());

					TbArea cityArea = tbAreaServiceImpl.findById(Long
							.valueOf(privData.getCityId()));
					privData.setCityName(cityArea.getName());
				} else if (areaStr.length() == 6) {
					privData.setCountyId(Integer.valueOf(areaStr));
					privData.setCityId(Integer.valueOf(areaStr.substring(0, 4)));
					privData.setProvinceId(Integer.valueOf(areaStr.substring(0,
							2)));

					TbArea area = tbAreaServiceImpl.findById(Long
							.valueOf(privData.getProvinceId()));
					privData.setProvinceName(area.getName());

					TbArea cityArea = tbAreaServiceImpl.findById(Long
							.valueOf(privData.getCityId()));
					privData.setCityName(cityArea.getName());

					TbArea countyArea = tbAreaServiceImpl.findById(Long
							.valueOf(privData.getCountyId()));
					privData.setCountyName(countyArea.getName());
				} else {
					LOG.warn(aclCodeStr + "is an invalid aclCodeStr");
					continue;
				}

				privData.setUserType(userInfo.getType());
				privData.setUserName(userInfo.getShowName());

				dataPrivList.add(privData);
			}

			tblUserDataPrivDaoImpl.insertBatch(dataPrivList);

			if (UserType.TEACHER == userInfo.getType()) {
				Integer schoolId = selectSchoolId(dataPrivList);
				if (schoolId != null) {
					TbtTeacher teacher = new TbtTeacher();
					teacher.setUserId(userInfo.getId());
					teacher.setSchoolId(Long.valueOf(schoolId));
					tbtTeacherServiceImpl.updateByUserId(teacher);
				}
			}
		} else if (UserType.TEACHER == userInfo.getType()) {
			// 数据当前操作是清空数据权限的操作，并且用户为老师类型的用户，则需要将其表中的school_id一并清空
			tbtTeacherServiceImpl.clearSchoolId(userId);
		}

		// tblUserDataPrivDaoImpl.deleteByUserId(userId);
		//
		// if(!dataPrivList.isEmpty()){
		// tblUserDataPrivDaoImpl.insertBatch(dataPrivList);
		// }
		auditLogFacade.saveAuditLog(null, new Object[] { userId, aclCodeStr }, "数据权限");
		return true;
	}

	@Override
	public String getAclCodeStr(long userId) {

		List<TblUserDataPriv> privDataList = loadAllUserDataPriv(userId);
		StringBuilder builder = new StringBuilder();
		if (privDataList != null && !privDataList.isEmpty()) {
			for (TblUserDataPriv dataPriv : privDataList) {
				if (dataPriv.getSchoolId() != null
						&& dataPriv.getSchoolId() > 0) {
					if ((dataPriv.getCountyId() != null && dataPriv
							.getCountyId() > 0)) {
						builder.append(dataPriv.getCountyId() + "_"
								+ dataPriv.getSchoolId());
					} else if (dataPriv.getCityId() != null
							&& dataPriv.getCityId() > 0) { // 对于直辖市下边只有区没有县的情况，就用cityId
						builder.append(dataPriv.getCityId() + "_"
								+ dataPriv.getSchoolId());
					} else {
						LOG.warn("acl data error for id '" + dataPriv.getId()
								+ "' in table Tbl_User_Data_Priv");
						continue;
					}
				} else if (dataPriv.getCountyId() != null
						&& dataPriv.getCountyId() > 0) {
					builder.append(dataPriv.getCountyId());
				} else if (dataPriv.getCityId() != null
						&& dataPriv.getCityId() > 0) {
					builder.append(dataPriv.getCityId());
				} else if (dataPriv.getProvinceId() != null
						&& dataPriv.getProvinceId() > 0) {
					builder.append(dataPriv.getProvinceId());
				} else {
					LOG.warn("acl data error for id '" + dataPriv.getId()
							+ "' in table Tbl_User_Data_Priv");
					continue;
				}

				builder.append(",");
			}
		}

		if (builder.length() > 0) {
			builder.setLength(builder.length() - 1); // 去掉末尾的逗号
		}

		return builder.toString();
	}

	@Override
	public Page<TblUserDataPriv> searchDataPrivList(Integer provinceId,
			Integer cityId, String keyword, int currPage, int maxRows) {

		TblUserDataPriv query = new TblUserDataPriv();
		query.setUserType(UserType.AGENT);
		query.setProvinceId(provinceId);
		query.setCityId(cityId);
		query.setSchoolName(StringUtils.isNotBlank(keyword) ? "%" + keyword
				+ "%" : null);

		return this.findByPage(query, currPage, maxRows,
				" order by province_id, city_id, school_name ");
	}

	@Override
	public List<TblUserDataPriv> loadAllUserDataPriv(long userId) {

		List<TblUserDataPriv> dataPrivList = null; // EhCacheTool.getValue(DefaultCacheNS.DATA_PRIV,
													// "u"+userId);
		String valStr = ShardJedisTool.hget(DefaultJedisKeyNS.dp, "u" + userId);
		if (StringUtils.isNotBlank(valStr)) {
			dataPrivList = new Gson().fromJson(valStr, new TypeToken<List<TblUserDataPriv>>() {
			}.getType());
			return dataPrivList;
		}
		TblUserDataPriv query = new TblUserDataPriv();
		query.setUserId(userId);

		dataPrivList = this.findByEntity(query);
		if (dataPrivList != null && !dataPrivList.isEmpty()) {
			// EhCacheTool.put(DefaultCacheNS.DATA_PRIV, "u"+userId,
			// dataPrivList);
			ShardJedisTool.hset(DefaultJedisKeyNS.dp, "u" + userId,
					new Gson().toJson(dataPrivList, new TypeToken<List<TblUserDataPriv>>() {
					}.getType()));
		}

		return dataPrivList;
	}

	@Override
	public Set<Integer> loadAllProviceId(long userId) {

		List<TblUserDataPriv> dataPrivList = loadAllUserDataPriv(userId);

		Set<Integer> provinceIds = new HashSet<Integer>();
		if (dataPrivList != null && !dataPrivList.isEmpty()) {
			for (TblUserDataPriv dataPriv : dataPrivList) {
				if (dataPriv.getProvinceId() != null) {
					provinceIds.add(dataPriv.getProvinceId());
				}
			}
		}
		return provinceIds;
	}

	@Override
	public Set<Integer> loadAllCityId(long userId, Integer provinceId) {

		List<TblUserDataPriv> dataPrivList = loadAllUserDataPriv(userId);

		Set<Integer> cityIds = new HashSet<Integer>();
		if (dataPrivList != null && !dataPrivList.isEmpty()) {
			for (TblUserDataPriv dataPriv : dataPrivList) {
				if ((provinceId == null || (dataPriv.getProvinceId() != null && dataPriv.getProvinceId().intValue() == provinceId
						.intValue()))
						&& dataPriv.getCityId() != null) {
					cityIds.add(dataPriv.getCityId());
				}
			}
		}
		return cityIds;
	}

	@Override
	public Set<Integer> loadAllCountyId(long userId, Integer cityId) {

		List<TblUserDataPriv> dataPrivList = loadAllUserDataPriv(userId);

		Set<Integer> countyIds = new HashSet<Integer>();
		if (dataPrivList != null && !dataPrivList.isEmpty()) {
			for (TblUserDataPriv dataPriv : dataPrivList) {
				if ((cityId == null || (dataPriv.getCityId() != null && dataPriv.getCityId().intValue() == cityId.intValue()))
						&& dataPriv.getCountyId() != null) {
					countyIds.add(dataPriv.getCountyId());
				}
			}
		}
		return countyIds;
	}

	@Override
	public Set<Long> loadAllSchoolId(long userId, Integer countyIdOrCityId) {

		List<TblUserDataPriv> dataPrivList = loadAllUserDataPriv(userId);
		Set<Long> schoolIds = new HashSet<Long>();
		if (dataPrivList != null && !dataPrivList.isEmpty()) {
			for (TblUserDataPriv dataPriv : dataPrivList) {
				if ((countyIdOrCityId == null || (String.valueOf(countyIdOrCityId).length() == 6 ? (dataPriv.getCountyId() != null && dataPriv
						.getCountyId().intValue() == countyIdOrCityId.intValue())
						: (dataPriv.getCityId() != null && dataPriv.getCityId().intValue() == countyIdOrCityId.intValue())))
						&& dataPriv.getSchoolId() != null) {
					schoolIds.add(Long.valueOf(dataPriv.getSchoolId()));
				}
			}
		}
		return schoolIds;
	}

	@Override
	public List<Long> findAllUserIdByCoach(long userId) {

		return tblUserDataPrivDaoImpl.findAllUserIdByCoach(userId);
	}

	@Override
	public List<Long> findAllUserIdByCoach(Long userId, Long schoolId) {

		if (schoolId == null)
		{
			return tblUserDataPrivDaoImpl.findAllUserIdByCoach(userId);
		}

		return tblUserDataPrivDaoImpl.findAllUserIdByCoach(userId, schoolId);
	}

	@Override
	public List<Long> filterUserByType(long userId, int userType,
			Long... userIds) {

		if (userIds == null || userIds.length <= 0) {
			return new ArrayList<Long>();
		}
		return tblUserDataPrivDaoImpl.filterUserByType(userId, userType, userIds);
	}

	/**
	 * 
	 * @param dataPrivList
	 * @return 从dataPrivList中选择出第一个schoolId不为空的schoolId返回
	 */
	private Integer selectSchoolId(List<TblUserDataPriv> dataPrivList) {

		if (dataPrivList != null && !dataPrivList.isEmpty()) {
			for (TblUserDataPriv dataPriv : dataPrivList) {
				if (dataPriv.getSchoolId() != null) {
					return dataPriv.getSchoolId();
				}
			}
		}

		return null;
	}

	@Override
	public List<TbTeacherLinkCls> findTeachersLinkClasses(Long schoolId, List<Long> teachers) {

		return tblUserDataPrivDaoImpl.findTeachersLinkClasses(schoolId, teachers);
	}

	@Override
	public List<Long> filterTeacherBySchool(Long schoolId, Long... userIds) {

		return tblUserDataPrivDaoImpl.filterUserBySchool(schoolId, userIds);
	}

}