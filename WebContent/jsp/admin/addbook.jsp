<%@ page language="java" contentType="text/html" errorPage="error"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/jsp/jspf/bundle.jspf"%>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<title><fmt:message key="title.addbook-admin" /></title>
</head>
<body>
	<h1 align="right" style="padding-right: 1.3em; ">
		<fmt:message key="title.addbook-admin" />
	</h1>
	<h2 align="left" style="padding-left: 1.7em;">
		<fmt:message key="message.user.hello" />
		${sessionScope.user.login} ${sessionScope.user.name}
		${sessionScope.user.surname}
	</h2>
	<c:if test="${not empty message }">
		<h5 align="center" >
			<fmt:message key="${message }" />
		</h5>
	</c:if>
	<form method="POST" action="controller">
		<h1 align="center">
			<fmt:message key="book.name" />
			<br /> <input type="text" name="name" value="" /><br />
		</h1>
		<h1 align="center">
			<fmt:message key="book.author.name" />
			<br /> <input type="text" name="authorName" value="" /><br />
		</h1>
		<h1 align="center">
			<fmt:message key="book.author.surname" />
			<br /> <input type="text" name="authorSurname" value="" /><br />
		</h1>
		<h1 align="center">
			<fmt:message key="book.genre" />
			<br /> <select name="genre">
				<option selected disabled>
					<fmt:message key="book.genre" /></option>
				<c:forEach var="genre" items="${genres}">
					<option value="${genre}">${genre}</option>
				</c:forEach>
			</select><br />
		</h1>
		<h1 align="center">
			<fmt:message key="book.description" />
			<br />
			<textarea name="description" cols="50" rows="4"></textarea>
		</h1>

		<h1 align="center">
			<fmt:message key="book.impritYear" />
			<br /> <input type="text" name="impritYear" value="" /><br />
		</h1>
		<h1 align="center">
			<fmt:message key="book.amount" />
			<br /> <input type="text" name="amount" value="" /><br />
		</h1>
		<h1 align="center">
			<fmt:message key="book.picturePath" />
			<br /> <input type="text" name="picturePath"
				value="${book.picturePath }" /><br />
		</h1>
		<h1 align="center">
			<fmt:message key="book.onlyReadingHall" />
			<br /> <input type="radio" name="onlyReadingHall" value="yes" />
			<fmt:message key="yes" />
			<input type="radio" name="onlyReadingHall" value="no" />
			<fmt:message key="no" />

		</h1>
		<h1 align="center">
			<input id="buttonLogOut" type="submit" name="book.save"
				value="<fmt:message key="book.save" />" /> <input type="hidden"
				name="command" value="add_book" />
		</h1>

	</form>
	<h1 align="center">
		<%@include file="/jsp/jspf/toindex.jspf"%>
	</h1>
</body>
</html>