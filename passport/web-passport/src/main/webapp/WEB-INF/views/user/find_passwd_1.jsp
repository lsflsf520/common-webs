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
        	<span class="selectd"><i>1</i>验证身份 ---------</span> <span><i>2</i>重置密码 ---------</span> <span><i>3</i>重置密码成功</span>
        </div>
        <div class="find-password-step-con">
          <form id="codeForm" action="${base }/valid/emailcode/checksync">
            <input type="hidden" name="successUrl" value="/user/findpw/tostep2"/>
        	<ul class="l1">
            	<li>
                	<span>验证方式：</span>
                	<dl class="select select-box1">
                     <dt class="f1">邮箱验证</dt>
                     <dd>
                          <ul>
                          	<li><a href="javascript:void(0);">邮箱验证</a></li>
                            <!-- <li><a href="javascript:void(0);">手机验证</a></li> -->
                          </ul>
                      </dd>
                	</dl>
                </li>
                <li><span>&nbsp;</span><input type="text" id="emailPrefix" name="emailPrefix" class="form-control" style="width:180px;" placeholder="请输入QQ邮箱"><p style="display: inline-block; margin-left:10px;">@qq.com</p><a class="yzm" href="javascript:sendEmailCode();">获取验证码</a></li>
                <li id="emError" class="red-tip2 padding1"></li>
                <li id="lookCode" style="display:none;"><a href="https://mail.qq.com" target="_blank">去邮箱查看</a></li>
                <li><span>验证码：</span><input type="text" id="code" name="code" class="form-control" ></li>
                <li id="codeError" class="red-tip2 padding1"></li>
                <li><span>&nbsp;</span><button type="button" class="xyb-btn bg_01" onclick="doSubmit();">下一步</button></li>
            </ul>
          </form>
        </div>
    </div>
    <!--find-password-step end-->
    
</div>
<!--wrapper end-->
</body>
<script> 
  function sendEmailCode(){
	  var emailPrefix = $("#emailPrefix").val();
	  if(!emailPrefix || /\s/.test(emailPrefix)){
		  $("#emError").text("邮箱不能为空且不能包含空字符！");
		  return;
	  }
	  
	  if(/\w+@qq.com$/.test(emailPrefix)){
		  emailPrefix = emailPrefix.replace("@qq.com", "");
		  $("#emailPrefix").val(emailPrefix);
	  }
	  
	  var email = emailPrefix + "@qq.com";
	  $("#emError").text("");
	  $.ajax({
		  url : "${base}/valid/emailcode/send?email=" + email,
		  type : "GET",
		  success : function(data){
			  if(data && data.type && data.type != "success"){
				  $("#lookCode").css("display", "none");
				  $("#emError").text(data.message);
				  return;
			  }
			  $("#lookCode").css("display", "block");
		  }
	  });
  }
  
  function doSubmit(){
	  var code = $("#code").val();
	  if(!code){
		  $("#codeError").text("请输入验证码");
		  return;
	  }
	  $("#codeError").text("");
	  $("#codeForm").submit();
  }
</script> 
</html>
