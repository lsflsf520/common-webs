var Grid = function() {
	var a = false;
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
				treeReader : {
					level_field : "extraAttributes.level",
					parent_id_field : "extraAttributes.parent",
					leaf_field : "extraAttributes.isLeaf",
					expanded_field : "extraAttributes.expanded",
					loaded : "extraAttributes.loaded",
					icon_field : "extraAttributes.icon"
				},
				autowidth : true,
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
			a = true
		},
		initAjax : function(b) {
			if (b == undefined) {
				b = $("body")
			}
			$('table[data-grid="table"],table[data-grid="items"]', b).each(
					function() {
						Grid.initGrid($(this))
					})
		},
		initGrid : function(X, F) {
			if (!a) {
				Grid.initGridDefault()
			}
			var ak = $(X);
			if (ak.hasClass("ui-jqgrid-btable")) {
				return
			}
			if (ak.attr("id") == undefined) {
				ak.attr("id", "grid_" + new Date().getTime())
			}
			if (F == undefined && ak.data("gridOptions") == undefined) {
				alert("Grid options undefined: class=" + ak.attr("class"));
				return
			}
			var x = $.extend(true, {}, ak.data("gridOptions"), F);
			var d = ak.attr("data-grid");
			var I = null;
			var ac = ak.attr("id") + "-context-menu-container";
			var Y = null;
			var f = (d == "items" ? false : true);
			var J = (d == "items" ? false : true);
			var M = $
					.extend(
							true,
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
								cmTemplate : {
									sortable : d == "items" ? false : true
								},
								viewsortcols : d == "items" ? [ true,
										"vertical", false ] : [ true,
										"vertical", true ],
								altRows : d == "items" ? false : true,
								hoverrows : d == "items" ? false : true,
								pgbuttons : d == "items" ? false : true,
								pginput : d == "items" ? false : true,
								rowList : d == "items" ? [] : [20, 50,
										100, 200],
								inlineNav : {
									add : x.editurl || d == "items" ? true
											: false,
									edit : x.editurl || d == "items" ? true
											: false,
									del : x.delurl || d == "items" ? true
											: false,
									restoreAfterSelect : d == "items" ? false
											: true,
									addParams : {
										addRowParams : {
											extraparam : {},
											restoreAfterError : false,
											beforeSaveRow : function(c) {
												if (M.beforeInlineSaveRow) {
													M.beforeInlineSaveRow.call(
															ak, c)
												}
											},
											aftersavefunc : function(ax, ay) {
												if (M.editurl == "clientArray") {
													ak.jqGrid("resetSelection");
													if (M.afterInlineSaveRow) {
														M.afterInlineSaveRow
																.call(ak, ax)
													}
													setTimeout(
															function() {
																$("#" + I)
																		.find(
																				".ui-pg-div span.ui-icon-plus")
																		.click()
															}, 200);
													return
												}
												var aw = jQuery
														.parseJSON(ay.responseText);
												if (aw.type == "success"
														|| aw.type == "warning") {
													Global.notify(aw.type,
															aw.message);
													var c = aw.userdata.id;
													ak.find("#" + ax).attr(
															"id", c);
													ak.jqGrid("resetSelection");
													ak
															.jqGrid(
																	"setSelection",
																	c);
													if (M.afterInlineSaveRow) {
														M.afterInlineSaveRow
																.call(ak, ax)
													}
													setTimeout(
															function() {
																$("#" + I)
																		.find(
																				".ui-pg-div span.ui-icon-plus")
																		.click()
															}, 200)
												} else {
													if (aw.type == "failure"
															|| aw.type == "error") {
														Global.notify("error",
																aw.message)
													} else {
														Global
																.notify(
																		"error",
																		"数据处理异常，请联系管理员")
													}
												}
											},
											errorfunc : function(aw, ax) {
												var c = jQuery
														.parseJSON(ax.responseText);
												Global.notify("error",
														c.message)
											}
										}
									},
									editParams : {
										restoreAfterError : false,
										beforeSaveRow : function(c) {
											if (M.beforeInlineSaveRow) {
												M.beforeInlineSaveRow.call(ak,
														c)
											}
										},
										oneditfunc : function(az) {
											var aw = ak.jqGrid("getGridParam",
													"iCol");
											var c = ak.jqGrid("getGridParam",
													"colModel")[aw];
											var ay = ak.find("tr#" + az);
											var aA = ay.find("> td:eq(" + aw
													+ ")");
											var ax = aA
													.find("input:visible:first");
											if (ax.size() > 0
													&& ax.attr("readonly") == undefined) {
												setTimeout(function() {
													ax.focus()
												}, 200)
											} else {
												ay
														.find(
																"input:visible:enabled:first")
														.focus()
											}
										},
										aftersavefunc : function(aw, az) {
											var ay = true;
											if (M.editurl != "clientArray") {
												var c = jQuery
														.parseJSON(az.responseText);
												if (c.type == "success"
														|| c.type == "warning") {
													Global.notify(c.type,
															c.message)
												} else {
													if (c.type == "failure"
															|| c.type == "error") {
														Global.notify("error",
																c.message);
														ay = false
													} else {
														Global
																.notify(
																		"error",
																		"数据处理异常，请联系管理员");
														ay = false
													}
												}
											}
											if (ay) {
												if (M.afterInlineSaveRow) {
													M.afterInlineSaveRow.call(
															ak, aw)
												}
												if (M.editurl != "clientArray") {
													var ax = ak
															.find(
																	"tr.jqgrow[id='"
																			+ aw
																			+ "']")
															.next("tr");
													if (ax.size() > 0) {
														var aA = ax.attr("id");
														ak
																.jqGrid("resetSelection");
														ak.jqGrid(
																"setSelection",
																aA);
														setTimeout(
																function() {
																	$("#" + I)
																			.find(
																					".ui-pg-div span.ui-icon-pencil")
																			.click()
																}, 200)
													}
												}
											}
										},
										errorfunc : function(aw, ax) {
											var c = jQuery
													.parseJSON(ax.responseText);
											Global.notify("error", c.message)
										}
									}
								},
								filterToolbar : J,
								multiselect : f,
								contextMenu : true,
								columnChooser : true,
								exportExcelLocal : true,
								loadBeforeSend : function() {
								},
								subGridBeforeExpand : function() {
									var c = ak.closest(".ui-jqgrid-bdiv");
									c.css({
										height : "auto"
									})
								},
								beforeProcessing : function(aw) {
									if (aw && aw.content) {
										var c = 1000;
										$
												.each(
														aw.content,
														function(ax, ay) {
															if (ay.extraAttributes
																	&& ay.extraAttributes.dirtyRow) {
																ay.id = -(c++)
															}
														});
										if (aw.totalElements >= (2147473647 - 10000)) {
											ak.jqGrid("setGridParam", {
												recordtext : "{0} - {1}\u3000"
											})
										}
									}
								},
								loadComplete : function(ax) {
									ak.jqGrid("showAddEditButtons");
									if (ax.total == undefined
											&& ax.totalElements == undefined) {
										alert("表格数据格式不正确");
										return
									}
									if (ax && ax.content) {
										$.each(ax.content, function(ay, az) {
											ak.setRowData(az.id, {
												_arrayIndex : ay
											})
										});
										if (ax.totalElements >= (2147473647 - 10000)) {
											ak
													.closest(".ui-jqgrid")
													.find(
															".ui-pg-table td[id^='last_']")
													.addClass(
															"ui-state-disabled");
											ak
													.closest(".ui-jqgrid")
													.find(
															".ui-pg-table .ui-pg-input")
													.each(
															function() {
																$(this)
																		.parent()
																		.html(
																				$(this))
															})
										}
									}
									if (d == "items"
											&& M.inlineNav.add != false) {
										for (var aw = 1; aw <= 3; aw++) {
											ak.addRowData(-aw, {})
										}
									}
									if (M.footerLocalDataColumn) {
										$.each(M.footerLocalDataColumn,
												function(az, aB) {
													var aA = ak.jqGrid(
															"sumColumn", aB);
													var ay = [];
													ay[aB] = aA;
													ak.footerData("set", ay)
												})
									}
									if (ak.attr("data-selected")) {
										ak.jqGrid("setSelection", ak
												.attr("data-selected"), false)
									}
									var c = x.userLoadComplete;
									if (c) {
										c.call(ak, ax)
									}
								},
								beforeSelectRow : function(ax) {
									if (M.inlineNav.restoreAfterSelect == false) {
										var aw = ak.jqGrid("getGridParam",
												"selrow");
										var c = ak.find("tr#" + aw).attr(
												"editable");
										if (aw && aw != ax && c == "1") {
											$("#" + I)
													.find(
															".ui-pg-div span.ui-icon-disk")
													.click();
											return false
										}
									}
									return true
								},
								onSelectRow : function(ax, c, aw) {
									ak.find("tr.jqgrow").attr("tabindex", -1);
									ak.find("tr.jqgrow[id='" + ax + "']").attr(
											"tabindex", 0);
									if (d == "items") {
										$("#" + I)
												.find(
														".ui-pg-div span.ui-icon-pencil")
												.click()
									}
								},
								onCellSelect : function(aw, c) {
									ak.jqGrid("setGridParam", {
										iCol : c
									})
								},
								ondblClickRow : function(ay, aA, aw, az) {
									var c = $("#" + I).find("i.fa-edit")
											.parent("a");
									if (c.size() > 0) {
										c.click()
									} else {
										if (d != "items") {
											var ax = $("#" + I)
													.find(
															".ui-pg-div span.ui-icon-pencil");
											if (ax.size() > 0) {
												ax.click()
											} else {
												$("#" + I).find(
														"i.fa-credit-card")
														.parent("a").click()
											}
										}
									}
									az.stopPropagation()
								}
							}, x);
			ak.jqGrid(M);
				
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
		},
		initRecursiveSubGrid : function(f, c, e, h) {
			var b = $("<table data-grid='table' class='ui-jqgrid-subgrid'/>")
					.appendTo($("#" + f));
			var d = b.closest("table.ui-jqgrid-btable").data("gridOptions");
			d.url = Util.AddOrReplaceUrlParameter(d.url, "search['EQ_" + e
					+ "']", c);
			d.inlineNav = $.extend(true, {
				addParams : {
					addRowParams : {
						extraparam : {}
					}
				}
			}, d.inlineNav);
			d.inlineNav.addParams.addRowParams.extraparam[e] = c;
			d.parent = e;
			if (h) {
				d.postData = {}
			}
			b.data("gridOptions", d);
			Grid.initGrid(b);
			var g = $("#" + f).parent().closest(
					".ui-jqgrid-btable:not(.ui-jqgrid-subgrid)");
			if (d.gridDnD && g.hasClass("ui-jqgrid-dndtable")) {
				$("#" + f).find(".ui-icon-arrow-4:first").click()
			}
		},
		initSubGrid : function(e, d, c) {
			var b = $("<table data-grid='table' class='ui-jqgrid-subgrid'/>")
					.appendTo($("#" + e));
			b.data("gridOptions", c);
			Grid.initGrid(b)
		}
	}
}();