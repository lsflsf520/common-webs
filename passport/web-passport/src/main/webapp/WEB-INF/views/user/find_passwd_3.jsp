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
    
     <!--find-password-step start-->
    <div class="find-password-step">
		<div class="find-password-step-list">
        	<span class="selectd"><i>1</i>验证身份 ---------</span> <span class="selectd"><i>2</i>重置密码 ---------</span> <span class="selectd"><i>3</i>重置密码成功</span>
        </div>
        <div class="find-password-step-con">
        	<ul class="l3">
                <li class="f-size3">恭喜你，重置密码成功</li>
                <li><p id="seconds">5</p> 秒后将跳转到登录页</li>
                <li><a href="${base }/user/login/loginPage" class="a-bottom">立即登录</a>
            </li></ul>
        </div>
    </div>
    <!--find-password-step end-->
    
</div>
<!--wrapper end-->
</body>
<script> 
  function resetPwd(){
	  var password = $("#password").val();
	  var retype = $("#retype").val();
	  
	  if(!password || password.length < 6 || /\s/.test(password)){
		  $("#pwError").text("密码不能为空，且长度为6到20个非空字符");
		  return false;
	  }
	  $("#pwError").text("");
	  if(retype != password){
		  $("#rpError").text("两次密码输入不一致，请重新输入密码");
		  return false;
	  }
	  $("#rpError").text("");
	  
	  $("#resetForm").submit();
  }
  
  function jumpFunc(){
	  var sec = $("#seconds").text();
	  if(sec > 0){
		 $("#seconds").text(sec - 1);
		 setTimeout(jumpFunc, 1000);
	 }else{
		window.location.href = "${base }/user/login/loginPage"; 
	 }
  }
  
  $(document).ready(function(){
	  setTimeout(jumpFunc, 1000);
  });
</script> 
</html>
