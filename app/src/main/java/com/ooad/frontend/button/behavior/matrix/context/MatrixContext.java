package com.ooad.frontend.button.behavior.matrix.context;

/**
 * Class implements methods and attributes store matrix context for  relevant operations - single and double
 * matrix operations. Implements methos to generate matrix from the input string obtained form the user.
 * @author Hasil, Sandesh, Gautham
 */
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
    /**
     * Constructor for instantiating the object that sets the matrix elem list and matrix m,m int array.
     * @param
     */
    public MatrixContext() {
        mnArray = new ArrayList<>();
        matrices = new ArrayList<>();
        len = 0;
    }

    /**
     * adds m,n to integer array
     * @param mnArray
     */
    public void updateSize(Integer[] mnArray) {
        this.mnArray.add(mnArray);
        len++;
    }

    /**
     * getter method for number of matrices
     * @return len
     */
    public int getLen() {
        return len;
    }

    /**
     * Getter method for Matrix
     * @param i
     * @return matrix
     */
    public Matrix getMatrices(int i) {
        return matrices.get(i);
    }

    /**
     * Getter method for Matrix - M and N
     * @param i
     * @return
     */

    public Integer[] getMNArray(int i) {
        return mnArray.get(i);
    }

    /**
     * To check for elements entering pending from user
     * @return
     */
    public Boolean pendingMatrix(){
        return mnArray.size() > matrices.size();
    }

    /**
     * To add matrix to matrix list
     * @param matrix
     */
    public void addMatrix(Matrix matrix) {
        matrices.add(matrix);
    }

    /**
     * To get last entered matrix for operation evaluation
     * @return last but one matrix.
     */
    public Matrix getLastMatrix() {
        return matrices.get(len - 1);
    }

    /**
     * To Generate matrix from string input of matrix elements taken from user
     * @param rawElems
     */
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
