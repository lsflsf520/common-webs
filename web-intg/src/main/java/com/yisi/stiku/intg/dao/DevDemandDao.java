package com.yisi.stiku.intg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yisi.stiku.db.dao.BaseDao;
import com.yisi.stiku.intg.entity.DevDemand;

@Repository
public interface DevDemandDao extends BaseDao<Integer, DevDemand> {

	public void upDemandStatus(@Param("demandIds") List<Integer> demandIds, @Param("status") byte status);

	public List<DevDemand> listDemandByIds(@Param("demandIds") List<Integer> demandIds);

	public List<DevDemand> listParentDemand(@Param("creatorId") int creatorId);

	public List<DevDemand> listChildDemand(@Param("parentId") int parentId);

	public DevDemand getAvailDemandByRepoAndBranch(@Param("repoName") String repoName, @Param("branchName") String branchName);

	public List<DevDemand> listRepoDemand(@Param("repoName") String repoName,
			@Param("demandIdList") List<Integer> demandIdList);

}