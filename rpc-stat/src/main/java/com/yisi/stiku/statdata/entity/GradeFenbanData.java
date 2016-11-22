package com.yisi.stiku.statdata.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;

public class GradeFenbanData extends BaseEntity<Integer> {

	private Integer id;

	/**
	 * 试卷id, 计算全省排名的时候需要用到
	 */
	private Long paperId;

	/**
	 * 年级组长发布的任务ID
	 */
	private Long weektestId;

	/**
	 * 年级组长发布的任务名称
	 */
	private String weektestName;

	/**
	 * 省份id
	 */
	private Long provinceId;

	/**
	 * 城市id
	 */
	private Long cityId;

	/**
	 * 县/区的id
	 */
	private Long countyId;

	/**
	 * 学校id
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
	 * 文理科
	 */
	private Integer sType;

	/**
	 * 实考人数
	 */
	private Integer realTestCnt;

	/**
	 * 班级学生总人数
	 */
	private Integer studentCnt;

	/**
	 * 优秀人数
	 */
	private Integer excellentCnt;

	/**
	 * 及格人数
	 */
	private Integer passCnt;

	/**
	 * 不及格人数
	 */
	private Integer unpassCnt;

	/**
	 * 优秀率上升的百分点
	 */
	private Integer excellentIncPoint;

	/**
	 * 及格率上升的百分点
	 */
	private Integer passIncPoint;

	/**
	 * 不及格率上升的百分点
	 */
	private Integer unpassIncPoint;

	/**
	 * 年级在全省的排名
	 */
	private Integer provinceRank;

	public Integer getExcellentIncPoint() {

		return excellentIncPoint;
	}

	public void setExcellentIncPoint(Integer excellentIncPoint) {

		this.excellentIncPoint = excellentIncPoint;
	}

	public Integer getPassIncPoint() {

		return passIncPoint;
	}

	public void setPassIncPoint(Integer passIncPoint) {

		this.passIncPoint = passIncPoint;
	}

	public Integer getUnpassIncPoint() {

		return unpassIncPoint;
	}

	public void setUnpassIncPoint(Integer unpassIncPoint) {

		this.unpassIncPoint = unpassIncPoint;
	}

	/**
	 * 平均分 乘以 10，展示的时候需要除以10，保留一位小数
	 */
	private Integer avgScore;

	/**
	 * 最高分 乘以 10，展示的时候需要除以10，保留一位小数
	 */
	private Integer maxScore;

	/**
	 * 最低分 乘以 10，展示的时候需要除以10，保留一位小数
	 */
	private Integer minScore;

	/**
	 * 100 为100分制；150 为150分制；120 为120分制
	 */
	private Integer scoreType;

	/**
	 * 优秀率 乘以 1000， 展示的时候需要除以1000
	 */
	private Integer excellentPercent;

	/**
	 * 及格率 乘以 1000， 展示的时候需要除以1000
	 */
	private Integer passPercent;

	/**
	 * 不及格率 乘以 1000， 展示的时候需要除以1000
	 */
	private Integer unpassPercent;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 试卷任务发布的时间
	 */
	private Date taskPubTime;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Long getPaperId() {

		return paperId;
	}

	public void setPaperId(Long paperId) {

		this.paperId = paperId;
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

	public Long getProvinceId() {

		return provinceId;
	}

	public void setProvinceId(Long provinceId) {

		this.provinceId = provinceId;
	}

	public Long getCityId() {

		return cityId;
	}

	public void setCityId(Long cityId) {

		this.cityId = cityId;
	}

	public Long getCountyId() {

		return countyId;
	}

	public void setCountyId(Long countyId) {

		this.countyId = countyId;
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

	public Integer getsType() {

		return sType;
	}

	public void setsType(Integer sType) {

		this.sType = sType;
	}

	public Integer getRealTestCnt() {

		return realTestCnt;
	}

	public void setRealTestCnt(Integer realTestCnt) {

		this.realTestCnt = realTestCnt;
	}

	public Integer getStudentCnt() {

		return studentCnt;
	}

	public void setStudentCnt(Integer studentCnt) {

		this.studentCnt = studentCnt;
	}

	public Integer getExcellentCnt() {

		return excellentCnt;
	}

	public void setExcellentCnt(Integer excellentCnt) {

		this.excellentCnt = excellentCnt;
	}

	public Integer getPassCnt() {

		return passCnt;
	}

	public void setPassCnt(Integer passCnt) {

		this.passCnt = passCnt;
	}

	public Integer getUnpassCnt() {

		return unpassCnt;
	}

	public void setUnpassCnt(Integer unpassCnt) {

		this.unpassCnt = unpassCnt;
	}

	public Integer getProvinceRank() {

		return provinceRank;
	}

	public void setProvinceRank(Integer provinceRank) {

		this.provinceRank = provinceRank;
	}

	public Integer getAvgScore() {

		return avgScore;
	}

	public void setAvgScore(Integer avgScore) {

		this.avgScore = avgScore;
	}

	public Integer getMaxScore() {

		return maxScore;
	}

	public void setMaxScore(Integer maxScore) {

		this.maxScore = maxScore;
	}

	public Integer getMinScore() {

		return minScore;
	}

	public void setMinScore(Integer minScore) {

		this.minScore = minScore;
	}

	public Integer getScoreType() {

		return scoreType;
	}

	public void setScoreType(Integer scoreType) {

		this.scoreType = scoreType;
	}

	public Integer getExcellentPercent() {

		return excellentPercent;
	}

	public void setExcellentPercent(Integer excellentPercent) {

		this.excellentPercent = excellentPercent;
	}

	public Integer getPassPercent() {

		return passPercent;
	}

	public void setPassPercent(Integer passPercent) {

		this.passPercent = passPercent;
	}

	public Integer getUnpassPercent() {

		return unpassPercent;
	}

	public void setUnpassPercent(Integer unpassPercent) {

		this.unpassPercent = unpassPercent;
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

	@Override
	public Integer getPK() {

		return id;
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
}