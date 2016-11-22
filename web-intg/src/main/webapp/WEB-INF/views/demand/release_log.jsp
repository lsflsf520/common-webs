<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<html>
  <head>
    <title>添加开发需求-首页</title>
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/x-style.css?_=${buildVersion}">
  </head>

  <body>
    <div class="x-contain">
      <div class="x-title">${projectName }在 ${envName }环境的最新发布日志</div>
      <div><a href="${base }/intg/demand/listEnvDemand.do?repoName=${repoName }&envName=${envName}&projectName=${projectName}">返回集成环境</a></div>
     <div class="x-list">
       <div id="logArea"></div>
       <a name="ldImg"><img id="loaderImg" alt="发布中..."  src="${projectResDomain }${base }/images/loader.gif"></a>
     </div>
     <div><a href="${base }/intg/demand/listEnvDemand.do?repoName=${repoName }&envName=${envName}&projectName=${projectName}">返回集成环境</a></div>
   </div>
   
   <script src="${commonResDomain }/assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}"
		type="text/javascript"></script>
   <script type="text/javascript">
     $(document).ready(loadLog);
     
     var startTime = new Date().getTime();
     function loadLog(){
    	 $.ajax({
    		 url : "${base}/intg/demand/loadLog.do?repoName=${repoName }&envName=${envName}&projectName=${projectName}",
 			type: "GET",
 	  	    success:function(data){
 	  	    	$("#logArea").html(data.userdata.log);
 	  	    	location.href="#ldImg";
 	  	    	if(data.userdata.inRelease == 1 && new Date().getTime() - startTime < 5 * 60 * 1000){
 	     		  //如果当前web处于正在发布状态，并且距离页面刚开始加载时的时间没有超过5分钟，则需要定时扫描日志
 	     	      setTimeout("loadLog()",1000); 
 	     	    }else{
 	     	    	$("#loaderImg").css("display", "none");
 	     	    }
 	  	    }
    	 });
     }
   </script>
  </body>
</html>
