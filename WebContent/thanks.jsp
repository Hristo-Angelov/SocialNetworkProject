<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/includes/header.html" />

<h1>Thanks for joining our website!</h1>

<p>Here is the information that you entered:</p>
<label>Email:</label>
<span>${user.email}</span><br>
<label>Username:</label>
<span>${user.username}</span><br>

<c:import url="/includes/footer.jsp" />