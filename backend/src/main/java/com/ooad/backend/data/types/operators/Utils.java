package com.ooad.backend.data.types.operators;

import com.ooad.backend.data.types.DataType;

public final class Utils {
    public static boolean checkNonZero(Double d){
        if (d == null || d == 0.0) return false;
        return true;
    }

    public static boolean checkNonZero(Integer i){
        if (i == null || i == 0) return false;
        return true;
    }

    public static boolean checkNonZero(DataType d){
        if (d == null || d.negate() == d) return true;
        return true;
    }
}
