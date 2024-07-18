package com.dev.domain.exception.template;

import com.dev.domain.enums.MessageErrorEnum;
import com.dev.domain.exception.DomainExceptionHandler;

public class NotFoundException extends DomainExceptionHandler {
    public NotFoundException(final String param) {
        super(MessageErrorEnum.NOT_FOUND_EXCEPTION.message().concat(param));
    }
    public NotFoundException(final String process,
                             final String codeErrorMessage,
                             final String... param) {
        super(MessageErrorEnum.buildMessageWithParameters(
                process,
                codeErrorMessage,
                param
        ));
    }
}