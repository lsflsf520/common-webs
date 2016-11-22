package com.yisi.stiku.statdata.entity;

import java.util.Date;

public class AsSchoolGradeVO {

	/* 任务编号 */
	private Long weektestId;
	/* 试卷名称 */
	private String weektestName;
	/* 生成报告时间 */
	private Date createTime;
	/* 入学年份 */
	private Integer gradeYear;

	/* 文科,理科 */
	private Integer sType;

	private Long classId;

	public Integer getsType() {

		return sType;
	}

	public void setsType(Integer sType) {

		this.sType = sType;
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

	public String getWeektestName() {

		return weektestName;
	}

	public void setWeektestName(String weektestName) {

		this.weektestName = weektestName;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public Integer getGradeYear() {

		return gradeYear;
	}

	public void setGradeYear(Integer gradeYear) {

		this.gradeYear = gradeYear;
	}

}
