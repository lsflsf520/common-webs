package com.yisi.stiku.statdata.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.common.utils.ArrayUtils;
import com.yisi.stiku.rpc.annotation.RpcService;
import com.yisi.stiku.statdata.dao.impl.ClassFenbanProblemDataDaoImpl;
import com.yisi.stiku.statdata.entity.AsSchoolGradeVO;
import com.yisi.stiku.statdata.entity.ClassFenbanData;
import com.yisi.stiku.statdata.entity.ClassFenbanProblemData;
import com.yisi.stiku.statdata.entity.GradeFenbanData;
import com.yisi.stiku.statdata.entity.GradeFenbanProblemData;
import com.yisi.stiku.statdata.entity.Report;
import com.yisi.stiku.statdata.entity.StudentBookPointSortVO;
import com.yisi.stiku.statdata.entity.StudentFenbanData;
import com.yisi.stiku.statdata.rpc.ReportRpcService;

@Service
@RpcService(ReportRpcService.class)
public class ReportServiceImpl implements ReportRpcService {

	@Resource
	private ClassFenbanDataServiceImpl classFenbanDataServiceImpl;

	@Resource
	private ClassFenbanProblemDataServiceImpl classFenbanProblemDataServiceImpl;

	@Resource
	private GradeFenbanDataServiceImpl gradeFenbanDataServiceImpl;

	@Resource
	private GradeFenbanProblemDataServiceImpl gradeFenbanProblemDataServiceImpl;

	@Resource
	private StudentFenbanDataServiceImpl studentFenbanDataServiceImpl;

	@Resource
	private ClassFenbanProblemDataDaoImpl classFenbanProblemDataDaoImpl;

	@Override
	public Report queryClsReport(Long weekId, Long clsId) {

		Report report = new Report();
		ClassFenbanData cls = new ClassFenbanData();
		cls.setWeektestId(weekId);
		cls.setClassId(clsId);

		ClassFenbanData clsFenBan = classFenbanDataServiceImpl.findOneByEntity(cls);

		report.setClassFenbanData(clsFenBan);

		ClassFenbanProblemData cfpd = new ClassFenbanProblemData();
		cfpd.setClassId(clsId);
		cfpd.setWeektestId(weekId);

		List<ClassFenbanProblemData> classFenbanProblemData = classFenbanProblemDataServiceImpl.findByEntity(cfpd);

		report.setClassFenbanProblemDatas(classFenbanProblemData);
		return report;
	}

	@Override
	public Report queryGradeReport(Long paperId, Long weekId) {

		Report report = new Report();
		GradeFenbanData gfd = new GradeFenbanData();
		gfd.setPaperId(paperId);
		gfd.setWeektestId(weekId);

		GradeFenbanData gfdResult = gradeFenbanDataServiceImpl.findOneByEntity(gfd);

		report.setGradeFenbanData(gfdResult);

		GradeFenbanProblemData gfpd = new GradeFenbanProblemData();
		gfpd.setWeektestId(weekId);

		List<GradeFenbanProblemData> gfpdresult = gradeFenbanProblemDataServiceImpl.findByEntity(gfpd);

		report.setGradeFenbanProblemDatas(gfpdresult);
		return report;
	}

	@Override
	public List<StudentFenbanData> queryStudentReport(Long studentId, Long weekId, Long clsId) {

		StudentFenbanData query = new StudentFenbanData();
		query.setStudentId(studentId);
		query.setWeektestId(weekId);
		query.setClassId(clsId);

		List<StudentFenbanData> data = studentFenbanDataServiceImpl.findByEntity(query);
		return data;
	}

