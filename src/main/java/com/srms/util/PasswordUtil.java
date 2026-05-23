package com.srms.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
    if (hashedPassword == null) {
        return false;
    }
    // Try BCrypt first
    if (hashedPassword.startsWith("$2a$") || hashedPassword.startsWith("$2b$")) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            System.out.println("BCrypt error: " + e.getMessage());
            return false;
        }
    }
    // Fall back to plain text comparison
    return plainPassword.equals(hashedPassword);
}
}
