package com.ooad.backend.data.types.operators;


public class Add implements Operator2Arg {

    @Override
    public Double apply(Double d1, Double d2) {
        return d1 + d2;
    }

    @Override
    public Double apply(Integer d1, Integer d2) {
        return d1*1.0 + d2;
    }

    @Override
    public Double apply(Double d1, Integer d2) {
        return d1 + d2;
    }

    @Override
    public Double apply(Integer d1, Double d2) {
        return d1 + d2;
    }
}