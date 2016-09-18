<%@ page import="database.UserDAOImpl"%>
<%@ page import="database.ConnectionPool"%>
<%@ page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />
<center>
<%
	if (request.getParameter("username") == null) {
		out.println("Please enter your name.");
	} else {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		request.setAttribute("user",
				UserDAOImpl.getInstance().selectUser((String)request.getParameter("username"), connection));
		pool.freeConnection(connection);
	}
%>

<c:if test="${requestScope.user == null}">
	<c:redirect url="noSuchUser.jsp" />
</c:if>

<style>
table {
		font-family: Georgia;
    	font-size: 100%;
   
    	padding: 30px;}
a  	 	{color: blue;}
td 		{padding: 10px;}
</style>

<h1>${requestScope.user}</h1>


<c:set var="followed" value="false" />
<c:forEach var="item" items="${sessionScope.user.followers}">
  <c:if test="${item.username eq rqeuestScope.user.username}">
    <c:set var="followed" value="true" />
  </c:if>
</c:forEach>


<form action="follow" method="get">
	<input type="submit"
		value=<c:choose>
    		<c:when test="${followed}">
        		"Unfollow" 
   			</c:when>    
   			<c:otherwise>
        		"Follow" 
    		</c:otherwise>
</c:choose>
		class="margin_left">
</form>

<h2>Posts</h2>
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
		
<c:forEach var="post" items="${requestScope.user.userPosts}">

	<c:if test="${post == null}">
		<p>
			<i>No Posts</i>
		</p>
	</c:if>
		<tr>
		
		<td><a href="profile.jsp?username=${post.poster.username}"> <c:out
					value="${post.poster.username}" />
		</a></td>
	
		<th></th>
		<td><c:out value="${post.dateWhenPosted}" /></td>
		
	
		<th></th>
		<th></th>
		<th></th>
		<td><c:out value="${post.text}" /></td>
		</tr>
</c:forEach>
</table>
</center>
<c:import url="/includes/footer.jsp" />