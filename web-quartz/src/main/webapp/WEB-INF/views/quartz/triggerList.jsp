<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>触发器-Quartz系统</title>
<script language="JavaScript">
	$().ready(function() {
	});
	
</script>
</head>
<body>
	<div class="col-sm-10">
		<h1>触发器列表</h1>
		<table id="triggerTable" class="tablesorter">
			<thead>
				<tr>
					<th>状态</th>
					<th><em>操作</em></th>
					<th>Job名称/Job组</th>
					<th>触发器组</th>
					<th>触发器名称</th>
					<!-- <th>备注</th> -->
					<th>触发器类型</th>
					<th>开始时间</th>
					<th>停止时间</th>
					<th>上次运行时间</th>
					<th>下次运行时间</th>
					<th>计划失败</th>
				</tr>
				<c:if test="${!empty triggerList}">

					<c:forEach items="${triggerList}" var="item">
						<tr>

							<c:choose>

								<c:when test="${item.state=='NORMAL'}">
									<td><a
										href="${base}/trigger/changeState?triggerName=${item.trigger.name}&triggerGroup=${item.trigger.group}&action=PAUSED"><img
											type="image" value="pause"
											src="${projectResDomain}${base}/icons/Pause24.gif" alt="暂停"
											title="暂停触发器" /> </a></td>
								</c:when>
								<c:when test="${item.state=='PAUSED'}">
									<td><a
										href="${base}/trigger/changeState?triggerName=${item.trigger.name}&triggerGroup=${item.trigger.group}&action=NORMAL"><img
											type="image" value="start"
											src="${projectResDomain}${base}/icons/Play24.gif" alt="启动"
											title="启动触发器" /> </a></td>
								</c:when>
							</c:choose>
							<td><a
								href="${base}/trigger/delete?triggerName=${item.trigger.name}&triggerGroup=${item.trigger.group}">删除</a>
								&nbsp;</a></td>
							<td>${item.trigger.jobName}/${item.trigger.jobGroup}</td>
							<td>${item.trigger.group}</td>
							<td>${item.trigger.name}</td>
							<%-- <td>${item.trigger.description}</td> --%>
							<td>${item.trigger.getClass().getSimpleName()}</td>
							<td><fmt:formatDate value="${item.trigger.startTime}"
									type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${item.trigger.endTime}"
									type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${item.trigger.previousFireTime}"
									type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${item.trigger.nextFireTime}"
									type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${item.trigger.misfireInstruction}</td>
						</tr>
					</c:forEach>
				</c:if>
				</div>
				</div>
</body>

</html>
