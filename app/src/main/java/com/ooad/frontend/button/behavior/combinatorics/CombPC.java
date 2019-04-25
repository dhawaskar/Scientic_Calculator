package com.ooad.frontend.button.behavior.combinatorics;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

public class CombPC extends DefaultButtonBehavior implements View.OnClickListener {

    private final String symbol;

    public CombPC(EditText txtOutput, Button btn, String symbol) {
        super(txtOutput, btn);
        this.symbol = symbol;
    }

    @Override
    public void onClick(View view) {
        String oldStr = txtOutput.getText().toString();
        String newStr = symbol + oldStr + ",";
        txtOutput.setText(newStr);
    }
}
