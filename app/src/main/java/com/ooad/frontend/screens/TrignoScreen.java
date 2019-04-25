/*
Authors: Sandesh D Sathyanarayana, Hasil Sharma and Gautham Kashim
File: Java Activity file to support the Trigonometric operation of our Andriod Application.
 */
package com.ooad.frontend.screens;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.lang.*;

import com.ooad.frontend.R;
import com.ooad.frontend.misc.StateConstants;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;
/**
* Class: It access the buttons and performs the specified trigonometric operations
* Key Attributes: buttons specific to this activity layout ex: btnSinh
 * */

public class TrignoScreen extends CommonScreenElements {
    /**
     * This class is used used to remember the state of the mode in the layout screen
     * States can be Radian or in Degree
     */
    class AppState {
        private StateConstants degreeMode;

        /**
         * Constructor instantiates the state to radian mode
         */
        public AppState() {
            degreeMode = StateConstants.RADIAN;
        }

        /**
         * Setter for Radian mode
         */
        public void setModeToRadian() {
            degreeMode = StateConstants.RADIAN;
        }

        /**
         * Setter for Degree mode
         */
        public void setModeToDegree() {
            degreeMode = StateConstants.DEGREE;
        }

        /**
         * Getter for State in the screen
         * @return mode
         */
        public StateConstants getDegreeMode() {
            return degreeMode;
        }
    }

    private Button btnSub, btnMul, btnDiv, btnCos, btnSin, btnTan, btnCosh, btnSinh, btnTanh,
            btnMod, btnEqu, btnBrkt1, btnBrkt2, btnDot, btnPlus,btnC;
    private ToggleButton toggleButton;

    private AppState appState;

    public TrignoScreen() {
        super(R.layout.activity_trigonometric);
        this.appState = new AppState();

    }

    /**
     * Defining specific operations after the button clicks that are different from DefaultBehaviour
     * @return void
     */
    @Override
    protected void addOnclickListenersForButtonDelegation() {
        btnDot.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnDot));
        btnPlus.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnPlus));
        btnSub.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnSub));
        btnMul.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnMul));
        btnDiv.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnDiv));
        btnCos.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnCos));
        btnSin.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnSin));
        btnTan.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnTan));
        btnCosh.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnCosh));
        btnSinh.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnSinh));
        btnTanh.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnTanh));
        btnMod.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnMod));
        btnBrkt1.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnBrkt1));
        btnBrkt2.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnBrkt2));

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOutput.setText("");
            }
        });
        /**
         * checks for state and evaluates expression using the mxparser expression class
         */
        btnEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (appState.getDegreeMode() == StateConstants.DEGREE) {
                    mXparser.setDegreesMode();
                } else if (appState.getDegreeMode() == StateConstants.RADIAN) {
                    mXparser.setRadiansMode();
                } else {
                    throw new UnsupportedOperationException();
                }
                String expStr = txtOutput.getText().toString();
                System.out.println(txtOutput.getText().toString());
                Expression exp = new Expression(expStr);
                Double value = exp.calculate();
                txtOutput.setText(Double.toString(value));
                // final string

            }
        });
        /**
         * To check for radian or degree mode setting. Displays the selected operation for user
         */
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(getApplicationContext(), "Degree", Toast.LENGTH_LONG).show();
                    appState.setModeToDegree();
                } else {
                    Toast.makeText(getApplicationContext(), "Rad", Toast.LENGTH_LONG).show();
                    appState.setModeToRadian();
                }
            }
        });

    }
    /**
     * button id to Button id mapping on Button's creation
     * @return void
     */
    @Override
    protected void onCreateDelegation() {

        Spinner selection_screen = (Spinner) findViewById(R.id.selection_screen);
        selection_screen.setOnItemSelectedListener(this);
        //Creating the drop down menue
        ArrayAdapter comb = new ArrayAdapter(this, android.R.layout.simple_spinner_item, choices);
        comb.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selection_screen.setPrompt("Select Operation");
        selection_screen.setAdapter(comb);

        btnDot = findViewById(R.id.btndot);
        btnPlus = findViewById(R.id.btnadd);
        btnSub = findViewById(R.id.btnsub);
        btnMul = findViewById(R.id.btnmul);
        btnDiv = findViewById(R.id.btndiv);
        btnCos = findViewById(R.id.btncos);
        btnSin = findViewById(R.id.btnsin);
        btnTan = findViewById(R.id.btntan);
        btnCosh = findViewById(R.id.btncosh);
        btnSinh = findViewById(R.id.btnsinh);
        btnTanh = findViewById(R.id.btntanh);
        btnMod = findViewById(R.id.btnmod);
        btnEqu = findViewById(R.id.equals);
        btnBrkt1 = findViewById(R.id.btnbrkt);
        btnBrkt2 = findViewById(R.id.btnbrkt2);
        btnC = findViewById(R.id.btn_c);
        toggleButton = (ToggleButton) findViewById(R.id.toggle);

    }
}