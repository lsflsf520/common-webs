package com.yisi.stiku.intg.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.intg.constant.DmStatus;

public class DevDemand extends BaseEntity<Integer> {

	private Integer id;

	private String name;

	private String repoName;

	private String branch;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date planTestTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date planOnlineTime;

	private Date realTestTime;

	private Date realOnlineTime;

	private Integer creatorId;

	private String creator;

	private Integer parentId;

	private Integer productDemandId;

	private Byte status;

	private Integer envs;

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

	public String getRepoName() {

		return repoName;
	}

	public void setRepoName(String repoName) {

		this.repoName = repoName == null ? null : repoName.trim();
	}

	public String getBranch() {

		return branch;
	}

	public void setBranch(String branch) {

		this.branch = branch == null ? null : branch.trim();
	}

	public Date getPlanTestTime() {

		return planTestTime;
	}

	public String getPlanTestTimeStr() {

		return this.getPlanTestTime() == null ? "" : DateUtil.getDateStr(this.getPlanTestTime());
	}

	public void setPlanTestTime(Date planTestTime) {

		this.planTestTime = planTestTime;
	}

	public Date getPlanOnlineTime() {

		return planOnlineTime;
	}

	public String getPlanOnlineTimeStr() {

		return this.getPlanOnlineTime() == null ? "" : DateUtil.getDateStr(this.getPlanOnlineTime());
	}

	public void setPlanOnlineTime(Date planOnlineTime) {

		this.planOnlineTime = planOnlineTime;
	}

	public Date getRealTestTime() {

		return realTestTime;
	}

	public void setRealTestTime(Date realTestTime) {

		this.realTestTime = realTestTime;
	}

	public Date getRealOnlineTime() {

		return realOnlineTime;
	}

	public void setRealOnlineTime(Date realOnlineTime) {

		this.realOnlineTime = realOnlineTime;
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

	public Integer getParentId() {

		return parentId;
	}

	public void setParentId(Integer parentId) {

		this.parentId = parentId;
	}

	public Integer getProductDemandId() {

		return productDemandId;
	}

	public void setProductDemandId(Integer productDemandId) {

		this.productDemandId = productDemandId;
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

	public Integer getEnvs() {

		return envs == null ? 0 : envs;
	}

	public void setEnvs(Integer envs) {

		this.envs = envs;
	}

	public Date getCreateTime() {

		return createTime;
	}

	public String getCreateTimeStr() {

		return this.getCreateTime() == null ? "" : DateUtil.getDateTimeStr(this.getCreateTime());
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Override
	public Integer getPK() {

		return id;
	}
}