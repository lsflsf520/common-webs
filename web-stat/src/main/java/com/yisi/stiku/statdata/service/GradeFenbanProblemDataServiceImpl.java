package com.yisi.stiku.statdata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.statdata.dao.impl.GradeFenbanProblemDataDaoImpl;
import com.yisi.stiku.statdata.entity.GradeFenbanProblemData;

@Service
public class GradeFenbanProblemDataServiceImpl extends BaseServiceImpl<Integer, GradeFenbanProblemData> {

	@Resource
	private GradeFenbanProblemDataDaoImpl gradeFenbanProblemDataDaoImpl;

	@Override
	protected BaseDaoImpl<Integer, GradeFenbanProblemData> getBaseDaoImpl() {

		return gradeFenbanProblemDataDaoImpl;
	}
}