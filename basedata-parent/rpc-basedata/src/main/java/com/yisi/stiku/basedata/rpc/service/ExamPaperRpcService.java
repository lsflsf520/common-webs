package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import com.yisi.stiku.basedata.entity.TbExamPaper;
import com.yisi.stiku.basedata.entity.TbExamPaperProm;

public interface ExamPaperRpcService {

	List<TbExamPaper> findBySchoolId(long schoolId);
	
	
	/**
	 * 根据试卷Id获取试卷信息
	 * @param paperId
	 * @return
	 */
	TbExamPaper findByPaperId(Long paperId);
	
	List<TbExamPaper> findAll(String whereSql,String orderBySql);
	
}
