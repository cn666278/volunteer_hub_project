package com.wsa.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Utility class for handling password-related operations.
 * This class provides methods to generate and verify BCrypt hashed passwords using Spring Security.
 */
public class PasswordUtils {

    // PasswordEncoder instance using BCrypt for secure password hashing
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Generates a BCrypt hashed password.
     * This method takes a raw password and returns its BCrypt hash for secure storage.
     *
     * @param rawPassword The raw password to hash.
     * @return The BCrypt hashed password.
     */
    public static String generateBCryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Main method for testing purposes.
     * Demonstrates how to generate a BCrypt hashed password.
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Example usage
        String rawPassword = "123456";
        String hashedPassword = generateBCryptPassword(rawPassword);
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
