<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
  <title>修改Job-Quartz系统</title>
    
</head>
<body>
<div class="col-sm-10">
<h1>修改Job</h1>
 
   <form name="jobDetailForm" method="post" action="${base}/Job/updatejob">
<table>
	<tr>
		<td>任务组</td>
		<td><input type="text" name="groupName" readonly="readonly" value="${jobDetail.group}"/></td>
	</tr>
	<tr>
		<td>任务ID</td>
		<td><input type="text" name="name" readonly="readonly" value="${jobDetail.name}"/> </td>
	</tr>
	<tr>
		<td>Job的Java类</td>
		<td><input readonly="readonly" type="text" name="jobClass" size="27" value="${jobDetail.jobClass.getName()}" /></td>
	</tr> 																			
	<tr>
		<td>任务简介</td>
		<td><textarea name="description">${jobDetail.description} </textarea></td>
	</tr>
	<tr>
		<td>恢复</td>
		<td><input type="checkbox" name="recoveryRequesting" value="${jobDetail.requestsRecovery()}" checked="${jobDetail.requestsRecovery()}" /></td>
	</tr>
	<tr>
		<td>任务完成之后是否依然保留到数据库</td>
		<td><input type="checkbox" name="durability" value="${jobDetail.isDurable()}" checked="${jobDetail.isDurable()}" /></td>
	</tr>
 

<h3>选择Job</h3>

<h3>Job参数</h3>
 <tr>
		<td>参数名</td>
		<td>值</td>
   </tr>
   <c:if test="${!empty jobDetail.jobDataMap}">   
         <c:forEach items="${jobDetail.jobDataMap}" var="parameter">
         <tr>
           <input type="hidden" name="parameterNames" value="${parameter.key}"/> 
         
         <td> ${parameter.key} </td>
        <td>  <input type="text" name="parameterValues" value="${parameter.value}"/></td>
         </tr>
          </c:forEach>
  </c:if>
 
  
  </form>
</table>
 
<input type="submit"  class="submit" name="saveAction" value="保存"/>
<form>


</div>
</div>
 </div>
</body>
</html>
 