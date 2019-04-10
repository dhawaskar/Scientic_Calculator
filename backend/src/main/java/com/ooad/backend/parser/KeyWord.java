package com.ooad.backend.parser;

import com.ooad.backend.constants.ConstantValue;

public class KeyWord {
    String wordString;
    int	wordId;
    int wordTypeId;
    String description;

    public KeyWord() {
        wordString = "";
        wordId = ConstantValue.NaN;
        wordTypeId = ConstantValue.NaN;
        description = "";
    }
    /**
     * Constructor - creates key words form wordStrin wordId
     * and wordTypId
     *
     * @param wordString   the word string (refers to below interfaces)
     * @param wordId       the word identifier (refers to below interfaces)
     * @param wordTypeId   the word type (refers to below interfaces)
     */
    public KeyWord(String wordString, int wordId, int wordTypeId) {
        this.wordString = wordString;
        this.wordId = wordId;
        this.wordTypeId = wordTypeId;
    }
    public KeyWord(String wordString, String description, int wordId, int wordTypeId) {
        this.wordString = wordString;
        this.wordId = wordId;
        this.wordTypeId = wordTypeId;
        this.description = description;
    }

    public String getWordString() {
        return wordString;
    }

    public int getWordId() {
        return wordId;
    }

    public int getWordTypeId() {
        return wordTypeId;
    }

    public String getDescription() {
        return description;
    }
}
