<%@page import="database.PostDAOImpl"%>
<%@ page import="database.UserDAOImpl"%>
<%@ page import="database.ConnectionPool"%>
<%@ page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:import url="/includes/header.jsp" />
<style>
table {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 100%;
	padding: 30px;
}

a {
	color: blue;
}

td {
	padding: 10px;
}

form {
	display: inline-block;
}
</style>
<center>
	<%
		if (request.getParameter("username") == null) {
			out.println("Please enter your name.");
		} else {
			ConnectionPool pool = ConnectionPool.getInstance();
			Connection connection = pool.getConnection();
			session.setAttribute("subject",
					UserDAOImpl.getInstance().selectUser((String) request.getParameter("username"), connection));
			pool.freeConnection(connection);
		}
	%>

	<c:if test="${subject == null}">
		<c:redirect url="noSuchUser.jsp" />
	</c:if>

	<style>
table {
	font-family: Georgia;
	font-size: 100%;
	padding: 30px;
}

a {
	color: blue;
}

td {
	padding: 10px;
}
</style>

	<h1>
		<c:out value="${subject.username}"></c:out>
	</h1>


	<c:set var="followed" value="false" />
	<c:forEach var="follower" items="${subject.followers}">
		<c:if test="${follower.userId == sessionScope.user.userId}">
			<c:set var="followed" value="true" />
		</c:if>
	</c:forEach>

	<c:if test="${subject.username ne sessionScope.user.username}">
		<c:choose>
			<c:when test="${followed == true}">
				<form action="home" method="post">
					<input type="hidden" name="action" value="unfollow"> <input
						type="submit" value="Unfollow" class="margin_left">
				</form>
			</c:when>
			<c:otherwise>
				<form action="home" method="post">
					<input type="hidden" name="action" value="follow"> <input
						type="submit" value="Follow" class="margin_left">
				</form>
			</c:otherwise>
		</c:choose>
	</c:if>

	<h2>Posts</h2>
	<table>
		<tr>
			<th>User</th>
			<th>Posted on</th>
			<th>Text</th>
		</tr>

		<c:forEach var="post" items="${subject.userPosts}">

			<c:if test="${post == null}">
				<p>
					<i>No Posts</i>
				</p>
			</c:if>
			<tr>

				<td><a href="profile.jsp?username=${post.poster.username}">
						<c:out value="${post.poster.username}" />
				</a></td>

				<td><c:out value="${post.dateWhenPosted}" /></td>

				<td><c:out value="${post.text}" /> <br>
					<form action="retweet.jsp?originalPostId=${post.postId}"
						method="post">
						<input type="submit" value="Retweet (${fn:length(post.retweets)})"
							class="margin_left">
					</form>
					<form action="reply.jsp?originalPostId=${post.postId}"
						method="post">
						<input type="submit" value="Reply (${fn:length(post.replies)})"
							class="margin_left">
					</form>

				<c:forEach var="liker" items="${post.likes}">
					<c:if test="${liker.userId == sessionScope.user.userId}">
						<c:set var="liked" value="true" />
					</c:if>
				</c:forEach>
				<c:forEach var="liker" items="${post.likes}">
		<c:if test="${liker.userId == sessionScope.user.userId}">
			<c:set var="liked" value="true" />
		</c:if>
	</c:forEach>

		<c:choose>
			<c:when test="${liked != true}">
				<form action="like.jsp?postId=${post.postId}" method="post">
					<input type="hidden" name="action" value="like">
					<input type="submit" value="Like (${fn:length(post.likes)})" class="margin_left">
				</form>
			</c:when>
			<c:otherwise>
			<form action="like.jsp?postId=${post.postId}" method="post">
				<input type="hidden" name="action" value="unlike">
				<input type="submit" value="Unlike (${fn:length(post.likes)})" class="margin_left">
			</form>
			</c:otherwise>
		</c:choose>
				</td>
			</tr>
		</c:forEach>
	</table>
</center>
<c:import url="/includes/footer.jsp" />