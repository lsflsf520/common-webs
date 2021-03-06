package com.yisi.stiku.priv.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.bean.EntityState;

public class Role extends BaseEntity<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String name;

	private Date createTime;

	private Date lastUptime;

	private String remark;

	private EntityState dbState;

	private Byte show2User; // 是否可以用于用户的类型标识。0：不可以；1：可以

	private String projectName; // 角色所属ProjectName

	public Integer getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUptime() {
		return lastUptime;
	}

	public void setLastUptime(Date lastUptime) {
		this.lastUptime = lastUptime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Byte getState() {
		return getDbState() == null ? null : getDbState().getDbCode();
	}

	public void setState(Byte state) {
		setDbState(EntityState.getByDbCode(state));
	}

	public EntityState getDbState() {
		return dbState;
	}

	public void setDbState(EntityState dbState) {
		this.dbState = dbState;
	}

	public Byte getShow2User() {
		return show2User;
	}

	public void setShow2User(Byte show2User) {
		this.show2User = show2User;
	}

	@Override
	public Integer getPK() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Role) {
			return this.id == ((Role) obj).getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id == null ? 11 : 11 * this.id;
	}
}