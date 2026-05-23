<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.srms.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard</title>
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
        <aside class="sidebar">
            <div class="sidebar-header">
                <h2>SRMS Student</h2>
                <p><%= session.getAttribute("username") %></p>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/student/result">My Results</a></li>
                    <li><a href="${pageContext.request.contextPath}/change-password">Change Password</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a></li>
                </ul>
            </nav>
        </aside>

        <main class="main-content">
            <h1>Student Dashboard</h1>

            <%
                Student student = (Student) request.getAttribute("student");
                Integer totalSubjects = (Integer) request.getAttribute("totalSubjects");
                Double overallPercentage = (Double) request.getAttribute("overallPercentage");
                String passFail = (String) request.getAttribute("passFail");
                if (student != null) {
            %>
                <div class="profile-card">
                    <h2>Your Profile</h2>
                    <p><strong>Name:</strong> <%= student.getName() %></p>
                    <p><strong>Email:</strong> <%= student.getEmail() %></p>
                    <p><strong>Department:</strong> <%= student.getDepartment() %></p>
                    <p><strong>Semester:</strong> <%= student.getSemester() %></p>
                    <p><strong>Date of Birth:</strong> <%= student.getDob() %></p>
                    <p><strong>Phone:</strong> <%= student.getPhone() %></p>
                </div>

                <div class="stats-grid">
                    <div class="stat-card">
                        <h3>Total Subjects</h3>
                        <p><%= totalSubjects != null ? totalSubjects : 0 %></p>
                    </div>
                    <div class="stat-card">
                        <h3>Overall Percentage</h3>
                        <p><%= overallPercentage != null ? String.format("%.2f", overallPercentage) + "%" : "0%" %></p>
                    </div>
                    <div class="stat-card">
                        <h3>Status</h3>
                        <p><%= passFail != null ? passFail : "N/A" %></p>
                    </div>
                </div>

                <div class="dashboard-actions">
                    <a href="${pageContext.request.contextPath}/student/result" class="btn btn-primary">View My Results</a>
                </div>
            <%
                } else {
            %>
                <div class="alert alert-error">Student profile not loaded. Please log in again.</div>
            <%
                }
            %>
        </main>
    </div>
</body>
</html>
