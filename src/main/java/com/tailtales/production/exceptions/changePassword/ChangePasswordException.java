package com.tailtales.production.exceptions.changePassword;

public class ChangePasswordException {

    public static class PasswordChangeException extends RuntimeException {
        public PasswordChangeException(String message) {
            super(message);
        }
    }

    public static class IncorrectPasswordException extends PasswordChangeException {
        public IncorrectPasswordException() {
            super("Incorrect old password provided");
        }
    }

    public static class UserNotFoundException extends PasswordChangeException {
        public UserNotFoundException() {
            super("User not found");
        }
    }
}
