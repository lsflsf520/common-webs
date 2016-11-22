package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;
import java.util.Date;

public class TbCard extends BaseEntity<String> {
    private String id;

    private String password;

    private Date activationTime;

    private Integer activationFlag;

    private Long openSubject;

    private Long studentId;

    private Integer logoutFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public Integer getActivationFlag() {
        return activationFlag;
    }

    public void setActivationFlag(Integer activationFlag) {
        this.activationFlag = activationFlag;
    }

    public Long getOpenSubject() {
        return openSubject;
    }

    public void setOpenSubject(Long openSubject) {
        this.openSubject = openSubject;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getLogoutFlag() {
        return logoutFlag;
    }

    public void setLogoutFlag(Integer logoutFlag) {
        this.logoutFlag = logoutFlag;
    }

    @Override
    public String getPK() {
        return id;
    }
}