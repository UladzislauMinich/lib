<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/jsp/jspf/bundle.jspf"%>
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<title><fmt:message key="title.registration" /></title>
</head>
<body>
	<h1 align="right" style="padding-right: 1.3em;">
		<fmt:message key="title.registration" />
	</h1>
	<%@include file="/jsp/jspf/choice_lang.jspf"%>
	<form method="POST" action="controller">
		<h1 align="center">
			<fmt:message key="enterLogin" />
			<br /> <input type="text" name="login"
				pattern="[\w]*[.]*[\w]+@[a-z]+\.[a-z]{2,5}" required /><br />
		</h1>
		<h4 align="center">
			<fmt:message key="signup.login.info" />
			<br />
		</h4>
		<h1 align="center">
			<fmt:message key="password" />
			<br /> <input type="password" name="password" pattern="[\w]{3,20}"
				required /><br />
		</h1>
		<h4 align="center">
			<fmt:message key="signup.password.info" />
			<br />
		</h4>
		<h1 align="center">
			<fmt:message key="name" />
			<br /> <input type="text" name="name"
				pattern="[A-Za-zА-Яа-я\w]{2,20}" /><br />
		</h1>
		<h4 align="center">
			<fmt:message key="signup.name-surname.info" />
			<br />
		</h4>
		<h1 align="center">
			<fmt:message key="surname" />
			<br /> <input type="text" name="surname"
				pattern="[A-Za-zА-Яа-я\w]{2,20}" /><br />
		</h1>
		<h4 align="center">
			<fmt:message key="signup.name-surname.info" />
			<br />
		</h4>

		<c:if test="${not empty message }">
			<h5 align="center">
				<fmt:message key="${message }" />
			</h5>
		</c:if>
		<h4 align="center">
			<fmt:message key="signup.info" />
		</h4>

		<input type="hidden" name="command" value="signup" />
		<h1 align="center">
			<input id="button24" type="submit" name="signup"
				value="<fmt:message key="signup" />" />
				<input type="hidden"
							name="amount" value="0" />
		</h1>

	</form>
	<h1 align="center">
		<%@include file="/jsp/jspf/toindex.jspf"%>
	</h1>
</body>
</html>