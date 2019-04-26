package com.ooad.backend.data.types;

/**
 * Abstract class for all the element types possible. Allows arithmetic operations in a polymorphic manner
 */
public abstract class ElemType {
    public Double decimal;
    public Integer integer;

    /**
     * Default constructor
     */
    public ElemType() {

    }

    /**
     * Constructor
     *
     * @param integer
     */
    public ElemType(Integer integer) {
        this.decimal = null;
        this.integer = integer;
    }

    /**
     * Constructor
     *
     * @param decimal
     */
    public ElemType(Double decimal) {
        this.integer = null;
        this.decimal = decimal;
    }

    /**
     * Addition operation on objects of type ElemType
     * @param e
     * @return result of addition
     */
    public final ElemType add(ElemType e) {
        return OperatorEnum.ADDITION.apply(this, e);
    }

    /**
     * Negation operation on objects of type ElemType
     * @return result of negation
     */
    public final ElemType negate() {
        return OperatorEnum.MULTIPLY.apply(new IntegerElem(-1), this);
    }

    /**
     * Multiplication operation on objects of type ElemType
     * @param e
     * @return result of multiplication of two elements
     */
    public final ElemType mul(ElemType e) {
        return OperatorEnum.MULTIPLY.apply(this, e);
    }

    /**
     * Division operation on objects of type ElemType
     * @param e
     * @return Result of division
     */
    public final ElemType div(ElemType e) {
        return OperatorEnum.DIVIDE.apply(this, e);
    }

    /**
     * Modulus operation on objects of type ElemType
     * @param e
     * @return Result of modulo
     */
    public final ElemType mod(ElemType e) {
        return OperatorEnum.MOD.apply(this, e);
    }

    /**
     * Getter method for the type of object when work polymorphically
     * @return
     */
    public abstract ElemTypeEnum getDataType();

    /**
     * Getter method for decimal part
     * @return
     */
    public abstract Double getDecimal();

    /**
     * Getter method for integer part
     * @return
     */
    public abstract Integer getInteger();

    public abstract String toString();

}
