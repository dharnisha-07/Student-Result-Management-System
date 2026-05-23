<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
<%
    String dbErr = (String) application.getAttribute("dbError");
    if (dbErr != null) {
%>
    <div class="db-error-banner">DATABASE ERROR: <%= dbErr %></div>
<% } %>

    <div class="error-container">
        <div class="error-card">
            <h1>Error Occurred</h1>
            
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage == null) {
                    errorMessage = "An unexpected error occurred.";
                }
            %>
            <div class="alert alert-error">
                <strong>Error Details:</strong><br>
                <%= errorMessage %>
            </div>

            <%
                Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
                if (throwable != null) {
            %>
                <details class="error-details">
                    <summary>Stack Trace (click to expand)</summary>
                    <pre><%
                        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                        java.io.PrintStream ps = new java.io.PrintStream(baos);
                        throwable.printStackTrace(ps);
                        out.print(baos.toString());
                    %></pre>
                </details>
            <% } %>

            <div class="error-actions">
                <a href="javascript:history.back()" class="btn btn-secondary">Go Back</a>
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Back to Login</a>
            </div>
        </div>
    </div>
</body>
</html>
