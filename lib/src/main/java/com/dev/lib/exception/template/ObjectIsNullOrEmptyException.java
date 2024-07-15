package com.dev.lib.exception.template;

import com.dev.lib.exception.DefaultExceptionHandler;
import com.dev.lib.enums.MessageErrorEnum;

public class ObjectIsNullOrEmptyException extends DefaultExceptionHandler {
    public ObjectIsNullOrEmptyException(final String param) {
        super(MessageErrorEnum.OBJECT_IS_NULL_OR_EMPTY_EXCEPTION.getMessage().concat(param));
    }
}

