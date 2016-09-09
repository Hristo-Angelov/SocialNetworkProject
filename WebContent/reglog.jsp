<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/includes/header.html" />

<h1>Join our Twitter knock-off</h1>
<p>To join, enter your name and
   email address below.</p>

<form action="home" method="post">
    <input type="hidden" name="action" value="register"> 
    <label class="pad_top">Username:</label>
    <input type="text" name="username" value="${user.username}"><br>       
    <label class="pad_top">Email:</label>
    <input type="text" name="email" value="${user.email}"><br>
    <label class="pad_top">Password:</label>
    <input type="password" name="password" value=""><br>       
    <label>&nbsp;</label>
    <input type="submit" value="Join Now" class="margin_left">
</form>

<h1>Login to our Twitter knock-off</h1>
<p>If you already have an account, enter your username and password below.</p>
   
<form action="home" method="post">
    <input type="hidden" name="action" value="login"> 
    <label class="pad_top">Username:</label>
    <input type="text" name="username"><br>   
    <label class="pad_top">Password:</label>
    <input type="password" name="password"><br>      
    <label>&nbsp;</label>
    <input type="submit" value="Log in" class="margin_left">
</form>

<c:import url="/includes/footer.jsp" />