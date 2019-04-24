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
//import android.widget.Button;
//import android.widget.EditText;
//import org.mariuszgromada.math.mxparser.Expression;
//import org.mariuszgromada.math.mxparser.mXparser;
//
//
///* implements functionality method for button clicks */
//
////class DefaultButtonBehavior implements View.OnClickListener {
////    protected EditText txtInput;
////    protected Button btn;
////
////    public DefaultButtonBehavior(EditText txtOutput, Button btn) {
////        this.txtInput = txtOutput;
////        this.btn = btn;
////    }
////
////    @Override
////    public void onClick(View view) {
////        txtInput.setText(txtInput.getText().toString() + btn.getText().toString());
////    }
////}
//
///* To know the state - decimal or hex or binary */
//
//class AppStateNumberSystem {
//    private StateConstants Mode;
//
//    public AppStateNumberSystem() {
//        Mode = StateConstants.DECIMAL;
//    }
//
//    public void setModeToBinary(){
//        Mode = StateConstants.BINARY;
//    }
//
//    public void setModeToHex(){
//        Mode = StateConstants.HEX;
//    }
//
//    public StateConstants getDegreeMode() {
//        return Mode;
//    }
//}
//
//public class NumberSystem extends AppCompatActivity {
//
//    EditText txtInput,binOutput,hexOutput,decOutput;
//
//    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0,btnA,btnB,btnC,btnD,btnE,btnF, btnCe, btnPlus, btnSub,
//            btnCl, btnEqu;
//    private AppStateNumberSystem appState;
//
//    public NumberSystem(){
//        super();
//        this.appState = new AppStateNumberSystem();
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_number_system);
//        //Button to move to the original screen
//        Button button=(Button)findViewById(R.id.selection);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                Intent trignometric =new Intent(NumberSystem.this, TrignoScreen.class);
//                startActivity(trignometric);
//            }});
////        AddOnclickListenersForButton();
//    }
//
////    private void AddOnclickListenersForButton() {
//////
//////        txtInput = findViewById(R.id.editText);
//////        binOutput = findViewById(R.id.editText2);
//////        hexOutput = findViewById(R.id.editText3);
//////        decOutput = findViewById(R.id.editText4);
//////
//////
//////        btn0 = findViewById(R.id.btn0);
//////        btn1 = findViewById(R.id.btn1);
//////        btn2 = findViewById(R.id.btn2);
//////        btn3 = findViewById(R.id.btn3);
//////        btn4 = findViewById(R.id.btn4);
//////        btn5 = findViewById(R.id.btn5);
//////        btn6 = findViewById(R.id.btn6);
//////        btn7 = findViewById(R.id.btn7);
//////        btn8 = findViewById(R.id.btn8);
//////        btn9 = findViewById(R.id.btn9);
//////        btnA = findViewById(R.id.btna);
//////        btnB = findViewById(R.id.btnb);
//////        btnC = findViewById(R.id.btnc);
//////        btnD = findViewById(R.id.btnd);
//////        btnE = findViewById(R.id.btne);
//////        btnF = findViewById(R.id.btnf);
//////        btnCe = findViewById(R.id.btnce);
//////        btnCl = findViewById(R.id.btncl);
//////        btnPlus = findViewById(R.id.btnadd);
//////        btnSub = findViewById(R.id.btnsub);
////////        btnMul = findViewById(R.id.btnmul);
////////        btnDiv = findViewById(R.id.btndiv);
//////
//////        btnEqu = findViewById(R.id.equals);
//////
//////
//////        btn0.setOnClickListener(new DefaultButtonBehavior(txtInput, btn0));
//////        btn1.setOnClickListener(new DefaultButtonBehavior(txtInput, btn1));
//////        btn2.setOnClickListener(new DefaultButtonBehavior(txtInput, btn2));
//////        btn3.setOnClickListener(new DefaultButtonBehavior(txtInput, btn3));
//////        btn4.setOnClickListener(new DefaultButtonBehavior(txtInput, btn4));
//////        btn5.setOnClickListener(new DefaultButtonBehavior(txtInput, btn5));
//////        btn6.setOnClickListener(new DefaultButtonBehavior(txtInput, btn6));
//////        btn7.setOnClickListener(new DefaultButtonBehavior(txtInput, btn7));
//////        btn8.setOnClickListener(new DefaultButtonBehavior(txtInput, btn8));
//////        btn9.setOnClickListener(new DefaultButtonBehavior(txtInput, btn9));
//////        btnA.setOnClickListener(new DefaultButtonBehavior(txtInput, btnA));
//////        btnB.setOnClickListener(new DefaultButtonBehavior(txtInput, btnB));
//////        btnC.setOnClickListener(new DefaultButtonBehavior(txtInput, btnC));
//////        btnD.setOnClickListener(new DefaultButtonBehavior(txtInput, btnD));
//////        btnE.setOnClickListener(new DefaultButtonBehavior(txtInput, btnE));
//////        btnF.setOnClickListener(new DefaultButtonBehavior(txtInput, btnF));
//////
//////
//////
//////        btnCl.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////                txtInput.setText("");
//////            }
//////        });
//////
//////        btnCe.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////                try {
//////                    int i = txtInput.getText().length();
//////                    txtInput.setText(txtInput.getText().subSequence(0, i - 1));
//////                } catch (Exception ex) {
//////                    txtInput.setText("");
//////                } finally {
//////
//////                }
//////            }
//////        });
//////
//////        btnPlus.setOnClickListener(new DefaultButtonBehavior(txtInput, btnPlus));
//////        btnSub.setOnClickListener(new DefaultButtonBehavior(txtInput, btnSub));
//////        btnMul.setOnClickListener(new DefaultButtonBehavior(txtInput, btnMul));
//////        btnDiv.setOnClickListener(new DefaultButtonBehavior(txtInput, btnDiv));
//////
//////
//////        btnEqu.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////
//////
//////                if (appState.getDegreeMode() == StateConstants.DECIMAL){
//////
//////                } else if (appState.getDegreeMode() == StateConstants.RADIAN){
//////                    mXparser.setRadiansMode();
//////                } else {
//////                    throw new UnsupportedOperationException();
//////                }
//////                String expStr = txtInput.getText().toString();
//////                System.out.println(txtInput.getText().toString());
//////                Expression exp = new Expression(expStr);
//////                Double value = exp.calculate();
//////                txtInput.setText(Double.toString(value));
//////                // final string
//////
//////            }
//////        });
//////
//////    }
//}
