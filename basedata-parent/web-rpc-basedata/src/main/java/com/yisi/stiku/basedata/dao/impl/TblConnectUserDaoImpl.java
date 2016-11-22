package com.yisi.stiku.basedata.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.yisi.stiku.basedata.dao.TblConnectUserDao;
import com.yisi.stiku.basedata.entity.TblConnectUser;
import com.yisi.stiku.basedata.rpc.constant.ThirdLoginType;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class TblConnectUserDaoImpl extends BaseDaoImpl<Integer, TblConnectUser> {
    @Resource
    private TblConnectUserDao tblConnectUserDao;

    @Override
    protected BaseDao<Integer, TblConnectUser> getProxyBaseDao() {
        return tblConnectUserDao;
    }
    
    /**
     * 
     * @param userId
     * @param loginType
     * @return 解绑
     */
    public boolean delete(long userId, ThirdLoginType loginType){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("userId", userId);
    	paramMap.put("source", loginType.name());
    	
    	return this.getSqlSessionTemplate().update(getMapperNamespace() + ".delete", paramMap) >= 0;
    }
}