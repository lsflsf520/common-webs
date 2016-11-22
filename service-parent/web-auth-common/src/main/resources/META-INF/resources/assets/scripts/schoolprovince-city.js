var schoolprovince_city = new function(){
	 var g_show_one_div = false;

	 var g_province_first = true;
	 var g_city_first = true;
	 var g_school_first = true;
	 var g_gradeYear_first = true;
	 var g_class_first = true;
	 
	 var g_province_id_old = "";
	 var g_city_id_old = "";
	 var g_school_id_old = "";
	 var g_gradeYear_id_old = "";	 
	 var g_class_id_old = "";
	 var uu_id_prefix = $("input[name=uu_id_prefix]").val();
	 var g_init_province = false;
	 var g_init_city = false;
	 var g_init_school = false;
	 
	 var _self = {
			 g_all_province : false,
			 g_all_city : false,
			 g_all_school : false,
			 g_all_gradeYear : false,
			 g_all_class : false,
			 g_is_priv : false,
		init : function(){
						$("#province_div #province_span").html("");
						$("#city_div #city_span").html("");
						$("#school_div #school_span").html("");
						$("#gradeYear_div #GradeYear_span").html("");
						$("#class_div #class_span").html("");
						if($("#"+uu_id_prefix+"province").length>0)
						 	_self.initProvince();
						if($("#"+uu_id_prefix+"city").length>0)
						 	_self.initCity();
						if($("#"+uu_id_prefix+"school").length>0)
						 	_self.initSchool();
						if($("#"+uu_id_prefix+"gradeYear").length>0)
							_self.initGradeYear();
						if($("#"+uu_id_prefix+"class").length>0)
							_self.initClass();
						_self.initData();
						}, 
		initData : function()
		{
			var _url = "";
			 g_init_province = true;
			 g_init_city = true;
			 g_init_school = true;
			if(_self.g_is_priv)
			{
				_url = BASE_DATA_DOMAIN+"/base/area/getPrivProvince";
			}
		else
			{
				_url = BASE_DATA_DOMAIN+"/base/area/getSchoolProvince";
			}
			$.get(_url,{},function (data){
				if(data.length==undefined||data.length<1)
					{
						return;
					}
					var html_province="";
					g_province_id_old = data[0].id;
					if(_self.g_all_province)
						html_province+="<a id=\"province_"+0+"\"href=\"javascript:void(0)\">所有省份</a>";
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
						html_province+="<a id=\"province_"+_id+"\"href=\"javascript:void(0)\">"+_name+"</a>";
					}
						$("#province_div #province_span").html(html_province);
						_self.chooseProvince($("#province_div #province_span #province_"+g_province_id_old));
						if(_self.g_is_priv)
						{
							_url = BASE_DATA_DOMAIN+"/base/area/getPrivCity";
						}
					else
						{
							_url = BASE_DATA_DOMAIN+"/base/area/getCity";
						}
						$.get(_url,{"pId":g_province_id_old},function (data2){
							if(data2.length==undefined||data2.length<1)
							{
								return;
							}
								var html_city="";
								g_city_id_old = data2[0].id;
								if(_self.g_all_city)
									html_city+="<a id=\"city_"+0+"\"href=\"javascript:void(0)\">所有市</a>";							
								$("#city_div #city_span").html(html_city);
			 					for(var i=0;i<data2.length;i++)
									{
			 							var _id="";
										var _name = "";
										for(var key in data2[i])
											{
												if(key=="id")
													_id=data2[i][key];
												if(key=="name")
													{
														_name = data2[i][key];
													}
											}
										html_city+="<a id=\"city_"+_id+"\"href=\"javascript:void(0)\">"+_name+"</a>";  
			   						}
								$("#city_div #city_span").html(html_city);
									_self.chooseCity($("#city_div #city_span #city_"+g_city_id_old));
									if(_self.g_is_priv)
									{
										_url = BASE_DATA_DOMAIN+"/base/school/getPrivSchool";
									}
								else
									{
										_url = BASE_DATA_DOMAIN+"/base/school/loadSchoolByAreaId";
									}
									$.get(_url,{"areaId":g_city_id_old,"level":2},function (data3){
										if(data3.length==undefined||data3.length<1)
										{
											return;
										}
										var html_school="";
										$("#school_div #school_span").html(html_school);
										if(_self.g_all_school)
											html_school+="<a id=\"school_"+0+"\"href=\"javascript:void(0)\">所有学校</a>";							
										g_school_id_old = data3[0].id;
										for(var i=0;i<data3.length;i++)
											{
					 							var _id="";
												var _name = "";
												for(var key in data3[i])
													{
														if(key=="id")
															_id=data3[i][key];
														if(key=="name")
															{
																_name = data3[i][key];
															}
													}
												html_school+="<a id=\"school_"+_id+"\"href=\"javascript:void(0)\">"+_name+"</a>";  
					   						} 	
										$("#school_div #school_span").html(html_school);
										_self.chooseSchool($("#school_div #school_span #school_"+g_school_id_old));
						}, "jsonp");
			}, "jsonp");
	}, "jsonp");
},
		initProvince : function()
		{
			$("#"+uu_id_prefix+"province").click(function(){
			if(g_init_province)
				{
					$("#province_div #province_span").html("");
					g_init_province = false;
				}
				if($("#province_div #province_span").html()=="")
				{
					if(g_province_first)
					{
						var _url = "";
						if(_self.g_is_priv)
							{
								_url = BASE_DATA_DOMAIN+"/base/area/getPrivProvince";
							}
						else
							{
								_url = BASE_DATA_DOMAIN+"/base/area/getSchoolProvince";
							}
						$.get(_url,{},function (data){
						var html="";
						$("#province_div #province_span").html(html);
						if(_self.g_all_province)
							html+="<a id=\"province_"+0+"\"href=\"javascript:void(0)\">所有省份</a>";
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
							html+="<a id=\"province_"+_id+"\"href=\"javascript:void(0)\">"+_name+"</a>";  
						} 
						if(html=="")
						{
							alert("暂无合作省份");
							g_show_one_div = false;
							return;
						}
						$("#province_div #province_span").html(html);
						$("#province_div").jqpopup_open($("#"+uu_id_prefix+"province").attr("id")); 
						$("#province_div #province_span a").click(function(){
							_self.chooseProvince(this);
							$("#province_div").jqpopup_hide();
						});
						g_province_first = true;
						}, "jsonp");
						g_province_first = false;
					}
				}
				else
				{
					//$("#province_div_p").css("display","block");
					$("#province_div").jqpopup_open($("#"+uu_id_prefix+"province").attr("id")); 
				}
				g_show_one_div = true;
			});	 
		},
		initCity : function()
		{
			$("#"+uu_id_prefix+"city").click(function(){
				if(g_init_city)
				{
					$("#city_div #city_span").html("");
					g_init_city = false;
				}
				if($("input[name=province]").val()=="")
					{
						alert("请先选择省份");
						return;
					}
				if($("input[name=province]").val()=="0")
				{
					alert("您已选择所有省份");
					return;
				}
				var _pId = $("input[name=province]").val();
				if($("#city_div #city_span").html()=="")
					{
					if(g_city_first)
						{
						var _url = "";
						if(_self.g_is_priv)
							{
								_url = BASE_DATA_DOMAIN+"/base/area/getPrivCity";
							}
						else
							{
								_url = BASE_DATA_DOMAIN+"/base/area/getCity";
							}
						$.get(_url,{"pId":_pId},function (data){
							var html="";
							if(_self.g_all_city)
								html+="<a id=\"city_"+0+"\"href=\"javascript:void(0)\">所有市</a>";							
							$("#city_div #city_span").html(html);
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
		    							html+="<a id=\"city_"+_id+"\"href=\"javascript:void(0)\">"+_name+"</a>";  
		   						}
							g_city_first = true;
							if(html=="")
								{
									alert("暂无合作市");
									g_show_one_div = false;
									return;
								}
								$("#city_div #city_span").html(html);
								$("#city_div").jqpopup_open($("#"+uu_id_prefix+"city").attr("id")); 
								$("#city_div_p").css("display","block");
								$("#city_div #city_span a").click(function(){
									_self.chooseCity(this);
									$("#city_div").jqpopup_hide();
								});
						}, "jsonp");
						g_city_first = false;
						}
					}
				else
					{
						//$("#city_div_p").css("display","block");
					$("#city_div").jqpopup_open($("#"+uu_id_prefix+"city").attr("id"));
					}
				g_show_one_div = true;
			});
		},
	initSchool : function()
	{
			$("#"+uu_id_prefix+"school").click(function(){
				if(g_init_school)
				{
					$("#school_div #school_span").html("");
					g_init_school = false;
				}
				if($("input[name=province]").val()=="")
					{
						alert("请先选择省份");
						return;
					}
				if($("input[name=city]").val()=="")
					{
						alert("请先选择市");
						return;
					}
				if($("input[name=city]").val()=="0")
				{
					alert("您已选择所有市");
					return;
				}				
				var _areaId = $("input[name=city]").val();
				if($("#school_div #school_span").html()=="")
					{
					if(g_school_first)
						{
						if(_self.g_is_priv)
						{
							_url = BASE_DATA_DOMAIN+"/base/school/getPrivSchool";
						}
					else
						{
							_url = BASE_DATA_DOMAIN+"/base/school/loadSchoolByAreaId";
						}
						$.get(_url,{"areaId":_areaId,"level":2},function (data){
							var html="";
							$("#school_div #school_span").html(html);
							if(_self.g_all_school)
								html+="<a id=\"school_"+0+"\"href=\"javascript:void(0)\">所有学校</a>";							
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
		    							html+="<a id=\"school_"+_id+"\"href=\"javascript:void(0)\">"+_name+"</a>";  
		   						} 
							g_school_first = true;
							if(html=="")
							{
								alert("暂无合作学校");
								g_show_one_div = false;
								return;
							}
							$("#school_div #school_span").html(html);
							$("#school_div").jqpopup_open($("#"+uu_id_prefix+"school").attr("id")); 
							$("#school_div_p").css("display","block");
							$("#school_div #school_span a").click(function(){
								_self.chooseSchool(this);
								$("#school_div").jqpopup_hide();
							});
						}, "jsonp");
						g_school_first = false;
						}
					}
				else
					{
						//$("#school_div_p").css("display","block");
					$("#school_div").jqpopup_open($("#"+uu_id_prefix+"school").attr("id")); 
					}
				g_show_one_div = true;
			});			
	},
	initGradeYear : function(a)
	{
		$("#"+uu_id_prefix+"gradeYear").click(function(){
			/*
			 if(g_show_one_div == true)
				 return;
			*/
			if($("input[name=province]").val()=="")
				{
					alert("请先选择省份");
					return;
				}
			if($("input[name=city]").val()=="")
				{
					alert("请先选择市");
					return;
				}
			if($("input[name=school]").val()=="")
			{
				alert("请先选择学校");
				return;
			}
			if($("input[name=school]").val()=="0")
			{
				alert("您已选择所有学校");
				return;
			}
			var _schoolId = $("input[name=school]").val();
			if($("#gradeYear_div #gradeYear_span").html()=="")
				{
				if(g_gradeYear_first)
					{
					$.get(BASE_DATA_DOMAIN+"/SchoolStudentCountInfo/querySchoolGradeYear",{"schoolId":_schoolId},function (data){
						var html="";
						$("#gradeYear_div #gradeYear_span").html(html);
						if(_self.g_all_gradeYear)
							html+="<a id=\"gradeYear_"+0+"\"href=\"javascript:void(0)\">所有入学年份</a>";
						/*alert(JSON.stringify(data));*/ 
	 					for(var i=0;i<data.length;i++)
							{
	 							var _id="";
								var _name = "";
								_id = data[i];
								_name = data[i];
	    						html+="<a id=\"gradeYear_"+_id+"\"href=\"javascript:initPaper()\">"+_name+"</a>";  
	   						} 
						g_gradeYear_first = true;
						if(html=="")
						{
							alert("暂无入学年份");
							g_show_one_div = false;
							return;
						}
						$("#gradeYear_div #gradeYear_span").html(html);
						$("#gradeYear_div").jqpopup_open($("#"+uu_id_prefix+"gradeYear").attr("id")); 
						$("#gradeYear_div_p").css("display","block");
						$("#gradeYear_div #gradeYear_span a").click(function(){
							_self.chooseGradeYear(this);
							$("#gradeYear_div").jqpopup_hide(); 
						});
					}, "jsonp");
					g_gradeYear_first = false;
					}
				}
			else
				{
					//$("#gradeYear_div_p").css("display","block");
				$("#gradeYear_div").jqpopup_open($("#"+uu_id_prefix+"gradeYear").attr("id")); 
				}
			g_show_one_div = true;
		});		
	},
	initClass : function(a)
	{
		$("#"+uu_id_prefix+"class").click(function(){
			/*
			 if(g_show_one_div == true)
				 return;
			*/
			if($("input[name=province]").val()=="")
				{
					alert("请先选择省份");
					return;
				}
			if($("input[name=city]").val()=="")
				{
					alert("请先选择市");
					return;
				}
			if($("input[name=school]").val()=="")
			{
				alert("请先选择学校");
				return;
			}
			if($("input[name=gradeYear]").val()=="")
			{
				alert("请先选择入学年份");
				return;
			}
			if($("input[name=gradeYear]").val()=="0")
			{
				alert("您已选择所有入学年份");
				return;
			}			
			var _schoolId = $("input[name=school]").val();
			var _gradeYear = $("input[name=gradeYear]").val();
			if($("#class_div #class_span").html()=="")
				{
				if(g_class_first)
					{
					$.get(BASE_DATA_DOMAIN+"/base/class/getBySchoolIdAndGradeYear",{"schoolId":_schoolId,"gradeYear":_gradeYear},function (data){
						var html="";
						$("#class_div #class_span").html(html);
						if(_self.g_all_class)
							html+="<a id=\"class_"+0+"\"href=\"javascript:void(0)\">所有班级</a>";						
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
	    							html+="<a id=\"class_"+_id+"\"href=\"javascript:void(0)\">"+_name+"</a>";  
	   						} 
						g_class_first = true;
						if(html=="")
						{
							alert("暂无班级");
							g_show_one_div = false;
							return;
						}
						$("#class_div #class_span").html(html);
						$("#class_div").jqpopup_open($("#"+uu_id_prefix+"class").attr("id")); 
						$("#class_div_p").css("display","block");
						$("#class_div #class_span a").click(function(){
							_self.chooseClass(this);
							$("#class_div").jqpopup_hide(); 
						});
					}, "jsonp");
					g_class_first = false;
					}
				}
			else
				{
					//$("#class_div_p").css("display","block");
				$("#class_div").jqpopup_open($("#"+uu_id_prefix+"class").attr("id")); 
				}
			g_show_one_div = true;
		});		
	},
	chooseProvince : function(a)
	{
		var _province_id = $(a).attr("id").replace("province_","");
		$("#province_div #province_span a").each(function(){
			$(this).removeClass("selected7");
		});	
		$(a).addClass("selected7");
		$("input[name=province]").val(_province_id);
		$("#"+uu_id_prefix+"province span").html($(a).html());
		$("#province_div_p").css("display","none");
		g_show_one_div = false;
		if($("#"+uu_id_prefix+"city").length<=0)
			return ;
		var reset_msg="请选择市";
		if(_province_id=="0")
			{
				reset_msg = "所有市";
			}
		if(_province_id!=g_province_id_old)
			{
				_self.resetCityData(reset_msg);
				g_province_id_old = _province_id;
			}
	},
	chooseCity : function(a)
	{
		var _city_id = $(a).attr("id").replace("city_","");
		$("#city_div #city_span a").each(function(){
			$(this).removeClass("selected7");
		});	
		$(a).addClass("selected7");
		$("input[name=city]").val(_city_id);
		$("#"+uu_id_prefix+"city span").html($(a).html());
		$("#city_div_p").css("display","none");
		g_show_one_div = false;
		if($("#"+uu_id_prefix+"school").length<=0)
			return;
		var reset_msg="请选择学校";
		if(_city_id=="0")
			{
				reset_msg = "所有学校";
			}		
		if(_city_id!=g_city_id_old)
		{
			_self.resetSchoolData(reset_msg);
			g_city_id_old = _city_id;
		}
	},
	chooseSchool : function(a)
	{
		var _school_id = $(a).attr("id").replace("school_","");
		$("#school_div #school_span a").each(function(){
			$(this).removeClass("selected7");
		});	
		$(a).addClass("selected7");
		$("input[name=school]").val(_school_id);
		$("#"+uu_id_prefix+"school span").html($(a).html());
		$("#school_div_p").css("display","none");
		g_show_one_div = false;
		if($("#"+uu_id_prefix+"gradeYear").length<=0)
			return;
		var reset_msg="请选择入学年份";
		if(_school_id=="0")
			{
				reset_msg = "所有入学年份";
			}		
		if(_school_id!=g_school_id_old)
		{
			_self.resetGradeYearData(reset_msg);
			g_school_id_old = _school_id;
		}		
	},
	chooseGradeYear : function(a)
	{
		var _gradeYear_id = $(a).attr("id").replace("gradeYear_","");
		$("#gradeYear_div #gradeYear_span a").each(function(){
			$(this).removeClass("selected7");
		});	
		$(a).addClass("selected7");
		$("input[name=gradeYear]").val(_gradeYear_id);
		$("#"+uu_id_prefix+"gradeYear span").html($(a).html());
		$("#gradeYear_div_p").css("display","none");
		g_show_one_div = false;
		if($("#"+uu_id_prefix+"class").length<=0)
			return;
		var reset_msg="请选择班级";
		if(_gradeYear_id=="0")
			{
				reset_msg = "所有班级";
			}		
		if(_gradeYear_id!=g_gradeYear_id_old)
		{
			_self.resetClassData(reset_msg);
			g_gradeYear_id_old = _gradeYear_id;
		}	
	},
	chooseClass : function(a)
	{
		var _class_id = $(a).attr("id").replace("class_","");
		$("#class_div #class_span a").each(function(){
			$(this).removeClass("selected7");
		});	
		$(a).addClass("selected7");
		$("input[name=class]").val(_class_id);
		$("#"+uu_id_prefix+"class span").html($(a).html());
		$("#class_div_p").css("display","none");
		g_show_one_div = false;
	},
	resetCityData : function(reset_msg)
	{
		$("#city_div #city_span").html("");
		$("input[name=city]").val("");
		$("#"+uu_id_prefix+"city span").html(reset_msg);
		g_city_first = true;
		g_city_id_old = "";
		if($("input[name=province]").val()=="0")
		{
			reset_msg = "所有学校";
			$("input[name=city]").val("0");
		}
		else
			{
				reset_msg = "请选择学校";
			}		
		if($("#"+uu_id_prefix+"school").length<=0)
			return;
		_self.resetSchoolData(reset_msg); 
	},
	resetSchoolData : function(reset_msg)
	{
			$("#school_div #school_span").html("");	
			$("input[name=school]").val("");
			$("#"+uu_id_prefix+"school span").html(reset_msg);
			g_school_first = true;	
			g_school_id_old = "";
			if($("input[name=city]").val()=="0")
			{
				reset_msg = "所有入学年份";
				$("input[name=school]").val("0");
			}
			else
				{
					reset_msg = "请选择入学年份";
				}			
			if($("#"+uu_id_prefix+"gradeYear").length<=0)
				return;
			_self.resetGradeYearData(reset_msg); 
	},
	resetGradeYearData : function(reset_msg)
	{
			$("#gradeYear_div #gradeYear_span").html("");
			$("input[name=gradeYear]").val("");
			$("#"+uu_id_prefix+"gradeYear span").html(reset_msg);
			g_gradeYear_first = true;
			g_gradeYear_id_old = "";
			if($("input[name=school]").val()=="0")
			{
				reset_msg = "所有班级";
				$("input[name=gradeYear]").val("0");
			}
			else
				{
					reset_msg = "请选择班级";
				}				
			if($("#"+uu_id_prefix+"class").length<=0)
				return;
			_self.resetClassData(reset_msg); 
	},
	resetClassData : function(reset_msg)
	{
			$("#class_div #class_span").html("");	
				$("input[name=class]").val("");
				
				if($("input[name=gradeYear]").val()=="0")
				{
					reset_msg = "所有班级";
					$("input[name=class]").val("0");
				}
				else
					{
						reset_msg = "请选择班级";
					}
			$("#"+uu_id_prefix+"class span").html(reset_msg);
			g_class_first = true;
			g_class_id_old = "";
	},
	};
