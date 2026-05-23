<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.srms.model.Result" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Results</title>
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
            <h1>My Results</h1>
            
            <%
                List<Result> results = (List<Result>) request.getAttribute("results");
                if (results != null && !results.isEmpty()) {
            %>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Subject</th>
                            <th>Max Marks</th>
                            <th>Marks Obtained</th>
                            <th>Percentage</th>
                            <th>Grade</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (Result result : results) {
                        %>
                            <tr>
                                <td><%= result.getSubject().getSubjectName() %></td>
                                <td><%= result.getSubject().getMaxMarks() %></td>
                                <td><%= result.getMarksObtained() %></td>
                                <td><%= String.format("%.2f", result.getPercentage()) %>%</td>
                                <td><%= result.getGrade() %></td>
                                <td>
                                    <% if ("Pass".equals(result.getPassFail())) { %>
                                        <span class="badge badge-success">Pass</span>
                                    <% } else { %>
                                        <span class="badge badge-danger">Fail</span>
                                    <% } %>
                                </td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            <% } else { %>
                <p>No results available yet.</p>
            <% } %>
        </main>
    </div>
</body>
</html>
