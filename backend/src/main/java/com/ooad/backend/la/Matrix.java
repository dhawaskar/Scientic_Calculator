package com.ooad.backend.la;

import com.ooad.backend.data.types.ElemTypeEnum;
import com.ooad.backend.data.types.IntegerElemType;
import com.ooad.backend.data.types.operators.Utils;
import com.ooad.backend.la.exceptions.MatrixNonSquareException;
import com.ooad.backend.la.exceptions.SingularMatrixException;
import com.ooad.backend.la.exceptions.IncompatibleMatricesException;

public class Matrix {
    private int M;
    private int N;
    private ElemTypeEnum[][] data;

    public Matrix(int M, int N, ElemTypeEnum[][] data) {
        this.M = M;
        this.N = N;
        this.data = data;
    }

    public int[] getDim(){
        return new int[]{this.M, this.N};
    }

    public ElemTypeEnum[][] getData() {
        return data;
    }

    private boolean isSquare(){
        return this.M != 0 && this.M == this.N;
    }

    public Matrix inv() throws Exception {
        if (!this.isSquare())
            throw new MatrixNonSquareException();

        ElemTypeEnum det = this.determinant();
        if (!Utils.checkNonZero(det))
            throw new SingularMatrixException();

        ElemTypeEnum[][] adj = new ElemTypeEnum[N][N];
        this.adjoint(adj);

        ElemTypeEnum[][] inverse = new ElemTypeEnum[N][N];
        for (int i=0; i<N; i++) {
            for (int j = 0; j < N; j++) {
                inverse[i][j] = adj[i][j].div(det);
            }
        }

        return new Matrix(M, N, inverse);
    }

    public ElemTypeEnum determinant() throws Exception {
        if (!this.isSquare())
            throw new MatrixNonSquareException();

        return determinantHelper(this.data, this.M);
    }

    private ElemTypeEnum determinantHelper(ElemTypeEnum[][] mat, int n){
        ElemTypeEnum ans = new IntegerElemType(0);
        if (n == 1)
            return mat[0][0];

        ElemTypeEnum[][] temp = new ElemTypeEnum[this.M][this.M];
        ElemTypeEnum sign = new IntegerElemType(1);

        for(int f = 0; f < n; f++){
            getCofactor(mat, temp, 0, f, n);
            ElemTypeEnum tans = determinantHelper(temp, n - 1);
            tans = tans.mul(mat[0][f]);
            tans = tans.mul(sign);
            ans = ans.add(tans);
//            ans = ans.add(sign.mul(mat[0][f].mul(determinantHelper(temp, n - 1))));
            sign = sign.negate();
        }

        return ans;
    }

    private void getCofactor(ElemTypeEnum[][] mat, ElemTypeEnum[][] temp, int p, int q, int n){
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

        ElemTypeEnum[][] product = new ElemTypeEnum[r1][c2], firstMatrix = this.getData(), secondMatrix = mat.getData();

        for(int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                    product[i][j] = new IntegerElemType(0);
                for (int k = 0; k < c1; k++) {
                    product[i][j] = product[i][j].add(firstMatrix[i][k].mul(secondMatrix[k][j]));
                }
            }
        }

        return new Matrix(r1, c2, product);
    }

    private void adjoint(ElemTypeEnum[][] adj){
        if (N == 1){
            adj[0][0] = new IntegerElemType(1);
            return;
        }
        ElemTypeEnum sign = new IntegerElemType(1);

        ElemTypeEnum[][] temp = new ElemTypeEnum[N][N];

        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                getCofactor(this.data, temp, i, j, N);
                sign = ((i+j)%2==0)? new IntegerElemType(1): new IntegerElemType(-1);
                adj[j][i] = sign.mul(determinantHelper(temp, N-1));
            }
        }

    }
}
