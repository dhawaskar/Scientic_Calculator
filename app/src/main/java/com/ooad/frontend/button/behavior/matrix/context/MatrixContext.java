package com.ooad.frontend.button.behavior.matrix.context;

import com.ooad.backend.la.Matrix;
import com.ooad.frontend.misc.Utils;

import java.util.ArrayList;

public class MatrixContext {
    public final static String PROMPT = "Enter the matrix elements (Row wise)";
    public final static String INPUT_ERROR = "Wrong Input";
    private ArrayList<Integer[]> mnArray;
    private ArrayList<Matrix> matrices;
    private int len;
    private boolean pendingAdd;
    private boolean pendingMul;

    public MatrixContext() {
        mnArray = new ArrayList<>();
        matrices = new ArrayList<>();
        len = 0;
    }

    public void updateSize(Integer[] mnArray) {
        this.mnArray.add(mnArray);
        len++;
    }

    public int getLen() {
        return len;
    }

    public Matrix getMatrices(int i) {
        return matrices.get(i);
    }

    public Integer[] getMNArray(int i) {
        return mnArray.get(i);
    }

    public Boolean pendingMatrix(){
        return mnArray.size() > matrices.size();
    }

    public void addMatrix(Matrix matrix) {
        matrices.add(matrix);
    }

    public Matrix getLastMatrix() {
        return matrices.get(len - 1);
    }

    public void takeMatrixFromString(String rawElems){
        int len = this.getLen();
        Integer[] size = this.getMNArray(len - 1);
        int m = size[0], n = size[1];
        Integer[] matrixElems = Utils.stringToIntegerArray(rawElems);
        Integer[][] matrixData = new Integer[m][n];

        int k = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                matrixData[i][j] = matrixElems[k++];
            }
        }
        Matrix matrix = new Matrix(m, n, matrixData);
        this.addMatrix(matrix);
    }

    public void addPendingAdd() {
        this.pendingAdd = true;
    }

    public void removePendingAdd() {
        this.pendingAdd = false;
    }

    public boolean isPendingAdd() {
        return pendingAdd;
    }


    public void addPendingMultiply() {
        this.pendingMul = true;
    }

    public void removePendingMultiply() {
        this.pendingMul = false;
    }

    public boolean isPendingMultiply() {
        return pendingMul;
    }
}