return _self;
}();

function initPaper(){
	var paperType = $("input[name=isGetPaper]").val();
	if(paperType== 1){
		//考前冲刺卷
		findKQCCJ();
	}
	else if(paperType== 2){
		//提分密卷
		
	}
	else{
		
	}
}

function findKQCCJ(){
	if($("input[name=school]").val()=="")
	{
		alert("请选择学校");
		return false;
	}
	if($("input[name=gradeYear]").val()=="")
	{
		alert("请选择年级");
		return false;
	}
	var g_school_id = $("input[name=school]").val();	
	var g_gradeYear = $("input[name=gradeYear]").val();
	var uu_id_prefix = $("input[name=uu_id_prefix]").val();
	$.ajax({
		url:BASE_DATA_DOMAIN+'/sprintTestManage/findKQCCJ',
		data:{
			schoolId:g_school_id,
			gradeYear:g_gradeYear
			},    
		type:'POST',               
		dataType:'json',            
		success:function (data){
			document.getElementById(uu_id_prefix+"test").innerHTML = "";
			if(data != null){				
				$.each(data,function (i,item) { 
					var bjId  = item.id;
					var bjmc = item.name;
					$("#"+uu_id_prefix+"test").css("line-height","35px");
					jQuery("#"+uu_id_prefix+"test").append("<option value="+ bjId+">"+ bjmc+"</option>");
				});
			}
			else
			{
				return;
			}
	
		},
		error:function() {   
			 
		}
 	});
}
/*$(function(){
	schoolprovince_city.init();
});*/
