package com.yisi.stiku.intg.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.CmdExecUtil;
import com.yisi.stiku.common.utils.RegexUtil;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;

/**
 * @author shangfeng
 *
 */
public class IntgUtil {

	/**
	 * 
	 * @param projectName
	 * @return
	 */
	public static String getProjectCnName(String projectName) {

		return ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "product." + projectName + ".cn.name", projectName);
	}

	public static Map<String, String> getProjectNameMap() {

		String[] projectNames = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, "product.names");

		Map<String, String> projectNameMap = new LinkedHashMap<String, String>();
		if (projectNames != null && projectNames.length > 0) {
			for (String projectName : projectNames) {
				projectNameMap.put(projectName,
						IntgUtil.getProjectCnName(projectName));
			}
		}

		return projectNameMap;
	}

	public static Map<String, String> getModuleCnNameMap4Project(String projectName) {

		Map<String, String> projectModuleMap = new HashMap<String, String>();
		String[] modules = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, "product." + projectName + ".modules");
		if (modules == null || modules.length <= 0) {
			if (StringUtils.isNotBlank(projectName)) {
				projectModuleMap.put(projectName,
						ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "product." + projectName + ".cn.name", projectName));
			}
		} else {
			for (String module : modules) {
				projectModuleMap.put(module, getModuleCnName4Project(projectName, module));
			}
		}

		return projectModuleMap;
	}

	public static String getModuleCnName4Project(String projectName, String moduleName) {

		return ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "module." + projectName + "." + moduleName + ".cn.name",
				ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "module." + moduleName + ".cn.name", moduleName));
	}

	public static void createBranch(String repoName, String branchName, boolean replace) {

		String scriptDir = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "script.base.dir");

		CmdExecUtil.execCmdForStdout(scriptDir + "/git_new_branch.sh " + repoName + " "
				+ branchName + (replace ? " -r" : ""));
	}

	public static void delBranch(String repoName, String branchName) {

		String scriptDir = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "script.base.dir");

		CmdExecUtil.execCmdForStdout(scriptDir + "/git_del_branch.sh " + repoName + " "
				+ branchName);
	}

	public static void merge(String repoName, String envName, String branches) {

		merge(repoName, envName, branches, false);
	}

	public static void merge(String repoName, String envName, String branches, boolean reCreateEnvBranch) {

		try {

			String scriptDir = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "script.base.dir");

			CmdExecUtil.execCmdForStdout(scriptDir + "/git_merge.sh " + repoName + " " + envName + " "
					+ branches + (reCreateEnvBranch ? " -f" : ""));

		} catch (BaseRuntimeException e) {
			// TODO 如果发生合并冲突，则需要将合并冲突的分支记录到 intg 中去
			List<String> conflictBranchList = null;
			String outMsg = e.getUserData();
			if (StringUtils.isNotBlank(outMsg)) {
				conflictBranchList = RegexUtil.extractGroups("busi_branch=(.*), to_resolve_branch=(.*)", outMsg);
			}
			throw new BaseRuntimeException("MERGE_CONFLICT", "发生合并冲突", conflictBranchList, e);
		}
	}

	public static void resolveConflict(String repoName, String envName) {

		try {
			String scriptDir = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "script.base.dir");

			CmdExecUtil.execCmdForStdout(scriptDir + "/git_resolve.sh " + repoName + " " + envName);
		} catch (BaseRuntimeException e) {
			// TODO 如果发生合并冲突，则需要将合并冲突的分支记录到 intg 中去
			List<String> conflictBranchList = null;
			String outMsg = e.getUserData();
			if (StringUtils.isNotBlank(outMsg)) {
				conflictBranchList = RegexUtil.extractGroups("busi_branch=(.*), to_resolve_branch=(.*)", outMsg);
			}
			throw new BaseRuntimeException("MERGE_CONFLICT", "发生合并冲突", conflictBranchList, e);
		}
	}

	/**
	 * 
	 * @param projectName
	 * @param envName
	 */
	public static void releaseEnv(String projectName, String envName, String outputfile) {

		String scriptDir = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "script.base.dir");

		CmdExecUtil.execCmd(scriptDir + "/release_java.sh " + projectName + " "
				+ envName + " " + outputfile);

	}

	public static void restartEnv(String projectName, String envName, String outputfile) {

		String scriptDir = ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "script.base.dir");

		CmdExecUtil.execCmd(scriptDir + "/restart_server_java.sh " + projectName + " "
				+ envName + " " + outputfile);
	}

}
