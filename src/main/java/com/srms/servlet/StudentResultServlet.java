package com.srms.servlet;

import com.srms.dao.MarksDAO;
import com.srms.dao.StudentDAO;
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

@WebServlet(name = "StudentResultServlet", urlPatterns = {"/student/result"})
public class StudentResultServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();
    private MarksDAO marksDAO = new MarksDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=session");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");
        if (!"student".equalsIgnoreCase(role)) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }

        try {
            Student student = studentDAO.getStudentByUserId(userId);
            if (student == null) {
                request.setAttribute("errorMessage", "Student profile not found.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            List<Mark> marks = marksDAO.getResultsByStudentId(student.getPersonId());
            List<Result> results = new ArrayList<>();
            for (Mark mark : marks) {
                Subject subject = subjectDAO.getSubjectById(mark.getSubjectId());
                if (subject != null) {
                    double percentage = GradeCalculator.computePercentage(mark.getMarksObtained(), subject.getMaxMarks());
                    String grade = GradeCalculator.computeGrade(percentage);
                    String passFail = GradeCalculator.computePassFail(percentage);
                    Result result = new Result(student, subject, mark.getMarksObtained(), percentage, grade, passFail);
                    results.add(result);
                }
            }

            request.setAttribute("results", results);
            request.getRequestDispatcher("/WEB-INF/views/student/result.jsp").forward(request, response);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
