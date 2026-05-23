<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body class="admin-page">
<%
    String dbErr = (String) application.getAttribute("dbError");
    if (dbErr != null) {
%>
    <div class="db-error-banner">DATABASE ERROR: <%= dbErr %></div>
<% } %>

    <div class="admin-container">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="sidebar-header">
                <h2>SRMS Admin</h2>
                <p><%= session.getAttribute("username") %></p>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/students">Students</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/subjects">Subjects</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/marks">Marks</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/search">Search</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/export">Export Results</a></li>
                    <li><a href="${pageContext.request.contextPath}/change-password">Change Password</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a></li>
                </ul>
            </nav>
        </aside>

        <!-- Main Content -->
        <main class="main-content">
            <h1>Admin Dashboard</h1>
            
            <div class="dashboard-cards">
                <div class="card">
                    <h3>Total Students</h3>
                    <p class="card-number"><%= request.getAttribute("totalStudents") != null ? request.getAttribute("totalStudents") : 0 %></p>
                </div>
                <div class="card">
                    <h3>Total Subjects</h3>
                    <p class="card-number"><%= request.getAttribute("totalSubjects") != null ? request.getAttribute("totalSubjects") : 0 %></p>
                </div>
            </div>

            <div class="dashboard-actions">
                <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-primary">Manage Students</a>
                <a href="${pageContext.request.contextPath}/admin/subjects" class="btn btn-primary">Manage Subjects</a>
                <a href="${pageContext.request.contextPath}/admin/marks" class="btn btn-primary">Assign Marks</a>
            </div>
        </main>
    </div>
</body>
</html>
