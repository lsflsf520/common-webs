package com.yisi.stiku.basedata.dao;

import java.util.Date;
import java.util.List;

import com.yisi.stiku.basedata.entity.TbSchoolExamPaper;
import com.yisi.stiku.db.dao.BaseDao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TbSchoolExamPaperDao extends BaseDao<Long, TbSchoolExamPaper> {

	List<TbSchoolExamPaper> finfindByCreateDtAndSchoolID(@Param("schoolId")long schoolId,
			@Param("useType")String useType,@Param("createdDt") Date createdDt);
	
	List<TbSchoolExamPaper> findListBySchooIdAreaId(@Param("schoolId")long schoolId,@Param("areaId")long areaId);

}