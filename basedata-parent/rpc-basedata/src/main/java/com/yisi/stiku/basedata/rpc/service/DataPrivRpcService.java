package com.yisi.stiku.basedata.rpc.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.yisi.stiku.basedata.entity.TbTeacherLinkCls;
import com.yisi.stiku.basedata.entity.TblUserDataPriv;

/**
 * 
 * @author shangfeng
 *
 */
public interface DataPrivRpcService {

	/**
	 * 
	 * @param userId
	 * @param aclCode
	 * @return
	 */
	boolean saveAclCode(long userId, String aclCodeStr);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	String getAclCodeStr(long userId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<TblUserDataPriv> loadAllUserDataPriv(long userId);

	/**
	 * 
	 * @param userId
	 * @return 将用户userId上拥有的省份id返回
	 */
	Set<Integer> loadAllProviceId(long userId);

	/**
	 * 
	 * @param userId
	 * @param provinceId
	 *            省份id，如果为空，则说明查询该用户下所有市级权限
	 * @return 如果provinceId为null，则只要数据权限上的cityId字段不为空，则将其加入返回集合中；
	 *         如果provinceId不为null
	 *         ，则需要数据权限上的provinceId与该参数相等，并且数据权限上的cityId字段不为空，才将其加入返回集合中；
	 *         否则，忽略掉数据权限上的 cityId 字段
	 */
	Set<Integer> loadAllCityId(long userId, Integer provinceId);

	/**
	 * 
	 * @param userId
	 * @param cityId
	 *            城市id，如果为空，则说明查询该用户下所有县级权限
	 * @return 如果cityId为null，则只要数据权限上的countyId字段不为空，则将其加入返回集合中；
	 *         如果cityId不为null，则需要数据权限上的cityId与该参数相等
	 *         ，并且数据权限上的countyId字段不为空，才将其加入返回集合中； 否则，忽略掉数据权限上的 countyId 字段
	 */
	Set<Integer> loadAllCountyId(long userId, Integer cityId);

	/**
	 * 
	 * @param userId
	 * @param countyIdOrCityId
	 * @return 如果 countyIdOrCityId 为 countyId，则返回旗下userId有访问权限的学校id； 如果
	 *         countyIdOrCityId 为 cityId，则返回旗下userId有访问权限的学校id； 如果
	 *         countyIdOrCityId 为null，则只要用户数据权限上的
	 *         schoolId不为空，则需要将该schoolId加入到返回集合中
	 * 
	 */
	Set<Long> loadAllSchoolId(long userId, Integer countyIdOrCityId);

	/**
	 * 
	 * @param proviceId
	 * @param cityId
	 * @param keyword
	 *            目前只支持按名称查询
	 * @return
	 */
	Page<TblUserDataPriv> searchDataPrivList(Integer provinceId, Integer cityId, String keyword, int currPage, int maxRows);

	/**
	 * 
	 * @param userId
	 * @return 根据学习教练所在的学校，查出旗下所有的老师用户ID
	 */
	List<Long> findAllUserIdByCoach(long userId);

	/**
	 * 
	 * @param userId
	 *            当前登录用户的Id
	 * @param userType
	 *            用户类型
	 * @param userIds
	 *            需要进行过滤的用户id
	 * @return 过滤掉userIds中学校不是 userId 所在学校，并且用户类型不是 userType 类型的用户id，然后返回
	 */
	List<Long> filterUserByType(long userId, int userType, Long... userIds);

	/**
	 * @param schoolId
	 *            过滤的学校
	 * @param userIds
	 *            需要进行过滤的用户id
	 * @return 过滤掉userIds中学校不是schoolId的老师用户id，然后返回
	 */
	List<Long> filterTeacherBySchool(Long schoolId, Long... userIds);

	/**
	 * 
	 * @param schoolId
	 *            学校编号
	 * @param userIds
	 *            用户列表
	 * 
	 * @return 返回老师列表管理的班级
	 */
	List<TbTeacherLinkCls> findTeachersLinkClasses(Long schoolId, List<Long> userIds);

	/**
	 * 
	 * @param userId
	 * @return 根据学校ID，查询该教练下所有老师用户ID
	 */
	List<Long> findAllUserIdByCoach(Long userId, Long schoolId);

}
