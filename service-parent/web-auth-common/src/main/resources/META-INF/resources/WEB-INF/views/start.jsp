<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="./common/taglibs.jsp"%>
<%@ include file="./common/app-ver.jsp"%>


<html>
<head>
<link rel="shortcut icon" href="${commonResDomain }/favicon.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- Basic Javascripts -->
<title>首页</title>
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/bootstrap-datepicker/css/datepicker.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/bootstrap-timepicker/compiled/timepicker.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/bootstrap-colorpicker/css/colorpicker.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/bootstrap-editable/bootstrap-editable/css/bootstrap-editable.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/jquery-ui/redmond/jquery-ui-1.10.3.custom.min.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/extras/jquery-jqgrid/plugins/ui.multiselect.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/extras/jquery-jqgrid/css/ui.jqgrid.css?_=${buildVersion}">
<link rel="stylesheet" type="text/css"
	href="${projectResDomain}${base}/assets/app/bootstrap-jqgrid.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css" href="${commonResDomain }/assets/plugins/layer/skin/layer.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css" href="${commonResDomain }/assets/plugins/layer/skin/layer.ext.css?_=${buildVersion}" />


<link href="${commonResDomain }/assets/plugins/bootstrap/css/bootstrap.min.css?_=${buildVersion}"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${commonResDomain}${base}/css/base.css?_=${buildVersion}">
<style>
body {
	background-color: #ebe9e4;
	font-family: 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial;
}

ul {
	padding: 0;
	margin: 0;
	list-style-type: none;
}
</style>

</head>