	@Override
	public List<AsSchoolGradeVO> queryASSchoolGradeReport(Integer gradeYear, Long schoolId) {

		GradeFenbanData fenban = new GradeFenbanData();
		fenban.setGradeYear(gradeYear);
		fenban.setSchoolId(schoolId);
		List<GradeFenbanData> gradeFenbanDatas = gradeFenbanDataServiceImpl.findByEntity(fenban);
		List<AsSchoolGradeVO> results = new ArrayList<AsSchoolGradeVO>(gradeFenbanDatas.size());

		if (ArrayUtils.isEmpty(gradeFenbanDatas)) {
			return null;
		}

		for (GradeFenbanData gradeFenbanData : gradeFenbanDatas) {
			AsSchoolGradeVO temp = new AsSchoolGradeVO();
			temp.setCreateTime(gradeFenbanData.getCreateTime());
			temp.setGradeYear(gradeFenbanData.getGradeYear());
			temp.setsType(gradeFenbanData.getsType());
			temp.setWeektestId(gradeFenbanData.getWeektestId());
			temp.setWeektestName(gradeFenbanData.getWeektestName());
			results.add(temp);
		}

		return results;
	}

	@Override
	public AsSchoolGradeVO queryASSchoolGradeReport(Long weektestId) {

		GradeFenbanData fenban = new GradeFenbanData();
		fenban.setWeektestId(weektestId);
		GradeFenbanData result = gradeFenbanDataServiceImpl.findOneByEntity(fenban);

		AsSchoolGradeVO temp = new AsSchoolGradeVO();
		temp.setCreateTime(result.getCreateTime());
		temp.setGradeYear(result.getGradeYear());
		temp.setsType(result.getsType());
		temp.setWeektestId(result.getWeektestId());
		temp.setWeektestName(result.getWeektestName());

		return temp;
	}

	@Override
	public Integer queryASSchoolGradeCnt(Integer gradeYear, Long schoolId) {

		ClassFenbanData fenban = new ClassFenbanData();
		fenban.setGradeYear(gradeYear);
		fenban.setSchoolId(schoolId);
		List<ClassFenbanData> classFenbanDatas = classFenbanDataServiceImpl.findByEntity(fenban);
		return classFenbanDatas.size();
	}

	@Override
	public Integer queryASSchoolGradeCnt(Integer gradeYear, Integer sType, Long schoolId) {

		ClassFenbanData fenban = new ClassFenbanData();
		fenban.setGradeYear(gradeYear);
		fenban.setSchoolId(schoolId);
		fenban.setsType(sType);
		List<ClassFenbanData> classFenbanDatas = classFenbanDataServiceImpl.findByEntity(fenban);
		return classFenbanDatas.size();
	}

	@Override
	public Report queryGradeScoreCompare(Long schoolId, Integer gradeYear) {

		ClassFenbanData fenban = new ClassFenbanData();
		fenban.setSchoolId(schoolId);
		fenban.setGradeYear(gradeYear);
		Report report = new Report();
		report.setGradeScoreCompare(classFenbanDataServiceImpl.findByEntity(fenban));

		return report;
	}

	@Override
	public Report queryGradeFenBanPropose(Long weekId, Integer grade_year, Long schoolId) {

		Report report = new Report();
		GradeFenbanProblemData gfd = new GradeFenbanProblemData();
		gfd.setWeektestId(weekId);
		gfd.setGradeYear(grade_year);
		gfd.setSchoolId(schoolId);

		report.setGradeExcellentFenBan(
				gradeFenbanProblemDataServiceImpl.findByEntity(gfd, "order by excellent_diff_score_percent asc "));
		report.setGradePassFenBan(
				gradeFenbanProblemDataServiceImpl.findByEntity(gfd, "order by pass_diff_score_percent  asc "));
		report.setGradeUnPassFenBan(
				gradeFenbanProblemDataServiceImpl.findByEntity(gfd, "order by unpass_diff_score_percent  asc "));

		return report;

	}

