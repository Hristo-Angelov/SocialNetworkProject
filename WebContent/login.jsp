<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/includes/header.html" />

<h1>Login to our Twitter knock-off</h1>
<p>Enter your username and password below.</p>

<form action="home" method="post">
    <input type="hidden" name="action" value="login"> 
    <label class="pad_top">Username:</label>
    <input type="text" name="username" value="${user.username}">
    <c:if test="${usernameMessage != null}">
    <i>${usernameMessage}</i>
	</c:if><br>       
    <label class="pad_top">Password:</label>
    <input type="password" name="password" value="">
    <c:if test="${passwordMessage != null}">
    <i>${passwordMessage}</i>
	</c:if><br>       
    <label>&nbsp;</label>
    <input type="submit" value="Log in" class="margin_left">
</form><br>

<a href="registration.jsp">Don't have an account?</a>

<c:import url="/includes/footer.jsp" />