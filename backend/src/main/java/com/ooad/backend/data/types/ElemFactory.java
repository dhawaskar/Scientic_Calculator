package com.ooad.backend.data.types;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.math3.complex.Complex;

public final class ElemFactory {
    public static ElemTypeEnum getObject(Object value){
        if (value instanceof Number) return getObject((Number) value);
        else if (value instanceof Complex)
            return new ComplexElemType(((Complex) value).getReal(), ((Complex) value).getImaginary());
        else
            throw new UnsupportedOperationException();
    }
    public static ElemTypeEnum getObject(Double value) {
        return new DoubleElemType(value);
    }

    public static ElemTypeEnum getObject(Integer value) {
        return new IntegerElemType(value);
    }

    public static ElemTypeEnum getObject(Number value) {
        if (value instanceof Double) {
            Double dvalue = (Double) value;
            double eps = Math.abs(dvalue - Math.round(dvalue));
            if (eps < 1e-8){
                dvalue = (double) Math.round(dvalue);
            }
            return dvalue != dvalue.intValue() ? getObject(dvalue) : getObject(dvalue.intValue());
        } else if (value instanceof Integer)
            return getObject((Integer) value);
        else
            throw new NotImplementedException();
    }

    public static Complex dataTypeToComplex(ElemTypeEnum d){
        ElemTypeEnum real = d.getReal(), imag = d.getImag();
        if (real.getDataType() == Type.DOUBLE && imag.getDataType() == Type.DOUBLE){
            return new Complex(real.getDecimal(), imag.getDecimal());
        } else if (real.getDataType() == Type.DOUBLE && imag.getDataType() == Type.INTEGER){
            return new Complex(real.getDecimal(), imag.getInteger());
        } else if (real.getDataType() == Type.INTEGER && imag.getDataType() == Type.DOUBLE){
            return  new Complex(real.getInteger(), imag.getDecimal());
        } else if (real.getDataType() == Type.INTEGER && imag.getDataType() == Type.INTEGER){
            return new Complex(real.getInteger(), imag.getInteger());
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
