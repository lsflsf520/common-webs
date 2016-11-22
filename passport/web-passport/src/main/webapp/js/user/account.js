/**
 * 
 * @param operPayPwdSet 支付密码已设置之后的回调函数
 * @param jsonParam 作为operPayPwdSet函数的参数
 */
function checkTransPwd(operPayPwdSet, jsonParam, setPwdDivId) {
	if($("#loadingPopDiv") && $("#loadingPopDiv").length > 0){
		globalJs.showDiv("loadingPopDiv");
	}
	$.ajax({
		url : PASSPORT_DOMAIN + "/account/hasAccount",
		type : "POST",
		dataType : "jsonp",
		success : function(data) {
			if($("#loadingPopDiv") && $("#loadingPopDiv").length > 0){
				globalJs.closeDiv("loadingPopDiv");
			}
			if(jsonParam && (jsonParam.preAmount*100) > data.balance){
				$("#balAmount").text(data.balance / 100);
				globalJs.showDiv('balancePopDiv');
			}else if (data.code == '0') {
				if(!setPwdDivId){
					globalJs.setOKBtnText("去设置");
					globalJs.setCancelBtnText("取消");
					globalJs.revertClass();
					globalJs.confirm("为了保障你的账户安全，请设置支付密码", PASSPORT_DOMAIN
							+ "/account/setPwdForTrans");
				}else{
					globalJs.showDiv(setPwdDivId);
//					globalJs.setAlertBtnText("去设置");
//					globalJs.setAlertBtnHref(PASSPORT_DOMAIN
//							+ "/account/setPwdForTrans");
//					globalJs.alert("为了保障你的账户安全，请设置支付密码");
				}
			}else if(operPayPwdSet){
				jsonParam.balance = data.balance;
				operPayPwdSet(jsonParam);
			}
		}
	});
}




function showPayDiv(data){
	$("#payPasswd").val('');
	$(".payA2").css("display", "none");
	if(data.preAmount){
		$("#preAmt").text(data.preAmount);
		$("#simpleLi").css("display", "block");
	}else if(data.originNum && data.youhuiNum){
		$("#originNum").text(data.originNum);
		$("#youhuiNum").text(data.youhuiNum);
		$("#finalNum").text(data.originNum - data.youhuiNum);
		
		$("#yhLi").css("display", "block");
	}else{
		globalJs.alert("对不起，参数错误！");
	}
	/*if($("#balance")){
		$("#balance").text(data.balance - data.preAmount);
	}*/
	$("#confirmA").attr("href", "javascript:payOrder("+data.orderNo+", '" + data.backUrl +"', "+data.successPay+")");
	
	globalJs.showDiv("payPopDiv");
}

var paying = false;
/**
 * 
 * @param orderNo 订单号
 * @param successPay 支付成功后的回调函数，用订单号作为该函数的参数，如果不指定，默认重新加载当前页面；
 */
function payOrder(orderNo, backUrl, successPay){
	var payPasswd = $("#payPasswd").val();
	if(!payPasswd){
		$("#errorInfo").text("支付密码不能为空！");
		$("#errorInfo").css("display", "inline");
		return;
	}
	$("#errorInfo").css("display", "none");
	if(paying){
		return;
	}
	paying = true;
	$.ajax({
		url : PASSPORT_DOMAIN + "/order/prePayOrder?orderNo=" + orderNo + "&payPasswd=" + payPasswd,
		type : "GET",
		dataType : "jsonp",
		success : function(data){
			if(data && data.type != "success"){
			    paying = false;
				$("#errorInfo").text(data.message ? data.message : "支付失败，请重试！");
	    		$("#errorInfo").css("display", "inline");
				return;
			}
			globalJs.closeDiv("payPopDiv");
			if(successPay){
				successPay(orderNo, backUrl);
			}else if(backUrl){
				var href = location.href;
				if(href.indexOf("?") > 0){
					href = href.substr(0, href.indexOf("?"));
				}
				href += "?backUrl=" + backUrl;
				location.href = href;
			}else{
				location.reload();
			}
		}
		
	});
}

function afterConfirmDivClose(){
	if(paying){
		paying = false;
	}
	if(typeof(canceling) == "undefined"){
		canceling = false;
	}
}
