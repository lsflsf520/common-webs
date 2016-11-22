<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建模板-Quartz系统</title>
 <style type="text/css">
fieldset input {
	width: 100px;
}

form label 	{
		padding:0px 10px;
		width: 100px;
		float: left;
}

fieldset {
	width: 699px;
}
.buttonFwd {
	width: 90px;
}
</style>

<script language="JavaScript">
function doAddParameter() {
	var name = document.getElementById("parameter.name");
	var desc = document.getElementById("parameter.description");
	var required=document.getElementById("parameter.required");

	if (name.value.length > 0) {
		addParameter(name.value, desc.value, required.checked);
		name.value="";
		desc.value="";
		required.value=1;
		required.checked=true;
	} else {
		alert('error.paramname.null');
	}
}

function addParameter(name, desc, required) {
//alert('about to add parameter:' + name + ' ' + desc + ' ' + required);
	
	var parent = document.getElementById("paramterHolder");
	var proto = document.getElementById("proto");
		clone = proto.cloneNode(true);
		clone.setAttribute("id", "notproto");
		clone.getElementsByTagName("input")[0].value = name; //set the value for the dnsServer
		clone.getElementsByTagName("input")[0].name = "paramMap['" + name + "'].name"; 
		
		clone.getElementsByTagName("input")[1].value = desc; //set the value for the dnsServer
		clone.getElementsByTagName("input")[1].name = "paramMap['" + name + "'].description"; 
		
		clone.getElementsByTagName("input")[2].value = required; //set the value for the dnsServer
		clone.getElementsByTagName("input")[2].name = "paramMap['" + name + "'].required"; 

		clone.getElementsByTagName("td")[0].innerHTML = clone.getElementsByTagName("td")[0].innerHTML + ' ' + name; //set the value for the dnsServer
		clone.getElementsByTagName("td")[1].innerHTML = desc; //set the value for the dnsServer
		
		if (required) {
			reqStr='true';
		} else {
			reqStr='false';
		}
		clone.getElementsByTagName("td")[2].innerHTML = reqStr + " " + clone.getElementsByTagName("td")[2].innerHTML; 
	parent.appendChild(clone);

}

function removeParameter(obj) {
	deleteByParent(obj,3); 
}


function deleteByParent(obj, levels) {
	var parent = obj;
	var prev=null;
	
	for (var i = 0; i < levels; i++) {
	 	prev = parent;
	 	parent = 	parent.parentNode;
	}
	
	if (prev)  {
		//alert(parent.tagName + " is deleting an inner " + prev.tagName);
		parent.removeChild(prev);
	}
		
}
</script>
</head>
<body>


<div id="middlebox">
<form name="definitionForm" method="post" action="save.action">
 <input type="hidden" name="definitionName" value="" />
 
    <fieldset>
      <legend>
创建Job模板      </legend>
      
      
		<label for="definitionName">任务id</label>
		<input type="text" id="definitionName" name="definition.name" value=""/> <br/>
	
		<label for="definitionClass">Job的Java类</label>
		<input type="text" id="definitionClass"  name="definition.className" value="" /><br/>
		<label for="defDescription">任务简介</label>
		<textarea id="defDescription" name="definition.description"></textarea><br/>
		
<h3>参数信息:</h3>
<h3>Job参数</h3>

<table border=1>
	<tr>
		<td>参数名       </td>
		<td>备注</td>
		<td>必要项   </td>
        <td>操作	     </td>
	</tr>
<tr>
	<td><input type="hidden" name="definitionName" value=""/><input id="parameter.name" type="text" name="parameter.name"/></td>
	<td><input type="text" id="parameter.description" name="parameter.description"/></td>
	<td><input type="checkbox" value="true" id="parameter.required" name="parameter.required"/>	</td>
	
  <td>
	<input type="button" class="submit" name="cmdAddParameter" value="添加参数" onclick="javascript:doAddParameter()" /></td>
</tr>
<tr>
<td colspan=4>

<table id="paramterHolder"  border=1>
</table>
</td>
</tr>
</table>
<input type="submit" class="buttonFwd" name="saveAction" value="保存"/>
<input type="submit" class="submit" name="cancel" value="取消"/>
<BR>注意：支持Job模板的新建，不支持修改
</fieldset>

</form>
<table  style="display: none">
  <tr id="proto" >
    <td><input type="hidden" name="parameter.name"/>
	<input type="hidden" name="parameter.description"/>
	<input type="hidden" value="true" name="parameter.required"/></td>
    	<td></td>
    	<td><a href="#" onclick="removeParameter(this)">去除</a></td>
    </tr>
</table>
</div>

<div id="footer" ><div class="footer micro">
	<br />
	<br />
</div></div>
</body>
</html>
