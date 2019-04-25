package com.ooad.frontend.button.behavior.matrix;
/**
 * Implements onclick behavior for comma button in matrix screen layout
 * @author Hasil, Sandesh, Gautham
 */

import android.widget.Button;
import android.widget.EditText;

import com.ooad.backend.la.Matrix;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;

public class MatrixCom extends DefaultButtonBehavior {
    public MatrixCom(EditText txtOutput, Button btn, MatrixContext ctx) {
        super(txtOutput, btn);
    }
}
