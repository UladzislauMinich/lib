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
<title><fmt:message key="title.login" /></title>
</head>
<body style="">
	<c:if test="${not empty sessionScope.user}">
		<jsp:forward page="/jsp/main.jsp" />
	</c:if>
	<h1 align="right" style="padding-right: 1.3em; ">
		<fmt:message key="title.login" />
	</h1>
	<%@include file="/jsp/jspf/choice_lang.jspf"%>
	<form name="loginForm" method="POST" action="controller">
		<h1 align="left" style="padding-left: 1.7em;">
			<input type="hidden" name="command" value="login" />
			<fmt:message key="login" />
			<br /> <input type="text" name="login" value="" /> <br />
			<fmt:message key="enterPassword" />
			<br /> <input type="password" name="password" value="" /> <br />
		</h1>
		<c:if test="${not empty message }">
			<h5 style="padding-left: 2.1em;">
				<fmt:message key="${message }" />
			</h5>
		</c:if>
		<h1 align="left" style="padding-left: 1.7em;">
			<br /> <input id="button24" type="submit"
				value="<fmt:message key="signin" />" /><br /> <input type="hidden"
				name="amount" value="0" />
		</h1>

	</form>
	<form method="POST" action="controller">
		<h1 align="left" style="padding-left: 1.7em;">
			<input id="button24" type="submit"
				value="<fmt:message key="signup" />" /> <input type="hidden"
				name="command" value="to_signup" />
		</h1>
	</form>
	<form method="POST" action="controller">
		<h1 align="left" align="left" style="padding-left: 1.7em;">
			<input id="button24" type="submit"
				value="<fmt:message key="to_main" />" /> <input type="hidden"
				name="command" value="to_main" />
		</h1>
	</form>
</body>
</html>