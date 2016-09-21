<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<c:if test="${user == null}">
	<c:redirect url="welcome" />
</c:if>
<h1>Twittar newsfeed</h1>

<form action="welcome" method="post">
	<input type="hidden" name="action" value="tweet"> <input
		type="text" name="tweet" placeholder="What's happening?"
		value="${post.text}" autofocus maxlength="140"><br> <label>&nbsp;</label>
	<input type="submit" value="Tweet" class="margin_left">
</form>

<table >
<tr>
		<th>User</th>
		<th>Posted on</th>
		<th>Text</th>
</tr>

<c:forEach var="post" items="${sessionScope.user.newsfeed}">
	<c:if test="${post == null}">
		<p>
			<i>No Posts</i>
		</p>
	</c:if>
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
		<br>
		<form action="retweet.jsp?originalPostId=${post.postId}" method="post">
			<input type="submit" value="Retweet (${fn:length(post.retweets)})" class="margin_left">
		</form>
		<form action="reply.jsp?originalPostId=${post.postId}" method="post">
			<input type="submit" value="Reply (${fn:length(post.replies)})" class="margin_left">
		</form>
		<c:set var="liked" value="false" />
	<c:forEach var="liker" items="${post.likes}">
		<c:if test="${liker.userId == sessionScope.user.userId}">
			<c:set var="liked" value="true" />
		</c:if>
	</c:forEach>

		<c:choose>
			<c:when test="${liked != true}">
				<form action="likes" method="post">
					<input type="hidden" name="postId" value="${post.postId}">
					<input type="hidden" name="action" value="like">
					<input type="submit" value="Like (${fn:length(post.likes)})" class="margin_left">
				</form>
			</c:when>
			<c:otherwise>
			<form action="likes" method="post">
				<input type="hidden" name="postId" value="${post.postId}">
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