package com.srms.servlet;

import com.srms.dao.UserDAO;
import com.srms.exception.DatabaseException;
import com.srms.util.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/change-password"})
public class ChangePasswordServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=session");
            return;
        }
        
        String role = (String) session.getAttribute("role");
        if ("admin".equalsIgnoreCase(role)) {
            request.getRequestDispatcher("/WEB-INF/views/admin/change-password.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/student/change-password.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=session");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String role = (String) session.getAttribute("role");
        String redirectPage = "admin".equalsIgnoreCase(role) ? 
            "/WEB-INF/views/admin/change-password.jsp" : 
            "/WEB-INF/views/student/change-password.jsp";

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New password and confirm password do not match.");
            request.getRequestDispatcher(redirectPage).forward(request, response);
            return;
        }

        if (newPassword.length() < 6) {
            request.setAttribute("errorMessage", "New password must be at least 6 characters long.");
            request.getRequestDispatcher(redirectPage).forward(request, response);
            return;
        }

        try {
            var user = userDAO.getUserById(userId);
            if (user == null || !PasswordUtil.verifyPassword(currentPassword, user.getPasswordHash())) {
                request.setAttribute("errorMessage", "Current password is incorrect.");
                request.getRequestDispatcher(redirectPage).forward(request, response);
                return;
            }

            userDAO.updatePassword(userId, newPassword);
            response.sendRedirect(request.getContextPath() + "/login?success=password_changed");
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
