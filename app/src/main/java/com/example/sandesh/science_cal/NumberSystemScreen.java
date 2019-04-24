package com.example.sandesh.science_cal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sandesh.science_cal.button.behavior.DefaultButtonBehavior;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;

public class NumberSystemScreen extends CommonScreenElements {
    Button btnPlus, btnSub, btnEqu, btnhA, btnhB, btnhC, btnhD, btnhE, btnhF,btnBin,btnDec,btnHex;
    protected EditText txtInput, binOutput, hexOutput, decOutput;

    class AppState {
        private StateConstants mode;

        public AppState() {
            mode = StateConstants.DECIMAL;
        }

        public void setModeToBinary(){
            mode = StateConstants.BINARY;
        }

        public void setModeToHex(){
            mode = StateConstants.HEX;
        }

        public void setModeToDecimal(){
            mode = StateConstants.DECIMAL;
        }

        public StateConstants getMode() {
            return mode;
        }
    }

    private AppState appState;

    public NumberSystemScreen() {
        super(R.layout.activity_number_system);
        this.appState = new AppState();
    }
    private String intToBinary(String exp){
        return Integer.toBinaryString(Integer.parseInt(exp));
    }
    private String intToHex(String exp){
        return Integer.toHexString(Integer.parseInt(exp));
    }
    private String hextoInt(String exp){
        return Integer.toString(Integer.parseInt(exp,16));

    }
    private String bintoInt(String exp){
        return Integer.toString(Integer.parseInt(exp,2));
    }

    @Override
    protected void addOnclickListenersForButtonDelegation() {

        btnPlus.setOnClickListener(new DefaultButtonBehavior(txtInput, btnPlus));
        btnSub.setOnClickListener(new DefaultButtonBehavior(txtInput, btnSub));

        btnhA.setOnClickListener(new DefaultButtonBehavior(txtInput, btnhA));
        btnhB.setOnClickListener(new DefaultButtonBehavior(txtInput, btnhB));
        btnhC.setOnClickListener(new DefaultButtonBehavior(txtInput, btnhC));
        btnhD.setOnClickListener(new DefaultButtonBehavior(txtInput, btnhD));
        btnhE.setOnClickListener(new DefaultButtonBehavior(txtInput, btnhE));
        btnhF.setOnClickListener(new DefaultButtonBehavior(txtInput, btnhF));

        btnBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appState.setModeToBinary();
            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appState.setModeToDecimal();
            }
        });
        btnHex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appState.setModeToHex();
            }
        });

        btnEqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String expStr = txtInput.getText().toString();
                String exp;
                System.out.println(txtInput.getText().toString());
                if (appState.getMode() == StateConstants.DECIMAL) {
                    exp=intToBinary(expStr);
                    binOutput.setText(exp);
                    exp=intToHex(expStr);
                    hexOutput.setText(exp);
                } else if (appState.getMode() == StateConstants.HEX) {
                    exp=hextoInt(expStr);
                    decOutput.setText(exp);
                    exp=intToBinary(exp);
                    binOutput.setText(exp);
                } else if(appState.getMode()== StateConstants.BINARY){
                    exp=bintoInt(expStr);
                    decOutput.setText(exp);
                    exp=intToHex(exp);
                    hexOutput.setText(exp);
                }else {
                    throw new UnsupportedOperationException();
                }

            }
        });
    }

    @Override
    protected void onCreateDelegation() {
        Button button = (Button) findViewById(R.id.selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric = new Intent(NumberSystemScreen.this, TrignoScreen.class);
                startActivity(trignometric);
            }
        });

        txtInput = findViewById(R.id.editText);
        binOutput = findViewById(R.id.editText2);
        hexOutput = findViewById(R.id.editText3);
        decOutput = findViewById(R.id.editText4);

        btnPlus = findViewById(R.id.btn_add);
        btnSub = findViewById(R.id.btn_sub);

        btnEqu = findViewById(R.id.btn_eq);

        btnhA = findViewById(R.id.btna);
        btnhB = findViewById(R.id.btnb);
        btnhC = findViewById(R.id.btnc);
        btnhD = findViewById(R.id.btnd);
        btnhE = findViewById(R.id.btne);
        btnhF = findViewById(R.id.btnf);

        btnDec= findViewById(R.id.btndec);
        btnHex=findViewById(R.id.btnhex);
        btnBin=findViewById(R.id.btnbin);

    }
}
