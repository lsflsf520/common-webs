package com.yisi.stiku.intg.web.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.intg.constant.DmStatus;
import com.yisi.stiku.intg.entity.ProductDemand;
import com.yisi.stiku.intg.service.impl.CommonServiceHelper;
import com.yisi.stiku.intg.service.impl.ProductDemandServiceImpl;
import com.yisi.stiku.intg.util.IntgUtil;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.WebUtils;

/**
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/product/demand")
public class ProductDemandController {

	@Resource
	private ProductDemandServiceImpl productDemandServiceImpl;

	@Resource
	private CommonServiceHelper commonServiceHelper;

	@RequestMapping("jumpToAdd")
	public String jumpToAdd(HttpServletRequest request) {

		Map<String, String> projectNameMap = IntgUtil.getProjectNameMap();

		request.setAttribute("projectNameMap", projectNameMap);

		if (projectNameMap != null && projectNameMap.size() > 0) {
			request.setAttribute("firstProjModuleMap",
					IntgUtil.getModuleCnNameMap4Project(projectNameMap.keySet().iterator().next()));
		}

		request.setAttribute("userMap", commonServiceHelper.queryIntgUsers());

		return "demand/jump_product_add";
	}

	@RequestMapping("loadModules")
	public void loadModules(HttpServletRequest request, HttpServletResponse response, String projectName) {

		WebUtils.writeJson(IntgUtil.getModuleCnNameMap4Project(projectName), request, response);
	}

	@RequestMapping("detail")
	public String findById(HttpServletRequest request, @RequestParam("demandId") int demandId) {

		ProductDemand demand = productDemandServiceImpl.findById(demandId);
		request.setAttribute("demand", demand);
		request.setAttribute("fileUris", StringUtils.isNotBlank(demand.getAttachs()) ? demand.getAttachs().split(",") : null);
		request.setAttribute("fileDomain", ConfigOnZk.getValue(ZkConstant.APP_ZK_PATH, "upload.file.domain"));
		return "demand/product_detail";
	}

	@RequestMapping("add")
	public String addDemand(ProductDemand demand, @RequestParam("fileUris") String[] fileUris) {

		demand.setCreateTime(new Date());
		demand.setCreator(LoginSesionUtil.getUserName());
		demand.setCreatorId((int) LoginSesionUtil.getUserId());
		demand.setStatus(DmStatus.REVIEW.getDbCode());
		demand.setAttachs(joinArr(fileUris));

		productDemandServiceImpl.insert(demand);

		return "redirect:/product/demand/list.do?projectName=" + demand.getProject() + "&moduleName=" + demand.getModule();
	}

	@RequestMapping("update")
	public String update(@RequestParam("demandId") int demandId, @RequestParam("fileUris") String[] fileUris) {

		ProductDemand demand = productDemandServiceImpl.findById(demandId);
		demand.setAttachs(joinArr(fileUris));

		productDemandServiceImpl.update(demand);

		return "redirect:/product/demand/list.do?projectName=" + demand.getProject() + "&moduleName=" + demand.getModule();
	}

	@RequestMapping("del")
	public String del(@RequestParam("demandId") int demandId) {

		ProductDemand demand = productDemandServiceImpl.findById(demandId);
		demand.setStatus(DmStatus.INVALID.getDbCode());

		productDemandServiceImpl.update(demand);

		return "redirect:/product/demand/list.do?projectName=" + demand.getProject() + "&moduleName=" + demand.getModule();
	}

	@RequestMapping("list")
	public String listDemand(HttpServletRequest request, String projectName, String moduleName) {

		List<ProductDemand> demandList = productDemandServiceImpl.listAvailProductDemand(projectName, moduleName);
		request.setAttribute("demandList", demandList);
		request.setAttribute("projectName", projectName);
		request.setAttribute("moduleName", moduleName);
		request.setAttribute("projectNameMap", IntgUtil.getProjectNameMap());
		request.setAttribute("firstProjModuleMap", IntgUtil.getModuleCnNameMap4Project(projectName));

		return "demand/product_list";
	}

	@RequestMapping("listNotRelateDemand")
	public void loadProductDemand(HttpServletRequest request, HttpServletResponse response) {

		List<ProductDemand> demandList = productDemandServiceImpl.listNotRelateDemand();

		WebUtils.writeJson(demandList, request, response);
	}

	private String joinArr(String[] fileUris) {

		String fileUri = "";
		if (fileUris != null && fileUris.length > 0) {
			fileUri = fileUris[0];
			for (int index = 1; index < fileUris.length; index++) {
				if (StringUtils.isNotBlank(fileUris[index])) {
					fileUri += "," + fileUris[index];
				}
			}
		}
		return fileUri;
	}

}
