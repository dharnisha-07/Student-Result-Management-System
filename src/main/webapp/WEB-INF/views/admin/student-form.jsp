<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.srms.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <script src="${pageContext.request.contextPath}/resources/js/validation.js"></script>
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
            <h1><%= request.getAttribute("student") != null ? "Edit" : "Add" %> Student</h1>
            
            <form method="POST" action="${pageContext.request.contextPath}/admin/students" onsubmit="validateStudentForm(event)">
                <input type="hidden" name="action" value="<%= request.getAttribute("student") != null ? "edit" : "add" %>">
                <%
                    Student student = (Student) request.getAttribute("student");
                    if (student != null) {
                %>
                    <input type="hidden" name="studentId" value="<%= student.getPersonId() %>">
                <% } %>

                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" value="<%= student != null ? student.getName() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="<%= student != null ? student.getEmail() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="department">Department:</label>
                    <input type="text" id="department" name="department" value="<%= student != null ? student.getDepartment() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="semester">Semester:</label>
                    <input type="number" id="semester" name="semester" min="1" value="<%= student != null ? student.getSemester() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="dob">Date of Birth:</label>
                    <input type="date" id="dob" name="dob" value="<%= student != null ? student.getDob() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone:</label>
                    <input type="tel" id="phone" name="phone" value="<%= student != null ? student.getPhone() : "" %>" required>
                </div>

                <% if (student == null) { %>
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" id="username" name="username" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Initial Password:</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                <% } %>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save</button>
                    <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </main>
    </div>
</body>
</html>
