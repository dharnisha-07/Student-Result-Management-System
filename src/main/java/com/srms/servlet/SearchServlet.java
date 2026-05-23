package com.srms.servlet;

import com.srms.dao.StudentDAO;
import com.srms.exception.DatabaseException;
import com.srms.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = {"/admin/search"})
public class SearchServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String department = request.getParameter("department");
            int semester = Integer.parseInt(request.getParameter("semester"));

            List<Student> students = studentDAO.searchByDeptAndSem(department, semester);
            request.setAttribute("students", students);
            request.setAttribute("department", department);
            request.setAttribute("semester", semester);
            request.getRequestDispatcher("/WEB-INF/views/admin/search.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error in search: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
