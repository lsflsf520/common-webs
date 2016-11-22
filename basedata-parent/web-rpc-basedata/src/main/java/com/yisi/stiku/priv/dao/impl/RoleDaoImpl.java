package com.yisi.stiku.priv.dao.impl;

import java.util.List;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.priv.dao.RoleDao;
import com.yisi.stiku.priv.entity.Role;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Integer, Role> {
    @Resource
    private RoleDao roleDao;

    @Override
    protected BaseDao<Integer, Role> getProxyBaseDao() {
        return roleDao;
    }
    
    /**
     * 
     * @param userId
     * @return
     */
    public List<Role> findByUserId(long userId){
    	return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".findByUserId", userId);
    }
    
    /**
     * 
     * @return 获取可以用于设置用户类型的角色
     */
    public List<Role> findUserRole(){
    	return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".findUserRole");
    }
    
}