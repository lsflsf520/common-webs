package com.yisi.stiku.basedata.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.entity.TbStudent;
import com.yisi.stiku.db.dao.BaseDao;

@Repository
public interface TbStudentDao extends BaseDao<Long, TbStudent> {

	public List<TbStudent> getStudentByClassId(@Param("classId") long classId);
}