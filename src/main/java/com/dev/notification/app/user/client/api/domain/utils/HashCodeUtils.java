package com.dev.notification.app.user.client.api.domain.utils;

import java.util.Random;

public class HashCodeUtils {
    private HashCodeUtils() {}
    private static final Random random = new Random();
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String SPECIAL_CHARACTERS = "*-@#$";

    public static String create(final Integer digits) {
        final var hash = new StringBuilder();
        for (int i = 0; i < digits; i++) {
            int randomNumber = random.nextInt(10);
            hash.append(randomNumber);
        }
        return hash.toString();
    }

    /**
     * Generates a secure password consisting of exactly 9 characters, adhering to the following criteria:
     * <ul>
     *     <li>At least one uppercase letter.</li>
     *     <li>At least one lowercase letter.</li>
     *     <li>At least one special character (!@#$%^&*()_-+={}[]|:;<>,.?/).</li>
     * </ul>
     * The remaining characters can be either uppercase or lowercase letters.
     *
     * @return A string representing the generated password.
     */
    public static String generateValidPassword() {
        final var upperCaseIndex = random.nextInt(UPPER_CASE.length());
        final var lowerCaseIndex = random.nextInt(LOWER_CASE.length());
        final var specialCharacterIndex = random.nextInt(SPECIAL_CHARACTERS.length());

        final var finalPassword = new StringBuilder(9);
        finalPassword.append(UPPER_CASE.charAt(upperCaseIndex))
                .append(LOWER_CASE.charAt(lowerCaseIndex))
                .append(SPECIAL_CHARACTERS.charAt(specialCharacterIndex));

        for (int i = 0; i < 6; i++) {
            int typeOfCharacter = random.nextBoolean() ? 0 : 1;
            if (typeOfCharacter == 0) {
                finalPassword.append(UPPER_CASE.charAt(random.nextInt(UPPER_CASE.length())));
            } else {
                finalPassword.append(LOWER_CASE.charAt(random.nextInt(LOWER_CASE.length())));
            }
        }

        return finalPassword.toString();
    }
}
