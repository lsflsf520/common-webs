<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain}${base}/css/account.css" />

<script type="text/javascript"
	src="${projectResDomain}${base}/js/user/edit_info.js?v=1.1"></script>

<script type="text/javascript"
	src="${projectResDomain}${base}/js/user/account.js"></script>

<title>账号管理</title>
<script type="text/javascript">

function showDiv(){
 
    }
    function closeDiv(){
    document.getElementById('successPopDiv').style.display='none';
    document.getElementById('t_bg1').style.display='none';
    document.getElementById('popIframe').style.display='none';
    }
    
    
	$(document).ready(
			function() {
				if ($("#loadingPopDiv") && $("#loadingPopDiv").length > 0) {
					globalJs.showDiv("loadingPopDiv");
				}
				var alreadySetTranPwd = "${alreadySetTranPwd}";
                var isZizhuPrint = ${isZizhuPrint };
				if (isZizhuPrint && alreadySetTranPwd == 'false') {
					globalJs.setOKBtnText("去设置");
					globalJs.setCancelBtnText("取消");
					globalJs.revertClass();
					globalJs.confirm("为了保障你的账户安全，请设置支付密码", PASSPORT_DOMAIN
							+ "/account/setPwdForTrans");
				}
			});

	$(function() {
		$(".select").each(function() {
			var s = $(this);
			var z = parseInt(s.css("z-index"));
			var dt = $(this).children("dt");
			var dd = $(this).children("dd");
			var _show = function() {
				dd.slideDown(200);
				dt.addClass("cur");
				s.css("z-index", z + 1);
			}; //展开效果
			var _hide = function() {
				dd.slideUp(200);
				dt.removeClass("cur");
				s.css("z-index", z);
			}; //关闭效果
			dt.click(function() {
				dd.is(":hidden") ? _show() : _hide();
			});
			dd.find("a").click(function() {
				dt.html($(this).html());
				_hide();
			}); //选择效果（如需要传值，可自定义参数，在此处返回对应的"value"值 ）
			$("body").click(function(i) {
				!$(i.target).parents(".select").first().is(s) ? _hide() : "";
			});
		})
	})

	function charge() {

		if ($("#applyBtn").hasClass("disableA")) {
			$("#errorLi").text("请求正在处理，请不要重复点击");
			return;
		}
		var actCode = $("#actCode").val();
		if (/^\s*$/.test(actCode)) {
			$("#errorLi").text("激活码不能为空！");
			return;
		}
		$("#errorLi").text("");
		$("#applyBtn").addClass("disableA");
		$.ajax({
			url : "${base }/account/rechargeCard",
			type : "POST",
			data : {
				actCode : actCode
			},
			success : function(data) {
				$("#applyBtn").removeClass("disableA");
				if (data && data.type && data.type != "success") {
					$("#errorLi").text(data.message);
					return;
				} else {
					globalJs.closeDiv("chargePopDiv");
					globalJs.showDiv("successPopDiv");
					globalJs.showDiv("popIframe");
					globalJs.showDiv("t_bg1");
					
					
				}
				//$("#typeName").text(data.userdata.desc);

			}
		});

	}
</script>

</head>

<body>

	<!--wrapper start-->
	<div class="wrapper">
		<!--header start-->

		<div class="header">
			<%@include file="../common/_header.jsp"%>

		</div>
		<!--header end-->

		<!--account start-->
	  <c:if test="${isZizhuPrint }">
		<div class="account">
			<ul>
				<li class="a1">魔数豆余额</li>
				<li class="a2"><c:choose>
						<c:when test="${account == null}">
							<i>0</i>
						</c:when>
						<c:otherwise>
							<i><fmt:formatNumber type="number"
									value="${account.availableBalance/100}" maxFractionDigits="0"
									pattern="#" /> </i>
							<c:if test="${account.frozenBalance > 0 }">
								<span>(可用)+<fmt:formatNumber type="number"
										value="${account.frozenBalance/100}" maxFractionDigits="0"
										pattern="#" />（已冻结）
								</span>
							</c:if>
						</c:otherwise>
					</c:choose></li>
				<li class="a3"><a href="javascript:void(0);"
					onclick='globalJs.showDiv("chargePopDiv")'>充值</a> <a
					href="${base }/order/loadMyOrderList">订单记录</a><span
					id="waitpayNumSpan" style="display: none;">3</span></li>
			</ul>
		</div>
	  
		<!--account end-->
		<div id="chargePopDiv" class="upgrade-box" style="display: none; font-family: 微软雅黑;">
			<div class="upgrade-box-title">
				魔数豆充值<a href="javascript:globalJs.closeDiv('chargePopDiv')">&times;</a>
			</div>
			<div class="upgrade-box-con">
				<ul>
					<li style="line-height: 30px;">请在下方输入激活码</li>
					<li><input type="text" class="form-control selected"
						id="actCode" placeholder="请输入激活码" /></li>
					<li class="error-tip" id="errorLi"></li>
					<li><a href="javascript:charge();" id="applyBtn">确认</a></li>
					<li class="upgrade-tip">如果没有激活码请到校区服务人员处购买激活卡。</li>
				</ul>
			</div>
		</div>
		<div id="successPopDiv" class="pop-up-box" style="display: none; font-family: 微软雅黑;">
			<ul>
				<li><img src="${commonResDomain}${base}/images/icon.png" width="72" height="72" alt="图标" /></li>
				<li class="t2" style="font-size: 24px; margin:30px 0;">恭喜你，充值成功！</li>
				<li style="line-height: 60px; font-size: 16px;">${alreadySetTranPwd ? "" :"为了保障您的账号安全，请先设置支付密码" }</li>
				<li class="t2"><a href="${passportDomain }/${alreadySetTranPwd ? 'priv/info/account' : 'account/resetPwdForTrans' }" class="btn1"
					style="width: 380px; padding: 0; float:none;">${alreadySetTranPwd ? "确定" : "设置支付密码" }</a></li>
			</ul>
		</div>
     </c:if>

		 

		<!--set-a start-->
		<div class="set-a">
		  <c:if test="${isZizhuPrint }">
			<a href="${passportDomain }/account/resetPwdForTrans"
				class="margin-b">${alreadySetTranPwd ? "修改" : "设置" }支付密码<span>${alreadySetTranPwd ? "" : "紧急" }</span></a>
		  </c:if>
			<a
				href="${passportDomain }/priv/info/edit?preview=true">修改资料</a><a
				href="${passportDomain }/user/pwd/toresetpage">修改登录密码</a>
		</div>
		<!--set-a end-->
	</div>
	<!--wrapper end-->

	<script>
		$(document)
				.ready(
						function() {
							$
									.ajax({
										url : WEB_ROOT
												+ "/order/loadWaitPayOrderNum",
										type : "GET",
										success : function(data) {
											if (data
													&& (data.type != "success" || data.userdata <= 0)) {
												return;
											}
											$("#waitpayNumSpan").text(
													data.userdata);
											$("#waitpayNumSpan").css("display",
													"inline");
										}
									});

						});
	</script>
</html>


