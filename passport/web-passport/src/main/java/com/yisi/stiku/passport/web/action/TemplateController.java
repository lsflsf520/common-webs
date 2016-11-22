package com.yisi.stiku.passport.web.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yisi.stiku.common.exception.BaseRuntimeException;
import com.yisi.stiku.common.utils.ThreadUtil;
import com.yisi.stiku.common.utils.UserInfoUtil;
import com.yisi.stiku.conf.BaseConfig;
import com.yisi.stiku.web.util.LoginSesionUtil;
import com.yisi.stiku.web.util.WebUtils;

/**
 * @author shangfeng
 *
 */
@Controller
@RequestMapping("/tmpl")
public class TemplateController {

	private final static Logger LOG = LoggerFactory.getLogger(TemplateController.class);

	@RequestMapping("/loadPagePart")
	public void loadHeader(HttpServletRequest request, HttpServletResponse response) {

		String header = readFile("header");
		String footer = readFile("footer");

		String userName = null;
		String domain = null;
		try {
			Map<String, String> sessionInfoMap = LoginSesionUtil.getSessionInfo();
			if (sessionInfoMap != null && StringUtils.isBlank(sessionInfoMap.get(LoginSesionUtil.getKOMsgKey()))) {
				userName = sessionInfoMap.get(ThreadUtil.USER_SHOW_NAME);
				Integer userType = sessionInfoMap.get(ThreadUtil.USER_TYPE) == null ? null : Integer.valueOf(sessionInfoMap
						.get(ThreadUtil.USER_TYPE));

				domain = (UserInfoUtil.isStudent(userType)) ? (String) request.getAttribute("studentIndex")
						: (UserInfoUtil.isTeacher(userType) ? (String) request.getAttribute("teacherDomain")
								: (String) request
										.getAttribute("msDomain"));
			}
		} catch (BaseRuntimeException e) {
			LOG.debug("not login");
		}
		if (StringUtils.isNotBlank(userName)) {
			header = header.replace("#loginInfo", "<a class='login' href='" + domain + "'>" + userName
					+ "</a> "
					// + "| <a class='login' href='" + ((String)
					// request.getAttribute("passportDomain"))
					// + "/user/login/doLogout'>退出</a>"
					);

			footer = footer.replace("#loginInfo", "");
		} else {
			header = header.replace("#loginInfo",
					"<a class='login' href='" + ((String) request.getAttribute("passportDomain")) + "'>登录中心</a>");

			footer = footer.replace("#loginInfo", "<a href='" + ((String) request
					.getAttribute("msDomain")) + "'>后台登录</a> <a href='http://2015.17daxue.com/aindex.action'>2015版后台登录</a>");
		}

		Map<String, String> contentMap = new HashMap<String, String>();
		contentMap.put("header", header);
		contentMap.put("footer", footer);

		WebUtils.writeJson(contentMap, request, response);
	}

	private String readFile(String fileName) {

		String footerPath = BaseConfig.getPath("template/" + fileName + ".html");
		if (StringUtils.isNotBlank(footerPath) && footerPath.startsWith("file:/")) {
			footerPath = footerPath.replace("file:", "");
		}
		File file = new File(footerPath);
		if (!file.exists()) {
			LOG.warn("not found relative path header file 'template/header.html'");
		}
		String content = "";
		try {
			content = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			LOG.error("read file content failure. file path '" + footerPath + "'", e);
		}

		return content;
	}
}
