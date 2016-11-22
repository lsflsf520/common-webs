package com.yisi.stiku.interceptor;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yisi.stiku.common.utils.ThreadUtil;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.priv.rpc.service.LinkMgrRpcService;
import com.yisi.stiku.web.constant.WebConstant;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.OperationResult;
import com.yisi.stiku.web.util.WebUtils;
import com.yisi.stiku.web.util.OperationResult.OperResultType;

public class AccessInterceptor extends HandlerInterceptorAdapter{

//	private final static Logger LOG = LoggerFactory.getLogger(AccessInterceptor.class);
	
	@Resource
	private LinkMgrRpcService linkMgrRpcService;
	
	private AntPathMatcher urlMatcher = new AntPathMatcher();
	private Set<String> excludeUrls;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestUri = request.getServletPath();
		// 如果为 /ping/pang 链接，则直接返回true，不管当前用户有没有登录
		if (WebConstant.PING_PANG_URI.equals(requestUri)) {
			return true;
		}
		if(excludeUrls != null && !excludeUrls.isEmpty()){
			for(String excludeUri : excludeUrls){
				if(urlMatcher.match(excludeUri, requestUri)){
					return true;
				}
			}
		}
		boolean result = linkMgrRpcService.checkUserPriv(LoginSesionUtil.getUserId(), ZkConstant.PROJECT_NAME, request.getServletPath());
		if(!result){
			handleError(request, response, OperResultType.ACCESS_DENIED, "非授权的访问", request.getContextPath() + "/error/403");
		}
		
		return result;
	}
	
	
	private void handleError(HttpServletRequest request, HttpServletResponse response, OperResultType operType, String errorMsg, String redirectUrl) throws IOException{
		if(ThreadUtil.isAppReq() || WebUtils.isAjax(request)){
			WebUtils.writeJson(OperationResult.buildResult(operType, errorMsg), request, response);
		}else{
			response.sendRedirect(redirectUrl);
		}
	}
	
	public void setExcludeUrls(Set<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
}
