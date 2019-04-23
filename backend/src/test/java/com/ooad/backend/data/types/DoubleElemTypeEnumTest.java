package com.ooad.backend.data.types;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoubleElemTypeEnumTest {
    private DoubleElemType doubleType;
    private DoubleElemType doubleTypeSame1, doubleTypeSame2;

    @Before
    public void setUp() throws Exception {
        doubleType = new DoubleElemType(1.0);
        doubleTypeSame1 = new DoubleElemType(2);
        doubleTypeSame2 = new DoubleElemType(2.0);
    }

    @Test
    public void getDataType() {
        assertSame(doubleType.getDataType(), Type.DOUBLE);
    }

    @Test
    public void getDecimal() {
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getInteger() {
        doubleType.getInteger();
    }

    @Test
    public void checkEqual() {
        assertTrue(doubleTypeSame1 == doubleTypeSame2);
    }
}