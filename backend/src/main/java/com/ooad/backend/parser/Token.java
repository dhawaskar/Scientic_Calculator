package com.ooad.backend.parser;


import com.ooad.backend.constants.ConstantValue;

/**
 * Token recognized by parser after string tokenization process.
 */

public class Token {
    /**
     * Indicator that token was not matched
     */
    public static final int NOT_MATCHED = ConstantValue.NaN;

    /**
     * String token
     */
    public String tokenStr;

    /**
     * Key word string (if matched)
     */
    public String keyWord;

    /**
     * Token identifier
     */
    public int tokenId;

    /**
     * Token type
     */
    public int tokenTypeId;

    /**
     * Token level
     */
    public int tokenLevel;

    /**
     * Token value if number
     */
    public double tokenValue;

    public Token() {
        tokenStr = "";
        keyWord = "";
        tokenId = NOT_MATCHED;
        tokenTypeId = NOT_MATCHED;
        tokenLevel = -1;
        tokenValue = Double.NaN;
    }

    public Token clone() {
        Token token = new Token();
        token.keyWord = keyWord;
        token.tokenStr = tokenStr;
        token.tokenId = tokenId;
        token.tokenLevel = tokenLevel;
        token.tokenTypeId = tokenTypeId;
        token.tokenValue = tokenValue;
        return token;
    }
}
