package com.yisi.stiku.intg.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.common.utils.TaskUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.intg.constant.DmStatus;
import com.yisi.stiku.intg.dao.IntegrationDao;
import com.yisi.stiku.intg.dao.impl.IntegrationDaoImpl;
import com.yisi.stiku.intg.entity.DevDemand;
import com.yisi.stiku.intg.entity.Integration;
import com.yisi.stiku.intg.util.IntgUtil;
import com.yisi.stiku.web.util.LoginSesionUtil;

@Service
public class IntegrationServiceImpl extends BaseServiceImpl<Integer, Integration> {

	private final static Logger LOG = LoggerFactory.getLogger(IntegrationServiceImpl.class);

	@Resource
	private IntegrationDaoImpl integrationDaoImpl;

	@Resource
	private IntegrationDao integrationDao;

	@Resource
	private DevDemandServiceImpl devDemandServiceImpl;

	@Override
	protected BaseDaoImpl<Integer, Integration> getBaseDaoImpl() {

		return integrationDaoImpl;
	}

	public Integration loadIntg(String repoName, String envName) {

		Integration query = new Integration();
		query.setRepoName(repoName);
		query.setEnvName(envName);

		List<Integration> intgList = this.findByEntity(query);

		if (intgList == null || intgList.isEmpty()) {
			query.setInRelease((byte) 0);
			this.insertReturnPK(query);
			return query;
		}

		return intgList.get(0);
	}

	public List<DevDemand> loadDevDemand(String repoName, String envName) {

		Integration intg = loadIntg(repoName, envName);

		return loadDevDemand(intg);
	}

	public List<DevDemand> loadDevDemand(Integration intg) {

		List<DevDemand> demandList = new ArrayList<DevDemand>();
		List<Integer> demandIdList = parseDemandIds(intg.getDevDemandIds());
		if (demandIdList != null && !demandIdList.isEmpty()) {

			if ("master".equals(intg.getEnvName())) {
				// 如果是master，则只保留最近20个需求
				if (demandIdList.size() > 20) {
					List<Integer> lastIdList = new ArrayList<Integer>();
					for (int index = demandIdList.size() - 20; index < demandIdList.size(); index++) {
						lastIdList.add(demandIdList.get(index));
					}

					intg.setDevDemandIds(toDemandIdStr(lastIdList));

					this.update(intg);
				}

				demandList = devDemandServiceImpl.listDemandByIds(demandIdList);
			} else {
				List<DevDemand> dbList = devDemandServiceImpl.listDemandByIds(demandIdList);
				if (dbList != null && !dbList.isEmpty()) {
					for (DevDemand demand : dbList) {
						if (DmStatus.ONLINE.equals(demand.getDbStatus()) || DmStatus.INVALID.equals(demand.getDbStatus())) {
							demandIdList.remove(demand.getId());
						} else {
							demandList.add(demand);
						}
					}

					if (dbList.size() != demandIdList.size()) {
						intg.setDevDemandIds(toDemandIdStr(demandIdList));

						this.update(intg);
					}
				}
			}

		}

		return demandList;
	}

	public void releaseEnv(final Integration intg) {

		operServer(intg, new OperAction() {

			@Override
			public void doAction(Integration intg, String outputfile) {

				IntgUtil.releaseEnv(intg.getReleaseProject(),
						intg.getEnvName(), outputfile);
			}
		});
	}

	public void restartEnv(final Integration intg) {

		operServer(intg, new OperAction() {

			@Override
			public void doAction(Integration intg, String outputfile) {

				IntgUtil.restartEnv(intg.getReleaseProject(),
						intg.getEnvName(), outputfile);
			}
		});
	}

	private void operServer(final Integration intg, final OperAction action) {

		intg.setReleaser(LoginSesionUtil.getRealName());
		intg.setInRelease((byte) 1);
		intg.setLastReleaseTime(new Date());
		final String outputfile = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "output.file.dir",
				System.getProperty("java.io.tmpdir"))
				+ "/" + intg.getReleaseProject() + "_" + intg.getEnvName() + ".log";
		intg.setOutputFile(outputfile);
		this.update(intg);

		TaskUtil.exec(new Runnable() {

			@Override
			public void run() {

				try {
					action.doAction(intg, outputfile);
				} catch (Exception e) {
					LOG.error("release " + intg.getReleaseProject() + " in " + intg.getEnvName()
							+ " environment failure", e);
				} finally {
					intg.setInRelease((byte) 0);

					IntegrationServiceImpl.this.update(intg);
				}

			}
		});
	}

	public void resolveConflict(int intgId) {

		integrationDao.resolveConflict(intgId);
	}

	public String toDemandIdStr(List<Integer> demandIdList) {

		if (demandIdList == null || demandIdList.isEmpty()) {
			return "";
		}

		String demandIdStr = "" + demandIdList.get(0);
		for (int index = 1; index < demandIdList.size(); index++) {
			demandIdStr += "," + demandIdList.get(index);
		}

		return demandIdStr;
	}

	public List<Integer> parseDemandIds(String demandIdStr) {

		List<Integer> demandIdList = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(demandIdStr)) {
			for (String demandId : demandIdStr.split(",")) {
				if (RegexUtil.isInt(demandId)) {
					demandIdList.add(Integer.valueOf(demandId));
				}
			}
		}

		return demandIdList;
	}

	private static interface OperAction {

		public void doAction(Integration intg, String outputfile);

	}
}