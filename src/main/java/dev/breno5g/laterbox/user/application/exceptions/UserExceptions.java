package dev.breno5g.laterbox.user.application.exceptions;

public interface UserExceptions {
    String USER_ALREADY_EXISTS_EXCEPTION_MSG = "User already exists";
    UserAlreadyExistsException USER_ALREADY_EXISTS_EXCEPTION = new UserAlreadyExistsException(USER_ALREADY_EXISTS_EXCEPTION_MSG);


}
