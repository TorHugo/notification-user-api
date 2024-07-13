package com.dev.lib.exception.template;

import com.dev.lib.exception.DefaultExceptionHandler;
import com.dev.lib.exception.MessageError;

public class InvalidParameterException extends DefaultExceptionHandler {
    public InvalidParameterException(final String param) {
        super(MessageError.INVALID_PARAMETER_EXCEPTION.getMessage().concat(param));
    }
}