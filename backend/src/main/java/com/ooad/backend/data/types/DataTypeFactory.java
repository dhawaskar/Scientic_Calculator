package com.ooad.backend.data.types;

import org.apache.commons.lang.NotImplementedException;

public final class DataTypeFactory {
    public static DataType getObject(Double value) {
        return new DoubleDataType(value);
    }

    public static DataType getObject(Integer value) {
        return new IntegerDataType(value);
    }

    public static DataType getObject(Number value) {
        if (value instanceof Double) {
            Double dvalue = (Double) value;
            return dvalue != dvalue.intValue() ? getObject(dvalue) : getObject(dvalue.intValue());
        } else if (value instanceof Integer)
            return getObject((Integer) value);
        else
            throw new NotImplementedException();
    }
}
