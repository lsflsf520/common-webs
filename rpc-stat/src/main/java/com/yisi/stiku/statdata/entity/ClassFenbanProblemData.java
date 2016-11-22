package com.yisi.stiku.statdata.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;

public class ClassFenbanProblemData extends BaseEntity<Integer> {

	private Integer id;
	/*
	 * 年纪平均正确率 private Integer gradeAvgScorePercent;
	 * 
	 * public Integer getGradeAvgScorePercent() {
	 * 
	 * return gradeAvgScorePercent; }
	 * 
	 * public void setGradeAvgScorePercent(Integer gradeAvgScorePercent) {
	 * 
	 * this.gradeAvgScorePercent = gradeAvgScorePercent; }
	 */

	/**
	 * 年级组长发布的任务ID
	 */
	private Long weektestId;

	/**
	 * 题号索引
	 */
	private Integer indexNo;
	/**
	 * 年级组长发布的任务名称
	 */
	private String weektestName;

	public Integer getIndexNo() {

		return indexNo;
	}

	public void setIndexNo(Integer indexNo) {

		this.indexNo = indexNo;
	}

	/* 学校编号 */
	private Long schoolId;
	/* 学校名称 */
	private String schoolName;
	/* 入学年份 */
	private Integer gradeYear;
	/**
	 * 班级ID
	 */
	private Long classId;
	/**
	 * 班级名称
	 */
	private String className;

	/**
	 * 题号
	 */
	private Long problemId;

	/**
	 * 涉及知识点
	 */
	private String points;

	/**
	 * 做对的人数
	 */
	private Integer rightCnt;

	/**
	 * 做错的人数
	 */
	private Integer wrongCnt;

	/**
	 * 班级整体平均得分率 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer avgScorePercent;

	/**
	 * 班级优秀学生平均得分率 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer excellentScorePercent;

	/**
	 * 班级及格学生平均得分率 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer passScorePercent;

	/**
	 * 班级不及格学生平均得分率 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer unpassScorePercent;

	/**
	 * 班级整体平均得分率差值 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer avgDiffScorePercent;

	/**
	 * 班级不及格学生平均得分率差值 乘以 1000，展示的时候需要除以1000，保留
	 */
	private Integer unpassDiffScorePercent;

	/**
	 * 班级及格学生平均得分率差值 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer passDiffScorePercent;

	/**
	 * 班级优秀学生平均得分率差值 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer excellentDiffScorePercent;

	/**
	 * 测试时间
	 */
	private Date createTime;

	/**
	 * 试卷任务发布的时间
	 */
	private Date taskPubTime;

	/**
	 * 是否为高频考题；0：不是，1：是'
	 */
	private Byte isHighFreq;

	private Integer problemType;

	private Integer aCnt;

	private Integer bCnt;

	private Integer cCnt;

	private Integer dCnt;

	private Integer eCnt;

	private Integer noFullScoreCnt;

	private Integer noAnswerCnt;

	/**
	 * @return the isHighFreq
	 */
	public Byte getIsHighFreq() {

		return isHighFreq;
	}

	/**
	 * @param isHighFreq
	 *            the isHighFreq to set
	 */
	public void setIsHighFreq(Byte isHighFreq) {

		this.isHighFreq = isHighFreq;
	}

	/**
	 * 将数据库显示的百分数转换为展示用的百分点
	 * 
	 * @param pamamter
	 * @return
	 */
	public String parseViewPercent(Integer pamamter)
	{

		return PerCentFractionUtil.parseViewPercent(pamamter);

	}

	/**
	 * 将数据库显示的分数类型转换为展示用的分数，保留小数点后一位
	 * 
	 * @param pamamter
	 * @return
	 */
	public Double parseViewFraction(Integer pamamter)
	{

		return PerCentFractionUtil.parseViewFraction(pamamter);
	}

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Long getWeektestId() {

		return weektestId;
	}

	public void setWeektestId(Long weektestId) {

		this.weektestId = weektestId;
	}

	public String getWeektestName() {

		return weektestName;
	}

	public void setWeektestName(String weektestName) {

		this.weektestName = weektestName == null ? null : weektestName.trim();
	}

	public Long getSchoolId() {

		return schoolId;
	}

	public void setSchoolId(Long schoolId) {

		this.schoolId = schoolId;
	}

	public String getSchoolName() {

		return schoolName;
	}

	public void setSchoolName(String schoolName) {

		this.schoolName = schoolName == null ? null : schoolName.trim();
	}

