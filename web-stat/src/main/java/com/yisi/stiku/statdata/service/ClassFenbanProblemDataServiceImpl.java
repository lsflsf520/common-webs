package com.yisi.stiku.statdata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.statdata.dao.impl.ClassFenbanProblemDataDaoImpl;
import com.yisi.stiku.statdata.entity.ClassFenbanProblemData;

@Service
public class ClassFenbanProblemDataServiceImpl extends BaseServiceImpl<Integer, ClassFenbanProblemData> {

	@Resource
	private ClassFenbanProblemDataDaoImpl classFenbanProblemDataDaoImpl;

	@Override
	protected BaseDaoImpl<Integer, ClassFenbanProblemData> getBaseDaoImpl() {

		return classFenbanProblemDataDaoImpl;
	}
}