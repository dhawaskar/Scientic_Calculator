package com.ooad.backend.data.types;


public class IntegerElemType extends ElemTypeEnum {
    public final static String DESC = "IntegerElemType";

    public IntegerElemType(Integer integer) {
        super(integer);
    }

    public IntegerElemType(int i) {
        super(i);
    }

    @Override
    public Type getDataType() {
        return Type.INTEGER;
    }

    @Override
    public Double getDecimal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getInteger() {
        return integer;
    }

    @Override
    public ElemTypeEnum getImag() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ElemTypeEnum getReal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        IntegerElemType dobj = (IntegerElemType) obj;
        return dobj.getInteger().equals(this.getInteger());
    }

}
