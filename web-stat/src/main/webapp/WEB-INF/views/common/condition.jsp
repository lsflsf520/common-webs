<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<style>
.ct_form{
	padding:0;
	margin:0;
	float:left;
}
.ct_form li{
	margin-bottom:15px;
	width:100%;
	float:left;
}
.ct_form li span{
	margin-right:25px;
	float:left;
}
.ct_form li span p{
	margin:0 15px 0 0;
	display: inline;
	padding:0;
	float:left;
}
.ct_form li span input{
	margin:0 5px 0 0;
	float:left;
}
.ct_form li span p input{
	margin:5px 5px 0 0;
	float:left;
}
.ct_form li span label{
	float:left;
}
.ct_form li span i{
	width: 70px;
	float: left;
	font-style: normal;
}
</style>
<c:if test="${condList != null && !empty condList }">
      <form action='${pageData.url == null || fn:startsWith(pageData.url,"http://") ? "" : base}${pageData.url == null || fn:startsWith(pageData.url,"/") ? "" : "/"}${pageData.url}' method="post">
      <ul class="ct_form">
       <c:forEach items="${condList }" var="cond">
         <li>
         <c:choose>
           <c:when test="${cond.group }">
             <c:forEach items="${cond.condList }" var="cond">
                 <%@ include file="_cond.jsp"%>
            </c:forEach>
           </c:when>
           
           <c:otherwise>
               <%@ include file="_cond.jsp"%>
           </c:otherwise>
         </c:choose>
         </li>
       </c:forEach>
         <li><input type="submit" value="查 询" /></li>
      </ul>
      </form>
    </c:if>