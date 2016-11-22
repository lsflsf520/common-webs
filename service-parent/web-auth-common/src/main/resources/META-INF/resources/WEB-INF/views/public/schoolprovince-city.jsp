<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="../common/app-ver.jsp"%>
<input type="hidden" name="province" value="" />
<input type="hidden" name="city" value="" />
<input type="hidden" name="school" value="" />
<input type="hidden" name="gradeYear" value="" />
<input type="hidden" name="class" value="" />
<div id="province_div" style="display: none">
	<!--<i style="position:absolute; z-index:0; top:-10px;" class="arrow-top"></i>-->
	<span id="province_span"></span>
</div>
<div id="city_div" style="display: none">
	<!--<i style="position:absolute; z-index:0; top:-10px;" class="arrow-top"></i>-->
	<span id="city_span"></span>
</div>
<div id="school_div" style="display: none">
	<!--<i style="position:absolute; z-index:0; top:-10px;" class="arrow-top"></i>-->
	<span id="school_span"></span>
</div>
<div id="gradeYear_div" style="display: none">
	<!--<i style="position:absolute; z-index:0; top:-10px;" class="arrow-top"></i>-->
	<span id="gradeYear_span"></span>
</div>
<div id="class_div" style="display: none">
	<!--<i style="position:absolute; z-index:0; top:-10px;" class="arrow-top"></i>-->
	<span id="class_span"></span>
</div>
<input type="hidden" name='uu_id_prefix' value="${uu_id_prefix}"/>
<input type="hidden" name='isGetPaper' value="${isGetPaper}"/>
 <script src="assets/scripts/schoolprovince-city.js?_=${buildVersion}"></script>
