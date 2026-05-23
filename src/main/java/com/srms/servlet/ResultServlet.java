package com.srms.servlet;

import com.srms.dao.MarksDAO;
import com.srms.dao.SubjectDAO;
import com.srms.exception.DatabaseException;
import com.srms.model.Mark;
import com.srms.model.Result;
import com.srms.model.Student;
import com.srms.model.Subject;
import com.srms.util.GradeCalculator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ResultServlet", urlPatterns = {"/admin/results"})
public class ResultServlet extends HttpServlet {
    private MarksDAO marksDAO = new MarksDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession(false).getAttribute("userId");
            String role = (String) request.getSession(false).getAttribute("role");

            if (userId == null) {
                request.setAttribute("errorMessage", "Session expired. Please login again.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                return;
            }

            int studentId = 0;
            String studentIdParam = request.getParameter("studentId");
            if (studentIdParam != null && !studentIdParam.isEmpty()) {
                studentId = Integer.parseInt(studentIdParam);
            }

            if (studentId <= 0) {
                request.setAttribute("results", null);
                request.getRequestDispatcher("/WEB-INF/views/admin/result-view.jsp").forward(request, response);
                return;
            }

            List<Mark> marks = marksDAO.getMarksByStudent(studentId);
            List<Result> results = new ArrayList<>();

            for (Mark mark : marks) {
                Subject subject = subjectDAO.getSubjectById(mark.getSubjectId());
                if (subject != null) {
                    double percentage = GradeCalculator.computePercentage(mark.getMarksObtained(), subject.getMaxMarks());
                    String grade = GradeCalculator.computeGrade(percentage);
                    String passFail = GradeCalculator.computePassFail(percentage);

                    Student student = new Student();
                    Result result = new Result(student, subject, mark.getMarksObtained(), percentage, grade, passFail);
                    results.add(result);
                }
            }

            request.setAttribute("results", results);
            request.getRequestDispatcher("/WEB-INF/views/admin/result-view.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error fetching results: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
