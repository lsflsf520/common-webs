package com.yisi.stiku.basedata.entity;

import com.yisi.stiku.common.bean.BaseEntity;

public class TbExtendProperty extends BaseEntity<Long> {
    private Long id;

    private String name;

    private String type;

    private String tbName;

    private String tbRefer;

    private String showName;

    private String convertClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName == null ? null : tbName.trim();
    }

    public String getTbRefer() {
        return tbRefer;
    }

    public void setTbRefer(String tbRefer) {
        this.tbRefer = tbRefer == null ? null : tbRefer.trim();
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
    }

    public String getConvertClass() {
        return convertClass;
    }

    public void setConvertClass(String convertClass) {
        this.convertClass = convertClass == null ? null : convertClass.trim();
    }

    @Override
    public Long getPK() {
        return id;
    }
}