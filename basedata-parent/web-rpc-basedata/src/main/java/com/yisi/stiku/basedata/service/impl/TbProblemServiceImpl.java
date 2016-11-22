package com.yisi.stiku.basedata.service.impl;

import com.yisi.stiku.basedata.dao.impl.TbProblemDaoImpl;
import com.yisi.stiku.basedata.entity.TbProblem;
import com.yisi.stiku.basedata.rpc.service.ProblemRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
@RpcService(ProblemRpcService.class)
public class TbProblemServiceImpl extends BaseServiceImpl<Long, TbProblem> implements ProblemRpcService {
    @Resource
    private TbProblemDaoImpl tbProblemDaoImpl;

    @Override
    protected BaseDaoImpl<Long, TbProblem> getBaseDaoImpl() {
        return tbProblemDaoImpl;
    }

	@Override
	public TbProblem findTbProblemByProblemId(Long problemId) {
		return tbProblemDaoImpl.findByPK(problemId);
	}
}