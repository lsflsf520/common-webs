package com.yisi.stiku.wallet.entity;

import java.util.Date;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.wallet.rpc.constant.FuncCardState;
import com.yisi.stiku.wallet.rpc.constant.FuncCardType;

public class FuncCard extends BaseEntity<Integer> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer cardNo;

    private String actCode;

    private Integer resInfoId;

//    private String type;
    private FuncCardType dbType;
    
    private Date createTime;
    
    private Date lastUptime;

    private Date actTime;

    private Long userId;

    private String userName;

    private Integer batchId;

//    private Byte state;
    private FuncCardState dbState;

    public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode == null ? null : actCode.trim();
    }

    public Integer getResInfoId() {
        return resInfoId;
    }

    public void setResInfoId(Integer resInfoId) {
        this.resInfoId = resInfoId;
    }
    
    public String getFormatCreateTime(){
		return createTime == null ? null : DateUtil.getDateTimeStr(createTime);
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

	public Date getActTime() {
		return actTime;
	}

	public void setActTime(Date actTime) {
		this.actTime = actTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
        return getDbType() == null ? null : getDbType().name();
    }

    public void setType(String type) {
        this.setDbType(FuncCardType.valueOf(type));
    }
    
    public FuncCardType getDbType() {
		return dbType;
	}

	public void setDbType(FuncCardType dbType) {
		this.dbType = dbType;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public Byte getState() {
        return getDbState() == null ? null : getDbState().getDbCode();
    }

    public void setState(Byte state) {
        this.setDbState(FuncCardState.getByDbCode(state));
    }
    
    public FuncCardState getDbState() {
		return dbState;
	}

	public void setDbState(FuncCardState dbState) {
		this.dbState = dbState;
	}
	
	/**
	 * 
	 * @return 如果是金卡，则返回true
	 */
	public boolean isGoldCard(){
		return getDbType() != null && FuncCardType.G12.getDbCode().equals(getDbType().getDbCode());
	}
	
	/**
	 * 
	 * @return 如果是银卡，则返回true
	 */
	public boolean isSilverCard(){
		return getDbType() != null && FuncCardType.S12.getDbCode().equals(getDbType().getDbCode());
	}
	
	/**
	 * 
	 * @return 如果是钻石卡，则返回true
	 */
	public boolean isDiamondCard(){
		return getDbType() != null && FuncCardType.D12.getDbCode().equals(getDbType().getDbCode());
	}

	@Override
    public Integer getPK() {
        return cardNo;
    }
}
