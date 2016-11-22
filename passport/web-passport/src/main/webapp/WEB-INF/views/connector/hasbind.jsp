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
    	<div class="logo"><a href="${studentDomain }"><img src="${commonResDomain}${base}/images/login.png" width="185" height="35" alt="创数教育" /></a></div>
        <div class="header-right">我有帐号，<a href="${base }/user/login/loginPage">马上登录<img src="${commonResDomain}${base}/images/jt.png" width="11" height="19" alt="箭头"/></a></div>
    </div>
    <!--header end-->
    
    <!--register-succeed start-->
    <div class="register-succeed">
    	<ul>
        	<li>此QQ已经与17大学网站的另一个用户 ${bindUserName } 绑定了</li>
            <li class="tel">请确认是否与用户 ${bindUserName } 解绑，然后与当前登录用户 ${currUserName } 绑定？</li>
            <li><a href="${base }/user/flogin/bind2AnotherUser?fid=${fid }&referer=${referer }">是</a>&nbsp;<a href="${base }/priv/info/edit">否，我要返回个人信息页</a></li>
        </ul>
    </div>
    <!--register-succeed end-->
    
</div>
<!--wrapper end-->
</body>
</html>
