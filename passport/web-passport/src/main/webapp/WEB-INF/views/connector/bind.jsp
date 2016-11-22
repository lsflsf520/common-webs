<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<link rel="stylesheet" type="text/css"
	href="${commonResDomain}${base}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain}${base}/css/base-style.css" />

</head>

<body>
	<!--wrapper start-->
	<div class="wrapper">
		<!--header start-->
		<div class="header">
			<div class="logo">
				<a href="${studentDomain }"><img
					src="${commonResDomain}${base}/images/login.png" width="185" height="35"
					alt="创数教育" /></a>
			</div>
			<div class="header-right">
				<a href="${studentIndex }">返回首页</a>
			</div>
		</div>
		<!--header end-->

		<!--register start-->
		<div class="register">
			<div id="bindDiv" class="row" style="display: none;">
					<div class="col-sm-7">
						<div class="login-QQ">
						   <ul>
							<li class="login-QQ-title">只差一步，即可完成登录设置</li>
							<li>快速完成17大学帐号绑定，完成帐号绑定后，即可直接登录17大学哦！</li>
							<li class="m-top"><span class="header-pic"> <i><img
										src="${avatar }" width="108" height="108" alt="头像" /></i> <i>${nick }</i>
							</span> <span class="spot"><img
									src="${commonResDomain}${base}/images/spot_03.png" width="74"
									height="4" alt="点" /></span> <span class="logo1"> <i><img
										src="${commonResDomain}${base}/images/logo1_03.png" width="108"
										height="108" alt="图片" /></i> <i>创数教育</i>
							</span></li>
						  </ul>
						</div>
					</div>
					<div class="col-sm-5">
						<div class="register-right">
							<form id="bindForm" action="${base }/user/flogin/binduser"
								method="post">
								<input type="hidden" name="fid" value="${fid }" />
								<input type="hidden" name="avatar" value="${avatar }" />
								<input type="hidden" name="nick" value="${nick }" />
								<!-- connectUser中ID -->
								<ul style="margin-top: 10px;">
									<li><input type="text" id="loginName" name="loginName" value="${loginName }" class="form-control" placeholder="已验证的17大学账号" /></li>
									<li id="lnError" class="red-color">${errorMsg }</li>
									<li><input type="password" id="password" name="password"
										class="form-control" placeholder="密码" /></li>
									<li id="pwError" class="red-color"></li>
									<li><button type="button" onclick="bindUser();"
											class="button1">绑定账号</button></li>
									<li class="s1">没有17大学帐号？ <a
										href="javascript:changeTab('cbDiv');">创建并绑定</a></li>
								</ul>
							</form>
						</div>
					</div>
			</div>

			<!-- create and bind start -->
			<div id="cbDiv" class="row" style="display: none;">
				<div class="col-sm-7">
					<div class="login-QQ">
						<ul>
								<li class="login-QQ-title">同学你好，注册请先阅读以下说明</li>
								<li class="m-top">1.
									如果老师以前给你发过或者自己注册过17大学帐号，请勿重新注册！可以直接用老师之前给的或者自行注册的账号和密码登录；</li>
								<li class="m-top">2. 重复注册的帐号可能无法加入班级，以前帐号所积累的学习币和经验值也无法使用！</li>
						</ul>
					</div>
				</div>
				<div class="col-sm-5">
					<div class="register-right">
						<form id="cbForm" action="${base }/user/flogin/cbuser"
							method="post">
							<input type="hidden" name="fid" value="${fid }" />
							<input type="hidden" name="avatar" value="${avatar }" />
							<input type="hidden" name="nick" value="${nick }" />
							<ul>
		                    	<li>
		                    	  <div class="row">
		                            	<div class="col-sm-9"><input type="text" id="realName" name="realName" value="${nick }" class="form-control" placeholder="真实姓名"></div>
		                          </div>
		                        </li>
		                        <li id="emError" class="red-color">${errorMsg1 }</li>
		                        <li><input type="password" id="cpw" name="password" class="form-control"  placeholder="密码(6~20个字符)"/></li>
		                        <li id="pwError" class="red-color"></li>
		                        <li><input type="password" id="retype" name="retype" class="form-control"  placeholder="确认密码"/></li>
		                        <li id="rpError" class="red-color"></li>
		                        <li><button type="button" class="button1" onclick="cbUser();">同意协议并注册</button></li>
		                        <li class="agreement"><a href="javascript:showDiv()">创数教育使用协议</a></li>
		                        <li class="s1">我有17大学帐号 <a
										href="javascript:changeTab('bindDiv');">绑定</a></li>
		                    </ul>
						</form>
					</div>
				</div>
			</div>
			<!-- create and bind end -->
		</div>
		<!--register end-->
	</div>
	<!--wrapper end-->
</body><script>
	var showDiv = "${showDiv}";
	if (!showDiv) {
		showDiv = "bindDiv";
	}

	$("#" + showDiv).css("display", "block");

	function changeTab(showDiv) {
		$("#bindDiv").css("display", "none");
		$("#cbDiv").css("display", "none");

		$("#" + showDiv).css("display", "block");
	}

	function bindUser() {
		var loginName = $("#loginName").val();
		var password = $("#password").val();
		if (!loginName) {
			$("#lnError").text("帐号不能为空");
			return;
		}
		if (!password) {
			$("#pwError").text("密码不能为空");
			return;
		}

		$("#lnError").text("");
		$("#pwError").text("");

		$("#bindForm").submit();
	}

	  function cbUser(){
		  var realName = $("#realName").val();
		  var password = $("#cpw").val();
		  var retype = $("#retype").val();
		  var code = $("#code").val();
		  if(!realName || /\s/.test(realName) || !/^[\u4e00-\u9fa5]+$/.test(realName)){
			  $("#emError").text("真实姓名不能为空，且必须为汉字");
			  return;
		  }
		  
		  $("#emError").text("");
		  if(!password || password.length < 6 || /\s/.test(password)){
			  $("#pwError").text("密码不能为空，且长度不能小于6个非空字符");
			  return false;
		  }
		  $("#pwError").text("");
		  if(retype != password){
			  $("#rpError").text("两次密码输入不一致，请重新输入密码");
			  return false;
		  }
		  $("#rpError").text("");
		  
		  $("#cbForm").submit();
	  }
	
</script>

</html>

