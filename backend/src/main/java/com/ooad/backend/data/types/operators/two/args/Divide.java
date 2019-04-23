package com.ooad.backend.data.types.operators.two.args;

import com.ooad.backend.data.types.operators.Operator2Arg;
import com.ooad.backend.data.types.operators.Utils;
import org.apache.commons.math3.complex.Complex;

public class Divide implements Operator2Arg {
    @Override
    public Double apply(Double d1, Double d2) {
        if (!Utils.checkNonZero(d2))
            throw new RuntimeException("Divide by Zero");
        return d1 / d2;
    }

    @Override
    public Double apply(Integer d1, Integer d2) {
        if (!Utils.checkNonZero(d2))
            throw new RuntimeException("Divide by Zero");
        return d1 * 1.0 / d2;
    }

    @Override
    public Double apply(Double d1, Integer d2) {
        if (!Utils.checkNonZero(d2))
            throw new RuntimeException("Divide by Zero");
        return d1 / d2;
    }

    @Override
    public Double apply(Integer d1, Double d2) {
        if (!Utils.checkNonZero(d2))
            throw new RuntimeException("Divide by Zero");
        return d1 / d2;
    }

    @Override
    public Complex apply(Complex d1, Complex d2) {
        return d1.divide(d2);
    }

    @Override
    public Complex apply(Complex d1, Double d2) {
        return d1.divide(d2);
    }

    @Override
    public Complex apply(Double d1, Complex d2) {
        return d2.reciprocal().multiply(d1);
    }

    @Override
    public Complex apply(Complex d1, Integer d2) {
        return d1.divide(d2);
    }

    @Override
    public Complex apply(Integer d1, Complex d2) {
        return d2.reciprocal().divide(d2);
    }
}
