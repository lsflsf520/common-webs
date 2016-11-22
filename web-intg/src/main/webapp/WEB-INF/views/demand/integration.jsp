<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${repoName }集成环境</title>
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/x-style.css?_=${buildVersion}">
    <link rel="stylesheet" type="text/css" href="${commonResDomain }/web-passport/css/base-style.css?_=${buildVersion}">
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/float_menu.css?_=${buildVersion}">
    <style>
    .pop-up-box ul{
    	width:500px;
    }
    .pop-up-box ul li{
    	width:500px;
    }
    </style>
  </head>

  <body>
    <div class="x-contain">
      <div class="top-btn">
       <input class='${envName == "develop" ? "bg" : ""}' type="button" value="开发环境" onClick="location.href='${base}/intg/demand/listEnvDemand.do?repoName=${repoName}&envName=develop'">
       <input class='${envName == "test" ? "bg" : ""}' type="button" value="测试环境" onClick="location.href='${base}/intg/demand/listEnvDemand.do?repoName=${repoName}&envName=test'">
       <input class='${envName == "pre" ? "bg" : ""}' type="button" value="预发环境" onClick="location.href='${base}/intg/demand/listEnvDemand.do?repoName=${repoName}&envName=pre'">
      <%--  <input class='${envName == "huidu" ? "bg" : ""}' type="button" value="灰度环境" onClick="location.href='${base}/intg/demand/listEnvDemand.do?repoName=${repoName}&envName=huidu'">
       --%> <input class='${envName == "master" ? "bg" : ""}' type="button" value="正式环境" onClick="location.href='${base}/intg/demand/listEnvDemand.do?repoName=${repoName}&envName=master'">
      </div>
      <div class="new-btn"><span><a href="${base}/dev/demand/list.do">回到需求列表</a></span></div>
    <div class="x-current">
      <span style="float:left; margin-right:20px;">当前git仓库：${repoName }</span>
      <span class="menu">
      <ul>
       <c:choose>
        <c:when test='${waitResolveBranch != null && "" != waitResolveBranch}'>
          <li>合并${conflictBranch } 分支时发生冲突，请先在 ${mergeRepo } 仓库的 ${waitResolveBranch } 分支上解决，然后<a href="javascript:void(0);" onclick="resolveConflict();">继续</a></li>
        </c:when>
        <c:otherwise>
         <c:if test="${inRelease == 0 }">
           <c:if test='${envName != "master" }'>
            <li><a href="${base}/intg/demand/refresh.do?repoName=${repoName }&envName=${envName }">刷新代码</a></li>
           </c:if>
            <li><a href="javascript:loadMyDemand('${repoName }', '${envName }')">添加开发需求</a></li>
           
            <li><a href="javascript:void(0);" class="hide">发 布</a>
              <ul>
              <c:forEach items="${projectNames }" var="projectName">
                <li><a href="${base }/intg/demand/releaseEnv.do?repoName=${repoName }&envName=${envName }&projectName=${projectName }">${projectName }</a></li>
              </c:forEach>
              </ul>
            </li>
           
           <c:if test="${! empty webProjectNames }">
            <li><a href="javascript:void(0);" class="hide">重 启</a>
              <ul>
              <c:forEach items="${webProjectNames }" var="projectName">
                <li><a href="${base }/intg/demand/restartEnv.do?repoName=${repoName }&envName=${envName }&projectName=${projectName }">${projectName }</a></li>
              </c:forEach>
              </ul>
            </li>
           </c:if>
         </c:if>
         <c:if test="${releaser != null }">
           <li title="发布人：${releaser }，发布时间：${lastReleaseTime }"><a href="${base }/intg/demand/queryLog.do?repoName=${repoName }&envName=${envName }&projectName=${releaseProject}" >查看发布日志</a></li>
         </c:if>
        </c:otherwise>
       </c:choose>
       </ul>
      </span>
      <span>
        <c:if test='${hasDemand && aimEnvName != null && (waitResolveBranch == null || "" == waitResolveBranch)}'>
          <a href="${base }/intg/demand/addEnv.do?repoName=${repoName}&aimEnvName=${aimEnvName}&srcEnvName=${envName}">全部合并到${aimEnvCnName }</a>
        </c:if>
      </span>
      <span style="color:red;">${errorMsg }</span>
    </div>
    <div class="table-list">
      <table cellpadding="0" cellspacing="0">
          <tr>
            <th>需求名称</th>
            <th>git分支</th>
            <th>创建时间</th>
            <th>创建人</th>
            <th>预计提测时间</th>
            <th>预计上线时间</th>
            <th>操作</th>
          </tr>
         <c:forEach items="${demandList }" var="demand">
          <tr>
            <td>${demand.name }</td>
            <td>${demand.branch }</td>
            <td>${demand.createTimeStr }</td>
            <td>${demand.creator }</td>
            <td>${demand.planTestTimeStr }</td>
            <td>${demand.planOnlineTimeStr }</td>
            <td>
              <c:if test='${"master" != envName }'>
                <a href="${base }/intg/demand/quit.do?demandId=${demand.id}&envName=${envName }&repoName=${repoName}">退出</a> | 
              </c:if>
              <a href="${base }/dev/demand/detail.do?demandId=${demand.id}">详情</a>
            </td>
          </tr>
         </c:forEach>
      </table>
    </div>
    
    <div class="repo_list">
      <span>点击可以查看代码库对应的集成列表页：</span>
      <ul>
     <c:forEach items="${repoNames }" var="item"> 
         <li><a href="${base }/intg/demand/listEnvDemand.do?repoName=${item}&envName=develop">${item }</a></li>
     </c:forEach>
      </ul> 
    </div>
  </div>   
   
    <div id="demandPopDiv" class="pop-up-box" style="display:none; min-height:440px; top:20%;">
      <ul>
      	<li><a href="javascript:closeDiv('demandPopDiv');" style="display: inline-block; font-size: 30px; color: rgb(119, 119, 119); float: right; margin-top: -30px;">x</a></li>
    	<li id="glAlertTipLi">请选择开发需求</li>
        <li class="t2" style="height:400px; overflow-y:auto; overflow-x:hidden;">
          <table id="demandUl" cellpadding="0" cellspacing="0" width="100%" >
                
          </table>
        </li>
      </ul>	
    </div>

    <div id="t_bg1" class="t_bg1" style="display:none;"></div>
    <iframe id='popIframe' class='popIframe' frameborder='0' style="display:none;"></iframe>
    
    <script src="${commonResDomain }/assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}"
		type="text/javascript"></script>
    <script type="text/javascript">
      function loadMyDemand(repoName, envName){
    	  $.ajax({
    			url : "${base}/intg/demand/ajaxList.do?repoName=" + repoName + "&envName=" + envName,
    			type: "GET",
    	  	    success:function(data){
    	  	       $("#demandUl").html("");
    	  	       var content = "";
    	  		   $(data).each(function(i, item){
    	  			 content += "<tr><td>" + item.name + "</td><td>" + item.branch + "</td><td><a href='${base }/intg/demand/addDemand.do?repoName=${repoName}&envName=${envName}&demandId="+item.id+"' style='padding: 0px;height: 35px;line-height: 35px;font-size: 16px;float: none;width: 70px;text-align: center;'>添加</a></td></tr>";
    	  		   });
    	  		   if("" == content){
    	  			 content = "<tr><td>您暂时没有开发中的需求，<a href='${base}/dev/demand/jumpToAdd.do'  style='padding: 0px;height: 35px;line-height: 35px;font-size: 16px;float: none;width: 70px;text-align: center;'>新建一个需求</a></td></tr>";
    	  		   }
    	  		   $("#demandUl").append(content);
    	  		   
    	  		   showDiv("demandPopDiv");
    	  	    }
    		});
      }
      
      function resolveConflict(){
    	  if(confirm("请先确定${waitResolveBranch}分支已经在gerrit上审核通过之后，再继续，否则很麻烦哦！\n\n确定要继续吗？")){
    		  location.href="${base }/intg/demand/resolve.do?envName=${envName }&repoName=${repoName}";
    	  }
      }
      
      function showDiv(divId){
		    document.getElementById(divId).style.display='block';
			document.getElementById('popIframe').style.display='block';
			document.getElementById('t_bg1').style.display='block';
		 }
	  function closeDiv(divId){
			document.getElementById(divId).style.display='none';
			document.getElementById('t_bg1').style.display='none';
			document.getElementById('popIframe').style.display='none';
	  }
    </script>
  </body>
</html>
