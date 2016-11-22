package com.yisi.stiku.basedata.entity;

public class SchoolExamPaperVO {
	
	private Long id;
	
	//private Long schoolId;
	
	private Long paperId;
	
	//private Long areaId;
	
	//private String schoolName;
	
	private String paperName;
	
	private Integer paperCountScore;
	
	//private String areaName;
	//试卷类型 0高考卷    1月考卷    2其他试卷
	private Integer paperType;
	
	private Integer grade;
	
	private Integer studentCountNumber;
	
	public Integer getPaperCountScore() {
		return paperCountScore;
	}

	public void setPaperCountScore(Integer paperCountScore) {
		this.paperCountScore = paperCountScore;
	}
	
	public Integer getStudentCountNumber() {
		return studentCountNumber;
	}

	public void setStudentCountNumber(Integer studentCountNumber) {
		this.studentCountNumber = studentCountNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Long getSchoolId() {
//		return schoolId;
//	}
//
//	public void setSchoolId(Long schoolId) {
//		this.schoolId = schoolId;
//	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

//	public Long getAreaId() {
//		return areaId;
//	}
//
//	public void setAreaId(Long areaId) {
//		this.areaId = areaId;
//	}

//	public String getSchoolName() {
//		return schoolName;
//	}
//
//	public void setSchoolName(String schoolName) {
//		this.schoolName = schoolName;
//	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

//	public String getAreaName() {
//		return areaName;
//	}
//
//	public void setAreaName(String areaName) {
//		this.areaName = areaName;
//	}

	public Integer getPaperType() {
		return paperType;
	}

	public void setPaperType(Integer paperType) {
		this.paperType = paperType;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

}
