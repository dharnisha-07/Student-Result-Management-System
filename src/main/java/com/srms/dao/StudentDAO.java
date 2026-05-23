package com.srms.dao;

import com.srms.model.Student;
import com.srms.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public int addStudent(String name, String email, String department, int semester, String dob, String phone, int userId) throws DatabaseException {
        String sql = "INSERT INTO students (user_id, name, email, department, semester, dob, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, department);
            pstmt.setInt(5, semester);
            pstmt.setString(6, dob);
            pstmt.setString(7, phone);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while adding student: " + e.getMessage(), e);
        }
    }

    public void updateStudent(int studentId, String name, String email, String department, int semester, String dob, String phone) throws DatabaseException {
        String sql = "UPDATE students SET name = ?, email = ?, department = ?, semester = ?, dob = ?, phone = ? WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, department);
            pstmt.setInt(4, semester);
            pstmt.setString(5, dob);
            pstmt.setString(6, phone);
            pstmt.setInt(7, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while updating student: " + e.getMessage(), e);
        }
    }

    public void deleteStudent(int studentId) throws DatabaseException {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while deleting student: " + e.getMessage(), e);
        }
    }

    public Student getStudentById(int studentId) throws DatabaseException {
        String sql = "SELECT student_id, user_id, name, email, department, semester, dob, phone FROM students WHERE student_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToStudent(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching student: " + e.getMessage(), e);
        }
    }

    public Student getStudentByUserId(int userId) throws DatabaseException {
        String sql = "SELECT student_id, user_id, name, email, department, semester, dob, phone FROM students WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToStudent(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching student by user ID: " + e.getMessage(), e);
        }
    }

    public List<Student> getAllStudents() throws DatabaseException {
        String sql = "SELECT student_id, user_id, name, email, department, semester, dob, phone FROM students ORDER BY name";
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching all students: " + e.getMessage(), e);
        }
        return students;
    }

    public int getTotalCount() throws DatabaseException {
        String sql = "SELECT COUNT(*) AS total FROM students";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while counting students: " + e.getMessage(), e);
        }
    }

    public List<Student> searchByDeptAndSem(String department, int semester) throws DatabaseException {
        String sql = "SELECT student_id, user_id, name, email, department, semester, dob, phone FROM students WHERE department = ? AND semester = ? ORDER BY name";
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, department);
            pstmt.setInt(2, semester);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRowToStudent(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error while searching students: " + e.getMessage(), e);
        }
        return students;
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setPersonId(rs.getInt("student_id"));
        student.setUserId(rs.getInt("user_id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setDepartment(rs.getString("department"));
        student.setSemester(rs.getInt("semester"));
        student.setDob(rs.getString("dob"));
        student.setPhone(rs.getString("phone"));
        return student;
    }
}
