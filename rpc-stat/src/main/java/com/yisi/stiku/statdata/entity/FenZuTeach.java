package com.yisi.stiku.statdata.entity;

import java.util.List;

public class FenZuTeach {

	/**
	 * 班级报告 -相差较大
	 */
	private List<ClassFenbanProblemData> scorePensentHuge;

	/**
	 * 班级报告- 相差一般
	 */
	private List<ClassFenbanProblemData> scorePensentNormal;

	/**
	 * 班级报告- 相差很小
	 */
	private List<ClassFenbanProblemData> scorePensentLow;

	/**
	 * 用于优秀段 班级报告- 优秀教学报告低于满分
	 */
	private List<ClassFenbanProblemData> scorePensentLowToFull;

	/**
	 * 用于优秀段 班级报告- 优秀教学报告满分
	 */
	private List<ClassFenbanProblemData> scorePensentFull;

	/**
	 * @return the scorePensentLowToFull
	 */
	public List<ClassFenbanProblemData> getScorePensentLowToFull() {

		return scorePensentLowToFull;
	}

	/**
	 * @param scorePensentLowToFull
	 *            the scorePensentLowToFull to set
	 */
	public void setScorePensentLowToFull(List<ClassFenbanProblemData> scorePensentLowToFull) {

		this.scorePensentLowToFull = scorePensentLowToFull;
	}

	/**
	 * @return the scorePensentFull
	 */
	public List<ClassFenbanProblemData> getScorePensentFull() {

		return scorePensentFull;
	}

	/**
	 * @param scorePensentFull
	 *            the scorePensentFull to set
	 */
	public void setScorePensentFull(List<ClassFenbanProblemData> scorePensentFull) {

		this.scorePensentFull = scorePensentFull;
	}

	public List<ClassFenbanProblemData> getScorePensentHuge() {

		return scorePensentHuge;
	}

	public void setScorePensentHuge(List<ClassFenbanProblemData> scorePensentHuge) {

		this.scorePensentHuge = scorePensentHuge;
	}

	public List<ClassFenbanProblemData> getScorePensentNormal() {

		return scorePensentNormal;
	}

	public void setScorePensentNormal(List<ClassFenbanProblemData> scorePensentNormal) {

		this.scorePensentNormal = scorePensentNormal;
	}

	public List<ClassFenbanProblemData> getScorePensentLow() {

		return scorePensentLow;
	}

	public void setScorePensentLow(List<ClassFenbanProblemData> scorePensentLow) {

		this.scorePensentLow = scorePensentLow;
	}

}
