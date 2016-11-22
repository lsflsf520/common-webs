<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
</head>

<body>
<!--wrapper start-->
<div class="wrapper">
	<!--header start-->
    <div class="header">
    	<%@include file="../common/_header.jsp" %>
    </div>
    <!--header end-->
    
     <!--find-password-step start-->
    <div class="register-succeed" style="display: block;" id="succeed">
    	<ul>
        	<li><img src="${commonResDomain}${base}/images/icon.png" width="45" height="45" alt="图标"></li>
            <li>恭喜您，修改密码成功！</li>
            <li><a href="${studentIndex }">立即进入<img src="${commonResDomain}${base}/images/jt1.png" width="11" height="19" alt="箭头"></a></li>
        </ul>
    </div>
    <!--find-password-step end-->
    
</div>
<!--wrapper end-->
</body>
</html>
