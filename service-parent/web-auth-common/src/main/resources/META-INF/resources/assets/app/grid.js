var lastSel; //记录用户上一次选择的行
var gridTable;
var finalGridOptions;
var Grid = function() {
	return {
		initGridDefault : function() {
			$.extend($.ui.multiselect, {
				locale : {
					addAll : "全部添加",
					removeAll : "全部移除",
					itemsCount : "已选择项目列表"
				}
			});
			$.extend($.jgrid.ajaxOptions, {
				dataType : "json"
			});
			$.extend($.jgrid.defaults, {
				datatype : "json",
				loadui : false,
				loadonce : false,
				filterToolbar : {},
				ignoreCase : true,
				prmNames : {
					npage : "npage"
				},
				jsonReader : {
					repeatitems : false,
					root : "content",
					total : "totalPages",
					records : "totalElements"
				},
//				treeReader : {
//					level_field : "extraAttributes.level",
//					parent_id_field : "extraAttributes.parent",
//					leaf_field : "extraAttributes.isLeaf",
//					expanded_field : "extraAttributes.expanded",
//					loaded : "extraAttributes.loaded",
//					icon_field : "extraAttributes.icon"
//				},
				autowidth : true,
				setGridWidth : true,
				rowNum : 20,
				page : 1,
				altclass : "ui-jqgrid-evennumber",
				height : "stretch",
				viewsortcols : [ true, "vertical", true ],
				mtype : "GET",
				viewrecords : true,
				rownumbers : true,
				toppager : true,
				recordpos : "left",
				gridview : true,
				altRows : true,
				sortable : false,
				multiboxonly : true,
				multiselect : true,
				multiSort : false,
				forceFit : false,
				shrinkToFit : true,
				sortorder : "desc",
				sortname : "createdDate",
				ajaxSelectOptions : {
					cache : true
				},
				loadError : function(d, e, b, c) {
					Global.notify("error", "表格数据加载处理失败,请尝试刷新或联系管理员!")
				},
				subGridOptions : {
					reloadOnExpand : false
				}
			});
		},
		initAjax : function(b) {
			if (b == undefined) {
				b = $("body");
			}
			$('table[data-grid="table"],table[data-grid="items"]', b).each(
					function() {
						Grid.initGrid($(this), getGridOptions)
					})
		},
		initGrid : function(tableElem, extFunc) {
			gridTable = $(tableElem); //初始化表格
			if (gridTable.hasClass("ui-jqgrid-btable")) { //如果表格已经被初始化过了(有这个样式，就说明已经初始化过了)，就直接返回
				return;
			}
			
			Grid.initGridDefault(); //初始化默认配置
			
			if (gridTable.attr("id") == undefined) {
				gridTable.attr("id", "grid_" + new Date().getTime());
			}
			
			var userGridOptions = gridTable.data("gridOptions");
			
			if (extFunc == undefined && userGridOptions == undefined) {
				Global.notify("function getGridOptions() has not defined");
				return;
			}
			var gridOptions = $.extend(true, {}, userGridOptions, extFunc()); //x
			var dataVal = gridTable.attr("data-grid");
			finalGridOptions = $
					.extend(true,
							{},
							$.jgrid.defaults,
							{
								formatter : {
									integer : {
										defaultValue : ""
									},
									number : {
										decimalSeparator : ".",
										thousandsSeparator : ",",
										decimalPlaces : 2,
										defaultValue : ""
									},
									currency : {
										decimalSeparator : ".",
										thousandsSeparator : ",",
										decimalPlaces : 2,
										defaultValue : ""
									}
								},
								viewsortcols :  [ true, "vertical", true ],
								altRows : true,
								hoverrows : true,
								pgbuttons : true,
								pginput : true,
								rowList : [20, 50, 100, 200],
								filterToolbar : false, //是否展示过滤框，貌似没用
								multiselect : false,
								loadComplete : function(responseJson) {
//									if (responseJson == undefined || (responseJson.total == undefined
//											&& responseJson.totalElements == undefined)) {
//										alert("数据返回格式不正确");
//										return;
//									}
								},
								onSelectRow : function(rowid,status){
									if(lastSel){
										gridTable.restoreRow(lastSel);
									}
								},
								ondblClickRow : function(rowId, iRow, iCol, event){
									if(rowId){
										if(lastSel && rowId != lastSel){
											gridTable.restoreRow(lastSel);
										}
										gridTable.editRow(rowId, true);
										
										/*
										gridTable.jqGrid("editRow", rowId, {
											keys : true,
											url : $.jgrid.defaults.editurl,
											mtype : "POST",
											restoreAfterError: true,
											successfunc : function(responseJson){ //操作成功，即服务器端返回成功，将触发此方法
//												console.log("success:" + JSON.stringify(responseJson));
												Global.notify(responseJson.message);
											},
											errorfunc : function(rowid, res){ //编辑失败，即服务器端返回错误，则将触发此方法
												console.log("error:" + rowid + "," + JSON.stringify(res));
												Global.notify(res.statusText);
											},
											oneditfunc : function(rowid){ //某行上发生了双击事件，将触发此方法
//												console.log("oneditfunc:" + rowid );
//												Global.notify("onedit",
//														rowid + "处于编辑状态");
											}
										});*/
										lastSel = rowId;
									}
									
									event.stopPropagation();
								}
							}, gridOptions);
			   
			   gridTable.jqGrid(finalGridOptions);
			   
			   //下边做一些扩展功能
	     }, //initGrid end
	     addRow : function(rowId){
	    	 if(!getRowDataModel){
	    		 Global.notify("function getRowDataModel() has not defined");
	    		 return;
	    	 }
	    	 var datarow = getRowDataModel(rowId);
	    	 var addId = datarow.id;
	    	 if(!addId){
	    		 addId = "dataId_" + new Date().getTime();
	    		 datarow.id = addId;
	    	 }
	    	 
	    	 var su ;
	    	 if(rowId){
	    	  	su = gridTable.jqGrid('addRowData', addId, datarow, "after", rowId);
	    	 }else{
	    	   	su = gridTable.jqGrid('addRowData', addId, datarow);
	    	 }
	    	 if (su){
	    	   	$("#jqg_dataTbl_" + addId).click();
	    	   	gridTable.jqGrid("editRow", addId); 
	    	 }else{
	    	   	Global.notify("Can not update");
	    	 }
	     },
	     delRow : function(rowId, operName, needReload){
//	    	var id = gridTable.jqGrid('getGridParam', 'selrow');
//	 		if(!id){
//	 			id = $(this).attr("data-id"); //如果没有选择的行，则选择当前元素上绑定的data-id
//	 			if(!id){
//	 				Global.notify("请先勾选要删除的行！");
//	 				return;
//	 			}
//	 		}
	 		
	 		var result = confirm("确定要"+(operName ? operName : "删除")+"吗？");
	 		if(!result){
	 			return;
	 		}
	 		var ret = gridTable.jqGrid('getRowData', rowId);
	 	    $.ajax({
	 	    	 // url: WEB_ROOT + "/sys/menu/doDelete?id=" + rowId + "&version=" + ret.version,
	 	    	  url : finalGridOptions.delurl + "?id=" + rowId + "&version=" + ret.version,
	 	    	  type: "GET",
	 	    	  success:function(data){
	 	    		  if(!data || "success" != data.type){
	 	    			 Global.notify(data ? data.message : "操作失败！");
	 	    			 return;
	 	    		  }
	 	    		  
	 	    		  var su = gridTable.jqGrid('delRowData', rowId);
	 	    		  
	 	    		  var msg = data.message ? data.message : "操作成功！";
	 	    		  if(!su){
	 	    			  msg = "Allready deleted or not in list";
	 	    		  }
	 	    		  Global.notify(msg);
	 	    		  if(needReload){
	 	    			 location.reload();
	 	    		  }
	 	    	  }
	 	    });
	     },
	     saveRow : function(rowId){
	    	gridTable.jqGrid("saveRow", rowId, function(data){
	    		if(data.responseJSON.type == "success"){
	    			Global.notify(data.responseJSON.message);
	    			location.reload();
	    		}else{
	    			Global.notify(data.responseJSON.message);
	    		}
	    	}); 
	     },
	     refreshWidth : function() {
				$("table.ui-jqgrid-btable:visible").each(function() {
					var c = $(this);
					var d = c.jqGrid("getGridParam", "width");
					var b = c.closest("div.ui-jqgrid").parent("div").width();
					if (d != b) {
						c.jqGrid("setGridWidth", b);
						var e = $(this).jqGrid("getGridParam", "groupHeader");
						if (e) {
							c.jqGrid("destroyGroupHeader");
							c.jqGrid("setGroupHeaders", e)
						}
					}
				})
			}
	 }
	 //return end
}(); //Grid function end
