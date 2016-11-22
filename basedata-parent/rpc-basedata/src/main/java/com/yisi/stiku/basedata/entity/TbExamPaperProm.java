package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import java.util.Date;

public class TbExamPaperProm extends BaseEntity<Long> {
    private Long id;

    private Long problemId;

    private Long problemPrepId;

    private Integer problemNo;

    private Long parentId;

    private Integer type;

    private Long examPaperId;

    private Short isleaf;

    private Integer score;

    private Integer mainProblemNo;

    private Integer subProblemNo;

    private Integer groupNo;

    private Integer groupSubNo;

    private Integer groupCount;

    private Integer groupSubCount;

    private String aclCode;

    private String aclType;

    private String createdBy;

    private Date createdDt;

    private String updatedBy;

    private Date updatedDt;

    private Integer version;

    private Boolean isLeaf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getProblemPrepId() {
        return problemPrepId;
    }

    public void setProblemPrepId(Long problemPrepId) {
        this.problemPrepId = problemPrepId;
    }

    public Integer getProblemNo() {
        return problemNo;
    }

    public void setProblemNo(Integer problemNo) {
        this.problemNo = problemNo;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getExamPaperId() {
        return examPaperId;
    }

    public void setExamPaperId(Long examPaperId) {
        this.examPaperId = examPaperId;
    }

    public Short getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Short isleaf) {
        this.isleaf = isleaf;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getMainProblemNo() {
        return mainProblemNo;
    }

    public void setMainProblemNo(Integer mainProblemNo) {
        this.mainProblemNo = mainProblemNo;
    }

    public Integer getSubProblemNo() {
        return subProblemNo;
    }

    public void setSubProblemNo(Integer subProblemNo) {
        this.subProblemNo = subProblemNo;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getGroupSubNo() {
        return groupSubNo;
    }

    public void setGroupSubNo(Integer groupSubNo) {
        this.groupSubNo = groupSubNo;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getGroupSubCount() {
        return groupSubCount;
    }

    public void setGroupSubCount(Integer groupSubCount) {
        this.groupSubCount = groupSubCount;
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

    public Boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    @Override
    public Long getPK() {
        return id;
    }
}