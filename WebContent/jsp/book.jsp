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
	<c:choose>
		<c:when test="${empty sessionScope.user}">
			<h3 style="padding-left: 2.2em;">
				<fmt:message key="message.guest.hello" />
			</h3>
			<form action="controller" method="post">
				<h1  style="padding-left: 1.2em;">
					<input id="button24" type="submit" name="toSignUp"
						value="<fmt:message key="toSignUp" />" /> <input type="hidden"
						name="command" value="to_signup" />
				</h1>
			</form>
		</c:when>
		<c:otherwise>
			<table width="100%">
				<tr>
					<td>
						<h2 align="left" style="padding-left: 1.7em;">
							<fmt:message key="message.user.hello" />
							${sessionScope.user.login} ${sessionScope.user.name}
							${sessionScope.user.surname}
						</h2>
					</td>

					<td align="right" style="padding-right: 1.9em;">
						<form action="controller" method="post">
							<input type="hidden" name="basketBooks"
								value="${sessionScope.basketBooks}" /><input type="hidden"
								name="command" value="to_basket" /> <input id="buttonLogOut"
								type="submit"
								value="<ctg:basket amount="${sessionScope.amount}" locale="${sessionScope.locale}"/>" />

						</form>
						<%@include file="/jsp/jspf/logout.jspf"%>
					</td>

				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	<c:if test="${not empty message }">
		<h5 style="padding-left: 1.9em;">
			<fmt:message key="${message }" />
			<c:if test="${not empty text }"> ${text} <fmt:message
					key="message.searching_book_success_end" />
			</c:if>
		</h5>
	</c:if>
	<table id="table1" width="100%"  style="padding-left: 1.9em; padding-right:1.9em ">
		<tr>
			<th><fmt:message key="book.name" /></th>
			<th><fmt:message key="book.author" /></th>
			<th><fmt:message key="book.genre" /></th>
			<th><fmt:message key="book.amount" /></th>
			<th><fmt:message key="action" /></th>
		</tr>
		<c:forEach var="book" items="${books}">
			<tr>

				<td align="center">${book.name}</td>
				<td align="center">${book.authorName} ${book.authorSurname}</td>
				<td align="center">${book.genre}</td>
				<td align="center">${book.amount}</td>
				<td align="center">
					<form action="controller" method="post">
						<input id="buttonLogOut" type="submit"
							value="<fmt:message key="book.add-toorder" />" /> <input
							type="hidden" name="idBook" value="${book.idBook}" /><input
							type="hidden" name="command" value="add_book_to_basket" /> <input
							type="hidden" name="user" value="${sessionScope.user}" /> <input
							type="hidden" name="books" value="${books}" /> <input
							type="hidden" name="basketBooks"
							value="${sessionScope.basketBooks}" /> <input type="hidden"
							name="amount" value="${sessionScope.amount}" />
					</form>
					<form action="controller" method="post">
						<input id="buttonLogOut" type="submit"
							value="<fmt:message key="book.read-more" />" /> <input
							type="hidden" name="idBook" value="${book.idBook}" /> <input
							type="hidden" name="command" value="show_book_information" />
					</form> <c:if test="${sessionScope.user.adminFlag ==1}">
						<form action="controller" method="post">
							<input id="buttonLogOut" type="submit"
								value="<fmt:message key="book.edit" />" /> <input type="hidden"
								name="idBook" value="${book.idBook}" /><input type="hidden"
								name="command" value="to_edit" />
						</form>
						<form>
							<input id="buttonLogOut" type="submit"
								value="<fmt:message key="book.delete" />" /> <input
								type="hidden" name="command" value="delete_book" /><input
								type="hidden" name="idBook" value="${book.idBook}" />
						</form>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	<h1 align="center">
		<%@include file="/jsp/jspf/toindex.jspf"%>
	</h1>
</body>
</html>