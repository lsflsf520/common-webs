<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8" %>
<%@ include file="taglibs.jsp"%>

<link rel="shortcut icon" href="${commonResDomain }/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${commonResDomain}${base}/css/bootstrap.min.css?v=${buildVersion}"/>
<link rel="stylesheet" type="text/css" href="${commonResDomain}${base}/css/base-style.css?v=${buildVersion}"/>


<div id="glConfirmPopDiv" class="pop-up-box" style="display:none;">
    <ul>
    	<li class="t1" id="glConfirmTipLi"></li>
        <li class="t2" style="text-align: center;">
        	<a href="javascript:globalJs.closeDiv('glConfirmPopDiv');" id="glConfirmOKBtn" style="width:130px; float:none; padding:0;">是</a><a href="javascript:globalJs.closeDiv('glConfirmPopDiv');" id="glConfirmCancelBtn" class="btn1" style="width:130px; float:none; padding:0;">否</a>
        </li>
    </ul>	
</div>

<div id="glAlertPopDiv" class="pop-up-box" style="display:none;">
    <ul>
    	<li class="t1" id="glAlertTipLi"></li>
        <li class="t2">
        	<a href="javascript:globalJs.closeDiv('glAlertPopDiv');" id="glAlertBtn" class="btn1" style="float:right;">确定</a>
        </li>
    </ul>	
</div>

<div id="t_bg1" class="t_bg1" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0' style="display:none;"></iframe>

<script src="${commonResDomain}/assets/plugins/jquery-1.10.2.min.js"></script>
<script>
  var WEB_ROOT = "${base}";
  var RES_VERSION = "${buildVersion}";
  var PASSPORT_DOMAIN = "${passportDomain}";
  var PROJECT_RES_DOMAIN = "${projectResDomain}";
  
  Function.prototype.getName = function(){
	 return this.name || this.toString().match(/function\s*([^(]*)\(/)[1];
  }
  
  var globalJs = function(){
	var _self = {
	     showDiv : function(divId){
		    document.getElementById(divId).style.display='block';
			document.getElementById('popIframe').style.display='block';
			document.getElementById('t_bg1').style.display='block';
		 },
	     closeDiv : function (divId){
			document.getElementById(divId).style.display='none';
			document.getElementById('t_bg1').style.display='none';
			document.getElementById('popIframe').style.display='none';
			
			$("#" + this.getOKBtnId()).text("是");
			$("#" + this.getCancelBtnId()).text("否");
			$("#" + this.getOKBtnId()).attr("href", "javascript:globalJs.closeDiv('glConfirmPopDiv');");
			$("#" + this.getCancelBtnId()).attr("href", "javascript:globalJs.closeDiv('glConfirmPopDiv');");
			$("#" + this.getOKBtnId()).removeClass("btn1");
			$("#" + this.getCancelBtnId()).removeClass("btn1");
			$("#" + this.getCancelBtnId()).addClass("btn1");
			
			if(typeof(afterConfirmDivClose) != "undefined"){
				afterConfirmDivClose();
			}
		 },
	     alert: function(msg){
	    	$("#glAlertTipLi").text(msg);
	    	this.showDiv("glAlertPopDiv");
	     },
	     setAlertBtnText : function(text){
	    	 $("#glAlertBtn").text(text);
	     },
	     setAlertBtnHref : function(link){
	    	 $("#glAlertBtn").attr("href", link);
	     },
	     /**
	     * msg : 提示消息
	     * okCallback : 点击“是”按钮后要执行的函数(暂时不支持)或者需要跳转的超链接字符串
	     * okParam : 如果 okCallback 是函数，则将okParam作为 okCallback 的参数执行
	     * cancelCallback :  点击“否”按钮后要执行的函数(暂时不支持)或者需要跳转的超链接字符串
	     * cancelParam ：如果 cancelCallback 是函数，则将cancelParam作为 cancelCallback 的参数执行
	     */
	     confirm : function(msg, okCallback, okParam, cancelCallback, cancelParam){
	    	 $("#glConfirmTipLi").text(msg);
	    	 if(okCallback){
	    		 if(typeof okCallback == "function"){
	    			 $("#" + this.getOKBtnId()).attr("href", "javascript:" + okCallback.getName() + "(" + okParam + ")");
	    		 }else{
	    			 $("#" + this.getOKBtnId()).attr("href", okCallback);
	    		 }
	    	 }
	    	 
	    	 if(cancelCallback){
	    		 if(typeof cancelCallback == "function"){
	    			 $("#" + this.getCancelBtnId()).attr("href", "javascript:" + cancelCallback.getName() + "(" + cancelParam + ")");
	    		 }else{
	    			 $("#" + this.getCancelBtnId()).attr("href", cancelCallback);
	    		 }
	    	 }
	    	 
	    	 this.showDiv("glConfirmPopDiv");
	     },
	     revertClass : function(){
	    	$("#" + this.getOKBtnId()).addClass("btn1");
			$("#" + this.getCancelBtnId()).removeClass("btn1");
	     },
	     setOKBtnText:function(text){
	    	 $("#" + this.getOKBtnId()).text(text);
	     },
	     setCancelBtnText:function(text){
	    	 $("#" + this.getCancelBtnId()).text(text);
	     },
	     getOKBtnId :function(){
	    	 return "glConfirmOKBtn";
	     },
	     getCancelBtnId :function(){
	    	 return "glConfirmCancelBtn";
	     },
	     hideConfirmDialog : function(){
	    	 this.closeDiv("glConfirmPopDiv");
	     }
	   };
	return _self;
  }();
  
</script>
