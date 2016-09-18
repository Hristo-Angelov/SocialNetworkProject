<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>Georgi and Hristo's cheap Twitter knock-off</title>
	<link rel="stylesheet" href="styles/main.css" type="text/css" />
</head>
<body>
<c:if test="${sessionScope.user != null}">
	<a href="newsfeed.jsp">
		HOME
	</a>
	<a href="profile.jsp?username=${sessionScope.user.username}">
		${sessionScope.user.username}
	</a>
	<form action="home" method="post">
		<input type="hidden" name="action" value="logout">
		<input type="submit" value="Log Out" class="margin_left">
	</form>
</c:if>