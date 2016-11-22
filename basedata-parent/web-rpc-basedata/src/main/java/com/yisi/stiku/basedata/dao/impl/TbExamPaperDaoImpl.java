package com.yisi.stiku.basedata.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yisi.stiku.basedata.dao.TbExamPaperDao;
import com.yisi.stiku.basedata.entity.TbExamPaper;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

@Repository
public class TbExamPaperDaoImpl extends BaseDaoImpl<Long, TbExamPaper> {
    @Resource
    private TbExamPaperDao tbExamPaperDao;

    @Override
    protected BaseDao<Long, TbExamPaper> getProxyBaseDao() {
        return tbExamPaperDao;
    }
    
    public List<TbExamPaper> findAll(String whereSql,String orderBySql){
    	Map paramMap = new HashMap();
    	paramMap.put(whereSql, whereSql);
    	paramMap.put(orderBySql, orderBySql);
    	return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".findAll", paramMap);
    }
}