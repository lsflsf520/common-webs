<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ include file="taglibs.jsp"%>

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
            <li>哇塞，您访问的页面不存在！</li>
        </ul>
    </div>
    <!--AI-Matrix-no end-->
    
    <!--bottom-btn start-->
    <div class="bottom-btn">
    	<a  href="javascript:history.back();" class="btn-bg1 padding4">返回</a>&nbsp;&nbsp;
    </div>
    
