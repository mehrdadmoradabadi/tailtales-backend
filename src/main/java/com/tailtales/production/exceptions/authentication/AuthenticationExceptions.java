package com.tailtales.production.exceptions.authentication;
import org.springframework.security.core.AuthenticationException;
public class AuthenticationExceptions extends AuthenticationException {
    public AuthenticationExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
