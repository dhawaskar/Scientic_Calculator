package com.ooad.frontend.button.behavior.combinatorics;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

public class CombAppendSymbol extends DefaultButtonBehavior implements View.OnClickListener {
    private String symbol;

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
