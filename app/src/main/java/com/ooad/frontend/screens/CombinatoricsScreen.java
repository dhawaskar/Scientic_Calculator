package com.ooad.frontend.screens;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.R;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
//import org.apache.commons.lang3.StringEscapeUtils;


import org.mariuszgromada.math.mxparser.Expression;

public class CombinatoricsScreen extends CommonScreenElements {

    Button btnFact,btnSq,btnEpow,btnNpr,btnNcr,btnPi,btnLn ,btnLog,btnSqrt,btnNsqrt,btnC,btnEqu;
    protected EditText txtOutput;
    public CombinatoricsScreen() {
        super(R.layout.activity_combinatorics);

    }

    @Override
    protected void addOnclickListenersForButtonDelegation() {
        btnFact.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnFact));
        btnSq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldStr = txtOutput.getText().toString();
                String newStr= oldStr.replace("a^2","");
                newStr= newStr+ "*" +newStr;
                Expression exp = new Expression(newStr);
                Double value = exp.calculate();
                txtOutput.setText(Double.toString(value));

            }
        });

        btnEpow.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnFact));
        btnNpr.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnNpr));
        btnNcr.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnNcr));
        btnPi.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnPi));
        btnLn.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnLn));
        btnLog.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnLog));
        /*btnSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldStr = txtOutput.getText().toString();
                String newStr= oldStr.replace("","");
                newStr= newStr+ "*" +newStr;
                Expression exp = new Expression(newStr);
                Double value = exp.calculate();
                txtOutput.setText(Double.toString(value));

            }
        });*/
        btnSqrt.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnSqrt));

        btnNsqrt.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnNsqrt));
        btnLog.setOnClickListener(new DefaultButtonBehavior(txtOutput,btnLog));
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOutput.setText("");
            }
        });
        btnEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String expStr = txtOutput.getText().toString();
                System.out.println(txtOutput.getText().toString());
                Expression exp = new Expression(expStr);
                Double value = exp.calculate();
                txtOutput.setText(Double.toString(value));
                // final string

            }
        });

    }

    @Override
    protected void onCreateDelegation() {
        //Button to move to the original screen
        Button button=(Button)findViewById(R.id.selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric =new Intent(CombinatoricsScreen.this, TrignoScreen.class);
                startActivity(trignometric);
            }});
        txtOutput = findViewById(R.id.editText);

        btnFact = findViewById(R.id.btn_fact);
        btnSq=findViewById(R.id.btn_sq);
        btnEpow = findViewById(R.id.btn_epow);
        btnNpr = findViewById(R.id.btn_npr);
        btnNcr = findViewById(R.id.btn_ncr);
        btnPi = findViewById(R.id.btn_pi);
        btnLn = findViewById(R.id.btn_ln);
        btnLog = findViewById(R.id.btn_log);
        btnSqrt = findViewById(R.id.btn_sqrt);
        btnNsqrt = findViewById(R.id.btn_nsqrt);
        btnC = findViewById(R.id.btn_c);
        btnEqu = findViewById(R.id.btn_eq);
    }
}
