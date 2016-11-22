var Global = function() {
	var a;
	return {
		init : function() {
			$(window).resize(function() {
				if ($.fn.jqGrid) {
					Grid.refreshWidth()
				}
			});
			if ($.address) {
				$.address
						.change(function(g) {
							var h = $('.nav-list p > a.ajaxify[rel="address:'
									+ g.value + '"]');
							$('.nav-list p > a').each(function (){
								$(this).removeClass("selected");
							})
							h.toggleClass("selected");
							
							if (h.size() > 0) {
								Global.addOrActivePanel(h);
								var k = $(".page-sidebar-menu").find("li");
								k.removeClass("active");
								var n = h.parent("li");
								n.addClass("active");
								var i = n.closest("ul.sub-menu");
								while (i.size() > 0) {
									i.show();
									var o = i.parent("li");
									o.addClass("open");
									o.find(" > a > span.arrow")
											.addClass("open");
									i = o.closest("ul.sub-menu")
								}
							} else {
								if (g.value == "/" || g.value == "/dashboard") {
									var m = $("#layout-nav");
									var l = m.next(".tab-content");
									var j = $("#tab_content_dashboard");
									if (j.is(":empty")) {
										$("#tab_content_dashboard").ajaxGetUrl(
												"layout!dashboard")
									}
									j.show();
									l.find("> div").not(j).hide();
									m.find("> li:not(.btn-group)").remove();
									m
											.append('<li><i class="fa fa-home"></i> <a href="javascript:;">Dashboard</a></li>')
								} else {
									if (g.value == "/lock") {
										$.backstretch([ "assets/images/bg/1.jpg",
												"assets/images/bg/2.jpg",
												"assets/images/bg/3.jpg",
												"assets/images/bg/4.jpg" ], {
											fade : 1000,
											duration : 8000
										});
										$(".page-container,.header,.footer")
												.hide();
										$("#form-unlock").find(":text").focus()
												.val("");
										$("#page-lock").show();
										$("#form-unlock").find("input").first()
												.focus();
										$("body").ajaxPostURL({
											url : WEB_ROOT + "/layout!lock",
											confirmMsg : false
										})
									} else {
										var h = $("a[rel='address:" + g.value
												+ "']");
										h.attr("href", WEB_ROOT + g.value);
									}
								}
							} 
							setTimeout('Global.tranMathJax()',2000); 
						})
			}
		},
		tranMathJax : function(){
			MathJax.Hub
			.Queue([ "Typeset",
			MathJax.Hub ]);
		},
		addOrActivePanel : function(e, c) {
			var f = "欢迎访问";
			if (e.size() > 0) {
				c = e.attr("href");
				if (c == undefined) {
					c = e.attr("data-url")
				}
				f = e.text()
			}
			if($("#laydate_box")){
				$("#laydate_box").remove();
			}
			var g = $(".right-content");
			var l = g.find("> div[data-url='" + c + "']");
			if(typeof _hmt != 'undefined'){
			_hmt.push(['_trackPageview', c]);
			}
			if (l.length == 0) {
				l = $('<div data-url="' + c + '" class="panel-content"></div>')
						.appendTo(g);
				l.ajaxGetUrl(c);
			} else {
				l.show()
			}
			g.find("> div").not(l).remove();
		},
		notify : function(message, status){
			if(status){
				message += ", status:" + status;
			}
			alert(message);
		},
		/*popDiv : function(title, content, width, height, suffixName){
			var suffix = new Date().getMilliseconds();
			var popDivId = 'popDiv_'+suffix;
			var tbgId = 't_bg_' + suffix;
			var iframeId = 'popIframe_' + suffix;
			var totalDivId = "popTotal_" + suffix;
			var contentDivId = "contentDivId_" + suffix;
			
			var div = '<div id="'+totalDivId+'"><input type="hidden" id="'+(suffixName ? suffixName : "popIdSuffix")+'" value="'+suffix+'" /><div id="'+popDivId+'" class="pop-up-box" style="display:none;'+(width ? 'width:' + width + 'px;' : '') + (height ? 'height:' + height + 'px;'  : '') +'">' 
	    	          +'<div class="pop-up-box-title" style="'+(width ? 'width:' + width + 'px;' : '') +'">'+title+'<a href="javascript:Global.closeDiv('+suffix+')">x</a></div>'
	    	          + '<div class="pop-up-box-con" id="'+contentDivId+'">'
	    	          + content.replace(/suffixPopId/g, suffix)
	    	          + '</div>'
	    	          +'</div>'
	                  +'<div id="'+tbgId+'" class="t_bg1" style="display:none;"></div>'
	                  +'<iframe id="'+iframeId+'" class="popIframe" frameborder="0" ></iframe></div>';
			
			$(div).appendTo($("body"));
			
			$("#" + popDivId).css("display", "block");
			$("#" + tbgId).css("display", "block");
			$("#" + iframeId).css("display", "block");
			
			return suffix;
		},
		closeDiv : function(idSuffix){
			$("#popDiv_" + idSuffix).css("display", "none");
			$("#t_bg_" + idSuffix).css("display", "none");
			$("#popIframe_" + idSuffix).css("display", "none");
			$("#popTotal_" + idSuffix).remove();
		},*/
		popDiv : function(title, divSelector, width, height, suffixName){
			var suffix = new Date().getMilliseconds();
			var popDivId = "popDivId_" + suffix;
			var contentDivId = "contentDivId_" + suffix;
			
			height = height && height > 540 ? height : 540;//高度最少为540
			
			var div = '<div id="'+popDivId+'"><input type="hidden" id="'+(suffixName ? suffixName : "popIdSuffix")+'" value="'+suffix+'" /><div class="pop-up-box" style="'+(width ? 'width:' + width + 'px;' : '') + (height ? 'height:' + height + 'px;'  : '') +'">' 
	          +'<div class="pop-up-box-title" style="'+(width ? 'width:' + width + 'px;' : '') +'">'+title+'<a href="javascript:Global.closeDiv('+suffix+', \''+divSelector+'\')">x</a></div>'
	          + '<div class="pop-up-box-con" id="'+contentDivId+'">'
	          + '</div>'
	          +'</div>'
            +'<div  class="t_bg1" ></div>'
            +'<iframe style="display:block;opacity:0.6;" class="popIframe" frameborder="0" ></iframe></div>';
			
			$(div).appendTo($("body"));
			
			$("#" + contentDivId).append($(divSelector).children());
			
			return suffix;
		},
		closeSelDiv : function(divSelector){
			this.closeDiv($("#popIdSuffix").val(), divSelector);
		},
		closeDiv : function(idSuffix, divSelector){
			$("#popDivId_" + idSuffix).css("display", "none");
			
			$(divSelector).append($("#contentDivId_" + idSuffix).children());
			
			$("#popDivId_" + idSuffix).remove();
		},
		initTab : function(tabDivId, actTabClass){
			var selClass = (actTabClass ? actTabClass : "selected");
			$("#"+tabDivId + " li" ).click({tabDivId : tabDivId, actTabClass : selClass}, changeTab);
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
	    		changeTab(vEvent, tabConfig.tabDivId, selClass, true);
	    	}else{
	    		$("#" + tabConfig.tabDivId + " > ul").append("<li tar='"+tarCntDivId+"' remoteUrl='"+(tabConfig.remoteUrl ? tabConfig.remoteUrl : "")+"' cacheTime='"+(tabConfig.cacheTime ? tabConfig.cacheTime : "")+"' callback='"+(tabConfig.callback ? tabConfig.callback : "")+"' onclick='changeTab(event, \"" + tabConfig.tabDivId + "\", \"" + selClass +"\");' id='"+tabId+"' class='"+selClass+"'>"+(tabConfig.title ? tabConfig.title : tabId)+"&nbsp;&nbsp;<span onclick='Global.closeTab(\""+tabConfig.tabDivId+"\", \""+tabId+"\", \""+selClass+"\")'>X</span><i class='arrow-bottom'></i></li>");
	    		$("#" + tabConfig.contentDivId).append("<div id='"+tarCntDivId+"' style='display:block;'></div>");
	    		
	    		//刷新内容
	    		refreshTab(tabId, tabConfig.remoteUrl, tabConfig.cacheTime, tabConfig.callback, true);
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
	    }
	}
}();

