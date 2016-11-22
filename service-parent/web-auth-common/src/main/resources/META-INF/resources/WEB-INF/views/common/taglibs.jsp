<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>

<%
    pageContext.setAttribute("base", request.getContextPath());
%>

<script type="text/javascript">
 var PROJECT_RES_DOMAIN = "${projectResDomain}";
 var COMMON_RES_DOMAIN = "${commonResDomain}";
 var BASE_DATA_DOMAIN = "${baseDataDomain}";
</script>
