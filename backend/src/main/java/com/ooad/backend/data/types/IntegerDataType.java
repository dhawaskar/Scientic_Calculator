package com.ooad.backend.data.types;


public class IntegerDataType extends DataType {
    public final static String DESC = "IntegerDataType";

    public IntegerDataType(Integer integer) {
        super(integer);
    }

    public IntegerDataType(int i){
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
    public Boolean checkEqual(DataType d) {
        if (d.getDataType() == Type.INTEGER)
            return this.getInteger().equals(d.getInteger());
        else throw new UnsupportedOperationException(
                String.format("Cannot compare %s with type IntegerDataType", d.getDataType())
        );
    }


}
