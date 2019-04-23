///*
//Authors: Sandesh Dhawaskar Sathyanarayana,Hasil Sharma and Gautham Kashim
//Purpose: First screen activity on the android application and performs the Trigonometric operations
//*/
//
//package com.example.sandesh.science_cal;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.CompoundButton;
//import android.widget.Spinner;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import org.mariuszgromada.math.mxparser.Expression;
//import org.mariuszgromada.math.mxparser.mXparser;
//
///* implements functionality method for button clicks */
//
//
//
///* To know the state - Radian or Degree mode */
//
//class AppState {
//    private StateConstants degreeMode;
//
//    public AppState() {
//        degreeMode = StateConstants.RADIAN;
//    }
//
//    public void setModeToRadian(){
//        degreeMode = StateConstants.RADIAN;
//    }
//
//    public void setModeToDegree(){
//        degreeMode = StateConstants.DEGREE;
//    }
//
//    public StateConstants getDegreeMode() {
//        return degreeMode;
//    }
//}
///*
//This class basically handles the activity of the Trigonometric screen and uses the
//back end code to perform all the trigonometric operations
// */
//public class Trigonometric extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
//    //Four screen choices given to the user
//    String choices[] = {"OPTIONS", "Number System", "Combinatorics", "Matrix Operations"};
//    EditText txtOutput;
//    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDot, btnCE, btnPlus, btnSub, btnMul,
//            btnC, btnDiv, btnCos, btnSin, btnTan, btnCosh, btnSinh, btnTanh, btnMod, btnEqu,btnBrkt1,btnBrkt2;
//
//    private AppState appState;
//
//    public Trigonometric(){
//        super();
//        this.appState = new AppState();
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trigonometric);
//        //Spinner to move between the screen
//        Spinner selection_screen = (Spinner) findViewById(R.id.selection_screen);
//        selection_screen.setOnItemSelectedListener(this);
//        //Creating the drop down menue
//        ArrayAdapter comb = new ArrayAdapter(this, android.R.layout.simple_spinner_item, choices);
//        comb.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        selection_screen.setPrompt("Select Operation");
//        selection_screen.setAdapter(comb);
//        AddOnclickListenersForButton();
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
////        Toast.makeText(getApplicationContext(), choices[position], Toast.LENGTH_LONG).show();
//        //To go to Number system Screen
//        if (choices[position] == "Number System") {
//            finish();
//            Intent number_system = new Intent(this, NumberSystem.class);
//            startActivity(number_system);
//        }
//        //To go to Combinatorial screen
//        else if (choices[position] == "Combinatorics") {
//            finish();
//            Intent combination = new Intent(this, Combinatorics.class);
//            startActivity(combination);
//        }
//        //To go to Matrix operation screen
//        else if (choices[position] == "Matrix Operations") {
//            finish();
//            Intent matrixoperations = new Intent(this, MatrixOperations.class);
//            startActivity(matrixoperations);
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//// TODO Auto-generated method stub
//    }
//
//    private void AddOnclickListenersForButton() {
//
//        txtOutput = findViewById(R.id.editText);
//        btn0 = findViewById(R.id.button0);
//        btn1 = findViewById(R.id.button1);
//        btn2 = findViewById(R.id.button2);
//        btn3 = findViewById(R.id.button3);
//        btn4 = findViewById(R.id.button4);
//        btn5 = findViewById(R.id.button5);
//        btn6 = findViewById(R.id.button6);
//        btn7 = findViewById(R.id.button7);
//        btn8 = findViewById(R.id.button8);
//        btn9 = findViewById(R.id.button9);
//        btnCE = findViewById(R.id.btnce);
//        btnC = findViewById(R.id.btnc);
//        btnDot = findViewById(R.id.btndot);
//        btnPlus = findViewById(R.id.btnadd);
//        btnSub = findViewById(R.id.btnsub);
//        btnMul = findViewById(R.id.btnmul);
//        btnDiv = findViewById(R.id.btndiv);
//        btnCos = findViewById(R.id.btncos);
//        btnSin = findViewById(R.id.btnsin);
//        btnTan = findViewById(R.id.btntan);
//        btnCosh = findViewById(R.id.btncosh);
//        btnSinh = findViewById(R.id.btnsinh);
//        btnTanh = findViewById(R.id.btntanh);
//        btnMod = findViewById(R.id.btnmod);
//        btnEqu = findViewById(R.id.equals);
//        btnBrkt1= findViewById(R.id.btnbrkt);
//        btnBrkt2= findViewById(R.id.btnbrkt2);
//
//        btn0.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn0));
//        btn1.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn1));
//        btn2.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn2));
//        btn3.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn3));
//        btn4.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn4));
//        btn5.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn5));
//        btn6.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn6));
//        btn7.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn7));
//        btn8.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn8));
//        btn9.setOnClickListener(new DefaultButtonBehavior(txtOutput, btn9));
//
//
//
//        btnC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                txtOutput.setText("");
//            }
//        });
//
//        btnCE.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    int i = txtOutput.getText().length();
//                    txtOutput.setText(txtOutput.getText().subSequence(0, i - 1));
//                } catch (Exception ex) {
//                    txtOutput.setText("");
//                } finally {
//
//                }
//            }
//        });
//
//        btnDot.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnDot));
//        btnPlus.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnPlus));
//        btnSub.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnSub));
//        btnMul.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnMul));
//        btnDiv.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnDiv));
//        btnCos.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnCos));
//        btnSin.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnSin));
//        btnTan.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnTan));
//        btnCosh.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnCosh));
//        btnSinh.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnSinh));
//        btnTanh.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnTanh));
//        btnMod.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnMod));
//        btnBrkt1.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnBrkt1));
//        btnBrkt2.setOnClickListener(new DefaultButtonBehavior(txtOutput, btnBrkt2));
//
//        btnEqu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (appState.getDegreeMode() == StateConstants.DEGREE){
//                    mXparser.setDegreesMode();
//                } else if (appState.getDegreeMode() == StateConstants.RADIAN){
//                    mXparser.setRadiansMode();
//                } else {
//                    throw new UnsupportedOperationException();
//                }
//                String expStr = txtOutput.getText().toString();
//                System.out.println(txtOutput.getText().toString());
//                Expression exp = new Expression(expStr);
//                Double value = exp.calculate();
//                txtOutput.setText(Double.toString(value));
//                // final string
//
//            }
//        });
//
//        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggle);
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    Toast.makeText(getApplicationContext(), "Degree", Toast.LENGTH_LONG).show();
//                    appState.setModeToDegree();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Rad", Toast.LENGTH_LONG).show();
//                    appState.setModeToRadian();
//                }
//            }
//        });
//
//    }
//
//
//
//
//}