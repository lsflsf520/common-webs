package com.yisi.stiku.priv.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.priv.dao.UserR2RoleDao;
import com.yisi.stiku.priv.entity.UserR2Role;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class UserR2RoleDaoImpl extends BaseDaoImpl<Integer, UserR2Role> {
    @Resource
    private UserR2RoleDao userR2RoleDao;
    
    @Override
    protected BaseDao<Integer, UserR2Role> getProxyBaseDao() {
        return userR2RoleDao;
    }
    
    public boolean deleteByUserId(long userId){
    	return this.getSqlSessionTemplate().delete(getMapperNamespace() + ".deleteByUserId", userId) >= 0;
    }
    
    public boolean deleteByRoleId(int roleId){
    	return this.getSqlSessionTemplate().delete(getMapperNamespace() + ".deleteByRoleId", roleId) >= 0;
    }
    
    public boolean deleteByUserAndRole(long userId, int roleId){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("userId", userId);
    	paramMap.put("roleId", roleId);
    	return this.getSqlSessionTemplate().delete(getMapperNamespace() + ".deleteByUserAndRole", paramMap) >= 0;
    }
    
    public boolean exist(long userId, int roleId){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("userId", userId);
    	paramMap.put("roleId", roleId);
    	Integer count = this.getSqlSessionTemplate().selectOne(getMapperNamespace() + ".exist", paramMap);
    	
    	return count != null && count > 0;
    }
    
}