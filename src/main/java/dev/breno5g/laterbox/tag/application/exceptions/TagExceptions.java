package dev.breno5g.laterbox.tag.application.exceptions;

public interface TagExceptions {
    String TAG_ALREADY_EXISTS_EXCEPTION_MSG = "Tag already exists";
    TagAlreadyExistsException TAG_ALREADY_EXISTS_EXCEPTION = new TagAlreadyExistsException(TAG_ALREADY_EXISTS_EXCEPTION_MSG);

    String TAG_NOT_FOUND_EXCEPTION_MSG = "Tag not found";
    TagNotFoundException TAG_NOT_FOUND_EXCEPTION = new TagNotFoundException(TAG_NOT_FOUND_EXCEPTION_MSG);
}
