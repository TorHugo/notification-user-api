package com.dev.domain.exception.template;

import com.dev.domain.enums.MessageErrorEnum;
import com.dev.domain.exception.DomainExceptionHandler;

public class ObjectIsNullOrEmptyException extends DomainExceptionHandler {
    public ObjectIsNullOrEmptyException(final String param) {
        super(MessageErrorEnum.OBJECT_IS_NULL_OR_EMPTY_EXCEPTION.message().concat(param));
    }
}

