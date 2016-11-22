<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<div id="${serialId}AreaSchoolGroup">
	<ul>
		<li>
            <span class="form">
               	<select name="area" id="${serialId}area">
                   	<option value="-1">请选择省份</option>
                </select>
                <select name="city" id="${serialId}city">
                   	<option value="-1">请选择城市</option>
                </select>
                <select name="county" id="${serialId}country">
                   	<option value="-1">请选择区县</option>
                </select>
	            <select name="school" id="${serialId}school">
	            	<option value="-1">选择学校</option>
	            </select>
				<select id="${serialId}tbclass" name="tbclass" >
					<option value="-1">选择班级</option>
				</select>
            </span>
        </li>
        <li id="${serialId}liExampaper">
            <span class="form"> 
				<select id="${serialId}tbExamPaper" name="tbExamPaper" >
					<option value="-1">选择试卷</option>
				</select>
			</span>
		</li>
		<li id="${serialId}liStudentCards">	
			<span class="word" >学生卡号</span>
			<span class="form">
				<input id="${serialId}studentCards" name="textfield" type="text" size="100" title="输入多个学生卡号，请以英文,分隔" maxlength="180">
            </span>
        </li>       
	</ul>
</div>
<script type="text/javascript" src="${projectResDomain}${base}/assets/scripts/area_school_v20150603001.js"></script>
