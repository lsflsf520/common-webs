<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设置/修改支付密码</title>
</head>

<body>
	<!--wrapper start-->
	<div class="wrapper">
		<!--header start-->
		<div class="header">
			<%@include file="../common/_header.jsp"%>
		</div>
		<!--header end-->

		<!--find-password-step start-->
		<div class="find-password-step">
			<div class="find-password-step-con">
				<form id="resetForm" action="${base }/account/initAccount"
					method="post">
					<input type="hidden" name="code" value="${code }" />
					<ul class="l2">
						<li>为了保障您的账户安全，请牢记你的支付密码，勿泄露给他人。</li>
						<c:if test="${TRANS_PWD_TYPE=='2'}">
							<li><span>旧支付密码：</span><input
								onfocus="$('#oldpwd').attr('type', 'password')" id="oldpwd"
								name="oldpwd" class="form-control"></li>
							<li id="oldpwdError" class="red-tip2 padding2">${errorMsg }</li>
						</c:if>
						<input type="hidden" name="TRANS_PWD_TYPE"
							value='${TRANS_PWD_TYPE}'>


							<li><span>支付密码：</span><input
								onfocus="$('#password').attr('type', 'password')" id="password"
								name="password" class="form-control"></li>
							<li id="pwError" class="red-tip2 padding2"></li>
							<li><span>确认支付密码：</span><input
								onfocus="$('#retype').attr('type', 'password')" id="retype"
								name="retype" class="form-control "></li> <!-- input-bg1 -->
							<li id="rpError" class="red-tip2 padding2"></li>
							<li><span>&nbsp;</span>
							<button  id="submitBtn" type="button" class="xyb-btn bg_01" onclick="resetPwd();">下一步</button></li>
					</ul>
				</form>
			</div>
		</div>
		<!--find-password-step end-->

	</div>
	<!--wrapper end-->
</body>
<script>
	function resetPwd() {
		
		var type = ${TRANS_PWD_TYPE};

		if (type == "2") {
			var oldpwd = $("#oldpwd").val();
			if (!oldpwd) {
				$("#oldpwdError").text("旧密码不能为空");
				return false;
			}

			$("#oldpwdError").text("");
		}

		var password = $("#password").val();
		var retype = $("#retype").val();

		if (!password || password.length < 6 || password.length > 20 || /\s/.test(password)) {
			$("#pwError").text("密码不能为空，且长度为6到20个非空字符");
			return false;
		}

		if (oldpwd == password) {
			$("#pwError").text("新密码不能和旧密码一致！");
			return false;
		}

		$("#pwError").text("");
		if (retype != password) {
			$("#rpError").text("两次密码输入不一致，请重新输入密码");
			return false;
		}
		$("#rpError").text("");

		if($("#submitBtn").hasClass("tokenFlagA")){ 
		    $("#rpError").text("正在提交");
			return false;
		}
	 
		$("#submitBtn").addClass("tokenFlagA");
		
		$.ajax({
			url : WEB_ROOT + "/account/checkTrnPwdSameLoginPwd",
			type : "POST",
			async : false,
			data : {
				password : password
			},
			success : function(data) {
			
				if (data == '1') {
					$("#rpError").text("登录密码与支付密码不能相同，请重新设置密码");
					$("#password").val("");
					$("#retype").val("");
					return false;
				} else if (data == '0') {
					$("#resetForm").submit();
				}
				
			}
		});
		$("#submitBtn").removeClass("tokenFlagA");
	}
</script>
</html>
