<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%
    pageContext.setAttribute("base", request.getContextPath());
%>

<script type="text/javascript">
var WEB_ROOT = "${base}";
var PASSPORT_DOMAIN = "${passportDomain}";
var PROJECT_RES_DOMAIN = "${projectResDomain}";
var COMMON_RES_DOMAIN = "${commonResDomain}";
</script>