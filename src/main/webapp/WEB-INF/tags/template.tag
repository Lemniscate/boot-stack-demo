<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="head" required="false" fragment="true" %>
<%@ attribute name="footer" required="false" fragment="true" %>
<%@ attribute name="pageTitle" required="false" fragment="false" %>
<%@ attribute name="ngApp" required="false" fragment="false" %>

<c:set var="_baseUrl" scope="application" value="${empty pageContext.request.contextPath || pageContext.request.contextPath == '/' ? '' : pageContext.request.contextPath}"/>

<html lang="en" <c:if test="${not empty ngApp}">ng-app="${ngApp}"</c:if>>
<head>
  <title><c:if test="${not empty pageTitle}"><c:out value="${pageTitle}"/> - </c:if>[Site Name Here]</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <link rel="stylesheet" href="${_baseUrl}/static/css/bootstrap.css"/>
  <link rel="stylesheet" href="${_baseUrl}/static/css/bootstrap-theme.css"/>
  <%--<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>--%>
  <script>
      window.globals = window.globals || {};
      window.globals.module = 'app';
      window.globals.baseUrl = '${_baseUrl}';
      // TODO handle deploy change here
      window.globals.jsBaseUrl = '${_baseUrl}/src/src/scripts';
  </script>
  <jsp:invoke fragment="head"/>
</head>
<body>
  <nav class="navbar navbar-default navbar-fixed-top" ng-controller="navController">
    <div class="navbar-header">
      <a class="navbar-brand logo">
          Logo Here
      </a>
      <sec:authorize access="isAuthenticated()">
        <ul class="nav navbar-nav navbar-collapse" collapse="isCollapsed">
          <tags:nav></tags:nav>
        </ul>
        <button type="button" class="navbar-toggle" data-toggle="collapse" ng-click="isCollapsed = !isCollapsed">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
      </sec:authorize>
    </div>
  </nav>
    <jsp:doBody />    
    <script>
      window.baseUrl =  '${_baseUrl}';
      <c:if test="${_csrf != null}">
      window.csrf = {
          token: '${_csrf.token}',
          header: '${_csrf.headerName}'
      };
      </c:if>      
    </script>    
    <jsp:invoke fragment="footer"/>
</body>
</html>