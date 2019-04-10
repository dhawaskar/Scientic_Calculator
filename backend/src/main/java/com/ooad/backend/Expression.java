package com.ooad.backend;

import com.ooad.backend.constants.ConstantValue;
import com.ooad.backend.functions.MathFunctions;
import com.ooad.backend.parser.*;

import java.util.ArrayList;
import java.util.Stack;

public class Expression {

    /**
     * FOUND / NOT_FOUND
     * used for matching purposes
     */
    static final int NOT_FOUND = Parser.NOT_FOUND;
    static final int FOUND = Parser.FOUND;
    /**
     * Status of the Expression syntax
     */
    public static final boolean NO_SYNTAX_ERRORS = true;
    public static final boolean SYNTAX_ERROR_OR_STATUS_UNKNOWN = false;
    /**
     * Expression string (for example: "sin(x)+cos(y)")
     */
    String expressionString;
    private String description;

    /**
     * Flag used internally to mark started recursion
     * call on the current object, necessary to
     * avoid infinite loops while recursive syntax
     * checking (i.e. f -> g and g -> f)
     * or marking modified flags on the expressions
     * related to this expression.
     * <p>
     * //     * @see setExpressionModifiedFlag()
     * //     * @see checkSyntax()
     */
    private boolean recursionCallPending;
    /**
     * if true then new tokenizing is required
     * (the initialTokens list needs to be updated)
     */
    private boolean expressionWasModified;
    /**
     * Status of the expression syntax
     * <p>
     * Please referet to the:
     * - NO_SYNTAX_ERRORS
     * - SYNTAX_ERROR_OR_STATUS_UNKNOWN
     */
    private boolean syntaxStatus;
    /**
     * Message after checking the syntax
     */
    private String errorMessage;
    /**
     * List of key words known by the parser
     */
    private ArrayList<KeyWord> keyWordsList;
    /**
     * List of expression tokens (words).
     * Token class defines all needed
     * attributes for recognizing the structure of
     * arithmetic expression. This is the key result when
     * initial parsing is finished (tokenizeExpressionString() - method).
     * Token keeps information about:
     * - token type (for example: function, operator, argument, number, etc...)
     * - token identifier within given type (sin, cos, operaotr, etc...)
     * - token value (if token is a number)
     * - token level - key information regarding sequence (order) of further parsing
     */
    private ArrayList<Token> initialTokens;
    /**
     * the initialTokens list keeps unchanged information about
     * found tokens.
     * <p>
     * While parsing the tokensList is used. The tokensList is the same
     * as initialTokens list at the beginning of the calculation process.
     * Each math operation changes tokens list - it means that
     * tokens are parameters when performing math operation
     * and the result is also presented as token (usually as a number token)
     * At the end of the calculation the tokensList should contain only one
     * element - the result of all calculations.
     */
    private ArrayList<Token> tokensList;
    /**
     * Verbose mode prints processing info
     * calls System.out.print* methods
     */
    private boolean verboseMode;

    public Expression(String expressionString) {
        verboseMode = false;
        this.expressionString = expressionString;
        expressionWasModified = true;
        syntaxStatus = SYNTAX_ERROR_OR_STATUS_UNKNOWN;
        errorMessage = "Syntax status unknown.";
    }

    /**
     * Checks syntax of the expression string.
     *
     * @return true if syntax is ok
     */

    private boolean checkSyntax() {
        boolean syntax = checkSyntax("[" + expressionString + "] ", false);
        return syntax;
    }

    /**
     * Checking the syntax (recursively).
     *
     * @param level string representing the recurssion level.
     * @return true if syntax was correct,
     * otherwise returns false.
     */
    private boolean checkSyntax(String level, boolean functionWithBodyExt) {
        if ((expressionWasModified == false) && (syntaxStatus == NO_SYNTAX_ERRORS)) {
            errorMessage = level + "already checked - no errors!\n";
            recursionCallPending = false;
            return NO_SYNTAX_ERRORS;
        }
        if (functionWithBodyExt) {
            syntaxStatus = NO_SYNTAX_ERRORS;
            recursionCallPending = false;
            expressionWasModified = false;
            errorMessage = errorMessage + level + "function with extended body - assuming no errors.\n";
            return NO_SYNTAX_ERRORS;
        }

        recursionCallPending = true;
        errorMessage = level + "checking ...\n";

        boolean syntax = NO_SYNTAX_ERRORS;
        try {
            tokenizeExpressionString();
            /*
             * Duplicated tokens?
             */
            String kw1;
            String kw2;
            java.util.Collections.sort(keyWordsList, new KwStrComparator());
            for (int kwId = 1; kwId < keyWordsList.size(); kwId++) {
                kw1 = keyWordsList.get(kwId - 1).getWordString();
                kw2 = keyWordsList.get(kwId).getWordString();
                if (kw1.equals(kw2)) {
                    syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN;
                    errorMessage = errorMessage + level + "(" + kw1 + ") Duplicated <KEYWORD>.\n";
                }
            }
            int tokensNumber = initialTokens.size();
            Stack<SyntaxStackElement> syntaxStack = new Stack<SyntaxStackElement>();
            SyntaxStackElement stackElement;

            for (int tokenIndex = 0; tokenIndex < tokensNumber; tokenIndex++) {
                Token t = initialTokens.get(tokenIndex);
                String tokenStr = "(" + t.tokenStr + ", " + tokenIndex + ") ";
                /*
                 * Check syntax for "UNARY FUNCTION" token
                 */
                if (t.tokenTypeId == Function1Arg.TYPE_ID) {
                    if (getParametersNumber(tokenIndex) != 1) {
                        syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN;
                        errorMessage = errorMessage + level + tokenStr + "<FUNCTION> expecting 1 argument.\n";
                    }
                }

                if ((t.tokenTypeId == ParserSymbol.TYPE_ID) && (t.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID)) {
                    if (syntaxStack.size() > 0)
                        if (t.tokenLevel == syntaxStack.lastElement().tokenLevel)
                            syntaxStack.pop();
                }
            }

        } catch (Exception e) {
            syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN;
            errorMessage = errorMessage + level + "lexical error \n\n" + e.getMessage() + "\n";
        }

        if (syntax == NO_SYNTAX_ERRORS) {
            errorMessage = errorMessage + level + "no errors.\n";
            expressionWasModified = false;
        } else {
            errorMessage = errorMessage + level + "errors were found.\n";
            expressionWasModified = true;
        }

        syntaxStatus = syntax;
        recursionCallPending = false;
        return syntax;
    }

    /**
     * copy initial tokens lito to tokens list
     */
    private void copyInitialTokens() {
        tokensList = new ArrayList<Token>();
        for (Token token : initialTokens) {
            tokensList.add(token.clone());
        }
    }

    public double calculate() {
        /*
         * check expression syntax and
         * evaluate expression string tokens
         *
         */
        if ((expressionWasModified == true) || (syntaxStatus != NO_SYNTAX_ERRORS))
            syntaxStatus = checkSyntax();
        if (syntaxStatus == SYNTAX_ERROR_OR_STATUS_UNKNOWN)
            return Double.NaN;
        copyInitialTokens();
        /*
         * if nothing to calculate return Double.NaN
         */
        if (tokensList.size() == 0)
            return Double.NaN;

        /*
         * position for particular tokens types
         */
        int calculusPos;
        int ifPos;
        int iffPos;
        int variadicFunPos;
        int recArgPos;
        int f3ArgPos;
        int f2ArgPos;
        int f1ArgPos;
        int userFunPos;
        int plusPos;
        int minusPos;
        int multiplyPos;
        int dividePos;
        int powerPos;
        int powerNum;
        int factPos;
        int modPos;
        int negPos;
        int bolPos;
        int eqPos;
        int neqPos;
        int ltPos;
        int gtPos;
        int leqPos;
        int geqPos;
        int commaPos;
        int lParPos;
        int rParPos;
        int bitwisePos;
        int bitwiseComplPos;
        Token token;
        Token tokenL;
        Token tokenR;
        int tokensNumber;
        int maxPartLevel;
        int lPos;
        int rPos;
        int tokenIndex;
        int pos;
        int p;
        ArrayList<Integer> commas = null;
        /* While exist token which needs to bee evaluated */

        do {
            maxPartLevel = -1;
            lPos = -1;
            rPos = -1;
            /*
             * initializing tokens types positions
             */
            calculusPos = -1;
            ifPos = -1;
            iffPos = -1;
            f1ArgPos = -1;
            plusPos = -1;
            minusPos = -1;
            multiplyPos = -1;
            dividePos = -1;
            powerPos = -1;
            factPos = -1;
            modPos = -1;
            powerNum = 0;
            commaPos = -1;
            lParPos = -1;
            rParPos = -1;
            tokensNumber = tokensList.size();

            /* Find start index of the tokens with the highest level */
            for (tokenIndex = 0; tokenIndex < tokensNumber; tokenIndex++) {
                token = tokensList.get(tokenIndex);
                if (token.tokenLevel > maxPartLevel) {
                    maxPartLevel = tokensList.get(tokenIndex).tokenLevel;
                    lPos = tokenIndex;
                }
            }
            tokenIndex = lPos;
            /* Find end index of the tokens with the highest level */
            while ((tokenIndex < tokensNumber) && (maxPartLevel == tokensList.get(tokenIndex).tokenLevel))
                tokenIndex++;

            rPos = tokenIndex - 1;

            /* if no calculus operations were found
             * check for other tokens
             */
            boolean leftIsNUmber;
            boolean rigthIsNUmber;

            for (pos = lPos; pos <= rPos; pos++) {
                leftIsNUmber = false;
                rigthIsNUmber = false;
                token = tokensList.get(pos);
                if (pos - 1 >= 0) {
                    tokenL = tokensList.get(pos - 1);
                    if (tokenL.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) leftIsNUmber = true;
                }
                if (pos + 1 < tokensNumber) {
                    tokenR = tokensList.get(pos + 1);
                    if (tokenR.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) rigthIsNUmber = true;
                }

                if ((token.tokenTypeId == Function1Arg.TYPE_ID) && (f1ArgPos < 0))
                    f1ArgPos = pos;
                else if (token.tokenTypeId == Operator.TYPE_ID) {
                    if ((token.tokenId == Operator.POWER_ID) && (leftIsNUmber && rigthIsNUmber)) {
                        powerPos = pos;
                        powerNum++;
                    } else if ((token.tokenId == Operator.FACT_ID) && (factPos < 0) && (leftIsNUmber)) {
                        factPos = pos;
                    } else if ((token.tokenId == Operator.MOD_ID) && (modPos < 0) && (leftIsNUmber && rigthIsNUmber)) {
                        modPos = pos;
                    } else if ((token.tokenId == Operator.PLUS_ID) && (plusPos < 0) && (leftIsNUmber && rigthIsNUmber))
                        plusPos = pos;
                    else if ((token.tokenId == Operator.MINUS_ID) && (minusPos < 0) && (rigthIsNUmber))
                        minusPos = pos;
                    else if ((token.tokenId == Operator.MULTIPLY_ID) && (multiplyPos < 0) && (leftIsNUmber && rigthIsNUmber))
                        multiplyPos = pos;
                    else if ((token.tokenId == Operator.DIVIDE_ID) && (dividePos < 0) && (leftIsNUmber && rigthIsNUmber))
                        dividePos = pos;
                } else if (token.tokenTypeId == ParserSymbol.TYPE_ID) {
                    if ((token.tokenId == ParserSymbol.LEFT_PARENTHESES_ID) && (lParPos < 0))
                        lParPos = pos;
                    else if ((token.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID) && (rParPos < 0))
                        rParPos = pos;
                }
            }
            /*
             * powering should be done using backwards sequence
             */
            if (powerNum > 1) {
                powerPos = -1;
                p = rPos + 1;
                do {
                    p--;
                    token = tokensList.get(p);
                    if ((token.tokenTypeId == Operator.TYPE_ID) && (token.tokenId == Operator.POWER_ID))
                        powerPos = p;
                } while ((p > lPos) && (powerPos == -1));
            }


            if (f1ArgPos >= 0) f1ArgCalc(f1ArgPos);
            else
                /* ... powering  ... */
                if (powerPos >= 0) {
                    POWER(powerPos);
                } else if (factPos >= 0) {
                    FACT(factPos);
                } else if (modPos >= 0) {
                    MODULO(modPos);
                } else
                    /* ... arithmetical operators  ... */
                    if ((multiplyPos >= 0) || (dividePos >= 0)) {
                        if ((multiplyPos >= 0) && (dividePos >= 0))
                            if (multiplyPos <= dividePos)
                                MULTIPLY(multiplyPos);
                            else
                                DIVIDE(dividePos);
                        else if (multiplyPos >= 0)
                            MULTIPLY(multiplyPos);
                        else
                            DIVIDE(dividePos);
                    } else if ((minusPos >= 0) || (plusPos >= 0)) {
                        if ((minusPos >= 0) && (plusPos >= 0))
                            if (minusPos <= plusPos)
                                MINUS(minusPos);
                            else
                                PLUS(plusPos);
                        else if (minusPos >= 0)
                            MINUS(minusPos);
                        else
                            PLUS(plusPos);
                    } else if ((lParPos >= 0) && (rParPos > lParPos)) {
                        PARENTHESES(lParPos, rParPos);
                    } else if (tokensList.size() > 1) {
                        this.errorMessage = errorMessage + "\n" + "[" + description + "][" + expressionString + "] " + "Fatal error - not know what to do with tokens while calculate().";
                    }

        } while (tokensList.size() > 1);

        return tokensList.get(0).tokenValue;
    }

