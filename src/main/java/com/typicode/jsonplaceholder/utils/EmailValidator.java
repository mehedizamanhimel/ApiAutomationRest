package com.typicode.jsonplaceholder.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Consolidated, correct email validation utility.
 * Replaces the broken verifyEmail() that always returned true.
 */
public class EmailValidator {

    private static final Logger logger = LogManager.getLogger(EmailValidator.class);
    // RFC-5322 simplified pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private EmailValidator() {}

    /**
     * Validates every email in the list.
     * @return true only if ALL emails are valid, false if any fail.
     */
    public static boolean validateAll(List<String> emails) {
        for (String email : emails) {
            boolean valid = EMAIL_PATTERN.matcher(email).matches();
            logger.info("Email: {} -> valid: {}", email, valid);
            if (!valid) {
                return false;
            }
        }
        return true;
    }
}
