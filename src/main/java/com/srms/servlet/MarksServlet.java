package com.srms.servlet;

import com.srms.dao.MarksDAO;
import com.srms.dao.StudentDAO;
import com.srms.dao.SubjectDAO;
import com.srms.exception.DatabaseException;
import com.srms.model.Mark;
import com.srms.model.Student;
import com.srms.model.Subject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MarksServlet", urlPatterns = {"/admin/marks"})
public class MarksServlet extends HttpServlet {
    private MarksDAO marksDAO = new MarksDAO();
    private StudentDAO studentDAO = new StudentDAO();
    private SubjectDAO subjectDAO = new SubjectDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String studentIdParam = request.getParameter("studentId");

        try {
            List<Student> students = studentDAO.getAllStudents();
            request.setAttribute("students", students);

            if ("delete".equals(action)) {
                int markId = Integer.parseInt(request.getParameter("id"));
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                marksDAO.deleteMark(markId);
                response.sendRedirect(request.getContextPath() + "/admin/marks?studentId=" + studentId);
                return;
            }

            if (studentIdParam != null && !studentIdParam.isEmpty()) {
                int studentId = Integer.parseInt(studentIdParam);
                Student student = studentDAO.getStudentById(studentId);
                List<Subject> subjects = subjectDAO.getSubjectsByDeptAndSem(student.getDepartment(), student.getSemester());
                List<Mark> marks = marksDAO.getMarksByStudentId(studentId);

                request.setAttribute("selectedStudent", student);
                request.setAttribute("selectedStudentId", studentId);
                request.setAttribute("subjects", subjects);
                request.setAttribute("marks", marks);
            }

            request.getRequestDispatcher("/WEB-INF/views/admin/marks-form.jsp").forward(request, response);
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
        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            Student student = studentDAO.getStudentById(studentId);
            List<Subject> subjects = subjectDAO.getSubjectsByDeptAndSem(student.getDepartment(), student.getSemester());
            List<Mark> existingMarks = marksDAO.getMarksByStudentId(studentId);

            for (Subject subject : subjects) {
                String marksParam = request.getParameter("marks_" + subject.getSubjectId());
                String examTypeParam = request.getParameter("examType_" + subject.getSubjectId());
                String examDateParam = request.getParameter("examDate_" + subject.getSubjectId());

                if (marksParam == null || marksParam.trim().isEmpty()) continue;

                int marksObtained = Integer.parseInt(marksParam.trim());
                String examType = examTypeParam != null ? examTypeParam : "Internal";
                String examDate = examDateParam != null && !examDateParam.isEmpty() ? examDateParam : new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

                Mark existing = null;
                for (Mark m : existingMarks) {
                    if (m.getSubjectId() == subject.getSubjectId()) {
                        existing = m;
                        break;
                    }
                }

                if (existing != null) {
                    marksDAO.updateMark(existing.getMarkId(), marksObtained, examType, examDate);
                } else {
                    marksDAO.addMark(studentId, subject.getSubjectId(), marksObtained, examType, examDate);
                }
            }

            response.sendRedirect(request.getContextPath() + "/admin/marks?studentId=" + studentId);
        } catch (DatabaseException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error processing marks: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
