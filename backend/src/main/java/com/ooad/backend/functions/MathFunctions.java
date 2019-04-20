package com.ooad.backend.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathFunctions {

    public static double binomCoeff(double n, int k) {
        if (Double.isNaN(n))
            return Double.NaN;
        double result = Double.NaN;
        if (k >= 0) {
            double numerator = 1;
            if (k > 0)
                for (int i = 0; i <= k - 1; i++)
                    numerator *= (n - i);
            double denominator = 1;
            if (k > 1)
                for (int i = 1; i <= k; i++)
                    denominator *= i;
            result = numerator / denominator;
        }
        return result;
    }

    public static double factorial(int n) {
        double f = Double.NaN;
        if (n >= 0)
            if (n < 2) f = 1;
            else {
                f = 1;
                for (int i = 1; i <= n; i++)
                    f = f * i;
            }
        return f;
    }

    public static final double factorial(double n) {
        if (Double.isNaN(n))
            return Double.NaN;
        return factorial( (int)Math.round(n) );
    }

    public static double power(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b))
            return Double.NaN;
        return Math.pow(a, b);
    }


    public static double mod(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b))
            return Double.NaN;
        return a % b;
    }


    public static double div(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b))
            return Double.NaN;
        double result = Double.NaN;
        if (b != 0)
            result = a / b;
        return result;
    }

    public static double sin(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.sin(a);
    }

    public static double cos(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.cos(a);
    }

    public static double tan(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.tan(a);
    }

    public static double ctan(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        double tg = Math.tan(a);
        if (tg != 0)
            result = 1.0 / tg;
        return result;
    }

    public static double sec(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        double cos = Math.cos(a);
        if (cos != 0)
            result = 1.0 / cos;
        return result;
    }


    public static double cosec(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        double sin = Math.sin(a);
        if (sin != 0)
            result = 1.0 / sin;
        return result;
    }

    public static double asin(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.asin(a);
    }

    public static double acos(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.acos(a);
    }

    public static double atan(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.atan(a);
    }

    public static double actan(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.atan(1 / a);
    }

    public static double ln(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.log(a);
    }

    public static double logN(double a, double n) {
        if (Double.isNaN(a) || Double.isNaN(n))
            return Double.NaN;
        double logn = Math.log(n);
        return logn != 0 ? Math.log(a) / Math.log(n) : Double.NaN;
    }


    public static double degreeToRad(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.toRadians(a);
    }

    public static double exp(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.exp(a);
    }

    public static double sqrt(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.sqrt(a);
    }

    public static double sinh(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.sinh(a);
    }

    public static double cosh(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.cosh(a);
    }

    public static double tanh(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.tanh(a);
    }

    public static double coth(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        double tanh = Math.tanh(a);
        if (tanh != 0)
            result = 1.0 / tanh;
        return result;
    }

    public static double sech(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        double cosh = Math.cosh(a);
        if (cosh != 0)
            result = 1.0 / cosh;
        return result;
    }

    public static double csch(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        double sinh = Math.sinh(a);
        if (sinh != 0)
            result = 1.0 / sinh;
        return result;
    }

    public static double radianToDeg(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.toDegrees(a);
    }

    public static double abs(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.abs(a);
    }

    /**
     * Signum function
     *
     * @param a
     * @return
     */
    public static double sgn(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.signum(a);
    }

    public static double floor(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.floor(a);
    }

    public static double ceil(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.ceil(a);
    }

    public static double arsinh(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.log(a + Math.sqrt(a * a + 1));
    }

    public static double arcosh(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        return Math.log(a + Math.sqrt(a * a - 1));
    }

    public static double artanh(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        if (1 - a != 0)
            result = 0.5 * Math.log((1 + a) / (1 - a));
        return result;
    }

    public static double arcoth(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        if (a - 1 != 0)
            result = 0.5 * Math.log((a + 1) / (a - 1));
        return result;
    }

    public static double arsech(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        if (a != 0)
            result = Math.log((1 + Math.sqrt(1 - a * a)) / a);
        return result;
    }

    public static double arcsch(double a) {
        if (Double.isNaN(a))
            return Double.NaN;
        double result = Double.NaN;
        if (a != 0)
            result = Math.log(1 / a + Math.sqrt(1 + a * a) / Math.abs(a));
        return result;
    }

    public static double round(double value, int places) {
        if (Double.isNaN(value)) return Double.NaN;
        if (places < 0) return Double.NaN;
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
