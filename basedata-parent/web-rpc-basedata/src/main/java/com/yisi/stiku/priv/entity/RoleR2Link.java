package com.yisi.stiku.priv.entity;

import com.yisi.stiku.common.bean.BaseEntity;

public class RoleR2Link extends BaseEntity<Integer> {
    private Integer id;

    private Integer roleId;

    private Integer linkId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    @Override
    public Integer getPK() {
        return id;
    }
}