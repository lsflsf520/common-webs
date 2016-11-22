<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<html>
  <head>
    <title>集成发布系统-首页</title>
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/x-style.css?_=${buildVersion}">
     <link rel="stylesheet" type="text/css" href="${commonResDomain }/web-passport/css/base-style.css?_=${buildVersion}">
  </head>

  <body>
    <div class="x-contain">
  <div class="top-btn">
    <input class="bg" type="button" value="开发需求" onClick="location.href='${base}/dev/demand/list.do'">
    <input type="button" value="产品需求" onClick="location.href='${base}/product/demand/list.do'">
  </div>
  <div class="new-btn"> <span style="color:red;">${errorMsg }</span> <span><a href="${base }/dev/demand/jumpToAdd.do">新建</a></span> </div>
  <div class="table-list">
    <table cellpadding="0" cellspacing="0">
        <tr>
          <th>需求名称</th>
          <th>git分支</th>
          <th>创建时间</th>
          <th>创建人</th>
          <th>预计提测时间</th>
          <th>预计上线时间</th>
          <th>状态</th>
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
            <td>${demand.dbStatus.desc }</td>
            <td>
              <a href="${base }/dev/demand/del.do?demandId=${demand.id}">废弃</a> | 
              <a href="${base }/dev/demand/detail.do?demandId=${demand.id}">详情</a>  
              <c:choose>
                <c:when test="${demand.productDemandId == null }">
                  | <a href="javascript:loadProductDemand(${demand.id});">关联产品需求</a>
                </c:when>
                <c:otherwise>
                  | <a href="${base }/product/demand/detail.do?demandId=${demand.productDemandId}">查看产品需求</a>
                </c:otherwise>
              </c:choose>
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
    	<li id="glAlertTipLi">产品需求列表</li>
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
      function loadProductDemand(devDemandId){
    	  $.ajax({
    			url : "${base}/product/demand/listNotRelateDemand.do",
    			type: "GET",
    	  	    success:function(data){
    	  	       $("#demandUl").html("");
    	  	       var content = "";
    	  		   $(data).each(function(i, item){
    	  			 content += "<tr><td>" + item.name + "</td><td><a href='${base }/dev/demand/relatePd.do?productDemandId="+item.id+"&devDemandId="+devDemandId+"' style='padding: 0 20px; height: 35px; line-height: 35px; font-size: 16px; float:none;'>关联</a></td></tr>";
    	  		   });
    	  		   if("" == content){
    	  			 content = "<tr><td>暂时没有新的产品需求</td></tr>";
    	  		   }
    	  		   $("#demandUl").append(content);
    	  		   
    	  		   showDiv("demandPopDiv");
    	  	    }
    		});
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
