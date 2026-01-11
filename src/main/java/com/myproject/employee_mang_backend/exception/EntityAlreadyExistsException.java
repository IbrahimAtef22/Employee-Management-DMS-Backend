package com.myproject.employee_mang_backend.exception;

/**
 * That can be thrown when user tries to add an entity that already exists
 */
public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
