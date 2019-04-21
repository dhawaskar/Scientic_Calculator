package com.ooad.backend.data.types.operators;

public interface Operator2Arg {
    Double apply(Double d1, Double d2);

    Double apply(Integer d1, Integer d2);

    Double apply(Double d1, Integer d2);

    Double apply(Integer d1, Double d2);
}