    /**
     * Return number of functions parameters.
     *
     * @param pos the function position
     */
    private int getParametersNumber(int pos) {
        int lPpos = pos + 1;
        if (lPpos == initialTokens.size())
            return -1;
        if ((initialTokens.get(lPpos).tokenTypeId == ParserSymbol.TYPE_ID) && (initialTokens.get(lPpos).tokenId == ParserSymbol.LEFT_PARENTHESES_ID)) {
            int tokenLevel = initialTokens.get(lPpos).tokenLevel;
            /*
             * Evaluate right parenthesis position
             */
            int endPos = lPpos + 1;
            while (!((initialTokens.get(endPos).tokenTypeId == ParserSymbol.TYPE_ID)
                    && (initialTokens.get(endPos).tokenId == ParserSymbol.RIGHT_PARENTHESES_ID)
                    && (initialTokens.get(endPos).tokenLevel == tokenLevel)))
                endPos++;
            if (endPos == lPpos + 1)
                return 0;
            /*
             * Evaluate number of parameters by
             * counting number of ',' between parenthesis
             */
            int numberOfCommas = 0;
            for (int p = lPpos; p < endPos; p++) {
                Token token = initialTokens.get(p);
                if ((token.tokenTypeId == ParserSymbol.TYPE_ID) && (token.tokenId == ParserSymbol.COMMA_ID) && (token.tokenLevel == tokenLevel))
                    numberOfCommas++;
            }
            return numberOfCommas + 1;
        } else {
            return -1;
        }
    }

    /**
     * Calculates unary function
     *
     * @param pos token position
     */
    private void f1ArgCalc(int pos) {
        switch (tokensList.get(pos).tokenId) {
            case Function1Arg.SIN_ID:
                SIN(pos);
                break;
            case Function1Arg.COS_ID: COS(pos); break;
            case Function1Arg.TAN_ID: TAN(pos); break;
        }
    }


    /**
     * SetDecreaseRemove for 1 arg functions
     * <p>
     * SetDecreaseRemove like methods are called by the methods
     * calculating values of the unary operation, binary relations
     * and functions.
     * <p>
     * 3 things are done by this type of methods
     * 1) Set token type to number type / value
     * 2) Decrease level of the token
     * 3) Remove no longer needed tokens
     * <p>
     * For example:
     * <p>
     * Expression string: 1+cos(0)
     * will be tokened as follows:
     * <p>
     * idx   :  0   1    2    3   4   5
     * token :  1   +   cos   (   0   )
     * level :  0   0    1    2   2   2
     * <p>
     * Partitions with the highest level will be handled first.
     * In the case presented above, it means, that the parenthesis will be removed
     * <p>
     * idx   :  0   1    2    3
     * token :  1   +   cos   0
     * level :  0   0    1    2
     * <p>
     * Next step is to calculate cos(0) = 1
     * <p>
     * SetDecreaseRemove like methods
     * <p>
     * 1) Set cos token to 1 (pos=2, result=1):
     * idx   :  0   1    2    3
     * token :  1   +    1    0
     * level :  0   0    1    2
     * <p>
     * 2) Decrease level (pos=2):
     * idx   :  0   1    2    3
     * token :  1   +    1    0
     * level :  0   0    0    2
     * <p>
     * 3) Remove no longer needed tokens (pos+1=3):
     * idx   :  0   1    2
     * token :  1   +    1
     * level :  0   0    0
     *
     * @param pos      the position on which token
     *                 should be updated to the given number
     * @param result   the number
     * @param ulpRound If true,
     *                 intelligent ULP rounding is applied.
     */
    private void f1SetDecreaseRemove(int pos, double result, boolean ulpRound) {
        setToNumber(pos, result, ulpRound);
        tokensList.get(pos).tokenLevel--;
        tokensList.remove(pos + 1);
    }

    private void f1SetDecreaseRemove(int pos, double result) {
        f1SetDecreaseRemove(pos, result, false);
    }

    /**
     * Sets given token to the number type / value.
     * Method should be called only by the SetDecreaseRemove like methods
     *
     * @param pos      the position on which token
     *                 should be updated to the given number
     * @param number   the number
     * @param ulpRound If true, then if
     *                 intelligent ULP rounding is applied.
     */
    private void setToNumber(int pos, double number, boolean ulpRound) {
        Token token = tokensList.get(pos);
        token.tokenValue = number;
        token.tokenTypeId = ParserSymbol.NUMBER_TYPE_ID;
        token.tokenId = ParserSymbol.NUMBER_ID;
        token.keyWord = ParserSymbol.NUMBER_STR;
    }

    private void setToNumber(int pos, double number) {
        setToNumber(pos, number, false);
    }

    /**
     * Gets token value
     *
     * @param tokenIndex the token index
     * @return the token value
     */
    private double getTokenValue(int tokenIndex) {
        return tokensList.get(tokenIndex).tokenValue;
    }

    /**
     * Sine function
     *
     * @param pos the token position
     */
    private void SIN(int pos) {
        double a = getTokenValue(pos + 1);
        f1SetDecreaseRemove(pos, MathFunctions.sin(a));
    }

    /**
     * Cosine function
     *
     * @param pos the token position
     */
    private void COS(int pos) {
        double a = getTokenValue(pos + 1);
        f1SetDecreaseRemove(pos, MathFunctions.cos(a));
    }

    /**
     * TAN function
     *
     * @param pos the token position
     */
    private void TAN(int pos) {
        double a = getTokenValue(pos + 1);
        f1SetDecreaseRemove(pos, MathFunctions.tan(a));
    }

    /**
     * Power handling.
     *
     * @param pos the token position
     */
    private void POWER(int pos) {
        double a = getTokenValue(pos - 1);
        double b = getTokenValue(pos + 1);
        opSetDecreaseRemove(pos, MathFunctions.power(a, b), true);
    }

    /**
     * Factorilal function
     * Sets tokens to number token
     *
     * @param pos the token position
     */
    private void FACT(int pos) {
        double a = getTokenValue(pos - 1);
        setToNumber(pos, MathFunctions.factorial(a));
        tokensList.remove(pos - 1);
    }

    /**
     * Modulo handling.
     *
     * @param pos the token position
     */
    private void MODULO(int pos) {
        double a = getTokenValue(pos - 1);
        double b = getTokenValue(pos + 1);
        opSetDecreaseRemove(pos, MathFunctions.mod(a, b));
    }

    /**
     * Multiplication handling.
     *
     * @param pos the token position
     */
    private void MULTIPLY(int pos) {
        double a = getTokenValue(pos - 1);
        double b = getTokenValue(pos + 1);
        opSetDecreaseRemove(pos, a * b, true);
    }

    /**
     * Division handling.
     *
     * @param pos the token position
     */
    private void DIVIDE(int pos) {
        double a = getTokenValue(pos - 1);
        double b = getTokenValue(pos + 1);
        opSetDecreaseRemove(pos, MathFunctions.div(a, b), true);
    }

