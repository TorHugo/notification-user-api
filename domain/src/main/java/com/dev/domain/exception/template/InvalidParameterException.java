package com.dev.domain.exception.template;

import com.dev.domain.enums.MessageErrorEnum;
import com.dev.domain.exception.DomainExceptionHandler;

public class InvalidParameterException extends DomainExceptionHandler {
    public InvalidParameterException(final String param) {
        super(MessageErrorEnum.INVALID_PARAMETER_EXCEPTION.message().concat(param));
    }
    public InvalidParameterException(final String process,
                                     final String codeErrorMessage,
                                     final String... param) {
        super(MessageErrorEnum.buildMessageWithParameters(
                process,
                codeErrorMessage,
                param
        ));
    }
}