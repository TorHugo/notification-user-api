package com.dev.lib.exception.template;

import com.dev.lib.exception.DefaultExceptionHandler;
import com.dev.lib.enums.MessageErrorEnum;

public class InvalidParameterException extends DefaultExceptionHandler {
    public InvalidParameterException(final String param) {
        super(MessageErrorEnum.INVALID_PARAMETER_EXCEPTION.getMessage().concat(param));
    }
}