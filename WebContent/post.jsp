<%@page import="database.PostDAOImpl"%>
<%@ page import="database.UserDAOImpl"%>
<%@ page import="database.ConnectionPool"%>
<%@ page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />
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
<c:forEach var="post" items="${post.retweets}">
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
<c:forEach var="reply" items="${post.replies}">
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

<c:if test="${requestScope.user == null}">
	<c:redirect url="noSuchUser.jsp" />
</c:if>





</center>
<c:import url="/includes/footer.jsp" />