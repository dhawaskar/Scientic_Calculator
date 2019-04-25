package com.ooad.frontend.button.behavior.matrix;
/**
 * Implements onclick behavior for Determinant operation  in matrix screen layout
 * @author Hasil, Sandesh, Gautham
 */

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.backend.data.types.ElemType;
import com.ooad.backend.la.Matrix;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;

public class MatrixDet extends DefaultButtonBehavior {

    private MatrixContext ctx;

    /**
     * Constructor that instantiates the default button behavior and context
     * @param txtOutput
     * @param btn
     * @param ctx
     */
    public MatrixDet(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
        this.ctx = ctx;
    }

    /**
     * Evaluates the determinant of matrix using backend code
     * @param view
     */

    @Override
    public void onClick(View view) {
        Matrix matrix = ctx.getLastMatrix();
        try {
            ElemType matrixDet = matrix.determinant();
            txtOutput.setText(matrixDet.toString());

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            txtOutput.setText(MatrixContext.INPUT_ERROR);
        }

    }
}
