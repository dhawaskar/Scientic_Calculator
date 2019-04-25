package com.ooad.backend.data.types;


import com.ooad.backend.data.types.operators.Operator2Arg;
import com.ooad.backend.data.types.operators.two.args.Add;
import com.ooad.backend.data.types.operators.two.args.Divide;
import com.ooad.backend.data.types.operators.two.args.Multiply;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.math3.complex.Complex;

/**
 * This class handles the application of operations on the types defined by ElemType
 *
 * @author Hasil, Sandesh, Gautham
 */
//https://stackoverflow.com/questions/2902458/is-it-possible-to-pass-arithmetic-operators-to-a-method-in-java
public enum OperatorEnum {
    ADDITION() {
        @Override
        public ElemType apply(ElemType x1, ElemType x2) {
            return apply(new Add(), x1, x2);
        }
    },
    MULTIPLY() {
        @Override
        public ElemType apply(ElemType x1, ElemType x2) {
            return apply(new Multiply(), x1, x2);
        }
    },
    DIVIDE() {
        @Override
        public ElemType apply(ElemType x1, ElemType x2) {
            return apply(new Divide(), x1, x2);
        }
    },
    MOD() {
        @Override
        public ElemType apply(ElemType x1, ElemType x2) {
            throw new NotImplementedException("Mod not implemented");
        }
    };

    public abstract ElemType apply(ElemType x1, ElemType x2);

    public ElemType apply(Operator2Arg op, ElemType x1, ElemType x2) {
        Object temp;
        if (x1.getDataType() == ElemTypeEnum.DOUBLE && x2.getDataType() == ElemTypeEnum.DOUBLE) {
            temp = op.apply(x1.getDecimal(), x2.getDecimal());
        } else if (x1.getDataType() == ElemTypeEnum.DOUBLE && x2.getDataType() == ElemTypeEnum.INTEGER) {
            temp = op.apply(x1.getDecimal(), x2.getInteger());
        } else if (x2.getDataType() == ElemTypeEnum.DOUBLE && x1.getDataType() == ElemTypeEnum.INTEGER) {
            temp = op.apply(x1.getInteger(), x2.getDecimal());
        } else if (x2.getDataType() == ElemTypeEnum.INTEGER && x1.getDataType() == ElemTypeEnum.INTEGER) {
            temp = op.apply(x1.getInteger(), x2.getInteger());
        } else if (x1.getDataType() == ElemTypeEnum.COMPLEX && x2.getDataType() == ElemTypeEnum.COMPLEX) {
            Complex c1 = ElemFactory.dataTypeToComplex((ComplexElem) x1), c2 = ElemFactory.dataTypeToComplex((ComplexElem) x2);
            temp = op.apply(c1, c2);
        } else if (x1.getDataType() == ElemTypeEnum.COMPLEX && x2.getDataType() == ElemTypeEnum.DOUBLE) {
            Complex c1 = ElemFactory.dataTypeToComplex((ComplexElem) x1);
            temp = op.apply(c1, x2.getDecimal());
        } else if (x1.getDataType() == ElemTypeEnum.DOUBLE && x2.getDataType() == ElemTypeEnum.COMPLEX) {
            Complex c2 = ElemFactory.dataTypeToComplex((ComplexElem) x2);
            temp = op.apply(x1.getDecimal(), c2);
        } else if (x1.getDataType() == ElemTypeEnum.COMPLEX && x2.getDataType() == ElemTypeEnum.INTEGER) {
            Complex c1 = ElemFactory.dataTypeToComplex((ComplexElem) x1);
            temp = op.apply(c1, x2.getInteger());
        } else if (x1.getDataType() == ElemTypeEnum.INTEGER && x2.getDataType() == ElemTypeEnum.COMPLEX) {
            Complex c2 = ElemFactory.dataTypeToComplex((ComplexElem) x2);
            temp = op.apply(x1.getInteger(), c2);
        } else {
            throw new UnsupportedOperationException();
        }
        ElemType ans = ElemFactory.getObject(temp);
        return ans;
    }

}
