package com.ooad.frontend.button.behavior;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.mariuszgromada.math.mxparser.Expression;

public class DefaultButtonBehavior implements View.OnClickListener {
    protected EditText txtOutput;
    protected Button btn;

    public DefaultButtonBehavior(EditText txtOutput, Button btn) {
        this.txtOutput = txtOutput;
        this.btn = btn;
    }

    @Override
    public void onClick(View view) {
        txtOutput.setText(txtOutput.getText().toString() + btn.getText().toString());
    }

    protected final Double evaluate(String str){
        Expression exp = new Expression(str);
        return exp.calculate();
    }
}
