package com.ooad.backend.data.types.operators;

import org.apache.commons.math3.complex.Complex;

public interface Operator2Arg {
    Double apply(Double d1, Double d2);

    Double apply(Integer d1, Integer d2);

    Double apply(Double d1, Integer d2);

    Double apply(Integer d1, Double d2);

    Complex apply(Complex d1, Complex d2);

    Complex apply(Complex d1, Double d2);

    Complex apply(Double d1, Complex d2);

    Complex apply(Complex d1, Integer d2);

    Complex apply(Integer d1, Complex d2);

}
