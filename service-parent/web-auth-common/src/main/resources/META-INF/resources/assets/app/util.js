var Util = function() {
	return {
		baiduTJ : function(){
			var _hmt = _hmt || [];
			(function() {
			  var hm = document.createElement("script");
			  hm.src = "//hm.baidu.com/hm.js?8105a189fe2b59c194b82563c2fdc669";
			  var s = document.getElementsByTagName("script")[0]; 
			  s.parentNode.insertBefore(hm, s);
			})();
		},
		assertNotBlank : function(b, a) {
			if (b == undefined || $.trim(b) == "") {
				Util.assert(false, a);
				return
			}
		},
		notSmallViewport : function() {
			var a = $(window).width();
			return a >= 768
		},
		AddOrReplaceUrlParameter : function(f, a, e) {
			var d = f.indexOf("?");
			if (d == -1) {
				f = f + "?" + a + "=" + e
			} else {
				var g = f.split("?");
				var h = g[1].split("&");
				var c = "";
				var b = false;
				for (i = 0; i < h.length; i++) {
					c = h[i].split("=")[0];
					if (c == a) {
						h[i] = a + "=" + e;
						b = true;
						break
					}
				}
				if (!b) {
					f = f + "&" + a + "=" + e
				} else {
					f = g[0] + "?";
					for (i = 0; i < h.length; i++) {
						if (i > 0) {
							f = f + "&"
						}
						f = f + h[i]
					}
				}
			}
			return f
		},
		init : function() {
			 $.fn.ajaxGetUrl = function(b, d, c, t,isCross) {
						Util.assertNotBlank(b, "ajaxGetUrl调用的url参数不能为空");
						$("#btn-profile-param").hide();
						var a = $(this);
						a.addClass("ajax-get-container");
						a.attr("data-url", b);
						a.css("min-height", "100px");
					 if(typeof STLogUtil != 'undefined') {
						 STLogUtil.beforeUnloadCurrPage();
					 }
						$.ajax({
							type : "GET",
							cache : false,
							timeout:t,
							url : b,
							data : c,
							timeout:t,
							xhrFields:{
								withCredentials:isCross
							},
							crossDomain:isCross,
							dataType : "html",
							success : function(f) {
								if((typeof f == "object" && f.type == "NOT_LOGIN") || (f.indexOf('"type":"NOT_LOGIN"') > 0)){
									alert("您的帐号在另一处登录或被禁用或长时间未操作，请重新登录或联系管理员!");
									location.reload();
									return;
								}
								a.empty();
								var e = $("<div class='ajax-page-inner'/>")
										.appendTo(a);
								e.hide();
								e.html(f);
								if (d) {
									d.call(a, f)
								}
								Page.initAjaxBeforeShow(e);
								e.show();
								Page.initAjaxAfterShow(e);
								Grid.initAjax(e);
							},
							error : function(g, e, f) {
								a.html("<h4>页面内容加载失败</h4>" + g.responseText);
							},
							statusCode : {
								403 : function() {
									Global
											.notify("error", "URL: " + b,
													"未授权访问")
								},
								404 : function() {
									Global.notify("error", "页面未找到：" + b
											+ "，请联系管理员", "请求资源未找到")
								}
							}
						});
						return a
					};
					 $.fn.ajaxPostUrl = function(b, d, c) {
							Util.assertNotBlank(b, "ajaxGetUrl调用的url参数不能为空");
							$("#btn-profile-param").hide();
							var a = $(this);
							a.addClass("ajax-get-container");
							a.attr("data-url", b);
							a.css("min-height", "100px");
							$.ajax({
								type : "POST",
								cache : false,
								url : b,
								data : c,
								dataType : "html",
								success : function(f) {
									a.empty();
									var e = $("<div class='ajax-page-inner'/>")
											.appendTo(a);
									e.hide();
									e.html(f);
									if (d) {
										d.call(a, f)
									}
									Page.initAjaxBeforeShow(e);
									e.show();
									Page.initAjaxAfterShow(e);
									Grid.initAjax(e);
								},
								error : function(g, e, f) {
									a.html("<h4>页面内容加载失败</h4>" + g.responseText);
								},
								statusCode : {
									403 : function() {
										Global
												.notify("error", "URL: " + b,
														"未授权访问")
									},
									404 : function() {
										Global.notify("error", "页面未找到：" + b
												+ "，请联系管理员", "请求资源未找到")
									}
								}
							});
							return a
				};
						
			$.fn.ajaxJsonUrl = function(b, d, c) {
				Util.assertNotBlank(b, "ajaxJsonUrl调用的url参数不能为空");
				var a = $(this);
				$.ajax({
					traditional : true,
					type : "GET",
					cache : false,
					url : b,
					dataType : "json",
					data : c,
					success : function(e) {
						if (e.type == "error" || e.type == "warning"
								|| e.type == "failure") {
							Global.notify("error", e.message)
						} else {
							if (d) {
								d.call(a, e)
							}
							json = e
						}
					},
					error : function(g, e, f) {
						Global.notify("error", "数据请求异常，请联系管理员", "系统错误");
					},
					statusCode : {
						403 : function() {
							Global.notify("error", "URL: " + b, "未授权访问")
						},
						404 : function() {
							Global.notify("error", "请尝试刷新页面试试，如果问题依然请联系管理员",
									"请求资源未找到")
						}
					}
				})
			};
		}
	}
}();
var BooleanUtil = function() { 
	return {
		toBoolean : function(b) {
			if (b) {
				var a = $.type(b);
				if (a === "string"
						&& (b == "true" || b == "1" || b == "y" || b == "yes"
								|| b == "readonly" || b == "checked"
								|| b == "enabled" || b == "enable" || b == "selected")) {
					return true
				} else {
					if (a === "number" && (b == 1)) {
						return true
					}
				}
			}
			return false
		}
	}
}();
var UtilTools = {};
UtilTools.Time ={
        /**
         * 当前时间戳
         * @return <int>        unix时间戳(秒)   
         */
        CurTime: function(){
            return Date.parse(new Date())/1000;
        },
        /**               
         * 日期 转换为 Unix时间戳 
         * @param <string> 2014-01-01 20:20:20  日期格式               
         * @return <int>        unix时间戳(秒)               
         */
        DateToUnix: function(string) {
            var f = string.split(' ', 2);
            var d = (f[0] ? f[0] : '').split('-', 3);
            var t = (f[1] ? f[1] : '').split(':', 3);
            return (new Date(
                    parseInt(d[0], 10) || null,
                    (parseInt(d[1], 10) || 1) - 1,
                    parseInt(d[2], 10) || null,
                    parseInt(t[0], 10) || null,
                    parseInt(t[1], 10) || null,
                    parseInt(t[2], 10) || null
                    )).getTime() / 1000;
        },
        /**               
         * 时间戳转换日期               
         * @param <int> unixTime    待时间戳(秒)               
         * @param <bool> isFull    返回完整时间(Y-m-d 或者 Y-m-d H:i:s)               
         * @param <int>  timeZone   时区               
         */
        UnixToDate: function(unixTime, isFull, timeZone) {
            if (typeof (timeZone) == 'number')
            {
                unixTime = parseInt(unixTime) + parseInt(timeZone) * 60 * 60;
            }
            var time = new Date(unixTime * 1000);
            var ymdhis = "";
            ymdhis += time.getUTCFullYear() + "-";
            ymdhis += (time.getUTCMonth()+1) + "-";
            ymdhis += time.getUTCDate();
            if (isFull === true)
            {
                ymdhis += " " + time.getUTCHours() + ":";
                ymdhis += time.getUTCMinutes() + ":";
                ymdhis += time.getUTCSeconds();
            }
            return ymdhis;
        }
};		
