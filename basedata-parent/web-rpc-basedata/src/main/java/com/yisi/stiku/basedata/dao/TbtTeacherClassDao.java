package com.yisi.stiku.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.entity.TbtTeacherClass;
import com.yisi.stiku.db.dao.BaseDao;

@Repository
public interface TbtTeacherClassDao extends BaseDao<Long, TbtTeacherClass> {

	int updateByPrimaryKeyWithBLOBs(TbtTeacherClass record);

	void deleteByTeacherIdAndClassIds(@Param("teacherId") Long teacherId, @Param("classIds") List<Long> classIds);

	void deleteByTeacherId(@Param("teacherId") Long teacherId);
}