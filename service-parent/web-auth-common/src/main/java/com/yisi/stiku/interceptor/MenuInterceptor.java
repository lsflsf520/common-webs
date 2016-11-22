package com.yisi.stiku.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.yisi.stiku.basedata.rpc.bean.MenuInfo;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.priv.rpc.service.LinkMgrRpcService;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.WebUtils;

public class MenuInterceptor extends HandlerInterceptorAdapter {

	@Resource
	private LinkMgrRpcService linkMgrRpcService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (!WebUtils.isAjax(request)) {
			long userId = LoginSesionUtil.getUserId();
			List<MenuInfo> menuInfoList = linkMgrRpcService.loadMenu(ZkConstant.PROJECT_NAME, userId);

			String menuJson = new Gson().toJson(menuInfoList);

			request.setAttribute("menuJson", menuJson);
		}
		super.postHandle(request, response, handler, modelAndView);
	}

}
