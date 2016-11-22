package com.yisi.stiku.basedata.rpc.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageImpl;

import com.yisi.stiku.basedata.entity.TbStudent;

public interface StudentRpcService {

	/**
	 * 
	 * @param schoolId
	 * @param classId
	 * @param keyword
	 * @return 根据学校或者班级来查询出对应的学生信息
	 */
	PageImpl<TbStudent> searchStudent(long schoolId, long classId, String keyword, int currPage, int maxRows);

	/**
	 * 
	 * @param queryCond
	 * @param keyword
	 * @param currPage
	 * @param maxRows
	 * @return
	 */
	PageImpl<TbStudent> searchStudent(TbStudent queryCond, String keyword, int currPage, int maxRows);

	/**
	 * 
	 * @param kaohao
	 * @return
	 */
	TbStudent findStudentByKaohao(String kaohao);

	/**
	 * 根据学校Id加载学生列表
	 * 
	 * @param schoolId
	 * @return
	 */
	PageImpl<TbStudent> findStudentBySchoolId(Long schoolId, int currPage, int maxRows);

	/**
	 * 
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	List<TbStudent> findStudentBySchoolIdAndClassId(Long schoolId, Long classId);

	/**
	 * 
	 * @param schoolId
	 * @param gradeYear
	 * @return
	 */
	List<TbStudent> findStudentList(Long schoolId, int gradeYear);

	/**
	 * 
	 * @param studentId
	 * @return
	 */
	TbStudent findByStudentId(Long studentId);

	List<TbStudent> findListByStudentIds(Long... studentIds);

	Map<Long/* studentId */, TbStudent> findMapByStudentIds(Long... studentIds);

	public boolean save(TbStudent student);

}
