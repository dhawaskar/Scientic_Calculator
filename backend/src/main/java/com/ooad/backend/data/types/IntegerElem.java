package com.ooad.backend.data.types;

/**
 * Implements interface ElemType and used to handle the representation for Integers.
 * @author Hasil, Sandesh, Gautham
 */

public class IntegerElem extends ElemType {
    /**
     * DESC String for debugging
     */
    public final static String DESC = "IntegerElem";

    /**
     * Constructor
     * @param integer
     */
    public IntegerElem(Integer integer) {
        super(integer);
    }

    /**
     * Constructor
     * @param i
     */
    public IntegerElem(int i) {
        super(i);
    }

    /**
     * Returns the type of class when using the class in polymorphic way
     * @return ElemTypeEnum.INTEGER
     */
    @Override
    public ElemTypeEnum getDataType() {
        return ElemTypeEnum.INTEGER;
    }

    /**
     * Getter method for the decimal
     * @return Double
     */
    @Override
    public Double getDecimal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Getter method for the Integer
     * @return Double
     */
    @Override
    public Integer getInteger() {
        return integer;
    }

//    @Override
//    public ElemType getImag() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public ElemType getReal() {
//        throw new UnsupportedOperationException();
//    }

    /**
     * Check for the object equality
     * @param obj
     * @return whether the current object is same as obj
     */
    @Override
    public boolean equals(Object obj) {
        IntegerElem dobj = (IntegerElem) obj;
        return dobj.getInteger().equals(this.getInteger());
    }

}
