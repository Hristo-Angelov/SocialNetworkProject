<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/includes/header.jsp" />

<c:if test="${sessionScope.user != null}">
	<c:redirect url="welcome"/>
</c:if>

<h1>Join our Twitter knock-off</h1>
<p>To join, enter your name and
   email address below.</p>

<form action="home" method="post">
    <input type="hidden" name="action" value="register"> 
    <label class="pad_top">Username:</label>
    <input type="text" name="username" value="${user.username}">
    <c:if test="${usernameMessage != null}">
    <i>${usernameMessage}</i>
	</c:if><br>       
    <label class="pad_top">Email:</label>
    <input type="text" name="email" value="${user.email}">
    <c:if test="${emailMessage != null}">
    <i>${emailMessage}</i>
	</c:if><br>
    <label class="pad_top">Password:</label>
    <input type="password" name="password" value="">
    <c:if test="${passwordMessage != null}">
    <i>${passwordMessage}</i>
	</c:if><br>       
    <label>&nbsp;</label>
    <input type="submit" value="Join Now" class="margin_left">
</form><br>
<a href="login.jsp">Already have an account?</a>

<c:import url="/includes/footer.jsp" />