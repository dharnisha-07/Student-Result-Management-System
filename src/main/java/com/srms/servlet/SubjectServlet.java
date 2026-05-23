package com.srms.servlet;

import com.srms.dao.SubjectDAO;
import com.srms.exception.DatabaseException;
import com.srms.model.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SubjectServlet", urlPatterns = {"/admin/subjects"})
public class SubjectServlet extends HttpServlet {
    private SubjectDAO subjectDAO = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                request.setAttribute("subject", null);
                request.getRequestDispatcher("/WEB-INF/views/admin/subject-form.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                int subjectId = Integer.parseInt(request.getParameter("id"));
                Subject subject = subjectDAO.getSubjectById(subjectId);
                request.setAttribute("subject", subject);
                request.getRequestDispatcher("/WEB-INF/views/admin/subject-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int subjectId = Integer.parseInt(request.getParameter("id"));
                subjectDAO.deleteSubject(subjectId);
                response.sendRedirect(request.getContextPath() + "/admin/subjects");
            } else {
                List<Subject> subjects = subjectDAO.getAllSubjects();
                request.setAttribute("subjects", subjects);
                request.getRequestDispatcher("/WEB-INF/views/admin/subject-list.jsp").forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action) || "edit".equals(action)) {
                String subjectName = request.getParameter("subjectName");
                String department = request.getParameter("department");
                int semester = Integer.parseInt(request.getParameter("semester"));
                int maxMarks = Integer.parseInt(request.getParameter("maxMarks"));

                if ("add".equals(action)) {
                    subjectDAO.addSubject(subjectName, department, semester, maxMarks);
                } else {
                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    subjectDAO.updateSubject(subjectId, subjectName, department, semester, maxMarks);
                }
                response.sendRedirect(request.getContextPath() + "/admin/subjects");
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing subject: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
