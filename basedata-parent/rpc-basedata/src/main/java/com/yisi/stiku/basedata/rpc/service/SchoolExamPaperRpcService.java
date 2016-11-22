package com.yisi.stiku.basedata.rpc.service;

import java.util.Date;
import java.util.List;

import com.yisi.stiku.basedata.entity.SchoolExamPaperVO;
import com.yisi.stiku.basedata.entity.TbSchoolExamPaper;

public interface SchoolExamPaperRpcService {

	List<TbSchoolExamPaper> findBySchoolId(long schoolId, String useType);

	List<TbSchoolExamPaper> findBycreatedDtAndSchoolId(Long schoolId,
			String useType, Date createdDt);
	
	public List<TbSchoolExamPaper> findListBySchooIdAreaId(long schoolId, long areaId);
	
	List<SchoolExamPaperVO> findBySchoolIdAndAreaId(Long schoolId,Long areaId,int gradeYear);
	
	List<SchoolExamPaperVO> findBySchoolIdAndAreaId(Long schoolId,Long areaId,int gradeYear,Integer startIndex, Integer pagesize);

}