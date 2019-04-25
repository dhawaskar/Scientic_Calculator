package com.ooad.frontend.button.behavior.matrix;
/**
 * Implements onclick behavior for (m,n) size button that is different form Default behaviour
 * @author Hasil, Sandesh, Gautham
 */

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;
import com.ooad.frontend.misc.Utils;

public class MatrixSize extends DefaultButtonBehavior {

    private MatrixContext ctx;

    /**
     * Constructor that instantiates the context and default button txt display behaviour
     * @param txtOutput
     * @param btn
     * @param ctx
     */
    public MatrixSize(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
        this.ctx = ctx;
    }

    /**
     * Records the size and hints users to enter elements of matrix
     * @param view
     */
    @Override
    public void onClick(View view) {
        String oldStr = txtOutput.getText().toString();
        Integer[] mnArray = Utils.stringToIntegerArray(oldStr);
        ctx.updateSize(mnArray);
        txtOutput.setText("");
        txtOutput.setHint("Enter Matrix (Row-Wise)");
    }
}
