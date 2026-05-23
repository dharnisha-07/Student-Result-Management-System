<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.srms.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Students List</title>
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
            <h1>Students</h1>
            <a href="${pageContext.request.contextPath}/admin/students?action=add" class="btn btn-primary">Add New Student</a>
            
            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Department</th>
                        <th>Semester</th>
                        <th>Phone</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Student> students = (List<Student>) request.getAttribute("students");
                        if (students != null && !students.isEmpty()) {
                            for (Student student : students) {
                    %>
                        <tr>
                            <td><%= student.getPersonId() %></td>
                            <td><%= student.getName() %></td>
                            <td><%= student.getEmail() %></td>
                            <td><%= student.getDepartment() %></td>
                            <td><%= student.getSemester() %></td>
                            <td><%= student.getPhone() %></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/students?action=edit&id=<%= student.getPersonId() %>" class="btn btn-small btn-info">Edit</a>
                                <a href="${pageContext.request.contextPath}/admin/students?action=delete&id=<%= student.getPersonId() %>" class="btn btn-small btn-danger" onclick="return confirm('Are you sure?')">Delete</a>
                                <a href="${pageContext.request.contextPath}/admin/results?studentId=<%= student.getPersonId() %>" class="btn btn-small btn-secondary">View Results</a>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr><td colspan="7" class="text-center">No students found.</td></tr>
                    <% } %>
                </tbody>
            </table>
        </main>
    </div>
</body>
</html>
