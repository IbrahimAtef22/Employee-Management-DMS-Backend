package com.myproject.employee_mang_backend.exception;
/**
 * That can be thrown when user tries to update, delete or get an entity that does not exist
 */
public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }

}
