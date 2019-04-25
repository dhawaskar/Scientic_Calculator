package com.ooad.frontend.button.behavior.matrix;
/**
 * Implements onclick behavior for equals to button operation in matrix screen
 * @author Hasil, Sandesh, Gautham
 */

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.backend.la.Matrix;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;
import com.ooad.frontend.misc.Utils;

public class MatrixEqu extends DefaultButtonBehavior {

    private MatrixContext ctx;

    /**
     * Constructor that instantiates the context and default button txt display behaviour
     * @param txtOutput
     * @param btn
     * @param ctx
     */
    public MatrixEqu(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
        this.ctx = ctx;
    }

    /**
     * Implements the operation based on the context - add, multiply or stake another input for matrix from user
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (ctx.isPendingMultiply() || ctx.isPendingAdd()) {

            ctx.takeMatrixFromString(txtOutput.getText().toString());
            int len = ctx.getLen();
            Matrix m1 = ctx.getMatrices(len - 1), m2 = ctx.getMatrices(len - 2);
            Matrix m3 = null;

            if (ctx.isPendingAdd()) {
                try {
                    m3 = m1.add(m2);
                    txtOutput.setText(m3.convertToString());
                } catch (Exception e) {
                    txtOutput.setText(MatrixContext.INPUT_ERROR);
                }
                ctx.removePendingAdd();
            } else {
                try {
                    m3 = m1.multiply(m2);
                    txtOutput.setText(m3.convertToString());
                } catch (Exception e) {
                    txtOutput.setText(MatrixContext.INPUT_ERROR);
                    ctx.removePendingMultiply();
                }
            }
        } else if (ctx.pendingMatrix()) {
            ctx.takeMatrixFromString(txtOutput.getText().toString());
            txtOutput.setText("");
        }
    }
}
