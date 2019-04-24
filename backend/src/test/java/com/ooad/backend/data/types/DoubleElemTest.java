package com.ooad.backend.data.types;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author Hasil Sharma
 */
public class DoubleElemTest {
    private DoubleElem doubleType;
    private DoubleElem doubleTypeSame1, doubleTypeSame2;

    @Before
    public void setUp() throws Exception {
        doubleType = new DoubleElem(1.0);
        doubleTypeSame1 = new DoubleElem(2);
        doubleTypeSame2 = new DoubleElem(2.0);
    }

    @Test
    public void getDataType() {
        assertSame(doubleType.getDataType(), ElemTypeEnum.DOUBLE);
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