package com.yisi.stiku.basedata.service.impl;

import java.util.List;

import com.yisi.stiku.basedata.dao.impl.TbExamPaperPromDaoImpl;
import com.yisi.stiku.basedata.entity.TbExamPaperProm;
import com.yisi.stiku.basedata.rpc.service.ExamPaperPromRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
@RpcService(ExamPaperPromRpcService.class)
public class TbExamPaperPromServiceImpl extends BaseServiceImpl<Long, TbExamPaperProm> implements ExamPaperPromRpcService{
    @Resource
    private TbExamPaperPromDaoImpl tbExamPaperPromDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbExamPaperProm> getBaseDaoImpl() {
        return tbExamPaperPromDaoImpl;
    }

	@Override
	public TbExamPaperProm getPaperProblem(Long paperId, Integer problemOrder) {
		TbExamPaperProm tbExamPaperProm = new TbExamPaperProm();
		tbExamPaperProm.setExamPaperId(paperId);
		tbExamPaperProm.setProblemNo(problemOrder);
		List<TbExamPaperProm> tbExamPaperPromList =  tbExamPaperPromDaoImpl.findByEntity(tbExamPaperProm);
		if(tbExamPaperPromList!=null && tbExamPaperPromList.size()==1) {
			return tbExamPaperPromList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<TbExamPaperProm> getExamPaperPromList(Long paperId) {
		TbExamPaperProm tbExamPaperProm = new TbExamPaperProm();
		tbExamPaperProm.setExamPaperId(paperId);
		String orderBySql = " and problem_no is not null ORDER BY problem_no";
		List<TbExamPaperProm> tbExamPaperPromList =  tbExamPaperPromDaoImpl.findByEntity(tbExamPaperProm,orderBySql);
		if(tbExamPaperPromList!=null && tbExamPaperPromList.size()>0) {
			return tbExamPaperPromList;
		}else{
			return null;
		}
	}
}