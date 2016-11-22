package com.yisi.stiku.basedata.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yisi.stiku.basedata.dao.TbProblemContentDao;
import com.yisi.stiku.basedata.entity.TbProblemContent;
import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;

@Repository
public class TbProblemContentDaoImpl extends BaseDaoImpl<Long, TbProblemContent> {

	@Resource
	private TbProblemContentDao tbProblemContentDao;

	@Override
	protected BaseDao<Long, TbProblemContent> getProxyBaseDao() {

		return tbProblemContentDao;
	}

	public List<TbProblemContent> getProblemContentsByProblemIds(List<Long> pids) {

		return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".getProblemContentsByProblemIds", pids);
	}

	public List<TbProblemContent> getProblemContentsBySeq(List<Long> seqs) {

		return this.getSqlSessionTemplate().selectList(getMapperNamespace() + ".getProblemContentsBySeq", seqs);
	}
}