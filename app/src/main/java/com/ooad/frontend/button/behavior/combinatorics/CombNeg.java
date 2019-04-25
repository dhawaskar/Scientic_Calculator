package com.ooad.frontend.button.behavior.combinatorics;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

public class CombNeg extends DefaultButtonBehavior implements View.OnClickListener {
    public CombNeg(EditText txtOutput, Button btn) {
        super(txtOutput, btn);
    }

    @Override
    public void onClick(View view) {
        String oldStr = txtOutput.getText().toString();
        String newStr = oldStr + "-";
        Double value = evaluate(newStr);
        txtOutput.setText(Double.toString(value));

    }
}
