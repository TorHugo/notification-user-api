package com.dev.domain.exception.template;

import com.dev.domain.enums.MessageErrorEnum;
import com.dev.domain.exception.DomainExceptionHandler;

public class InvalidArgumentException extends DomainExceptionHandler {
    public InvalidArgumentException(final String param) {
        super(MessageErrorEnum.INVALID_PARAMETER_EXCEPTION.message().concat(param));
    }
    public InvalidArgumentException(final String codeErrorMessage,
                                    final String process,
                                    final String... param) {
        super(MessageErrorEnum.buildMessageWithParameters(
                codeErrorMessage,
                process,
                param
        ));
    }
}