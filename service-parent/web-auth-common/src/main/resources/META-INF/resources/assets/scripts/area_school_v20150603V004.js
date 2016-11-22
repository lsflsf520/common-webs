function createAreaSchool(containId) {
	var o = new Object();
	o.contain_id=containId;
	
	o.initProvince = "请选择省份";
	o.initCity = "请选择城市";
	o.initCountry = "请选择区县";
	o.initSchool = "请选择学校";
	o.initGradeYear = "请选择入学年份";
	o.initClass = "请选择班级";
	o.initExamPaper = "请选择试卷"
	
	o.province = $("#"+o.contain_id+"area");
	o.city = $("#"+o.contain_id+"city");
	o.country =$("#"+o.contain_id+"country");
	o.school = $("#"+o.contain_id+"school");
	o.gradeYear = $("#"+o.contain_id+"gradeYear");
	o.classList = $("#"+o.contain_id+"tbclass");
	o.examPaper = $("#"+o.contain_id+"examPaper");
	o.cardList = $("#"+o.contain_id+"liStudentCards");
	/*o.queryBtn= $("#"+o.contain_id+"queryNormalBtn");*/
	
	o.provinceTitle = $("#"+o.contain_id+"area span");
	o.cityTitle = $("#"+o.contain_id+"city span");
	o.countryTitle =$("#"+o.contain_id+"country span");
	o.schoolTitle = $("#"+o.contain_id+"school span");
	o.gradeYearTitle = $("#"+o.contain_id+"gradeYear span");
	o.classListTitle = $("#"+o.contain_id+"tbclass span");
	o.examPaperTitle = $("#"+o.contain_id+"examPaper span");	
	
	o.showSpan=$("#"+o.contain_id+"data_span");
	o.citySpan=$("#"+o.contain_id+"city_span");
	o.countrySpan=$("#"+o.contain_id+"country_span");
	o.schoolSpan=$("#"+o.contain_id+"school_span");
	o.gradeYearSpan=$("#"+o.contain_id+"gradeYearHide_span");
	o.classSpan=$("#"+o.contain_id+"class_span");
	o.examPaperSpan=$("#"+o.contain_id+"examPaper_span");
	
	o.provinceHide=$("#"+o.contain_id+"provinceHide");
/*	o.cityHide=$("#"+o.contain_id+"cityHide");
	$("#"+o.contain_id+"schoolHide")=$("#"+o.contain_id+"countryHide");
	$("#"+o.contain_id+"gradeYearHide")=$("#"+o.contain_id+"schoolHide");
	o.gradeYearHide=$("#"+o.contain_id+"gradeYearHide");
	o.classHide=$("#"+o.contain_id+"classHide");
	o.ExamPaperHide=$("#"+o.contain_id+"examPaperHide");*/

	o.g_province_id_old = "";
	o.g_city_id_old = "";
	o.g_country_id_old="";
	o.g_school_id_old = "";
	o.g_gradeYear_id_old="";
	o.g_class_id_old="";
	o.g_exampaper_id_old="";
	
	o.provinceLoaded = false;
	o.cityLoaded = false;
	o.countryLoaded = false;
	o.schoolLoaded = false;
	o.gradeYearLoaded = false;
	o.classListLoaded = false;
	o.examPaperLoaded = false;
	
	o.provinceAlreadyShowed = false;
	o.cityAlreadyShowed = false;
	o.countryAlreadyShowed = false;
	o.schoolAlreadyShowed = false;
	o.gradeYearAlreadyShowed = false;
	o.classListAlreadyShowed = false;
	o.examPaperAlreadyShowed = false;
	
	o.resetCity = function(){
		o.countrySpan.html("");
		o.cityTitle.html(o.initCity);
		o.g_city_id_old = "";
		$("#"+o.contain_id+"cityHide").val("");
		o.resetCountry();
		o.cityLoaded = false;
		o.cityAlreadyShowed = false;
	},
	o.resetCountry = function(){
		o.showSpan.html("");
		o.countryTitle.html(o.initCountry);
		o.g_country_id_old = "";
		$("#"+o.contain_id+"countryHide").val("");
		o.resetSchool();
		o.countryLoaded = false;
		o.countryAlreadyShowed = false;
	},
	o.resetSchool = function(){
		$("#"+o.contain_id+"schoolHide").html("");
		o.schoolTitle.html(o.initSchool);
		o.g_school_id_old = "";
		$("#"+o.contain_id+"schoolHide").val("");
		o.schoolLoaded = false;
		o.schoolAlreadyShowed = false;
		o.resetGradeYear();
		o.resetClass();
		o.resetExampaper();
		
	},
	o.resetGradeYear = function(){
		o.showSpan.html("");
		o.gradeYearTitle.html(o.initGradeYear);
		o.g_gradeYear_id_old = "";
		$("#"+o.contain_id+"gradeYearHide").val("");
		o.gradeYearLoaded = false;
		o.gradeYearAlreadyShowed = false;
	},
	o.resetClass = function(){
		o.showSpan.html("");
		o.classListTitle.html(o.initClass);
		o.g_class_id_old = "";
		$("#"+o.contain_id+"classHide").val("");
		o.classListLoaded = false;
		o.classListAlreadyShowed = false;
	},
	o.resetExampaper = function(){
		o.showSpan.html("");
		o.examPaperTitle.html(o.initExamPaper);
		o.g_exampaper_id_old = "";
		$("#"+o.contain_id+"examPaperHide").val("");
		o.examPaperLoaded = false;
		o.examPaperAlreadyShowed = false;
	},

	
	o.chooseProvince=function(a)
	{
		var _province_id = $(a.target).attr("id").replace(o.contain_id+"province_","");;
		$("#"+o.contain_id+"data_span a").removeClass("selected7");
		$(a.target).addClass("selected7");
		o.provinceHide.val(_province_id);
		o.provinceTitle.html($(a.target).html());
		alert("_province_id="+_province_id);
		alert("o.g_province_id_old="+o.g_province_id_old);		
		if(_province_id!=o.g_province_id_old)
			{
				o.fillCitys(o.provinceHide.val());
				o.g_province_id_old = _province_id;
			}
		$("#"+$("#sampleformdiv"+o.contain_id).attr("id")+"_p").hide();
	},	
	
	o.fillProvinces=function(){
		o.showSpan.html("");
		$.get(WEB_ROOT+"/base/area/getSchoolProvince",{},function (data){
			var html="";
			if (data.length>0){
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
					html+="<a id='"+o.contain_id+"province_"+_id+"' href='javascript:void(0)'>"+_name+"</a>";  
					
				}
				o.showSpan.html(html);
				$("#"+o.contain_id+"data_span a").on("click",{id:this},o.chooseProvince);
				o.provinceLoaded = true;
			}
			else{
				html+="<a id='"+o.contain_id+"province_-1' href='javascript:void(0)'>"+_name+"</a>";
				o.showSpan.html(html);
				Global.notify("暂无合作省份");
			}
		});
	},

    o.chooseCity = function(a){
		var _id = $(a.target).attr("id").replace(o.contain_id+"city_","");
		$("#"+o.contain_id+"city_span a").removeClass("selected7");
		$(a.target).addClass("selected7");
		$("#"+o.contain_id+"cityHide").val(_id);
		o.cityTitle.html($(a.target).html());
		alert("_id"+_id);
		alert("o.g_city_id_old"+o.g_city_id_old);
		if(_id!=o.g_city_id_old)
			{
				o.fillCountry($("#"+o.contain_id+"cityHide").val());
				o.g_city_id_old = _id;
			} 
		$("#"+$("#city_div"+o.contain_id).attr("id")+"_p").hide();
    }
	
	o.fillCitys=function(selectParent) {
		alert(selectParent);
		o.resetCity();
		$.get(WEB_ROOT+"/base/area/getCity",{"pId":selectParent},function (data){
			var html="";
			if (data.length>0){
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
					html+="<a id='"+o.contain_id+"city_"+_id+"' href='javascript:void(0)'>"+_name+"</a>";  
					o.citySpan.html(html);
					$("#"+o.contain_id+"city_span a").on("click",{id:this},o.chooseCity);	
					o.cityLoaded = true;
				}				
			}else{
				html+="<a id='"+o.contain_id+"city_-1' href='javascript:void(0)'>本省无合作学校</a>";
				o.citySpan.html(html);
				Global.notify("本省无合作学校");				
			}
		});
    };
    
    o.chooseCountry=function(a){
		var _id = $(a.target).attr("id").replace(o.contain_id+"country_","");
		$("#"+o.contain_id+"country_span a").removeClass("selected7");
		$(a.target).addClass("selected7");
		$("#"+o.contain_id+"countryHide").val(_id);
		o.countryTitle.html($(a.target).html());
		if(_id!=o.g_city_id_old)
			{
				o.fillSchools($("#"+o.contain_id+"countryHide").val(),3);
				o.g_country_id_old = _id;
			}
		$("#"+$("#country_div"+o.contain_id).attr("id")+"_p").hide();
    };
    
    o.fillCountry=function (selectParent) {
    	alert(selectParent);
		o.resetCountry();
		$.get(WEB_ROOT+"/base/area/getCountry",{"pId":selectParent},function (data){
			var html="";
			if (data.length>0){
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
					html+="<a id='"+o.contain_id+"country_"+_id+"' href='javascript:void(0)'>"+_name+"</a>";	  
					o.countrySpan.html(html);
					$("#"+o.contain_id+"country_span a").on("click",{id:this},o.chooseCountry);						
				}				
			}else{
				html+="<a id='"+o.contain_id+"country_-1' href='javascript:void(0)'>本市无合作学校</a>";
				o.countrySpan.html(html);
				Global.notify("本市无合作学校");				
			}
			o.countryLoaded = true;
		}); 
    };

    
    o.fillSchools=function(areaId, level){
    	o.resetSchool();
    	$.ajax({
    		url : WEB_ROOT + "/base/school/loadSchoolByAreaId?areaId=" + areaId + "&level=" + level,
    		type: "GET",
      	    success:function(data){
      	    	var html="";
      	    	if(data.length > 0){
    				for(var i=0;i<data.length;i++)
    				{
						html+="<a id='"+o.contain_id+"school_"+data[i].id+"' href='javascript:void(0)'>"+data[i].name+"</a>";  
						o.schoolSpan.html(html);
						$("#"+o.contain_id+"school_span a").on("click",{id:this},o.chooseSchool);						
    				}      	    		
      	    	}else {
    				html+="<a id='"+o.contain_id+"school_-1' href='javascript:void(0)'>没有合适的学校</a>";
    				o.countrySpan.html(html);
    				Global.notify("没有合适的学校");     	    		
      	    	}
      	    	o.schoolLoaded = true;
      	    }
    	});
    };
    
    o.chooseSchool=function(a){
		var _id = $(a.target).attr("id").replace(o.contain_id+"school_","");
		$("#"+o.contain_id+"school_span a").removeClass("selected7");
		$(a.target).addClass("selected7");
		$("#"+o.contain_id+"schoolHide").val(_id);
		o.schoolTitle.html($(a.target).html());
		if(_id!=o.g_school_id_old){
			if (o.gradeYear.is(":visible")){ 
				o.fillGradeYear($("#"+o.contain_id+"schoolHide").val());
			}
			if (o.classList.is(":visible")){ 
				o.fillClass($("#"+o.contain_id+"schoolHide").val());
			}
			if (o.examPaper.is(":visible")){ 
				o.fillExamPaper($("#"+o.contain_id+"schoolHide").val(),o.provinceHide.val(),$("#"+o.contain_id+"cityHide").val(),$("#"+o.contain_id+"countryHide").val());
			}
			o.g_school_id_old = _id;
		}
		$("#"+$("#school_div"+o.contain_id).attr("id")+"_p").hide();
    };
    
    o.fillClass=function(schoolid){
    	o.resetClass();
		$.ajax({
			url : WEB_ROOT+'/base/class/getClassBySchool?schoolId='+schoolid,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
      	    	var html="";
      	    	if(data.length > 0){
    				for(var i=0;i<data.length;i++)
    				{
						html+="<a id='"+o.contain_id+"class_"+data[i].id+"' href='javascript:void(0)'>"+data[i].name+"</a>";  
						o.classSpan.html(html);
						$("#"+o.contain_id+"class_span a").on("click",{id:this},o.chooseClass);						
    				}      	    		
      	    	}
      	    	o.classListLoaded = true;
			}
		});    	
    };
    
    o.chooseClass=function(a){
		var _id = $(a.target).attr("id").replace(o.contain_id+"school_","");
		$("#"+o.contain_id+"class_span a").removeClass("selected7");
		$(a.target).addClass("selected7");
		$("#"+o.contain_id+"classHide").val(_id);
		o.classListTitle.html($(a.target).html());
		$("#"+$("#class_div"+o.contain_id).attr("id")+"_p").hide();
    };
    
    o.fillGradeYear=function(schoolid){
    	o.resetGradeYear();
		$.ajax({
			url : WEB_ROOT+'/WebMsComm/getGradeYear?schoolId='+schoolid,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
      	    	var html="";
      	    	if(data.length > 0){
    				for(var i=0;i<data.length;i++)
    				{
						html+="<a id='"+o.contain_id+"gradeYear_"+data[i]+"' href='javascript:void(0)'>"+data[i]+"</a>";  
						o.gradeYearSpan.html(html);
						$("#"+o.contain_id+"gradeYearHide_span a").on("click",{id:this},o.chooseGradeYear);						
    				}      	    		
      	    	}
      	    	o.gradeYearLoaded = true;
			}
		});     	
    };
    
    o.chooseGradeYear=function(a){
		var _id = $(a.target).attr("id").replace(o.contain_id+"school_","");
		$("#"+o.contain_id+"gradeYearHide_span a").removeClass("selected7");
		$(a.target).addClass("selected7");
		$("#"+o.contain_id+"gradeYearHide").val(_id);
		o.gradeYearTitle.html($(a.target).html());
		$("#"+$("#gradeYearHide_div"+o.contain_id).attr("id")+"_p").hide();
    };
    
    o.fillExamPaper=function(schoolid,provinceId,cityId,countryId){
    	o.resetExampaper();
    	examPaper = "&areaId="+provinceId+"&cityId="+cityId+"&countryId="+countryId;
		$.ajax({
			url : WEB_ROOT+'/base/class/getExampaper?schoolId='+schoolid+examPaper,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
      	    	var html="";
      	    	if(data.length > 0){
    				for(var i=0;i<data.length;i++)
    				{
						html+="<a id='"+o.contain_id+"examPaper_"+data[i].id+"' href='javascript:void(0)'>"+data[i].name+"</a>";  
						o.examPaperSpan.html(html);
						$("#"+o.contain_id+"examPaper_span a").on("click",{id:this},o.chooseExamPaper);						
    				}      	    		
      	    	}
      	    	o.examPaperLoaded = true;
			}
		});    	
    };
    
    o.chooseExamPaper=function(a){
		var _id = $(a.target).attr("id").replace(o.contain_id+"examPaper_","");
		$("#"+o.contain_id+"examPaper_span a").removeClass("selected7");
		$(a.target).addClass("selected7");
		$("#"+o.contain_id+"examPaperHide").val(_id);
		o.examPaperTitle.html($(a.target).html());
		$("#"+$("#examPaper_div"+o.contain_id).attr("id")+"_p").hide();
    };
    
	o.provinceClick = function(a){
		if(o.provinceAlreadyShowed) {
			var span = $("#"+$("#sampleformdiv"+o.contain_id).attr("id")+"_p"); 
			if (span.is(":visible")){
				span.hide();
			}else{
				span.show();
			}
			return;
		}
		if (o.provinceLoaded){
			$("#sampleformdiv"+o.contain_id).jqpopup_open(a);
			o.provinceAlreadyShowed = true;
		}
	};

	o.cityClick = function(a){
		if(o.cityAlreadyShowed) {
			var span = $("#"+$("#city_div"+o.contain_id).attr("id")+"_p"); 
			if (span.is(":visible")){
				span.hide();
			}else{
				span.show();
			}
			return;
		}
		if (o.cityLoaded){
			$("#city_div"+o.contain_id).jqpopup_open(a);
			o.cityAlreadyShowed = true;
		}		
	};
	
	o.countryClick = function(a){
		if(o.countryAlreadyShowed) {
			var span = $("#"+$("#country_div"+o.contain_id).attr("id")+"_p"); 
			if (span.is(":visible")){
				span.hide();
			}else{
				span.show();
			}
			return;
		}
		if (o.countryLoaded){
			$("#country_div"+o.contain_id).jqpopup_open(a);
			o.countryAlreadyShowed = true;
		}		
	};
	
	o.schoolClick = function(a){
		if(o.schoolAlreadyShowed) {
			var span = $("#"+$("#school_div"+o.contain_id).attr("id")+"_p"); 
			if (span.is(":visible")){
				span.hide();
			}else{
				span.show();
			}
			return;
		}
		if (o.schoolLoaded){
			$("#school_div"+o.contain_id).jqpopup_open(a);
			o.schoolAlreadyShowed = true;
		}		
	};
	
	o.gradeYearClick = function(a){
		if(o.schoolAlreadyShowed) {
			var span = $("#"+$("#gradeYearHide_div"+o.contain_id).attr("id")+"_p"); 
			if (span.is(":visible")){
				span.hide();
			}else{
				span.show();
			}
			return;
		}
		if (o.gradeYearLoaded){
			$("#gradeYearHide_div"+o.contain_id).jqpopup_open(a);
			o.schoolAlreadyShowed = true;
		}		
	};	
	
	o.classClick = function(a){
		if(o.classListAlreadyShowed) {
			var span = $("#"+$("#class_div"+o.contain_id).attr("id")+"_p"); 
			if (span.is(":visible")){
				span.hide();
			}else{
				span.show();
			}
			return;
		}
		if (o.classListLoaded){
			$("#class_div"+o.contain_id).jqpopup_open(a);
			o.classListAlreadyShowed = true;
		}		
	};
	
	o.examPaperClick = function(a){
		if(o.examPaperAlreadyShowed) {
			var span = $("#"+$("#examPaper_div"+o.contain_id).attr("id")+"_p"); 
			if (span.is(":visible")){
				span.hide();
			}else{
				span.show();
			}
			return;
		}
		if (o.examPaperLoaded){
			$("#examPaper_div"+o.contain_id).jqpopup_open(a);
			o.examPaperAlreadyShowed = true;
		}		
	};	
	
	o.loadData = function() {
/*		o.gradeYear.hide();
		o.classList.hide();
		o.examPaper.hide();
		o.cardList.hide();
		o.queryBtn.hide();*/
		
		o.provinceTitle.html(o.initProvince);
		o.cityTitle.html(o.initCity);
		o.countryTitle.html(o.initCountry);
		o.schoolTitle.html(o.initSchool);
		o.gradeYearTitle.html(o.initGradeYear);
		o.classListTitle.html(o.initClass);
		o.examPaperTitle.html(o.initExamPaper);
		
		o.province.on("click",function(){
			o.provinceClick(this.id);
		});
		o.city.on("click",function(){
			o.cityClick(this.id);
		});
		o.country.on("click",function(){
			o.countryClick(this.id);
		});
		o.school.on("click",function(){
			o.schoolClick(this.id);
		});
		o.gradeYear.on("click",function(){
			o.gradeYearClick(this.id);
		});			
		o.classList.on("click",function(){
			o.classClick(this.id);
		});
		o.examPaper.on("click",function(){
			o.examPaperClick(this.id);
		});
		
		o.fillProvinces();
    };		


	
    o.setContainId=function(serialId){
		o.contain_id=serialId;
	};
	o.getProvinceId=function(){
		return o.provinceHide.val();
	};
	
	o.getCityId=function(){
		return $("#"+o.contain_id+"cityHide").val();
	};
	
	o.getCountryId=function(){
		return $("#"+o.contain_id+"countryHide").val();
	};
	
	o.getSchoolId=function(){
		return $("#"+o.contain_id+"schoolHide").val();
	};
	
	o.getClassId=function(){
		return $("#"+o.contain_id+"classHide").val();
	};
	
	o.getPaperId=function(){
		return $("#"+o.contain_id+"examPaperHide").val();
	};

	o.getGradeYear=function(){
		return $("#"+o.contain_id+"gradeYearHide").val();
	};	
	
	o.getStudentCards=function(){
		return $("#"+o.contain_id+"liStudentCards").val();
	};
	
	o.getProvinceName=function(){
		return o.provinceTitle.html();
	};
	
	o.getCityName=function(){
		return o.cityTitle.text();
	};
	
	o.getCountryName=function(){
		return o.countryTitle.text();
	};
	
	o.getSchoolName=function(){
		return o.schoolTitle.text();
	};
	
	o.getClassName=function(){
		return o.classListTitle.text();
	};
	
	o.getPaperName=function(){
		return o.examPaperTitle.text();
	};	
	
	o.showExamPaper=function(){
		o.examPaper.show();
	};
	
	o.showStudentCards=function(){
		o.cardList.show();
	};
	
	o.showClass=function(){
		o.classList.show();
	};
	
	o.showGradeYear=function(){
		o.gradeYear.show();
	};

/*	o.showQueryBtn=function(){
		o.queryBtn.show();
	};*/
	
	o.hideExamPaper=function(){
		o.examPaper.hide();
	};
	
	o.hideStudentCards=function(){
		o.cardList.hide();
	};
	
	o.hideClass=function(){
		o.classList.hide();
	};
	
	o.hideGradeYear=function(){
		o.gradeYear.hide();
	};

/*	o.hideQueryBtn=function(){
		o.queryBtn.hide();
	};*/
	
/*	o.setQueryBtnHandle=function(type,data,fn){
		if (o.queryBtn.is(":visible")){
			o.queryBtn.on(type,data,fn);
		}
	};*/
	return o;
};