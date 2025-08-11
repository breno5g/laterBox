package dev.breno5g.laterbox.config.exceptions;

public interface SecurityExceptions {
    String INVALID_OR_EMPTY_TOKEN_MSG = "Invalid or empty token";
    InvalidOrEmptyToken INVALID_OR_EMPTY_TOKEN_EXCEPTION = new InvalidOrEmptyToken(INVALID_OR_EMPTY_TOKEN_MSG);
}

