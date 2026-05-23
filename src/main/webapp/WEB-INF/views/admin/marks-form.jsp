<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.srms.model.Student, com.srms.model.Subject, com.srms.model.Mark" %>
<%
    List<Student> students = (List<Student>) request.getAttribute("students");
    Student selectedStudent = (Student) request.getAttribute("selectedStudent");
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjects");
    List<Mark> marks = (List<Mark>) request.getAttribute("marks");
    Integer selectedStudentId = (Integer) request.getAttribute("selectedStudentId");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Marks</title>
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
                <li><a href="${pageContext.request.contextPath}/admin/marks">Marks</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/results">Results</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/search">Search</a></li>
                <li><a href="${pageContext.request.contextPath}/change-password">Change Password</a></li>
                <li><a href="${pageContext.request.contextPath}/logout" class="logout-link">Logout</a></li>
            </ul>
        </nav>
    </aside>

    <main class="main-content">
        <h1>Assign Marks</h1>

        <form method="GET" action="${pageContext.request.contextPath}/admin/marks">
            <div class="form-group">
                <label for="studentId">Select Student:</label>
                <select id="studentId" name="studentId" required>
                    <option value="">Choose a student</option>
                    <% if (students != null) {
                        for (Student student : students) { %>
                        <option value="<%= student.getPersonId() %>"
                            <%= selectedStudentId != null && selectedStudentId == student.getPersonId() ? "selected" : "" %>>
                            <%= student.getName() %> - <%= student.getDepartment() %> / Sem <%= student.getSemester() %>
                        </option>
                    <% } } %>
                </select>
                <button type="submit" class="btn btn-primary">Load Student</button>
            </div>
        </form>

        <% if (selectedStudent != null) { %>
            <div class="profile-card">
                <h2>Student: <%= selectedStudent.getName() %></h2>
                <p><strong>Department:</strong> <%= selectedStudent.getDepartment() %></p>
                <p><strong>Semester:</strong> <%= selectedStudent.getSemester() %></p>
            </div>

            <% if (subjects != null && !subjects.isEmpty()) { %>
                <h2>Assign Marks for All Subjects</h2>
                <p style="color:#666; margin-bottom:15px;"><em>Fill in marks for each subject. Leave empty to skip.</em></p>
                <form method="POST" action="${pageContext.request.contextPath}/admin/marks">
                    <input type="hidden" name="studentId" value="<%= selectedStudentId %>">
                    <table class="data-table" style="width:100%; margin-bottom:20px;">
                        <thead>
                            <tr>
                                <th>Subject</th>
                                <th>Max Marks</th>
                                <th>Marks Obtained</th>
                                <th>Exam Type</th>
                                <th>Exam Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Subject subject : subjects) {
                                Mark existingMark = null;
                                if (marks != null) {
                                    for (Mark m : marks) {
                                        if (m.getSubjectId() == subject.getSubjectId()) {
                                            existingMark = m;
                                            break;
                                        }
                                    }
                                }
                            %>
                            <tr>
                                <td><strong><%= subject.getSubjectName() %></strong></td>
                                <td><%= subject.getMaxMarks() %></td>
                                <td>
                                    <input type="hidden" name="subjectId_<%= subject.getSubjectId() %>" value="<%= subject.getSubjectId() %>"/>
                                    <input type="number" name="marks_<%= subject.getSubjectId() %>"
                                           min="0" max="<%= subject.getMaxMarks() %>"
                                           value="<%= existingMark != null ? existingMark.getMarksObtained() : "" %>"
                                           style="width:100%;" placeholder="0"/>
                                </td>
                                <td>
                                    <select name="examType_<%= subject.getSubjectId() %>" style="width:100%;">
                                        <option value="Internal" <%= existingMark != null && "Internal".equals(existingMark.getExamType()) ? "selected" : "" %>>Internal</option>
                                        <option value="Midterm"  <%= existingMark != null && "Midterm".equals(existingMark.getExamType())  ? "selected" : "" %>>Midterm</option>
                                        <option value="Final"    <%= existingMark != null && "Final".equals(existingMark.getExamType())    ? "selected" : "" %>>Final</option>
                                        <option value="Quiz"     <%= existingMark != null && "Quiz".equals(existingMark.getExamType())     ? "selected" : "" %>>Quiz</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="date" name="examDate_<%= subject.getSubjectId() %>"
                                           value="<%= existingMark != null && existingMark.getExamDate() != null ? existingMark.getExamDate() : "" %>"
                                           style="width:100%;"/>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Save All Marks</button>
                        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            <% } else { %>
                <div style="background:#ffe6e6; border:1px solid #ff6b6b; padding:15px; margin:20px 0; border-radius:4px; color:#c92a2a;">
                    <strong>No subjects found for this student's department and semester.</strong><br/>
                    Please add subjects first from the <a href="${pageContext.request.contextPath}/admin/subjects" style="color:inherit; text-decoration:underline;">Subjects menu</a>.
                </div>
            <% } %>

            <h2>Mark Assignment History</h2>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Subject</th>
                        <th>Marks</th>
                        <th>Exam Type</th>
                        <th>Exam Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (marks != null && !marks.isEmpty()) {
                        for (Mark mark : marks) {
                            Subject matchedSubject = null;
                            if (subjects != null) {
                                for (Subject s : subjects) {
                                    if (s.getSubjectId() == mark.getSubjectId()) {
                                        matchedSubject = s;
                                        break;
                                    }
                                }
                            }
                    %>
                    <tr>
                        <td><%= matchedSubject != null ? matchedSubject.getSubjectName() : "Unknown" %></td>
                        <td><%= mark.getMarksObtained() %></td>
                        <td><%= mark.getExamType() %></td>
                        <td><%= mark.getExamDate() %></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/marks?action=delete&id=<%= mark.getMarkId() %>&studentId=<%= selectedStudentId %>"
                               class="btn btn-small btn-danger"
                               onclick="return confirm('Delete this mark?')">Delete</a>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr><td colspan="5" class="text-center">No marks assigned yet.</td></tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <p>Select a student above to view and manage marks.</p>
        <% } %>
    </main>
</div>
</body>
</html>