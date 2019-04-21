package com.ooad.backend.data.types;


import com.ooad.backend.data.types.operators.Add;
import com.ooad.backend.data.types.operators.Divide;
import com.ooad.backend.data.types.operators.Multiply;
import com.ooad.backend.data.types.operators.Operator2Arg;
import org.apache.commons.lang3.NotImplementedException;

//https://stackoverflow.com/questions/2902458/is-it-possible-to-pass-arithmetic-operators-to-a-method-in-java
public enum OperatorEnum {
    ADDITION() {
        @Override
        public DataType apply(DataType x1, DataType x2) {
            return apply(new Add(), x1, x2);
        }
    },
    MULTIPLY() {
        @Override
        public DataType apply(DataType x1, DataType x2) {
            return apply(new Multiply(), x1, x2);
        }
    },
    DIVIDE() {
        @Override
        public DataType apply(DataType x1, DataType x2) {
            return apply(new Divide(), x1, x2);
        }
    },
    MOD() {
        @Override
        public DataType apply(DataType x1, DataType x2) {
            throw new NotImplementedException("Mod not implemented");
        }
    };
    // You'd include other operators too...

    public abstract DataType apply(DataType x1, DataType x2);

    public DataType apply(Operator2Arg op, DataType x1, DataType x2) {
        Number temp;
        if (x1.getDataType() == Type.DOUBLE && x2.getDataType() == Type.DOUBLE) {
            temp = op.apply(x1.getDecimal(), x1.getDecimal());
        } else if (x1.getDataType() == Type.DOUBLE && x2.getDataType() == Type.INTEGER) {
            temp = op.apply(x1.getDecimal(), x2.getInteger());
        } else if (x2.getDataType() == Type.DOUBLE && x1.getDataType() == Type.INTEGER) {
            temp = op.apply(x1.getInteger(), x2.getDecimal());
        } else if (x2.getDataType() == Type.INTEGER && x1.getDataType() == Type.INTEGER) {
            temp = op.apply(x1.getInteger(), x2.getInteger());
        } else {
            throw new UnsupportedOperationException();
        }
        DataType ans = DataTypeFactory.getObject(temp);
        return ans;
    }

}