function changeTab(event, tabDivId, actTabClass, forceFlush){
//    $("#"+tabDivId).delegate("#"+tabDivId + " li:not(."+selClass+")","click",function(){
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
//     var cacheTime = currTab.attr("cacheTime");
//     var lastReqTime = currTab.attr("lastReqTime"); //以秒为单位
//     if((!lastReqTime || new Date().getTime() < lastReqTime + cacheTime * 1000) && (!currTarId || actTarId == currTarId)){
//	   return;
//	 }
    	
     $("#"+actTarId).css("display","none");
     actTabElem.removeClass(actTabClass);
     
     currTab.addClass(actTabClass);
     $("#"+currTarId).css("display","block");
        
     var remoteUrl = currTab.attr("remoteUrl");
     var cacheTime = currTab.attr("cacheTime");
     var callback = currTab.attr("callback");
        
     refreshTab(currTab.attr("id"), remoteUrl, cacheTime, callback, forceFlush);
}

function refreshTab(currTabId, remoteUrl, cacheTime, callback, forceFlush){
	if(remoteUrl){
    	if(!forceFlush && cacheTime && cacheTime != 0){
    		var lastReqTime = $("#" + currTabId).attr("lastReqTime");
    		var currTime = new Date().getTime();
    		if(lastReqTime && currTime <= lastReqTime + cacheTime * 1000){
    			return;
    		}
    	}
    	
//    	$("#" + currTabId).ajaxGetUrl(remoteUrl)
    	
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
