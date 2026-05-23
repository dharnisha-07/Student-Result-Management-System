<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Students</title>
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
                    <li><a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a></li>
                </ul>
            </nav>
        </aside>

        <main class="main-content">
            <h1>Search Students</h1>
            
            <form method="POST" action="${pageContext.request.contextPath}/admin/search">
                <div class="form-group">
                    <label for="department">Department:</label>
                    <input type="text" id="department" name="department" value="<%= request.getAttribute("department") != null ? request.getAttribute("department") : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="semester">Semester:</label>
                    <input type="number" id="semester" name="semester" min="1" value="<%= request.getAttribute("semester") != null ? request.getAttribute("semester") : "" %>" required>
                </div>

                <button type="submit" class="btn btn-primary">Search</button>
            </form>

            <%
                java.util.List<com.srms.model.Student> students = (java.util.List<com.srms.model.Student>) request.getAttribute("students");
                if (students != null) {
            %>
                <h2>Results</h2>
                <% if (students.isEmpty()) { %>
                    <p>No students found.</p>
                <% } else { %>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Department</th>
                                <th>Semester</th>
                                <th>Phone</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (com.srms.model.Student student : students) {
                            %>
                                <tr>
                                    <td><%= student.getPersonId() %></td>
                                    <td><%= student.getName() %></td>
                                    <td><%= student.getEmail() %></td>
                                    <td><%= student.getDepartment() %></td>
                                    <td><%= student.getSemester() %></td>
                                    <td><%= student.getPhone() %></td>
                                </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                <% } %>
            <% } %>
        </main>
    </div>
</body>
</html>
