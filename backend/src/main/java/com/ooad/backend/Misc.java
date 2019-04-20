package com.ooad.backend;

import com.ooad.backend.parser.KeyWord;

import java.util.Comparator;

/**
 * Comparator for key word list sorting by
 * descending key word length
 * .
 * This king of sorting is used while tokenizing
 * (best match)
 */
class DescKwLenComparator implements Comparator<KeyWord> {
    /**
     *
     */
    public int compare(KeyWord kw1, KeyWord kw2) {
        int l1 = kw1.getWordString().length();
        int l2 = kw2.getWordString().length();
        return l2-l1;
    }
}

/**
 * Comparator for key word list sorting by key word string.
 * This king of sorting is used while checking the syntax
 * (duplicated key word error)
 */
class KwStrComparator implements Comparator<KeyWord> {
    /**
     *
     */
    public int compare(KeyWord kw1, KeyWord kw2) {
        String s1 = kw1.getWordString();
        String s2 = kw2.getWordString();
        return s1.compareTo(s2);
    }
}
/**
 * Internal token class
 * which is used with stack while
 * evaluation of tokens levels
 */

class TokenStackElement {
    int tokenIndex;
    int tokenId;
    int tokenTypeId;
    int tokenLevel;
    boolean precedingFunction;
}

class SyntaxStackElement {
    String tokenStr;
    int tokenLevel;
    SyntaxStackElement(String tokenStr, int tokenLevel) {
        this.tokenStr = tokenStr;
        this.tokenLevel = tokenLevel;
    }
}
