package com.srms.servlet;

import com.srms.dao.StudentDAO;
import com.srms.dao.UserDAO;
import com.srms.exception.DatabaseException;
import com.srms.model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "StudentServlet", urlPatterns = {"/admin/students"})
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                request.setAttribute("student", null);
                request.getRequestDispatcher("/WEB-INF/views/admin/student-form.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                int studentId = Integer.parseInt(request.getParameter("id"));
                Student student = studentDAO.getStudentById(studentId);
                request.setAttribute("student", student);
                request.getRequestDispatcher("/WEB-INF/views/admin/student-form.jsp").forward(request, response);
            } else if ("delete".equals(action)) {
                int studentId = Integer.parseInt(request.getParameter("id"));
                Student student = studentDAO.getStudentById(studentId);
                if (student != null) {
                    studentDAO.deleteStudent(studentId);
                    if (student.getUserId() > 0) {
                        userDAO.deleteUser(student.getUserId());
                    }
                }
                response.sendRedirect(request.getContextPath() + "/admin/students");
            } else {
                List<Student> students = studentDAO.getAllStudents();
                request.setAttribute("students", students);
                request.getRequestDispatcher("/WEB-INF/views/admin/student-list.jsp").forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action) || "edit".equals(action)) {
                int studentId = "edit".equals(action) ? Integer.parseInt(request.getParameter("studentId")) : 0;
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String department = request.getParameter("department");
                int semester = Integer.parseInt(request.getParameter("semester"));
                String dob = request.getParameter("dob");
                String phone = request.getParameter("phone");

                if ("add".equals(action)) {
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    userDAO.addUser(username, password, "student");
                    int userId = userDAO.getUserByUsername(username).getUserId();
                    studentDAO.addStudent(name, email, department, semester, dob, phone, userId);
                } else {
                    studentDAO.updateStudent(studentId, name, email, department, semester, dob, phone);
                }
                response.sendRedirect(request.getContextPath() + "/admin/students");
            }
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing student: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
