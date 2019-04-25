package com.ooad.frontend.button.behavior.combinatorics;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

public class CombnCr extends DefaultButtonBehavior implements View.OnClickListener {

    public CombnCr(EditText txtOutput, Button btn) {
        super(txtOutput, btn);
    }

    @Override
    public void onClick(View view) {
        String oldStr = txtOutput.getText().toString();
        String newStr = "C(" + oldStr + ",";
        txtOutput.setText(newStr);
    }
}
