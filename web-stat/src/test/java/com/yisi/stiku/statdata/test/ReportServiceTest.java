package com.yisi.stiku.statdata.test;

import java.util.List;

import javax.sql.DataSource;

import org.dbunit.database.IDatabaseConnection;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;

import com.yisi.stiku.statdata.entity.AsSchoolGradeVO;
import com.yisi.stiku.statdata.entity.ClassFenbanData;
import com.yisi.stiku.statdata.entity.ClassFenbanProblemData;
import com.yisi.stiku.statdata.entity.FenZuTeach;
import com.yisi.stiku.statdata.entity.GradeFenbanProblemData;
import com.yisi.stiku.statdata.entity.Report;
import com.yisi.stiku.statdata.entity.StudentBookPointSortVO;
import com.yisi.stiku.statdata.entity.StudentFenbanData;
import com.yisi.stiku.statdata.service.ReportServiceImpl;

public class ReportServiceTest extends UnitilsJUnit4 {

	@TestDataSource("database1")
	private DataSource dataSource;
	private static ReportServiceImpl reportServiceImpl;

	private static ApplicationContext applicationContext;

	private static IDatabaseConnection conn;

	static {
		applicationContext = new ClassPathXmlApplicationContext("classpath:context/*.xml");
		reportServiceImpl = applicationContext.getBean(ReportServiceImpl.class);
	}

	@Test
	@DataSet("ReportServiceTest.queryClsReport.xml")
	public void queryClsReport() {

		Report re = reportServiceImpl.queryClsReport(1L, 2095L);
		ClassFenbanData classFenbanData = re.getClassFenbanData();
		List<ClassFenbanProblemData> classFenbanProblemDatas = re.getClassFenbanProblemDatas();
		System.out.println(classFenbanData.getClassName());
		// ReflectionAssert.assertPropertyReflectionEquals("className",
		// "201301", classFenbanData);
		Assert.assertEquals(classFenbanData.getClassName(), "201301");
		Assert.assertEquals(classFenbanProblemDatas.size(), 6);

	}

	@Test
	@DataSet("ReportServiceTest.queryGradeReport.xml")
	public void queryGradeReport() {

		Report re = reportServiceImpl.queryGradeReport(null, 1L);
		System.out.println(re.getGradeFenbanData());
		System.out.println(re.getGradeFenbanProblemDatas().size());

	}

	@Test
	@DataSet("ReportServiceTest.queryGradeFenBanPropose.xml")
	public void queryGradeFenBanPropose() {

		Report queryGradeFenBanPropose = reportServiceImpl.queryGradeFenBanPropose(1L, 2013, 595L);
		List<GradeFenbanProblemData> gradeExcellentFenBan = queryGradeFenBanPropose.getGradeExcellentFenBan();
		List<GradeFenbanProblemData> gradePassFenBan = queryGradeFenBanPropose.getGradePassFenBan();

		List<GradeFenbanProblemData> gradeUnPassFenBan = queryGradeFenBanPropose.getGradeUnPassFenBan();

		for (GradeFenbanProblemData gradeFenbanProblemData : gradeExcellentFenBan) {
			System.out.println(gradeFenbanProblemData.getProblemId());
		}

		for (GradeFenbanProblemData gradeFenbanProblemData : gradePassFenBan) {
			System.out.println(gradeFenbanProblemData.getProblemId());
		}

		for (GradeFenbanProblemData gradeFenbanProblemData : gradeUnPassFenBan) {
			System.out.println(gradeFenbanProblemData.getProblemId());
		}

	}

	@Test
	@DataSet("ReportServiceTest.queryStudentReport.xml")
	public void queryStudentReport() {

		List<StudentFenbanData> queryStudentReport = reportServiceImpl.queryStudentReport(52349L, 1L, 2095L);
		// ReflectionAssert.assertPropertyReflectionEquals("size", "2",
		// queryStudentReport);
		Assert.assertEquals(queryStudentReport.size(), 2);
	}

