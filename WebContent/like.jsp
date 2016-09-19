<%@ page import="database.PostDAOImpl"%>
<%@ page import="database.UserDAOImpl"%>
<%@ page import="database.ConnectionPool"%>
<%@ page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/includes/header.jsp" />

<%
	if (request.getParameter("postId") == null) {
		out.println("Please enter post to like.");
	} else {
		ConnectionPool pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		int opId = Integer.parseInt(request.getParameter("postId"));
		out.println(opId);
		session.setAttribute("postId", opId);

		String action = (String)request.getParameter("action");
		session.setAttribute("likeAction", action);
		out.println(action);
		pool.freeConnection(connection);
	}
%>

<c:redirect url="likes">
</c:redirect>

<c:import url="/includes/footer.jsp" />