<body>

	<!--wrapper start-->
	<div class="wrapper">
		<!--header start-->
		<div class="header">
			<!--header-wrapper start-->
			<div class="header-wrapper">
				<div class="row">
					<div class="col-sm-8">
						<a href="javascript:void(0)"><img src="${commonResDomain}/assets/images/logo.png" alt="logo" /></a>
					</div>
					<div class="col-sm-4">
						<div class="login-info">
							<ul>
								<li>${userName}</li>
								<li class="border"></li>
								<li><a href="${passportDomain }/user/login/doLogout">退出</a></li>
							</ul>
						</div>
					</div>

				</div>
			</div>
			<!--header-wrapper end-->
		</div>
		<!--header end-->

		<!--main start-->
		<div class="main">
			<!--main-wrapper start-->
			<div class="main-wrapper" style="min-height: 700px;">
				<div class="row">
					<div class="col-sm-2">
						<div class="subbar nav-list">
							<ul>
								<c:forEach items="${rootMenus }" var="root" varStatus="status">
									<li class="public-class"><a data-toggle="collapse"
										data-parent="#accordion" href="#collapse${status.index }">
											<i class="icon"></i><span class="title">${root.name }</span>
											<i class="arrow1"></i>
									</a> <c:if test="${root.childrenSize>0 }">
											<div id="collapse${status.index }"
												class="panel-collapse ${root.open?'in':'' } collapse">
												<c:forEach items="${root.children }" var="childLevel2">
													<p>
														<a class="ajaxify" data-code="${childLevel2.code }"
															href-org="${childLevel2.projectDomain}${childLevel2.url }"
															rel="address:/${childLevel2.code == null ? childLevel2.id : childLevel2.code}">
															<span class="title">${childLevel2.name }</span>
														</a>
													</p>
												</c:forEach>
											</div>
										</c:if></li>
								</c:forEach>
								<%-- <!-- 增加登出功能 -->
                                        <li class="public-class">
                                          <a href="${base}/j_spring_security_logout"> 
                                                <i class="icon"></i><span class="title">退出</span><i class="arrow1"></i>
                                          </a>
                                        </li> --%>
							</ul>
						</div>
					</div>
					<div class="col-sm-10">
						<div class="alert alert-info"
							style="position: absolute; margin: 0px auto; right: 10px; text-align: center; display: none;"
							id="simplehint">正在获取数据，请稍候......</div>
						<!--right-conbtent start-->
						<div class="right-content" style="min-height: 430px;">
							<div class="welcome">
								<!-- <i class="time_icon day"></i> -->
								<h3 style="line-height: 32px;">你好，${userName}</h3>
							</div>
						</div>
						<!--right-conbtent end-->
					</div>
				</div>
			</div>
			<!--main-wrapper end-->
		</div>
		<!--main end-->

		<!--footer start-->
		<div class="footer">
			<div class="footer-wrapper">
				<h5>©2015-2016 beijingchuangshu</h5>
			</div>
		</div>
		<!--footer end-->
	</div>
	<!--wrapper end-->
	<script src="${commonResDomain }/assets/plugins/compat/respond.js?_=${buildVersion}"></script>
    <script src="${commonResDomain }/assets/plugins/compat/ie8-responsive-file-warning.js?_=${buildVersion}"></script>
    <script src="${commonResDomain }/assets/plugins/compat/html5.js?_=${buildVersion}"></script>
	<script src="${commonResDomain }/assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script src="${commonResDomain }/assets/plugins/jquery.blockui.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script
		src="${commonResDomain }/assets/plugins/bootstrap/js/bootstrap.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script
		src="${commonResDomain }/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script
		src="${commonResDomain }/assets/plugins/jquery-migrate-1.2.1.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script
		src="${commonResDomain }/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script src="${commonResDomain }/assets/plugins/jquery.bgiframe.min.js?_=${buildVersion}"
		type="text/javascript"></script>
	<script src="${commonResDomain }/assets/plugins/jquery.jqpopup.min.js?_=${buildVersion}"
		type="text/javascript"></script>

	<script src="${commonResDomain }/assets/extras/jquery.address/jquery.address-1.5.min.js"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js?_=${buildVersion}"
		charset="UTF-8"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/clockface/js/clockface.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/bootstrap-daterangepicker/daterangepicker.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/bootstrap-timepicker/js/bootstrap-timepicker.js?_=${buildVersion}"></script>
	<script
		src="${commonResDomain }/assets/extras/jquery-jqgrid/plugins/ui.multiselect.js?_=${buildVersion}"></script>
	<script src="${commonResDomain }/assets/extras/jquery-jqgrid/js/i18n/grid.locale-cn.js?_=${buildVersion}"></script>
	<script src="${commonResDomain }/assets/extras/jquery-jqgrid/js/jquery.jqGrid.src.js?_=111"></script>
	
	<script src="${commonResDomain }/assets/plugins/layer/layer.js?_=${buildVersion}"></script>
	<script src="${commonResDomain }/assets/plugins/plupload/js/plupload.full.min.js?_=${buildVersion}"></script>

	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/bootstrap-daterangepicker/daterangepicker.js?_=${buildVersion}"></script>
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
		
	</script>
	<!-- layDate -->
	<script type="text/javascript"
		src="${commonResDomain }/assets/plugins/laydate/laydate.dev.js?_=${buildVersion}">
		
	</script>
	<script type="text/x-mathjax-config">
    MathJax.Hub.Config({
        tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}});
    </script>
	<script type="text/javascript">
		var WEB_ROOT = "${base}";
		var AUTH_USER = {
			uid : '${userId}',
			username : '${realName}'
		};
	</script>

	<script src="${projectResDomain}${base}/assets/app/util.js?_=${buildVersion}"></script>
	<script src="${projectResDomain}${base}/assets/app/global.js?_=${buildVersion}"></script>
	<script src="${projectResDomain}${base}/assets/app/page.js?_=${buildVersion}"></script>
	<script src="${projectResDomain}${base}/assets/app/grid.js?_=${buildVersion}"></script>
	<script src="${projectResDomain}${base}/assets/scripts/app.js?_=${buildVersion}"></script>
	<script src="${projectResDomain}${base}/assets/app/upload-problem.js?_=${buildVersion}"></script>
	<script>
	
		var _ajax_loading_flag = 0;
		$(function() {
			$(".nav-list .ajaxify").each(function(e){
                this.setAttribute("href",this.getAttribute("href-org"));
            });
			/* $("simplehint").hide;
			$("#simplehint").ajaxStart(function(){
				$(this).show();
			});
			$("#simplehint").ajaxStop(function(){
				$(this).hide();
			});	 */
			$(document).ajaxStart(showLoading).ajaxStop(hiddenLoading)
					.ajaxError(errorHandler);
			// console.profile('Profile Sttart');
			
			App.init();
			Util.init();
			Global.init();
		});
		function showLoading() {
			setTimeout(
					function() {
						if (_ajax_loading_flag == 0) {
							$
									.blockUI({
										message : "<img src='${commonResDomain}/assets/images/loading.gif" + "' /> 加载中.... ",
										css : {
											border : 'none',
											padding : '15px',
											backgroundColor : '#647fbc',
											'-webkit-border-radius' : '10px',
											'-moz-border-radius' : '10px',
											opacity : .5,
											color : '#000'
										}
									});
						}
					}, 500);
			_ajax_loading_flag = 0;
		}
		function hiddenLoading() {
			_ajax_loading_flag = 1;
			$.unblockUI();
		}
		function errorHandler(e, xhr, opt) {
			alert("数据加载错误!");
			_ajax_loading_flag = 0;
		}
	</script>

</body>
</html>
