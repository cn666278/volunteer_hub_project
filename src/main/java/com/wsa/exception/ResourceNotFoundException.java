package com.wsa.exception;

/**
 * Custom exception to handle scenarios where a requested resource is not found.
 * This exception extends RuntimeException, allowing it to be used for unchecked exceptions.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Serial version UID for serialization

    /**
     * Constructor for ResourceNotFoundException.
     * @param message The error message to be displayed when the exception is thrown.
     */
    public ResourceNotFoundException(String message) {
        super(message); // Pass the error message to the superclass constructor
    }
}
