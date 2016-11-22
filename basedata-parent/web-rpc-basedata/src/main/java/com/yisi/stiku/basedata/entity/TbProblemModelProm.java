package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import java.util.Date;

public class TbProblemModelProm extends BaseEntity<Long> {
    private Long id;

    private Long problemPrepId;

    private Long problemId;

    private Long problemSequence;

    private Long problemModelId;

    private Integer problemIndex;

    private String aclCode;

    private String aclType;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProblemPrepId() {
        return problemPrepId;
    }

    public void setProblemPrepId(Long problemPrepId) {
        this.problemPrepId = problemPrepId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getProblemSequence() {
        return problemSequence;
    }

    public void setProblemSequence(Long problemSequence) {
        this.problemSequence = problemSequence;
    }

    public Long getProblemModelId() {
        return problemModelId;
    }

    public void setProblemModelId(Long problemModelId) {
        this.problemModelId = problemModelId;
    }

    public Integer getProblemIndex() {
        return problemIndex;
    }

    public void setProblemIndex(Integer problemIndex) {
        this.problemIndex = problemIndex;
    }

    public String getAclCode() {
        return aclCode;
    }

    public void setAclCode(String aclCode) {
        this.aclCode = aclCode == null ? null : aclCode.trim();
    }

    public String getAclType() {
        return aclType;
    }

    public void setAclType(String aclType) {
        this.aclType = aclType == null ? null : aclType.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Long getPK() {
        return id;
    }
}