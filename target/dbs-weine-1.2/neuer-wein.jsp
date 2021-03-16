<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="net.froihofer.dbs.weine.NeuerWein" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${contextPath}/css/main.css" />
    <title>Weine</title>
  </head>
  <body>
    <h1>Neuer Wein</h1>
    <% if (request.getAttribute(NeuerWein.ERROR_MSG_PARAM) != null) { %>
      <p style="color: red"><%=request.getAttribute(NeuerWein.ERROR_MSG_PARAM)%></p>
    <% } %>
    <% if (request.getAttribute(NeuerWein.SUCCESS_MSG_PARAM) != null) { %>
      <p style="color: blue"><%=request.getAttribute(NeuerWein.SUCCESS_MSG_PARAM)%></p>
    <% } %>
    <form method="POST" action="${contextPath}/NeuerWein" style="display: inline-block">
      <table>
        <tr><td>Name:</td><td><input type="text" name="name" value="${param.name}"/></td></tr>
        <tr><td>Farbe:</td><td><input type="text" name="farbe" value="${param.farbe}"/></td></tr>
        <tr><td>Jahrgang:</td><td><input type="text" name="jahrgang" value="${param.jahrgang}"/></td></tr>
        <tr><td>Weingut:</td><td><input type="text" name="weingut" value="${param.weingut}"/></td></tr>
        <tr><td style="padding-top: 5px">JPA verwenden?</td><td style="padding-top: 5px"><input type="checkbox" name="useJpa" <%= request.getParameter("useJpa") != null ? "checked=\"true\"" : ""%>/></td></tr>
      </table>
      
      <div style="float: right; margin-top: 10px">
      <a href="${contextPath}/weine.jsp" class="btn btn-secondary">Abbrechen</a>
      <button class="btn btn-primary" style="margin-left: 10px">Speichern</button>
      </div>
    </form>
    <% if (request.getAttribute(NeuerWein.WEINE) != null) { %>
      <br/>
      <h2>Weine vom Weingut "${param.weingut}"</h2>
      <table class="data table-striped">
        <tr><th>Name</th><th>Farbe</th><th>Jahrgang</th></tr>
        <c:forEach var="wein" items="${WEINE_VON_ERZEUGER}">
          <tr><td>${wein.name}</td><td>${wein.farbe}</td><td>${wein.jahrgang}</td></tr>
        </c:forEach>
      </table>
    <%} %>
  </body>
</html>
