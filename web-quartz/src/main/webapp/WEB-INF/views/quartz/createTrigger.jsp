<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建触发器</title>
<script language="JavaScript">
	function selectTriggerType() {
		var chooseTriggerType = $(".triggerType").val();
		if (chooseTriggerType == "simpl") {
			$("#divSipTrigger").show();
			$("#divCronTrigger").hide();
		} else {
			$("#divCronTrigger").show();
			$("#divSipTrigger").hide();
		}
	}
</script>


</head>
<body>

<div class="col-sm-10">
		<form name="triggerForm" method="post" action="${base}/trigger/create">
			<h3>新建触发器 用于 任务名: ${jobDetail.name} in 任务组: ${jobDetail.group}</h3>
			<input type="hidden" name="jobName" value="${jobDetail.name}">
				<input type="hidden" name="jobGroup" value="${jobDetail.group}">
					<select name="triggerType" class="triggerType"
					onclick="selectTriggerType()">
						<option name="triggerType" value="simpl">简单触发器</option>
						<option name="triggerType" value="cron">复杂触发器</option>
				</select>
					<table>
						<tr>
							<td>触发器组</td>
							<td><input type="text" name="triggerGroup" value="" /></td>
							<td><i>触发器组</i></td>
						</tr>
						<tr>
							<td>触发器名称</td>
							<td><input type="text" name="triggerName" value="" /></td>
							<td><i>触发器名称</i></td>
						</tr>
						<tr>
							<td>备注</td>
							<td><textarea rows="5" cols="50" name="description"></textarea></td>
							<td>描述信息</td>
						</tr>
						<tr>
							<td>开始时间</td>
							<td><input type="text"
								style="width: 150px; padding-left: 15px;" name="startTime"
								id="startTime"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								value="${startTime }"></td>
							<td><i>不填无开始时间</i></td>
						</tr>
						<tr>
							<td>停止时间</td>
							<td><input type="text"
								style="width: 150px; padding-left: 15px;" name="stopTime"
								id="stopTime"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								value="${stopTime }"></td>
							<td><i>不填无结束时间</i></td>
						</tr>
						<tbody id="divSipTrigger" name="divSipTrigger">
							<tr>
								<td>次数</td>
								<td><input type="text" name="repeatCount" value="" /></td>
								<td><i>执行次数</i></td>
							</tr>
							<tr>
								<td>间隔时间</td>
								<td><input type="text" name="repeatInterval" value="" /></td>
								<td><i>每次执行时间间隔,单位(秒)</i></td>
							</tr>
						</tbody>
						<tbody style='display: none' id="divCronTrigger"
							name="divCronTrigger">

							<tr>
								<td>Cron表达式</td>
								<td><input type="text" name="cronExpression" value="" /></td>
								<td><i></i></td>
							</tr>
						</tbody>
					</table> <input type="submit" value="提交" />
		</form>
		<table>
			<tbody>
				<tr>
					<th>字段名</th>
					<th>是否必需</th>
					<th>允许的值</th>
					<th>允许的特殊字符</th>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td>秒（seconds）</td>
					<td>Y</td>
					<td>0-59</td>
					<td>, - * /</td>
				</tr>
				<tr>
					<td>分（minutes）</td>
					<td>Y</td>
					<td>0-59</td>
					<td>, - * /</td>
				</tr>
				<tr>
					<td>时（hours）</td>
					<td>Y</td>
					<td>0-23</td>
					<td>, - * /</td>
				</tr>
				<tr>
					<td>天（day of month）</td>
					<td>Y</td>
					<td>1-31</td>
					<td>, - * /L W C</td>
				</tr>
				<tr>
					<td>月（month）</td>
					<td>Y</td>
					<td>0-11 或者 JAN-DEC</td>
					<td>, - * /</td>
				</tr>
				<tr>
					<td>星期（day of week）</td>
					<td>Y</td>
					<td>1-7 或者 SUN-SAT</td>
					<td>, - * /?L C#</td>
				</tr>
				<tr>
					<td>年（year）</td>
					<td>N</td>
					<td>1970-2099或者不写</td>
					<td>, - * /</td>
				</tr>
			</tbody>
		</table>
		<p>这时就可以明白前面所说的六个或者七个子表达式或者字段是什么意思了，从第一位到最后一位分别表示秒 分 时 天 月 星期 年
			最后一位年可以不写。</p>
		<p>对于以上特殊字符，可以这么理解：</p>
		<p>“，”表示and</p>
		<p>“-”表示一个区间段，即开始到结束</p>
		<p>“*”表示全选，即用汉语中的“每”或者英文中的every/each/per</p>
		<p>“/”表示一个区间段的时长，例如放在第一位“/10”则表示每10秒</p>
		<p>“L”表示最后,即Last</p>
		<p>“W”表示weekday，即工作日也就是周一到周五</p>
		<p>“C”表示canlendar,即日历，例如“1C”在星期位上就是包括日历上的星期日</p>
		<p>“#”表示序列，如“#2”表示第二</p>
		<p>下面在举例中详细说明：</p>
		<table border="1" cellpadding="3" cellspacing="0">
			<tbody>
				<tr>
					<th>表达式</th>
					<th>表达的时间</th>
				</tr>
			</tbody>
			<tbody>
				<tr>
					<td>0 0 12 * * ?</td>
					<td>每天中午12点</td>
				</tr>
				<tr>
					<td>0 15 10 ? * *</td>
					<td>每天早上10点15分</td>
				</tr>
				<tr>
					<td>0 15 10 * * ?</td>
					<td>每天早上10点15分</td>
				</tr>
				<tr>
					<td>0 15 10 * * ? *</td>
					<td>每天早上10点15分</td>
				</tr>
				<tr>
					<td>0 15 10 * * ? 2005</td>
					<td>2005年的每天早上10点15分</td>
				</tr>
				<tr>
					<td>0 * 14 * * ?</td>
					<td>每天下午14点钟开始到14点59分结束这么一个时间段</td>
				</tr>
				<tr>
					<td>0 0/5 14 * * ?</td>
					<td>每天下午14点到14点55分之间每5分钟触发一次</td>
				</tr>
				<tr>
					<td>0 0/5 14,18 * * ?</td>
					<td>每天下午14点到14点55分 和18点到18点55分之间 &nbsp;每5分钟触发一次</td>
				</tr>
				<tr>
					<td>0 0-5 14 * * ?</td>
					<td>每天下午14点开始到14点05结束</td>
				</tr>
				<tr>
					<td>0 10,44 14 ? 3 WED</td>
					<td>每年三月份的每个周三下午14点10分和14点44各一次</td>
				</tr>
				<tr>
					<td>0 15 10 ? * MON-FRI</td>
					<td>每个工作日的10点15分</td>
				</tr>
				<tr>
					<td>0 15 10 15 * ?</td>
					<td>每个月15号的上午10点15分</td>
				</tr>
				<tr>
					<td>0 15 10 L * ?</td>
					<td>每个月最后一天的10点15分</td>
				</tr>
				<tr>
					<td>0 15 10 ? * 6L</td>
					<td>每个月最后一个周五的10点15分</td>
				</tr>
				<tr>
					<td>0 15 10 ? * 6L 2002-2005</td>
					<td>2002年到2005年每个月最后一个周五的10点15分</td>
				</tr>
				<tr>
					<td>0 15 10 ? * 6#3</td>
					<td>每个月的第三个周五的10点15分</td>
				</tr>
				<tr>
					<td>0 0 12 1/5 * ?</td>
					<td>每个月从第一天开始每隔5天中午12点触发一次</td>
				</tr>
				<tr>
					<td>0 11 11 11 11 ?</td>
					<td>每年11月11号11点11分</td>
				</tr>
			</tbody>
		</table>
	</div>

	</div>

	 </div>
</body>
</html>
