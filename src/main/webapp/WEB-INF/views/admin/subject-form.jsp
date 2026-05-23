<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.srms.model.Subject" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Subject Form</title>
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
            <h1><%= request.getAttribute("subject") != null ? "Edit" : "Add" %> Subject</h1>
            
            <form method="POST" action="${pageContext.request.contextPath}/admin/subjects">
                <input type="hidden" name="action" value="<%= request.getAttribute("subject") != null ? "edit" : "add" %>">
                <%
                    Subject subject = (Subject) request.getAttribute("subject");
                    if (subject != null) {
                %>
                    <input type="hidden" name="subjectId" value="<%= subject.getSubjectId() %>">
                <% } %>

                <div class="form-group">
                    <label for="subjectName">Subject Name:</label>
                    <input type="text" id="subjectName" name="subjectName" value="<%= subject != null ? subject.getSubjectName() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="department">Department:</label>
                    <input type="text" id="department" name="department" value="<%= subject != null ? subject.getDepartment() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="semester">Semester:</label>
                    <input type="number" id="semester" name="semester" min="1" value="<%= subject != null ? subject.getSemester() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="maxMarks">Max Marks:</label>
                    <input type="number" id="maxMarks" name="maxMarks" min="1" value="<%= subject != null ? subject.getMaxMarks() : "" %>" required>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Save</button>
                    <a href="${pageContext.request.contextPath}/admin/subjects" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </main>
    </div>
</body>
</html>
