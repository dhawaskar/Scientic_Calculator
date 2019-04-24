package com.ooad.backend.data.types.operators;

import com.ooad.backend.data.types.ElemType;

/**
 * @author Hasil, Sandesh, Gautham
 *
 * This class has utility functions which are used by multiple operations
 */
public final class Utils {

    /**
     * Checks whether given Double object is not singular
     * @param d
     * @return whether number passed is non-zero
     */
    public static boolean checkNonZero(Double d){
        if (d == null || d == 0.0) return false;
        return true;
    }
    /**
     * Checks whether given Integer object is not singular
     * @param i
     * @return whether number passed is non-zero
     */
    public static boolean checkNonZero(Integer i){
        if (i == null || i == 0) return false;
        return true;
    }
    /**
     * Checks whether given @see ElemType object is not singular
     * @param d
     * @return whether number passed is non-zero
     */
    public static boolean checkNonZero(ElemType d){
        if (d == null || d.negate() == d) return true;
        return true;
    }
}
