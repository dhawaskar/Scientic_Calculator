package com.ooad.frontend.button.behavior.combinatorics;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

public class CombEpow extends DefaultButtonBehavior implements View.OnClickListener {

    public CombEpow(EditText txtOutput, Button btn) {
        super(txtOutput, btn);
    }

    @Override
    public void onClick(View view) {
        txtOutput.setText("e^");
    }
}
