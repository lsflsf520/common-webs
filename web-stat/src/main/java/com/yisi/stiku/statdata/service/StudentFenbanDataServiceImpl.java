package com.yisi.stiku.statdata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.statdata.dao.impl.StudentFenbanDataDaoImpl;
import com.yisi.stiku.statdata.entity.StudentFenbanData;

@Service
public class StudentFenbanDataServiceImpl extends BaseServiceImpl<Integer, StudentFenbanData> {

	@Resource
	private StudentFenbanDataDaoImpl studentFenbanDataDaoImpl;

	@Override
	protected BaseDaoImpl<Integer, StudentFenbanData> getBaseDaoImpl() {

		return studentFenbanDataDaoImpl;
	}
}