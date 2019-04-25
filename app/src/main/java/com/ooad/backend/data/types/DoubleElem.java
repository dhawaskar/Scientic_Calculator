package com.ooad.backend.data.types;

/**
 * Implements interface ElemType and used to handle the representation for Double.
 * @author Hasil, Sandesh, Gautham
 */
public class DoubleElem extends ElemType {
    /**
     * DESC String for debugging
     */
    public final static String DESC = "DoubleElem";

    /**
     * Constructor
     * @param decimal
     */
    public DoubleElem(Double decimal) {
        super(decimal);
    }

    /**
     * Constructor
     * @param decimal
     */
    public DoubleElem(double decimal) {
        super(decimal);
    }

    /**
     * Returns the type of class when using the class in polymorphic way
     * @return ElemTypeEnum.DOUBLE
     */
    @Override
    public ElemTypeEnum getDataType() {
        return ElemTypeEnum.DOUBLE;
    }

    /**
     * Getter method for the decimal
     * @return Double
     */
    @Override
    public Double getDecimal() {
        return decimal;
    }

    /**
     * Doesn't make sense to use this for a Double
     * @throws UnsupportedOperationException whenever called
     */
    @Override
    public Integer getInteger() {
        throw new UnsupportedOperationException();
    }

//    /**
//     * Doesn't make sense to use this for a Double Number
//     * @throws UnsupportedOperationException whenever called
//     */
//    @Override
//    public ElemType getImag() {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Doesn't make sense to use this for a Double Number
//     * @throws UnsupportedOperationException whenever called
//     */
//    @Override
//    public ElemType getReal() {
//        throw new UnsupportedOperationException();
//    }

    /**
     * Converts the object to string
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return Double.toString(this.getDecimal());
    }

    /**
     * Check for the object equality
     * @param obj
     * @return whether the current object is same as obj
     */
    @Override
    public boolean equals(Object obj) {
        DoubleElem dobj = (DoubleElem) obj;
        return dobj.getDecimal().equals(this.getDecimal());
    }
}
