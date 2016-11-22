package com.yisi.stiku.statdata.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;

public class GradeFenbanProblemData extends BaseEntity<Integer> {

	private Integer id;

	/**
	 * 试卷中的题号
	 */
	private Integer indexNo;

	public Integer getIndexNo() {

		return indexNo;
	}

	public void setIndexNo(Integer indexNo) {

		this.indexNo = indexNo;
	}

	/**
	 * 年级组长发布的任务ID
	 */
	private Long weektestId;

	/**
	 * 年级组长发布的任务名称
	 */
	private String weektestName;

	/**
	 * 学校ID
	 */
	private Long schoolId;

	/**
	 * 学校名称
	 */
	private String schoolName;

	/**
	 * 入学年份
	 */
	private Integer gradeYear;

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
	 * 年级整体平均得分率 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer avgScorePercent;

	/**
	 * 年级优秀学生平均得分率 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer excellentScorePercent;

	/**
	 * 年级及格学生平均得分率 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer passScorePercent;

	/**
	 * 年级不及格学生平均得分率 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer unpassScorePercent;

	/**
	 * 年级不及格学生的平均得分率差值 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer unpassDiffScorePercent;

	/**
	 * 年级及格学生的平均得分率差值 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer passDiffScorePercent;

	/**
	 * 年级优秀学生平均得分率差值 乘以 1000，展示的时候需要除以1000，保留一位小数
	 */
	private Integer excellentDiffScorePercent;

	/**
	 * 创建时间
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

	@Override
	public Integer getPK() {

		return id;
	}
}