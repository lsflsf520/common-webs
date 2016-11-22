<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
  <title>任务列表-Quartz系统</title>
 <script type="text/javascript">
$(document).ready( function() {
	 $("#jobTable").tablesorter({
	 		 widgets: ['zebra'],
	 		 headers: { 
            	// assign the secound column (we start counting zero) 
            	0: { 
                	// disable it by setting the property sorter to false 
                	sorter: false 
            	} 
            }
   	});
	 $("#searchName").focus(); 	
	 //$("#summaryTable").tableHover(); 
});
 </script>
</head>
<body>

 


<div class="col-sm-10">
<h3><s:text name="title.listAllJobs" /></h3>
根据Job名搜索(Find job by name):
<br />
<form name="JobSearchForm" method="post"
	action="${base}/Job/jobList?seachName="${searchName}>
	<input type="text"
	id="searchName" name="searchName" value="" /> 
	<input type="submit" value="搜索(Search)"  name="jobSearchSubmit" />
</form>
<br />
<br />
<table id="jobTable" cellspacing="0" cellpadding="3" class="tablesorter">
    <thead>
    <tr>
	<th class="{sorter: false}"><em>操作</em></th>
        <th>任务组</th>
        <th>任务id</th>
        <th>任务简介</th>
        <th>Job的Java类</th>      
   </tr>
   </thead><tbody>
 
             <c:if test="${!empty jobList}"> 
        <c:forEach items="${jobList}" var="item">
         <tr >
           <td nowrap="true">
        <a href='${base}/Job/viewJob?jobName=${item.name}&jobGroup=${item.group}'>查看</a> |
		<a href='${base}/Job/editJob?jobName=${item.name}&jobGroup=${item.group}'>编辑</a> |
		<a href='${base}/Job/deleteJob?jobName=${item.name}&jobGroup=${item.group}'>删除</a> |
		<a href='${base}/Job/runNow?jobName=${item.name}&jobGroup=${item.group}'>立即执行</a>
       </td>
        <td>${item.group}</td>
        <td>${item.name}</td>
        <td>${item.description}</td>
        <td>${item.jobClass}</td>
           </tr>
        </c:forEach>
        </c:if>
    </tbody>
    </table>
</div>

 </div>
</div>
</body>
</html>
 