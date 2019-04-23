package com.example.sandesh.science_cal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.sandesh.science_cal.button.behavior.DefaultButtonBehavior;

public abstract class CommonScreenElements extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected  Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnCE, btnC;
    private final static String choices[] = {"OPTIONS", "Number System", "Combinatorics", "Matrix Operations"};
    private int RLayoutActivity;
    protected EditText txtOutput;
    public CommonScreenElements(int RLayoutActivity) {
        this.RLayoutActivity = RLayoutActivity;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(), choices[position], Toast.LENGTH_LONG).show();
        //To go to Number system Screen
        if (choices[position].equals("Number System")) {
            finish();
            Intent number_system = new Intent(this, NumberSystem.class);
            startActivity(number_system);
        }
        //To go to Combinatorial screen
        else if (choices[position].equals("Combinatorics")) {
            finish();
            Intent combination = new Intent(this, Combinatorics.class);
            startActivity(combination);
        }
        //To go to Matrix operation screen
        else if (choices[position].equals("Matrix Operations")) {
            finish();
            Intent matrixoperations = new Intent(this, MatrixOperations.class);
            startActivity(matrixoperations);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.RLayoutActivity);
        onCreateDelegation();
        addOnclickListenersForButton();
    }

    private void addOnclickListenersForButton(){

        txtOutput = findViewById(R.id.editText);

        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);
        btnCE = findViewById(R.id.btnce);
        btnC = findViewById(R.id.btnc);

        btn0.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn0));
        btn1.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn1));
        btn2.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn2));
        btn3.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn3));
        btn4.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn4));
        btn5.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn5));
        btn6.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn6));
        btn7.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn7));
        btn8.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn8));
        btn9.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn9));

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOutput.setText("");
            }
        });

        btnCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = txtOutput.getText().length();
                    txtOutput.setText(txtOutput.getText().subSequence(0, i - 1));
                } catch (Exception ex) {
                    txtOutput.setText("");
                }
            }
        });

        addOnclickListenersForButtonDelegation();
    }

    protected abstract void addOnclickListenersForButtonDelegation();
    protected abstract void onCreateDelegation();

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
