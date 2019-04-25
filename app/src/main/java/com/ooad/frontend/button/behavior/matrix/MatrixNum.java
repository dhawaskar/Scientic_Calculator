package com.ooad.frontend.button.behavior.matrix;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;

public class MatrixNum extends DefaultButtonBehavior {
    private MatrixContext ctx;

    public MatrixNum(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {
        if (ctx.pendingMatrix() && txtOutput.getText().toString().equals(MatrixContext.PROMPT)){
            txtOutput.setText("");
        }


    }
}
