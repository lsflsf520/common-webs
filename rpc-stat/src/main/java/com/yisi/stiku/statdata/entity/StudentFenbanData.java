package com.yisi.stiku.statdata.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;

public class StudentFenbanData extends BaseEntity<Integer> {

	private Integer id;

	/**
	 * 学生编号
	 */
	private Long studentId;

	/**
	 * 学生姓名
	 */
	private String studentName;

	/**
	 * 班级ID
	 */
	private Long classId;

	/**
	 * 年级组长发布的任务ID
	 */
	private Long weektestId;

	/**
	 * 本次考试得分 乘以 10，展示的时候需要除以10，保留一位小数
	 */
	private Integer graspScore;

	/**
	 * 本次考试的班级排名
	 */
	private Integer classRank;

	/**
	 * 上次班级排名
	 */
	private Integer lastClassRank;

	/**
	 * 班级总人数
	 */
	private Integer classStudentCnt;

	/**
	 * 参加了本次考试的实际人数
	 */
	private Integer classRealTestCnt;

	/**
	 * 班级内比自己分数少的人的数量
	 */
	private Integer followCnt;

	/**
	 * 班级内比自己分数多的人的数量
	 */
	private Integer overCnt;

	/**
	 * 数据生成的时间
	 */
	private Date createTime;

	/**
	 * 试卷任务发布的时间
	 */
	private Date taskPubTime;

	/**
	 * 章ID
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
	 * 章排序
	 */
	private Integer exampaperIndex;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public Long getStudentId() {

		return studentId;
	}

	public void setStudentId(Long studentId) {

		this.studentId = studentId;
	}

	public String getStudentName() {

		return studentName;
	}

	public void setStudentName(String studentName) {

		this.studentName = studentName == null ? null : studentName.trim();
	}

	public Long getClassId() {

		return classId;
	}

	public void setClassId(Long classId) {

		this.classId = classId;
	}

	public Long getWeektestId() {

		return weektestId;
	}

	public void setWeektestId(Long weektestId) {

		this.weektestId = weektestId;
	}

	public Integer getGraspScore() {

		return graspScore;
	}

	public void setGraspScore(Integer graspScore) {

		this.graspScore = graspScore;
	}

	public Integer getClassRank() {

		return classRank;
	}

	public void setClassRank(Integer classRank) {

		this.classRank = classRank;
	}

	public Integer getLastClassRank() {

		return lastClassRank;
	}

	public void setLastClassRank(Integer lastClassRank) {

		this.lastClassRank = lastClassRank;
	}

	public Integer getClassStudentCnt() {

		return classStudentCnt;
	}

	public void setClassStudentCnt(Integer classStudentCnt) {

		this.classStudentCnt = classStudentCnt;
	}

	public Integer getClassRealTestCnt() {

		return classRealTestCnt;
	}

	public void setClassRealTestCnt(Integer classRealTestCnt) {

		this.classRealTestCnt = classRealTestCnt;
	}

	public Integer getFollowCnt() {

		return followCnt;
	}

	public void setFollowCnt(Integer followCnt) {

		this.followCnt = followCnt;
	}

	public Integer getOverCnt() {

		return overCnt;
	}

	public void setOverCnt(Integer overCnt) {

		this.overCnt = overCnt;
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