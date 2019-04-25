package com.ooad.frontend.button.behavior.combinatorics;
/**
 * Implements onlick string modifier to get relevant string in generating appropriate mathematical parsing expression for calculation
 * using mxparser library
 * @author Hasil, Sandesh, Gautham
 */

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

public class CombAppendSymbol extends DefaultButtonBehavior implements View.OnClickListener {
    private String symbol;

    /**
     * Constructor for instantiating the object from params - ,button and symbol string
     * @param txtOutput
     * @param btn
     * @param symbol
     */
    public CombAppendSymbol(EditText txtOutput, Button btn, String symbol) {
        super(txtOutput, btn);
        this.symbol = symbol;
    }

    @Override
    public void onClick(View view) {
        String oldStr = txtOutput.getText().toString();
        String newStr = oldStr + symbol;
        txtOutput.setText(newStr);

    }
}
