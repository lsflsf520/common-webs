<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="taglibs.jsp"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintWriter"%>

<style>
.AI-Matrix-no {
  width: 100%;
  _height: 200px;
  min-height: 200px;
  overflow: hidden;
  display: block;
  margin-top: 100px;
}
.AI-Matrix-no ul {
    float: left;
    width: 100%;
}
ul {
    padding: 0px;
    margin: 0px;
    list-style-type: none;
}
.AI-Matrix-no ul li {
    float: left;
    width: 100%;
    display: block;
    text-align: center;
    margin-bottom: 35px;
    font-size: 16px;
}
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
  text-decoration: none;
}
.padding4 {
  width: 23%;
}
.btn-bg1 {
  background-color: #ff8a01;
}
</style>

<!--AI-Matrix-no start-->
    <div class="AI-Matrix-no" style="margin-top:200px;">
    	<ul>
        	<li><img src="${commonResDomain}/assets/images/error_03.png" width="156" height="156" alt="图片" /></li>
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


<script>
  function showOrHide(){
	  var displayStr = document.getElementById('errorDiv').style.display;
	  if(displayStr == "none"){
		  document.getElementById('errorDiv').style.display = "block";
		  document.getElementById('errorBtn').text = "隐藏错误报告";
	  }else{
		  document.getElementById('errorDiv').style.display = "none";
		  document.getElementById('errorBtn').text = "查看错误报告";
	  }
  }
</script>