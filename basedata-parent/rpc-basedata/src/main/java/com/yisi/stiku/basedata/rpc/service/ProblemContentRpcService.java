package com.yisi.stiku.basedata.rpc.service;

import java.util.List;
import java.util.Map;

import com.yisi.stiku.basedata.entity.TbProblemContent;

public interface ProblemContentRpcService {

	public List<TbProblemContent> getProblemContentsByProblemIds(List<Long> pids);

	public List<TbProblemContent> getProblemContentsBySeq(List<Long> seqs);

	public List<Long> getProblemIds(List<Long> seqs);

	public Map<Long /* sequence */, Long/* problemId */> getSeq2ProblemIdMap(List<Long> seqs);

}
