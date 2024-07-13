package com.dev.lib.exception.template;

import com.dev.lib.exception.DefaultExceptionHandler;
import com.dev.lib.exception.MessageError;

public class ObjectIsNullOrEmptyException extends DefaultExceptionHandler {
    public ObjectIsNullOrEmptyException(final String param) {
        super(MessageError.OBJECT_IS_NULL_OR_EMPTY_EXCEPTION.getMessage().concat(param));
    }
}

