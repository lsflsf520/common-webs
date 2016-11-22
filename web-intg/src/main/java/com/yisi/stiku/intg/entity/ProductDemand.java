package com.yisi.stiku.intg.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.intg.constant.DmStatus;
import com.yisi.stiku.intg.util.IntgUtil;

public class ProductDemand extends BaseEntity<Integer> {

	private Integer id;

	private String name;

	private Integer creatorId;

	private String creator;

	private String description;

	private String attachs;

	private String project;

	private String module;

	private String devLeader;

	private String testLeader;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expectedTime;

	private Byte status;

	private Date createTime;

	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name == null ? null : name.trim();
	}

	public Integer getCreatorId() {

		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {

		this.creatorId = creatorId;
	}

	public String getCreator() {

		return creator;
	}

	public void setCreator(String creator) {

		this.creator = creator == null ? null : creator.trim();
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description == null ? null : description.trim();
	}

	public String getAttachs() {

		return attachs;
	}

	public void setAttachs(String attachs) {

		this.attachs = attachs == null ? null : attachs.trim();
	}

	public String getProject() {

		return project;
	}

	public void setProject(String project) {

		this.project = project;
	}

	public String getProjectCnName() {

		return IntgUtil.getProjectCnName(getProject());
	}

	public String getModule() {

		return module;
	}

	public void setModule(String module) {

		this.module = module;
	}

	public String getModuleCnName() {

		return IntgUtil.getModuleCnName4Project(getProject(), getModule());
	}

	public String getDevLeader() {

		return devLeader;
	}

	public void setDevLeader(String devLeader) {

		this.devLeader = devLeader;
	}

	public String getTestLeader() {

		return testLeader;
	}

	public void setTestLeader(String testLeader) {

		this.testLeader = testLeader;
	}

	public Date getExpectedTime() {

		return expectedTime;
	}

	public void setExpectedTime(Date expectedTime) {

		this.expectedTime = expectedTime;
	}

	public String getExpectedTimeStr() {

		return this.getExpectedTime() == null ? "" : DateUtil.getDateStr(this.getExpectedTime());
	}

	public Byte getStatus() {

		return status;
	}

	public void setStatus(Byte status) {

		this.status = status;
	}

	public DmStatus getDbStatus() {

		return DmStatus.getByDbCode(this.status);
	}

	public void setDbStatus(DmStatus dmStatus) {

		this.setStatus(dmStatus.getDbCode());
	}

	public Date getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public String getCreateTimeStr() {

		return this.getCreateTime() == null ? "" : DateUtil.getDateTimeStr(this.getCreateTime());
	}

	@Override
	public Integer getPK() {

		return id;
	}
}