<%@ page language="java" trimDirectiveWhitespaces="true"
	contentType="text/html; charset=utf-8"%>
<script src="${projectResDomain}${base}/js/plugins/page_js.js"  type="text/javascript"></script>
<!--page start-->
    <div class="page">
    	<ul>
    	<c:if test="${pageList.pageCount>1}">
            <li>
            	<span><i>${pageList.pageNo}</i>/${pageList.pageCount}</span>
            	<a href="javascript:prePage('2' ,'${pageList.url}')">首页</a>
                <a
                <c:if test="${pageList.pageNo==1}">
                 href="javascript:void(0)"
                 </c:if>
                <c:if test="${pageList.pageNo>1}">
                 href="javascript:prePage('${pageList.pageNo}' ,'${pageList.url}')"
                 </c:if>
                 >
                <img src="${commonResDomain}${base}/images/pre.png" width="8" height="13" alt="上一页" />
                                                      上一页
                </a>
                <a 
                <c:if test="${pageList.pageCount==pageList.pageNo}">                	
                	href="javascript:void(0)"
                 </c:if>
                <c:if test="${pageList.pageCount>pageList.pageNo}">                	
                	href="javascript:nextPage(${pageList.pageNo},${pageList.pageCount},'${pageList.url}')"
                 </c:if>                 
                >
                	下一页
                <img src="${commonResDomain}${base}/images/next.png" width="8" height="13" alt="下一页" />
                </a>
                <input type="text" id="_page_no_id" style="width:80px"/>
                <a href="javascript:goRanPage( '<c:out value='${pageList.url}' />' , '<c:out value='${pageList.pageCount}' />' )">跳页</a>
            </li>
           </c:if>
    	<c:if test="${pageList.pageCount<=1}">
    	<li>
    	共一页
    	</li>
    	</c:if>           
        </ul>
    </div>
    <!--page end-->
