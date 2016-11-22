package com.yisi.stiku.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yisi.stiku.auditlog.rpc.AuditLogFacade;
import com.yisi.stiku.conf.ConfigOnZk;
import com.yisi.stiku.conf.ZkConstant;
import com.yisi.stiku.interceptor.constant.InterceptConstant;

public class OperLogInterceptor extends HandlerInterceptorAdapter {

	private final static Logger LOG = LoggerFactory.getLogger(OperLogInterceptor.class);

	@Resource
	private AuditLogFacade auditLogFacade;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String[] uriArr = ConfigOnZk.getValueArr(ZkConstant.ALIAS_PROJECT_NAME + "/operlog.properties", "operlog.uris");

		if (uriArr != null && uriArr.length > 0) {
			String requestUri = request.getServletPath();
			for (String uri : uriArr) {
				if (requestUri.equals(uri)) {
					try {
						Object preData = request.getAttribute(InterceptConstant.PRE_DATA);
						Object afterData = request.getAttribute(InterceptConstant.AFTER_DATA);

						String keyParam = null;

						Object keyObj = request.getAttribute(InterceptConstant.KEY_PARAM);
						if (keyObj != null) {
							keyParam = keyObj.toString();
						} else {
							String keyParamName = ConfigOnZk.getValue(ZkConstant.ALIAS_PROJECT_NAME + "/operlog.properties",
									uri + ".key.paramName");
							if (StringUtils.isNotBlank(keyParamName)) {
								keyParam = request.getParameter(keyParamName);
							}
						}

						String operType = ConfigOnZk.getValue(ZkConstant.ALIAS_PROJECT_NAME + "/operlog.properties", uri
								+ (StringUtils.isNotBlank(keyParam) ? "." + keyParam : "")
								+ ".operType", "") + "(" + uri + ")";
						auditLogFacade.saveAuditLog(preData, afterData, operType);
						break;
					} catch (Exception e) {
						LOG.warn("save auditlog for '" + requestUri + "' error", e);
					}
				}
			}
		}

		super.postHandle(request, response, handler, modelAndView);
	}

}
