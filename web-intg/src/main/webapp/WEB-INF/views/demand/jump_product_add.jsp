<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<html>
  <head>
    <title>添加产品需求-首页</title>
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/x-style.css?_=${buildVersion}">
  </head>

  <body>
    <div class="x-contain">
      <div class="x-title">新建产品需求</div>
     <form id="demandForm" action="${base }/product/demand/add.do" method="post">
      <div class="x-list">
        <ul>
      	  <li><span>需求名称：</span><p><input type="text" id="name" name="name" style="padding-left:10px; width:500px; height:30px; line-height:30px;"  /></p></li>
      	  <li><span>所属产品：</span>
      	      <p>
      	        <select name="project" id="projSel" onchange="loadModules();">
      	          <c:forEach items="${projectNameMap }" var="proj">
      	            <option value="${proj.key }">${proj.value }</option>
      	          </c:forEach>
      	        </select>
      	      </p>
      	  </li>
      	  <li><span>所属模块：</span>
      	      <p>
      	        <select name="module" id="moduleSel">
      	          <c:forEach items="${firstProjModuleMap }" var="module">
      	            <option value="${module.key }">${module.value }</option>
      	          </c:forEach>
      	        </select>
      	      </p>
      	  </li>
      	  <li><span>开发负责人：</span>
      	      <p>
      	        <select name="devLeader">
      	          <c:forEach items="${userMap }" var="user">
      	            <option value="${user.value }">${user.value }</option>
      	          </c:forEach>
      	        </select>
      	      </p>
      	  </li>
      	  <li><span>测试负责人：</span>
      	      <p>
      	        <select name="testLeader">
      	          <c:forEach items="${userMap }" var="user">
      	            <option value="${user.value }">${user.value }</option>
      	          </c:forEach>
      	        </select>
      	      </p>
      	  </li>
      	  <li><span>需求简介：</span><p><textarea rows="10" cols="50" name="description"></textarea></p></li>
          <li><span>期望上线时间：</span><p><input type="text" id="expectedTime" name="expectedTime" onclick="laydate()" style="padding-left:10px; height:30px; line-height:30px;" /></p></li>
          <li><span>附件：</span>
        	<p>
              <table id="fileTb">
                <tr>
                  <td>
                    <input type="file" id="file_1" name="file" onChange="uploadFile(1);"/><input id="fileUri_1" type="hidden" name="fileUris">
                  </td>
                </tr>
                <tr>
                  <td>
                    <input type="file" id="file_2" name="file" onChange="uploadFile(2);"/><input id="fileUri_2" type="hidden" name="fileUris">
                  </td>
                </tr>
                <tr>
                  <td>
                    <input type="file" id="file_3" name="file" onChange="uploadFile(3);"/><input id="fileUri_3" type="hidden" name="fileUris">
                  </td>
                </tr>
                <tr>
                  <td>
                    <input type="file" id="file_4" name="file" onChange="uploadFile(4);"/><input id="fileUri_4" type="hidden" name="fileUris">
                  </td>
                </tr>
                <tr>
                  <td>
                    <input type="file" id="file_5" name="file" onChange="uploadFile(5);"/><input id="fileUri_5" type="hidden" name="fileUris">
                  </td>
                </tr>
               </table>
               <p class="new-btn">
                  <input type="button" value="增加附件" onclick="addFileBtn();" style="float:left; margin: 10px 0px 0px 50px; line-height: 25px; padding: 0px 10px;"/>
               </p>
            </p>
          </li>
        </ul>
      </div>
      <div class="new-btn">
       <input type="button" onclick="submitDemand()" value="提 交" style="float:none; height: 38px" />
       <a href="${base }/product/demand/list.do" style="float:none; border:1px solid #0080ef; color:#0080ef; background-color:#fff;">取 消</a>
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
   <script src="${projectResDomain }${base }/js/ajaxfileupload.js?_=${buildVersion}"
		 type="text/javascript"></script>
   <script type="text/javascript">
	   function uploadFile(no){
	  	 $.ajaxFileUpload({
	  	     url: '${base }/file/uploadAjax.do',
	  	        secureuri: false,
	  	        fileElementId: 'file_' + no,
	  	        dataType: 'json',
	  	        success: function (data) {
	  	        	
	  	            $("#fileUri_" + no).val(data.userdata);
	  	 
	  	        },
	  	        error: function (data) {
	  	        	alert("上传失败");
	  	        }
	  	    });
	   }
	   
	   function submitDemand(){
	  	 if(!$("#name").val()){
	  		 alert("需求名称不能为空");
	  		 return;
	  	 }
	  	 
	  	 var currTime = new Date().getTime();
	  	 if(!$("#expectedTime").val() || (Date.parse($("#expectedTime").val())  + 24 * 3600 * 1000) < currTime){
	  		 alert("预计上线时间不能为空， 且不能早于当前时间");
	  		 return;
	  	 }
	  	 
	  	 $("#demandForm").submit();
	   }
	   
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
	   
	   var fileIndex = 100;
	   function addFileBtn(){
		   ++fileIndex;
		   $("#fileTb").append("<tr>" +
	                  "<td>" + 
	                    "<input type='file' id='file_"+fileIndex+"' name='file' onChange='uploadFile("+fileIndex+");'/><input id='fileUri_"+fileIndex+"' type='hidden' name='fileUris'>" +
	                  "</td>" +
	                "</tr>");
	   }
   </script>
  </body>
</html>
