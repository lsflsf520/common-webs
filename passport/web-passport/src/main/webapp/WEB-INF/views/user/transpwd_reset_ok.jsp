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
    
   
    
       <!--AI-Matrix-no start-->
    <div class="AI-Matrix-no" style="margin-top:200px;">
    	<ul style="width:340px; margin:0 auto; float:none;">
        	<li><img src="${commonResDomain}${base}/images/icon.png" width="110" height="110" alt="图片" /></li>
     <li class="f-size3">设置成功</li> 
            <li style="text-align:left; line-height:25px;">为了保障你的账号安全，请牢记你的支付密码，勿泄露给他人。</span></li>
        </ul>
    </div>
    
    <!--AI-Matrix-no end-->
      <!--bottom-btn start-->
    <div class="bottom-btn">
    	<a href="${base}/priv/info/account" class="btn-bg1" style="width:200px;">返回账号管理</a>
    </div>
    
    <!--find-password-step end-->
    
</div>
<!--wrapper end-->
</body>
</html>
