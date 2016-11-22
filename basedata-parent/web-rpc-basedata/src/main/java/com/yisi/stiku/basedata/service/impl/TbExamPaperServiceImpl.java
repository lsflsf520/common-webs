package com.yisi.stiku.basedata.service.impl;

import java.util.List;

import com.yisi.stiku.basedata.dao.impl.TbExamPaperDaoImpl;
import com.yisi.stiku.basedata.entity.TbExamPaper;
import com.yisi.stiku.basedata.entity.TbExamPaperProm;
import com.yisi.stiku.basedata.rpc.service.DataPrivRpcService;
import com.yisi.stiku.basedata.rpc.service.ExamPaperRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
@RpcService(ExamPaperRpcService.class)
public class TbExamPaperServiceImpl extends BaseServiceImpl<Long, TbExamPaper> implements ExamPaperRpcService{
    @Resource
    private TbExamPaperDaoImpl tbExamPaperDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbExamPaper> getBaseDaoImpl() {
        return tbExamPaperDaoImpl;
    }

	@Override
	public List<TbExamPaper> findBySchoolId(long schoolId) {
		TbExamPaper exampaper = new TbExamPaper();
		exampaper.setSchoolId(schoolId);
		return tbExamPaperDaoImpl.findByEntity(exampaper);
	}


	@Override
	public TbExamPaper findByPaperId(Long paperId) {
		TbExamPaper tbExamPaper = new TbExamPaper();
		tbExamPaper.setId(paperId);
		List<TbExamPaper> tbExamPaperList = tbExamPaperDaoImpl.findByEntity(tbExamPaper);
		if(tbExamPaperList!=null && tbExamPaperList.size()==1 ) {
			return tbExamPaperList.get(0);
		}else {
			return new TbExamPaper();
		}
	}
	
	@Override
	public List<TbExamPaper> findAll(String whereSql,String orderBySql) {
		return this.tbExamPaperDaoImpl.findAll(whereSql, orderBySql);
	}
	
}