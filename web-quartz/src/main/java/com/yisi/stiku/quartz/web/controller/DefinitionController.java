package com.yisi.stiku.quartz.web.controller;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.quartz.web.model.JobDefinition;

@Controller
@RequestMapping("/definition")
public class DefinitionController extends BaseController {

	private static final Logger LOG = LoggerFactory
			.getLogger(DefinitionController.class);
	private static final long serialVersionUID = 0xc19d46942fc35f45L;
	public JobDefinition _definition;
	public String definitionName;
	public Map paramMap;

	public DefinitionController() {
		_definition = new JobDefinition();
		definitionName = "";
	}

	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("开始查询所有模板");
		if (definitionName != null || definitionName.length() > 1)
			_definition = BaseController.getDefinitionManager().getDefinition(
					definitionName);

		request.setAttribute("defList", getDefinitions());
		LOG.debug("结束查询所有模板:" + getDefinitions());
		return "/quartz/defList";
	}

	public Collection getDefinitions() {
		return getDefinitionManager().getDefinitions().values();
	}

	public String delete() {
		BaseController.getDefinitionManager().removeDefinition(definitionName);
		return "success";
	}

	@RequestMapping("/input")
	public String input(HttpServletRequest request, HttpServletResponse response) {
		return "/quartz/createDefined";
	}

	@RequestMapping("/readXML")
	public String readXML(HttpServletRequest request,
			HttpServletResponse response) {

		LOG.debug("开始读取模板XML");
		StringWriter outputWriter = new StringWriter();
		outputWriter.write("<?xml version='1.0' ?><JobDefinitions>");
		BeanWriter writer = new BeanWriter(outputWriter);
		try {
			writer.write("definitions", BaseController.getDefinitionManager()
					.getDefinitions());
			String xmlResult = (new StringBuilder(String.valueOf(outputWriter
					.toString()))).append("</JobDefinitions>").toString();
			request.setAttribute("content", xmlResult);
		} catch (Exception e) {
			LOG.error("读取模板XML失败" + e);
		}
		LOG.debug("结束读取模板XML");
		return "/quartz/readDefinedXML";
	}

	@RequestMapping("/create")
	public String create(HttpServletRequest request,
			HttpServletResponse response, JobDefinition _definition) {
		LOG.debug("创建模板开始");
		getQuartzContext().put("CREATE_NULL_OBJECTS", "true");
		JobDefinition def = BaseController.getDefinitionManager()
				.getDefinition(_definition.getName());
		if (def != null) {

			_definition = def;
		} else {
			if (paramMap != null)
				_definition.getParameters().addAll(paramMap.values());
			BaseController.getDefinitionManager().addDefinition(
					_definition.getName(), _definition);
		}
		LOG.debug("创建模板结束");
		return "success";
	}

	public String getDefinitionName() {
		return definitionName;
	}

	public void setDefinitionName(String string) {
		definitionName = string;
	}

	public JobDefinition getDefinition() {
		return _definition;
	}

	public void setDefinition(JobDefinition definition) {
		_definition = definition;
	}

	public Map getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map paramMap) {
		this.paramMap = paramMap;
	}
}
