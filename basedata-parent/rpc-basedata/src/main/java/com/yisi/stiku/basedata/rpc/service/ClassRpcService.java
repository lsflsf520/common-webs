package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import com.yisi.stiku.basedata.entity.TbClass;

public interface ClassRpcService {

	TbClass findById(Long id);

	List<TbClass> findClassList(Long... classIds);

	long save(TbClass clazz);

	/**
	 * 
	 * @param schoolId
	 * @return
	 */
	List<TbClass> loadByShoolId(long schoolId);

	/**
	 * 读取可用班级列表：筛选已经毕业的,已被关联的
	 * 
	 * @param schoolId
	 * @return
	 */
	List<TbClass> loadByAvailableShoolId(Long schoolId);

	/**
	 * 
	 * @param schoolId
	 * @param gradeYear
	 * @return
	 */
	List<TbClass> getBySchoolIdAndGradeYear(long schoolId, int gradeYear);

	TbClass updateClassName(long classId, String name, long schoolId, int gradeYear, int type);

}
