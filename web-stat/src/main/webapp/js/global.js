var Global = function() {
	var a;
	return {
		notify : function(message, status){
			if(status){
				message += ", status:" + status;
			}
			alert(message);
		},
		popDiv : function(title, divSelector, width, height, suffixName){
			var suffix = new Date().getMilliseconds();
			var popDivId = "popDivId_" + suffix;
			var contentDivId = "contentDivId_" + suffix;
			
			height = height && height > 540 ? height : 540;//高度最少为540
			
			var div = '<div id="'+popDivId+'"><input type="hidden" id="'+(suffixName ? suffixName : "popIdSuffix")+'" value="'+suffix+'" /><div class="pop-up-box" style="'+(width ? 'width:' + width + 'px;' : '') + (height ? 'height:' + height + 'px;'  : '') +'">' 
	          +'<div class="pop-up-box-title" style="'+(width ? 'width:' + width + 'px;' : '') +'">'+title+'<a href="javascript:Global.closeDiv(\''+divSelector+'\', '+suffix+')">x</a></div>'
	          + '<div class="pop-up-box-con" id="'+contentDivId+'">'
	          + '</div>'
	          +'</div>'
            +'<div  class="t_bg1" ></div>'
            +'<iframe style="display:block;opacity:0.6;" class="popIframe" frameborder="0" ></iframe></div>';
			
			$(div).appendTo($("body"));
			
			$("#" + contentDivId).append($(divSelector).children());
			
			return suffix;
		},
		closeDiv : function(divSelector){
			var idSuffix = $("#popIdSuffix").val();
			$("#popDivId_" + idSuffix).css("display", "none");
			
			$(divSelector).append($("#contentDivId_" + idSuffix).children());
			
			$("#popDivId_" + idSuffix).remove();
		},
		initTab : function(tabDivId, actTabClass){
			var selClass = (actTabClass ? actTabClass : "selected");
			$("#"+tabDivId + " li" ).click({tabDivId : tabDivId, actTabClass : selClass}, this.changeTab);
	    },
	    popNewTab : function(tabConfig){
	    	if(typeof tabConfig != "object"){
	    		Global.notity("标签参数格式不正确，请以json格式指定");
	    		return;
	    	}
	    	/*{
	    		tabDivId : 'tablDivId', //tab标签的id
	    		contentDivId : 'contentDivId', //tab内容页的divID
	    		actTabClass : 'selected', //如果选中tab后需要给tab添加的样式类
	    		title : '', //tab的标题
	    		remoteUrl : '', 
	    		cacheTime : '', //缓存的时间，如果指定了大于0的正整数，则弹出tab后指定的时间(秒)内不再请求remoteUrl
	    		callback : '', //如果指指定了 remoteUrl ， 该回调函数可以对请求完remoteUrl后返回的结果做处理
	    		tabSuffix : ''  //指定新tab的后缀，每次调用该方法，将检查指定的后缀tab是否存在，如果已存在则替换掉原来的tab；如果没有指定后缀，则默认以时间戳为后缀且每次弹出一个新的tab
	    	}*/
	    	if($("#" + tabConfig.tabDivId + " > ul").children("li").length > 9){
	    		Global.notify("打开的Tab过多，请先关闭掉一部分!"); //最多能同时打开10个标签页
	    		return;
	    	}
	    	var selClass = (tabConfig.actTabClass ? tabConfig.actTabClass : "selected");
	    	var actTabElem = $("#"+tabConfig.tabDivId + " ."+selClass);
	    	actTabElem.removeClass(selClass);
	    	
	    	var actTarId = actTabElem.attr("tar");
	    	$("#"+actTarId).css("display","none");
	    	
	    	var tabSuffix = tabConfig.tabSuffix;
	    	if(!tabSuffix){
	    		tabSuffix = new Date().getTime();
	    	}
	    	var tarCntDivId = "cnt_" + tabSuffix; //内容div的id
	    	var tabId = "Tab_" + tabSuffix; //标签页的id
	    	if($("#" + tabId) && $("#" + tabId).length > 0){
	    		var existTab = $("#" + tabId);
	    		existTab.attr("remoteUrl", tabConfig.remoteUrl ? tabConfig.remoteUrl : "");
	    		existTab.attr("cacheTime", tabConfig.cacheTime ? tabConfig.cacheTime : "");
	    		existTab.attr("callback", tabConfig.callback ? tabConfig.callback : "");
	    		
	    		var vEvent = {
	    				target : 	existTab,
	    				data : {
	    					tabDivId : tabConfig.tabDivId,
		    				actTabClass : selClass
	    				}
	    		};
	    		$("#" + tarCntDivId).html("");
	    		this.changeTab(vEvent, tabConfig.tabDivId, selClass, true);
	    	}else{
	    		$("#" + tabConfig.tabDivId + " > ul").append("<li tar='"+tarCntDivId+"' remoteUrl='"+(tabConfig.remoteUrl ? tabConfig.remoteUrl : "")+"' cacheTime='"+(tabConfig.cacheTime ? tabConfig.cacheTime : "")+"' callback='"+(tabConfig.callback ? tabConfig.callback : "")+"' onclick='Global.changeTab(event, \"" + tabConfig.tabDivId + "\", \"" + selClass +"\");' id='"+tabId+"' class='"+selClass+"'>"+(tabConfig.title ? tabConfig.title : tabId)+"&nbsp;&nbsp;<span onclick='Global.closeTab(\""+tabConfig.tabDivId+"\", \""+tabId+"\", \""+selClass+"\")'>X</span><i class='arrow-bottom'></i></li>");
	    		$("#" + tabConfig.contentDivId).append("<div id='"+tarCntDivId+"' style='display:block;'></div>");
	    		
	    		//刷新内容
	    		this.refreshTab(tabId, tabConfig.remoteUrl, tabConfig.cacheTime, tabConfig.callback, true);
	    	}
	    	
	    },
	    closeTab : function(tabDivId, tabId, actTabClass){
	    	var selClass = (actTabClass ? actTabClass : "selected");
	    	var tabElem = $("#" + tabId);
	    	var tarCntId = tabElem.attr("tar");
	    	
	    	var defaultSelElem = $(tabElem).prev() ? $(tabElem).prev() : $(tabElem).next();
	    	
	    	tabElem.remove();
	    	$("#" + tarCntId).remove();
	    	
	    	var selectedTab = $("#" + tabDivId + " > ul > li." + selClass);
	    	if((!selectedTab || selectedTab.length <= 0) && defaultSelElem){
	    		defaultSelElem.addClass(selClass);
	    		
	    		var tarCntId = defaultSelElem.attr("tar");
	    		$("#" + tarCntId).css("display", "block");
	    	}
	    },
	    changeTab : function (event, tabDivId, actTabClass, forceFlush){
//	      $("#"+tabDivId).delegate("#"+tabDivId + " li:not(."+selClass+")","click",function(){
	    	var currTab = $(this);
	    	 if(event){
	    		 currTab = $(event.target);
	    		 if(event.data){
	    			 tabDivId = event.data.tabDivId;
	    			 actTabClass = event.data.actTabClass;
	    		 }
	    	 }
	         var actTabElem = $("#"+tabDivId + " ."+actTabClass);
	         var actTarId = actTabElem.attr("tar");
	         var currTarId = currTab.attr("tar");
	         
	         if(!currTarId){
	        	 return;
	         }
	        	
	         $("#"+actTarId).css("display","none");
	         actTabElem.removeClass(actTabClass);
	         
	         currTab.addClass(actTabClass);
	         $("#"+currTarId).css("display","block");
	            
	         var remoteUrl = currTab.attr("remoteUrl");
	         var cacheTime = currTab.attr("cacheTime");
	         var callback = currTab.attr("callback");
	            
	         this.refreshTab(currTab.attr("id"), remoteUrl, cacheTime, callback, forceFlush);
	    },
	    refreshTab : function (currTabId, remoteUrl, cacheTime, callback, forceFlush){
	    	if(remoteUrl){
	        	if(!forceFlush && cacheTime && cacheTime != 0){
	        		var lastReqTime = $("#" + currTabId).attr("lastReqTime");
	        		var currTime = new Date().getTime();
	        		if(lastReqTime && currTime <= lastReqTime + cacheTime * 1000){
	        			return;
	        		}
	        	}
	        	
//	        	$("#" + currTabId).ajaxGetUrl(remoteUrl)
	        	
	        	var currTarId = $("#" + currTabId).attr("tar");
	        	$.ajax({
	            	url : remoteUrl,
	        	    type : "GET",
	        	    success : function(data){
	        	    	if(callback){
	        	    		callback(data, currTarId /*当前内容页的ID*/);
	        	    	}else{
	        	    		$("#"+currTarId).html(typeof data == "object" ? JSON.stringify(data) : data);
	        	    	}
	        	    	
	        	    	if(cacheTime && cacheTime != 0){
	        	    		//如果设置了缓存，则需要更新最近一次刷新的时间
	        	    		$("#" + currTabId).attr("lastReqTime", new Date().getTime());
	        	    	}
	        	    }
	            });
	        }
	    }
	}
}();



