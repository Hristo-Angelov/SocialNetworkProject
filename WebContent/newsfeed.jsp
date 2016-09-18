<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />
<center>
<style>
table {
		font-family: Arial, Helvetica, sans-serif;
    	font-size: 100%;
   
    	padding: 30px;}
a  	 	{color: blue;}
td 		{padding: 10px;}
</style>
<c:if test="${user == null}">
	<c:redirect url="welcome" />
</c:if>
<h1>Placeholder tweet factory header</h1>

<form action="welcome" method="post">
	<input type="hidden" name="action" value="tweet"> <input
		type="text" name="tweet" placeholder="What's happening?"
		value="${post.text}" autofocus><br> <label>&nbsp;</label>
	<input type="submit" value="Tweet" class="margin_left">
</form>

<table >
<tr>
		<th>User</th>
		<th></th>
		<th>Posted on</th>
		<th></th>
		<th></th>
		<th></th>
		<th>Text</th>
</tr>

<c:forEach var="post" items="${sessionScope.user.newsfeed}">
	<c:if test="${post == null}">
		<p>
			<i>No Posts</i>
		</p>
	</c:if>
	<br>
	<tr>
		<td>
			<a href="profile.jsp?username=${post.poster.username}"> 
				<c:out value="${post.poster.username}" />
			</a>
		</td>
		
		<th></th>
		<td><c:out value="${post.dateWhenPosted}" /></td>
		
		<th></th>
		<th></th>
		<th></th>
		<td>
		<a href="post.jsp?postId=${post.postId}">
		<c:out value="${post.text}" />
		</a>
		</td>
	</tr>
</c:forEach>
</table>
</center>
<c:import url="/includes/footer.jsp" />