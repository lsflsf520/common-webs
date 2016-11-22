package com.yisi.stiku.basedata.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;

public class TbtTeacherClass extends BaseEntity<Long> {

	private static final long serialVersionUID = 5236846496702132329L;

	private Long id;

	private String aclCode;

	private String aclType;

	private String createdBy;

	private Date createdDt;

	private String updatedBy;

	private Date updatedDt;

	private Integer version;

	private Long classId;

	private Long teacherId;

	private String teacherName;

	private Long schoolId;

	private Integer teacherType;

	private byte[] teacher;

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	public String getAclCode() {

		return aclCode;
	}

	public void setAclCode(String aclCode) {

		this.aclCode = aclCode == null ? null : aclCode.trim();
	}

	public String getAclType() {

		return aclType;
	}

	public void setAclType(String aclType) {

		this.aclType = aclType == null ? null : aclType.trim();
	}

	public String getCreatedBy() {

		return createdBy;
	}

	public void setCreatedBy(String createdBy) {

		this.createdBy = createdBy == null ? null : createdBy.trim();
	}

	public Date getCreatedDt() {

		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {

		this.createdDt = createdDt;
	}

	public String getUpdatedBy() {

		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {

		this.updatedBy = updatedBy == null ? null : updatedBy.trim();
	}

	public Date getUpdatedDt() {

		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {

		this.updatedDt = updatedDt;
	}

	public Integer getVersion() {

		return version;
	}

	public void setVersion(Integer version) {

		this.version = version;
	}

	public Long getClassId() {

		return classId;
	}

	public void setClassId(Long classId) {

		this.classId = classId;
	}

	public Long getTeacherId() {

		return teacherId;
	}

	public void setTeacherId(Long teacherId) {

		this.teacherId = teacherId;
	}

	public String getTeacherName() {

		return teacherName;
	}

	public void setTeacherName(String teacherName) {

		this.teacherName = teacherName == null ? null : teacherName.trim();
	}

	public Long getSchoolId() {

		return schoolId;
	}

	public void setSchoolId(Long schoolId) {

		this.schoolId = schoolId;
	}

	public Integer getTeacherType() {

		return teacherType;
	}

	public void setTeacherType(Integer teacherType) {

		this.teacherType = teacherType;
	}

	public byte[] getTeacher() {

		return teacher;
	}

	public void setTeacher(byte[] teacher) {

		this.teacher = teacher;
	}

	@Override
	public Long getPK() {

		return id;
	}
}