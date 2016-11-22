<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" isErrorPage="true"%>
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
            <li>哇塞，您访问的页面不存在！</li>
        </ul>
    </div>
    <!--AI-Matrix-no end-->
    
    <!--bottom-btn start-->
    <div class="bottom-btn">
    	<a  href="javascript:history.back();" class="btn-bg1 padding4">返回</a>&nbsp;&nbsp;
    </div>
</div>
