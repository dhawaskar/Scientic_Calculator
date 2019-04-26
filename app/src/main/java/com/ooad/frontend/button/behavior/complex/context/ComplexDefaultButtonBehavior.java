

/**
 * Defines standard behaviour for buttons - to set text on output screen * expression evaluation
 * @author Hasil, Sandesh, Gautham
 */

package com.ooad.frontend.button.behavior.complex.context;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mariuszgromada.math.mxparser.Expression;

public class ComplexDefaultButtonBehavior implements View.OnClickListener {
    protected EditText txtOutput;
    protected Button btn;
    private ComplexContext ctx;
    /**
     * Constructor for instantiating default layout defined text
     * @param txtOutput
     * @param btn
     */
    public ComplexDefaultButtonBehavior(EditText txtOutput, Button btn, ComplexContext ctx) {
        this.txtOutput = txtOutput;
        this.btn = btn;
        this.ctx = ctx;
    }

    /**
     * Sets the default behaviour for button clicks - writing text output on to app display
     * @param view
     */
    @Override
    public void onClick(View view) {
        txtOutput.setText(txtOutput.getText().toString() + btn.getText().toString());
        if (btn.getText().toString().equals("+")){
            ctx.setAddOpt(true);
        } else if (btn.getText().toString().equals("-")){
            ctx.setSubOpt(true);
        } else if (btn.getText().toString().equals("*")){
            ctx.setMulOpt(true);
        } else if (btn.getText().toString().equals("/")){
            ctx.setDivOpt(true);
        }
    }

    protected final Double evaluate(String str){
        Expression exp = new Expression(str);
        return exp.calculate();
    }
}