    /**
     * Subtraction handling
     *
     * @param pos the token position
     */
    private void MINUS(int pos) {
        Token b = tokensList.get(pos + 1);
        if (pos > 0) {
            Token a = tokensList.get(pos - 1);
            if ((a.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) && (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID))
                opSetDecreaseRemove(pos, a.tokenValue - b.tokenValue, true);
            else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
                setToNumber(pos, -b.tokenValue);
                tokensList.remove(pos + 1);
            }
        } else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
            setToNumber(pos, -b.tokenValue);
            tokensList.remove(pos + 1);
        }
    }

    /**
     * Addition handling.
     *
     * @param pos the token position
     */
    private void PLUS(int pos) {
        Token b = tokensList.get(pos + 1);
        if (pos > 0) {
            Token a = tokensList.get(pos - 1);
            if ((a.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) && (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID))
                opSetDecreaseRemove(pos, a.tokenValue + b.tokenValue, true);
            else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
                setToNumber(pos, b.tokenValue);
                tokensList.remove(pos + 1);
            }
        } else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
            setToNumber(pos, b.tokenValue);
            tokensList.remove(pos + 1);
        }
    }

    /**
     * Parser symbols
     * Removes parenthesis
     *
     * @param lPos left token index (position)
     * @param rPos roght token index (position)
     */
    private void PARENTHESES(int lPos, int rPos) {
        for (int p = lPos; p <= rPos; p++)
            tokensList.get(p).tokenLevel--;
        tokensList.remove(rPos);
        tokensList.remove(lPos);
    }

    /**
     * SetDecreaseRemove for operators
     * <p>
     * For detailed specification refer to the
     * f1SetDecreaseRemove()
     *
     * @param pos      the position on which token
     *                 should be updated to the given number
     * @param result   the number
     * @param ulpRound If true,
     *                 intelligent ULP rounding is applied.
     */
    private void opSetDecreaseRemove(int pos, double result, boolean ulpRound) {
        setToNumber(pos, result, ulpRound);
        tokensList.remove(pos + 1);
        tokensList.remove(pos - 1);
    }

    private void opSetDecreaseRemove(int pos, double result) {
        opSetDecreaseRemove(pos, result, false);
    }

    private void tokenizeExpressionString() {
        /*
         * Add parser and argument key words
         */

        keyWordsList = new ArrayList<>();
        addParserKeyWords();

        java.util.Collections.sort(keyWordsList, new DescKwLenComparator());
        /*
         * Evaluate position after sorting for the following keywords types
         *    number
         *    plus operator
         *    minus operator
         *
         * Above mentioned information is required
         * when distinguishing between numbers (regexp) and operators
         *
         * For example
         *
         * 1-2 : two numbers and one operator, but -2 is also a valid number
         * (-2)+3 : two number and one operator
         */
        int numberKwId = ConstantValue.NaN;
        int plusKwId = ConstantValue.NaN;
        int minusKwId = ConstantValue.NaN;
        for (int kwId = 0; kwId < keyWordsList.size(); kwId++) {
            if (keyWordsList.get(kwId).getWordTypeId() == ParserSymbol.NUMBER_TYPE_ID)
                numberKwId = kwId;
            if (keyWordsList.get(kwId).getWordTypeId() == Operator.TYPE_ID) {
                if (keyWordsList.get(kwId).getWordId() == Operator.PLUS_ID)
                    plusKwId = kwId;
                if (keyWordsList.get(kwId).getWordId() == Operator.MINUS_ID)
                    minusKwId = kwId;
            }
        }

        String newExpressionString = new String(expressionString);
        /*
         * words list and tokens list
         */
        initialTokens = new ArrayList<>();
        int lastPos = 0; /* position of the key word previously added*/
        int pos = 0; /* current position */
        String tokenStr = "";
        int matchStatusPrev = NOT_FOUND; /* unknown key word (previous) */
        int matchStatus = NOT_FOUND; /* unknown key word (current) */
        KeyWord kw = null;
        String sub = "";
        String kwStr = "";
        char precedingChar;
        char followingChar;
        char firstChar;
        char c;
        /*
         * Check all available positions in the expression tokens list
         */

        do {
            /*
             * 1st step
             *
             * compare with the regExp for real numbers
             * find the longest word which could be matched
             * with the given regExp
             */
            int numEnd = -1;

            c = newExpressionString.charAt(pos);

            if (c == '+' || c == '-' || (c >= '0' && c <= '9')) {
                for (int i = pos; i < newExpressionString.length(); i++) {
                    /*
                     * Escaping if encountering char that can not
                     * be included in number
                     */
                    if (i > pos) {
                        c = newExpressionString.charAt(i);
                        if (c != '+' && c != '-' && !(c >= '0' && c <= '9')
                                && c != '.') break;
                    }

                    /*
                     * Checking if substring represents number
                     */

                    String str = newExpressionString.substring(pos, i + 1);
                    if (Parser.regexMatch(str, ParserSymbol.NUMBER_REG_EXP))
                        numEnd = i;
                }
            }

            /*
             * If number was found
             */
            if (numEnd >= 0)
                if (pos > 0) {
                    precedingChar = newExpressionString.charAt(pos - 1);
                    if (
                            (precedingChar != ',') &&
                                    (precedingChar != ';') &&
                                    (precedingChar != '|') &&
                                    (precedingChar != '&') &&
                                    (precedingChar != '+') &&
                                    (precedingChar != '-') &&
                                    (precedingChar != '*') &&
                                    (precedingChar != '\\') &&
                                    (precedingChar != '/') &&
                                    (precedingChar != '(') &&
                                    (precedingChar != ')') &&
                                    (precedingChar != '=') &&
                                    (precedingChar != '>') &&
                                    (precedingChar != '<') &&
                                    (precedingChar != '~') &&
                                    (precedingChar != '^') &&
                                    (precedingChar != '#') &&
                                    (precedingChar != '%') &&
                                    (precedingChar != '@') &&
                                    (precedingChar != '!'))
                        numEnd = -1;
                }

            if (numEnd >= 0)
                if (numEnd < newExpressionString.length() - 1) {
                    followingChar = newExpressionString.charAt(numEnd + 1);
                    if (
                            (followingChar != ',') &&
                                    (followingChar != ';') &&
                                    (followingChar != '|') &&
                                    (followingChar != '&') &&
                                    (followingChar != '+') &&
                                    (followingChar != '-') &&
                                    (followingChar != '*') &&
                                    (followingChar != '\\') &&
                                    (followingChar != '/') &&
                                    (followingChar != '(') &&
                                    (followingChar != ')') &&
                                    (followingChar != '=') &&
                                    (followingChar != '>') &&
                                    (followingChar != '<') &&
                                    (followingChar != '~') &&
                                    (followingChar != '^') &&
                                    (followingChar != '#') &&
                                    (followingChar != '%') &&
                                    (followingChar != '@') &&
                                    (followingChar != '!'))
                        numEnd = -1;
                }

            if (numEnd >= 0) {
                /*
                 * Check leading operators ('-' or '+')
                 *
                 * For example:
                 *    '2-1' :  1(num) -(op) 2(num) = 1(num)
                 *    -1+2  : -1(num) +(op) 2(num) = 1(num)
                 */
                firstChar = newExpressionString.charAt(pos);
                boolean leadingOp = true;
                if ((firstChar == '-') || (firstChar == '+')) {
                    if (initialTokens.size() > 0) {
                        Token lastToken = initialTokens.get(initialTokens.size() - 1);
                        if (((lastToken.tokenTypeId == Operator.TYPE_ID) && (lastToken.tokenId != Operator.FACT_ID)) ||
//                                (lastToken.tokenTypeId == BinaryRelation.TYPE_ID) ||
//                                (lastToken.tokenTypeId == BooleanOperator.TYPE_ID) ||
//                                (lastToken.tokenTypeId == BitwiseOperator.TYPE_ID) ||
                                ((lastToken.tokenTypeId == ParserSymbol.TYPE_ID) && (lastToken.tokenId == ParserSymbol.LEFT_PARENTHESES_ID)))
                            leadingOp = false;
                        else leadingOp = true;
                    } else leadingOp = false;
                } else leadingOp = false;

                /*
                 * If leading operator was found
                 */
                if (leadingOp == true) {
                    /*
                     * Add leading operator to the tokens list
                     */
                    if (firstChar == '-')
                        addToken("-", keyWordsList.get(minusKwId));
                    if (firstChar == '+')
                        addToken("+", keyWordsList.get(plusKwId));
                    pos++;
                }

                /*
                 * Add found number to the tokens list
                 */
                tokenStr = newExpressionString.substring(pos, numEnd + 1);
                addToken(tokenStr, keyWordsList.get(numberKwId));
                /*
                 * change current position (just after the number ends)
                 */
                pos = numEnd + 1;
                lastPos = pos;
                /*
                 * Mark match status indicators
                 */
                matchStatus = FOUND;
                matchStatusPrev = FOUND;
            } else {
                /*
                 * If there is no number which starts with current position
                 * Check for known key words
                 */
                int kwId = -1;
                matchStatus = NOT_FOUND;
                do {
                    kwId++;
                    kw = keyWordsList.get(kwId);
                    kwStr = kw.getWordString();

                    if (pos + kwStr.length() <= newExpressionString.length()) {
                        sub = newExpressionString.substring(pos, pos + kwStr.length());
                        if (sub.equals(kwStr))
                            matchStatus = FOUND;
                        /*
                         * If key word is known by the parser
                         */
                        if (matchStatus == FOUND) {
                            /*
                             * If key word is in the form of identifier
                             * then check preceding and following characters
                             */
                            int wordTypeID = kw.getWordTypeId();
                            if (wordTypeID == Function1Arg.TYPE_ID) {
                                /*
                                 * Checking preceding character
                                 */
                                if (pos > 0) {
                                    precedingChar = newExpressionString.charAt(pos - 1);
                                    if ((precedingChar != ',') &&
                                            (precedingChar != ';') &&
                                            (precedingChar != '|') &&
                                            (precedingChar != '&') &&
                                            (precedingChar != '+') &&
                                            (precedingChar != '-') &&
                                            (precedingChar != '*') &&
                                            (precedingChar != '\\') &&
                                            (precedingChar != '/') &&
                                            (precedingChar != '(') &&
                                            (precedingChar != ')') &&
                                            (precedingChar != '=') &&
                                            (precedingChar != '>') &&
                                            (precedingChar != '<') &&
                                            (precedingChar != '~') &&
                                            (precedingChar != '^') &&
                                            (precedingChar != '#') &&
                                            (precedingChar != '%') &&
                                            (precedingChar != '@') &&
                                            (precedingChar != '!')) matchStatus = NOT_FOUND;
                                }
                                /*
                                 * Checking following character
                                 */
                                if ((matchStatus == FOUND) && (pos + kwStr.length() < newExpressionString.length())) {
                                    followingChar = newExpressionString.charAt(pos + kwStr.length());
                                    if ((followingChar != ',') &&
                                            (followingChar != ';') &&
                                            (followingChar != '|') &&
                                            (followingChar != '&') &&
                                            (followingChar != '+') &&
                                            (followingChar != '-') &&
                                            (followingChar != '*') &&
                                            (followingChar != '\\') &&
                                            (followingChar != '/') &&
                                            (followingChar != '(') &&
                                            (followingChar != ')') &&
                                            (followingChar != '=') &&
                                            (followingChar != '>') &&
                                            (followingChar != '<') &&
                                            (followingChar != '~') &&
                                            (followingChar != '^') &&
                                            (followingChar != '#') &&
                                            (followingChar != '%') &&
                                            (followingChar != '@') &&
                                            (followingChar != '!')) matchStatus = NOT_FOUND;
                                }

                            }

                        }
                    }
                } while ((kwId < keyWordsList.size() - 1) && (matchStatus == NOT_FOUND));

                /*
                 * If key word known by the parser was found
                 */
                if (matchStatus == FOUND) {
                    /*
                     * if preceding word was not known by the parser
                     */
                    if ((matchStatusPrev == NOT_FOUND) && (pos > 0)) {
                        /*
                         * Add preceding word to the tokens list
                         * as unknown key word
                         */
                        tokenStr = newExpressionString.substring(lastPos, pos);
                        addToken(tokenStr, new KeyWord());
                    }
                    matchStatusPrev = FOUND;
                    /*
                     * Add current (known by the parser)
                     * key word to the tokens list
                     */
                    tokenStr = newExpressionString.substring(pos, pos + kwStr.length());
                    addToken(tokenStr, kw);
                    /*
                     * Remember position where last added word ends + 1
                     */
                    lastPos = pos + kwStr.length();
                    /*
                     * Change current position;
                     */
                    pos = pos + kwStr.length();
                } else {
                    /*
                     * Update preceding word indicator
                     */
                    matchStatusPrev = NOT_FOUND;
                    /*
                     * Increment position if possible
                     */
                    if (pos < newExpressionString.length())
                        pos++;
                }
            }


        } while (pos < newExpressionString.length());

        /*
         * If key word was not known by the parser
         * and end with the string end
         * it needs to be added to the tokens list
         * as unknown key word
         */
        if (matchStatus == NOT_FOUND) {
            tokenStr = newExpressionString.substring(lastPos, pos);
            addToken(tokenStr, new KeyWord());
        }
        /*
         * Evaluate tokens levels
         *
         * token level identifies the sequance of parsing
         */
        evaluateTokensLevels();
    }

    /**
     * Evaluates tokens levels
     */
    private void evaluateTokensLevels() {
        int tokenLevel = 0;
        Stack<TokenStackElement> tokenStack = new Stack<TokenStackElement>();
        boolean precedingFunction = false;
        if (initialTokens.size() > 0)
            for (int tokenIndex = 0; tokenIndex < initialTokens.size(); tokenIndex++) {
                Token token = initialTokens.get(tokenIndex);
                if ((token.tokenTypeId == Function1Arg.TYPE_ID) // ||
//                        ( token.tokenTypeId == Function2Arg.TYPE_ID ) ||
//                        ( token.tokenTypeId == Function3Arg.TYPE_ID )	||
//                        ( token.tokenTypeId == Function.TYPE_ID )	||
//                        ( token.tokenTypeId == CalculusOperator.TYPE_ID ) ||
//                        ( token.tokenTypeId == RecursiveArgument.TYPE_ID_RECURSIVE ) ||
//                        ( token.tokenTypeId == FunctionVariadic.TYPE_ID )
                ) {
                    tokenLevel++;
                    precedingFunction = true;
                } else if ((token.tokenTypeId == ParserSymbol.TYPE_ID) && (token.tokenId == ParserSymbol.LEFT_PARENTHESES_ID)) {
                    tokenLevel++;
                    TokenStackElement stackEl = new TokenStackElement();
                    stackEl.tokenId = token.tokenId;
                    stackEl.tokenIndex = tokenIndex;
                    stackEl.tokenLevel = tokenLevel;
                    stackEl.tokenTypeId = token.tokenTypeId;
                    stackEl.precedingFunction = precedingFunction;
                    tokenStack.push(stackEl);
                    precedingFunction = false;
                } else
                    precedingFunction = false;
                token.tokenLevel = tokenLevel;
                if ((token.tokenTypeId == ParserSymbol.TYPE_ID) && (token.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID)) {
                    tokenLevel--;
                    if (!tokenStack.isEmpty()) {
                        TokenStackElement stackEl = tokenStack.pop();
                        if (stackEl.precedingFunction == true)
                            tokenLevel--;
                    }
                }
            }
    }

    /**
     * Creates parseres key words list
     */
    private void addParserKeyWords() {
        /*
         * Operators key words
         */
        addKeyWord(Operator.PLUS_STR, Operator.PLUS_DESC, Operator.PLUS_ID, Operator.TYPE_ID);
        addKeyWord(Operator.MINUS_STR, Operator.MINUS_DESC, Operator.MINUS_ID, Operator.TYPE_ID);
        addKeyWord(Operator.MULTIPLY_STR, Operator.MULTIPLY_DESC, Operator.MULTIPLY_ID, Operator.TYPE_ID);
        addKeyWord(Operator.DIVIDE_STR, Operator.DIVIDE_DESC, Operator.DIVIDE_ID, Operator.TYPE_ID);
        addKeyWord(Operator.POWER_STR, Operator.POWER_DESC, Operator.POWER_ID, Operator.TYPE_ID);
        addKeyWord(Operator.FACT_STR, Operator.FACT_DESC, Operator.FACT_ID, Operator.TYPE_ID);
        addKeyWord(Operator.MOD_STR, Operator.MOD_DESC, Operator.MOD_ID, Operator.TYPE_ID);

        /*
         * 1 arg functions key words
         */
        addKeyWord(Function1Arg.SIN_STR, Function1Arg.SIN_DESC, Function1Arg.SIN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.COS_STR, Function1Arg.COS_DESC, Function1Arg.COS_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.TAN_STR, Function1Arg.TAN_DESC, Function1Arg.TAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.TG_STR, Function1Arg.TAN_DESC, Function1Arg.TAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.CTAN_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.CTG_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.COT_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.SEC_STR, Function1Arg.SEC_DESC, Function1Arg.SEC_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.COSEC_STR, Function1Arg.COSEC_DESC, Function1Arg.COSEC_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.CSC_STR, Function1Arg.COSEC_DESC, Function1Arg.COSEC_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ASIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARSIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCSIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ATAN_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCTAN_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ATG_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCTG_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACTAN_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCTAN_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACTG_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCTG_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACOT_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCOT_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.LN_STR, Function1Arg.LN_DESC, Function1Arg.LN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.LOG2_STR, Function1Arg.LOG2_DESC, Function1Arg.LOG2_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.LOG10_STR, Function1Arg.LOG10_DESC, Function1Arg.LOG10_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.RAD_STR, Function1Arg.RAD_DESC, Function1Arg.RAD_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.EXP_STR, Function1Arg.EXP_DESC, Function1Arg.EXP_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.SQRT_STR, Function1Arg.SQRT_DESC, Function1Arg.SQRT_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.SINH_STR, Function1Arg.SINH_DESC, Function1Arg.SINH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.COSH_STR, Function1Arg.COSH_DESC, Function1Arg.COSH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.TANH_STR, Function1Arg.TANH_DESC, Function1Arg.TANH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.TGH_STR, Function1Arg.TANH_DESC, Function1Arg.TANH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.CTANH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.COTH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.CTGH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.SECH_STR, Function1Arg.SECH_DESC, Function1Arg.SECH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.CSCH_STR, Function1Arg.CSCH_DESC, Function1Arg.CSCH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.COSECH_STR, Function1Arg.CSCH_DESC, Function1Arg.CSCH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.DEG_STR, Function1Arg.DEG_DESC, Function1Arg.DEG_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ABS_STR, Function1Arg.ABS_DESC, Function1Arg.ABS_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.SGN_STR, Function1Arg.SGN_DESC, Function1Arg.SGN_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.FLOOR_STR, Function1Arg.FLOOR_DESC, Function1Arg.FLOOR_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.CEIL_STR, Function1Arg.CEIL_DESC, Function1Arg.CEIL_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.NOT_STR, Function1Arg.NOT_DESC, Function1Arg.NOT_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ASINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARSINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCSINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ATANH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCTANH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ATGH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCTGH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACTANH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCTANH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACTGH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCTGH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ASECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARSECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCSECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ACOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ARCCOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.SA_STR, Function1Arg.SA_DESC, Function1Arg.SA_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.SA1_STR, Function1Arg.SA_DESC, Function1Arg.SA_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.SINC_STR, Function1Arg.SINC_DESC, Function1Arg.SINC_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.BELL_NUMBER_STR, Function1Arg.BELL_NUMBER_DESC, Function1Arg.BELL_NUMBER_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.FIBONACCI_NUMBER_STR, Function1Arg.FIBONACCI_NUMBER_DESC, Function1Arg.FIBONACCI_NUMBER_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.LUCAS_NUMBER_STR, Function1Arg.LUCAS_NUMBER_DESC, Function1Arg.LUCAS_NUMBER_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.HARMONIC_NUMBER_STR, Function1Arg.HARMONIC_NUMBER_DESC, Function1Arg.HARMONIC_NUMBER_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.IS_PRIME_STR, Function1Arg.IS_PRIME_DESC, Function1Arg.IS_PRIME_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.PRIME_COUNT_STR, Function1Arg.PRIME_COUNT_DESC, Function1Arg.PRIME_COUNT_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.EXP_INT_STR, Function1Arg.EXP_INT_DESC, Function1Arg.EXP_INT_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.LOG_INT_STR, Function1Arg.LOG_INT_DESC, Function1Arg.LOG_INT_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.OFF_LOG_INT_STR, Function1Arg.OFF_LOG_INT_DESC, Function1Arg.OFF_LOG_INT_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.GAUSS_ERF_STR, Function1Arg.GAUSS_ERF_DESC, Function1Arg.GAUSS_ERF_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.GAUSS_ERFC_STR, Function1Arg.GAUSS_ERFC_DESC, Function1Arg.GAUSS_ERFC_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.GAUSS_ERF_INV_STR, Function1Arg.GAUSS_ERF_INV_DESC, Function1Arg.GAUSS_ERF_INV_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.GAUSS_ERFC_INV_STR, Function1Arg.GAUSS_ERFC_INV_DESC, Function1Arg.GAUSS_ERFC_INV_ID, Function1Arg.TYPE_ID);
        addKeyWord(Function1Arg.ULP_STR, Function1Arg.ULP_DESC, Function1Arg.ULP_ID, Function1Arg.TYPE_ID);

        /*
         * Other parser symbols key words
         */
        addKeyWord(ParserSymbol.LEFT_PARENTHESES_STR, ParserSymbol.LEFT_PARENTHESES_DESC, ParserSymbol.LEFT_PARENTHESES_ID, ParserSymbol.TYPE_ID);
        addKeyWord(ParserSymbol.RIGHT_PARENTHESES_STR, ParserSymbol.RIGHT_PARENTHESES_DESC, ParserSymbol.RIGHT_PARENTHESES_ID, ParserSymbol.TYPE_ID);
        addKeyWord(ParserSymbol.COMMA_STR, ParserSymbol.COMMA_DESC, ParserSymbol.COMMA_ID, ParserSymbol.TYPE_ID);
        addKeyWord(ParserSymbol.SEMI_STR, ParserSymbol.SEMI_DESC, ParserSymbol.COMMA_ID, ParserSymbol.TYPE_ID);
        addKeyWord(ParserSymbol.NUMBER_REG_EXP, ParserSymbol.NUMBER_REG_DESC, ParserSymbol.NUMBER_ID, ParserSymbol.NUMBER_TYPE_ID);

    }

    /**
     * Adds key word to the keyWords list
     *
     * @param wordString
     * @param wordDescription
     * @param wordId
     * @param wordTypeId
     */
    private void addKeyWord(String wordString, String wordDescription, int wordId, int wordTypeId) {
        keyWordsList.add(new KeyWord(wordString, wordDescription, wordId, wordTypeId));
    }

    /**
     * Adds expression token
     * Method is called by the tokenExpressionString()
     * while parsing string expression
     *
     * @param tokenStr the token string
     * @param keyWord  the key word
     */
    private void addToken(String tokenStr, KeyWord keyWord) {
        Token token = new Token();
        initialTokens.add(token);
        token.tokenStr = tokenStr;
        token.keyWord = keyWord.getWordString();
        token.tokenId = keyWord.getWordId();
        token.tokenTypeId = keyWord.getWordTypeId();
//        if (token.tokenTypeId == Argument.TYPE_ID)
//            token.tokenValue = argumentsList.get(token.tokenId).argumentValue;
//        else
        if (token.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
            token.tokenValue = Double.valueOf(token.tokenStr);
            token.keyWord = ParserSymbol.NUMBER_STR;
        }
    }
}

