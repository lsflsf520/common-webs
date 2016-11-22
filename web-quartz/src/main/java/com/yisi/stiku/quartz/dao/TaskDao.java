package com.yisi.stiku.quartz.dao;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.quartz.dao.entity.TaskEntity;

@Repository
public interface TaskDao extends BaseDao<Integer, TaskEntity> {

}
