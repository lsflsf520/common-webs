<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<html>
  <head>
    <title>集成发布系统-首页</title>
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/x-style.css?_=${buildVersion}">
  </head>

  <body>
   <div class="x-contain">
     <div class="top-btn">
       <input type="button" value="开发需求" onClick="location.href='${base}/dev/demand/list.do'">
       <input class="bg" type="button" value="产品需求" onClick="location.href='${base}/product/demand/list.do'">
     </div>
     
     <div><span style="color:red;">${errorMsg }</span></div>
     <div class="new-btn"> 
      <form action="${base }/product/demand/list.do" method="get">
       <span style="float:left; display: inline-block; margin-top:15px;">
         <select id="projSel" name="projectName" onchange="loadModules();" style="float:left; height:35px; margin-right:10px;">
           <option value="">请选择所属产品</option>
           <c:forEach items="${projectNameMap }" var="proj">
      	     <option value="${proj.key }" ${proj.key == projectName ? "selected" : "" }>${proj.value }</option>
      	   </c:forEach>
         </select>
         <select id="moduleSel" name="moduleName" style="float:left; height:35px;">
           <option value="">请选择所属模块</option>
           <c:forEach items="${firstProjModuleMap }" var="module">
      	     <option value="${module.key }" ${module.key == moduleName ? "selected" : "" }>${module.value }</option>
      	   </c:forEach>
         </select>
         <a href="" style="float:left;">查 询</a>
       </span>
      </form>
       <span><a href="${base }/product/demand/jumpToAdd.do">新建</a></span> 
     </div>
     <div class="table-list">
      <table cellpadding="0" cellspacing="0">
        <tr>
          <th>需求名称</th>
          <th>期望上线时间</th>
          <th>所属产品</th>
          <th>所属模块</th>
          <th>开发负责人</th>
          <th>测试负责人</th>
          <th>创建时间</th>
          <th>创建人</th>
          <th>状态</th>
          <th>操作</th>
        </tr>
         <c:forEach items="${demandList }" var="demand">
          <tr>
            <td>${demand.name }</td>
            <td>${demand.expectedTimeStr }</td>
            <td>${demand.projectCnName }</td>
            <td>${demand.moduleCnName }</td>
            <td>${demand.devLeader }</td>
            <td>${demand.testLeader }</td>
            <td>${demand.createTimeStr }</td>
            <td>${demand.creator }</td>
            <td>${demand.dbStatus.desc }</td>
            <td>
              <a href="${base }/product/demand/del.do?demandId=${demand.id}">废弃</a> | 
              <a href="${base }/product/demand/detail.do?demandId=${demand.id}">详情</a> 
            </td>
          </tr>
         </c:forEach>
      </table>
     </div>
   </div>
   
   <script src="${commonResDomain }/assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}"
		type="text/javascript"></script>
   <script type="text/javascript">
     function loadModules(){
		 var projectName = $("#projSel").val();
		 
		 $("#moduleSel").html("");
		 $.ajax({
			url : "${base}/product/demand/loadModules.do?projectName=" + projectName,
			type: "GET",
	  	    success:function(data){
	  	    	var optionText = '';
	  	    	
	  	    	for(var o in data){
	  	    		optionText += '<option value="'+o+'">'+data[o]+'</option>';
	  	    	}
	  	    	
	  	    	$("#moduleSel").append(optionText);
	  	    }
		 });
	   }
   </script>
  </body>
</html>
