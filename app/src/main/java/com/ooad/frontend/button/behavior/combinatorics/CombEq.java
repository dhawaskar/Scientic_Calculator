package com.ooad.frontend.button.behavior.combinatorics;
/**
 * Implements equal button click method for calculating expression using mxparser for combinatorics operations
 * @author Hasil, Sandesh, Gautham
 */


import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

public class CombEq extends DefaultButtonBehavior implements View.OnClickListener {
    /**
     * Constrictor that sets the Default txt behavior for button
     * @param txtOutput
     * @param btn
     */
    public CombEq(EditText txtOutput, Button btn) {
        super(txtOutput, btn);
    }

    /**
     * Operation for equals to button which takes the string and evaluates using mxparser expression class
     * @param view
     */
    @Override
    public void onClick(View view) {
            String oldStr = txtOutput.getText().toString();
            Double value = evaluate(oldStr);
            txtOutput.setText(Double.toString(value));
        }
}
