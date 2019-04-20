package com.ooad.backend.parser;

import java.util.regex.Pattern;

public final class Parser {

    /**
     * FOUND / NOT_FOUND
     * used for matching purposes
     */
    public static final int NOT_FOUND = -1;
    public static final int FOUND = 0;

    /**
     * Function used to introduce regexp matching.
     *
     * @param str         String
     * @param pattern     Pattern (regexp)
     *
     * @return            True if pattern matches entirely, False otherwise
     */
    public static final boolean regexMatch(String str, String pattern){
        return Pattern.matches(pattern, str);
    }
}
