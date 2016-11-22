package com.yisi.stiku.stat.loader.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.db.multi.SqlSessionTemplateFactory;

/**
 * @author shangfeng
 *
 */
@Repository
public class QueryDao {

	@Resource
	private SqlSessionTemplateFactory sqlSessionTemplateFactory;

	private SqlSessionTemplate getSqlSessionTemplate(String dsKey) {

		return sqlSessionTemplateFactory.getSqlSessionTemplate(dsKey);
	}

	public List<Map<String, Object>> queryData(String dsKey, String statement, Map<String, Object> paramMap) {

		return getSqlSessionTemplate(dsKey).selectList(statement, paramMap);
	}

}
