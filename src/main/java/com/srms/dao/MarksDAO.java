package com.srms.dao;

import com.srms.model.Mark;
import com.srms.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {

    public int addMark(int studentId, int subjectId, int marksObtained, String examType, String examDate) throws DatabaseException {
        String sql = "INSERT INTO marks (student_id, subject_id, marks_obtained, exam_type, exam_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, subjectId);
            pstmt.setInt(3, marksObtained);
            pstmt.setString(4, examType);
            pstmt.setString(5, examDate);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while adding marks: " + e.getMessage(), e);
        }
    }

    public void updateMark(int markId, int marksObtained, String examType, String examDate) throws DatabaseException {
        String sql = "UPDATE marks SET marks_obtained = ?, exam_type = ?, exam_date = ? WHERE mark_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, marksObtained);
            pstmt.setString(2, examType);
            pstmt.setString(3, examDate);
            pstmt.setInt(4, markId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while updating marks: " + e.getMessage(), e);
        }
    }

    public void deleteMark(int markId) throws DatabaseException {
        String sql = "DELETE FROM marks WHERE mark_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, markId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while deleting marks: " + e.getMessage(), e);
        }
    }

    public List<Mark> getMarksByStudent(int studentId) throws DatabaseException {
        String sql = "SELECT mark_id, student_id, subject_id, marks_obtained, exam_type, exam_date FROM marks WHERE student_id = ? ORDER BY exam_date";
        List<Mark> marks = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    marks.add(mapRowToMark(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching marks for student: " + e.getMessage(), e);
        }
        return marks;
    }

    public Mark getMarkById(int markId) throws DatabaseException {
        String sql = "SELECT mark_id, student_id, subject_id, marks_obtained, exam_type, exam_date FROM marks WHERE mark_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, markId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToMark(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching mark by ID: " + e.getMessage(), e);
        }
    }

    public List<Mark> getMarksByStudentId(int studentId) throws DatabaseException {
        return getMarksByStudent(studentId);
    }

    public List<Mark> getResultsByStudentId(int studentId) throws DatabaseException {
        return getMarksByStudent(studentId);
    }

    private Mark mapRowToMark(ResultSet rs) throws SQLException {
        Mark mark = new Mark();
        mark.setMarkId(rs.getInt("mark_id"));
        mark.setStudentId(rs.getInt("student_id"));
        mark.setSubjectId(rs.getInt("subject_id"));
        mark.setMarksObtained(rs.getInt("marks_obtained"));
        mark.setExamType(rs.getString("exam_type"));
        mark.setExamDate(rs.getString("exam_date"));
        return mark;
    }
}