	@Override
	public Report queryClassFenZuTeaching(Long weekId, Long class_id, Long school_id) {

		ClassFenbanProblemData cfpd = new ClassFenbanProblemData();
		cfpd.setClassId(class_id);
		cfpd.setWeektestId(weekId);
		cfpd.setSchoolId(school_id);

		Report queryClassFenZuTeaching = classFenbanProblemDataDaoImpl.queryClassFenZuTeaching(cfpd);

		// findGradeScorePersent(queryClassFenZuTeaching,
		// gradeFenbanProblemDatas);

		return queryClassFenZuTeaching;
	}

	@Override
	public Report queryGradeScoreCompare(Long weekId, Long schoolId, Integer gradeYear) {

		ClassFenbanData fenban = new ClassFenbanData();
		fenban.setWeektestId(weekId);
		fenban.setSchoolId(schoolId);
		fenban.setGradeYear(gradeYear);
		Report report = new Report();
		report.setGradeScoreCompare(classFenbanDataServiceImpl.findByEntity(fenban));

		return report;
	}

	@Override
	public Report queryChapterReport(Long bookPointSortId, Long classId) {

		ClassFenbanData fenban = new ClassFenbanData();
		fenban.setBookPointSortId(bookPointSortId);
		fenban.setClassId(classId);
		Report report = new Report();
		List<ClassFenbanData> results = classFenbanDataServiceImpl.findByEntity(fenban,
				" order by exampaper_index asc");
		if (!ArrayUtils.isEmpty(results)) {
			report.setChapterReport(results);
		}

		return report;
	}

	@Override
	public List<StudentBookPointSortVO> queryChapterReportForStudent(Long bookPointSortId, Long studentId) {

		StudentFenbanData student = new StudentFenbanData();
		student.setBookPointSortId(bookPointSortId);
		student.setStudentId(studentId);

		List<ClassFenbanData> resultsCls = null;
		List<StudentFenbanData> resultsStudent = studentFenbanDataServiceImpl.findByEntity(student,
				"order by exampaper_index asc");

		List<StudentBookPointSortVO> results = null;
		if (!ArrayUtils.isEmpty(resultsStudent)) {
			ClassFenbanData cls = new ClassFenbanData();
			cls.setClassId(resultsStudent.get(0).getClassId());
			cls.setBookPointSortId(bookPointSortId);
			resultsCls = classFenbanDataServiceImpl.findByEntity(cls, "order by exampaper_index asc");

			if (!ArrayUtils.isEmpty(resultsCls)) {
				results = new ArrayList<StudentBookPointSortVO>(resultsCls.size());
			}

		}
		Map<Long, StudentBookPointSortVO> temps = new HashMap<Long, StudentBookPointSortVO>();

		for (ClassFenbanData classFenbanData : resultsCls) {
			StudentBookPointSortVO temp = new StudentBookPointSortVO();
			temp.setClassFenbanData(classFenbanData);
			temps.put(classFenbanData.getWeektestId(), temp);
			for (StudentFenbanData studentFenbanData : resultsStudent) {
				StudentBookPointSortVO vo = null;
				if (temps.containsKey(classFenbanData.getWeektestId())) {
					vo = temps.get(classFenbanData.getWeektestId());
					if (classFenbanData.getWeektestId().equals(studentFenbanData.getWeektestId())) {
						vo.setStudentFenbanData(studentFenbanData);
					} else if (vo.getStudentFenbanData() == null) {
						vo.setStudentFenbanData(new StudentFenbanData());
					}

				}
			}
		}
		for (Iterator<Long> iterator = temps.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			StudentBookPointSortVO studentBookPointSortVO = temps.get(key);
			results.add(studentBookPointSortVO);

		}
		Collections.sort(results, new Comparator<StudentBookPointSortVO>() {

			@Override
			public int compare(StudentBookPointSortVO o1, StudentBookPointSortVO o2) {

				return o1.getClassFenbanData().getExampaperIndex() - o2.getClassFenbanData().getExampaperIndex();
			}
		});

		return results;
	}
}
