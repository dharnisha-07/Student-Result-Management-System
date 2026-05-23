<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Result Management System - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<%
    String dbErr = (String) application.getAttribute("dbError");
    if (dbErr != null) {
%>
    <div class="db-error-banner">DATABASE ERROR: <%= dbErr %></div>
<% } %>

    <div class="login-container">
        <div class="login-card">
            <h1>SRMS Login</h1>
            
            <%
                String error = request.getParameter("error");
                if ("session".equals(error)) {
            %>
                <div class="alert alert-error">Session expired. Please login again.</div>
            <% } else if ("unauthorized".equals(error)) { %>
                <div class="alert alert-error">Unauthorized access.</div>
            <% } %>

            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                <div class="alert alert-error"><%= errorMessage %></div>
            <% } %>

            <%
                String successMessage = (String) request.getAttribute("successMessage");
                if (successMessage != null) {
            %>
                <div class="alert alert-success"><%= successMessage %></div>
            <% }
                String successParam = request.getParameter("success");
                if ("password_changed".equals(successParam)) {
            %>
                <div class="alert alert-success">Password changed successfully. Please login again.</div>
            <% } %>

            <form method="POST" action="${pageContext.request.contextPath}/login">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>

            <p class="login-hint">Default Admin: username=<strong>admin</strong>, password=<strong>Admin@1234</strong></p>
        </div>
    </div>
</body>
</html>
