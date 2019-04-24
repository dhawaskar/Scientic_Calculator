package com.ooad.backend.data.types.operators.two.args;

import com.ooad.backend.data.types.operators.Operator2Arg;
import org.apache.commons.math3.complex.Complex;

/**
 * @author Hasil, Sandesh, Gautham
 *
 * Implements interface Operator2Arg and supports the multiplication operation between two entities of different types, as
 * dictated by the interface
 */
public class Multiply implements Operator2Arg {
    @Override
    public Double apply(Double d1, Double d2) {
        return d1 * d2;
    }

    @Override
    public Double apply(Integer d1, Integer d2) {
        return d1 * 1.0 * d2;
    }

    @Override
    public Double apply(Double d1, Integer d2) {
        return d1 * d2;
    }

    @Override
    public Double apply(Integer d1, Double d2) {
        return d1 * d2;
    }

    @Override
    public Complex apply(Complex d1, Complex d2) {
        return d1.multiply(d2);
    }

    @Override
    public Complex apply(Complex d1, Double d2) {
        return d1.multiply(d2);
    }

    @Override
    public Complex apply(Double d1, Complex d2) {
        return d2.multiply(d1);
    }

    @Override
    public Complex apply(Complex d1, Integer d2) {
        return d1.multiply(d2);
    }

    @Override
    public Complex apply(Integer d1, Complex d2) {
        return d2.multiply(d1);
    }
}
