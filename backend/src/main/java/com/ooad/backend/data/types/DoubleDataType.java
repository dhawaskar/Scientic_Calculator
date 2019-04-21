package com.ooad.backend.data.types;

public class DoubleDataType extends DataType {
    public final static String DESC = "DoubleDataType";
    public DoubleDataType(Double decimal) {
        super(decimal);
    }

    public DoubleDataType(double decimal) {
        super(decimal);
    }

    @Override
    public Type getDataType() {
        return Type.DOUBLE;
    }

    @Override
    public Double getDecimal() {
        return decimal;
    }

    @Override
    public Integer getInteger() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean checkEqual(DataType d) {
        if (d.getDataType() == Type.DOUBLE)
            return this.getDecimal().equals(d.getDecimal());
        else throw new UnsupportedOperationException(
                String.format("Cannot compare %s with type DoubleDataType", d.getDataType())
        );
    }

    @Override
    public String toString() {
        return Double.toString(this.getDecimal());
    }

    @Override
    public boolean equals(Object obj) {
        DoubleDataType dobj = (DoubleDataType)obj;
        return dobj.getDecimal().equals(this.getDecimal());
    }
}
