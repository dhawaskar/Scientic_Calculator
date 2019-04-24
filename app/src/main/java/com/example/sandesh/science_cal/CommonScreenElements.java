package com.example.sandesh.science_cal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sandesh.science_cal.button.behavior.DefaultButtonBehavior;

public abstract class CommonScreenElements extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected  Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDel;

    private final static String numberSystemDesc = "Number System";
    private final static String complexNumberDesc = "Complex Numbers";
    private final static String combinatoricsDesc = "Combinatorics";
    private final static String matrixDesc = "Matrix Operations";
    protected final static String choices[] = {"OPTIONS", numberSystemDesc, combinatoricsDesc,
            matrixDesc, complexNumberDesc};

    private int RLayoutActivity;
    protected EditText txtOutput;
    public CommonScreenElements(int RLayoutActivity) {
        this.RLayoutActivity = RLayoutActivity;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(), choices[position], Toast.LENGTH_LONG).show();
        //To go to Number system Screen
        if (choices[position].equals(numberSystemDesc)) {
            finish();
            Intent number_system = new Intent(this, NumberSystemScreen.class);
            startActivity(number_system);
        }
        //To go to Combinatorial screen
        else if (choices[position].equals(combinatoricsDesc)) {
            finish();
            Intent combination = new Intent(this, Combinatorics.class);
            startActivity(combination);
        }
        //To go to Matrix operation screen
        else if (choices[position].equals(matrixDesc)) {
            finish();
            Intent matrixoperations = new Intent(this, MatrixOperations.class);
            startActivity(matrixoperations);
        } else if (choices[position].equals(complexNumberDesc)){
            finish();
            Intent complex = new Intent(this, ComplexOperations.class);
            startActivity(complex);}
//        } else {
//            finish();
//            Intent trigno = new Intent(this, TrignoScreen.class);
//            startActivity(trigno);
//        }
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

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnDel = findViewById(R.id.btn_del);

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



        btnDel.setOnClickListener(new View.OnClickListener() {
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
