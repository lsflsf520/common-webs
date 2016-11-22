package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;

public class TbVideoZhenduan extends BaseEntity<Long> {
    private Long id;

    private String url;

    private Integer flag;

    private Long problemId;

    private Long pointId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    @Override
    public Long getPK() {
        return id;
    }
}