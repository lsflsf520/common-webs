<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
  <title>任务模板-Quartz系统</title>
   
<script type="text/javascript">
$(document).ready( function() {
	 $("#definitionTable").tablesorter({
	 		 widgets: ['zebra'],
	 		  headers: { 
            	// assign the secound column (we start counting zero) 
            	0: { 
                	// disable it by setting the property sorter to false 
                	sorter: false 
            	} 
            }
		});
});
 </script>
</head>
<body>
 
<div class="col-sm-10">
<h3>可用的Job模板</h3>
<!-- <i>Job模板是通过init()加载的</i><br/> -->
<!--  <a href="${base}/definition/input">创建Job模板</a>-->
<!--<a href="${base}/definition/readXML">Job模板XML文件</a>-->
<table id="definitionTable" cellspacing="0" cellpadding="3" width="100%"  class="tablesorter">
    <thead>
    <tr>
	<th class="{sorter: false}"><em>操作</em></th>
        <th>任务id</th>
        <th>任务简介</th>
        <th>Java类</th>
        <th>参数信息:</th>
   </tr>
   </thead><tbody>
  
     <c:if test="${!empty defList}"> 
        <c:forEach items="${defList}" var="item">
         <tr>
        <td  nowrap="true"><a href="${base}/Job/start?definitionName=${item.name}">创建Job</a>
       <!-- <a href="edit.action?definitionName=${item.name}">查看</a>-->
        <!--  <a onclick="javascript:return confirm('删除Job模板');" href="delete.action?definitionName=${item.name}">删除</a> -->
        </td>
        <td>${item.name}</td>
        <td>${item.description}</td>
        <td>${item.className}</td>
        <c:if test="${!empty item.parameters}">  
         <td>
         <c:forEach items="${item.parameters}" var="parameter">
          ${parameter.name}= 
           ${parameter.required}<br>
          </c:forEach> 
          </td>
          </c:if>
           </c:forEach> 
           </tr>
     </c:if> 
    </tr>
    </tbody>
    </table>
</div>

</div>
</div>
</div>
</body>
</html>
 