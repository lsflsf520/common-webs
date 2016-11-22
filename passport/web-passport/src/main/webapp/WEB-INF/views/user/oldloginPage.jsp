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
    	<div class="logo"><a href="${studentDomain }"><img src="${commonResDomain}${base}/images/login.png" width="185" height="35" alt="创数教育" /></a></div>
       <!-- 
        <div class="header-right">没有账号，<a href="${base }/user/reg/regPage">马上注册<img src="${commonResDomain}${base}/images/jt.png" width="11" height="19" alt="箭头"/></a></div>
       -->
       <div class="header-right"><a href="${studentDomain }?force=true">创数教育官网<img src="${commonResDomain}${base}/images/jt.png" width="11" height="19" alt="箭头"/></a></div>
    </div>
    <!--header end-->
    
    <!--register start-->
    <div class="register">

 
            	<div class="register-left">


                        	<ul>
                            	<li class="f-size">
                                	<span>死做题</span>
                                    <span>不如找方法！</span>
                                </li>
                                <li class="f-size1">
                                	<span>创数智能诊断专家帮你</span>
                                    <span>乐享数学，高效提分！</span>
                                </li>
                            </ul>

                </div>
 

            	<div class="register-right" style="margin-top:50px;">
            	  <form id="loginForm" action="${base }/user/login/doLogon" method="post" >
            	    <input type="hidden" name="referer" value="${referer }"/>
                	<ul>
                    	<li><input type="text" id="loginName" name="loginName" class="form-control"  placeholder="17大学帐号/QQ邮箱"/></li>
                        <li id="lnError" class="red-color">${errorMsg }</li>
                        <li>
                          <input type="text" class="form-control" id="bkPwd" onfocus="document.getElementById('bkPwd').style.display='none';document.getElementById('password').style.display='block';document.getElementById('password').focus()"/>
                          <input id="password" name="password" style="display:none;" type="password" class="form-control"  placeholder="密码"/>
                        </li>
                        <li id="pwError" class="red-color"></li>
                        <li><button class="button1" onclick="doLogon();">登录</button></li>
                        <li>
                        	<div class="row">
                        		 <div class="col-sm-6">
                        		   <!-- 
                        		   <span class="l1"><a href="/web-passport/user/flogin/qq?referer="><img src="http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/Connect_logo_7.png" alt="Connect_logo_1.png"></a></span>
                        		    -->
                        		 </div>  
                            	<div class="col-sm-6"><span class="l1 t-right"><a href="${base }/user/findpw/step1">忘记密码</a></span></div>
                            </div>
                        </li>
                    </ul>
                  </form>
                </div>


    </div>
    <!--register end-->
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
  
  $(document).ready(function(){
	 $("#loginName").val('');
	 $("#bkPwd").val('');
	 $("#password").val('');
  });
</script>
</html>


