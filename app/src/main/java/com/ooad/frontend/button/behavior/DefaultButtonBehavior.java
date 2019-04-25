

/**
 * Defines standard behaviour for buttons - to set text on output screen * expression evaluation
 * @author Hasil, Sandesh, Gautham
 */

package com.ooad.frontend.button.behavior;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mariuszgromada.math.mxparser.Expression;

public class DefaultButtonBehavior implements View.OnClickListener {
    protected EditText txtOutput;
    protected Button btn;
    /**
     * Constructor for instantiating default layout defined text
     * @param txtOutput
     * @param btn
     */
    public DefaultButtonBehavior(EditText txtOutput, Button btn) {
        this.txtOutput = txtOutput;
        this.btn = btn;
    }

    /**
     * Sets the default behaviour for button clicks - writing text output on to app display
     * @param view
     */
    @Override
    public void onClick(View view) {
        txtOutput.setText(txtOutput.getText().toString() + btn.getText().toString());
    }

    protected final Double evaluate(String str){
        Expression exp = new Expression(str);
        return exp.calculate();
    }
}
