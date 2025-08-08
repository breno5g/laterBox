package dev.breno5g.laterbox.user.application.exceptions;

public interface UserExceptions {
    String USER_ALREADY_EXISTS_EXCEPTION_MSG = "User already exists";
    UserAlreadyExistsException USER_ALREADY_EXISTS_EXCEPTION = new UserAlreadyExistsException(USER_ALREADY_EXISTS_EXCEPTION_MSG);

    String USER_NOT_FOUND_EXCEPTION_MSG = "User not found";
    UserNotFoundException USER_NOT_FOUND_EXCEPTION = new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_MSG);

    String USERNAME_OR_PASSWORD_NOT_MATCH_EXCEPTION_MSG = "Invalid username or password";
    InvalidUsernameOrPasswordException INVALID_USERNAME_OR_PASSWORD_EXCEPTION = new InvalidUsernameOrPasswordException(USERNAME_OR_PASSWORD_NOT_MATCH_EXCEPTION_MSG);
}
