package com.yisi.stiku.wallet.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.common.bean.BaseEntity;
import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.wallet.rpc.constant.JieSuanState;
import com.yisi.stiku.wallet.rpc.constant.ResFromType;
import com.yisi.stiku.wallet.rpc.constant.ResType;

public class ResourceInfo extends BaseEntity<Integer> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String resId;

    private String resName;

//    private Byte resType;
//
//    private Byte resFrom;
    
    private ResType dbResType;
    
    private ResFromType dbResFromType;

    private Date createTime;

    private String effectParser;

    private String feature;

//    private Byte state;
    
    private EntityState dbState;

    private Long creatorId;

    private String remark;

    private String creatorName;

    private Integer needMoney;

    private Integer needPoint;
    
    private Integer splitRatio;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public Byte getResType() {
        return getDbResType() == null ? null : getDbResType().getDbCode();
    }

    public void setResType(Byte resType) {
//        this.resType = resType;
    	this.dbResType = ResType.getByDbCode(resType);
    }

    public Byte getResFrom() {
        return getDbResFromType() == null ? null : getDbResFromType().getDbCode();
    }

    public void setResFrom(Byte resFrom) {
//        this.resFrom = resFrom;
        this.dbResFromType = ResFromType.getByDbCode(resFrom);
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEffectParser() {
		return StringUtils.isNotBlank(effectParser) ? effectParser.trim() : null;
	}

	public void setEffectParser(String effectParser) {
		this.effectParser = effectParser;
	}

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature == null ? null : feature.trim();
    }

    public Byte getState() {
        return getDbState() == null ? null : getDbState().getDbCode();
    }

    public void setState(Byte state) {
//        this.state = state;
    	this.dbState = EntityState.getByDbCode(state);
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public Integer getNeedMoney() {
        return needMoney == null ? 0 : needMoney;
    }

    public void setNeedMoney(Integer needMoney) {
        this.needMoney = needMoney;
    }

    public Integer getNeedPoint() {
        return needPoint == null ? 0 : needPoint;
    }

    public void setNeedPoint(Integer needPoint) {
        this.needPoint = needPoint;
    }

    @Override
    public Integer getPK() {
        return id;
    }
    
    public ResType getDbResType() {
		return dbResType;
	}

	public void setDbResType(ResType dbResType) {
		this.dbResType = dbResType;
	}

	public ResFromType getDbResFromType() {
		return dbResFromType;
	}

	public void setDbResFromType(ResFromType dbResFromType) {
		this.dbResFromType = dbResFromType;
	}

	public EntityState getDbState() {
		return dbState;
	}

	public void setDbState(EntityState dbState) {
		this.dbState = dbState;
	}

	
	public Integer getSplitRatio() {
		splitRatio = (splitRatio == null ? JieSuanState.SPLIT_RATIO : splitRatio);
		
		if(splitRatio < 0 || splitRatio > 100){
			throw new IllegalStateException("资源的分成比例只能在[0~100]之间的整数");
		}
		
		return splitRatio;
	}

	public void setSplitRatio(Integer splitRatio) {
		this.splitRatio = splitRatio;
	}
	
}