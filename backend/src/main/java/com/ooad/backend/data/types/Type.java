package com.ooad.backend.data.types;

public enum Type {
    DOUBLE(){
        @Override
        public String toString(){
            return DoubleElemType.DESC;
        }
    }, INTEGER{
        @Override
        public String toString(){
            return IntegerElemType.DESC;
        }
    }, COMPLEX {
        @Override
        public String toString(){
            return "ComplexElemType";
        }
    };

}
