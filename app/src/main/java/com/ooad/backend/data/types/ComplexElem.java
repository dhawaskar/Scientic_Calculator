package com.ooad.backend.data.types;

/**
 * Implements interface ElemType and used to handle the representation for Complex Numbers. Consists of Imaginary and Real
 * components which are both type of ElemType. Supports operations on Complex Numbers such as calculating conjugate etc.
 * @author Hasil, Sandesh, Gautham
 */
public class ComplexElem extends ElemType {
    public static final String DESC = "ComplexElem";
    private ElemType img, real;

    /**
     * Constructor for instantiating the object from params of type ElemType
     * @param real
     * @param img
     */
    public ComplexElem(ElemType real, ElemType img){
        super();
        this.real = real;
        this.img = img;
    }

    /**
     * Constructor for instantiation the object from params of type double
     * @param real
     * @param img
     */
    public ComplexElem(double real, double img) {
        super();
        this.real = ElemFactory.getObject(real);
        this.real = ElemFactory.getObject(img);
    }

    /**
     * Getter method for Real
     * @return real part of the Complex number
     */
    public ElemType getReal() {
        return real;
    }

    /**
     * Getter method for Imag
     * @return imaginary part of the Complex number
     */
    public ElemType getImag() {
        return img;
    }

    /**
     * Calculating conjugate of the complex number
     * @return conjugate of the imaginary number
     */
    public ComplexElem getConjugate(){
        return new ComplexElem(real, img.negate());
    }

    /**
     * Returns the type of class when using the class in polymorphic way
     * @return ElemTypeEnum.Complex
     */
    @Override
    public ElemTypeEnum getDataType() {
        return ElemTypeEnum.COMPLEX;
    }

    /**
     * Doesn't make sense to use this for a Complex Number
     * @throws UnsupportedOperationException whenever called
     */
    @Override
    public Double getDecimal() {
        throw new UnsupportedOperationException("getDecimal doesn't make sense");
    }

    /**
     * Doesn't make sense to use this for a Complex Number
     * @throws UnsupportedOperationException whenever called
     */
    @Override
    public Integer getInteger() {
        throw new UnsupportedOperationException("getInteger doesn't make sense");
    }

    @Override
    public String toString() {
        return this.real + " + j" + this.img;
    }

}
