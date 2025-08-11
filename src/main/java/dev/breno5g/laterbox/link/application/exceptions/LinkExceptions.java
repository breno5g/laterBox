package dev.breno5g.laterbox.link.application.exceptions;

public interface LinkExceptions {
    String LINK_ALREADY_EXISTS = "Link already exists";
    LinkAlreadyExistsException LINK_ALREADY_EXISTS_EXCEPTION = new LinkAlreadyExistsException(LINK_ALREADY_EXISTS);

    String LINK_NOT_FOUND = "Link not found";
    LinkNotFoundException LINK_NOT_FOUND_EXCEPTION = new LinkNotFoundException(LINK_NOT_FOUND);
}
