package com.ooad.backend.data.types;

public class DoubleElemType extends ElemTypeEnum {
    public final static String DESC = "DoubleElemType";

    public DoubleElemType(Double decimal) {
        super(decimal);
    }

    public DoubleElemType(double decimal) {
        super(decimal);
    }

    @Override
    public Type getDataType() {
        return Type.DOUBLE;
    }

    @Override
    public Double getDecimal() {
        return decimal;
    }

    @Override
    public Integer getInteger() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ElemTypeEnum getImag() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ElemTypeEnum getReal() {
        throw new UnsupportedOperationException();
    }

//    @Override
//    public Boolean checkEqual(ElemTypeEnum d) {
//        if (d.getDataType() == Type.DOUBLE)
//            return this.getDecimal().equals(d.getDecimal());
//        else throw new UnsupportedOperationException(
//                String.format("Cannot compare %s with type DoubleElemType", d.getDataType())
//        );
//    }

    @Override
    public String toString() {
        return Double.toString(this.getDecimal());
    }

    @Override
    public boolean equals(Object obj) {
        DoubleElemType dobj = (DoubleElemType) obj;
        return dobj.getDecimal().equals(this.getDecimal());
    }
}
