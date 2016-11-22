var user_info_modify = new function()
{
	var _self = 
	{
		entity : null,
		regTele : /^1[3|4|5|7|8][0-9]\d{8}$/,
		regFixPhone :/^([0-9]{3,4}-?)?[0-9]{7,8}$/,
		regEmail :  /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/,
		regNum : /^[0-9]([0-9])*$/,
		isSubmiting : false,
		createEntity : function()
		{
			var _entity = new function()
			{
				var _self_entity = {
				provinceId : null,
				cityId : null,
				countyId : null,
				schoolId : null,
				icon : null,
				sex : null,
				gradeYear : null,
				classId : null,
				sType : null,
				scoreSegment : null,
				qqNum : null,
				realName : null,
				telePhone : null,
				parentName : null,
				parentPhone : null,
				email : null,
				targetScore : null,
				motto : null,
				};	
				return _self_entity;
			}();
			return _entity;
		},
		init : function()
		{
			this.entity = this.createEntity();
		},
		hideDD : function(obj)
		{
			$(obj).parent().parent().parent().css("display","none");
			$(obj).parent().parent().parent().prev().html($(obj).html());
		},
		submitOld : function(){
			if(this.check()){
				this.submitForm("saveBtn");
			}
		},
		check : function()
		{
			if(!this.checkName())
				return false;
			if(!this.checkTelePhone())
				return false;
			if(!this.checkSchool())
				return false;
			if(!this.checkGradeYear())
				return false;
			if(!this.checkClass())
				return false;
			if(!this.checkScoreSegment())
				return false;
			if(!this.checkParentName())
				return false;
			if(!this.checkParentPhone())
				return false;
//			if(!this.checkEmail())
//				return;
			if(!this.checkTargetScore())
				return false;
			if($("#sex .selectd").length==0)
				{
				    globalJs.alert("请选择您的性别");
				    return false;
				}
			/*if($("#chooseSubject .selectd").length==0)
			{
				alert("请选择您的文理科");
				return;
			}*/	
			if(!this.checkMotto()){
				return false;
			}
			this.entity.sex = $("#sex .selectd").attr("name");
			this.entity.sType = $("#chooseSubject .selectd").attr("name");
			//this.entity.motto = $("#motto").val();
			this.entity.userIcon = 	$("#userIcon").attr("src");
			if(this.entity.userIcon){
				this.entity.userIcon = this.entity.userIcon.replace(PROJECT_RES_DOMAIN + WEB_ROOT, "");
			}
			this.entity.qqNumber = $("input[name=qq]").val();
			
			return true;
		},
		submitForm : function(submitBtnId){
			if($("#" + submitBtnId).hasClass("disableA")){
			    globalJs.alert("正在提交");
				return false;
			}
			$("#" + submitBtnId).addClass("disableA");
			$("#" + globalJs.getCancelBtnId()).css("display", "none");
			$("#" + submitBtnId).text("正在保存...");
			$.post(WEB_ROOT + "/priv/info/save", this.entity,function(data){
			  if(data && data.type && data.type != "success"){
				if("NOT_LOGIN" == data.type){
				  location.reload();
				  return;
				}
				globalJs.hideConfirmDialog();
				globalJs.alert(data.message);
				
				$("#" + submitBtnId).removeClass("disableA");
				$("#" + submitBtnId).text("保存");
				return ;
			  }
			  hasStdInfo = true;
			  user_info_modify.previewInfo(true); 
			  
			  //设置 _header.jsp 头部的用户信息
			  //$("#headerUserNm").text($('#pvw_name').text());
			  //$("#headerSchName").text($('#pvw_school').text());
			});
		},
		confirmSave : function(){
			if(this.check()){
				globalJs.confirm("请填写真实正确的个人资料，保存后将不能修改，是否保存？", "javascript:user_info_modify.submitForm('"+globalJs.getOKBtnId()+"')");
			}
			
		},
		previewInfo : function(forceView){
			if(forceView || hasStdInfo){
				location.reload();
				/*$('#pvw_headImg').attr("src", $('#userIcon').attr("src"));
				$('#pvw_card').text($('#cardNum').val());
				$('#pvw_name').text($('#realName').val());
				$('#pvw_sex').text($("#sex .selectd").text());
				$('#pvw_mobile').text($('#telePhone').val());
				$('#pvw_area').text($('#province').text() + $('#city').text() + $('#county').text());
				$('#pvw_school').text($('#school').text());
				$('#pvw_gradeyear').text($('#gradeYear').text() + "年");
				$('#pvw_class').text($('#clazz').text());
				$('#pvw_stype').text($('#chooseSubject .selectd').text());
				$('#pvw_scoreSeg').text($('#scoreSegment').text());
				$('#pvw_aim_score').text($('#targetScore').val() + "分");
				$('#pvw_parent_name').text($('#parentName').val());
				$('#pvw_parent_mobile').text($('#parentPhone').val());
				$('#pvw_motto').text($('#motto').val());
				
				$('#editDiv').css('display', 'none');
				$('#chgTip').css('display', 'none');
				$('#previewDiv').css('display', 'block');*/
			}else{
				globalJs.alert("为了确保你能获得更适合自己的学习方案，请先提供真实的个人信息。");
			}
			
		},
		checkMotto : function(){
			var _motto = $("#motto").val();
			if(_motto && _motto.length > 500){
				
				this.showWarning("#mottoWarning");
				return;
			}
			$("#mottoWarning").css("display", "none");
			this.entity.motto = $.trim(_motto);
			return true;
		},
		checkName : function()
		{
			var _realName = $("#realName").val();
			if(!_realName || !/^[\u4e00-\u9fa5]+$/.test(_realName) || _realName.length < 2 || _realName.length > 10){
				  this.showWarning("realNameWarning");
				  location.href="#realNameArea";
				  return;
			}
			this.entity.realName = $.trim(_realName);
			$("#realNameWarning").css("display", "none");
			return true;
		},
		checkTelePhone : function()
		{
			var _telePhone = $("#telePhone").val();
			if(this.isBlankStr(_telePhone)||!this.regTele.test(_telePhone))
			{
				this.showWarning("telePhoneWarning");
				location.href="#telePhoneArea";
				return false;
			}
			this.entity.phoneNum = _telePhone;		
			$("#telePhoneWarning").css("display", "none");
			return true;
		},
		checkSchool : function()
		{
			if($("#school input[type=hidden]").length<=0)
				{
				    $("#schoolWarning").text("请选择你的学校");
					this.showWarning("schoolWarning");	
					location.href="#schoolArea";
					return false;
				}
			var _school = $("#school input[type=hidden]").val();
			if(this.isBlankStr(_school))
			{
				$("#schoolWarning").text("请选择你的学校");
				this.showWarning("schoolWarning");
				location.href="#schoolArea";
				return false;
			}	
			this.entity.schoolId = _school;
			$("#schoolWarning").css("display", "none");
			return true;			
		},
		checkGradeYear : function()
		{
			if($("#gradeYear input[type=hidden]").length<=0)
				{
					this.showWarning("gradeYearWarning");
					location.href="#gradeYearArea";
					return false;
				}
			var _gradeYear = $("#gradeYear input[type=hidden]").val();
			if(this.isBlankStr(_gradeYear))
			{
				this.showWarning("gradeYearWarning");
				location.href="#gradeYearArea";
				return false;
			}
			this.entity.gradeYear = _gradeYear;
			$("#gradeYearWarning").css("display", "none");
			return true;
		},	
		checkClass : function()
		{
			if($("#clazz input[type=hidden]").length<=0)
				{
				    $("#classWarning").text("请选择你的班级");
					this.showWarning("classWarning");
					location.href="#clazzArea";
					return false;
				}
			var _clazz = $("#clazz input[type=hidden]").val();
			if(this.isBlankStr(_clazz))
			{
				$("#classWarning").text("请选择你的班级");
				this.showWarning("classWarning");
				location.href="#clazzArea";
				return false;
			}	
			this.entity.classId = _clazz;
			$("#classWarning").css("display", "none");
			return true;			
		},	
		checkScoreSegment : function()
		{
			if($("#scoreSegment input[type=hidden]").length<=0)
			{
				this.showWarning("scoreSegmentWarning");
				location.href="#scoreSegArea";
				return false;
			}
			var _scoreSegment = $("#scoreSegment input[type=hidden]").val();
			if(this.isBlankStr(_scoreSegment))
			{
				this.showWarning("scoreSegmentWarning");
				location.href="#scoreSegArea";
				return false;
			}	
			this.entity.studentSection = _scoreSegment;
			$("#scoreSegmentWarning").css("display", "none");
			return true;			
		},		
		checkParentName : function()
		{
			var _parentName = $("#parentName").val();
			if(this.isBlankStr(_parentName) || !/^[\u4e00-\u9fa5]+$/.test(_parentName) || _parentName.length < 2 || _parentName.length > 10)
			{
				this.showWarning("parentNameWarning");
				location.href="#parentNameArea";
				return false;
			}
			this.entity.parentName = $.trim(_parentName);
			$("#parentNameWarning").css("display", "none");
			return true;			
		},
		checkParentPhone : function()
		{
			var _telePhone = $("#parentPhone").val();
			if(this.isBlankStr(_telePhone))
			{
				this.showWarning("parentPhoneWarning");
				location.href="#parentPhoneArea";
				return false;
			}	
			if(!this.regTele.test(_telePhone))
				{
					if(!this.regFixPhone.test(_telePhone))
						{
							this.showWarning("parentPhoneWarning");
							location.href="#parentPhoneArea";
							return false;
						}
				}
			this.entity.parentTelephone = _telePhone;
			$("#parentPhoneWarning").css("display", "none");
			return true;			
		},	
		checkEmail : function()
		{
			var _email = $("input[name=email]").val();
			if(this.isBlankStr(_email)||!this.regEmail.test(_email))
				{
					this.showWarning("emailWarning");
					return false;
				}
			this.entity.email = _email;
			$("#emailWarning").css("display", "none");
			return true;
		},
		checkTargetScore : function()
		{
			var _targetScore = $("#targetScore").val();
			if(this.isBlankStr(_targetScore)||!this.regNum.test(_targetScore))
				{
					this.showWarning("targetScoreWarning");
					location.href="#targetArea";
					return false;
				}
			if(!this.checkTargetScoreRange(parseInt(_targetScore))){
				location.href="#targetArea";
				return false;			
			}
			$(".red-tip1").css("display","none");
			this.entity.target = _targetScore;
			$("#targetScoreWarning").css("display", "none");
			return true;
		},
		checkTargetScoreRange : function(tagretScore)
		{
			var provinceId = $("#province").find("input[type=hidden]").val();
			var sType = $("#chooseSubject .selectd").attr("id");
			if(provinceId==32)
				{
					if(sType=="sType1")
						{
							if(tagretScore>160)
								{
									this.showWarning("targetScoreWarning","请输入0-160之间的分数");
									return false;
								}
						}
					else{
							if(tagretScore>200)
								{
									this.showWarning("targetScoreWarning","请输入0-200之间的分数");
									return false;
								}
						}
				}
			else
				{
					if(tagretScore>150)
						{
							this.showWarning("targetScoreWarning","请输入0-150之间的分数");
							return false;
						}
				}
			$("#targetScoreWarning").css("display", "none");
			return true;
		},
		isBlankStr :function(str)
		{
			str = $.trim(str);
			if(str=="")
				return true;
			return false;
		},
		showWarning : function(id)
		{
			var b = arguments[1] ? arguments[1] : null; 
			if(b!=null)
			{
				$("#"+id).html(b);	
			}
			$(".red-tip1").css("display","none");
			$("#"+id).css("display","block");
		},
		checkJiangsu : function()
		{
			var provinceId = this.entity.provinceId;
			var sType = $("#chooseSubject .selectd").attr("id");
			var array = null;
			if(provinceId==32)
				{
					if(sType=="sType1")
						{
							array = score_jiangsu_wen;
						}
					else
						{
							array = score_jiangsu_li;
						}
				}
			else
				{
					array = score_common;
				}
			var html="";
			for(var i=0;i<array.length;i++)
				{
					html+="<li><a href='javascript:void(0)' onclick='user_info_modify.chooseScoreSegment(this)'>"+array[i]+"<input type='hidden' value='"+i+"'/></a></li>";
				}
			$("#scoreSegmentUl").html(html);
			$("#scoreSegment").html(array[0]+"<input type='hidden' value='"+0+"'/>");
		},
		chooseProvince : function(obj)
		{
			this.hideDD(obj);
			var provinceId = $(obj).find("input[type=hidden]").val();
			if(this.entity.provinceId==provinceId)
				return;
			this.entity.provinceId = provinceId;
			_self.checkJiangsu(provinceId);
			_self.resetCity();
			$.get(baseDataDomain + "/base/area/getCity",{"pId":provinceId}, function(data){
				var html="";
				for(var i=0;i<data.length;i++)
				{
					var _id="";
					var _name = "";
					for(var key in data[i])
						{
							if(key=="id")
								_id=data[i][key];
							if(key=="name")
								{
									_name = data[i][key];
								}
						}
					html+="<li><a href='javascript:void(0)' onclick='user_info_modify.chooseCity(this)'>"+_name+"<input type='hidden' value='"+_id+"'/></a></li>";
					}
				$("#city").next().find("ul").html(html);
			}, "jsonp");
		},
		 chooseCity : function(obj)
		{
			this.hideDD(obj);
			var cityId = $(obj).find("input[type=hidden]").val();
			if(this.entity.cityId==cityId)
				return;
			this.entity.cityId = cityId;
			_self.resetCounty();
			$.get(baseDataDomain + "/base/area/getCountry",{"pId":cityId},function (data){
				var html="";
				
				for(var i=0;i<data.length;i++)
				{
					var _id="";
					var _name = "";
					for(var key in data[i])
						{
							if(key=="id")
								_id=data[i][key];
							if(key=="name")
								{
									_name = data[i][key];
								}
						}
					html+="<li><a href='javascript:void(0)' onclick='user_info_modify.chooseCounty(this)'>"+_name+"<input type='hidden' value='"+_id+"'/></a></li>";
					}
				$("#county").next().find("ul").html(html);
				if(html=="")
					{
					$.get(baseDataDomain + "/base/school/loadSchoolByAreaId",{"areaId":cityId,"level":2},function (result){
						var html2="";
	 					for(var i=0;i<result.length;i++)
							{
	 							var _id="";
								var _name = "";
								for(var key in result[i])
									{
										if(key=="id")
											_id=result[i][key];
										if(key=="name")
											{
												_name = result[i][key];
											}
									}
								html2+="<li><a href='javascript:void(0)' onclick='user_info_modify.chooseSchool(this)'>"+_name+"<input type='hidden' value='"+_id+"'/></a></li>";
	   						} 
	 					$("#school").next().find("ul").html(html2);
	 					if(!html2){
	 						$("#schoolWarning").text("该地区暂无合作的学校");
	 						$("#schoolWarning").css("display", "block");
	 					}else{
							$("#schoolWarning").css("display", "none");
						}
					}, "jsonp");
					}
			}, "jsonp");			
		}, 
		chooseCounty : function(obj)
		{
			this.hideDD(obj);
			var countyId = $(obj).find("input[type=hidden]").val();
			if(this.entity.countyId==countyId)
				return;
			this.entity.countyId = countyId;
			_self.resetSchool();
			$.get(baseDataDomain + "/base/school/loadSchoolByAreaId",{"areaId":countyId,"level":3},function (data){
				var html="";
					for(var i=0;i<data.length;i++)
					{
							var _id="";
						var _name = "";
						for(var key in data[i])
							{
								if(key=="id")
									_id=data[i][key];
								if(key=="name")
									{
										_name = data[i][key];
									}
							}
						html+="<li><a href='javascript:void(0)' onclick='user_info_modify.chooseSchool(this)'>"+_name+"<input type='hidden' value='"+_id+"'/></a></li>";
						} 
					$("#school").next().find("ul").html(html);
					if(!html){
 						$("#schoolWarning").text("该地区暂无合作的学校");
 						$("#schoolWarning").css("display", "block");
 					}else{
						$("#schoolWarning").css("display", "none");
					}
			}, "jsonp");			
		},
		chooseSchool : function(obj)
		{
			this.hideDD(obj);
			var schoolId = $(obj).find("input[type=hidden]").val();
			if(this.entity.schoolId==schoolId)
				return;
			this.entity.schoolId = schoolId;
			_self.resetGradeYear();			
		},
		chooseGradeYear : function(obj)
		{
			this.hideDD(obj);
			var gradeYear = $(obj).find("input[type=hidden]").val();
			if(this.entity.gradeYear==gradeYear)
				return;
			this.entity.gradeYear = gradeYear;
			
			if(this.getGradeId(gradeYear) <= 1){
				$("#chooseSubject").css("display", "none");
			}else{
				$("#chooseSubject").css("display", "block");
			}
			
			_self.resetClass();
			var schoolId = $("#school").find("input[type=hidden]").val();
			$.get(baseDataDomain + "/base/class/getBySchoolIdAndGradeYear",{"schoolId":schoolId,"gradeYear":gradeYear},function (data){
				var html="";
					for(var i=0;i<data.length;i++)
					{
						var _id="";
						var _name = "";
						for(var key in data[i])
							{
								if(key=="id")
									_id=data[i][key];
								if(key=="name")
									{
										_name = data[i][key];
									}
							}
						html+="<li><a href='javascript:void(0)' onclick='user_info_modify.chooseClass(this)'>"+_name+"<input type='hidden' value='"+_id+"'/></a></li>";
						} 
					$("#clazz").next().find("ul").html(html);
					if(!html){
						$("#classWarning").text("该学校暂无班级");
						$("#classWarning").css("display", "block");
					}else{
						$("#classWarning").css("display", "none");
					}
			}, "jsonp");				
		},
		getGradeId:function(gradeYear){
			var myDate = new Date();
			var year = myDate.getFullYear();
			var month= myDate.getMonth() + 1;
			var day = myDate.getDate();
			if (month >= 7 && day >= 1) {
				year += 1;
			}
			year -= gradeYear;
			return year;
		},
		chooseClass : function(obj)
		{
			this.hideDD(obj);
		},
		chooseScoreSegment : function(obj)
		{
			this.hideDD(obj);			
		},
		chooseStype : function(obj)
		{
			$("#chooseSubject a").each(function(){
				$(this).removeClass("selectd");
			});
			$(obj).addClass("selectd");
			this.checkJiangsu(); 
		},
		chooseSex : function(obj)
		{
			$("#sex a").each(function(){
				$(this).removeClass("selectd");
			});
			$(obj).addClass("selectd");
		},
		resetCity : function()
		{
			$("#city").html("");
			$("#city").next().find("ul").html("");
			this.entity.cityId = null;
			_self.resetCounty();
		},
		resetCounty : function()
		{
			$("#county").html("");
			$("#county").next().find("ul").html("");
			this.entity.countyId = null;
			_self.resetSchool();
		},
		resetSchool : function()
		{
			$("#school").html("");
			$("#school").next().find("ul").html("");
			this.entity.schoolId = null;
			_self.resetGradeYear();
		},
		resetGradeYear : function()
		{
			$("#gradeYear").html("");
			this.entity.gradeYear = null;
			_self.resetClass();			
		},
		resetClass : function()
		{
			$("#clazz").html("");
			$("#clazz").next().find("ul").html("");
			this.entity.classId = null;
		},
		showEditIcon : function()
		{
			var src = $("#userIcon").attr("src");
			$("#editIconDiv a img").each(function(){
				if($(this).attr("src")==src)
					{
					if($(this).parent().hasClass("selectd"))
						return false;
					else{
							$(this).parent().addClass("selectd");
								return false;
						}
					}
			});
			$("#popDiv,#popIframe,#t_bg1").css("display","block"); 
		},
		hideEditIcon : function()
		{
			$("#popDiv,#popIframe,#t_bg1").css("display","none");
			$("#editIconDiv a img").each(function(){
				$(this).parent().removeClass("selectd");
			});
		},
		chooseIcon : function(a)
		{
			if($(a).hasClass("selectd"))
				return;
			else
				{
					$("#editIconDiv a").each(function(){
						$(this).removeClass("selectd");
					});
					$(a).addClass("selectd");
				}
		},
		confirmIcon :function()
		{
			var length = $("#editIconDiv .selectd").length;
			if(length>=0)
			{
				var src = $("#editIconDiv .selectd img").attr("src");
				$("#userIcon").attr("src",src);
				$(".user-pic img").attr("src",src);
			}
			this.hideEditIcon();
		},
		sendEmailCode : function(emailAddr, errInfoSel, goSel, existCheck){
			  if("#goEmail" != goSel && (!emailAddr || /\s/.test(emailAddr))){
				  $(errInfoSel).text("邮箱不能为空且不能包含空字符！");
				  return;
			  }
			  
			var errElem = $(errInfoSel);
			  if(emailAddr && !/\w+@qq.com$/.test(emailAddr)){
				  if(errElem){
					  $(errInfoSel).text("请输入正确的QQ邮箱地址！");
				  }else{
					  globalJs.alert("请输入正确的QQ邮箱地址！");
				  }
				  return;
			  }
			  
			  if(errElem){
				  errElem.text("");
			  }
			  $.ajax({
				  url : WEB_ROOT + "/valid/emailcode/send?email=" + (emailAddr ? emailAddr : "") + (existCheck ? "&existCheck=true" : "") ,
				  type : "GET",
				  success : function(data){
					  if(data && data.type && data.type != "success"){
						  $(goSel).css("display", "none");
						  errElem.text(data.message);
						  return;
					  }
					  
					  $(goSel).css("display", "block");
				  }
			  });
		  },
		  checkEmailAvail : function(code, errInfoSel){
			  if(!code || /\s/.test(code)){
				  $(errInfoSel).text("验证码不能为空！");
				  return;
			  }
			  
			  $(errInfoSel).text("");
			  $.ajax({
				  url : WEB_ROOT + "/valid/emailcode/check?code=" + code,
				  type : "GET",
				  success : function(data){
					  if(data && data.type && data.type != "success"){
						  $(errInfoSel).text(data.message);
						  return;
					  }
					  toggle("email_bind_div1");
					  toggle("email_bind_div2");
					  
					  $("#emailCode1").val("");
				  }
			  });
		  },
		  bindEmail : function(email, code, errInfoSel, errCdSel){
			  if(!email || /\s/.test(email) || !/\w+@qq.com$/.test(email)){
				  $(errInfoSel).text("请输入正确的QQ邮箱地址！");
				  return;
			  }
			  $(errInfoSel).text("");
			  
			  if(!code || /\s/.test(code)){
				  $(errCdSel).text("验证码不能为空！");
				  return;
			  }
			  $(errCdSel).text("");
			  
			  $.ajax({
				  url : WEB_ROOT + "/priv/info/bindEmail",
				  type : "POST",
				  data : {email : email, code : code},
				  success : function(data){
					  if(data && data.type && data.type != "success"){
						  $(errInfoSel).text(data.message);
						  return;
					  }
					  
					  $("#emCode2").val("");
					  $("#emailCode").val("");
					  $("#showEm").text(data.userdata);
					  $("#emOperBtn").unbind("click");
					  $("#emOperBtn").removeAttr("onclick");
					  $("#emOperBtn").click(function(){
						  toggleEmail("email_bind_div1");
					  });
					  if("block" == $("#email_bind_div2").css("display")){
						  toggle("email_bind_div2");
						  toggle("email_bind_div3");
					  }else{
						  toggle("email_bind_div");
						  toggle("email_bind_div_s");
					  }
				  }
			  });
		  },
		  unBind : function(type){
			  if(!type){
				  globalJs.alert("参数错误！");
				  return;
			  }
			  if(confirm("确定要解除绑定吗？")){
				  $.ajax({
					  url : WEB_ROOT + "/user/flogin/unbindUser",
					  type : "POST",
					  data : {loginType : type},
					  success : function(data){
						  if(data && data.type && data.type != "success"){
							  globalJs.alert(data.message);
							  return;
						  }
						  $("#qqState").text("未绑定");
						  $("#qqoperBtn").text("绑定");
						  $("#qqoperBtn").unbind("click");
						  $("#qqoperBtn").removeAttr("onclick");
						  $("#qqoperBtn").attr("href", WEB_ROOT + "/user/flogin/qq");
						  toggle("qq_unbind_div");
					  }
				  });
			  }
		  }
		  
	};
	return _self;
}();
