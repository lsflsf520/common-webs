<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintWriter"%>
<%@ include file="/WEB-INF/views/common/global.jsp"%>

<style>
<!--
.bottom-btn {
  text-align: center;
}
.bottom-btn a {
  display: inline-block;
  height: 46px;
  line-height: 46px;
  color: #fff;
  font-size: 20px;
  border-radius: 5px;
  margin: 0 5px;
  text-align: center;
}
.padding4 {
  width: 23%;
}
.btn-bg1 {
  background-color: #ff8a01;
}
-->
</style>

<div class="wrapper">
	<!--header start-->
    <div class="header">
    	<div class="logo"><a href="${studentDomain }"><img src="${commonResDomain}${base}/images/login.png" width="185" height="35" alt="创数教育" /></a></div>
        <div class="header-right"><a  style="margin-right:20px;" href="${studentIndex }">返回首页</a><a href="${passportDomain }/user/pwd/toresetpage">修改密码</a></div>
    </div>
<!--AI-Matrix-no start-->
    <div class="AI-Matrix-no" style="margin-top:200px;">
    	<ul>
        	<li><img src="${commonResDomain}${base}/images/error_03.png" width="156" height="156" alt="图片" /></li>
        	<li class="f-size3">出错了</li>
            <li>我们正在努力解决问题。有任何疑问，请联系我们的工作人员。</li>
        </ul>
    </div>
    <!--AI-Matrix-no end-->
    
    <!--bottom-btn start-->
    <div class="bottom-btn">
    	<a  href="javascript:history.back();" class="btn-bg1 padding4">返回</a>&nbsp;&nbsp;<a href="javascript:void(0);" onclick="showOrHide();" id="errorBtn" class="btn-bg1 padding4">查看错误报告</a>
    </div>
    <!--bottom-btn end-->
    <div id="errorDiv" style="display:none;">
      <label>错误报告：</label>
      <%
        String messageId = request.getParameter("messageId") == null ? (String)request.getAttribute("messageId") : request.getParameter("messageId");
        if(messageId != null){
      %>
       <div><label>messageId:</label><span><%=messageId %></span></div>
      <%
        }
      %>
      <div><%
          Object errorObj = request.getAttribute("errorMsg");
          if(errorObj == null){
        	  errorObj = request.getParameter("errorMsg");
          }
          if(errorObj == null){
        	  out.println("服务器出现未知错误，请联系管理员检查！");
          }else if(errorObj instanceof Exception){
        	  Exception except = (Exception)errorObj;
        	  
        	  StringWriter sw=new StringWriter();
              PrintWriter pw=new PrintWriter(sw);
              except.printStackTrace(pw);
              out.println(sw); 
          }else{
        	  out.println(errorObj); 
          }
         
         %>
       </div>
    </div>
</div>
<script src="assets/plugins/jquery-1.10.2.min.js?_=${buildVersion}"
		type="text/javascript"></script>
<script>
  function showOrHide(){
	  if($("#errorDiv").css("display") + "" == "none"){
		  $("#errorDiv").css("display", "block");
		  $("#errorBtn").text("隐藏错误报告");
	  }else{
		  $("#errorDiv").css("display", "none");
		  $("#errorBtn").text("查看错误报告");
	  }
  }
</script>