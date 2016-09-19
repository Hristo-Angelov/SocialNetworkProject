<%@page import="database.PostDAOImpl"%>
<%@page import="java.sql.Connection"%>
<%@page import="database.ConnectionPool"%>
<%@ page import="socialnetwork.main.Post"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />
<center>
<style>
table {	font-family: Arial, Helvetica, sans-serif;
    	font-size: 100%;
    	padding: 30px;}
a  	 	{color: blue;}
td 		{padding: 10px;}
form	{display: inline-block;}
</style>

<c:if test="${sessionScope.user == null}">
	<c:redirect url="welcome" />
</c:if>

<%
	if (request.getParameter("originalPostId") == null) {
		out.println("Please enter originalPostId.");
	} else {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int opId = Integer.parseInt(request.getParameter("originalPostId"));
		session.setAttribute("originalPostId", opId);
		Post originalPost = PostDAOImpl.getInstance().selectPost(opId, connection);
		session.setAttribute("originalPost", originalPost);
		pool.freeConnection(connection);
	}
%>

<h2>Retweet</h2>
<table>
	<tr>
		<th>User</th>
		<th>Posted on</th>
		<th>Original Post</th>
	</tr>
	<tr>
		<td>
			<a href="profile.jsp?username=${originalPost.poster.username}"> 
				<c:out value="${originalPost.poster.username}" />
			</a>
		</td>
		
		<td><c:out value="${originalPost.dateWhenPosted}" /></td>
		<td>
		<a href="post.jsp?postId=${originalPost.postId}">
		<c:out value="${originalPost.text}" />
		</a>
		</td>
	</tr>
</table>
<form action="welcome" method="post">
	<input type="hidden" name="action" value="retweet"> 
	<input type="text" name="tweet" placeholder="Add a comment..." autofocus> 
	<input type="submit" value="Retweet" class="margin_left"><br>
</form>
</center>
<c:import url="/includes/footer.jsp" />