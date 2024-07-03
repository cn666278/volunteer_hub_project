package com.wsa.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Generates a BCrypt hashed password.
     *
     * @param rawPassword the raw password to hash
     * @return the hashed password
     */
    public static String generateBCryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public static void main(String[] args) {
        // Example usage
        String rawPassword = "password";
        String hashedPassword = generateBCryptPassword(rawPassword);
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
