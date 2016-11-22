package com.yisi.stiku.msg.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.bean.EntityState;

public class EmailTmpl extends BaseEntity<Integer> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String title;

    private EntityState dbState;

    private String content;
    
    private Date createTime;

    private Date lastUptime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

	@Override
    public Integer getPK() {
        return id;
    }
}