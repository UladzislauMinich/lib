<%@ page language="java" contentType="text/html" errorPage="error"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/jsp/jspf/bundle.jspf"%>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<title><fmt:message key="title.order-admin" /></title>
</head>
<body>
	<h1 align="right" style="padding-right: 1.3em; ">
		<fmt:message key="title.order-admin" />
	</h1>
	<h2 align="left" style="padding-left: 1.7em;">
		<fmt:message key="message.user.hello" />
		${sessionScope.user.login} ${sessionScope.user.name}
		${sessionScope.user.surname}<br/><br/>
		<%@include file="/jsp/jspf/logout.jspf"%>
	</h2>
	<c:if test="${not empty message }">
		<h4>
			<fmt:message key="${message }" />
		</h4>
	</c:if>
	<table id="table1"  width="100%" style=" padding-left: 1.9em; padding-right:1.9em;  ">
		<tr>
			<th><fmt:message key="order.id" /></th>
			<th><fmt:message key="order.date" /></th>
			<th><fmt:message key="order.user.login" /></th>
			<th><fmt:message key="action" /></th>
			<th width="150"><fmt:message key="book.name" /></th>
			<th ><fmt:message key="book.author" /></th>
			<th width="100"><fmt:message key="book.onlyReadingHall" /></th>
		</tr>
		<c:forEach var="order" items="${sessionScope.orders}">
			<tr>
				<td align="center">${order.idOrder }</td>
				<td align="center">${order.date }</td>
				<td align="center">${order.user.login }</td>
				<td align="center"><c:choose>
						<c:when test="${order.status == 1}">

							<form action="controller" method="post">
								<input name="command" type="hidden" value="complete_order" /> <input
									name="order.idOrder" type="hidden" value="${order.idOrder}" />
								<h2 align="center">
									<input id="button1" type="submit" name="complete_order"
										value="<fmt:message key="complete-order" />" />
								</h2>
							</form>
							<c:if test="${order.user.login == sessionScope.user.login }">
								<form action="controller" method="post">
									<input name="command" type="hidden" value="delete_order" /> <input
										name="order.idOrder" type="hidden" value="${order.idOrder}" />
									<input id="button1" type="submit"
										value="<fmt:message key="cansel-order" />" />
								</form>
							</c:if>
						</c:when>
						<c:otherwise>
							<fmt:message key="order-completed" />
						</c:otherwise>
					</c:choose></td>
				<td colspan="3"><%@include file="/jsp/jspf/order-info.jspf"%>
				</td>
			</tr>

		</c:forEach>
	</table>
	
	<h1 align="center">
		<%@include file="/jsp/jspf/toindex.jspf"%>
	</h1>
</body>
</html>