package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import java.util.Date;

public class TbProblemContent extends BaseEntity<Long> {
    private Long id;

    private Short type;

    private String problemContent;

    private String problemContentMathml;

    private String problemContentOoxml;

    private String answerContent;

    private String answerContentMathml;

    private String answerContentOoxml;

    private String aContent;

    private String aContentMathml;

    private String aContentOoxml;

    private String bContent;

    private String bContentMathml;

    private String bContentOoxml;

    private String cContent;

    private String cContentMathml;

    private String cContentOoxml;

    private String dContent;

    private String dContentMathml;

    private String dContentOoxml;

    private Long sequence;

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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getProblemContent() {
        return problemContent;
    }

    public void setProblemContent(String problemContent) {
        this.problemContent = problemContent == null ? null : problemContent.trim();
    }

    public String getProblemContentMathml() {
        return problemContentMathml;
    }

    public void setProblemContentMathml(String problemContentMathml) {
        this.problemContentMathml = problemContentMathml == null ? null : problemContentMathml.trim();
    }

    public String getProblemContentOoxml() {
        return problemContentOoxml;
    }

    public void setProblemContentOoxml(String problemContentOoxml) {
        this.problemContentOoxml = problemContentOoxml == null ? null : problemContentOoxml.trim();
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent == null ? null : answerContent.trim();
    }

    public String getAnswerContentMathml() {
        return answerContentMathml;
    }

    public void setAnswerContentMathml(String answerContentMathml) {
        this.answerContentMathml = answerContentMathml == null ? null : answerContentMathml.trim();
    }

    public String getAnswerContentOoxml() {
        return answerContentOoxml;
    }

    public void setAnswerContentOoxml(String answerContentOoxml) {
        this.answerContentOoxml = answerContentOoxml == null ? null : answerContentOoxml.trim();
    }

    public String getaContent() {
        return aContent;
    }

    public void setaContent(String aContent) {
        this.aContent = aContent == null ? null : aContent.trim();
    }

    public String getaContentMathml() {
        return aContentMathml;
    }

    public void setaContentMathml(String aContentMathml) {
        this.aContentMathml = aContentMathml == null ? null : aContentMathml.trim();
    }

    public String getaContentOoxml() {
        return aContentOoxml;
    }

    public void setaContentOoxml(String aContentOoxml) {
        this.aContentOoxml = aContentOoxml == null ? null : aContentOoxml.trim();
    }

    public String getbContent() {
        return bContent;
    }

    public void setbContent(String bContent) {
        this.bContent = bContent == null ? null : bContent.trim();
    }

    public String getbContentMathml() {
        return bContentMathml;
    }

    public void setbContentMathml(String bContentMathml) {
        this.bContentMathml = bContentMathml == null ? null : bContentMathml.trim();
    }

    public String getbContentOoxml() {
        return bContentOoxml;
    }

    public void setbContentOoxml(String bContentOoxml) {
        this.bContentOoxml = bContentOoxml == null ? null : bContentOoxml.trim();
    }

    public String getcContent() {
        return cContent;
    }

    public void setcContent(String cContent) {
        this.cContent = cContent == null ? null : cContent.trim();
    }

    public String getcContentMathml() {
        return cContentMathml;
    }

    public void setcContentMathml(String cContentMathml) {
        this.cContentMathml = cContentMathml == null ? null : cContentMathml.trim();
    }

    public String getcContentOoxml() {
        return cContentOoxml;
    }

    public void setcContentOoxml(String cContentOoxml) {
        this.cContentOoxml = cContentOoxml == null ? null : cContentOoxml.trim();
    }

    public String getdContent() {
        return dContent;
    }

    public void setdContent(String dContent) {
        this.dContent = dContent == null ? null : dContent.trim();
    }

    public String getdContentMathml() {
        return dContentMathml;
    }

    public void setdContentMathml(String dContentMathml) {
        this.dContentMathml = dContentMathml == null ? null : dContentMathml.trim();
    }

    public String getdContentOoxml() {
        return dContentOoxml;
    }

    public void setdContentOoxml(String dContentOoxml) {
        this.dContentOoxml = dContentOoxml == null ? null : dContentOoxml.trim();
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
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