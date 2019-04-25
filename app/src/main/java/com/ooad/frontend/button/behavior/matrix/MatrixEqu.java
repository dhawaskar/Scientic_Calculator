package com.ooad.frontend.button.behavior.matrix;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.backend.la.Matrix;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;
import com.ooad.frontend.misc.Utils;

public class MatrixEqu extends DefaultButtonBehavior {

    private MatrixContext ctx;

    public MatrixEqu(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
        this.ctx = ctx;
    }

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
