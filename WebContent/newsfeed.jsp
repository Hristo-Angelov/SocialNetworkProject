<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/includes/header.html" />

<h1>Placeholder tweet factory header</h1>

<form action="welcome" method="post">
    <input type="hidden" name="action" value="tweet"> 
    <input type="text" name="tweet" placeholder="What's happening?" value="${post.text}" autofocus><br>     
    <label>&nbsp;</label>
    <input type="submit" value="Tweet" class="margin_left">
</form>

<c:import url="/includes/footer.jsp" />