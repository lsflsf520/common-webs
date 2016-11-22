<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<html>
  <head>
    <title>${pageData.chart.title }-饼图</title>
  </head>
  
  <body>
    <div style="float:left; width:100%;">
      <%@include file="../common/condition.jsp" %>
    </div>
    <!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div id="chartDiv" style="width: 600px;height:400px; float:left;"></div>
    
       <div id="tbDiv" style="float:left; width:100%;">
        <%@include file="../common/table.jsp" %>
	  </div>
    
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('${pageData.chart.divId}'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '${pageData.chart.title}',
                subtext: '${pageData.chart.subTitle}',
                x: 'center', 
                y: 'center'
            },
            tooltip: {},
            legend: {
                data:[
                  <c:forEach items="${pageData.chart.legendData}" var="data">
                    '${data}',
                  </c:forEach>      
                ]
            },
            series: [
             <c:forEach items="${pageData.chart.series}" var="sery">
              {
                name: '${sery.name}',
                type: '${sery.type}',
                data: [
                   <c:forEach items="${sery.dataList}" var="data">
                     {name:'${data.name}', value:${data.value}},
                   </c:forEach>
                ]
              },
             </c:forEach>
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
  </body>
</html>