<%@ page import="database.PostDAOImpl"%>
<%@ page import="database.UserDAOImpl"%>
<%@ page import="database.ConnectionPool"%>
<%@ page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />

<style>
	table {	font-family: Arial, Helvetica, sans-serif;
    		font-size: 100%;
    		padding: 30px;}
	a  	 	{color: blue;}
	td 		{padding: 10px;}
</style>
<center>
<%
	if (request.getParameter("postId") == null) {
		out.println("What is this post?!");
	} else {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		request.setAttribute("post",
				PostDAOImpl.getInstance().selectPost(Integer.parseInt(request.getParameter("postId")), connection));
		pool.freeConnection(connection);
	}
%>


<table>

	<tr>
		<td>
			<a href="profile.jsp?username=${post.poster.username}"> 
				<c:out value="${post.poster.username}" />
				
			</a>
		</td>
		<td><c:out value="${post.dateWhenPosted}" /></td>
		<td>
		<a href="post.jsp?postId=${post.postId}">
		<c:out value="${post.text}" />
		</a>
		</td>
	</tr>
</table>

<table>

<tr>
		<th>User</th>
		<th>Posted on</th>
		<th>Retweets</th>
</tr>


<c:forEach var="retweet" items="${post.retweets}">
	<c:if test="${retweet == null}">
		<p>
			<i>No retweets</i>
		</p>
	</c:if>
	<tr>
		<td>
			<a href="profile.jsp?username=${retweet.poster.username}"> 
				<c:out value="${retweet.poster.username}" />
			</a>
		</td>
		
		<td><c:out value="${retweet.dateWhenPosted}" /></td>
		
		<td>
		<a href="post.jsp?postId=${retweet.postId}">
		<c:out value="${retweet.text}" />
		</a>
		</td>
	</tr>
</c:forEach>
</table>

<table>
<tr>
		<th>User</th>
		<th>Posted on</th>
		<th>Replies</th>
</tr>

<c:forEach var="reply" items="${post.replies}">
	<c:if test="${reply == null}">
		<p>
			<i>No replies</i>
		</p>
	</c:if>
	<tr>
		<td>
			<a href="profile.jsp?username=${reply.poster.username}"> 
				<c:out value="${reply.poster.username}" />
			</a>
		</td>
		
		<td><c:out value="${reply.dateWhenPosted}" /></td>
		
		<td>
		<a href="post.jsp?postId=${reply.postId}">
		<c:out value="${reply.text}" />
		</a>
		</td>
	</tr>
</c:forEach>
</table>

</center>
<c:import url="/includes/footer.jsp" />