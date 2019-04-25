package com.ooad.frontend.screens;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.R;
import com.ooad.frontend.button.behavior.combinatorics.CombAppendSymbol;
import com.ooad.frontend.button.behavior.combinatorics.CombPC;
import com.ooad.frontend.button.behavior.combinatorics.CombEq;
//import org.apache.commons.lang3.StringEscapeUtils;


public class CombinatoricsScreen extends CommonScreenElements {

    Button btnFact, btnPlus, btnNcr, btnLn, btnSqrt, btnC, btnEqu, btnNeg, btna_pow_n,
            btnright_brkt, btnleft_brkt, btnMul;
    protected EditText txtOutput;

    public CombinatoricsScreen() {
        super(R.layout.activity_combinatorics);

    }

    @Override
    protected void addOnclickListenersForButtonDelegation() {

        btnFact.setOnClickListener(new CombAppendSymbol(txtOutput, btnFact, "!"));
        btnNeg.setOnClickListener(new CombAppendSymbol(txtOutput, btnNeg, "-"));
        btna_pow_n.setOnClickListener(new CombAppendSymbol(txtOutput, btna_pow_n, "^"));
        btnPlus.setOnClickListener(new CombPC(txtOutput, btnPlus, "+"));
        btnNcr.setOnClickListener(new CombPC(txtOutput, btnNcr, "C("));
        btnLn.setOnClickListener(new CombAppendSymbol(txtOutput, btnLn, "ln("));
        btnSqrt.setOnClickListener(new CombAppendSymbol(txtOutput, btnSqrt, "sqrt("));
        btnleft_brkt.setOnClickListener(new CombAppendSymbol(txtOutput, btnleft_brkt, "("));
        btnright_brkt.setOnClickListener(new CombAppendSymbol(txtOutput, btnright_brkt, ")"));
        btnMul.setOnClickListener(new CombAppendSymbol(txtOutput, btnMul, "*"));
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOutput.setText("");
            }
        });

        btnEqu.setOnClickListener(new CombEq(txtOutput, btnEqu));

    }

    @Override
    protected void onCreateDelegation() {

        //Button to move to the original screen
        Button button = (Button) findViewById(R.id.selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric = new Intent(CombinatoricsScreen.this, TrignoScreen.class);
                startActivity(trignometric);
            }
        });

        txtOutput = findViewById(R.id.editText);
        btnFact = findViewById(R.id.btn_fact);
        btnNeg = findViewById(R.id.btn_neg);
        btnPlus = findViewById(R.id.btn_plus);
        btnNcr = findViewById(R.id.btn_ncr);
        btna_pow_n = findViewById(R.id.btn_a_pow_n);
        btnLn = findViewById(R.id.btn_ln);
        btnleft_brkt = findViewById(R.id.btn_left_bracket);
        btnright_brkt = findViewById(R.id.btn_right_bracket);
        btnSqrt = findViewById(R.id.btn_sqrt);
        btnMul = findViewById(R.id.btn_mul);
        btnC = findViewById(R.id.btn_c);
        btnEqu = findViewById(R.id.btn_eq);
    }
}
