<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/views/common/global.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${commonResDomain }${base }/css/account.css?v=${buildVersion}"/>
<%-- <link rel="stylesheet" type="text/css" href="${commonResDomain }/assets/plugins/jquery-page/css/page.css"/> --%>
<title>订单记录</title>
<style>
.table-content{
	overflow: visible;
	margin-bottom:50px;
}
</style>
</head>

<body>
	<!--wrapper start-->
	<div class="wrapper">
		<!--header start-->
		<%@include file="../common/_header.jsp"%>
		
		<!--table-content start-->
      <div class="table-content" style="overflow: !important;">
        <table cellpadding="0" cellspacing="0" class="table table-border table-striped">
          <tr>
            <th>订单号</th>
            <th>订单生成时间</th>
		    <th>名称</th>
		    <th>魔数豆(充值/消费)</th>
		    <th>状态</th>
		    <th>操作</th>
          </tr>
          <c:forEach items="${pageList.results }" var="item">
		    <tr>
		      <td>${item.orderNo }</td>
		      <td>${item.createTimeStr }</td>
		      <td>${item.orderInfo }</td>
		      <td class='${item.type == 2 ? "green-color" : "red-color" }'>${item.type == 2 ? "+" : "-" }<fmt:formatNumber type="number" value="${item.amount > 0 ? item.amount/100 : item.preAmount/100}" maxFractionDigits="0" pattern="#"/>&nbsp;<c:if test="${item.amount == null || item.amount <= 0 }"> <a href="javascript:void(0);" onmouseover="$('#tip_${item.orderNo }').css('display', 'block')" onclick="$('#tip_${item.orderNo }').css('display', 'block'); " onmouseout="$('#tip_${item.orderNo }').css('display', 'none')" class="why">(打印前冻结<img src="${commonResDomain }${base }/images/why.png" width="14" height="14" />)</a><div id="tip_${item.orderNo }" class="why-box info-box" style="display:none;">打印资料按1魔数豆/页的标准收费。打印前，我们会按照题量冻结余额中的部分魔数豆。打印后，我们将按实际页数扣除魔数豆。</div></c:if></td>
		      <td>${item.stateStr }</td>
		      <td>
		       <c:if test="${item.state == 100 }">
		        <a style="margin-bottom:6px;" class="btn-style feedback-btn" href='javascript:checkTransPwd(showPayDiv,{orderNo:${item.orderNo }, preAmount: ${item.preAmount/100 }})' class="btn-style feedback-btn" style="margin-bottom:6px;">确认打印</a>
		        <a class="btn-style look-btn" href='javascript:globalJs.revertClass(),globalJs.confirm("订单取消以后不可恢复，确定要取消该订单吗？", "javascript:cancelOrder(${item.orderNo })")' class="btn-style look-btn">取消订单</a>
		       </c:if>
		      </td>
		    </tr>
		  </c:forEach>
        </table>
     </div>
     <!--table-content end-->
     
     <!--page start-->
  <!--page end--> 
    <div style="position:relative;clear:both">
      <c:if test="${pageList.pageCount >= 1}">
		<%@include file="../common/_page.jsp" %>
	  </c:if> 
	</div>
	
	<div class="jq-page">
    
    </div>
</div>

<div id="loadingPopDiv" class="pop-up-box" style="display:none;">
  <ul>
  	<li><img src="${commonResDomain }/assets/images/loading_big.gif" alt="加载中" /></li>
  </ul>
</div>

<div id="balancePopDiv" class="pop-up-box" style="display:none;">
  <ul>
  	<li class="t7" style="font-size:24px; margin-bottom:25px;">魔数豆余额：<span id="balAmount" style="color:#52c200; margin:0 4px;">23</span></li>
    <li style="font-size: 16px;text-indent: 33px;text-align: left;line-height: 27px;margin-bottom: 25px;">你的魔数豆余额不足，请前往校区服务人员处购买充值卡充值。</li>
    <li class="t2"><a href="javascript:globalJs.closeDiv('balancePopDiv');" class="btn1" style="width:380px; padding:0;">确定</a> </li>
  </ul>
</div>

<div id="payPopDiv" class="pop-up-box" style="display:none;">
  <ul class="verify-box">
  	<li class="a1">支付密码验证</li>
    <li class="a2 payA2" style="display:none;" id="simpleLi">打印前冻结：<span id="preAmt">23</span>魔数豆</li>  
    <li class="a2 payA2" style="display:none;" id="yhLi"><p id="originNum">96</p> (原价) -  <p id="youhuiNum">40</p> (创数优惠季)  = <span id="finalNum">56</span>魔数豆</li>
    <li class="a3">
    	<span>请输入你的支付密码：</span>
        <span><input id="payPasswd" onfocus="$('#payPasswd').attr('type', 'password')" class="form-control"  /></span>
        <span id="errorInfo" class="red-tip" style="display:none;">支付密码错误，请重新输入。</span>
    </li>
    <li><a href="#" id="confirmA" class="btn-style feedback-btn">确定</a><a href="javascript:globalJs.closeDiv('payPopDiv');" class="btn-style all-btn1"> 取消</a></li>
  </ul>
</div>

<script type="text/javascript"
	src="${projectResDomain}${base}/js/user/account.js?v=${buildVersion}"></script>
<script>
    var canceling = false;
    function cancelOrder(orderNo){
    	if(canceling){
    		return;
    	}
    	canceling = true;
    	$.ajax({
    		url : WEB_ROOT + "/order/cancelOrder?orderNo=" + orderNo,
    		type : "GET",
    		success : function(data){
    			if(data && data.type != "success"){
    			    canceling = false;
    				globalJs.closeDiv("glConfirmPopDiv");
    				globalJs.alert(data.message ? data.message : "操作失败，请重试！");
    				return;
    			}
    			location.reload();
    		}
    		
    	});
    }
    
    $(".why").click(function(event){
    	event.stopPropagation();
    });
    $(document).click(function(){
    	$(".info-box").css("display", "none");
    });
    
</script>
</body>
</html>
