package com.ooad.backend.parser;

public final class Operator {
    /*
     * Operator - token type id.
     */
    public static final int TYPE_ID = 1;
    public static final String TYPE_DESC = "Operator";
    /*
     * Operator - tokens id.
     */
    public static final int PLUS_ID = 1;
    public static final int MINUS_ID = 2;
    public static final int MULTIPLY_ID = 3;
    public static final int DIVIDE_ID = 4;
    public static final int POWER_ID = 5;
    public static final int FACT_ID = 6;
    public static final int MOD_ID = 7;
    /*
     * Operator - tokens key words.
     */
    public static final String PLUS_STR = "+";
    public static final String MINUS_STR = "-";
    public static final String MULTIPLY_STR = "*";
    public static final String DIVIDE_STR = "/";
    public static final String POWER_STR = "^";
    public static final String FACT_STR = "!";
    public static final String MOD_STR = "%";
    /*
     * Operator - tokens description.
     */
    public static final String PLUS_DESC = "addition";
    public static final String MINUS_DESC = "subtraction";
    public static final String MULTIPLY_DESC = "multiplication";
    public static final String DIVIDE_DESC = "division";
    public static final String POWER_DESC = "exponentiation";
    public static final String FACT_DESC = "factorial";
    public static final String MOD_DESC = "modulo function";
}
