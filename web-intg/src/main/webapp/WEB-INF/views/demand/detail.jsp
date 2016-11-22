<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<html>
  <head>
    <title>需求详情-首页</title>
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/x-style.css?_=${buildVersion}">
  </head>

  <body>
    <div class="x-contain">
      <div class="x-title">开发需求详情 </div>
      <div class="x-list">
      <ul>
  	   <li><span>需求名称：</span><p>${demand.name }</p></li>
       <li><span>分支名称：</span><p>${demand.branch }</p></li>
       <li><span>预计提测时间：</span><p>${demand.planTestTimeStr }</p></li>
       <li><span>预计上线时间：</span><p>${demand.planOnlineTimeStr }</p></li>
       <li><span>创建时间：</span><p>${demand.createTimeStr }</p></li>
       <li><span>创建者：</span><p>${demand.creator }</p></li>
       <li><span>状   态：</span><p>${demand.dbStatus.desc }</p></li>
       <li><span>涉及代码库：</span>
    	 <p>
    	 <table>
    	   <c:forEach items="${childList }" var="child"> 
            <tr>
    	 	  <td> <i>${child.repoName }(${child.dbStatus.desc })</i> 
    	 	    <c:if test="${child.status > 0 && child.status != 15 }">
    	 	       <i><a href="${base }/dev/demand/del.do?demandId=${child.id}">废弃</a></i> 
                   <i><a href="${base }/intg/demand/addDemand.do?repoName=${child.repoName}&envName=develop&demandId=${child.id}">集成到开发环境</a></i> 
                </c:if>
                   <i><a href="${base }/intg/demand/listEnvDemand.do?repoName=${child.repoName}&envName=develop">查看集成环境</a></i> 
              </td>
    	 	</tr>
           </c:forEach>
    	 </table>
        </p>
       </li>
       <li class="x-border" id="otherRepoDiv" style="display:none;">
            <form id="apForm" action="${base }/dev/demand/addRepos.do" >
                 <input type="hidden" name="parentId" value="${demand.id}">
             <p style="width:100%;">
               <span>可选代码库：</span>
              <c:forEach items="${repoNames }" var="item"> 
                <span>
                 <input type="checkbox" name="repoNames" value="${item }"> ${item }</span> 
              </c:forEach>
             </p>
             <p style="width:100%;">
               <span>替换已有分支：</span>
               <span><input type="radio" name="replace" value="true"/>是</span> 
               <span><input type="radio" name="replace" value="false" checked="checked"/>否</span> 
             </p>
             <p style="width:100%;">
              <input type="button" onclick="addRepos()" value="追加" />
              <input type="button" onclick="cancelRepos()" value="取消"/>
             </p>
            </form>
      </li>
     </ul>
    </div>
    <div class="new-btn">
     <c:if test="${demand.status != 15 }">
      <a href="javascript:void(0);" onclick="$('#otherRepoDiv').css('display','block')" style="float:none;">添加代码库</a>
     </c:if> 
      <a href="${base }/dev/demand/list.do" style="float:none;">开发需求列表</a> 
    </div>
   </div>
   
   <script src="${commonResDomain }/assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}"
		type="text/javascript"></script>
   <script type="text/javascript">
     function addRepos(){
    	 if($("#otherRepoDiv input[type='checkbox']:checked").length <= 0){
    		 alert("请选择至少一个代码仓库");
    		 return;
    	 }
    	 
    	 $("#apForm").submit();
     }
     
     function cancelRepos(){
    	 $("#otherRepoDiv input[type='checkbox']:checked").each(function(i,elem){
    		 $(elem).removeAttr("checked");
    	 });
    	 $('#otherRepoDiv').css('display','none')
     }
   </script>
  </body>
</html>
