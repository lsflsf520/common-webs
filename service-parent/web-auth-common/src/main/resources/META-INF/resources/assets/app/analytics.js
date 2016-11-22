var STLog =function(){
	var params = {};
	params.ot={};
	var tk = "tk";
	var accessid;
	var init = function(){
		 //Document对象数据
	    if(document) {
	        params.domain = document.domain || ''; 
	        params.loc = document.URL || ''; 
	        params.title = document.title || ''; 
	        params.ot.url = document.URL || '';
	        params.ot.referrer = document.referrer || ''; 
	    }   
	    //Window对象数据
	    if(window && window.screen) {
	        params.ot.sh = window.screen.height || 0;
	        params.ot.sw = window.screen.width || 0;
	        params.ot.cd = window.screen.colorDepth || 0;
	    }   
	    //navigator对象数据
	    if(navigator) {
	        params.ot.lang = navigator.language || ''; 
	    }   
	    var token = getCookieByName(tk);
	    if(token){
	    	params.tk = token||'';
	    }
	    accessid = uuid(32,16);
	    if(accessid){
	    	params.accessid=accessid||'';
	    }
	    if(versions){
	    	params.src=versions||'';
	    }
	};
	
		
	var versions= function(){
		var u = navigator.userAgent, app = navigator.appVersion; 
		return  
		 (u.indexOf('Trident') > -1)?"trident,":""
		+ u.indexOf('Presto') > -1?"presto,":""
		+ u.indexOf('AppleWebKit') > -1?"webKit,":"",
		+ ( u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1)?"gecko,":""
		+ !!u.match(/AppleWebKit.*Mobile.*/)?"mobile,":""
		+ !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)?"ios,":""
		+  (u.indexOf('Android') > -1 || u.indexOf('Adr') > -1)?"android,":""
		+  u.indexOf('iPhone') > -1?"iPhone,":"" 
		+  u.indexOf('iPad') > -1?"iPad,":""
		+  u.indexOf('Safari') == -1?"webApp,":""
		+  u.indexOf('MicroMessenger') > -1?"weixin,":""
		+  u.match(/\sQQ/i) == " qq" ?"qq":"";
		; 
	}();
	//拼接参数串
   var combArgs= function(params){
	    var args = ''; 
	    for(var i in params) {
	        if(args != '') {
	            args += '&';
	        }   
	        if(i == 'ot'){
	        	var otarg = '';
	        	for(var o in params[i] ){
	        		if(otarg != ''){
	        			otarg+=',';
	        		}
	        		otarg += o + ':' + encodeURIComponent( params[i][o]);		
	        	}
	        	args += i + '=' + otarg;
	        }else{
	        	args += i + '=' + encodeURIComponent(params[i]);
	        }
	    }   
	    return args;
    };
   var getCookieByName = function(name)
   {
	   var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	   if(arr=document.cookie.match(reg))
	   return unescape(arr[2]);
	   	else
	   return null;
   };
	
   var uuid = function(len, radix) {
	    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	    var uuid = [], i;
	    radix = radix || chars.length;

	    if (len) {
	      // Compact form
	      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
	    } else {
	      // rfc4122, version 4 form
	      var r;

	      // rfc4122 requires these characters
	      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
	      uuid[14] = '4';

	      // Fill in random data.  At i==19 set the high bits of clock sequence as
	      // per rfc4122, sec. 4.1.5
	      for (i = 0; i < 36; i++) {
	        if (!uuid[i]) {
	          r = 0 | Math.random()*16;
	          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
	        }
	      }
	    }

	    return uuid.join('');
	};
  var send =function(type){
	  if(type==0){
		  params.type='s';
	  }else if(type==1){
		  params.type='e';
	  }else if(type==2){
		  params.type='x';
	  }
	  params.time=getDate()||'';
	  var args = combArgs(params);
   		//通过Image对象请求后端脚本
       var img = new Image(1, 1); 
       img.src = 'http://stlog.17daxue.com/log/1.gif?' + args;
   };
   var getDate = function() {
	   //var date = new Date();
	    //return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+ ":" +date.getMinutes()+":"+date.getSeconds();
	    return  (new Date()).valueOf();
	}
   return{
	   init:init,
	   send:send
   }
};
var STLogUtil = function () {
	var currentStlog;
	return {
	initSession : function(){
		var stlog = new STLog();
		stlog.init();
	    debugger;
	    stlog.send(0);
		$(window).bind('beforeunload', function(e) {
	    	debugger;
	        stlog.send(1);    
		});
	},
	initPage : function(){
		var stlog = new STLog();
		stlog.init();
	    debugger;
	    stlog.send(0);
	    this.currentStlog=stlog;
	    return stlog;
	},
	beforeUnloadCurrPage : function(){
			if(this.currentStlog){
				this.currentStlog.send(1);
			}
		}
	}
	 
}();