	@Test
	@DataSet("ReportServiceTest.queryASSchoolGradeCnt.xml")
	public void queryASSchoolGradeCnt() {

		Integer cntNoSchool = reportServiceImpl.queryASSchoolGradeCnt(2013, null);

		Integer queryASSchoolGradeCnt = reportServiceImpl.queryASSchoolGradeCnt(2013, 595L);

		Integer queryASSchoolGradeCnt2 = reportServiceImpl.queryASSchoolGradeCnt(2013, 2, 595L);

		Integer queryASSchoolGradeCnt3 = reportServiceImpl.queryASSchoolGradeCnt(2013, 1, null);
		System.out.println(cntNoSchool);
		System.out.println(queryASSchoolGradeCnt);
		System.out.println(queryASSchoolGradeCnt2);
		System.out.println(queryASSchoolGradeCnt3);

		Assert.assertSame(cntNoSchool, 10);
		Assert.assertSame(queryASSchoolGradeCnt, 9);
		Assert.assertSame(queryASSchoolGradeCnt2, 3);

		Assert.assertSame(queryASSchoolGradeCnt3, 6);

	}

	@Test
	@DataSet("ReportServiceTest.queryASSchoolGradeCnt.xml")
	public void queryASSchoolGradeReport() {

		List<AsSchoolGradeVO> queryASSchoolGradeReport = reportServiceImpl.queryASSchoolGradeReport(2013, 595L);

		System.out.println(queryASSchoolGradeReport.size());

	}

	@Test
	// @DataSet("ReportServiceTest.queryGradeReport.xml")
	public void queryClassFenZuTeaching() {

		Report queryClassFenZuTeaching = reportServiceImpl.queryClassFenZuTeaching(298L, 3380L, 640L);
		// printClassFenZuTeaching(queryClassFenZuTeaching.getWholeclassFenZuTeaching());
		// printClassFenZuTeaching(queryClassFenZuTeaching.getExcellClassFenZuTeaching());
		// printClassFenZuTeaching(queryClassFenZuTeaching.getPassClassFenZuTeaching());
		printClassFenZuTeaching(queryClassFenZuTeaching.getUnPassClassFenZuTeaching());
	}

	@Test
	// @DataSet("queryChapterReportForStudent.xml")
	public void queryChapterReportForStudent() {

		List<StudentBookPointSortVO> queryChapterReportForStudent = reportServiceImpl
				.queryChapterReportForStudent(25797L, 37942L);
		for (StudentBookPointSortVO studentBookPointSortVO : queryChapterReportForStudent) {
			System.out.println("Class" + studentBookPointSortVO.getClassFenbanData().getWeektestId());
			System.out.println("Student" + studentBookPointSortVO.getStudentFenbanData().getWeektestId());
			System.out.println("Student" + studentBookPointSortVO.getClassFenbanData().getExampaperIndex());
		}

	}

	@Test
	public void queryChapterReport() {

		Report queryChapterReport = reportServiceImpl.queryChapterReport(25794L, 2715L);
		System.out.println(queryChapterReport.getChapterReport());

	}

	private void printClassFenZuTeaching(FenZuTeach fenzuTeach) {

		List<ClassFenbanProblemData> scorePensentHuge = fenzuTeach.getScorePensentHuge();
		List<ClassFenbanProblemData> scorePensentNormal = fenzuTeach.getScorePensentNormal();
		List<ClassFenbanProblemData> scorePensentLow = fenzuTeach.getScorePensentLow();
		for (ClassFenbanProblemData classFenbanProblemData : scorePensentHuge) {
			System.out.println(classFenbanProblemData.getUnpassDiffScorePercent());
			// System.out.println(classFenbanProblemData.getGradeAvgScorePercent());
		}

		for (ClassFenbanProblemData classFenbanProblemData : scorePensentNormal) {
			System.out.println(classFenbanProblemData.getUnpassDiffScorePercent());
			// System.out.println(classFenbanProblemData.getGradeAvgScorePercent());
		}
		for (ClassFenbanProblemData classFenbanProblemData : scorePensentLow) {
			System.out.println(classFenbanProblemData.getUnpassDiffScorePercent());
			// System.out.println(classFenbanProblemData.getGradeAvgScorePercent());
		}
	}
}
