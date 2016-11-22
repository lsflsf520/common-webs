package com.yisi.stiku.wallet.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.bean.EntityState;

public class ResUser extends BaseEntity<Integer> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer resInfoId;

    private String resName;

    private Long userId;

    private String userName;

    private Byte channel;

    private Date createTime;

    private Date lastUptime;

    private String feature;

//    private Byte state;
    
    private EntityState dbState;

    private String bindRemark;

    private String unbindRemark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResInfoId() {
        return resInfoId;
    }

    public void setResInfoId(Integer resInfoId) {
        this.resInfoId = resInfoId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
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

    public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Byte getState() {
        return getDbState() == null ? null : getDbState().getDbCode();
    }

    public void setState(Byte state) {
//        this.state = state;
    	this.dbState = EntityState.getByDbCode(state);
    }

    public EntityState getDbState() {
		return dbState;
	}

	public void setDbState(EntityState dbState) {
		this.dbState = dbState;
	}

	public String getBindRemark() {
        return bindRemark;
    }

    public void setBindRemark(String bindRemark) {
        this.bindRemark = bindRemark == null ? null : bindRemark.trim();
    }

    public String getUnbindRemark() {
        return unbindRemark;
    }

    public void setUnbindRemark(String unbindRemark) {
        this.unbindRemark = unbindRemark == null ? null : unbindRemark.trim();
    }

    @Override
    public Integer getPK() {
        return id;
    }
}