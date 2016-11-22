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
        	<span class="selectd"><i>1</i>验证身份 ---------</span> <span class="selectd"><i>2</i>重置密码 ---------</span> <span><i>3</i>重置密码成功</span>
        </div>
        <div class="find-password-step-con">
          <form id="resetForm" action="${base }/user/findpw/step2" method="post">
            <input type="hidden" name="code" value="${code }"/>
        	<ul class="l2">
                <li><span>请输入密码：</span><input id="password" name="password" type="password" class="form-control" ></li>
                <li id="pwError" class="red-tip2 padding2">${errorMsg }</li>
                <li><span>请再次输入密码：</span><input id="retype" name="retype" type="password" class="form-control " ></li> <!-- input-bg1 -->
                <li id="rpError" class="red-tip2 padding2"></li>
                <li><span>&nbsp;</span><button type="button" class="xyb-btn bg_01" onclick="resetPwd();">下一步</button></li>
            </ul>
          </form>
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
</script> 
</html>
