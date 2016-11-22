function createAreaSchool(containId) {
	var o = new Object();
	o.contain_id=containId;
	o.provincesData = "";
	o.citysData = ""; 
	o.areasData = "";
	o.provinceId = $("#"+o.contain_id+"area").get(0);
	o.cityId = $("#"+o.contain_id+"city").get(0);
	o.countyId =$("#"+o.contain_id+"country").get(0);
	
	o.fillProvinces=function(){
		if (o.provincesData===""){
			$.get(WEB_ROOT+"/base/area/getPrivProvince",{},function (data){
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
						html+=_id+"|"+_name+",";  
						
					}
					o.provincesData = html;
					if (o.provincesData===""){
						Global.notify("没有找到合适的省份，请联系系统管理员或稍后再试。");
						return;
					}
			        var sp = o.provinceId;
			        sp.options.length = 0;

			        var pAs = o.provincesData.split(",");
			        for (var pA in pAs) {
			            var pA_parts = pAs[pA].split("|");
			            if (pA_parts[1]!==""&&pA_parts[0]!=="")  sp.options.add(new Option(pA_parts[1], pA_parts[0]));
			        }

			        if (sp.options.length == 0) {
			            sp.disabled = true;
			            sp.options.add(new Option("", "000000"));
			        }
			        else {
			            sp.disabled = false;
			        }
			        sp.options[0].selected = true;	
			    	o.fillCitys();
					
				}
			});			
		}
	
	};
	
	o.fillCitys=function() {
		if (o.citysData===""){
			$.get(WEB_ROOT+"/base/area/getPrivCity",{"pId":$("#"+o.contain_id+"area").val()},function (data){
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
						html+=_id+"|"+_name+",";
					}
					o.citysData = html;
					if (o.citysData===""){
						Global.notify("没有找到合适的城市，请联系系统管理员或稍后再试。");
						return;			
					}		
			        var sc = o.cityId;
			        sc.options.length = 0;

			        var sp = o.provinceId;
			        var pv = sp.options[sp.selectedIndex].value;

			        var cAs = o.citysData.split(",");
			        for (var cA in cAs) {
			            var cA_parts = cAs[cA].split("|");

			            if (pv.substring(0, 2) == cA_parts[0].substring(0, 2)) {
			                sc.options.add(new Option(cA_parts[1], cA_parts[0]));
			            }
			        }

			        if (sc.options.length == 0) {
			            sc.disabled = true;
			            sc.options.add(new Option("", "000000"));
			        }
			        else {
			            sc.disabled = false;
			        }

			        sc.options[0].selected = true;
			        o.fillAreas();
				}
			});			
		}


    };
    
    o.fillAreas=function () {
        if (o.areasData===""){
    		$.get(WEB_ROOT+"/base/area/getPrivCounty",{"pId":$("#"+o.contain_id+"city").val()},function (data){
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
    					html+=_id+"|"+_name+",";	  
    				}				
    				o.areasData = html;
			        if (o.areasData===""){
						Global.notify("没有找到合适的区县，请联系系统管理员或稍后再试。");
						return;	       	
			        }        
			    	var sa = o.countyId;
			        sa.options.length = 0;

			        var sc = o.cityId;
			        var cv = sc.options[sc.selectedIndex].value;

			        var aAs = o.areasData.split(",");
			        for (var aA in aAs) {
			            var aA_parts = aAs[aA].split("|");

			            if (cv.substring(0, 4) == aA_parts[0].substring(0, 4)) {
			                sa.options.add(new Option(aA_parts[1], aA_parts[0]));
			            }
			        }

			        if (sa.options.length == 0) {
			            sa.disabled = true;
			            sa.options.add(new Option("", "000000"));
			        }
			        else {
			            sa.disabled = false;
			        }

			        sa.options[0].selected = true;
			        o.chgCountry();
			      
			        if("000000" == $("#"+o.contain_id+"country").val()){
			    		var cityId = $("#"+o.contain_id+"city").val();
			        	if(cityId){
			        		o.fillSchools(cityId, 2);
			        	}
			        	$("#"+o.contain_id+"country").hide();
			    	}else{
			    		$("#"+o.contain_id+"country").show();
			    	}    				
    			}
    		});         	
        }
 
    };
    
    o.fillSchools=function(areaId, level){
    	$.ajax({
    		url : WEB_ROOT + "/base/school/getPrivSchool?areaId=" + areaId + "&level=" + level,
    		type: "GET",
      	    success:function(data){
      	    	$("#"+o.contain_id+"school").html("<option value='-1'>请选择学校</option>");
      	    	if(data.length > 0){
      	    		var schoolContent = "";
      	  	    	
      	    		$(data).each(function(){
      	    			schoolContent += "<option value='" +this.id+"'>" + this.name + "</option>";
      	    		});
      	    		
      	    		$(schoolContent).appendTo($("#"+o.contain_id+"school"));
      				if ($("#"+o.contain_id+"tbclass").is(":visible")){ 
      					$("#"+o.contain_id+"tbclass").html("<option value='-1'>请选择班级</option>"); 
      				}
      				if ($("#"+o.contain_id+"liExampaper").is(":visible")){
      					$("#"+o.contain_id+"tbExamPaper").html("<option value='-1'>请选择试卷</option>");
      				}
      				if (data.length==1){
          				$("#"+o.contain_id+"school").get(0).options[1].selected = true;
          				o.chgSchool();     					
      				}
      	    	}
      	    }
    	});
    };
    
    o.chgProvinces=function() {
    	o.citysData = ""; 
    	o.areasData = "";
    	o.fillCitys();
    };
    o.chgCitys=function(sc, sa) {
    	o.areasData = "";
    	o.fillAreas();
    };
    
    o.chgCountry=function () {
    	var countryId = $("#"+o.contain_id+"country").val();
    	if(countryId){
    		o.fillSchools(countryId, 3);
    	}
    };
    
    o.chgSchool=function() {
		var examPaper = "";
		var schoolid = "?schoolId="+$("#"+o.contain_id+"school").val();
		if(schoolid != "-1"){
			if ($("#"+o.contain_id+"tbclass").is(":visible")){ 
				$("#"+o.contain_id+"tbclass").html("<option value='-1'>请选择班级</option>"); 
				$.ajax({
					url : WEB_ROOT+'/base/class/getClassBySchool'+schoolid,
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						if(data !=null && data.length>0){
							$.each(data, function(idx, item) { //循环对象取值
								$("#"+o.contain_id+"tbclass").get(0).options.add(new Option(item.name,
										item.id));
							});
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						Global.notify("获取班级失败，请重登录后重试，错误参数：["+XMLHttpRequest.status+"]["+XMLHttpRequest.readyState+"]["+textStatus+"]");
					}
				});
			}
			if ($("#"+o.contain_id+"liExampaper").is(":visible")){
				examPaper = "&areaId="+$("#"+o.contain_id+"area").val()+"&cityId="+$("#"+o.contain_id+"city").val()+"&countryId="+$("#"+o.contain_id+"country").val();
				$("#"+o.contain_id+"tbExamPaper").html("<option value='-1'>请选择试卷</option>");   
				$.ajax({
					url : WEB_ROOT+'/base/class/getExampaper'+schoolid+examPaper,
					type : 'POST',
					dataType : 'json',
					success : function(data) {
						if ($("#"+o.contain_id+"liExampaper").is(":visible")){
							if(data !=null && data.length>0){
	    						$.each(data, function(idx, item) { //循环对象取值
	    							$("#"+o.contain_id+"tbExamPaper").get(0).options.add(new Option(item.name,
	    									item.id));
	    						});
	    					}    						
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						Global.notify("获取试卷失败，请重登录后重试，错误参数：["+XMLHttpRequest.status+"]["+XMLHttpRequest.readyState+"]["+textStatus+"]");
					}
				});    			
			}			
			
		}

	};		
	
	o.loadData = function() {
		$("#"+o.contain_id+"liExampaper").hide();
		$("#"+o.contain_id+"liStudentCards").hide();
		$("#"+o.contain_id+"tbclass").hide();
		$("#"+o.contain_id+"area").on("change",o.chgProvinces);
		$("#"+o.contain_id+"city").on("change",o.chgCitys);
		$("#"+o.contain_id+"country").on("change",o.chgCountry);
		$("#"+o.contain_id+"school").on("change",o.chgSchool);
		o.provinceId = $("#"+o.contain_id+"area").get(0);
		o.cityId = $("#"+o.contain_id+"city").get(0),
		o.countyId =$("#"+o.contain_id+"country").get(0)			
		o.fillProvinces();
/*		o.fillCitys();
		o.fillAreas();*/
    };		


	
    o.setContainId=function(serialId){
		o.contain_id=serialId;
	};
	o.getProvinceId=function(){
		return $("#"+o.contain_id+"area").val();
	};
	
	o.getCityId=function(){
		return $("#"+o.contain_id+"city").val();
	};
	
	o.getCountryId=function(){
		return $("#"+o.contain_id+"country").val();
	};
	
	o.getSchoolId=function(){
		return $("#"+o.contain_id+"school").val();
	};
	
	o.getClassId=function(){
		return $("#"+o.contain_id+"tbclass").val();
	};
	
	o.getPaperId=function(){
		return $("#"+o.contain_id+"tbExamPaper").val();
	};
	
	o.getStudentCards=function(){
		return $("#"+o.contain_id+"studentCards").val();
	};
	
	o.getProvinceName=function(){
		return $("#"+o.contain_id+"area option:selected").text();
	};
	
	o.getCityName=function(){
		return $("#"+o.contain_id+"city option:selected").text();
	};
	
	o.getCountryName=function(){
		return $("#"+o.contain_id+"country option:selected").text();
	};
	
	o.getSchoolName=function(){
		return $("#"+o.contain_id+"school option:selected").text();
	};
	
	o.getClassName=function(){
		return $("#"+o.contain_id+"tbclass option:selected").text();
	};
	
	o.getPaperName=function(){
		return $("#"+o.contain_id+"tbExamPaper option:selected").text();
	};	
	
	o.showExamPaper=function(){
		$("#"+o.contain_id+"liExampaper").show();
	};
	
	o.showStudentCards=function(){
		$("#"+o.contain_id+"liStudentCards").show();
	};
	
	o.showClass=function(){
		$("#"+o.contain_id+"tbclass").show();
	};

	o.hideExamPaper=function(){
		$("#"+o.contain_id+"liExampaper").hide();
	};
	
	o.hideStudentCards=function(){
		$("#"+o.contain_id+"liStudentCards").hide();
	};
	
	o.hideClass=function(){
		$("#"+o.contain_id+"tbclass").hide();
	};	
	return o;
};