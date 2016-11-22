<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<style>
.ct_table table tr th, .ct_table table tr td{
	line-height:35px;
	padding:0 20px;
	border:1px solid #333;
	text-align: center;
}

</style>
<c:if test="${pageData.tableData.colTitleList != null && !empty pageData.tableData.colTitleList}">
       <div class="ct_table">
	     <table>
	      <thead>
	       <tr>
	        <c:forEach items="${pageData.tableData.colTitleList }" var="title">
	         <th>${pageData.dataDictMap[title] == null ? title : pageData.dataDictMap[title] }</th>
	        </c:forEach>
	       </tr>
	      </thead>
	      <tbody id="dataBody">
	        <c:forEach items="${pageData.primDbDataList }" var="lineData">
	          <tr>
	            <c:forEach items="${pageData.tableData.colTitleList }" var="tl">
	              <td>${lineData[tl] }</td>
	            </c:forEach>
	          </tr>
	        </c:forEach>
	      </tbody>
	     </table>
	   </div>
	   <div class="holder"></div>  
  <script>
    if($("div.holder") && $("#dataBody") && $("#dataBody").children().length > 0){
      $("div.holder").jPages({  
        containerID : "dataBody",  
        previous : "上一页",  
        next : "下一页",  
        perPage : ${pageData.tableData.pageNum},  
        delay : 100  
      });  
     }
  </script>
</c:if>