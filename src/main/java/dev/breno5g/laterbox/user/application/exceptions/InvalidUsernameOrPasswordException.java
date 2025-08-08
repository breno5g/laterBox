package dev.breno5g.laterbox.user.application.exceptions;

public class InvalidUsernameOrPasswordException extends Exception{
    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }
}
