package com.yisi.stiku.statdata.entity;

import java.util.List;

public class Report {

	private ClassFenbanData classFenbanData;
	private List<ClassFenbanProblemData> classFenbanProblemDatas;
	private GradeFenbanData gradeFenbanData;
	private List<GradeFenbanProblemData> gradeFenbanProblemDatas;
	private List<ClassFenbanData> gradeScoreCompare;

	private List<ClassFenbanData> chapterReport;

	/**
	 * 年纪报告 -年纪优秀分班建议报告
	 */
	private List<GradeFenbanProblemData> gradeExcellentFenBan;

	public List<ClassFenbanData> getChapterReport() {

		return chapterReport;
	}

	public void setChapterReport(List<ClassFenbanData> chapterReports) {

		this.chapterReport = chapterReports;
	}

	/**
	 * 年纪报告- 年纪拔高分班建议报告
	 */
	private List<GradeFenbanProblemData> gradePassFenBan;

	/**
	 * 年纪报告- 年纪基础分班建议报告
	 */
	private List<GradeFenbanProblemData> gradeUnPassFenBan;

	/**
	 * 班级整体分组教学报告
	 */
	private FenZuTeach wholeclassFenZuTeaching;

	/**
	 * 班级 优秀分组教学报告
	 */
	private FenZuTeach excellClassFenZuTeaching;

	/**
	 * 班级 及格分组教学报告
	 */
	private FenZuTeach passClassFenZuTeaching;

	/**
	 * 班级 不及格分组教学报告
	 */
	private FenZuTeach unPassClassFenZuTeaching;

	public FenZuTeach getWholeclassFenZuTeaching() {

		return wholeclassFenZuTeaching;
	}

	public void setWholeclassFenZuTeaching(FenZuTeach wholeclassFenZuTeaching) {

		this.wholeclassFenZuTeaching = wholeclassFenZuTeaching;
	}

	public FenZuTeach getExcellClassFenZuTeaching() {

		return excellClassFenZuTeaching;
	}

	public void setExcellClassFenZuTeaching(FenZuTeach excellClassFenZuTeaching) {

		this.excellClassFenZuTeaching = excellClassFenZuTeaching;
	}

	public FenZuTeach getPassClassFenZuTeaching() {

		return passClassFenZuTeaching;
	}

	public void setPassClassFenZuTeaching(FenZuTeach passClassFenZuTeaching) {

		this.passClassFenZuTeaching = passClassFenZuTeaching;
	}

	public FenZuTeach getUnPassClassFenZuTeaching() {

		return unPassClassFenZuTeaching;
	}

	public void setUnPassClassFenZuTeaching(FenZuTeach unPassClassFenZuTeaching) {

		this.unPassClassFenZuTeaching = unPassClassFenZuTeaching;
	}

	/**
	 * 获取年纪分组排名
	 * 
	 * @return
	 */
	public List<ClassFenbanData> getGradeScoreCompare() {

		return gradeScoreCompare;
	}

	public List<GradeFenbanProblemData> getGradeExcellentFenBan() {

		return gradeExcellentFenBan;
	}

	public void setGradeExcellentFenBan(List<GradeFenbanProblemData> gradeExcellentFenBan) {

		this.gradeExcellentFenBan = gradeExcellentFenBan;
	}

	public List<GradeFenbanProblemData> getGradePassFenBan() {

		return gradePassFenBan;
	}

	public void setGradePassFenBan(List<GradeFenbanProblemData> gradePassFenBan) {

		this.gradePassFenBan = gradePassFenBan;
	}

	public List<GradeFenbanProblemData> getGradeUnPassFenBan() {

		return gradeUnPassFenBan;
	}

	public void setGradeUnPassFenBan(List<GradeFenbanProblemData> gradeUnPassFenBan) {

		this.gradeUnPassFenBan = gradeUnPassFenBan;
	}

	public void setGradeScoreCompare(List<ClassFenbanData> gradeScoreCompare) {

		this.gradeScoreCompare = gradeScoreCompare;
	}

	/**
	 * 获取班级数据报告
	 * 
	 * @return
	 */
	public ClassFenbanData getClassFenbanData() {

		return classFenbanData;
	}

	public void setClassFenbanData(ClassFenbanData classFenbanData) {

		this.classFenbanData = classFenbanData;
	}

	/**
	 * 获取年纪数据报告
	 * 
	 * @return
	 */
	public GradeFenbanData getGradeFenbanData() {

		return gradeFenbanData;
	}

	public void setGradeFenbanData(GradeFenbanData gradeFenbanData) {

		this.gradeFenbanData = gradeFenbanData;
	}

	/**
	 * 获取班级试题报告
	 * 
	 * @return
	 */
	public List<ClassFenbanProblemData> getClassFenbanProblemDatas() {

		return classFenbanProblemDatas;
	}

	public void setClassFenbanProblemDatas(List<ClassFenbanProblemData> classFenbanProblemDatas) {

		this.classFenbanProblemDatas = classFenbanProblemDatas;
	}

	/**
	 * 获取年级试题报告
	 * 
	 * @return
	 */
	public List<GradeFenbanProblemData> getGradeFenbanProblemDatas() {

		return gradeFenbanProblemDatas;
	}

	public void setGradeFenbanProblemDatas(List<GradeFenbanProblemData> gradeFenbanProblemDatas) {

		this.gradeFenbanProblemDatas = gradeFenbanProblemDatas;
	}

}
