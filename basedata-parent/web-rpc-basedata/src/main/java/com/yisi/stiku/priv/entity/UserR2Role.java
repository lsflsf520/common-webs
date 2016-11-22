package com.yisi.stiku.priv.entity;

import com.yisi.stiku.common.bean.BaseEntity;

public class UserR2Role extends BaseEntity<Integer> {
    private Integer id;

    private Long userId;

    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public Integer getPK() {
        return id;
    }
}