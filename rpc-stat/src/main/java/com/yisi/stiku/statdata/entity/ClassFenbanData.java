package com.yisi.stiku.statdata.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;

public class ClassFenbanData extends BaseEntity<Integer> {

	/**
	 * 主键
	 */
	private Integer id;

	/* 年级组长发布的任务ID */
	private Long weektestId;
	/* 年级组长发布的任务名称 */
	private String weektestName;

	/* 学校编号 */
	private Long schoolId;

	/* 学校名称 */
	private String schoolName;

	/* 入学年份 */
	private Integer gradeYear;

	/**
	 * 任务章id：tbt_weektest_work 中的 book_point_sort_id
	 */
	private Long bookPointSortId;

	public Long getBookPointSortId() {

		return bookPointSortId;
	}

	public void setBookPointSortId(Long bookPointSortId) {

		this.bookPointSortId = bookPointSortId;
	}

	public Integer getExampaperIndex() {

		return exampaperIndex;
	}

	public void setExampaperIndex(Integer exampaperIndex) {

		this.exampaperIndex = exampaperIndex;
	}

	/**
	 * 每一节在自己所在章中的排序索引
	 */
	private Integer exampaperIndex;
	/**
	 * 班级ID
	 */
	private Long classId;

	/**
	 * 班级名称
	 */
	private String className;

	/**
	 * 授课老师的用户id，即tbl_auth_user中的id
	 */
	private Long teacherUid;

	/**
	 * 授课老师的名字
	 */
	private String teacherName;

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
	 * 文理科0：文理通用；1：文科；2：理科
	 */
	private Integer sType;

	/**
	 * 排名
	 */
	private Integer rank;

	/**
	 * 上升的排名
	 */
	private Integer incRank;

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
	 * 缺勤率 乘以 1000，展示的时候需要除以1000
	 */
	private Integer absencePercent;

	/**
	 * 进步的平均分 乘以 10，展示的时候需要除以10，保留一位小数，可以为负数；如果是第一次考试，则此值为0
	 */
	private Integer incScore;

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
	 * 缺勤率上升的百分点
	 */
	private Integer absenceIncPoint;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 任务发布时间
	 */
	private Date taskPubTime;

	/**
	 * 上升的优秀人数
	 */
	private Integer excellentIncCnt;
	/**
	 * 上升的及格人数
	 */
	private Integer passIncCnt;

	public Integer getExcellentIncCnt() {

		return excellentIncCnt;
	}

	public void setExcellentIncCnt(Integer excellentIncCnt) {

		this.excellentIncCnt = excellentIncCnt;
	}

	public Integer getPassIncCnt() {

		return passIncCnt;
	}

	public void setPassIncCnt(Integer passIncCnt) {

		this.passIncCnt = passIncCnt;
	}

	public Integer getUnpassIncCnt() {

		return unpassIncCnt;
	}

	public void setUnpassIncCnt(Integer unpassIncCnt) {

		this.unpassIncCnt = unpassIncCnt;
	}

	/**
	 * 上升的不及格人数
	 */
	private Integer unpassIncCnt;

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

	public Long getTeacherUid() {

		return teacherUid;
	}

	public void setTeacherUid(Long teacherUid) {

		this.teacherUid = teacherUid;
	}

	public String getTeacherName() {

		return teacherName;
	}

	public void setTeacherName(String teacherName) {

		this.teacherName = teacherName == null ? null : teacherName.trim();
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

	public Integer getsType() {

		return sType;
	}

	public void setsType(Integer sType) {

		this.sType = sType;
	}

	public Integer getRank() {

		return rank;
	}

	public void setRank(Integer rank) {

		this.rank = rank;
	}

	public Integer getIncRank() {

		return incRank;
	}

	public void setIncRank(Integer incRank) {

		this.incRank = incRank;
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

	public Integer getAbsencePercent() {

		return absencePercent;
	}

	public void setAbsencePercent(Integer absencePercent) {

		this.absencePercent = absencePercent;
	}

	public Integer getIncScore() {

		return incScore;
	}

	public void setIncScore(Integer incScore) {

		this.incScore = incScore;
	}

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

	public Integer getAbsenceIncPoint() {

		return absenceIncPoint;
	}

	public void setAbsenceIncPoint(Integer absenceIncPoint) {

		this.absenceIncPoint = absenceIncPoint;
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
}