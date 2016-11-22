<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!--top-form start-->
   <div class="top-form">
   	<!--top-form-province start-->
   	<div class="top-form-province"  id="${serialId}AreaSchoolGroup">
       	<a href="javascript:void(0)"  id="${serialId}area">
           	<span></span>
               <i class="arrow-bottom"></i>
        </a>
        <a href="javascript:void(0)" id="${serialId}city">
        	<span></span>
            <i class="arrow-bottom"></i>
        </a>
        <a href="javascript:void(0)" id="${serialId}country">
        	<span></span>
            <i class="arrow-bottom"></i>
        </a>           
        <a href="javascript:void(0)" id="${serialId}school">
        	<span></span>
            <i class="arrow-bottom"></i>
        </a>
        <a href="javascript:void(0)" id="${serialId}gradeYear">
        	<span></span>
            <i class="arrow-bottom"></i>
        </a>
        <a href="javascript:void(0)" id="${serialId}tbclass">
        	<span></span>
            <i class="arrow-bottom"></i>
        </a>	
	   	<a href="javascript:void(0)"  id="${serialId}examPaper">
	      	<span></span>
	          <i class="arrow-bottom"></i>
	    </a>
	    
    </div> 
    <!--top-form-province end-->
    <!--top-form-search start-->
<%--     <div class="top-form-search">
     <button id="${serialId}queryNormalBtn"><img src="${commonResDomain}${base}/images/search-icon.png" alt="查询" /></button>
     
    </div> --%>
    <!--top-form-search end-->
   </div>
   <div class="top-form">
	<div class="top-form-province">
       		学生卡号：<input type="text"  id="${serialId}liStudentCards" name="textfield" type="text" title="请在此输入学生卡号，多个卡号请以英文,分隔" maxlength="180"/>
	</div>   
   </div>
   
   <!--top-form end-->
    <div id="sampleformdiv${serialId}" style="display: none">
		<span id="${serialId}data_span"></span>
		<input type="hidden" id="${serialId}provinceHide" value="" />
	</div>
    <div id="city_div${serialId}" style="display: none">
		<span id="${serialId}city_span"></span>
		<input type="hidden" id="${serialId}cityHide" value="" />
	</div>
    <div id="country_div${serialId}" style="display: none">
		<span id="${serialId}country_span"></span>
		<input type="hidden" id="${serialId}countryHide" value="" />
	</div>
    <div id="school_div${serialId}" style="display: none">
		<span id="${serialId}school_span"></span>
		<input type="hidden" id="${serialId}schoolHide" value="" />
	</div>
    <div id="gradeYearHide_div${serialId}" style="display: none">
		<span id="${serialId}gradeYearHide_span"></span>
		<input type="hidden" id="${serialId}gradeYearHide" value="" />
	</div>
    <div id="class_div${serialId}" style="display: none">
		<span id="${serialId}class_span"></span>
		<input type="hidden" id="${serialId}classHide" value="" />
	</div>
    <div id="examPaper_div${serialId}" style="display: none">
		<span id="${serialId}examPaper_span"></span>
		<input type="hidden" id="${serialId}examPaperHide" value="" />
	</div>	
<script type="text/javascript" src="${projectResDomain}${base}/assets/scripts/area_school_v20150603V004.js"></script>
