package com.yisi.stiku.basedata.rpc.service;

import java.util.List;

import com.yisi.stiku.basedata.entity.TbExamPaperProm;

public interface ExamPaperPromRpcService {

	TbExamPaperProm getPaperProblem(Long paperId,Integer problemOrder);

	List<TbExamPaperProm> getExamPaperPromList(Long paperId);
}
