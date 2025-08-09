package dev.breno5g.laterbox.user.controller;

import dev.breno5g.laterbox.user.application.exceptions.UserAlreadyExistsException;
import dev.breno5g.laterbox.user.application.exceptions.UserExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionAdvice {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return new ResponseEntity<>("message: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UsernameNotFoundException ex) {
        return new ResponseEntity<>("message: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

     @ExceptionHandler(BadCredentialsException.class)
     public ResponseEntity<String> handleBadCredentials() {
        return new ResponseEntity<>("message: " + UserExceptions.INVALID_USERNAME_OR_PASSWORD_EXCEPTION.getMessage(), HttpStatus.UNAUTHORIZED);
     }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal error: " + ex.getMessage());
    }
}
