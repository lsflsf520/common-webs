<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<html>
  <head>
    <title>需求详情-首页</title>
    <link rel="stylesheet" type="text/css" href="${projectResDomain }${base }/css/x-style.css?_=${buildVersion}">
  </head>

  <body>
    <div class="x-contain">
     <form action="${base }/product/demand/update.do?demandId=${demand.id}" method="post">
      <div class="x-title">产品需求详情 </div>
      <div class="x-list">
        <ul>
  	     <li><span>需求名称：</span><p>${demand.name }</p></li>
         <li><span>需求简介：</span><p>${demand.description }</p></li>
         <li><span>期望上线时间：</span><p>${demand.expectedTimeStr }</p></li>
         <li><span>所属产品：</span><p>${demand.projectCnName }</p></li>
         <li><span>所属模块：</span><p>${demand.moduleCnName }</p></li>
         <li><span>开发负责人：</span><p>${demand.devLeader }</p></li>
         <li><span>测试负责人：</span><p>${demand.testLeader }</p></li>
         <li><span>创建时间：</span><p>${demand.createTimeStr }</p></li>
         <li><span>创建者：</span><p>${demand.creator }</p></li>
         <li><span>状   态：</span><p>${demand.dbStatus.desc }</p></li>
         <li><span>附件：</span>
           <p>
             <ul id="fileUl" style="margin: -30px 0px 0px 10px;">
                <c:forEach items="${fileUris }" var="f" varStatus="status">
                  <li id="li_${status.index }"><span><a href="${fileDomain}/${f }">${fileDomain}/${f }</a></span><span style="margin-left:10px;"><a href="javascript:void(0);" onclick="$('#li_${status.index }').remove();">删除</a></span><input type="hidden" name="fileUris" value="${f }"></li>
                </c:forEach>
              </ul>
              <p class="new-btn">
                  <input type="button" value="增加附件" onclick="addFileBtn();" style="margin-left:50px; float: left; line-height: 25px; padding:0 10px;"/>
               </p>
           </p>
         </li>
        </ul>
      </div>
      <div class="new-btn">
        <input type="submit" value="提 交"  style="float:left; line-height:25px; height: 38px;"/>
        <a href="${base }/product/demand/list.do" style="float:none; border:1px solid #0080ef; color:#0080ef; background-color:#fff;">产品需求列表</a>
      </div>
     </form>
   </div>
   <script src="${commonResDomain }/assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}"
		type="text/javascript"></script>
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
     
     var fileIndex = 100;
	 function addFileBtn(){
	  ++fileIndex;
	  $("#fileUl").append("<li>" +
	                    "<input type='file' id='file_"+fileIndex+"' name='file' onchange='uploadFile("+fileIndex+");'/><input id='fileUri_"+fileIndex+"' type='hidden' name='fileUris'>" +
	                "</li>");
	 }
   </script>
  </body>
</html>
