package com.yisi.stiku.basedata.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.entity.TbClass;
import com.yisi.stiku.db.dao.BaseDao;

@Repository
public interface TbClassDao extends BaseDao<Long, TbClass> {

	List<TbClass> getNameBytype(@Param("name") String name, @Param("type") int type, @Param("gradeYear") int gradeYear,
			@Param("schoolId") long schoolId);

}