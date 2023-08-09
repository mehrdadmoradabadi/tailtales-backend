package com.tailtales.production.exceptions.login;

public class Exceptions {
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }

    }

    public static class InvalidUserObjectException extends RuntimeException {
        public InvalidUserObjectException(String message) {
            super(message);
        }
    }
}
