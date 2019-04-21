package com.ooad.backend.data.types;


public abstract class DataType {
    public Double decimal;
    public Integer integer;

    public DataType(Integer integer) {
        this.decimal = null;
        this.integer = integer;
    }

    public DataType(Double decimal) {
        this.integer = null;
        this.decimal = decimal;
    }

    public final DataType add(DataType e) {
        return OperatorEnum.ADDITION.apply(this, e);
    }

    public final DataType negate() {
        return OperatorEnum.MULTIPLY.apply(new IntegerDataType(-1), this);
    }

    public final DataType mul(DataType e) {
        return OperatorEnum.MULTIPLY.apply(this, e);
    }

    public final DataType div(DataType e) {
        return OperatorEnum.DIVIDE.apply(this, e);
    }

    public final DataType mod(DataType e) {
        return OperatorEnum.MOD.apply(this, e);
    }

    public abstract Type getDataType();
    public abstract Double getDecimal();
    public abstract Integer getInteger();
    public abstract Boolean checkEqual(DataType d);
}
