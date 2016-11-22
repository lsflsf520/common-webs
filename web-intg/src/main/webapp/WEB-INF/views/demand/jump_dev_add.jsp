<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<html>
  <head>
    <title>添加开发需求-首页</title>
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/x-style.css?_=${buildVersion}">
  </head>

  <body>
    <div class="x-contain">
      <div class="x-title">新建开发需求</div>
    <form id="demandForm" action="${base }/dev/demand/add.do" method="post">
     <div class="x-list">
       <ul>
      	  <li><span>需求名称：</span><p><input type="text" name="name" id="name" style="padding-left:10px; width:500px; height:30px; line-height:30px;"  /></p></li>
      	  <li><span>所属代码库：</span>
      	    <p id="repoP">
      	     <c:forEach items="${repoNames }" var="repoName">
               <span><input type="checkbox" name="repoNames" value="${repoName }"/>${repoName }</span>
             </c:forEach>
            </p>
          </li>
      	  <li><span>分支名称：</span><p><input type="text" name="branch" id="branch" style="padding-left:10px; height:30px; line-height:30px;"  /></p></li>
      	  <li><span>替换已有分支：</span>
      	    <p>
      	      <span><input type="radio" name="replace" value="true"/>是</span> 
              <span><input type="radio" name="replace" value="false" checked="checked"/>否</span> 
            </p>
          </li>
      	  <li><span>预计提测时间：</span><p><input type="text" id="planTestTime" name="planTestTime" onclick="laydate()" style="padding-left:10px; height:30px; line-height:30px;"  /></p></li>
      	  <li><span>预计上线时间：</span><p><input type="text" id="planOnlineTime" name="planOnlineTime" onclick="laydate()" style="padding-left:10px; height:30px; line-height:30px;"  /></p></li>
      	</ul>
      </div>
      <div class="new-btn">
       <input type="button" onclick="submitDemand()" value="提 交" style="float:none;" />
       <a href="${base }/dev/demand/list.do" style="float:none; border:1px solid #0080ef; color:#0080ef; background-color:#fff;">取 消</a>
      </div>
    </form>
   </div>
   
   <script src="${commonResDomain }/assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}"
		type="text/javascript"></script>
   <script type="text/javascript"
	       src="${commonResDomain }/assets/plugins/laydate/laydate.dev.js?_=${buildVersion}"></script>
   <link rel="stylesheet" type="text/css"
	       href="${commonResDomain }/assets/plugins/laydate/need/laydate.css?_=${buildVersion}" />
   <link rel="stylesheet" type="text/css"
	       href="${commonResDomain }/assets/plugins/laydate/skins/default/laydate.css?_=${buildVersion}" id="LayDateSkin"/>
   <script type="text/javascript">
     function submitDemand(){
    	 if(!$("#name").val()){
    		 alert("需求名称不能为空");
    		 return;
    	 }
    	 
    	 if($("#repoP input[type='checkbox']:checked").length <= 0){
    		 alert("必需要选择至少一个代码库");
    		 return;
    	 }
    	 if(!$("#branch").val() || !/^[a-zA-Z0-9_\-]+$/.test($("#branch").val())){
    		 alert("分支名称包含非法字符（只能包含英文、数字、下划线或中划线）");
    		 return;
    	 }
    	 if("master" == $("#branch").val().replace(/(^\s*)|(\s*$)/g,"")){
    		 alert("master分支属于特殊分支，不能新建，请修改后重试！");
    		 return;
    	 }
    	 
    	 var currTime = new Date().getTime();
    	 if(!$("#planTestTime").val() || !$("#planOnlineTime").val() || (Date.parse($("#planTestTime").val()) + 24 * 3600 * 1000)  < currTime || $("#planOnlineTime").val() < $("#planTestTime").val()){
    		 alert("预计提测时间和预计上线时间不能为空，也不能早于当前时间，且预计上线时间不能小于预计提测时间");
    		 return;
    	 }
    	 
    	 $("#demandForm").submit();
     }
   </script>
  </body>
</html>
