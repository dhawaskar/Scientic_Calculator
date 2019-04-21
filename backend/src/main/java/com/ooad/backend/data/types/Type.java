package com.ooad.backend.data.types;

import org.apache.commons.lang3.NotImplementedException;

public enum Type {
    DOUBLE(){
        @Override
        public String toString(){
            return DoubleDataType.DESC;
        }
    }, INTEGER{
        @Override
        public String toString(){
            return IntegerDataType.DESC;
        }
    }, COMPLEX {
        @Override
        public String toString(){
            return "ComplexDataType";
        }
    };

}
