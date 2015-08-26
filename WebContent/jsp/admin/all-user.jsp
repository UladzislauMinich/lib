<%@ page language="java" contentType="text/html" errorPage="error"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/jsp/jspf/bundle.jspf"%>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<title><fmt:message key="title.admin.all-user" /></title>
</head>
<body>
	<h1 align="right" style="padding-right: 1.3em;">
		<fmt:message key="title.admin.all-user" />
	</h1>
	<c:if test="${not empty message }">
		<h5 align="center">
			<fmt:message key="${message }" />
		</h5>
	</c:if>
	<table id="table1" width="100%" style=" padding-left: 1.9em; padding-right:1.9em;  ">
		<tr>
			<th><fmt:message key="user.id" /></th>
			<th><fmt:message key="user.login" /></th>
			<th><fmt:message key="user.name" /></th>
			<th><fmt:message key="user.surname" /></th>
			<th><fmt:message key="user.isAdmin" /></th>
			<th><fmt:message key="action" /></th>
		</tr>
		<c:forEach var="user" items="${users}">
			<tr>
				<td align="center">${user.idUser}</td>
				<td align="center">${user.login}</td>
				<td align="center">${user.name}</td>
				<td align="center">${user.surname}</td>
				<td align="center"><c:choose>
						<c:when test="${user.adminFlag == 1}">
							<fmt:message key="yes" />
						</c:when>
						<c:otherwise>
							<fmt:message key="no" />
						</c:otherwise>
					</c:choose></td>
				<td align="center">
					<form action="controller" method="post">
						<h1>
							<input id="buttonLogOut" type="submit" name="book.delete"
								value="<fmt:message key="book.delete" />" /> <input
								type="hidden" name="loginUser" value="${user.login}" /> <input
								type="hidden" name="user.adminFlag" value="${user.adminFlag}" /><input
								type="hidden" name="command" value="delete_user" />
						</h1>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div align="center">
		<%@include file="/jsp/jspf/toindex.jspf"%>
	</div>
</body>
</html>