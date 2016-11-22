package com.yisi.stiku.intg.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.intg.dao.ProductDemandDao;
import com.yisi.stiku.intg.entity.ProductDemand;

@Repository
public class ProductDemandDaoImpl extends BaseDaoImpl<Integer, ProductDemand> {

	@Resource
	private ProductDemandDao productDemandDao;

	@Override
	protected BaseDao<Integer, ProductDemand> getProxyBaseDao() {

		return productDemandDao;
	}

	public List<ProductDemand> listAvailProductDemand(String projectName, String moduleName) {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("projectName", StringUtils.isNotBlank(projectName) ? projectName : null);
		paramMap.put("moduleName", StringUtils.isNotBlank(moduleName) ? moduleName : null);
		return this.getSqlSessionTemplate().selectList(this.getMapperNamespace() + ".listAvailProductDemand", paramMap);
	}

	public List<ProductDemand> listNotRelateDemand() {

		return this.getSqlSessionTemplate().selectList(this.getMapperNamespace() + ".listNotRelateDemand");
	}

	public void updateProductStatus(int parentId, byte status) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("parentId", parentId);
		paramMap.put("status", status);

		this.getSqlSessionTemplate().update(this.getMapperNamespace() + ".updateProductStatus", paramMap);
	}
}