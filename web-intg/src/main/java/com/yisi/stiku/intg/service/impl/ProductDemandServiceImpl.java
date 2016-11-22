package com.yisi.stiku.intg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.intg.dao.impl.ProductDemandDaoImpl;
import com.yisi.stiku.intg.entity.ProductDemand;

@Service
public class ProductDemandServiceImpl extends BaseServiceImpl<Integer, ProductDemand> {

	@Resource
	private ProductDemandDaoImpl productDemandDaoImpl;

	@Override
	protected BaseDaoImpl<Integer, ProductDemand> getBaseDaoImpl() {

		return productDemandDaoImpl;
	}

	public List<ProductDemand> listAvailProductDemand(String projectName, String moduleName) {

		return productDemandDaoImpl.listAvailProductDemand(projectName, moduleName);
	}

	public List<ProductDemand> listNotRelateDemand() {

		return productDemandDaoImpl.listNotRelateDemand();
	}

	public void updateProductStatus(int parentId, byte status) {

		productDemandDaoImpl.updateProductStatus(parentId, status);
	}

}