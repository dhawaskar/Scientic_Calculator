package com.ooad.frontend.button.behavior.matrix;
/**
 * Implements onclick behavior for Multiplication button matrix operation that is different form Default behaviour
 * @author Hasil, Sandesh, Gautham
 */


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;

public class MatrixMul extends DefaultButtonBehavior {

    private MatrixContext ctx;

    /**
     * Constructor that instantiates the context and default button txt display behaviour
     * @param txtOutput
     * @param btn
     * @param ctx
     */
    public MatrixMul(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
        this.ctx = ctx;
    }

    /**
     * Sets the Mult operation context to true.
     * @param view
     */
    @Override
    public void onClick(View view) {
        ctx.addPendingMultiply();
        txtOutput.setText("");
    }
}
