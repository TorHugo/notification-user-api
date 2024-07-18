package com.dev.domain.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * Enumerates standard error messages used throughout the application.
 * Each error type is associated with a unique code and a descriptive message.
 * <p>
 * The purpose of this enum is to provide a centralized way to manage error messages,
 * making it easier to maintain consistency across the application and to localize messages
 * when needed.
 * </p>
 * <p>
 * Error messages can be built dynamically using the provided methods, allowing for
 * customization based on specific contexts or parameters.
 * </p>
 *
 * @author Victor Hugo
 */
public enum MessageErrorEnum {
    DOMAIN_EXCEPTION("E0001", "Domain Exception"),
    APPLICATION_EXCEPTION("E0002", "Application Parameter"),
    NOT_FOUND_EXCEPTION("E0003", "Object Not Found"),
    INVALID_PARAMETER_EXCEPTION("E0004", "Invalid Parameter"),
    OBJECT_ALREADY_EXISTS_EXCEPTION("E0005", "Object already exists"),
    OBJECT_IS_NULL_OR_EMPTY_EXCEPTION("E0006", "Object is null or empty");

    private final String code;
    private final String message;

    MessageErrorEnum(final String code,
                     final String message) {
        this.code = code;
        this.message = message;
    }

    public String message() {
        return message;
    }

    public String code(){
        return code;
    }

    /**
     * Builds a message combining the error message from the specified code with the given process.
     *
     * @param currentCode The code of the error message to use.
     * @param process     Additional context or process description.
     * @return The constructed message.
     */
    public static String buildMessage(final String currentCode,
                                      final String process) {
        final var messageException = getMessageByCode(currentCode);
        return messageException.message.concat(" |  ").concat(process);
    }

    /**
     * Builds a message combining the error message from the specified code with the given process and parameters.
     *
     * @param currentCode      The code of the error message to use.
     * @param process          Additional context or process description.
     * @param parameters       Parameters to include in the message.
     * @return The constructed message.
     */
    public static String buildMessageWithParameters(final String currentCode,
                                                    final String process,
                                                    final String... parameters) {
        final var messageException = buildMessage(currentCode, process);
        return messageException + concatenateParameters(parameters);
    }

    /**
     * Retrieves the {@link MessageErrorEnum} instance corresponding to the given code.
     *
     * @param currentCode The code of the error message to retrieve.
     * @return The {@link MessageErrorEnum} instance, or throws an exception if none matches.
     */
    public static MessageErrorEnum getMessageByCode(final String currentCode) {
        return Arrays.stream(values())
                .filter(value -> Objects.equals(currentCode, value.code))
                .findFirst()
                .orElseThrow();
    }

    /**
     * Concatenates the given parameters into a single string, separated by " | ".
     *
     * @param parameters The parameters to concatenate.
     * @return The concatenated string.
     */
    private static String concatenateParameters(final String... parameters) {
        if (parameters == null || parameters.length == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (String parameter : parameters) {
            sb.append(" | ").append(parameter);
        }
        return sb.toString();
    }
}
