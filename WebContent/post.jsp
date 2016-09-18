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

<c:if test="${requestScope.user == null}">
	<c:redirect url="noSuchUser.jsp" />
</c:if>


</center>
<c:import url="/includes/footer.jsp" />