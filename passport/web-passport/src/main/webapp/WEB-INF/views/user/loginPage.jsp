<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>登 录</title>
  <link rel="stylesheet" type="text/css" href="${commonResDomain }${base }/css/StyleCss.css?v=${buildVersion}">
  <link rel="stylesheet" type="text/css" href="${commonResDomain }${base }/css/new-login.css?v=${buildVersion}">
</head>

<body>
<div class="wrapper">
<!--header start-->
<div class="header">
	<div class="header-con">
	<!--logo start-->
    <div class="logo"> <a <%-- href="${studentDomain }" --%>> <img src="${commonResDomain }${base }/images/new-logo.png" width="148" height="30" alt="logo"></a><span>最专业的智能数学教育提供商</span></div>
    <!--logo end-->
    </div>
</div>
<!--header end-->

<!--login start-->
<div class="login">
	<!--role-tab start-->
	<div class="role-tab">
    	<a id="stdBtn" href="javascript:void(0);" onclick="$('#thBtn').removeClass('T-selected');$('#stdBtn').addClass('S-selected');" class="student S-selected">学生</a> 
    	<a id="thBtn" href="javascript:void(0);" onclick="$('#stdBtn').removeClass('S-selected');$('#thBtn').addClass('T-selected');" class="teacher">老师</a>
    </div>
    <!--role-tab end-->
    <!--login-form start-->
   <form id="loginForm" action="${base }/user/login/doLogon" method="post" >
    <div class="login-form">
    	<ul>
        	<li><span class="account"></span><input type="text" id="loginName" name="loginName" placeholder="账号" class="input-style"></li>
            <li id="lnError" class="red-color">${errorMsg }</li>
            <li>
              <span class="lock"></span>
              <input type="text" id="bkPwd" placeholder="密码" class="input-style" onfocus="document.getElementById('bkPwd').style.display='none';document.getElementById('password').style.display='inline-block';document.getElementById('password').focus()">
              <input type="password" id="password" name="password" style="display:none;" placeholder="密码" class="input-style">
             </li>
             <li id="pwError" class="red-color"></li>
            <li><span></span><button class="button-style" onclick="doLogon();">登 录</button></li>
        </ul>
    </div>
   </form>
    <!--login-form end-->
</div>
<!--login end-->
</div>
<!--script start-->
<script src="${commonResDomain}/assets/plugins/jquery-1.10.2.min.js"></script>
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
  
  $(document).ready(function(){
	 $("#loginName").val('');
	 $("#bkPwd").val('');
	 $("#password").val('');
  });
 </script>
<!--script end-->
</body>
</html>



