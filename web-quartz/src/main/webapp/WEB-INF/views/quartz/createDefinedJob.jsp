<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>



<title>新建任务-Quartz系统</title>

<script language="JavaScript">
	$().ready(function() {
		$("#isAsyn").val("false");
	});

	function saveDefJob() {
		$.ajax({
			url : "${base}/Job/create",
			type : "POST",
			data : $('#formJob').serialize(),
			success : function(data) {
				//var result=jQuery.parseJSON(data);
				showMessage(data);
			}
		});
	}

	function showMessage(data) {
			if (data == null || data == "") {
				swal("成功", "操作成功，成功添加", "success")
				window.location.href = "${base}/Job/jobList";
			} else {
				$.each(data, function(index) {
					sweetAlert("错误", data[index].message, "error");
				});
				
			} 
	}
</script>
</head>
<body>
	
		<div class="col-sm-10">
		<div class="container">
			<h1>创建基于模板的Job</h1>

			<form id="formJob" name="jobDetailForm" method="post"
				action="${base}/Job/create">
				<table>
					<tr>
						<td>任务组</td>
						<td><input type="text" name=groupName
							value="${jobDetail.groupName}" placeholder="只能为英文和数字" /></td>
					</tr>
					<tr>
						<td>任务id</td>
						<td><input type="text" name="name" value="${jobDetail.name}" placeholder="只能为英文和数字" />
							<form:errors path="jobDetailForm.name" cssClass="error" /></td>
					</tr>
					 <tr>
						<td>任务的Java类</td>
						<td><input readonly="readonly" type="text" name="jobClass"
							size="27" value="${jobDefinition.className}" /></td>
					</tr> 
					<tr>
						<td>任务简介</td>
						<td><textarea name="description">${jobDefinition.description} </textarea></td>
					</tr>
					<tr>
						<td>恢复(当调度器发生硬关闭时，如果调度器恢复运行后该Job会重新执行)</td>
						<td><input type="checkbox" name="recoveryRequesting"
							checked="true" /></td>
					</tr>
					<tr>
						<td>任务完成之后是否依然保留到数据库</td>
						<td><input type="checkbox" name="durability" checked="true" /></td>
					</tr>
					<h3>选择Job</h3>
					<h3>Job参数</h3>
					<c:if test="${!empty jobDefinition.parameters}">
						<c:forEach items="${jobDefinition.parameters}" var="paramter">
							<tr>
								<td>参数名</td>
								<td>参数值</td>
								<td>描述</td>
								<td>必要项</td>
							</tr>
							<input type="hidden" name="parameterNames"
								value="${paramter.name}" />
							<tr>
								<td>${paramter.name}</td>
								<td><input type="text" id="${paramter.name}"
									name="parameterValues" /></td>
								<td>${paramter.description}</td>
								<td>${paramter.required}</td>
							</tr>

						</c:forEach>
					</c:if>


					</form>
				</table>
				<input type="hidden" name="definitionName"
					value="${jobDefinition.name}" /> <input type="button"
					class="submit" name="saveAction" value="保存" onclick="saveDefJob()" />
				<form>
		</div>
	</div>
	</div>


	</div>
</body>
</html>
