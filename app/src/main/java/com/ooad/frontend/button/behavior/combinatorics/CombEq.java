package com.ooad.frontend.button.behavior.combinatorics;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

public class CombEq extends DefaultButtonBehavior implements View.OnClickListener {

    public CombEq(EditText txtOutput, Button btn) {
        super(txtOutput, btn);
    }

    @Override
    public void onClick(View view) {
            String oldStr = txtOutput.getText().toString() + ")";
            Double value = evaluate(oldStr);
            txtOutput.setText(Double.toString(value));
        }
}
