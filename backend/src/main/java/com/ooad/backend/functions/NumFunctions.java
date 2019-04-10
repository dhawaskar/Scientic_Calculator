package com.ooad.backend.functions;

import com.google.common.math.IntMath;
import com.google.common.primitives.Doubles;

public final class NumFunctions {
    public static double min(double... numbers) {
        if (numbers == null || numbers.length == 0)
            return Double.POSITIVE_INFINITY;
        return Doubles.min(numbers);
    }

    public static double max(double... numbers) {
        if (numbers == null || numbers.length == 0)
            return Double.POSITIVE_INFINITY;
        return Doubles.max(numbers);
    }

    public static double gcd(int... numbers) {
        if (numbers.length == 1)
            return numbers[0];
        if (numbers.length == 2)
            return IntMath.gcd(numbers[0], numbers[1]);
        for (int i = 1; i < numbers.length; i++)
            numbers[i] = IntMath.gcd(numbers[i - 1], numbers[i]);
        return numbers[numbers.length - 1];
    }

    public static double sum(double... numbers) {
        if (numbers.length == 0) return Double.NaN;
        if (numbers.length == 1) return numbers[0];
        double sum = 0;
        for (double xi : numbers) {
            if ( Double.isNaN(xi) )
                return Double.NaN;
            sum+=xi;
        }
        return sum;
    }

    public static double prod(double... numbers) {
        if (numbers.length == 0) return Double.NaN;
        if (numbers.length == 1) return numbers[0];
        double prod = 1;
        for (double xi : numbers) {
            if ( Double.isNaN(xi) )
                return Double.NaN;
            prod*=xi;
        }
        return prod;
    }
}