//public class Expression {
//
//    /**
//     * FOUND / NOT_FOUND
//     * used for matching purposes
//     */
//    static final int NOT_FOUND = Parser.NOT_FOUND;
//    static final int FOUND = Parser.FOUND;
//    /**
//     * Status of the Expression syntax
//     */
//    public static final boolean NO_SYNTAX_ERRORS = true;
//    public static final boolean SYNTAX_ERROR_OR_STATUS_UNKNOWN = false;
//    /**
//     * Expression string (for example: "sin(x)+cos(y)")
//     */
//    String expressionString;
//    private String description;
//
//    /**
//     * Flag used internally to mark started recursion
//     * call on the current object, necessary to
//     * avoid infinite loops while recursive syntax
//     * checking (i.e. f -> g and g -> f)
//     * or marking modified flags on the expressions
//     * related to this expression.
//     * <p>
//     * //     * @see setExpressionModifiedFlag()
//     * //     * @see checkSyntax()
//     */
//    private boolean recursionCallPending;
//    /**
//     * if true then new tokenizing is required
//     * (the initialTokens list needs to be updated)
//     */
//    private boolean expressionWasModified;
//    /**
//     * Status of the expression syntax
//     * <p>
//     * Please referet to the:
//     * - NO_SYNTAX_ERRORS
//     * - SYNTAX_ERROR_OR_STATUS_UNKNOWN
//     */
//    private boolean syntaxStatus;
//    /**
//     * Message after checking the syntax
//     */
//    private String errorMessage;
//    /**
//     * List of key words known by the parser
//     */
//    private ArrayList<KeyWord> keyWordsList;
//    /**
//     * List of expression tokens (words).
//     * Token class defines all needed
//     * attributes for recognizing the structure of
//     * arithmetic expression. This is the key result when
//     * initial parsing is finished (tokenizeExpressionString() - method).
//     * Token keeps information about:
//     * - token type (for example: function, operator, argument, number, etc...)
//     * - token identifier within given type (sin, cos, operaotr, etc...)
//     * - token value (if token is a number)
//     * - token level - key information regarding sequence (order) of further parsing
//     */
//    private ArrayList<Token> initialTokens;
//    /**
//     * the initialTokens list keeps unchanged information about
//     * found tokens.
//     * <p>
//     * While parsing the tokensList is used. The tokensList is the same
//     * as initialTokens list at the beginning of the calculation process.
//     * Each math operation changes tokens list - it means that
//     * tokens are parameters when performing math operation
//     * and the result is also presented as token (usually as a number token)
//     * At the end of the calculation the tokensList should contain only one
//     * element - the result of all calculations.
//     */
//    private ArrayList<Token> tokensList;
//    /**
//     * Verbose mode prints processing info
//     * calls System.out.print* methods
//     */
//    private boolean verboseMode;
//
//    /**
//     * Disables recursive mode
//     */
////    void disableRecursiveMode() {
////        recursiveMode = false;
////    }
//    public Expression(String expressionString) {
//        /*
//         * New lists
//         */
//        // expressionInit()
////        argumentsList = new ArrayList<Argument>();
////        functionsList = new ArrayList<Function>();
////        constantsList = new ArrayList<Constant>();
////        relatedExpressionsList = new ArrayList<Expression>();
//        /*
//         * Empty description
//         * Silent mode
//         * No recursive mode
//         */
//
////        setSilentMode();
//        verboseMode = false;
////        disableRecursiveMode();
////        expressionInternalVarsInit();
//        this.expressionString = expressionString;
////        setExpressionModifiedFlag();
//        expressionWasModified = true;
//        syntaxStatus = SYNTAX_ERROR_OR_STATUS_UNKNOWN;
//        errorMessage = "Syntax status unknown.";
////        addDefinitions(elements);
//    }
//
//    /**
//     * Checks syntax of the expression string.
//     *
//     * @return true if syntax is ok
//     */
//
//    private boolean checkSyntax() {
//        boolean syntax = checkSyntax("[" + expressionString + "] ", false);
//        return syntax;
//    }
//
//    /**
//     * Checking the syntax (recursively).
//     *
//     * @param level string representing the recurssion level.
//     * @return true if syntax was correct,
//     * otherwise returns false.
//     */
//    private boolean checkSyntax(String level, boolean functionWithBodyExt) {
//        if ((expressionWasModified == false) && (syntaxStatus == NO_SYNTAX_ERRORS)) {
//            errorMessage = level + "already checked - no errors!\n";
//            recursionCallPending = false;
//            return NO_SYNTAX_ERRORS;
//        }
//        if (functionWithBodyExt) {
//            syntaxStatus = NO_SYNTAX_ERRORS;
//            recursionCallPending = false;
//            expressionWasModified = false;
//            errorMessage = errorMessage + level + "function with extended body - assuming no errors.\n";
//            return NO_SYNTAX_ERRORS;
//        }
//
//        recursionCallPending = true;
//        errorMessage = level + "checking ...\n";
//
//        boolean syntax = NO_SYNTAX_ERRORS;
//        SyntaxChecker syn = new SyntaxChecker(new ByteArrayInputStream(expressionString.getBytes()));
//        try {
//
//        } catch (Exception e) {
//            syntax = SYNTAX_ERROR_OR_STATUS_UNKNOWN;
//            errorMessage = errorMessage + level + "lexical error \n\n" + e.getMessage() + "\n";
//        }
//
//        if (syntax == NO_SYNTAX_ERRORS) {
//            errorMessage = errorMessage + level + "no errors.\n";
//            expressionWasModified = false;
//        } else {
//            errorMessage = errorMessage + level + "errors were found.\n";
//            expressionWasModified = true;
//        }
//
//        syntaxStatus = syntax;
//        recursionCallPending = false;
//        return syntax;
//    }
//
//    /**
//     * copy initial tokens lito to tokens list
//     */
//    private void copyInitialTokens() {
//        tokensList = new ArrayList<Token>();
//        for (Token token : initialTokens) {
//            tokensList.add(token.clone());
//        }
//    }
//
//    public double calculate() {
//        /*
//         * check expression syntax and
//         * evaluate expression string tokens
//         *
//         */
//        if ((expressionWasModified == true) || (syntaxStatus != NO_SYNTAX_ERRORS))
//            syntaxStatus = checkSyntax();
//        if (syntaxStatus == SYNTAX_ERROR_OR_STATUS_UNKNOWN)
//            return Double.NaN;
//        copyInitialTokens();
//        /*
//         * if nothing to calculate return Double.NaN
//         */
//        if (tokensList.size() == 0)
//            return Double.NaN;
//
//        /*
//         * position for particular tokens types
//         */
//        int calculusPos;
//        int ifPos;
//        int iffPos;
//        int variadicFunPos;
//        int recArgPos;
//        int f3ArgPos;
//        int f2ArgPos;
//        int f1ArgPos;
//        int userFunPos;
//        int plusPos;
//        int minusPos;
//        int multiplyPos;
//        int dividePos;
//        int powerPos;
//        int powerNum;
//        int factPos;
//        int modPos;
//        int negPos;
//        int bolPos;
//        int eqPos;
//        int neqPos;
//        int ltPos;
//        int gtPos;
//        int leqPos;
//        int geqPos;
//        int commaPos;
//        int lParPos;
//        int rParPos;
//        int bitwisePos;
//        int bitwiseComplPos;
//        Token token;
//        Token tokenL;
//        Token tokenR;
//        int tokensNumber;
//        int maxPartLevel;
//        int lPos;
//        int rPos;
//        int tokenIndex;
//        int pos;
//        int p;
//        ArrayList<Integer> commas = null;
//        /* While exist token which needs to bee evaluated */
//
//        do {
//            tokensNumber = tokensList.size();
//            maxPartLevel = -1;
//            lPos = -1;
//            rPos = -1;
//            /*
//             * initializing tokens types positions
//             */
//            calculusPos = -1;
//            ifPos = -1;
//            iffPos = -1;
//            variadicFunPos = -1;
//            recArgPos = -1;
//            f3ArgPos = -1;
//            f2ArgPos = -1;
//            f1ArgPos = -1;
//            userFunPos = -1;
//            plusPos = -1;
//            minusPos = -1;
//            multiplyPos = -1;
//            dividePos = -1;
//            powerPos = -1;
//            factPos = -1;
//            modPos = -1;
//            powerNum = 0;
//            negPos = -1;
//            bolPos = -1;
//            eqPos = -1;
//            neqPos = -1;
//            ltPos = -1;
//            gtPos = -1;
//            leqPos = -1;
//            geqPos = -1;
//            commaPos = -1;
//            lParPos = -1;
//            rParPos = -1;
//            bitwisePos = -1;
//            bitwiseComplPos = -1;
//            tokensNumber = tokensList.size();
////            /* calculus operations ... */
////            p = -1;
////            do {
////                p++;
////                token = tokensList.get(p);
////                if (token.tokenTypeId == CalculusOperator.TYPE_ID)
////                    calculusPos = p;
////            } while ( (p < tokensNumber-1 ) && (calculusPos < 0) );
//
////            /* if operations ... */
////            if (calculusPos < 0) {
////                p = -1;
////                do {
////                    p++;
////                    token = tokensList.get(p);
////                    if ( (token.tokenTypeId == Function3Arg.TYPE_ID) && (token.tokenId == Function3Arg.IF_CONDITION_ID) )
////                        ifPos = p;
////                } while ( (p < tokensNumber-1 ) && (ifPos < 0) );
////            }
//
////            /* iff operations ... */
////            if ( (calculusPos < 0) && (ifPos < 0) ) {
////                p = -1;
////                do {
////                    p++;
////                    token = tokensList.get(p);
////                    if ( (token.tokenTypeId == FunctionVariadic.TYPE_ID) && (token.tokenId == FunctionVariadic.IFF_ID) )
////                        iffPos = p;
////                } while ( (p < tokensNumber-1 ) && (iffPos < 0 ) );
////            }
//
//            if ((calculusPos < 0) && (ifPos < 0) && (iffPos < 0)) {
//                /* Find start index of the tokens with the highest level */
//                for (tokenIndex = 0; tokenIndex < tokensNumber; tokenIndex++) {
//                    token = tokensList.get(tokenIndex);
//                    if (token.tokenLevel > maxPartLevel) {
//                        maxPartLevel = tokensList.get(tokenIndex).tokenLevel;
//                        lPos = tokenIndex;
//                    }
////                    if (token.tokenTypeId == Argument.TYPE_ID)
////                        ARGUMENT(tokenIndex);
////                    else if (token.tokenTypeId == ConstantValue.TYPE_ID)
////                        CONSTANT(tokenIndex);
////                    else if (token.tokenTypeId == Unit.TYPE_ID)
////                        UNIT(tokenIndex);
////                    else if (token.tokenTypeId == Constant.TYPE_ID)
////                        USER_CONSTANT(tokenIndex);
////                    else if (token.tokenTypeId == RandomVariable.TYPE_ID)
////                        RANDOM_VARIABLE(tokenIndex);
//                }
//                tokenIndex = lPos;
//                /* Find end index of the tokens with the highest level */
//                while ((tokenIndex < tokensNumber) && (maxPartLevel == tokensList.get(tokenIndex).tokenLevel))
//                    tokenIndex++;
//
//                rPos = tokenIndex - 1;
//
//                /* if no calculus operations were found
//                 * check for other tokens
//                 */
//                boolean leftIsNUmber;
//                boolean rigthIsNUmber;
//
//                for (pos = lPos; pos <= rPos; pos++) {
//                    leftIsNUmber = false;
//                    rigthIsNUmber = false;
//                    token = tokensList.get(pos);
//                    if (pos - 1 >= 0) {
//                        tokenL = tokensList.get(pos - 1);
//                        if (tokenL.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) leftIsNUmber = true;
//                    }
//                    if (pos + 1 < tokensNumber) {
//                        tokenR = tokensList.get(pos + 1);
//                        if (tokenR.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) rigthIsNUmber = true;
//                    }
//
//                    if ((token.tokenTypeId == Function1Arg.TYPE_ID) && (f1ArgPos < 0))
//                        f1ArgPos = pos;
//                    else if (token.tokenTypeId == Operator.TYPE_ID) {
//                        if ((token.tokenId == Operator.POWER_ID) && (leftIsNUmber && rigthIsNUmber)) {
//                            powerPos = pos;
//                            powerNum++;
//                        } else if ((token.tokenId == Operator.FACT_ID) && (factPos < 0) && (leftIsNUmber)) {
//                            factPos = pos;
//                        } else if ((token.tokenId == Operator.MOD_ID) && (modPos < 0) && (leftIsNUmber && rigthIsNUmber)) {
//                            modPos = pos;
//                        } else if ((token.tokenId == Operator.PLUS_ID) && (plusPos < 0) && (leftIsNUmber && rigthIsNUmber))
//                            plusPos = pos;
//                        else if ((token.tokenId == Operator.MINUS_ID) && (minusPos < 0) && (rigthIsNUmber))
//                            minusPos = pos;
//                        else if ((token.tokenId == Operator.MULTIPLY_ID) && (multiplyPos < 0) && (leftIsNUmber && rigthIsNUmber))
//                            multiplyPos = pos;
//                        else if ((token.tokenId == Operator.DIVIDE_ID) && (dividePos < 0) && (leftIsNUmber && rigthIsNUmber))
//                            dividePos = pos;
//                    } else if (token.tokenTypeId == ParserSymbol.TYPE_ID) {
//                        if ((token.tokenId == ParserSymbol.COMMA_ID)) {
//                            if (commaPos < 0)
//                                commas = new ArrayList<Integer>();
//                            commas.add(pos);
//                            commaPos = pos;
//                        } else if ((token.tokenId == ParserSymbol.LEFT_PARENTHESES_ID) && (lParPos < 0))
//                            lParPos = pos;
//                        else if ((token.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID) && (rParPos < 0))
//                            rParPos = pos;
//                    }
//                }
//                /*
//                 * powering should be done using backwards sequence
//                 */
//                if (powerNum > 1) {
//                    powerPos = -1;
//                    p = rPos + 1;
//                    do {
//                        p--;
//                        token = tokensList.get(p);
//                        if ((token.tokenTypeId == Operator.TYPE_ID) && (token.tokenId == Operator.POWER_ID))
//                            powerPos = p;
//                    } while ((p > lPos) && (powerPos == -1));
//                }
//            }
//
//            /* calculus operations */
////            if (calculusPos >= 0) calculusCalc(calculusPos);
////            else if (ifPos >= 0) {
////                IF_CONDITION(ifPos);
////            } else if (iffPos >= 0) {
////                IFF(iffPos);
////            } else                /* ... arguments ... */
////                /* ... recursive arguments ... */
////            if (recArgPos >= 0) {
////                RECURSIVE_ARGUMENT(recArgPos);
////            } else
////                    /* ... variadic functions  ... */
////            if (variadicFunPos >= 0) variadicFunCalc(variadicFunPos);
////            else
////            /* ... 3-args functions  ... */
////            if (f3ArgPos >= 0) f3ArgCalc(f3ArgPos);
////            else
////            /* ... 2-args functions  ... */
////            if (f2ArgPos >= 0) f2ArgCalc(f2ArgPos);
////            else
//            if (f1ArgPos >= 0) f1ArgCalc(f1ArgPos);
//            else
//                /* ... powering  ... */
//                if (powerPos >= 0) {
//                    POWER(powerPos);
//                } else if (factPos >= 0) {
//                    FACT(factPos);
//                } else if (modPos >= 0) {
//                    MODULO(modPos);
//                } else
////            if (negPos >= 0)
////                NEG(negPos);
////            else
//                    /* ... arithmetical operators  ... */
//                    if ((multiplyPos >= 0) || (dividePos >= 0)) {
//                        if ((multiplyPos >= 0) && (dividePos >= 0))
//                            if (multiplyPos <= dividePos)
//                                MULTIPLY(multiplyPos);
//                            else
//                                DIVIDE(dividePos);
//                        else if (multiplyPos >= 0)
//                            MULTIPLY(multiplyPos);
//                        else
//                            DIVIDE(dividePos);
//                    } else if ((minusPos >= 0) || (plusPos >= 0)) {
//                        if ((minusPos >= 0) && (plusPos >= 0))
//                            if (minusPos <= plusPos)
//                                MINUS(minusPos);
//                            else
//                                PLUS(plusPos);
//                        else if (minusPos >= 0)
//                            MINUS(minusPos);
//                        else
//                            PLUS(plusPos);
//                    } else if ((lParPos >= 0) && (rParPos > lParPos)) {
//                        PARENTHESES(lParPos, rParPos);
//                    } else if (tokensList.size() > 1) {
//                        this.errorMessage = errorMessage + "\n" + "[" + description + "][" + expressionString + "] " + "Fatal error - not know what to do with tokens while calculate().";
//                    }
//
//        } while (tokensList.size() > 1);
//
//        return tokensList.get(0).tokenValue;
//    }
//
//    /**
//     * Calculates unary function
//     *
//     * @param pos token position
//     */
//    private void f1ArgCalc(int pos) {
//        switch (tokensList.get(pos).tokenId) {
//            case Function1Arg.SIN_ID:
//                SIN(pos);
//                break;
////            case Function1Arg.COS_ID: COS(pos); break;
////            case Function1Arg.TAN_ID: TAN(pos); break;
////            case Function1Arg.CTAN_ID: CTAN(pos); break;
////            case Function1Arg.SEC_ID: SEC(pos); break;
////            case Function1Arg.COSEC_ID: COSEC(pos); break;
////            case Function1Arg.ASIN_ID: ASIN(pos); break;
////            case Function1Arg.ACOS_ID: ACOS(pos); break;
////            case Function1Arg.ATAN_ID: ATAN(pos); break;
////            case Function1Arg.ACTAN_ID: ACTAN(pos); break;
////            case Function1Arg.LN_ID: LN(pos); break;
////            case Function1Arg.LOG2_ID: LOG2(pos); break;
////            case Function1Arg.LOG10_ID: LOG10(pos); break;
////            case Function1Arg.RAD_ID: RAD(pos); break;
////            case Function1Arg.EXP_ID: EXP(pos); break;
////            case Function1Arg.SQRT_ID: SQRT(pos); break;
////            case Function1Arg.SINH_ID: SINH(pos); break;
////            case Function1Arg.COSH_ID: COSH(pos); break;
////            case Function1Arg.TANH_ID: TANH(pos); break;
////            case Function1Arg.COTH_ID: COTH(pos); break;
////            case Function1Arg.SECH_ID: SECH(pos); break;
////            case Function1Arg.CSCH_ID: CSCH(pos); break;
////            case Function1Arg.DEG_ID: DEG(pos); break;
////            case Function1Arg.ABS_ID: ABS(pos); break;
////            case Function1Arg.SGN_ID: SGN(pos); break;
////            case Function1Arg.FLOOR_ID: FLOOR(pos); break;
////            case Function1Arg.CEIL_ID: CEIL(pos); break;
////            case Function1Arg.NOT_ID: NOT(pos); break;
////            case Function1Arg.ARSINH_ID: ARSINH(pos); break;
////            case Function1Arg.ARCOSH_ID: ARCOSH(pos); break;
////            case Function1Arg.ARTANH_ID: ARTANH(pos); break;
////            case Function1Arg.ARCOTH_ID: ARCOTH(pos); break;
////            case Function1Arg.ARSECH_ID: ARSECH(pos); break;
////            case Function1Arg.ARCSCH_ID: ARCSCH(pos); break;
////            case Function1Arg.SA_ID: SA(pos); break;
////            case Function1Arg.SINC_ID: SINC(pos); break;
////            case Function1Arg.BELL_NUMBER_ID: BELL_NUMBER(pos); break;
////            case Function1Arg.LUCAS_NUMBER_ID: LUCAS_NUMBER(pos); break;
////            case Function1Arg.FIBONACCI_NUMBER_ID: FIBONACCI_NUMBER(pos); break;
////            case Function1Arg.HARMONIC_NUMBER_ID: HARMONIC_NUMBER(pos); break;
////            case Function1Arg.IS_PRIME_ID: IS_PRIME(pos); break;
////            case Function1Arg.PRIME_COUNT_ID: PRIME_COUNT(pos); break;
////            case Function1Arg.EXP_INT_ID: EXP_INT(pos); break;
////            case Function1Arg.LOG_INT_ID: LOG_INT(pos); break;
////            case Function1Arg.OFF_LOG_INT_ID: OFF_LOG_INT(pos); break;
////            case Function1Arg.GAUSS_ERF_ID: GAUSS_ERF(pos); break;
////            case Function1Arg.GAUSS_ERFC_ID: GAUSS_ERFC(pos); break;
////            case Function1Arg.GAUSS_ERF_INV_ID: GAUSS_ERF_INV(pos); break;
////            case Function1Arg.GAUSS_ERFC_INV_ID: GAUSS_ERFC_INV(pos); break;
////            case Function1Arg.ULP_ID: ULP(pos); break;
//        }
//    }
//
//
//    /**
//     * SetDecreaseRemove for 1 arg functions
//     * <p>
//     * SetDecreaseRemove like methods are called by the methods
//     * calculating values of the unary operation, binary relations
//     * and functions.
//     * <p>
//     * 3 things are done by this type of methods
//     * 1) Set token type to number type / value
//     * 2) Decrease level of the token
//     * 3) Remove no longer needed tokens
//     * <p>
//     * For example:
//     * <p>
//     * Expression string: 1+cos(0)
//     * will be tokened as follows:
//     * <p>
//     * idx   :  0   1    2    3   4   5
//     * token :  1   +   cos   (   0   )
//     * level :  0   0    1    2   2   2
//     * <p>
//     * Partitions with the highest level will be handled first.
//     * In the case presented above, it means, that the parenthesis will be removed
//     * <p>
//     * idx   :  0   1    2    3
//     * token :  1   +   cos   0
//     * level :  0   0    1    2
//     * <p>
//     * Next step is to calculate cos(0) = 1
//     * <p>
//     * SetDecreaseRemove like methods
//     * <p>
//     * 1) Set cos token to 1 (pos=2, result=1):
//     * idx   :  0   1    2    3
//     * token :  1   +    1    0
//     * level :  0   0    1    2
//     * <p>
//     * 2) Decrease level (pos=2):
//     * idx   :  0   1    2    3
//     * token :  1   +    1    0
//     * level :  0   0    0    2
//     * <p>
//     * 3) Remove no longer needed tokens (pos+1=3):
//     * idx   :  0   1    2
//     * token :  1   +    1
//     * level :  0   0    0
//     *
//     * @param pos      the position on which token
//     *                 should be updated to the given number
//     * @param result   the number
//     * @param ulpRound If true,
//     *                 intelligent ULP rounding is applied.
//     */
//    private void f1SetDecreaseRemove(int pos, double result, boolean ulpRound) {
//        setToNumber(pos, result, ulpRound);
//        tokensList.get(pos).tokenLevel--;
//        tokensList.remove(pos + 1);
//    }
//
//    private void f1SetDecreaseRemove(int pos, double result) {
//        f1SetDecreaseRemove(pos, result, false);
//    }
//
//    /**
//     * Sets given token to the number type / value.
//     * Method should be called only by the SetDecreaseRemove like methods
//     *
//     * @param pos      the position on which token
//     *                 should be updated to the given number
//     * @param number   the number
//     * @param ulpRound If true, then if
//     *                 intelligent ULP rounding is applied.
//     */
//    private void setToNumber(int pos, double number, boolean ulpRound) {
//        Token token = tokensList.get(pos);
//        token.tokenValue = number;
//        token.tokenTypeId = ParserSymbol.NUMBER_TYPE_ID;
//        token.tokenId = ParserSymbol.NUMBER_ID;
//        token.keyWord = ParserSymbol.NUMBER_STR;
//    }
//
//    private void setToNumber(int pos, double number) {
//        setToNumber(pos, number, false);
//    }
//
//    /**
//     * Gets token value
//     *
//     * @param tokenIndex the token index
//     * @return the token value
//     */
//    private double getTokenValue(int tokenIndex) {
//        return tokensList.get(tokenIndex).tokenValue;
//    }
//
//    /**
//     * Sine function
//     *
//     * @param pos the token position
//     */
//    private void SIN(int pos) {
//        double a = getTokenValue(pos + 1);
//        f1SetDecreaseRemove(pos, MathFunctions.sin(a));
//    }
//
//
//    /**
//     * Power handling.
//     *
//     * @param pos the token position
//     */
//    private void POWER(int pos) {
//        double a = getTokenValue(pos - 1);
//        double b = getTokenValue(pos + 1);
//        opSetDecreaseRemove(pos, MathFunctions.power(a, b), true);
//    }
//
//    /**
//     * Factorilal function
//     * Sets tokens to number token
//     *
//     * @param pos the token position
//     */
//    private void FACT(int pos) {
//        double a = getTokenValue(pos - 1);
//        setToNumber(pos, MathFunctions.factorial(a));
//        tokensList.remove(pos - 1);
//    }
//
//    /**
//     * Modulo handling.
//     *
//     * @param pos the token position
//     */
//    private void MODULO(int pos) {
//        double a = getTokenValue(pos - 1);
//        double b = getTokenValue(pos + 1);
//        opSetDecreaseRemove(pos, MathFunctions.mod(a, b));
//    }
//
//    /**
//     * Multiplication handling.
//     *
//     * @param pos the token position
//     */
//    private void MULTIPLY(int pos) {
//        double a = getTokenValue(pos - 1);
//        double b = getTokenValue(pos + 1);
//        opSetDecreaseRemove(pos, a * b, true);
//    }
//
//    /**
//     * Division handling.
//     *
//     * @param pos the token position
//     */
//    private void DIVIDE(int pos) {
//        double a = getTokenValue(pos - 1);
//        double b = getTokenValue(pos + 1);
//        opSetDecreaseRemove(pos, MathFunctions.div(a, b), true);
//    }
//
//    /**
//     * Subtraction handling
//     *
//     * @param pos the token position
//     */
//    private void MINUS(int pos) {
//        Token b = tokensList.get(pos + 1);
//        if (pos > 0) {
//            Token a = tokensList.get(pos - 1);
//            if ((a.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) && (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID))
//                opSetDecreaseRemove(pos, a.tokenValue - b.tokenValue, true);
//            else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
//                setToNumber(pos, -b.tokenValue);
//                tokensList.remove(pos + 1);
//            }
//        } else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
//            setToNumber(pos, -b.tokenValue);
//            tokensList.remove(pos + 1);
//        }
//    }
//
//    /**
//     * Addition handling.
//     *
//     * @param pos the token position
//     */
//    private void PLUS(int pos) {
//        Token b = tokensList.get(pos + 1);
//        if (pos > 0) {
//            Token a = tokensList.get(pos - 1);
//            if ((a.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) && (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID))
//                opSetDecreaseRemove(pos, a.tokenValue + b.tokenValue, true);
//            else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
//                setToNumber(pos, b.tokenValue);
//                tokensList.remove(pos + 1);
//            }
//        } else if (b.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
//            setToNumber(pos, b.tokenValue);
//            tokensList.remove(pos + 1);
//        }
//    }
//
//    /**
//     * Parser symbols
//     * Removes parenthesis
//     *
//     * @param lPos left token index (position)
//     * @param rPos roght token index (position)
//     */
//    private void PARENTHESES(int lPos, int rPos) {
//        for (int p = lPos; p <= rPos; p++)
//            tokensList.get(p).tokenLevel--;
//        tokensList.remove(rPos);
//        tokensList.remove(lPos);
//    }
//
//    /**
//     * SetDecreaseRemove for operators
//     * <p>
//     * For detailed specification refer to the
//     * f1SetDecreaseRemove()
//     *
//     * @param pos      the position on which token
//     *                 should be updated to the given number
//     * @param result   the number
//     * @param ulpRound If true,
//     *                 intelligent ULP rounding is applied.
//     */
//    private void opSetDecreaseRemove(int pos, double result, boolean ulpRound) {
//        setToNumber(pos, result, ulpRound);
//        tokensList.remove(pos + 1);
//        tokensList.remove(pos - 1);
//    }
//
//    private void opSetDecreaseRemove(int pos, double result) {
//        opSetDecreaseRemove(pos, result, false);
//    }
//
//    private void tokenizeExpressionString() {
//        /*
//         * Add parser and argument key words
//         */
//
//        keyWordsList = new ArrayList<>();
//        addParserKeyWords();
//
//        java.util.Collections.sort(keyWordsList, new DescKwLenComparator());
//        /*
//         * Evaluate position after sorting for the following keywords types
//         *    number
//         *    plus operator
//         *    minus operator
//         *
//         * Above mentioned information is required
//         * when distinguishing between numbers (regexp) and operators
//         *
//         * For example
//         *
//         * 1-2 : two numbers and one operator, but -2 is also a valid number
//         * (-2)+3 : two number and one operator
//         */
//        int numberKwId = ConstantValue.NaN;
//        int plusKwId = ConstantValue.NaN;
//        int minusKwId = ConstantValue.NaN;
//        for (int kwId = 0; kwId < keyWordsList.size(); kwId++) {
//            if (keyWordsList.get(kwId).getWordTypeId() == ParserSymbol.NUMBER_TYPE_ID)
//                numberKwId = kwId;
//            if (keyWordsList.get(kwId).getWordTypeId() == Operator.TYPE_ID) {
//                if (keyWordsList.get(kwId).getWordId() == Operator.PLUS_ID)
//                    plusKwId = kwId;
//                if (keyWordsList.get(kwId).getWordId() == Operator.MINUS_ID)
//                    minusKwId = kwId;
//            }
//        }
//
//        String newExpressionString = new String(expressionString);
//        /*
//         * words list and tokens list
//         */
//        initialTokens = new ArrayList<>();
//        int lastPos = 0; /* position of the key word previously added*/
//        int pos = 0; /* current position */
//        String tokenStr = "";
//        int matchStatusPrev = NOT_FOUND; /* unknown key word (previous) */
//        int matchStatus = NOT_FOUND; /* unknown key word (current) */
//        KeyWord kw = null;
//        String sub = "";
//        String kwStr = "";
//        char precedingChar;
//        char followingChar;
//        char firstChar;
//        char c;
//        /*
//         * Check all available positions in the expression tokens list
//         */
//
//        do {
//            /*
//             * 1st step
//             *
//             * compare with the regExp for real numbers
//             * find the longest word which could be matched
//             * with the given regExp
//             */
//            int numEnd = -1;
//
//            c = newExpressionString.charAt(pos);
//
//            if (c == '+' || c == '-' || (c >= '0' && c <= '9')) {
//                for (int i = pos; i < newExpressionString.length(); i++) {
//                    /*
//                     * Escaping if encountering char that can not
//                     * be included in number
//                     */
//                    if (i > pos) {
//                        c = newExpressionString.charAt(i);
//                        if (c != '+' && c != '-' && !(c >= '0' && c <= '9')
//                                && c != '.') break;
//                    }
//
//                    /*
//                     * Checking if substring represents number
//                     */
//
//                    String str = newExpressionString.substring(pos, i + 1);
//                    if (Parser.regexMatch(str, ParserSymbol.NUMBER_REG_EXP))
//                        numEnd = i;
//                }
//            }
//
//            /*
//             * If number was found
//             */
//            if (numEnd >= 0)
//                if (pos > 0) {
//                    precedingChar = newExpressionString.charAt(pos - 1);
//                    if (
//                            (precedingChar != ',') &&
//                                    (precedingChar != ';') &&
//                                    (precedingChar != '|') &&
//                                    (precedingChar != '&') &&
//                                    (precedingChar != '+') &&
//                                    (precedingChar != '-') &&
//                                    (precedingChar != '*') &&
//                                    (precedingChar != '\\') &&
//                                    (precedingChar != '/') &&
//                                    (precedingChar != '(') &&
//                                    (precedingChar != ')') &&
//                                    (precedingChar != '=') &&
//                                    (precedingChar != '>') &&
//                                    (precedingChar != '<') &&
//                                    (precedingChar != '~') &&
//                                    (precedingChar != '^') &&
//                                    (precedingChar != '#') &&
//                                    (precedingChar != '%') &&
//                                    (precedingChar != '@') &&
//                                    (precedingChar != '!'))
//                        numEnd = -1;
//                }
//
//            if (numEnd >= 0)
//                if (numEnd < newExpressionString.length() - 1) {
//                    followingChar = newExpressionString.charAt(numEnd + 1);
//                    if (
//                            (followingChar != ',') &&
//                                    (followingChar != ';') &&
//                                    (followingChar != '|') &&
//                                    (followingChar != '&') &&
//                                    (followingChar != '+') &&
//                                    (followingChar != '-') &&
//                                    (followingChar != '*') &&
//                                    (followingChar != '\\') &&
//                                    (followingChar != '/') &&
//                                    (followingChar != '(') &&
//                                    (followingChar != ')') &&
//                                    (followingChar != '=') &&
//                                    (followingChar != '>') &&
//                                    (followingChar != '<') &&
//                                    (followingChar != '~') &&
//                                    (followingChar != '^') &&
//                                    (followingChar != '#') &&
//                                    (followingChar != '%') &&
//                                    (followingChar != '@') &&
//                                    (followingChar != '!'))
//                        numEnd = -1;
//                }
//
//            if (numEnd >= 0) {
//                /*
//                 * Check leading operators ('-' or '+')
//                 *
//                 * For example:
//                 *    '2-1' :  1(num) -(op) 2(num) = 1(num)
//                 *    -1+2  : -1(num) +(op) 2(num) = 1(num)
//                 */
//                firstChar = newExpressionString.charAt(pos);
//                boolean leadingOp = true;
//                if ((firstChar == '-') || (firstChar == '+')) {
//                    if (initialTokens.size() > 0) {
//                        Token lastToken = initialTokens.get(initialTokens.size() - 1);
//                        if (((lastToken.tokenTypeId == Operator.TYPE_ID) && (lastToken.tokenId != Operator.FACT_ID)) ||
////                                (lastToken.tokenTypeId == BinaryRelation.TYPE_ID) ||
////                                (lastToken.tokenTypeId == BooleanOperator.TYPE_ID) ||
////                                (lastToken.tokenTypeId == BitwiseOperator.TYPE_ID) ||
//                                ((lastToken.tokenTypeId == ParserSymbol.TYPE_ID) && (lastToken.tokenId == ParserSymbol.LEFT_PARENTHESES_ID)))
//                            leadingOp = false;
//                        else leadingOp = true;
//                    } else leadingOp = false;
//                } else leadingOp = false;
//
//                /*
//                 * If leading operator was found
//                 */
//                if (leadingOp == true) {
//                    /*
//                     * Add leading operator to the tokens list
//                     */
//                    if (firstChar == '-')
//                        addToken("-", keyWordsList.get(minusKwId));
//                    if (firstChar == '+')
//                        addToken("+", keyWordsList.get(plusKwId));
//                    pos++;
//                }
//
//                /*
//                 * Add found number to the tokens list
//                 */
//                tokenStr = newExpressionString.substring(pos, numEnd + 1);
//                addToken(tokenStr, keyWordsList.get(numberKwId));
//                /*
//                 * change current position (just after the number ends)
//                 */
//                pos = numEnd + 1;
//                lastPos = pos;
//                /*
//                 * Mark match status indicators
//                 */
//                matchStatus = FOUND;
//                matchStatusPrev = FOUND;
//            } else {
//                /*
//                 * If there is no number which starts with current position
//                 * Check for known key words
//                 */
//                int kwId = -1;
//                matchStatus = NOT_FOUND;
//                do {
//                    kwId++;
//                    kw = keyWordsList.get(kwId);
//                    kwStr = kw.getWordString();
//
//                    if (pos + kwStr.length() <= newExpressionString.length()) {
//                        sub = newExpressionString.substring(pos, pos + kwStr.length());
//                        if (sub.equals(kwStr))
//                            matchStatus = FOUND;
//                        /*
//                         * If key word is known by the parser
//                         */
//                        if (matchStatus == FOUND) {
//                            /*
//                             * If key word is in the form of identifier
//                             * then check preceding and following characters
//                             */
//                            int wordTypeID = kw.getWordTypeId();
//                            if (wordTypeID == Function1Arg.TYPE_ID) {
//                                /*
//                                 * Checking preceding character
//                                 */
//                                if (pos > 0) {
//                                    precedingChar = newExpressionString.charAt(pos - 1);
//                                    if ((precedingChar != ',') &&
//                                            (precedingChar != ';') &&
//                                            (precedingChar != '|') &&
//                                            (precedingChar != '&') &&
//                                            (precedingChar != '+') &&
//                                            (precedingChar != '-') &&
//                                            (precedingChar != '*') &&
//                                            (precedingChar != '\\') &&
//                                            (precedingChar != '/') &&
//                                            (precedingChar != '(') &&
//                                            (precedingChar != ')') &&
//                                            (precedingChar != '=') &&
//                                            (precedingChar != '>') &&
//                                            (precedingChar != '<') &&
//                                            (precedingChar != '~') &&
//                                            (precedingChar != '^') &&
//                                            (precedingChar != '#') &&
//                                            (precedingChar != '%') &&
//                                            (precedingChar != '@') &&
//                                            (precedingChar != '!')) matchStatus = NOT_FOUND;
//                                }
//                                /*
//                                 * Checking following character
//                                 */
//                                if ((matchStatus == FOUND) && (pos + kwStr.length() < newExpressionString.length())) {
//                                    followingChar = newExpressionString.charAt(pos + kwStr.length());
//                                    if ((followingChar != ',') &&
//                                            (followingChar != ';') &&
//                                            (followingChar != '|') &&
//                                            (followingChar != '&') &&
//                                            (followingChar != '+') &&
//                                            (followingChar != '-') &&
//                                            (followingChar != '*') &&
//                                            (followingChar != '\\') &&
//                                            (followingChar != '/') &&
//                                            (followingChar != '(') &&
//                                            (followingChar != ')') &&
//                                            (followingChar != '=') &&
//                                            (followingChar != '>') &&
//                                            (followingChar != '<') &&
//                                            (followingChar != '~') &&
//                                            (followingChar != '^') &&
//                                            (followingChar != '#') &&
//                                            (followingChar != '%') &&
//                                            (followingChar != '@') &&
//                                            (followingChar != '!')) matchStatus = NOT_FOUND;
//                                }
//
//                            }
//
//                        }
//                    }
//                } while ((kwId < keyWordsList.size() - 1) && (matchStatus == NOT_FOUND));
//
//                /*
//                 * If key word known by the parser was found
//                 */
//                if (matchStatus == FOUND) {
//                    /*
//                     * if preceding word was not known by the parser
//                     */
//                    if ((matchStatusPrev == NOT_FOUND) && (pos > 0)) {
//                        /*
//                         * Add preceding word to the tokens list
//                         * as unknown key word
//                         */
//                        tokenStr = newExpressionString.substring(lastPos, pos);
//                        addToken(tokenStr, new KeyWord());
//                    }
//                    matchStatusPrev = FOUND;
//                    /*
//                     * Add current (known by the parser)
//                     * key word to the tokens list
//                     */
//                    tokenStr = newExpressionString.substring(pos, pos + kwStr.length());
//                    addToken(tokenStr, kw);
//                    /*
//                     * Remember position where last added word ends + 1
//                     */
//                    lastPos = pos + kwStr.length();
//                    /*
//                     * Change current position;
//                     */
//                    pos = pos + kwStr.length();
//                } else {
//                    /*
//                     * Update preceding word indicator
//                     */
//                    matchStatusPrev = NOT_FOUND;
//                    /*
//                     * Increment position if possible
//                     */
//                    if (pos < newExpressionString.length())
//                        pos++;
//                }
//            }
//
//
//        } while (pos < newExpressionString.length());
//
//        /*
//         * If key word was not known by the parser
//         * and end with the string end
//         * it needs to be added to the tokens list
//         * as unknown key word
//         */
//        if (matchStatus == NOT_FOUND) {
//            tokenStr = newExpressionString.substring(lastPos, pos);
//            addToken(tokenStr, new KeyWord());
//        }
//        /*
//         * Evaluate tokens levels
//         *
//         * token level identifies the sequance of parsing
//         */
//        evaluateTokensLevels();
//    }
//
//    /**
//     * Evaluates tokens levels
//     */
//    private void evaluateTokensLevels() {
//        int tokenLevel = 0;
//        Stack<TokenStackElement> tokenStack = new Stack<TokenStackElement>();
//        boolean precedingFunction = false;
//        if (initialTokens.size() > 0)
//            for (int tokenIndex = 0; tokenIndex < initialTokens.size(); tokenIndex++) {
//                Token token = initialTokens.get(tokenIndex);
//                if ((token.tokenTypeId == Function1Arg.TYPE_ID) // ||
////                        ( token.tokenTypeId == Function2Arg.TYPE_ID ) ||
////                        ( token.tokenTypeId == Function3Arg.TYPE_ID )	||
////                        ( token.tokenTypeId == Function.TYPE_ID )	||
////                        ( token.tokenTypeId == CalculusOperator.TYPE_ID ) ||
////                        ( token.tokenTypeId == RecursiveArgument.TYPE_ID_RECURSIVE ) ||
////                        ( token.tokenTypeId == FunctionVariadic.TYPE_ID )
//                ) {
//                    tokenLevel++;
//                    precedingFunction = true;
//                } else if ((token.tokenTypeId == ParserSymbol.TYPE_ID) && (token.tokenId == ParserSymbol.LEFT_PARENTHESES_ID)) {
//                    tokenLevel++;
//                    TokenStackElement stackEl = new TokenStackElement();
//                    stackEl.tokenId = token.tokenId;
//                    stackEl.tokenIndex = tokenIndex;
//                    stackEl.tokenLevel = tokenLevel;
//                    stackEl.tokenTypeId = token.tokenTypeId;
//                    stackEl.precedingFunction = precedingFunction;
//                    tokenStack.push(stackEl);
//                    precedingFunction = false;
//                } else
//                    precedingFunction = false;
//                token.tokenLevel = tokenLevel;
//                if ((token.tokenTypeId == ParserSymbol.TYPE_ID) && (token.tokenId == ParserSymbol.RIGHT_PARENTHESES_ID)) {
//                    tokenLevel--;
//                    if (!tokenStack.isEmpty()) {
//                        TokenStackElement stackEl = tokenStack.pop();
//                        if (stackEl.precedingFunction == true)
//                            tokenLevel--;
//                    }
//                }
//            }
//    }
//
//    /**
//     * Creates parseres key words list
//     */
//    private void addParserKeyWords() {
//        /*
//         * Operators key words
//         */
//        addKeyWord(Operator.PLUS_STR, Operator.PLUS_DESC, Operator.PLUS_ID, Operator.TYPE_ID);
//        addKeyWord(Operator.MINUS_STR, Operator.MINUS_DESC, Operator.MINUS_ID, Operator.TYPE_ID);
//        addKeyWord(Operator.MULTIPLY_STR, Operator.MULTIPLY_DESC, Operator.MULTIPLY_ID, Operator.TYPE_ID);
//        addKeyWord(Operator.DIVIDE_STR, Operator.DIVIDE_DESC, Operator.DIVIDE_ID, Operator.TYPE_ID);
//        addKeyWord(Operator.POWER_STR, Operator.POWER_DESC, Operator.POWER_ID, Operator.TYPE_ID);
//        addKeyWord(Operator.FACT_STR, Operator.FACT_DESC, Operator.FACT_ID, Operator.TYPE_ID);
//        addKeyWord(Operator.MOD_STR, Operator.MOD_DESC, Operator.MOD_ID, Operator.TYPE_ID);
//
//        /*
//         * 1 arg functions key words
//         */
//        addKeyWord(Function1Arg.SIN_STR, Function1Arg.SIN_DESC, Function1Arg.SIN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.COS_STR, Function1Arg.COS_DESC, Function1Arg.COS_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.TAN_STR, Function1Arg.TAN_DESC, Function1Arg.TAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.TG_STR, Function1Arg.TAN_DESC, Function1Arg.TAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.CTAN_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.CTG_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.COT_STR, Function1Arg.CTAN_DESC, Function1Arg.CTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.SEC_STR, Function1Arg.SEC_DESC, Function1Arg.SEC_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.COSEC_STR, Function1Arg.COSEC_DESC, Function1Arg.COSEC_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.CSC_STR, Function1Arg.COSEC_DESC, Function1Arg.COSEC_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ASIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARSIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCSIN_STR, Function1Arg.ASIN_DESC, Function1Arg.ASIN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCOS_STR, Function1Arg.ACOS_DESC, Function1Arg.ACOS_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ATAN_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCTAN_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ATG_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCTG_STR, Function1Arg.ATAN_DESC, Function1Arg.ATAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACTAN_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCTAN_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACTG_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCTG_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACOT_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCOT_STR, Function1Arg.ACTAN_DESC, Function1Arg.ACTAN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.LN_STR, Function1Arg.LN_DESC, Function1Arg.LN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.LOG2_STR, Function1Arg.LOG2_DESC, Function1Arg.LOG2_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.LOG10_STR, Function1Arg.LOG10_DESC, Function1Arg.LOG10_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.RAD_STR, Function1Arg.RAD_DESC, Function1Arg.RAD_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.EXP_STR, Function1Arg.EXP_DESC, Function1Arg.EXP_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.SQRT_STR, Function1Arg.SQRT_DESC, Function1Arg.SQRT_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.SINH_STR, Function1Arg.SINH_DESC, Function1Arg.SINH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.COSH_STR, Function1Arg.COSH_DESC, Function1Arg.COSH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.TANH_STR, Function1Arg.TANH_DESC, Function1Arg.TANH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.TGH_STR, Function1Arg.TANH_DESC, Function1Arg.TANH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.CTANH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.COTH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.CTGH_STR, Function1Arg.COTH_DESC, Function1Arg.COTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.SECH_STR, Function1Arg.SECH_DESC, Function1Arg.SECH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.CSCH_STR, Function1Arg.CSCH_DESC, Function1Arg.CSCH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.COSECH_STR, Function1Arg.CSCH_DESC, Function1Arg.CSCH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.DEG_STR, Function1Arg.DEG_DESC, Function1Arg.DEG_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ABS_STR, Function1Arg.ABS_DESC, Function1Arg.ABS_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.SGN_STR, Function1Arg.SGN_DESC, Function1Arg.SGN_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.FLOOR_STR, Function1Arg.FLOOR_DESC, Function1Arg.FLOOR_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.CEIL_STR, Function1Arg.CEIL_DESC, Function1Arg.CEIL_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.NOT_STR, Function1Arg.NOT_DESC, Function1Arg.NOT_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ASINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARSINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCSINH_STR, Function1Arg.ARSINH_DESC, Function1Arg.ARSINH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCOSH_STR, Function1Arg.ARCOSH_DESC, Function1Arg.ARCOSH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ATANH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCTANH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ATGH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCTGH_STR, Function1Arg.ARTANH_DESC, Function1Arg.ARTANH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACTANH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCTANH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCOTH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACTGH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCTGH_STR, Function1Arg.ARCOTH_DESC, Function1Arg.ARCOTH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ASECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARSECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCSECH_STR, Function1Arg.ARSECH_DESC, Function1Arg.ARSECH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCSCH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ACOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ARCCOSECH_STR, Function1Arg.ARCSCH_DESC, Function1Arg.ARCSCH_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.SA_STR, Function1Arg.SA_DESC, Function1Arg.SA_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.SA1_STR, Function1Arg.SA_DESC, Function1Arg.SA_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.SINC_STR, Function1Arg.SINC_DESC, Function1Arg.SINC_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.BELL_NUMBER_STR, Function1Arg.BELL_NUMBER_DESC, Function1Arg.BELL_NUMBER_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.FIBONACCI_NUMBER_STR, Function1Arg.FIBONACCI_NUMBER_DESC, Function1Arg.FIBONACCI_NUMBER_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.LUCAS_NUMBER_STR, Function1Arg.LUCAS_NUMBER_DESC, Function1Arg.LUCAS_NUMBER_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.HARMONIC_NUMBER_STR, Function1Arg.HARMONIC_NUMBER_DESC, Function1Arg.HARMONIC_NUMBER_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.IS_PRIME_STR, Function1Arg.IS_PRIME_DESC, Function1Arg.IS_PRIME_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.PRIME_COUNT_STR, Function1Arg.PRIME_COUNT_DESC, Function1Arg.PRIME_COUNT_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.EXP_INT_STR, Function1Arg.EXP_INT_DESC, Function1Arg.EXP_INT_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.LOG_INT_STR, Function1Arg.LOG_INT_DESC, Function1Arg.LOG_INT_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.OFF_LOG_INT_STR, Function1Arg.OFF_LOG_INT_DESC, Function1Arg.OFF_LOG_INT_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.GAUSS_ERF_STR, Function1Arg.GAUSS_ERF_DESC, Function1Arg.GAUSS_ERF_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.GAUSS_ERFC_STR, Function1Arg.GAUSS_ERFC_DESC, Function1Arg.GAUSS_ERFC_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.GAUSS_ERF_INV_STR, Function1Arg.GAUSS_ERF_INV_DESC, Function1Arg.GAUSS_ERF_INV_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.GAUSS_ERFC_INV_STR, Function1Arg.GAUSS_ERFC_INV_DESC, Function1Arg.GAUSS_ERFC_INV_ID, Function1Arg.TYPE_ID);
//        addKeyWord(Function1Arg.ULP_STR, Function1Arg.ULP_DESC, Function1Arg.ULP_ID, Function1Arg.TYPE_ID);
//
//        /*
//         * Other parser symbols key words
//         */
//        addKeyWord(ParserSymbol.LEFT_PARENTHESES_STR, ParserSymbol.LEFT_PARENTHESES_DESC, ParserSymbol.LEFT_PARENTHESES_ID, ParserSymbol.TYPE_ID);
//        addKeyWord(ParserSymbol.RIGHT_PARENTHESES_STR, ParserSymbol.RIGHT_PARENTHESES_DESC, ParserSymbol.RIGHT_PARENTHESES_ID, ParserSymbol.TYPE_ID);
//        addKeyWord(ParserSymbol.COMMA_STR, ParserSymbol.COMMA_DESC, ParserSymbol.COMMA_ID, ParserSymbol.TYPE_ID);
//        addKeyWord(ParserSymbol.SEMI_STR, ParserSymbol.SEMI_DESC, ParserSymbol.COMMA_ID, ParserSymbol.TYPE_ID);
//        addKeyWord(ParserSymbol.NUMBER_REG_EXP, ParserSymbol.NUMBER_REG_DESC, ParserSymbol.NUMBER_ID, ParserSymbol.NUMBER_TYPE_ID);
//
//    }
//
//    /**
//     * Adds key word to the keyWords list
//     *
//     * @param wordString
//     * @param wordDescription
//     * @param wordId
//     * @param wordTypeId
//     */
//    private void addKeyWord(String wordString, String wordDescription, int wordId, int wordTypeId) {
//        keyWordsList.add(new KeyWord(wordString, wordDescription, wordId, wordTypeId));
//    }
//
//    /**
//     * Adds expression token
//     * Method is called by the tokenExpressionString()
//     * while parsing string expression
//     *
//     * @param tokenStr the token string
//     * @param keyWord  the key word
//     */
//    private void addToken(String tokenStr, KeyWord keyWord) {
//        Token token = new Token();
//        initialTokens.add(token);
//        token.tokenStr = tokenStr;
//        token.keyWord = keyWord.getWordString();
//        token.tokenId = keyWord.getWordId();
//        token.tokenTypeId = keyWord.getWordTypeId();
////        if (token.tokenTypeId == Argument.TYPE_ID)
////            token.tokenValue = argumentsList.get(token.tokenId).argumentValue;
////        else
//        if (token.tokenTypeId == ParserSymbol.NUMBER_TYPE_ID) {
//            token.tokenValue = Double.valueOf(token.tokenStr);
//            token.keyWord = ParserSymbol.NUMBER_STR;
//        }
//    }
//}
