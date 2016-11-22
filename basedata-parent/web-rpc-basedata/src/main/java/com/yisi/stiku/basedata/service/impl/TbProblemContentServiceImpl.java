package com.yisi.stiku.basedata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.basedata.dao.impl.TbProblemContentDaoImpl;
import com.yisi.stiku.basedata.entity.TbProblemContent;
import com.yisi.stiku.basedata.rpc.service.ProblemContentRpcService;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.rpc.annotation.RpcService;

@Service
@RpcService(ProblemContentRpcService.class)
public class TbProblemContentServiceImpl extends BaseServiceImpl<Long, TbProblemContent> implements ProblemContentRpcService {

	@Resource
	private TbProblemContentDaoImpl tbProblemContentDaoImpl;

	@Override
	protected BaseDaoImpl<Long, TbProblemContent> getBaseDaoImpl() {

		return tbProblemContentDaoImpl;
	}

	public List<TbProblemContent> getProblemContentsByProblemIds(List<Long> pids) {

		return tbProblemContentDaoImpl.getProblemContentsByProblemIds(pids);
	}

	@Override
	public List<TbProblemContent> getProblemContentsBySeq(List<Long> seqs) {

		return tbProblemContentDaoImpl.getProblemContentsBySeq(seqs);
	}

	@Override
	public List<Long> getProblemIds(List<Long> seqs) {

		List<Long> pIds = new ArrayList<Long>();
		List<TbProblemContent> pcList = getProblemContentsBySeq(seqs);
		if (pcList != null && !pcList.isEmpty()) {
			for (TbProblemContent pc : pcList) {
				pIds.add(pc.getId());
			}
		}
		return pIds;
	}

	@Override
	public Map<Long, Long> getSeq2ProblemIdMap(List<Long> seqs) {

		Map<Long, Long> seq2ProblemIdMap = new HashMap<Long, Long>();
		List<TbProblemContent> pcList = getProblemContentsBySeq(seqs);
		if (pcList != null && !pcList.isEmpty()) {
			for (TbProblemContent pc : pcList) {
				seq2ProblemIdMap.put(pc.getSequence(), pc.getId());
			}
		}

		return seq2ProblemIdMap;
	}

}