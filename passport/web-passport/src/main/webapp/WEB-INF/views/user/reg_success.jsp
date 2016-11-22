<%@ page language="java" trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
</head>

<body>
<!--wrapper start-->
<div class="wrapper">
	<!--header start-->
    <div class="header">
    	<div class="logo"><a href="${studentDomain }"><img src="${commonResDomain}${base}/images/login.png" width="185" height="35" alt="创数教育" /></a></div>
    	<c:if test="${userName == null }">
         <div class="header-right">我有帐号，<a href="${base }/user/login/loginPage">马上登录<img src="${commonResDomain}${base}/images/jt.png" width="11" height="19" alt="箭头"/></a></div>
        </c:if>
        <c:if test="${userName != null }">
          <div class="header-right">欢迎你，${userName }</div>
        </c:if>
    </div>
    <!--header end-->
    
     <!--register-succeed start-->
    <div class="register-succeed">
    	<ul>
        	<li><img src="${commonResDomain}${base}/images/login-pic1.png" width="327" height="246" alt="图片"></li>
            <li class="f-size4">恭喜你，注册成功！</li>
            <li class="num-bg"><span>您的17大学学号是：<i>${acc }</i>
            <!-- 
              <a href="javascript:export_raw('17大学账号.txt', '账号：${acc }\r\n密码：${pw }\r\n网址：http://www.17daxue.com')"><img src="${commonResDomain}${base}/images/download_03.png" width="21" height="22" alt="下载">下载我的学号</a>
             -->
            </span>
            </li>
            <li class="f-color">此号码将作为下次登录的账号，请牢记，如果你担心忘记，请在【个人中心】绑定你的QQ号码。</li>
            <li><a href="${base }/user/login/loginPage">马上登录<img src="${commonResDomain}${base}/images/jt1.png" width="11" height="19" alt="箭头"></a></li>
        </ul>
    </div>
    <!--register-succeed end-->
    
</div>
<!--wrapper end-->
</body>
<script> 
function fake_click(obj) {
    var ev = document.createEvent("MouseEvents");
    ev.initMouseEvent(
        "click", true, false, window, 0, 0, 0, 0, 0
        , false, false, false, false, 0, null
        );
    obj.dispatchEvent(ev);
}

function export_raw(name, data) {
    var urlObject = window.URL || window.webkitURL || window;

    var export_blob = new Blob([data]);

    var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
    save_link.href = urlObject.createObjectURL(export_blob);
    save_link.download = name;
    fake_click(save_link);
}
 </script> 
</html>
