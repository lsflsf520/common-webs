package com.yisi.stiku.intg.web.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.intg.constant.DmStatus;
import com.yisi.stiku.intg.entity.DevDemand;
import com.yisi.stiku.intg.entity.ProductDemand;
import com.yisi.stiku.intg.service.impl.DevDemandServiceImpl;
import com.yisi.stiku.intg.service.impl.ProductDemandServiceImpl;
import com.yisi.stiku.intg.util.IntgUtil;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.WebUtils;

/**
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/dev/demand")
public class DevDemandController {

	private final static Logger LOG = LoggerFactory.getLogger(DevDemandController.class);

	@Resource
	private DevDemandServiceImpl devDemandServiceImpl;

	@Resource
	private ProductDemandServiceImpl productDemandServiceImpl;

	@RequestMapping("jumpToAdd")
	public String jumpToAdd(HttpServletRequest request) {

		String[] repos = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, "repo.names");
		request.setAttribute("repoNames", repos);

		return "demand/jump_dev_add";
	}

	@RequestMapping("relatePd")
	public String relatePd(@RequestParam("productDemandId") int productDemandId, @RequestParam("devDemandId") int devDemandId) {

		DevDemand demand = devDemandServiceImpl.findById(devDemandId);
		demand.setProductDemandId(productDemandId);

		devDemandServiceImpl.update(demand);

		ProductDemand pd = productDemandServiceImpl.findById(productDemandId);
		pd.setStatus(DmStatus.DEV.getDbCode());

		productDemandServiceImpl.update(pd);

		return "redirect:/dev/demand/list.do";
	}

	@RequestMapping("detail")
	public String detail(HttpServletRequest request, @RequestParam("demandId") int demandId) {

		DevDemand demand = devDemandServiceImpl.findById(demandId);

		List<DevDemand> childList = null;
		if (demand != null) {
			if (demand.getParentId() != null && demand.getParentId() > 0) {
				childList = devDemandServiceImpl.listChildDemand(demand.getParentId());
				demand = devDemandServiceImpl.findById(demand.getParentId());
			} else {
				childList = devDemandServiceImpl.listChildDemand(demand.getId());
			}
		}

		String[] repos = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, "repo.names");
		if (childList != null) {
			List<String> notInRepos = new ArrayList<String>();

			List<String> inRepos = new ArrayList<String>();
			for (DevDemand child : childList) {
				inRepos.add(child.getRepoName());
			}

			for (String repo : repos) {
				if (!inRepos.contains(repo)) {
					notInRepos.add(repo);
				}
			}

			repos = notInRepos.toArray(new String[0]);
		}

		request.setAttribute("demand", demand);
		request.setAttribute("childList", childList);
		request.setAttribute("repoNames", repos);

		return "demand/detail";
	}

	@RequestMapping("add")
	public String addDemand(DevDemand demand, @RequestParam("replace") boolean replace,
			@RequestParam("repoNames") String[] repoNames) {

		if (repoNames == null || repoNames.length <= 0 || StringUtils.isBlank(demand.getBranch())) {
			return "redirect:/dev/demand/jumpToAdd.do";
		}

		List<DevDemand> childList = new ArrayList<DevDemand>();
		for (String repoName : repoNames) {
			boolean exist = devDemandServiceImpl.existBranch(repoName, demand.getBranch());
			if (exist && !replace) {
				return "redirect:/dev/demand/jumpToAdd.do";
			}
		}

		demand.setCreatorId((int) LoginSesionUtil.getUserId());
		demand.setCreator(LoginSesionUtil.getUserName());
		demand.setCreateTime(new Date());
		demand.setDbStatus(DmStatus.DEV);
		demand.setEnvs(0);
		int pk = devDemandServiceImpl.insertReturnPK(demand);

		for (String repoName : repoNames) {
			try {

				IntgUtil.createBranch(repoName, demand.getBranch(), replace);

				DevDemand child = new DevDemand();
				child.setBranch(demand.getBranch());
				child.setCreateTime(demand.getCreateTime());
				child.setCreator(demand.getCreator());
				child.setCreatorId(demand.getCreatorId());
				child.setRepoName(repoName);
				child.setParentId(pk);
				child.setDbStatus(demand.getDbStatus());
				child.setName(demand.getName());
				child.setPlanOnlineTime(demand.getPlanOnlineTime());
				child.setPlanTestTime(demand.getPlanTestTime());
				child.setEnvs(0);

				childList.add(child);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				return "redirect:/dev/demand/jumpToAdd.do";
			}
		}

		devDemandServiceImpl.insertBatch(childList);

		return "redirect:/dev/demand/list.do";

	}

	@RequestMapping("addRepos")
	public String addRepos(@RequestParam("parentId") int parentId, @RequestParam("repoNames") String[] repoNames,
			@RequestParam("replace") boolean replace) {

		DevDemand parentDemand = devDemandServiceImpl.findById(parentId);

		if (parentDemand != null && repoNames != null && repoNames.length > 0) {
			List<DevDemand> childList = new ArrayList<DevDemand>();
			for (String repoName : repoNames) {
				try {
					IntgUtil.createBranch(repoName, parentDemand.getBranch(), replace);

					DevDemand child = new DevDemand();
					child.setBranch(parentDemand.getBranch());
					child.setCreateTime(parentDemand.getCreateTime());
					child.setCreator(parentDemand.getCreator());
					child.setCreatorId(parentDemand.getCreatorId());
					child.setRepoName(repoName);
					child.setParentId(parentId);
					child.setDbStatus(parentDemand.getDbStatus());
					child.setName(parentDemand.getName());
					child.setPlanOnlineTime(parentDemand.getPlanOnlineTime());
					child.setPlanTestTime(parentDemand.getPlanTestTime());
					child.setEnvs(0);

					childList.add(child);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					return "redirect:/dev/demand/jumpToAdd.do";
				}

			}

			devDemandServiceImpl.insertBatch(childList);
		}

		return "redirect:/dev/demand/detail.do?demandId=" + parentId;
	}

	@RequestMapping("del")
	public String delDemand(@RequestParam("demandId") int demandId) {

		DevDemand demand = devDemandServiceImpl.findById(demandId);

		List<Integer> demandIds = new ArrayList<Integer>();
		demandIds.add(demand.getId());

		String errorMsg = "";
		try {
			errorMsg = URLEncoder.encode("请先将仓库rrepoNamer的需求'"
					+ demand.getName() + "'从集成环境退出", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			LOG.error(e1.getMessage(), e1);
		}
		if (demand.getParentId() == null) {
			// 如果当前需求是父需求，则需检查其所有可用的子需求是否有进入集成环境的，如果有，则需提示用户先将其退出才能废弃
			List<DevDemand> childList = devDemandServiceImpl.listChildDemand(demandId);
			if (childList != null && !childList.isEmpty()) {
				for (DevDemand ch : childList) {
					if (ch.getEnvs() > 0) {
						return "redirect:/dev/demand/list.do?errorMsg=" + errorMsg.replace("rrepoNamer", ch.getRepoName());
					}
				}
				// 如果所有子需求都已经退出集成环境，则需先将所有子需求的分支删掉
				for (DevDemand ch : childList) {
					try {
						IntgUtil.delBranch(ch.getRepoName(), ch.getBranch());

						demandIds.add(ch.getId());
					} catch (Exception e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
		} else if (demand.getEnvs() > 0) {
			return "redirect:/dev/demand/list.do?errorMsg=" + errorMsg.replace("rrepoNamer", demand.getRepoName());
		}

		if (StringUtils.isNotBlank(demand.getRepoName()) && StringUtils.isNotBlank(demand.getBranch())) {
			try {
				IntgUtil.delBranch(demand.getRepoName(), demand.getBranch());
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		devDemandServiceImpl.invalidDemand(demandIds);

		return "redirect:/dev/demand/list.do";
	}

	@RequestMapping("list")
	public String listDemand(HttpServletRequest request) {

		List<DevDemand> demandList = devDemandServiceImpl.listParentDemand(LoginSesionUtil.getUserId());
		request.setAttribute("demandList", demandList);

		String[] repos = ConfigOnZk.getValueArr(ZkConstant.APP_ZK_PATH, "repo.names");
		request.setAttribute("repoNames", repos);
		request.setAttribute("errorMsg", WebUtils.getUTF8Value(request, "errorMsg"));

		return "demand/dev_list";
	}

}
