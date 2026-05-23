package com.srms.dao;

import com.srms.model.Subject;
import com.srms.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    public int addSubject(String subjectName, String department, int semester, int maxMarks) throws DatabaseException {
        String sql = "INSERT INTO subjects (subject_name, department, semester, max_marks) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, subjectName);
            pstmt.setString(2, department);
            pstmt.setInt(3, semester);
            pstmt.setInt(4, maxMarks);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while adding subject: " + e.getMessage(), e);
        }
    }

    public void updateSubject(int subjectId, String subjectName, String department, int semester, int maxMarks) throws DatabaseException {
        String sql = "UPDATE subjects SET subject_name = ?, department = ?, semester = ?, max_marks = ? WHERE subject_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subjectName);
            pstmt.setString(2, department);
            pstmt.setInt(3, semester);
            pstmt.setInt(4, maxMarks);
            pstmt.setInt(5, subjectId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while updating subject: " + e.getMessage(), e);
        }
    }

    public void deleteSubject(int subjectId) throws DatabaseException {
        String sql = "DELETE FROM subjects WHERE subject_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, subjectId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while deleting subject: " + e.getMessage(), e);
        }
    }

    public List<Subject> getAllSubjects() throws DatabaseException {
        String sql = "SELECT subject_id, subject_name, department, semester, max_marks FROM subjects ORDER BY subject_name";
        List<Subject> subjects = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                subjects.add(mapRowToSubject(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching all subjects: " + e.getMessage(), e);
        }
        return subjects;
    }

    public int getTotalCount() throws DatabaseException {
        String sql = "SELECT COUNT(*) AS total FROM subjects";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while counting subjects: " + e.getMessage(), e);
        }
    }

    public List<Subject> getSubjectsByDeptAndSem(String department, int semester) throws DatabaseException {
        String sql = "SELECT subject_id, subject_name, department, semester, max_marks FROM subjects WHERE department = ? AND semester = ? ORDER BY subject_name";
        List<Subject> subjects = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department);
            pstmt.setInt(2, semester);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    subjects.add(mapRowToSubject(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching subjects by department and semester: " + e.getMessage(), e);
        }
        return subjects;
    }

    public Subject getSubjectById(int subjectId) throws DatabaseException {
        String sql = "SELECT subject_id, subject_name, department, semester, max_marks FROM subjects WHERE subject_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, subjectId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToSubject(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching subject: " + e.getMessage(), e);
        }
    }

    private Subject mapRowToSubject(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectId(rs.getInt("subject_id"));
        subject.setSubjectName(rs.getString("subject_name"));
        subject.setDepartment(rs.getString("department"));
        subject.setSemester(rs.getInt("semester"));
        subject.setMaxMarks(rs.getInt("max_marks"));
        return subject;
    }
}
