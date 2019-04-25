package com.ooad.frontend.button.behavior.matrix;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;
import com.ooad.frontend.misc.Utils;

public class MatrixSize extends DefaultButtonBehavior {

    private MatrixContext ctx;

    public MatrixSize(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
        this.ctx = ctx;
    }

    @Override
    public void onClick(View view) {
        String oldStr = txtOutput.getText().toString();
        Integer[] mnArray = Utils.stringToIntegerArray(oldStr);
        ctx.updateSize(mnArray);
        txtOutput.setText("");
        txtOutput.setHint("Enter Matrix (Row-Wise)");
    }
}
