<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.srms.model.Subject" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subjects List</title>
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
                <h2>SRMS Admin</h2>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/students">Students</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/subjects">Subjects</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a></li>
                </ul>
            </nav>
        </aside>

        <main class="main-content">
            <h1>Subjects</h1>
            <a href="${pageContext.request.contextPath}/admin/subjects?action=add" class="btn btn-primary">Add New Subject</a>
            
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Subject Name</th>
                        <th>Department</th>
                        <th>Semester</th>
                        <th>Max Marks</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
                        if (subjects != null && !subjects.isEmpty()) {
                            for (Subject subject : subjects) {
                    %>
                        <tr>
                            <td><%= subject.getSubjectId() %></td>
                            <td><%= subject.getSubjectName() %></td>
                            <td><%= subject.getDepartment() %></td>
                            <td><%= subject.getSemester() %></td>
                            <td><%= subject.getMaxMarks() %></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/subjects?action=edit&id=<%= subject.getSubjectId() %>" class="btn btn-small btn-info">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/subjects?action=delete&id=<%= subject.getSubjectId() %>" class="btn btn-small btn-danger" onclick="return confirm('Are you sure?')">Delete</a>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr><td colspan="6" class="text-center">No subjects found.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </main>
    </div>
</body>
</html>
