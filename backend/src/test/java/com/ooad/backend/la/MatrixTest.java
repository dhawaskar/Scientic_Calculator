package com.ooad.backend.la;

import com.ooad.backend.data.types.DoubleElemType;
import com.ooad.backend.data.types.ElemTypeEnum;
import com.ooad.backend.data.types.IntegerElemType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {
    private Matrix doubleMatrix, integerMatrix, complexMatrix, integerMatrixInv, identityMatrix;
    private ElemTypeEnum[][] integerMatrixData, integerMatrixInvData;
    private int m, n;
    @Before
    public void setUp() throws Exception {
        this.m = 2;
        this.n = 2;

        integerMatrixData = new ElemTypeEnum[m][n];
        integerMatrixData[0][0] = new IntegerElemType(4);
        integerMatrixData[0][1] = new IntegerElemType(7);
        integerMatrixData[1][0] = new IntegerElemType(2);
        integerMatrixData[1][1] = new IntegerElemType(6);

        integerMatrix = new Matrix(m, n, integerMatrixData);

        integerMatrixInvData = new ElemTypeEnum[m][n];
        integerMatrixInvData[0][0] = new DoubleElemType(0.6);
        integerMatrixInvData[0][1] = new DoubleElemType(-0.7);
        integerMatrixInvData[1][0] = new DoubleElemType(-0.2);
        integerMatrixInvData[1][1] = new DoubleElemType(0.4);

        integerMatrixInv = new Matrix(m, n, integerMatrixInvData);

        ElemTypeEnum[][] identityData = new ElemTypeEnum[m][n];

        identityData[0][0] = new IntegerElemType(1);
        identityData[0][1] = new IntegerElemType(0);
        identityData[1][0] = new IntegerElemType(0);
        identityData[1][1] = new IntegerElemType(1);

        identityMatrix = new Matrix(m, n, identityData);
    }

    @Test
    public void getDim() {
        assertArrayEquals(integerMatrix.getDim(), new int[]{this.m, this.n});
    }

    @Test
    public void getData() {
        assertArrayEquals(integerMatrixData, integerMatrix.getData());
    }

    @Test
    public void inv() throws Exception{
        Matrix inverse = integerMatrix.inv();
        assertArrayEquals(inverse.getData(), integerMatrixInv.getData());
    }

    @Test
    public void inv_inv() throws Exception {
        Matrix inv_inv = integerMatrixInv.inv();
        assertArrayEquals(inv_inv.getData(), integerMatrix.getData());
    }

    @Test
    public void determinant() throws Exception {
        ElemTypeEnum det = integerMatrix.determinant();
        assertEquals(det, new IntegerElemType(10));
    }

    @Test
    public void multiply() throws Exception {
        Matrix mul = integerMatrix.multiply(integerMatrixInv);
        assertArrayEquals(identityMatrix.getData(), mul.getData());
    }
}