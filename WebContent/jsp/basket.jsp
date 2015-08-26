<%@ page language="java" contentType="text/html" errorPage="error"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ctg" uri="customtags"%>
<%@include file="/jsp/jspf/bundle.jspf"%>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<title><fmt:message key="title.book" /></title>
</head>
<body>
	<h1 align="right" style="padding-right: 1.3em;">
		<fmt:message key="title.book" />
	</h1>
	<h2 align="left" style="padding-left: 1.7em;">
		<fmt:message key="message.user.hello" />
		${sessionScope.user.login} ${sessionScope.user.name}
		${sessionScope.user.surname}<br /> <br />
		<%@include file="/jsp/jspf/logout.jspf"%>
	</h2>
	<c:if test="${not empty message }">
		<h5>
			<fmt:message key="${message }" />
		</h5>
	</c:if>
	<table id="table1" width="100%"
		style="padding-left: 1.9em; padding-right: 1.9em;">
		<tr>
			<th><fmt:message key="book.name" /></th>
			<th><fmt:message key="book.author" /></th>
			<th><fmt:message key="book.impritYear" /></th>
			<th><fmt:message key="book.onlyReadingHall" /></th>
			<th><fmt:message key="action" /></th>
		</tr>
		<c:forEach var="book" items="${sessionScope.basketBooks}">
			<tr>

				<td align="center">${book.name}</td>
				<td align="center">${book.authorName} ${book.authorSurname}</td>
				<td align="center">${book.impritYear}</td>
				<td align="center"><c:choose>
						<c:when test="${book.onlyReadingHall == true}">
							<fmt:message key="yes" />
						</c:when>
						<c:otherwise>
							<fmt:message key="no" />
						</c:otherwise>
					</c:choose></td>

				<td align="center">
					<form>
						<h1>
							<input id="buttonLogOut" type="submit" name="book.delete"
								value="<fmt:message key="book.delete" />" /> <input
								type="hidden" name="command" value="delete_book_from_basket" /><input
								type="hidden" name="idBook" value="${book.idBook}" /> <input
								type="hidden" name="basketBooks"
								value="${sessionScope.basketBooks}" /> <input type="hidden"
								name="amount" value="${sessionScope.amount}" />
						</h1>
					</form>

				</td>
			</tr>
		</c:forEach>
	</table>
	<c:choose>
		<c:when test="${not empty sessionScope.basketBooks}">
			<form>
				<h1 align="center">
					<input id="buttonLogOut" type="submit" name="make_order"
						value="<fmt:message key="make_order" />" /> <input type="hidden"
						name="command" value="make_order" /><input type="hidden"
						name="basketBooks" value="${sessionScope.basketBooks}" /> <input
						type="hidden" name="user" value="${sessionScope.user}" />
				</h1>
			</form>
		</c:when>
		<c:otherwise>
			<h1 align="center">
				<fmt:message key="basket-is-empty" />
			</h1>
		</c:otherwise>
	</c:choose>


	<h1 align="center">
		<%@include file="/jsp/jspf/toindex.jspf"%>
	</h1>
</body>
</html>