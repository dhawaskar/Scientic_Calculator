package com.ooad.frontend.misc;

/**
 * Class implements methos to parse the input string for matrix operation into integers
 */
public class Utils {
    /**
     * String to Integer array conversion
     * @param str
     * @return Integer Array
     */
    public static Integer[] stringToIntegerArray(String str){
        String[] strArray = str.split(",");
        Integer[] mnArray = new Integer[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            mnArray[i] = Integer.valueOf(strArray[i]);
        }

        return mnArray;
    }

    public static Double[] stringToDoublerArray(String str){
        String[] strArray = str.split(",");
        Double[] mnArray = new Double[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            mnArray[i] = Double.valueOf(strArray[i]);
        }

        return mnArray;
    }
}
