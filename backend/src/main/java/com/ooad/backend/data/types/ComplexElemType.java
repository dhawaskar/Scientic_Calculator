package com.ooad.backend.data.types;

public class ComplexElemType extends ElemTypeEnum {
    private ElemTypeEnum img, real;

    public ComplexElemType(ElemTypeEnum real, ElemTypeEnum img){
        super();
        this.real = real;
        this.img = img;
    }

    public ComplexElemType(double real, double imaginary) {
        super();
        this.real = ElemFactory.getObject(real);
        this.real = ElemFactory.getObject(imaginary);
    }

    public ElemTypeEnum getReal() {
        return real;
    }

    public ComplexElemType getConjugate(){
        return new ComplexElemType(real, img.negate());
    }

    @Override
    public Type getDataType() {
        return Type.COMPLEX;
    }

    @Override
    public Double getDecimal() {
        throw new UnsupportedOperationException("getDecimal doesn't make sense");
    }

    @Override
    public Integer getInteger() {
        throw new UnsupportedOperationException("getInteger doesn't make sense");
    }

    @Override
    public ElemTypeEnum getImag() {
        return img;
    }


}
