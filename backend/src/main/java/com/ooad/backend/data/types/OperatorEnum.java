package com.ooad.backend.data.types;


import com.ooad.backend.data.types.operators.two.args.Add;
import com.ooad.backend.data.types.operators.two.args.Divide;
import com.ooad.backend.data.types.operators.two.args.Multiply;
import com.ooad.backend.data.types.operators.Operator2Arg;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.math3.complex.Complex;

//https://stackoverflow.com/questions/2902458/is-it-possible-to-pass-arithmetic-operators-to-a-method-in-java
public enum OperatorEnum {
    ADDITION() {
        @Override
        public ElemTypeEnum apply(ElemTypeEnum x1, ElemTypeEnum x2) {
            return apply(new Add(), x1, x2);
        }
    },
    MULTIPLY() {
        @Override
        public ElemTypeEnum apply(ElemTypeEnum x1, ElemTypeEnum x2) {
            return apply(new Multiply(), x1, x2);
        }
    },
    DIVIDE() {
        @Override
        public ElemTypeEnum apply(ElemTypeEnum x1, ElemTypeEnum x2) {
            return apply(new Divide(), x1, x2);
        }
    },
    MOD() {
        @Override
        public ElemTypeEnum apply(ElemTypeEnum x1, ElemTypeEnum x2) {
            throw new NotImplementedException("Mod not implemented");
        }
    };
    // You'd include other operators too...

    public abstract ElemTypeEnum apply(ElemTypeEnum x1, ElemTypeEnum x2);

    public ElemTypeEnum apply(Operator2Arg op, ElemTypeEnum x1, ElemTypeEnum x2) {
        Object temp;
        if (x1.getDataType() == Type.DOUBLE && x2.getDataType() == Type.DOUBLE) {
            temp = op.apply(x1.getDecimal(), x2.getDecimal());
        } else if (x1.getDataType() == Type.DOUBLE && x2.getDataType() == Type.INTEGER) {
            temp = op.apply(x1.getDecimal(), x2.getInteger());
        } else if (x2.getDataType() == Type.DOUBLE && x1.getDataType() == Type.INTEGER) {
            temp = op.apply(x1.getInteger(), x2.getDecimal());
        } else if (x2.getDataType() == Type.INTEGER && x1.getDataType() == Type.INTEGER) {
            temp = op.apply(x1.getInteger(), x2.getInteger());
        } else if (x1.getDataType() == Type.COMPLEX &&  x2.getDataType() == Type.COMPLEX){
            Complex c1 = ElemFactory.dataTypeToComplex(x1), c2 = ElemFactory.dataTypeToComplex(x2);
            temp = op.apply(c1, c2);
        } else if (x1.getDataType() == Type.COMPLEX &&  x2.getDataType() == Type.DOUBLE) {
            Complex c1 = ElemFactory.dataTypeToComplex(x1);
            temp = op.apply(c1, x2.getDecimal());
        } else if (x1.getDataType() == Type.DOUBLE &&  x2.getDataType() == Type.COMPLEX){
            Complex c2 = ElemFactory.dataTypeToComplex(x2);
            temp = op.apply(x1.getDecimal(), c2);
        } else if (x1.getDataType() == Type.COMPLEX &&  x2.getDataType() == Type.INTEGER) {
            Complex c1 = ElemFactory.dataTypeToComplex(x1);
            temp = op.apply(c1, x2.getInteger());
        } else if (x1.getDataType() == Type.INTEGER &&  x2.getDataType() == Type.COMPLEX) {
            Complex c2 = ElemFactory.dataTypeToComplex(x2);
            temp = op.apply(x1.getInteger(), c2);
        } else {
            throw new UnsupportedOperationException();
        }
        ElemTypeEnum ans = ElemFactory.getObject(temp);
        return ans;
    }

}
