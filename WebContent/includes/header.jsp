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
	</li>
</c:if>
</ul>