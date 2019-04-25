package com.ooad.frontend.button.behavior.matrix;
/**
 * Implements onclick behavior for ADD button for matrix operation that is different form Default behaviour
 * @author Hasil, Sandesh, Gautham
 */

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.backend.la.Matrix;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;
import com.ooad.frontend.misc.Utils;

public class MatrixAdd extends DefaultButtonBehavior {

    private MatrixContext ctx;

    /**
     * Constructor that instantiates the context and default button txt display behaviour
     * @param txtOutput
     * @param btn
     * @param ctx
     */
    public MatrixAdd(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
        this.ctx = ctx;
    }

    /**
     * Sets the add operation context to true.
     * @param view
     */
    @Override
    public void onClick(View view) {
        ctx.addPendingAdd();
        txtOutput.setText("");
    }
}
