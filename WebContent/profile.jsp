<%@page import="database.UserDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />

<%
	if (request.getParameter("username") == null) {
		out.println("Please enter your name.");
	} else {
		request.setAttribute("user",
				UserDAOImpl.getInstance().selectUser((String) request.getParameter("username")));
	}
%>

<c:if test="${requestScope.user == null}">
	<c:redirect url="noSuchUser.jsp" />
</c:if>

<h1>${requestScope.user}</h1>
<br>
<h2>Posts</h2>
<c:forEach var="post" items="${requestScope.user.userPosts}">
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
		<td><c:out value="${post.dateWhenPosted}" /></td>
		<td><c:out value="${post.text}" /></td>
	</tr>
</c:forEach>

<c:import url="/includes/footer.jsp" />