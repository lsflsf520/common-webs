<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>模板XML文件查看-Quartz系统</title>
<body>
	<div id="middlebox">
		<h2>Job模板原始xml文件</h2>
		<i>Job模板是通过init()加载的</i> <br />
		<textarea cols="60" rows="30" name="content" value="${content}">
        </textarea>
	</div>

	<div id="footer">
		<div class="footer micro">
			<br /> <br />
		</div>
	</div>
</body>
</html>
