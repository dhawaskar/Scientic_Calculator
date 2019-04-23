package com.ooad.backend.data.types;


public abstract class ElemTypeEnum {
    public Double decimal;
    public Integer integer;

    public ElemTypeEnum() {

    }

    public ElemTypeEnum(Integer integer) {
        this.decimal = null;
        this.integer = integer;
    }

    public ElemTypeEnum(Double decimal) {
        this.integer = null;
        this.decimal = decimal;
    }

    public final ElemTypeEnum add(ElemTypeEnum e) {
        return OperatorEnum.ADDITION.apply(this, e);
    }

    public final ElemTypeEnum negate() {
        return OperatorEnum.MULTIPLY.apply(new IntegerElemType(-1), this);
    }

    public final ElemTypeEnum mul(ElemTypeEnum e) {
        return OperatorEnum.MULTIPLY.apply(this, e);
    }

    public final ElemTypeEnum div(ElemTypeEnum e) {
        return OperatorEnum.DIVIDE.apply(this, e);
    }

    public final ElemTypeEnum mod(ElemTypeEnum e) {
        return OperatorEnum.MOD.apply(this, e);
    }

    public abstract Type getDataType();

    public abstract Double getDecimal();

    public abstract Integer getInteger();

    public abstract ElemTypeEnum getImag();
    public abstract ElemTypeEnum getReal();
//    public abstract Boolean checkEqual(ElemTypeEnum d);
}
