package com.yisi.stiku.wallet.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.bean.EntityState;

public class Wallet extends BaseEntity<Long> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

    private Integer availBalance;

    private Integer availPoint;

    private Integer offlineYye;

    private Integer offlineTotalYye;

    private Integer onlineYye;

    private Integer onlineTotalYye;

    private Integer totalChargeMoney;
    
    private EntityState dbState;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getAvailBalance() {
        return availBalance == null ? 0 : availBalance;
    }

    public void setAvailBalance(Integer availBalance) {
        this.availBalance = availBalance; 
    }

    public int getAvailPoint() {
        return availPoint == null ? 0 : availPoint;
    }

    public void setAvailPoint(Integer availPoint) {
        this.availPoint = availPoint;
    }

    public Byte getState() {
        return getDbState().getDbCode();
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

	public Integer getOfflineYye() {
		return offlineYye == null ? 0 : offlineYye;
	}

	public void setOfflineYye(Integer offlineYye) {
		this.offlineYye = offlineYye;
	}

	public Integer getOfflineTotalYye() {
		return offlineTotalYye == null ? 0 : offlineTotalYye;
	}

	public void setOfflineTotalYye(Integer offlineTotalYye) {
		this.offlineTotalYye = offlineTotalYye;
	}

	public Integer getOnlineYye() {
		return onlineYye == null ? 0 : onlineYye;
	}

	public void setOnlineYye(Integer onlineYye) {
		this.onlineYye = onlineYye;
	}

	public Integer getOnlineTotalYye() {
		return onlineTotalYye == null ? 0 : onlineTotalYye;
	}

	public void setOnlineTotalYye(Integer onlineTotalYye) {
		this.onlineTotalYye = onlineTotalYye;
	}

	public Integer getTotalChargeMoney() {
		return totalChargeMoney == null ? 0 : totalChargeMoney;
	}

	public void setTotalChargeMoney(Integer totalChargeMoney) {
		this.totalChargeMoney = totalChargeMoney;
	}

	@Override
    public Long getPK() {
        return userId;
    }
}