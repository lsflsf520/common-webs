package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;

public class TbExtendPropertyFinal extends BaseEntity<Long> {
    private Long id;

    private Long propertyId;

    private String value;

    private Long managerId;

    private Long sourceId;

    private String tbSourceName;

    private Integer isApproved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getTbSourceName() {
        return tbSourceName;
    }

    public void setTbSourceName(String tbSourceName) {
        this.tbSourceName = tbSourceName == null ? null : tbSourceName.trim();
    }

    public Integer getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Integer isApproved) {
        this.isApproved = isApproved;
    }

    @Override
    public Long getPK() {
        return id;
    }
}