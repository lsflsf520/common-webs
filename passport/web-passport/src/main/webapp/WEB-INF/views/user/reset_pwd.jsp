<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp"%>

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
			<%@include file="../common/_header.jsp"%>
		</div>
		<!--header end-->

		<!--find-password-step start-->
		<div class="find-password-step">
			<div class="find-password-step-con">
				<form id="resetForm" action="${base }/user/pwd/reset" method="post">
					<input type="hidden" name="code" value="${code }" />
					<ul class="l2">
						<li>温馨提示：为了确保你的账号安全，请立刻修改你的密码，谢谢！</li>
						<li><span>旧登录密码：</span><input
							onfocus="$('#oldpwd').attr('type', 'password')" id="oldpwd"
							name="oldpwd" class="form-control"></li>
						<li id="oldpwdError" class="red-tip2 padding2">${errorMsg }</li>
						<li><span>新登录密码：</span><input
							onfocus="$('#password').attr('type', 'password')" id="password"
							name="password" class="form-control"></li>
						<li id="pwError" class="red-tip2 padding2"></li>
						<li><span>确认登录密码：</span><input
							onfocus="$('#retype').attr('type', 'password')" id="retype"
							name="retype" class="form-control "></li>
						<!-- input-bg1 -->
						<li id="rpError" class="red-tip2 padding2"></li>
						<li id="newpwdError" class="red-tip2 padding2">${newpwdError }</li>
						<li><span>&nbsp;</span>
							<button type="button" class="xyb-btn bg_01" onclick="resetPwd();">下一步</button></li>
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
		var oldpwd = $("#oldpwd").val();
		var password = $("#password").val();
		var retype = $("#retype").val();

		if (!oldpwd) {
			$("#oldpwdError").text("旧密码不能为空");
			return false;
		}
		$("#oldpwdError").text("");
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

		$("#newpwdError").text("");
		$.ajax({
			url : WEB_ROOT + "/account/checkLoginPwdSameTrnPwd",
			type : "POST",
			async : false,
			data : $('#resetForm').serialize(),
			success : function(data) {
				if (data == '1') {
					$("#newpwdError").text("登录密码不能与支付密码相同，请重新设置");
					 $("#password").val("");
					 $("#retype").val("");
					return false;
				} else if (data == '0') {
					$("#resetForm").submit();
				}
			}
		});

	}
</script>
</html>
