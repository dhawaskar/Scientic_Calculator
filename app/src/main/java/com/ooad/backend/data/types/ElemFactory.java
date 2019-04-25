package com.ooad.backend.data.types;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.math3.complex.Complex;

/**
 * This class helps in creating new objects of type ElemType
 * @author Hasil, Sandesh, Gautham
 */
public final class ElemFactory {
    /**
     * Helper function for converting objects
     * @param value
     * @return object of type ElemType
     */
    public static ElemType getObject(Object value){
        if (value instanceof Number) return getObject((Number) value);
        else if (value instanceof Complex)
            return new ComplexElem(((Complex) value).getReal(), ((Complex) value).getImaginary());
        else
            throw new UnsupportedOperationException();
    }

    /**
     * Helper function for converting objects
     * @param value
     * @return element of type DoubleElem
     */
    public static ElemType getObject(Double value) {
        return new DoubleElem(value);
    }

    /**
     * Helper function for converting objects
     * @param value
     * @return element of type IntegerElem
     */
    public static ElemType getObject(Integer value) {
        return new IntegerElem(value);
    }

    /**
     * Helper function for converting objects, takes care of rounding errors
     * @param value
     * @return element of type ElemType
     */
    public static ElemType getObject(Number value) {
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
            throw new UnsupportedOperationException();
    }

    /**
     * Converting ElemType to Complex number
     * @param d
     * @return Complex number representation of the ElemType
     */
    public static Complex dataTypeToComplex(ComplexElem d){
        ElemType real = d.getReal(), imag = d.getImag();
        if (real.getDataType() == ElemTypeEnum.DOUBLE && imag.getDataType() == ElemTypeEnum.DOUBLE){
            return new Complex(real.getDecimal(), imag.getDecimal());
        } else if (real.getDataType() == ElemTypeEnum.DOUBLE && imag.getDataType() == ElemTypeEnum.INTEGER){
            return new Complex(real.getDecimal(), imag.getInteger());
        } else if (real.getDataType() == ElemTypeEnum.INTEGER && imag.getDataType() == ElemTypeEnum.DOUBLE){
            return  new Complex(real.getInteger(), imag.getDecimal());
        } else if (real.getDataType() == ElemTypeEnum.INTEGER && imag.getDataType() == ElemTypeEnum.INTEGER){
            return new Complex(real.getInteger(), imag.getInteger());
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
