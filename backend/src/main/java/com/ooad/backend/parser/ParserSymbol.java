package com.ooad.backend.parser;

public final class ParserSymbol {
    /*
     * ParserSymbol - reg exp patterns.
     */
    public static final String DIGIT		= "[0-9]";
    public static final String DIGIT19		= "[1-9]";
    public static final String DIGITS		= DIGIT + "(" + DIGIT + ")*";
    public static final String INTEGER		= "(0|" + DIGIT19 + "(" + DIGIT + ")*" + ")";
    public static final String REAL			= "(0\\." + DIGITS + "|" + INTEGER + "\\." + DIGITS + ")";
    public static final String NUMBER		= "(" + REAL + "|" + INTEGER + ")";
    public static final String NUMBER_CONST	= "[+-]?" + NUMBER + "([eE][+-]?" + INTEGER + ")?";
    public static final String nameOnlyTokenRegExp = "([a-zA-Z_])+([a-zA-Z0-9_])*";
    public static final String nameTokenRegExp = "(\\s)*" + nameOnlyTokenRegExp + "(\\s)*";
    public static final String paramsTokenRegeExp = "(\\s)*\\(" + "(" + nameTokenRegExp + ",(\\s)*)*" + nameTokenRegExp + "\\)(\\s)*";
    public static final String constArgDefStrRegExp = nameTokenRegExp + "=" + "(\\s)*(.)+(\\s)*";
    public static final String functionDefStrRegExp = nameTokenRegExp + paramsTokenRegeExp + "=" + "(\\s)*(.)+(\\s)*";
    public static final String function1ArgDefStrRegExp = nameTokenRegExp + "(\\s)*\\(" + nameTokenRegExp + "(\\s)*\\)(\\s)*" + "=" + "(\\s)*(.)+(\\s)*";
    /*
     * ParserSymbol - token type id.
     */
    public static final int TYPE_ID 						= 20;
    public static final String TYPE_DESC					= "Parser Symbol";
    /*
     * ParserSymbol - tokens id.
     */
    public static final int LEFT_PARENTHESES_ID 			= 1;
    public static final int RIGHT_PARENTHESES_ID			= 2;
    public static final int COMMA_ID						= 3;
    public static final int NUMBER_ID						= 1;
    public static final int NUMBER_TYPE_ID					= 0;
    /*
     * ParserSymbol - tokens key words.
     */
    public static final String LEFT_PARENTHESES_STR 		= "(";
    public static final String RIGHT_PARENTHESES_STR		= ")";
    public static final String COMMA_STR					= ",";
    public static final String SEMI_STR						= ";";
    public static final String NUMBER_STR					= "_num_";
    public static final String NUMBER_REG_EXP				= NUMBER_CONST;
    /*
     * ParserSymbol - tokens description.
     */
    public static final String LEFT_PARENTHESES_DESC 		= "left parentheses";
    public static final String RIGHT_PARENTHESES_DESC		= "right parentheses";
    public static final String COMMA_DESC					= "comma (function parameters)";
    public static final String SEMI_DESC					= "semicolon (function parameters)";
    public static final String NUMBER_DESC					= "decimal number";
    public static final String NUMBER_REG_DESC				= "regullar expression for decimal numbers";
}
