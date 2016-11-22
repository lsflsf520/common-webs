package com.yisi.stiku.sys.web.action;

import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.basedata.rpc.bean.MenuInfo;
import com.yisi.stiku.basedata.rpc.service.UserRpcService;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.priv.rpc.service.LinkMgrRpcService;
import com.yisi.stiku.web.util.LoginSesionUtil;

/**
 * 全局布局处理
 */
@Controller
@RequestMapping("/")
public class LayoutController {

	// private final static Logger LOG =
	// LoggerFactory.getLogger(LayoutController.class);

	// @Resource
	// private MenuRpcService menuRpcService;

	@Resource
	private LinkMgrRpcService linkMgrRpcService;

	@Resource
	private UserRpcService userRpcService;

	@RequestMapping("/layout")
	public String start(HttpServletRequest request) {

		long userId = LoginSesionUtil.getUserId();
		List<MenuInfo> menuInfoList = linkMgrRpcService.loadMenu(ZkConstant.PROJECT_NAME, userId);

		request.setAttribute("rootMenus", menuInfoList);
		return "start";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("{module}/{component}")
	public String toIndex(HttpServletRequest request, @PathVariable String module, @PathVariable String component) {

		request.setAttribute("module", module);
		request.setAttribute("component", component);
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			request.setAttribute(paramName, request.getParameter(paramName));
		}
		return module + "/" + component + "-index";
	}

	@RequestMapping("/")
	public String toLayout() {

		return "redirect:/layout";
	}

}
