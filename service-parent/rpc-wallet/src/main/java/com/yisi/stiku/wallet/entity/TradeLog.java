package com.yisi.stiku.wallet.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.wallet.rpc.constant.PayChannel;
import com.yisi.stiku.wallet.rpc.constant.TradeType;

import java.util.Date;

public class TradeLog extends BaseEntity<Long> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Integer balance;

    private Integer point;

//    private Byte channel;
//
//    private Byte tradeType;
    private String formatChannel;
    private PayChannel dbChannel;

    private TradeType dbTradeType;
    private String formatTradeType;
    
    private EntityState dbState;

    private Integer remainBalance;

    private Integer remainPoint;

    private Long targetUserId;

    private String targetUserName;

    private Long srcUserId;

    private String srcUserName;

    private String remark;

    private Date createTime;
    
    private String formatCreateTime;
    
    private Integer splitRatio;
    
    private Integer resInfoId;

//    private Byte state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Byte getChannel() {
        return getDbChannel() == null ? null : getDbChannel().getDbCode();
    }
    
    public String getFormatChannel(){
    	return formatChannel;
    }

    public void setFormatChannel(String formatChannel) {
		this.formatChannel = formatChannel;
	}

	public void setChannel(Byte channel) {
//        this.channel = channel;
    	if(channel != null){
    		this.dbChannel = PayChannel.getByDbCode(channel);
    		setFormatChannel(this.dbChannel.getDesc());
    	}
    }

    public Byte getTradeType() {
        return getDbTradeType() == null ? null : getDbTradeType().getDbCode();
    }
    
    public String getFormatTradeType(){
    	return this.formatTradeType;
    }

    public void setTradeType(Byte tradeType) {
//        this.tradeType = tradeType;
    	if(tradeType != null){
    		this.dbTradeType = TradeType.getByDbCode(tradeType);
    		setFormatTradeType(dbTradeType == null ? null : dbTradeType.getDesc());
    	}
    }

    public Integer getRemainBalance() {
        return remainBalance;
    }

    public void setRemainBalance(Integer remainBalance) {
        this.remainBalance = remainBalance;
    }

    public Integer getRemainPoint() {
        return remainPoint;
    }

    public void setRemainPoint(Integer remainPoint) {
        this.remainPoint = remainPoint;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName == null ? null : targetUserName.trim();
    }

    public Long getSrcUserId() {
        return srcUserId;
    }

    public void setSrcUserId(Long srcUserId) {
        this.srcUserId = srcUserId;
    }

    public String getSrcUserName() {
        return srcUserName;
    }

    public void setSrcUserName(String srcUserName) {
        this.srcUserName = srcUserName == null ? null : srcUserName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getFormatCreateTime() {
		return formatCreateTime;
	}

	public void setFormatCreateTime(String formatCreateTime) {
		this.formatCreateTime = formatCreateTime;
	}

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        setFormatCreateTime(DateUtil.getDateTimeStr(createTime));
    }

    public Byte getState() {
        return getDbState() == null ? null : getDbState().getDbCode();
    }

    public void setState(Byte state) {
//        this.state = state;
    	if(state != null){
    		this.dbState = EntityState.getByDbCode(state);
    	}
    }

    public PayChannel getDbChannel() {
		return dbChannel;
	}

	public void setDbChannel(PayChannel dbChannel) {
		this.dbChannel = dbChannel;
		setFormatChannel(this.dbChannel.getDesc());
	}

	public TradeType getDbTradeType() {
		return dbTradeType;
	}

	public void setDbTradeType(TradeType dbTradeType) {
		this.dbTradeType = dbTradeType;
		setFormatTradeType(dbTradeType == null ? null : dbTradeType.getDesc());
	}

	public void setFormatTradeType(String formatTradeType) {
		this.formatTradeType = formatTradeType;
	}

	public Integer getSplitRatio() {
		return splitRatio;
	}

	public void setSplitRatio(Integer splitRatio) {
		this.splitRatio = splitRatio;
	}

	public EntityState getDbState() {
		return dbState;
	}

	public void setDbState(EntityState dbState) {
		this.dbState = dbState;
	}
	
	public Integer getResInfoId() {
		return resInfoId;
	}

	public void setResInfoId(Integer resInfoId) {
		this.resInfoId = resInfoId;
	}

	@Override
    public Long getPK() {
        return id;
    }
}