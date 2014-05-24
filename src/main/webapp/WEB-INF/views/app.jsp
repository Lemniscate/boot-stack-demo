<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tags:template pageTitle="Application" ngApp="app">
    <jsp:attribute name="head">
        <c:forEach items="${CSS_RESOURCES}" var="css">
            <link rel="stylesheet" href="${_baseUrl}/src/${css}"/>
        </c:forEach>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <%-- Include the application sources, depending on if we're deployed --%>
        <c:choose>
            <c:when test="${DEPLOYED}">
                <script src="${_baseUrl}/ar/scripts/libs.js"></script>
                <script src="${_baseUrl}/ar/scripts/all.js"></script>
                <script src="${_baseUrl}/ar/templates/templates.min.js"></script>
            </c:when>
            <c:otherwise>
                <c:forEach items="${JS_RESOURCES}" var="js">
                    <script src="${_baseUrl}/src/${js}"></script>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
	<jsp:body>
    <div ui-view></div>
	</jsp:body>
</tags:template>