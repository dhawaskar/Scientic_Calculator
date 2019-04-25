package com.ooad.backend.la;

import com.ooad.backend.data.types.ElemFactory;
import com.ooad.backend.data.types.ElemType;
import com.ooad.backend.data.types.IntegerElem;
import com.ooad.backend.data.types.operators.Utils;
import com.ooad.backend.la.exceptions.IncompatibleMatricesException;
import com.ooad.backend.la.exceptions.MatrixNonSquareException;
import com.ooad.backend.la.exceptions.SingularMatrixException;

import java.util.Arrays;

/**
 * @author Hasil Sharma
 */
public class Matrix {
    private int M;
    private int N;
    private ElemType[][] data;

    public Matrix(int M, int N, ElemType[][] data) {
        this.M = M;
        this.N = N;
        this.data = data;
    }

    public Matrix(int M, int N, Integer[][] data){
        ElemType[][] temp = ElemFactory.Integer2DToElemType(data);
        this.M = M;
        this.N = N;
        this.data = temp;
    }

    public int[] getDim(){
        return new int[]{this.M, this.N};
    }

    public ElemType[][] getData() {
        return data;
    }

    private boolean isSquare(){
        return this.M != 0 && this.M == this.N;
    }

    public Matrix inv() throws Exception {
        if (!this.isSquare())
            throw new MatrixNonSquareException();

        ElemType det = this.determinant();
        if (!Utils.checkNonZero(det))
            throw new SingularMatrixException();

        ElemType[][] adj = new ElemType[N][N];
        this.adjoint(adj);

        ElemType[][] inverse = new ElemType[N][N];
        for (int i=0; i<N; i++) {
            for (int j = 0; j < N; j++) {
                inverse[i][j] = adj[i][j].div(det);
            }
        }

        return new Matrix(M, N, inverse);
    }

    public ElemType determinant() throws Exception {
        if (!this.isSquare())
            throw new MatrixNonSquareException();

        return determinantHelper(this.data, this.M);
    }

    private ElemType determinantHelper(ElemType[][] mat, int n){
        ElemType ans = new IntegerElem(0);
        if (n == 1)
            return mat[0][0];

        ElemType[][] temp = new ElemType[this.M][this.M];
        ElemType sign = new IntegerElem(1);

        for(int f = 0; f < n; f++){
            getCofactor(mat, temp, 0, f, n);
            ElemType tans = determinantHelper(temp, n - 1);
            tans = tans.mul(mat[0][f]);
            tans = tans.mul(sign);
            ans = ans.add(tans);
//            ans = ans.add(sign.mul(mat[0][f].mul(determinantHelper(temp, n - 1))));
            sign = sign.negate();
        }

        return ans;
    }

    private void getCofactor(ElemType[][] mat, ElemType[][] temp, int p, int q, int n){
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

        ElemType[][] product = new ElemType[r1][c2], firstMatrix = this.getData(), secondMatrix = mat.getData();

        for(int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                    product[i][j] = new IntegerElem(0);
                for (int k = 0; k < c1; k++) {
                    product[i][j] = product[i][j].add(firstMatrix[i][k].mul(secondMatrix[k][j]));
                }
            }
        }

        return new Matrix(r1, c2, product);
    }

    private void adjoint(ElemType[][] adj){
        if (N == 1){
            adj[0][0] = new IntegerElem(1);
            return;
        }
        ElemType sign = new IntegerElem(1);

        ElemType[][] temp = new ElemType[N][N];

        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                getCofactor(this.data, temp, i, j, N);
                sign = ((i+j)%2==0)? new IntegerElem(1): new IntegerElem(-1);
                adj[j][i] = sign.mul(determinantHelper(temp, N-1));
            }
        }

    }


    @Override
    public String toString() {
        return "Matrix{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    public String convertToString(){
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < M; i ++){
            for(int j = 0; j < N; j++) {
                stringBuilder.append(data[i][j] + ",");
            }
        }

        return stringBuilder.toString();
    }

    public Matrix add(Matrix m2) throws Exception{
        int[] m2_size = m2.getDim();

        if(m2_size[0] != M || m2_size[1] != N){
            throw new IncompatibleMatricesException();
        }

        ElemType[][] temp = new ElemType[M][N];
        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                temp[i][j] = m2.getData()[i][j].add(this.data[i][j]);
            }
        }

        return new Matrix(M, N, temp);
    }

    public Matrix transpose() {
        ElemType[][] temp = new ElemType[N][M];

        for(int i = 0; i < M; i++){
            for(int j = 0; j < N; j++){
                temp[j][i] = this.data[i][j];
            }
        }

        return new Matrix(N, N, temp);
    }
}
