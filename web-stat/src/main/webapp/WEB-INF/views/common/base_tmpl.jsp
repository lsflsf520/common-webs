<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<%@ include file="global.jsp" %>

<!DOCTYPE html>
<html>
 <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <link rel="shortcut icon" href="${commonResDomain }/favicon.ico" type="image/x-icon" />
   <link rel="stylesheet" type="text/css" href="${commonResDomain }${base}/css/base.css?v=${buildVersion}">
   <link rel="stylesheet" type="text/css" href="${commonResDomain }${base}/css/bootstrap.min.css?v=${buildVersion}">
   <link rel="stylesheet" type="text/css" href="${commonResDomain }${base}/css/menu.css?v=${buildVersion}">
   
   <script src="${commonResDomain}/assets/plugins/jquery-1.10.2.min.js"></script>
   <script src="${commonResDomain }/assets/plugins/echart/echarts-all.js"></script>
   <script type="text/javascript"
	     src="${commonResDomain }/assets/plugins/laydate/laydate.dev.js?v=${buildVersion}"></script>
   <link rel="stylesheet" type="text/css"
	     href="${commonResDomain }/assets/plugins/laydate/skins/default/laydate.css?v=${buildVersion}" id="LayDateSkin"/>
	     
   <link rel="stylesheet" href="${commonResDomain }/assets/plugins/jPages/css/jPages.css"/> 
   <script type="text/javascript" src="${commonResDomain }/assets/plugins/jPages/jPages.js"></script>
	     
   <script src="${projectResDomain}${base }/js/global.js?v=${buildVersion}"></script>
   <script src="${projectResDomain}${base }/js/main_menu.js?v=${buildVersion}"></script>
	     
   <sitemesh:write property='head' />
   
   <title><sitemesh:write property='title' /></title>
   
 </head>
 <body>
     <!--wrapper start-->
<div class="wrapper">
    <!--header start-->
    <div class="header">
        <!--header-wrapper start-->
        <div class="header-wrapper">
            <div class="row">
                <div class="col-sm-8"><a href="javascript:void(0)"><img src="${commonResDomain}${base }/images/logo.png" alt="logo"></a></div>
                <div class="col-sm-4">
                    <div class="login-info">
                        <ul>
                            <li>${userName }</li>
                            <li class="border"></li>
                            <li><a href="${passportDomain }/user/login/doLogout">退出</a></li> 
                        </ul>
                    </div>
                </div>
                
            </div>
        </div>
        <!--header-wrapper end-->
    </div>
    <!--header end-->
    
    <!--main start-->
    <div class="main">
        <!--main-wrapper start-->
        <div class="main-wrapper" style="min-height:700px;">
            <div class="row">
                <div class="col-sm-2">
                    <div class="wrap-menu-left">
		               
	                </div>
                </div>
                <div class="col-sm-10">
                <div class="alert alert-info" style="position:absolute;margin:0px auto;right:10px;text-align:center;display:none;" id="simplehint">正在获取数据，请稍候......</div>
                    <div class="right-content" style="min-height:430px;">
                       <sitemesh:write property='body' />
                    </div>
                </div>
            </div>
        </div>
        <!--main-wrapper end-->
    </div>
    <!--main end-->
    
    <!--footer start-->
    <div class="footer">
        <div class="footer-wrapper">
            <h5>©2016 创数教育</h5>
        </div>
    </div>
    <!--footer end-->
</div>
<!--wrapper end-->
<script>
    var leftMenu = ${menuJson};

	$(function(){
		new AccordionMenu({menuArrs: leftMenu});
	});
</script> 
 </body>
 </html>
 
