package com.srms.dao;

import com.srms.model.User;
import com.srms.exception.DatabaseException;
import com.srms.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public int validateLogin(String username, String password) throws DatabaseException {
    String query = "SELECT user_id, password_hash FROM users WHERE username = ?";
    try (Connection conn = DBConnection.getInstance().getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, username);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String storedPassword = rs.getString("password_hash");
                System.out.println("=== LOGIN DEBUG ===");
                System.out.println("Username entered: " + username);
                System.out.println("Password entered: " + password);
                System.out.println("Stored value: " + storedPassword);
                System.out.println("Direct match: " + password.equals(storedPassword));
                System.out.println("BCrypt match: " + PasswordUtil.verifyPassword(password, storedPassword));
                System.out.println("==================");
                
                if (PasswordUtil.verifyPassword(password, storedPassword)) {
                    return rs.getInt("user_id");
                }
            } else {
                System.out.println("=== NO USER FOUND FOR: " + username + " ===");
            }
        }
    } catch (SQLException e) {
        throw new DatabaseException("Error validating login: " + e.getMessage(), e);
    }
    return -1;
}

    public String getUserRole(String username) throws DatabaseException {
        String sql = "SELECT role FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching user role: " + e.getMessage(), e);
        }
    }

    public User getUserByUsername(String username) throws DatabaseException {
        String sql = "SELECT user_id, username, password_hash, role FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching user: " + e.getMessage(), e);
        }
    }

    public User getUserById(int userId) throws DatabaseException {
        String sql = "SELECT user_id, username, password_hash, role FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching user by ID: " + e.getMessage(), e);
        }
    }

    public void deleteUser(int userId) throws DatabaseException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while deleting user: " + e.getMessage(), e);
        }
    }

    public void addUser(String username, String password, String role) throws DatabaseException {
        String sql = "INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, PasswordUtil.hashPassword(password));
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while adding user: " + e.getMessage(), e);
        }
    }

    public void updatePassword(int userId, String newPassword) throws DatabaseException {
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, PasswordUtil.hashPassword(newPassword));
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error while updating password: " + e.getMessage(), e);
        }
    }
}
