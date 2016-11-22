package com.yisi.stiku.wallet.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.wallet.rpc.constant.JieSuanState;

import java.util.Date;

public class JiesuanData extends BaseEntity<Integer> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Long userId;
    
    private String userName;

    private Date startTime;

    private Date endTime;
    
    private String formatStartTime;
    private String formatEndTime;

    private Integer offlineYye;

    private Integer onlineYye;

    private Integer income;

    private Integer needGet;

    private Integer needPay;

//    private Byte state;
    
    private JieSuanState dbState;
    
    private String formatState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        setFormatStartTime(DateUtil.getDateTimeStr(startTime));
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        setFormatEndTime(DateUtil.getDateTimeStr(endTime));
    }

    private void setFormatStartTime(String formatStartTime) {
		this.formatStartTime = formatStartTime;
	}

    private void setFormatEndTime(String formatEndTime) {
		this.formatEndTime = formatEndTime;
	}
    
    public String getFormatStartTime(){
    	return formatStartTime;
    }
    
    public String getFormatEndTime(){
    	return formatEndTime;
    }

	public Integer getOfflineYye() {
		return offlineYye;
	}

	public void setOfflineYye(Integer offlineYye) {
		this.offlineYye = offlineYye;
	}

	public Integer getOnlineYye() {
		return onlineYye;
	}

	public void setOnlineYye(Integer onlineYye) {
		this.onlineYye = onlineYye;
	}

	public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getNeedGet() {
        return needGet;
    }

    public void setNeedGet(Integer needGet) {
        this.needGet = needGet;
    }

    public Integer getNeedPay() {
        return needPay;
    }

    public void setNeedPay(Integer needPay) {
        this.needPay = needPay;
    }

    public Byte getState() {
        return getDbState() == null ? null : getDbState().getDbCode();
    }

    public void setState(Byte state) {
//        this.state = state;
    	this.dbState = JieSuanState.getByDbCode(state);
    	setFormatState(this.dbState == null ? null : this.dbState.getDesc());
    }
    
    public String getFormatState(){
    	return formatState;
    }
    
    private void setFormatState(String formatState) {
		this.formatState = formatState;
	}

	public JieSuanState getDbState() {
		return dbState;
	}

	public void setDbState(JieSuanState dbState) {
		this.dbState = dbState;
		setFormatState(dbState == null ? null : dbState.getDesc());
	}

	@Override
    public Integer getPK() {
        return id;
    }
}