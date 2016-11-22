package com.yisi.stiku.quartz.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.quartz.dao.TaskDao;
import com.yisi.stiku.quartz.dao.entity.TaskEntity;

@Repository
public class TaskDaoImpl extends BaseDaoImpl<Integer, TaskEntity> implements
		TaskDao {

	@Resource
	private TaskDao taskDao;

	@Override
	protected BaseDao<Integer, TaskEntity> getProxyBaseDao() {
		return taskDao;
	}

}
