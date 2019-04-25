package com.ooad.backend.data.types;

/**
 * Enum type for the elements type supported
 * @author Hasil, Sandesh, Gautham
 */
public enum ElemTypeEnum {
    DOUBLE(){
        @Override
        public String toString(){
            return DoubleElem.DESC;
        }
    }, INTEGER{
        @Override
        public String toString(){
            return IntegerElem.DESC;
        }
    }, COMPLEX {
        @Override
        public String toString(){
            return ComplexElem.DESC;
        }
    };

}
