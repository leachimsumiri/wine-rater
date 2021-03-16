<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<html>
  <head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${contextPath}/css/main.css" />
    <title>Weine</title>
  </head>
  <body>
    <h1>Weine</h1>
    <form method="GET" action="">
      <p>Suche nach Teil vom Namen: <input name="wName" type="text" value="${param.wName}"/>,
        und Jahrgang bis: <input name="wJahrgang" type="text" value="${param.wJahrgang}"/>
        <button class="btn btn-primary">Suchen</button>
      </p>
    </form>
      <a href="${contextPath}/index.jsp">Zur Startseite</a> - <a href="${contextPath}/neuer-wein.jsp">Neuen Wein anlegen</a>
        <hr /><br/>
        <sql:setDataSource dataSource="jdbc/WeineDB" />
        <c:if test="${empty param.wName}">
          <sql:query var="weine" sql="SELECT WeinId, Name, Farbe, Jahrgang, Weingut, Empfohlen FROM Weine" />
        </c:if>
        <c:if test="${not empty param.wName and empty param.wJahrgang}">
          <sql:query var="weine" sql="SELECT WeinId, Name, Farbe, Jahrgang, Weingut, Empfohlen FROM Weine WHERE name like ?">
            <sql:param value="%${param.wName}%" />
          </sql:query>
        </c:if>
        <c:if test="${not empty param.wName and not empty param.wJahrgang}">
          <sql:query var="weine" sql="SELECT WeinId, Name, Farbe, Jahrgang, Weingut, Empfohlen FROM Weine WHERE name like ? AND jahrgang <= ?">
            <sql:param value="%${param.wName}%" />
            <sql:param value="${param.wJahrgang}" />
          </sql:query>
        </c:if>
        <c:if test="${weine.rowCount == 0}">
          <p style="color: red;">Keine Einträge gefunden!</p>
        </c:if>
        <c:if test="${weine.rowCount > 0}">
          <table class="data table-striped">
            <tr><th>Name</th><th>Farbe</th><th>Jahrgang</th><th>Weingut</th><th>Empfohlen</th></tr>
            <c:forEach var="wein" items="${weine.rows}">
              <tr><td>${wein.name}</td><td>${wein.farbe}</td><td>${wein.jahrgang}</td><td>${wein.weingut}</td><td>${wein.empfohlen}</td></tr>
            </c:forEach>
          </table>
        </c:if>
</body>
</html>
