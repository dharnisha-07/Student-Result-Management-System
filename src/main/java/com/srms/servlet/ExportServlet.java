package com.srms.servlet;

import com.srms.dao.MarksDAO;
import com.srms.dao.SubjectDAO;
import com.srms.dao.StudentDAO;
import com.srms.exception.DatabaseException;
import com.srms.model.Mark;
import com.srms.model.Result;
import com.srms.model.Student;
import com.srms.util.ExportUtil;
import com.srms.util.GradeCalculator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ExportServlet", urlPatterns = {"/admin/export"})
public class ExportServlet extends HttpServlet {
    private MarksDAO marksDAO = new MarksDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();
    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Student> allStudents = studentDAO.getAllStudents();
            List<Result> allResults = new ArrayList<>();

            for (Student student : allStudents) {
                List<Mark> marks = marksDAO.getMarksByStudent(student.getPersonId());
                for (Mark mark : marks) {
                    var subject = subjectDAO.getSubjectById(mark.getSubjectId());
                    if (subject != null) {
                        double percentage = GradeCalculator.computePercentage(mark.getMarksObtained(), subject.getMaxMarks());
                        String grade = GradeCalculator.computeGrade(percentage);
                        String passFail = GradeCalculator.computePassFail(percentage);
                        Result result = new Result(student, subject, mark.getMarksObtained(), percentage, grade, passFail);
                        allResults.add(result);
                    }
                }
            }

            String csv = ExportUtil.generateCSV(allResults);

            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=\"results.csv\"");
            response.getWriter().write(csv);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            try {
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            } catch (ServletException ex) {
                ex.printStackTrace();
            }
        }
    }
}
