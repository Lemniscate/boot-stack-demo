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
        <%--&lt;%&ndash; Include the application sources, depending on if we're deployed &ndash;%&gt;--%>
        <%--<c:choose>--%>
            <%--<c:when test="${DEPLOYED}">--%>
                <%--<script src="${_baseUrl}/static/scripts/libs.js"></script>--%>
                <%--<script src="${_baseUrl}/static/scripts/all.js"></script> --%>
                <%--<script src="${_baseUrl}/static/templates/templates.min.js"></script>--%>
            <%--</c:when>--%>
            <%--<c:otherwise>--%>
                <%--<c:forEach items="${JS_RESOURCES}" var="js">--%>
                    <%--<script src="${_baseUrl}/static/src/${js}"></script>--%>
                <%--</c:forEach>--%>
                <%--<script src="${_baseUrl}/static/templates/templates.js"></script>--%>
            <%--</c:otherwise>--%>
        <%--</c:choose>        --%>
        <c:forEach items="${JS_RESOURCES}" var="js">
            <script src="${_baseUrl}/src/${js}"></script>
        </c:forEach>
    </jsp:attribute>
	<jsp:body>
    <div ui-view></div>
	</jsp:body>
</tags:template>