	public Integer getGradeYear() {

		return gradeYear;
	}

	public void setGradeYear(Integer gradeYear) {

		this.gradeYear = gradeYear;
	}

	public Long getClassId() {

		return classId;
	}

	public void setClassId(Long classId) {

		this.classId = classId;
	}

	public String getClassName() {

		return className;
	}

	public void setClassName(String className) {

		this.className = className == null ? null : className.trim();
	}

	public Long getProblemId() {

		return problemId;
	}

	public void setProblemId(Long problemId) {

		this.problemId = problemId;
	}

	public String getPoints() {

		return points;
	}

	public void setPoints(String points) {

		this.points = points == null ? null : points.trim();
	}

	public Integer getRightCnt() {

		return rightCnt;
	}

	public void setRightCnt(Integer rightCnt) {

		this.rightCnt = rightCnt;
	}

	public Integer getWrongCnt() {

		return wrongCnt;
	}

	public void setWrongCnt(Integer wrongCnt) {

		this.wrongCnt = wrongCnt;
	}

	public Integer getAvgScorePercent() {

		return avgScorePercent;
	}

	public void setAvgScorePercent(Integer avgScorePercent) {

		this.avgScorePercent = avgScorePercent;
	}

	public Integer getExcellentScorePercent() {

		return excellentScorePercent;
	}

	public void setExcellentScorePercent(Integer excellentScorePercent) {

		this.excellentScorePercent = excellentScorePercent;
	}

	public Integer getPassScorePercent() {

		return passScorePercent;
	}

	public void setPassScorePercent(Integer passScorePercent) {

		this.passScorePercent = passScorePercent;
	}

	public Integer getUnpassScorePercent() {

		return unpassScorePercent;
	}

	public void setUnpassScorePercent(Integer unpassScorePercent) {

		this.unpassScorePercent = unpassScorePercent;
	}

	public Integer getAvgDiffScorePercent() {

		return avgDiffScorePercent;
	}

	public void setAvgDiffScorePercent(Integer avgDiffScorePercent) {

		this.avgDiffScorePercent = avgDiffScorePercent;
	}

	public Integer getUnpassDiffScorePercent() {

		return unpassDiffScorePercent;
	}

	public void setUnpassDiffScorePercent(Integer unpassDiffScorePercent) {

		this.unpassDiffScorePercent = unpassDiffScorePercent;
	}

	public Integer getPassDiffScorePercent() {

		return passDiffScorePercent;
	}

	public void setPassDiffScorePercent(Integer passDiffScorePercent) {

		this.passDiffScorePercent = passDiffScorePercent;
	}

	public Integer getExcellentDiffScorePercent() {

		return excellentDiffScorePercent;
	}

	public void setExcellentDiffScorePercent(Integer excellentDiffScorePercent) {

		this.excellentDiffScorePercent = excellentDiffScorePercent;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public Date getTaskPubTime() {

		return taskPubTime;
	}

	public void setTaskPubTime(Date taskPubTime) {

		this.taskPubTime = taskPubTime;
	}

	public Integer getProblemType() {

		return problemType;
	}

	public void setProblemType(Integer problemType) {

		this.problemType = problemType;
	}

	public Integer getaCnt() {

		return aCnt;
	}

	public void setaCnt(Integer aCnt) {

		this.aCnt = aCnt;
	}

	public Integer getbCnt() {

		return bCnt;
	}

	public void setbCnt(Integer bCnt) {

		this.bCnt = bCnt;
	}

	public Integer getcCnt() {

		return cCnt;
	}

	public void setcCnt(Integer cCnt) {

		this.cCnt = cCnt;
	}

	public Integer getdCnt() {

		return dCnt;
	}

	public void setdCnt(Integer dCnt) {

		this.dCnt = dCnt;
	}

	public Integer geteCnt() {

		return eCnt;
	}

	public void seteCnt(Integer eCnt) {

		this.eCnt = eCnt;
	}

	public Integer getNoFullScoreCnt() {

		return noFullScoreCnt;
	}

	public void setNoFullScoreCnt(Integer noFullScoreCnt) {

		this.noFullScoreCnt = noFullScoreCnt;
	}

	public Integer getNoAnswerCnt() {

		return noAnswerCnt;
	}

	public void setNoAnswerCnt(Integer noAnswerCnt) {

		this.noAnswerCnt = noAnswerCnt;
	}

	@Override
	public Integer getPK() {

		return id;
	}
}