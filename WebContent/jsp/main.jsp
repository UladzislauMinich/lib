<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ctg" uri="customtags"%>
<%@include file="/jsp/jspf/bundle.jspf"%>

<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<title><fmt:message key="title.main" /></title>
</head>
<body>
	<h1 align="right" style="padding-right: 1.3em;">
		<fmt:message key="title.main" />
	</h1>
	<%@include file="/jsp/jspf/choice_lang.jspf"%>

	<table border="0" width="100%">
		<tr valign="top">
			<td width="120"><c:choose>
					<c:when test="${empty sessionScope.user}">
						<h3 style="padding-left: 2.2em;">
							<fmt:message key="message.guest.hello" />
						</h3>
						<form action="controller" method="post">
							<h1 style="padding-left: 1.2em;">
								<input id="button24" type="submit" name="toSignUp"
									value="<fmt:message key="toSignUp" />" /> <input type="hidden"
									name="command" value="to_signup" />
							</h1>
						</form>
						<h2 style="padding-left: 1.7em;">
							<%@include file="/jsp/jspf/toindex.jspf"%>
						</h2>
					</c:when>
					<c:otherwise>
						<h2 align="left" style="padding-left: 1.7em;">
							<fmt:message key="message.user.hello" />
							${sessionScope.user.login}<br /> ${sessionScope.user.name}
							${sessionScope.user.surname}
							<%@include file="/jsp/jspf/logout.jspf"%>
						</h2>
						<form action="controller" method="post">
							<h2 align="left" style="padding-left: 1.7em;">
								<input id="buttonLogOut" type="submit" name="to_order"
									value="<fmt:message key="to_order" />" /> <input type="hidden"
									name="command" value="show_order" /> <input type="hidden"
									name="user.adminFlag" value="${sessionScope.user.adminFlag}" />
							</h2>
						</form>
						<form action="controller" method="post">
							<h2 style="padding-left: 1.7em;">
								<input type="hidden" name="basketBooks"
									value="${sessionScope.basketBooks}" /><input type="hidden"
									name="command" value="to_basket" /> <input id="buttonLogOut"
									type="submit"
									value="<ctg:basket amount="${sessionScope.amount}" locale="${sessionScope.locale}"/>" />
							</h2>
						</form>
						<c:if test="${sessionScope.user.adminFlag ==1}">
							<form action="controller" method="post">
								<h2 align="left" style="padding-left: 1.7em;">
									<input id="buttonLogOut" type="submit"
										value="<fmt:message key="admin.show.all.user" />" /> <input
										type="hidden" name="command" value="show_all_user" />
								</h2>
							</form>
							<form action="controller" method="post">
								<h2 style="padding-left: 1.7em;">
									<input id="buttonLogOut" type="submit" name="book.add-book"
										value="<fmt:message key="book.add-book" />" /> <input
										type="hidden" name="idBook" value="-1" /><input type="hidden"
										name="command" value="to_add" />
								</h2>
							</form>

						</c:if>
					</c:otherwise>
				</c:choose></td>
			<td align="center"><form action="controller" method="post">
					<input name="command" type="hidden" value="search" />
					<div style="color: olive;" align="center">
						<fmt:message key="main.search" />
						<br /> <input type="text" name="text" value="" /> <input
							id="buttonSearch" type="submit"
							value="<fmt:message key="search" />" />

					</div>
				</form>
				<div align="center">
					<c:if test="${not empty message }">
						<h5>
							<fmt:message key="${message}" />
							<c:if test="${not empty text }"> ${text} <fmt:message
									key="message.searching_book_not_found_end" />
							</c:if>
						</h5>
					</c:if>
					<c:choose>
						<c:when test="${not empty newBook }"><%@include
								file="/jsp/jspf/book-info.jspf"%></c:when>
						<c:otherwise><%@include
								file="/jsp/jspf/main-info.jspf"%>
						</c:otherwise>
					</c:choose>
				</div></td>
			<td width="120">

				<h1 align="right" style="padding-right: 1.3em;">
					<fmt:message key="message.main.menu" />
				</h1>
				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_book_by_genre" />
					<input name="book_type" type="hidden" value="Научная литература" />
					<input name="amount" type="hidden" value="${sessionScope.amount }" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu.sci_fi"
							value="<fmt:message key="menu.sci_fi" />" />
					</h2>
				</form>
				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_book_by_genre" />
					<input name="book_type" type="hidden" value="Фантастика" /> <input
						name="amount" type="hidden" value="${sessionScope.amount }" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu.fantastic"
							value="<fmt:message key="menu.fantastic" />" />
					</h2>
				</form>
				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_book_by_genre" />
					<input name="amount" type="hidden" value="${sessionScope.amount }" />
					<input name="book_type" type="hidden" value="Учебная литература" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu.fiction"
							value="<fmt:message key="menu.studying" />" />
					</h2>
				</form>
				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_book_by_genre" />
					<input name="amount" type="hidden" value="${sessionScope.amount }" />
					<input name="book_type" type="hidden" value="Детектив" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu.detective"
							value="<fmt:message key="menu.detective" />" />
					</h2>
				</form>
				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_book_by_genre" />
					<input name="amount" type="hidden" value="${sessionScope.amount }" />
					<input name="book_type" type="hidden" value="Ужасы" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu.horrow"
							value="<fmt:message key="menu.horrow" />" />
					</h2>
				</form>
				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_book_by_genre" />
					<input name="book_type" type="hidden" value="Справочная литература" />
					<input name="amount" type="hidden" value="${sessionScope.amount }" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu.info-literature"
							value="<fmt:message key="menu.info-literature" />" />
					</h2>
				</form>
				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_book_by_genre" />
					<input name="book_type" type="hidden" value="Детская литература" />
					<input name="amount" type="hidden" value="${sessionScope.amount }" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu."
							value="<fmt:message key="menu.children" />" />
					</h2>
				</form>

				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_book_by_genre" />
					<input name="amount" type="hidden" value="${sessionScope.amount }" />
					<input name="book_type" type="hidden" value="Поэзия" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu.poetry"
							value="<fmt:message key="menu.poetry" />" />
					</h2>
				</form>
				<form action="controller" method="post">
					<input name="command" type="hidden" value="show_all_book" /> <input
						name="amount" type="hidden" value="${sessionScope.amount }" />
					<h2 align="right" style="padding-right: 1.8em;">
						<input id="button24" type="submit" name="menu.all-book"
							value="<fmt:message key="menu.all-book" />" />
					</h2>
				</form>
			</td>
		</tr>
	</table>
	<br/>
</body>
</html>