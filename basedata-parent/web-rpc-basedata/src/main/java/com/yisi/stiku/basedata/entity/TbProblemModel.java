package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import java.util.Date;

public class TbProblemModel extends BaseEntity<Long> {
    private Long id;

    private Integer scoreSegment;

    private String detailPointId;

    private Integer problemCount;

    private Integer problemType;

    private Integer stype;

    private String name;

    private String code;

    private Long groupPropertyId;

    private Integer pointCount;

    private Integer pointPCount;

    private Integer domainPointCount;

    private String totalScoreSegments;

    private Integer type;

    private String mainPointId;

    private String slaverPoints;

    private Integer enable;

    private Integer isNew;

    private String aclCode;

    private String aclType;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private Integer divisionType;

    private Integer modelType;

    private Integer pointpcount;

    private String mainDomainId;

    private String slaverDomainId;

    private byte[] sourceExamProm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScoreSegment() {
        return scoreSegment;
    }

    public void setScoreSegment(Integer scoreSegment) {
        this.scoreSegment = scoreSegment;
    }

    public String getDetailPointId() {
        return detailPointId;
    }

    public void setDetailPointId(String detailPointId) {
        this.detailPointId = detailPointId == null ? null : detailPointId.trim();
    }

    public Integer getProblemCount() {
        return problemCount;
    }

    public void setProblemCount(Integer problemCount) {
        this.problemCount = problemCount;
    }

    public Integer getProblemType() {
        return problemType;
    }

    public void setProblemType(Integer problemType) {
        this.problemType = problemType;
    }

    public Integer getStype() {
        return stype;
    }

    public void setStype(Integer stype) {
        this.stype = stype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Long getGroupPropertyId() {
        return groupPropertyId;
    }

    public void setGroupPropertyId(Long groupPropertyId) {
        this.groupPropertyId = groupPropertyId;
    }

    public Integer getPointCount() {
        return pointCount;
    }

    public void setPointCount(Integer pointCount) {
        this.pointCount = pointCount;
    }

    public Integer getPointPCount() {
        return pointPCount;
    }

    public void setPointPCount(Integer pointPCount) {
        this.pointPCount = pointPCount;
    }

    public Integer getDomainPointCount() {
        return domainPointCount;
    }

    public void setDomainPointCount(Integer domainPointCount) {
        this.domainPointCount = domainPointCount;
    }

    public String getTotalScoreSegments() {
        return totalScoreSegments;
    }

    public void setTotalScoreSegments(String totalScoreSegments) {
        this.totalScoreSegments = totalScoreSegments == null ? null : totalScoreSegments.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMainPointId() {
        return mainPointId;
    }

    public void setMainPointId(String mainPointId) {
        this.mainPointId = mainPointId == null ? null : mainPointId.trim();
    }

    public String getSlaverPoints() {
        return slaverPoints;
    }

    public void setSlaverPoints(String slaverPoints) {
        this.slaverPoints = slaverPoints == null ? null : slaverPoints.trim();
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
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

    public Integer getDivisionType() {
        return divisionType;
    }

    public void setDivisionType(Integer divisionType) {
        this.divisionType = divisionType;
    }

    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    public Integer getPointpcount() {
        return pointpcount;
    }

    public void setPointpcount(Integer pointpcount) {
        this.pointpcount = pointpcount;
    }

    public String getMainDomainId() {
        return mainDomainId;
    }

    public void setMainDomainId(String mainDomainId) {
        this.mainDomainId = mainDomainId == null ? null : mainDomainId.trim();
    }

    public String getSlaverDomainId() {
        return slaverDomainId;
    }

    public void setSlaverDomainId(String slaverDomainId) {
        this.slaverDomainId = slaverDomainId == null ? null : slaverDomainId.trim();
    }

    public byte[] getSourceExamProm() {
        return sourceExamProm;
    }

    public void setSourceExamProm(byte[] sourceExamProm) {
        this.sourceExamProm = sourceExamProm;
    }

    @Override
    public Long getPK() {
        return id;
    }
}