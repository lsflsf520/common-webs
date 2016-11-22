package com.yisi.stiku.intg.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.DateUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.intg.constant.DmStatus;
import com.yisi.stiku.intg.constant.EnvVal;
import com.yisi.stiku.intg.entity.DevDemand;
import com.yisi.stiku.intg.entity.Integration;
import com.yisi.stiku.intg.service.impl.DevDemandServiceImpl;
import com.yisi.stiku.intg.service.impl.IntegrationServiceImpl;
import com.yisi.stiku.intg.service.impl.ProductDemandServiceImpl;
import com.yisi.stiku.intg.util.IntgUtil;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;

/**
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("intg/demand")
public class IntegrationController {

	private final static Logger LOG = LoggerFactory.getLogger(IntegrationController.class);

	@Resource
	private IntegrationServiceImpl integrationServiceImpl;

	@Resource
	private DevDemandServiceImpl devDemandServiceImpl;

	@Resource
	private ProductDemandServiceImpl productDemandServiceImpl;

	@RequestMapping("quit")
	public String quitEnv(@RequestParam("repoName") String repoName, @RequestParam("envName") String envName,
			@RequestParam("demandId") int demandId) {

		Integration intg = integrationServiceImpl.loadIntg(repoName, envName);
		List<Integer> demandIdList = integrationServiceImpl.parseDemandIds(intg.getDevDemandIds());
		if (demandIdList.contains(demandId)) {
			demandIdList.remove(Integer.valueOf(demandId));
		}

		intg.setDevDemandIds(integrationServiceImpl.toDemandIdStr(demandIdList));

		if (demandIdList != null && !demandIdList.isEmpty()) {
			List<DevDemand> demandList = devDemandServiceImpl.listDemandByIds(demandIdList);
			if (demandList != null && !demandList.isEmpty()) {
				String busiBranches = demandList.get(0).getBranch();
				for (int index = 1; index < demandList.size(); index++) {
					busiBranches += "," + demandList.get(index).getBranch();
				}

				try {

					IntgUtil.merge(repoName, envName, busiBranches, true);
					if (StringUtils.isNotBlank(intg.getWaitResolveBranch())) {
						IntgUtil.delBranch(ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "merge.conflict.repo"),
								intg.getWaitResolveBranch());
						intg.setConflictBranch("");
						intg.setWaitResolveBranch("");
					}

				} catch (BaseRuntimeException e) {
					// TODO 如果发生合并冲突，则需要将合并冲突的分支记录到 intg 中去
					List<String> branches = e.getUserData();
					if (branches != null && branches.size() == 2) {
						intg.setConflictBranch(branches.get(0));
						intg.setWaitResolveBranch(branches.get(1));
					}
					LOG.error(e.getMessage(), e);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		} else {
			try {
				IntgUtil.createBranch(repoName, envName, true);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}

			intg.setConflictBranch("");
			intg.setWaitResolveBranch("");
		}

		integrationServiceImpl.update(intg);

		DevDemand demand = devDemandServiceImpl.findById(demandId);
		if (demand != null) {
			int envs = demand.getEnvs() - EnvVal.getVal(envName);
			demand.setEnvs(envs);

			devDemandServiceImpl.update(demand);
		}

		return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + envName;
	}

	@RequestMapping("resolve")
	public String resolveConflict(@RequestParam("repoName") String repoName,
			@RequestParam("envName") String envName) {

		Integration intg = integrationServiceImpl.loadIntg(repoName, envName);
		try {

			IntgUtil.resolveConflict(repoName, envName);

			intg.setConflictBranch("");
			intg.setWaitResolveBranch("");

			integrationServiceImpl.resolveConflict(intg.getId());
		} catch (BaseRuntimeException e) {
			List<String> branches = e.getUserData();
			if (branches != null && branches.size() == 2) {
				intg.setConflictBranch(branches.get(0));
				intg.setWaitResolveBranch(branches.get(1));

				integrationServiceImpl.update(intg);
			}
			LOG.error(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + envName;
	}

	@RequestMapping("refresh")
	public String refresh(@RequestParam("repoName") String repoName,
			@RequestParam("envName") String envName) {

		List<DevDemand> demandList = integrationServiceImpl.loadDevDemand(repoName, envName);
		if (demandList != null && !demandList.isEmpty()) {
			String branches = demandList.get(0).getBranch();
			for (int index = 1; index < demandList.size(); index++) {
				branches += "," + demandList.get(index).getBranch();
			}

			try {

				IntgUtil.merge(repoName, envName, branches);
			} catch (BaseRuntimeException e) {
				// TODO 如果发生合并冲突，则需要将合并冲突的分支记录到 intg 中去
				List<String> bchList = e.getUserData();
				if (bchList != null && bchList.size() == 2) {
					Integration intg = integrationServiceImpl.loadIntg(repoName, envName);
					intg.setConflictBranch(bchList.get(0));
					intg.setWaitResolveBranch(bchList.get(1));

					integrationServiceImpl.update(intg);
				}
				LOG.error(e.getMessage(), e);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);

			}
		}

		return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + envName;
	}

	@RequestMapping("addEnv")
	public String addEnv(@RequestParam("repoName") String repoName,
			@RequestParam("aimEnvName") String aimEnvName,
			@RequestParam("srcEnvName") String srcEnvName) {

		List<DevDemand> demandList = integrationServiceImpl.loadDevDemand(repoName, srcEnvName);
		if (demandList == null || demandList.isEmpty()) {
			// 如果当前环境下没有任何业务分支，则直接返回到原来的环境列表页
			return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + srcEnvName;
		}
		Integration intg = integrationServiceImpl.loadIntg(repoName, aimEnvName);
		try {

			IntgUtil.merge(repoName, aimEnvName, srcEnvName, intg == null || StringUtils.isBlank(intg.getDevDemandIds()));
		} catch (BaseRuntimeException e) {
			// TODO 如果发生合并冲突，则需要将合并冲突的分支记录到 intg 中去
			List<String> bchList = e.getUserData();
			if (bchList != null && bchList.size() == 2) {
				intg.setConflictBranch(bchList.get(0));
				intg.setWaitResolveBranch(bchList.get(1));
			}
			LOG.error(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

		}

		List<Integer> demandIdList = integrationServiceImpl.parseDemandIds(intg.getDevDemandIds());
		for (DevDemand demand : demandList) {
			demand.setEnvs(demand.getEnvs() | EnvVal.getVal(aimEnvName));
			if ("test".equals(aimEnvName) || "pre".equals(aimEnvName)) {
				demand.setDbStatus(DmStatus.TEST);
				demand.setRealTestTime(new Date());

				productDemandServiceImpl.updateProductStatus(demand.getParentId(), DmStatus.TEST.getDbCode());

				DevDemand parentDemand = devDemandServiceImpl.findById(demand.getParentId());
				parentDemand.setDbStatus(demand.getDbStatus());
				parentDemand.setEnvs(demand.getEnvs());
				devDemandServiceImpl.update(demand);
				devDemandServiceImpl.update(parentDemand);
			} else if ("huidu".equals(aimEnvName) || "master".equals(aimEnvName)) {
				demand.setDbStatus(DmStatus.ONLINE);
				demand.setRealOnlineTime(new Date());

				devDemandServiceImpl.update(demand);
				changeRelatedDemands(demand, repoName);
			} else {
				devDemandServiceImpl.update(demand);
			}

			if (!demandIdList.contains(demand.getId())) {
				demandIdList.add(demand.getId());
			}
		}

		intg.setDevDemandIds(integrationServiceImpl.toDemandIdStr(demandIdList));
		integrationServiceImpl.update(intg);

		return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + aimEnvName;
	}

	/**
	 * 
	 * @param demand
	 * @param repoName
	 */
	private void changeRelatedDemands(DevDemand demand, String repoName) {

		List<DevDemand> childDemands = devDemandServiceImpl.listChildDemand(demand.getParentId());
		if (childDemands != null && !childDemands.isEmpty()) {
			boolean allOnline = true; // 所有的子需求是否都已经上线，默认为true
			for (DevDemand child : childDemands) {
				// demand.getId() != child.getId()
				// 这个判断是用来排除当前需求的状态，因为当前需求的状态变化还没有更新到数据库中
				if (demand.getId() != child.getId() && !DmStatus.ONLINE.equals(child.getDbStatus())) {
					allOnline = false;
					break;
				}
			}

			if (allOnline) {
				DevDemand parentDemand = devDemandServiceImpl.findById(demand.getParentId());
				parentDemand.setDbStatus(DmStatus.ONLINE);
				parentDemand.setEnvs(demand.getEnvs());
				devDemandServiceImpl.update(parentDemand);

				productDemandServiceImpl.updateProductStatus(demand.getParentId(), DmStatus.ONLINE.getDbCode());
			}
		}
		try {
			IntgUtil.delBranch(repoName, demand.getBranch());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@RequestMapping("addDemand")
	public String addDemand(@RequestParam("repoName") String repoName,
			@RequestParam("envName") String envName,
			@RequestParam("demandId") int demandId) {

		DevDemand demand = devDemandServiceImpl.findById(demandId);

		if (demand != null) {
			Integration intg = integrationServiceImpl.loadIntg(repoName, envName);
			if (StringUtils.isNotBlank(intg.getWaitResolveBranch())) {
				return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + envName
						+ "&errorMsg=当前有未解决完冲突的分支，请先解决再操作！";
			}
			List<Integer> demandIdList = integrationServiceImpl.parseDemandIds(intg.getDevDemandIds());
			if (demandIdList.contains(demandId)) {
				return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + envName;
			}

			try {

				IntgUtil.merge(repoName, envName, demand.getBranch(),
						intg == null || StringUtils.isBlank(intg.getDevDemandIds()));
			} catch (BaseRuntimeException e) {
				// TODO 如果发生合并冲突，则需要将合并冲突的分支记录到 intg 中去
				List<String> branches = e.getUserData();
				if (branches != null && branches.size() == 2) {
					intg.setConflictBranch(branches.get(0));
					intg.setWaitResolveBranch(branches.get(1));
				}
				LOG.error(e.getMessage(), e);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);

			}

			demandIdList.add(demandId);
			intg.setDevDemandIds(integrationServiceImpl.toDemandIdStr(demandIdList));
			integrationServiceImpl.update(intg);

			demand.setEnvs(demand.getEnvs() | EnvVal.getVal(envName));
			if ("test".equals(envName) || "pre".equals(envName)) {
				demand.setDbStatus(DmStatus.TEST);
				demand.setRealTestTime(new Date());

				productDemandServiceImpl.updateProductStatus(demand.getParentId(), DmStatus.TEST.getDbCode());

				DevDemand parentDemand = devDemandServiceImpl.findById(demand.getParentId());
				parentDemand.setDbStatus(demand.getDbStatus());
				parentDemand.setEnvs(demand.getEnvs());
				devDemandServiceImpl.update(demand);
				devDemandServiceImpl.update(parentDemand);
			} else if ("huidu".equals(envName) || "master".equals(envName)) {
				demand.setDbStatus(DmStatus.ONLINE);
				demand.setRealOnlineTime(new Date());

				devDemandServiceImpl.update(demand);
				changeRelatedDemands(demand, repoName);
			} else {
				devDemandServiceImpl.update(demand);
			}
		}

		return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + envName;
	}

	@RequestMapping("listEnvDemand")
	public String listEnvDemand(HttpServletRequest request, @RequestParam("repoName") String repoName,
			@RequestParam("envName") String envName) {

		Integration intg = integrationServiceImpl.loadIntg(repoName, envName);

		List<DevDemand> demandList = integrationServiceImpl.loadDevDemand(intg);
		if (StringUtils.isNotBlank(intg.getConflictBranch()) && (demandList == null || demandList.isEmpty())) {
			IntgUtil.createBranch(repoName, envName, true);

			intg.setConflictBranch("");
			intg.setWaitResolveBranch("");
			integrationServiceImpl.update(intg);
		}

		String[] repos = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, "repo.names");
		String[] projectNames = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, repoName + ".project.names");
		if (projectNames == null || projectNames.length <= 0) {
			projectNames = new String[] { repoName };
		}

		List<String> webProjNames = getWebProjectNames(projectNames);

		request.setAttribute("projectNames", projectNames);
		request.setAttribute("webProjectNames", webProjNames);
		request.setAttribute("repoNames", repos);
		request.setAttribute("demandList", demandList);
		request.setAttribute("repoName", repoName);
		request.setAttribute("envName", envName);

		request.setAttribute("releaseProject", intg.getReleaseProject());
		request.setAttribute("conflictBranch", intg.getConflictBranch());
		request.setAttribute("mergeRepo", ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "merge.conflict.repo"));
		request.setAttribute("waitResolveBranch", intg.getWaitResolveBranch());
		request.setAttribute("hasDemand", demandList != null && !demandList.isEmpty());
		request.setAttribute("inRelease", intg.getInRelease());
		request.setAttribute("releaser", intg.getReleaser());
		request.setAttribute("lastReleaseTime", DateUtil.getDateTimeStr(intg.getLastReleaseTime()));

		if ("develop".equals(envName)) {
			request.setAttribute("aimEnvName", "test");
			request.setAttribute("aimEnvCnName", "测试环境");
		} else if ("test".equals(envName)) {
			request.setAttribute("aimEnvName", "pre");
			request.setAttribute("aimEnvCnName", "预发环境");
		} else if ("pre".equals(envName)) {
			request.setAttribute("aimEnvName", "master");
			request.setAttribute("aimEnvCnName", "正式环境");
		}

		return "demand/integration";
	}

	@RequestMapping("ajaxList")
	public void loadMyDemand(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("repoName") String repoName,
			@RequestParam("envName") String envName) {

		List<DevDemand> demandList = new ArrayList<DevDemand>();
		Integration intg = integrationServiceImpl.loadIntg(repoName, envName);
		if (intg != null) {
			List<Integer> demandIdList = integrationServiceImpl.parseDemandIds(intg.getDevDemandIds());
			demandList = devDemandServiceImpl.listRepoDemand(repoName, demandIdList);
		}
		WebUtils.writeJson(demandList, request, response);
	}

	@RequestMapping("releaseEnv")
	public String releaseEnv(HttpServletRequest request, @RequestParam("repoName") String repoName,
			@RequestParam("projectName") final String projectName,
			@RequestParam("envName") final String envName) {

		final Integration intg = integrationServiceImpl.loadIntg(repoName, envName);
		try {
			intg.setReleaseProject(projectName);
			if (intg != null && intg.getInRelease() == 0 && StringUtils.isNotBlank(intg.getReleaseProject())) {
				integrationServiceImpl.releaseEnv(intg);
			}
		} catch (BaseRuntimeException e) {
			request.setAttribute("errorMsg", e.getFriendlyMsg());
			LOG.error("release " + projectName + " in " + intg.getEnvName() + " failure.", e);
		}
		return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + envName;
	}

	@RequestMapping("restartEnv")
	public String restartEnv(HttpServletRequest request, @RequestParam("repoName") String repoName,
			@RequestParam("projectName") final String projectName,
			@RequestParam("envName") final String envName) {

		final Integration intg = integrationServiceImpl.loadIntg(repoName, envName);
		try {
			intg.setReleaseProject(projectName);
			if (intg != null && intg.getInRelease() == 0 && StringUtils.isNotBlank(intg.getReleaseProject())) {
				integrationServiceImpl.restartEnv(intg);
			}
		} catch (BaseRuntimeException e) {
			request.setAttribute("errorMsg", e.getFriendlyMsg());
			LOG.error("release " + projectName + " in " + intg.getEnvName() + " failure.", e);
		}
		return "redirect:/intg/demand/listEnvDemand.do?repoName=" + repoName + "&envName=" + envName;
	}

	@RequestMapping("queryLog")
	public String queryLog(HttpServletRequest request, @RequestParam("repoName") String repoName,
			@RequestParam("projectName") final String projectName,
			@RequestParam("envName") final String envName) {

		Integration intg = integrationServiceImpl.loadIntg(repoName, envName);
		request.setAttribute("inRelease", intg.getInRelease());
		request.setAttribute("repoName", repoName);
		request.setAttribute("projectName", projectName);
		request.setAttribute("envName", envName);

		return "demand/release_log";
	}

	@RequestMapping("loadLog")
	public void loadLog(HttpServletRequest request, HttpServletResponse response, @RequestParam("repoName") String repoName,
			@RequestParam("projectName") final String projectName,
			@RequestParam("envName") final String envName) {

		Integration intg = integrationServiceImpl.loadIntg(repoName, envName);

		String logContent = "";
		File outFile = null;
		if (StringUtils.isNotBlank(intg.getOutputFile()) && (outFile = new File(intg.getOutputFile())).exists()) {
			try {
				logContent = FileUtils.readFileToString(outFile, "UTF-8");
				logContent = logContent.replaceAll("\n", "<br/>");
			} catch (IOException e) {
				logContent = "read file '" + intg.getOutputFile() + "' occurs error\n stack trace=" + e;
				LOG.error("read file '" + intg.getOutputFile() + "' occurs error", e);
			}
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("log", logContent);
		resultMap.put("inRelease", intg.getInRelease());

		WebUtils.writeJson(OperationResult.buildSuccessResult("OK", resultMap), request, response);
	}

	private List<String> getWebProjectNames(String[] projectNames) {

		List<String> webProjNames = new ArrayList<String>();
		for (String projectName : projectNames) {
			if (projectName.startsWith("web-")) {
				webProjNames.add(projectName);
			}
		}

		return webProjNames;
	}

}
