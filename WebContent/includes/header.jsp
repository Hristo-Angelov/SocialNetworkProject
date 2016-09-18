<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	
	<meta charset="utf-8">
	<title>Georgi and Hristo's cheap Twitter knock-off</title>
	<link rel="stylesheet" href="styles/owl.theme.css" type="text/css" />

</head>
<body>
	<ul>
	<li>
	<a href="newsfeed.jsp">
	
		HOME
	</a>
	</li>
	
	<c:if test="${sessionScope.user != null}">
	<li>
	<a href="profile.jsp?username=${sessionScope.user.username}">
		${sessionScope.user.username}
	</a>
<<<<<<< HEAD
	</li>
</c:if>
</ul>
=======
	<form action="home" method="post">
		<input type="hidden" name="action" value="logout">
		<input type="submit" value="Log Out" class="margin_left">
	</form>
</c:if>
>>>>>>> a4fb22fff74d175789eeaf5dcc4cffa20060f001
