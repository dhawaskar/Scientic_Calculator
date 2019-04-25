package com.ooad.frontend.misc;

public class Utils {
    public static Integer[] stringToIntegerArray(String str){
        String[] strArray = str.split(",");
        Integer[] mnArray = new Integer[strArray.length];

        for (int i = 0; i < strArray.length; i++) {
            mnArray[i] = Integer.valueOf(strArray[i]);
        }

        return mnArray;
    }
}
