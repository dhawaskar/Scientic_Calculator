package com.ooad.backend.la;

import com.ooad.backend.data.types.DataType;
import com.ooad.backend.data.types.IntegerDataType;
import com.ooad.backend.data.types.operators.Utils;
import com.ooad.backend.la.exceptions.MatrixNonSquareException;
import com.ooad.backend.la.exceptions.SingularMatrixException;
import com.ooad.backend.la.exceptions.IncompatibleMatricesException;

public class Matrix {
    private int M;
    private int N;
    private DataType[][] data;

    public Matrix(int M, int N, DataType[][] data) {
        this.M = M;
        this.N = N;
        this.data = data;
    }

    public int[] getDim(){
        return new int[]{this.M, this.N};
    }

    public DataType[][] getData() {
        return data;
    }

    private boolean isSquare(){
        return this.M != 0 && this.M == this.N;
    }

    public Matrix inv() throws Exception {
        if (!this.isSquare())
            throw new MatrixNonSquareException();

        DataType det = this.determinant();
        if (!Utils.checkNonZero(det))
            throw new SingularMatrixException();

        DataType[][] adj = new DataType[N][N];
        this.adjoint(adj);

        DataType[][] inverse = new DataType[N][N];
        for (int i=0; i<N; i++) {
            for (int j = 0; j < N; j++) {
                inverse[i][j] = adj[i][j].div(det);
            }
        }

        return new Matrix(M, N, inverse);
    }

    public DataType determinant() throws Exception {
        if (!this.isSquare())
            throw new MatrixNonSquareException();

        return determinantHelper(this.data, this.M);
    }

    private DataType determinantHelper(DataType[][] mat, int n){
        DataType ans = new IntegerDataType(0);
        if (n == 1)
            return mat[0][0];

        DataType[][] temp = new DataType[this.M][this.M];
        DataType sign = new IntegerDataType(1);

        for(int f = 0; f < n; f++){
            getCofactor(mat, temp, 0, f, n);
            ans = ans.add(sign.mul(mat[0][f].mul(determinantHelper(temp, n - 1))));
            sign = sign.negate();
        }

        return ans;
    }

    private void getCofactor(DataType[][] mat, DataType[][] temp, int p, int q, int n){
        int i = 0, j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }
    public Matrix multiply(Matrix mat) throws Exception {
        int[] d1 = this.getDim(), d2 = mat.getDim();
        int r1 = d1[0], r2 = d2[0], c1 = d1[1], c2 = d2[1];
        if (c1 != r2)
            throw new IncompatibleMatricesException();

        DataType[][] product = new DataType[r1][c2], firstMatrix = this.getData(), secondMatrix = mat.getData();

        for(int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    product[i][j] = product[i][j].add(firstMatrix[i][k].mul(secondMatrix[k][j]));
                }
            }
        }

        return new Matrix(r1, c2, product);
    }

    private void adjoint(DataType[][] adj){
        if (N == 1){
            adj[0][0] = new IntegerDataType(1);
            return;
        }
        DataType sign = new IntegerDataType(1);

        DataType[][] temp = new DataType[N][N];

        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                getCofactor(this.data, temp, i, j, N);
                sign = ((i+j)%2==0)? new IntegerDataType(1): new IntegerDataType(-1);
                adj[j][i] = sign.mul(determinantHelper(temp, N-1));
            }
        }

    }
}
