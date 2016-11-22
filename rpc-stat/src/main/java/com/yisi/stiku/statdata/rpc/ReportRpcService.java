package com.yisi.stiku.statdata.rpc;

import java.util.List;

import com.yisi.stiku.statdata.entity.AsSchoolGradeVO;
import com.yisi.stiku.statdata.entity.Report;
import com.yisi.stiku.statdata.entity.StudentBookPointSortVO;
import com.yisi.stiku.statdata.entity.StudentFenbanData;

public interface ReportRpcService {

	/**
	 * 查询学生报告
	 * 
	 * @param studentId
	 *            学生id
	 * @param weekId
	 *            测验id
	 * @param clsId
	 *            班级id
	 * @return
	 */
	List<StudentFenbanData> queryStudentReport(Long studentId, Long weekId, Long clsId);

	/**
	 * 查询章测报告
	 * 
	 * @param bookPointSortId
	 *            章id
	 * @param clsId
	 *            班级id
	 * @return
	 */
	Report queryChapterReport(Long bookPointSortId, Long classId);

	/**
	 * 查询章测报告
	 * 
	 * @param bookPointSortId
	 *            章id
	 * @param studentId
	 *            学生ID
	 * @return
	 */
	List<StudentBookPointSortVO> queryChapterReportForStudent(Long bookPointSortId, Long studentId);

	/**
	 * 查询班级报告
	 * 
	 * @param weekId
	 *            测验id
	 * @param clsId
	 *            班级id
	 * @return
	 */
	Report queryClsReport(Long weekId, Long clsId);

	/**
	 * 查询年纪各分数段对比(使用getGradeScoreCompare()获取数据)
	 * 
	 * @param schoolId
	 *            学校ID
	 * @param gradeYear
	 *            入学年份
	 * @return
	 */
	Report queryGradeScoreCompare(Long schoolId, Integer gradeYear);

	/**
	 * 查询年纪各分数段对比(使用getGradeScoreCompare()获取数据)
	 * 
	 * @param weekId
	 *            测试编号
	 * @param schoolId
	 *            学校ID
	 * @param gradeYear
	 *            入学年份
	 * @return
	 */
	Report queryGradeScoreCompare(Long weekId, Long schoolId, Integer gradeYear);

	/**
	 * 查询年纪报告
	 * 
	 * @param paperId
	 *            试卷编号
	 * @param weekId
	 *            测验编号
	 * @return
	 */
	Report queryGradeReport(Long paperId, Long weekId);

	/**
	 * 获取学校入学年份文理科报告详情
	 * 
	 * @param gradeYear
	 *            入学年份
	 * @param school_id
	 *            学校编号
	 * @return
	 */
	List<AsSchoolGradeVO> queryASSchoolGradeReport(Integer gradeYear, Long school_id);

	/**
	 * 获取学校入学年份报告总数
	 * 
	 * @param gradeYear
	 * @param schoolId
	 * @return
	 */
	Integer queryASSchoolGradeCnt(Integer gradeYear, Long schoolId);

	/**
	 * 获取学校入学年份文理科报告总数
	 * 
	 * @param gradeYear
	 * @param sType
	 * @param schoolId
	 * @return
	 */

	Integer queryASSchoolGradeCnt(Integer gradeYear, Integer sType, Long schoolId);

	/**
	 * 年纪分班建议--年纪组长报告: 查询某个学校某个入学年份某个测验的所有题目
	 * 
	 * @param weekId
	 * @param grade_year
	 * @param schoolId
	 * @return
	 */
	Report queryGradeFenBanPropose(Long weekId, Integer grade_year, Long schoolId);

	/**
	 * 班级分组教学--老师报告 查询某个学校某个班级某个测验的所有题目，计算差值
	 * 
	 * @param weekId
	 * @param grade_year
	 * @param schoolId
	 * @return
	 */
	Report queryClassFenZuTeaching(Long weekId, Long class_id, Long school_id);

	/**
	 * 获取学校入学年份文理科报告详情
	 * 
	 * @param weektestId
	 * 
	 * @return
	 */
	AsSchoolGradeVO queryASSchoolGradeReport(Long weektestId);

}
