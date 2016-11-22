package com.yisi.stiku.stat.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.stat.builder.PageDataBuilder;
import com.yisi.stiku.stat.consant.StatConstant;
import com.yisi.stiku.stat.entity.CondGroup;
import com.yisi.stiku.stat.entity.Condition;
import com.yisi.stiku.stat.entity.PageData;

/**
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/chart")
public class ChartBaseController {

	@Resource
	private PageDataBuilder pageDataBuilder;

	@RequestMapping("{module}/{chartType}")
	public String loadChart(HttpServletRequest request, @PathVariable String module, @PathVariable String chartType) {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			if (pageDataBuilder.isMultiCondition(key)) {
				paramMap.put(key, request.getParameterValues(key));
			} else {
				paramMap.put(key, request.getParameter(key));
			}
		}

		List<Condition> condList = pageDataBuilder.buildCondition(module);
		if (paramMap.isEmpty() && condList != null && !condList.isEmpty()) {
			for (Condition cond : condList) {
				if (cond instanceof CondGroup) {
					for (Condition childCond : ((CondGroup) cond).getCondList()) {
						paramMap.put(childCond.getName(), childCond.getDefaultVal());
					}
				} else {
					paramMap.put(cond.getName(), cond.getDefaultVal());
				}
			}
		}
		paramMap.put(StatConstant.CHART_TYPE_KEY, chartType);
		Object obj = buildPageData(module, paramMap);
		request.setAttribute("condList", condList);
		// 在这将浏览器传过来的参数，再传回浏览器，用于显示相关的状态信息
		request.setAttribute("currKVMap", paramMap);
		request.setAttribute("pageData", obj);

		// 如果请求参数中指定了namespace，那么将优先使用 namespace + "/" + module 这个jsp返回；否则将使用
		// "chart/" + chartType 默认的jsp返回
		return StringUtils.isNotBlank(request.getParameter("namespace")) ? request.getParameter("namespace") + "/" + module
				: "chart/" + chartType;
	}

	@RequestMapping("index")
	public String index() {

		return "index";
	}

	/**
	 * 如果有特殊的页面数据要求，则可以重写该方法，并提供对应的页面展示出来即可
	 * 
	 * @param module
	 * @param paramMap
	 * @return
	 */
	protected Object buildPageData(String module, Map<String, Object> paramMap) {

		PageData pageData = pageDataBuilder.buildPageData(module, paramMap);

		return pageData;
	}

}
