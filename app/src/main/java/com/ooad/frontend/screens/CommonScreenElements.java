/*
Authors: Sandesh D Sathyanarayana, Hasil Sharma and Gautham Kashim
File: Common java base class file that would be derived by all the five activities for each screen of the application.
 */

package com.ooad.frontend.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.R;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
/*
Class: This class retrives the common button between all the activities and defines two abstract methods.
Key attributes: btn1,......btnDel id's of all the common buttons between multitple activities to support the five screen in the
andriod application.
 */
public abstract class CommonScreenElements extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected  Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDel;

    private final static String numberSystemDesc = "Number System";
    private final static String complexNumberDesc = "Complex Numbers";
    private final static String combinatoricsDesc = "CombinatoricsScreen";
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
            Intent combination = new Intent(this, CombinatoricsScreen.class);
            startActivity(combination);
        }
        //To go to Matrix operation screen
        else if (choices[position].equals(matrixDesc)) {
            finish();
            Intent matrixoperations = new Intent(this, MatrixOperationsScreen.class);
            startActivity(matrixoperations);
        } else if (choices[position].equals(complexNumberDesc)){ // To go to Complex Number System screen
            finish();
            Intent complex = new Intent(this, ComplexOperationsScreen.class);
            startActivity(complex);}
//        } else {
//            finish();
//            Intent trigno = new Intent(this, TrignoScreen.class);
//            startActivity(trigno);
//        }
    }
    //common activity called once the activity is launched.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.RLayoutActivity);
        onCreateDelegation();
        addOnclickListenersForButton();
    }

    private void addOnclickListenersForButton(){
        //common buttons/ text area of all the screens
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


        //Call back functions for button clicked.
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
    // Two abstract methods that would be overridden by five activities java files to access specific buttons
    protected abstract void addOnclickListenersForButtonDelegation();
    protected abstract void onCreateDelegation();

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
