<%@ page language="java" contentType="text/html" errorPage="error"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/jsp/jspf/bundle.jspf"%>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<title><fmt:message key="title.order-user" /></title>
</head>
<body>
	<h1 align="right" style="padding-right: 1.3em;">
		<fmt:message key="title.order-user" />
	</h1>
	<%@include file="/jsp/jspf/choice_lang.jspf"%>
	<h2 align="left" style="padding-left: 1.7em;">
		<fmt:message key="message.user.hello" />
		${sessionScope.user.login}<br /> ${sessionScope.user.name}
		${sessionScope.user.surname}<br /> <br />
		<%@include file="/jsp/jspf/logout.jspf"%>
	</h2>
	<br />
	<c:if test="${not empty message }">
		<h5>
			<fmt:message key="${message }" />
		</h5>
	</c:if>
	<table id="table1" width="100%"
		style="padding-left: 1.9em; padding-right: 1.9em;">
		<tr>
			<th><fmt:message key="order.id" /></th>
			<th><fmt:message key="order.date" /></th>
			<th><fmt:message key="action" /></th>
			<th width="150"><fmt:message key="book.name" /></th>
			<th><fmt:message key="book.author" /></th>
			<th width="100"><fmt:message key="book.onlyReadingHall" /></th>

		</tr>

		<c:forEach var="order" items="${sessionScope.orders}">
			<tr>
				<c:choose>
					<c:when test="${not empty sessionScope.orders}">
						<td align="center">${order.idOrder }</td>
						<td align="center">${order.date }</td>
						<td align="center"><c:choose>
								<c:when test="${order.status == 1}">
									<h2 align="center">
										<form action="controller" method="post">
											<input name="command" type="hidden" value="delete_order" />
											<input name="order.idOrder" type="hidden"
												value="${order.idOrder}" /> <input id="button1"
												type="submit" value="<fmt:message key="cansel-order" />" />
										</form>

									</h2>
								</c:when>
								<c:otherwise>
									<fmt:message key="order-completed" />
								</c:otherwise>
							</c:choose></td>
						<td colspan="3"><%@include file="/jsp/jspf/order-info.jspf"%>
						</td>
					</c:when>
					<c:otherwise>
						<fmt:message key="orders-is-empty" />
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>


	</table>
	<hr />
	<h1 align="center">
		<%@include file="/jsp/jspf/toindex.jspf"%>
	</h1>
</body>
</html>