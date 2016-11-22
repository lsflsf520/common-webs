<%@ page import ="java.util.*,java.text.*" language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>查看任务信息-Quartz系统</title>
<%
SimpleDateFormat template = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
%>
</head>
<body>

	<div class="col-sm-10">
		<link rel="stylesheet"
			href="/web-timetask/include/js/jquery/tablesorter/themes/blue/style.css"
			type="text/css" media="print, projection, screen" />
		<h1>Job细节特性</h1>
		
		<form name="jobDetailForm" method="post" action="viewJobDetail.do">
			<table align="left">
				<tr>
					<td>Job组</td>
				  <td><input type="hidden" name="groupName" value="testJobs" />${jobDetail.group}</td>
				</tr>
				<tr>
					<td>Job名称</td>
				  <td><input type="hidden" name="name" value="testJob3">${jobDetail.name}</td>
				</tr>
				<tr>
					<td>Job的Java类</td>
					<td>${jobDetail.jobClass.getName()}</td>
				</tr>
				<tr>
					<td>备注</td>
					<td>${jobDetail.description}</td>
				</tr>
				<tr>
					<td>恢复</td>

					<td><input type="checkbox" name="jobDetail.requestsRecovery"
						value="${jobDetail.requestsRecovery()}"
						checked="${jobDetail.requestsRecovery()}"></td>
				</tr>
				<tr>
					<td>保持结束后</td>
					<td><input type="checkbox" name="jobDetail.durability"
						value="${jobDetail.isDurable()}"
						checked="${jobDetail.isDurable()}"></td>
				</tr>
		  </table>
			<table>
			</table>
			<table style="margin: auto;">
              <tr>
                <td>变量名</td>
                <td>值</td>
              </tr>
              <tr>
                <c:if test="${!empty jobDetail.jobDataMap}">
                <td><c:forEach items="${jobDetail.jobDataMap}"
									var="parameter">
                </td>
              </tr>
			  <tr>
                <td>${parameter.key}</td>
			    <td>${parameter.value}</td>
		      </tr>
                    </c:forEach>
                     </c:if>
  </tr>
</table>
	<h3>触发器</h3>
				<c:choose>
					<c:when test="${!empty triggers}">
					
						<table class="tablesorter">
							<tr>
								<td><em>操作</em></td>
								<td>触发器组</td>
								<td>触发器名称</td>
								<td>触发器类型</td>
								<td>触发器状态</td>
								<td>上次执行时间</td>
								<td>下次执行时间</td>
							</tr>
							<tr>
								<c:forEach items="${triggers}" var="trigger">
									<td>修改|删除</td>
									<td>${trigger.trigger.getKey().name}</td>
									<td>${trigger.trigger.getKey().group}</td>
									<td>${trigger.trigger.getClass().getName()}</td>
								    <td>${trigger.state}</td>
									<td> 
									<fmt:formatDate value="${trigger.trigger.getPreviousFireTime()}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
									
							  </td>
									 <td> 
								    <fmt:formatDate value="${trigger.trigger.getNextFireTime()}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
									 </td>					
							</tr>
							</c:forEach>
	</c:when>
	<c:otherwise>
  没关联任何触发器  
   </c:otherwise>
				</c:choose>
			</table>
			
			<h3>操作</h3>
			&nbsp;<a
				href="${base}/Job/editJob?jobName=${jobDetail.name}&jobGroup=${jobDetail.group}">编辑</a>
			&nbsp;<a
				href="${base}/Job/deleteJob?jobName=${jobDetail.name}&jobGroup=${jobDetail.group}">删除</a>
			&nbsp;<a
				href="${base}/Job/runNow?jobName=${jobDetail.name}&jobGroup=${jobDetail.group}">立即运行</a>
			&nbsp;<a
				href="${base}/trigger/inputTrigger?jobName=${jobDetail.name}&jobGroup=${jobDetail.group}">>关联触发器</a>
		</form>
	</div>

	

	 
	</div>
</body>
</html>
