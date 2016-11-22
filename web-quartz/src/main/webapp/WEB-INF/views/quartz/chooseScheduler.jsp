<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
  <title>选择调度器-Quartz系统</title>
   
		<script language="JavaScript">
			 $().ready(function() { 
			 	
			 	$("#controls > button").click(
			 		function() {
						$("#command").attr("value", this.value);
						$('#frmChooseScheduler').submit();
					}
				);	
				$('#controls > button').tooltip();
			  }); 
		</script>
</head>
<body>
<div class="col-sm-10">
<form id="frmChooseScheduler" name="chooseSchedulerForm" method="get" action="${base}/schedule/chooseSchedule">
<input type="hidden" id="command" name="command" value=""/>
<table>
	<tr>
		<td width="200">调度</td>
		<td>
		<select id="schedulerName" name="schedulerName" onchange="submit()">
		 <c:forEach items="${scheduleInfo.schedulers}" var="schedule">
	    	<option id="${schedule.schedulerName}" value="${schedule.schedulerName}">${schedule.schedulerName}</option>
	    </c:forEach>
		</select>
		</td>
	</tr>
	<tr>
		<td>计划名称</td><td>${scheduleInfo.scheduler.schedulerName}</td>
	</tr>
	<tr>
		<td>状态</td><td>${scheduleInfo.scheduler.state}</td>
	</tr>
	<tr>
		<td>启动时间</td><td>${scheduleInfo.scheduler.runningSince}</td>
	</tr>
	<tr>
		<td>已执行Job数</td><td>${scheduleInfo.scheduler.numJobsExecuted}</td>
	</tr>
	<tr>
		<td>持久类型</td><td>${scheduleInfo.scheduler.persistenceType}</td>
	</tr>
	<tr>
		<td>线程池大小</td><td>${scheduleInfo.scheduler.threadPoolSize}</td>
	</tr>
	<tr>
		<td>版本</td><td>${scheduleInfo.scheduler.version}</td>
	</tr>
	<tr>
	
	</tr>
</table>	
	<span id="controls">
	<button name="play" value="start" type="submit" title="启动"><img type="image"  value="start" src="${projectResDomain}${base}/icons/Play24.gif" alt="启动调度器" title="Start Scheduler" /></button>
	<button name="pause" value="pause" type="submit" title="暂停"><img type="image" value="pause" src="${projectResDomain}${base}/icons/Pause24.gif"  alt="暂停调度器"  /></button> 
	<button name="stop" value="stop" type="submit" title="停止"><img type="image" value="stop" src="${projectResDomain}${base}/icons/Stop24.gif"  alt="停止调度器"  /></button>
	<button name="waitAndStop" value="waitAndStopScheduler" type="submit"  title="待任务完成后停止"><img type="image" value="waitAndStopScheduler" src="${projectResDomain}${base}/icons/Stop24.gif"  /> &nbsp;</button>
	</span>设置此调度作为当前的调度器 <input type="submit" class="submit" value="set" property="btnSetSchedulerAsCurrent"/>
 
</form>
<!--  <br>当前执行的Job<table>
<tr>
<td>Job组</td>
<td>Job名称</td>
<td>备注</td>
<td>Job的Java类</td>
</tr> -->
<%--  <c:forEach items="${scheduleInfo.executingJobs}" var="job">
	  <tr>
		<td>${job.groupName}</td>
		<td>${job.name}</td>
		<td>${job.description}</td>
		<td>${job.jobClass}</td>
	</tr>
 </c:forEach>
</table> 
<table>
	<tr>
		<td width="30">
			<img src="${projectResDomain}${base}/icons/Pause24.gif" value="btnPauseAllJobs" alt="Pause all jobs"/>
		</td>
		<td width="30">
			<img src="${projectResDomain}${base}/icons/Play24.gif" value="btnResumeAllJobs" alt="Resume all jobs"/>
		</td>
	</tr>
</table>
--%>
 
</table>
 </div> 
 </div>
</body>
</html>
 