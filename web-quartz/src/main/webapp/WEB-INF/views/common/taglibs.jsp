<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 

<%
	pageContext.setAttribute("base", request.getContextPath());
%>

<script type="text/javascript">
	var PROJECT_RES_DOMAIN = "${projectResDomain}";
	var COMMON_RES_DOMAIN = "${commonResDomain}";
</script>
 
<link rel=stylesheet type=text/css
	href="${projectResDomain}${base}/include/style/default.css" />
<link rel=stylesheet type=text/css
	href="${projectResDomain}${base}/include/style/display.css" />
<link rel=stylesheet type=text/css
	href="${projectResDomain}${base}/include/style/layout.css" />
<link rel=stylesheet type=text/css
	href="${projectResDomain}${base}/include/style/form.css" />
<script type="text/javascript"
	src="${projectResDomain}${base}/include/js/jquery/jquery-1.9.1.min.js"></script>
<link rel="stylesheet"
	href="${projectResDomain}${base}/include/js/jquery/tablesorter/themes/blue/style.css"
	type="text/css" media="print, projection, screen" />
<script type="text/javascript"
	src="${projectResDomain}${base}/include/js/jquery/tablesorter/jquery.tablesorter.min.js"></script>

<script type="text/javascript"
	src="${commonResDomain }/assets/plugins/laydate/laydate.dev.js?v=1.1"></script>
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/laydate/need/laydate.css?v=1.1" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/laydate/skins/default/laydate.css?v=1.1"
	id="LayDateSkin" />

<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/layer/skin/layer.css?_=${buildVersion}" />
<link rel="stylesheet" type="text/css"
	href="${commonResDomain }/assets/plugins/layer/skin/layer.ext.css?_=${buildVersion}" />


<link
	href="${commonResDomain }/assets/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

 <script src="${projectResDomain}${base}/js/sweetalert.min.js"></script>
	 <link rel="stylesheet" type="text/css" href="${projectResDomain}${base}/css/sweetalert.css">

<div class="row">
	<div class="col-sm-2">
		<div class="subbar nav-list">
			<ul>
				<li class="public-class"><a data-toggle="collapse"
					data-parent="#accordion"
					href="${base}/schedule/chooseSchedule"> <i
						class="icon"></i><span class="title">调度计划</span> <i class="arrow1"></i>
				</a></li>
				
				<li class="public-class"><a data-toggle="collapse"
					data-parent="#accordion"
					href="${base}/definition/list"> <i
						class="icon"></i><span class="title">任务模板</span> <i class="arrow1"></i>
				</a></li>
				
					<li class="public-class"><a data-toggle="collapse"
					data-parent="#accordion"
					href="${base}/Job/jobList"> <i
						class="icon"></i><span class="title">任务列表</span> <i class="arrow1"></i>
				</a></li>
				
					<li class="public-class"><a data-toggle="collapse"
					data-parent="#accordion"
					href="${base}/trigger/list"> <i
						class="icon"></i><span class="title">触发器</span> <i class="arrow1"></i>
				</a></li>
		</div>
		</li>
		</ul>


	</div>
