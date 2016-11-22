package com.yisi.stiku.intg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.intg.constant.DmStatus;
import com.yisi.stiku.intg.dao.DevDemandDao;
import com.yisi.stiku.intg.dao.impl.DevDemandDaoImpl;
import com.yisi.stiku.intg.entity.DevDemand;

@Service
public class DevDemandServiceImpl extends BaseServiceImpl<Integer, DevDemand> {

	@Resource
	private DevDemandDaoImpl devDemandDaoImpl;

	@Resource
	private DevDemandDao devDemandDao;

	@Override
	protected BaseDaoImpl<Integer, DevDemand> getBaseDaoImpl() {

		return devDemandDaoImpl;
	}

	public void invalidDemand(List<Integer> demandIds) {

		devDemandDao.upDemandStatus(demandIds, DmStatus.INVALID.getDbCode());
	}

	public List<DevDemand> listDemandByIds(List<Integer> demandIds) {

		return devDemandDao.listDemandByIds(demandIds);
	}

	public List<DevDemand> listParentDemand(long creatorId) {

		return devDemandDao.listParentDemand((int) creatorId);
	}

	public List<DevDemand> listChildDemand(int parentId) {

		return devDemandDao.listChildDemand(parentId);
	}

	public boolean existBranch(String repoName, String branchName) {

		return devDemandDao.getAvailDemandByRepoAndBranch(repoName, branchName) != null;
	}

	public List<DevDemand> listRepoDemand(String repoName, List<Integer> demandIdList) {

		if (demandIdList == null || demandIdList.isEmpty()) {
			demandIdList = null;
		}
		return devDemandDao.listRepoDemand(repoName, demandIdList);
	}
}