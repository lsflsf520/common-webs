<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>

</head>

<body>
<!--wrapper start-->
<div class="wrapper">
	<!--header start-->
    <div class="header">
    	<div class="logo"><a href="${studentDomain }?force=true">一起大学</a></div>
    	<!-- 
        <div class="header-right">没有账号，<a href="${base }/user/reg/regPage">马上注册<img src="${commonResDomain}${base}/images/jt.png" width="11" height="19" alt="箭头"/></a></div>
         -->
        <div class="header-right"><a href="${teacherDomain }?force=true">老师登录<img src="${commonResDomain}${base}/images/jt.png" width="11" height="19" alt="箭头"/></a></div>
    </div>
    
    <!--header end-->
    
    <!--pad-login start-->
    <div class="pad-login">
      <form id="loginForm" action="${base }/user/login/doLogon" method="post">
        <input type="hidden" name="referer" value="${referer }"/>
    	<ul>
        	<!-- <li class="pad-login-title">湖南省长沙市第一中学</li>  -->
            <li><input type="text" class="form-control" id="loginName" name="loginName" placeholder="17大学帐号/QQ邮箱"/></li>
            <li>
                          <input type="text" class="form-control" id="bkPwd" onfocus="document.getElementById('bkPwd').style.display='none';document.getElementById('password').style.display='block';document.getElementById('password').focus()"/>
                          <input id="password" name="password" style="display:none;" type="password" class="form-control"  placeholder="密码"/>
                        </li>
            <li id="lnError" class="error-tip">${errorMsg }</li>
            <!-- <li class="forget"><a href="javascript:void(0);">忘记密码</a></li>  -->
            <li><button type="button" onclick="doLogon();">登录</button></li>
            <li><!-- <a href="javascript:void(0);">QQ帐号登录</a> <a>&nbsp;</a><span><a href="${base }/user/reg/regPage">没有帐号，马上注册 ></a></span>--></li> 
        </ul>
      </form>
    </div>
    <div align="center" style="font-size:20px;text-decoration:underline;"><a href="http://7xq2xo.com1.z0.glb.clouddn.com/17daxuetakepic.apk">创数拍照组件下载</a></div>
    <!--pad-login end-->
</div>
<!--wrapper end-->
</body>
<script>
  function doLogon(){
	  var loginName = $("#loginName").val();
	  var password = $("#password").val();
	  if(!loginName){
		  $("#lnError").text("帐号不能为空");
		  return;
	  }
	  if(!password){
		  $("#pwError").text("密码不能为空");
		  return;
	  }
	  
	  $("#lnError").text("");
	  $("#pwError").text("");
	  
	  $("#loginForm").submit();
  }
</script>
</html>


