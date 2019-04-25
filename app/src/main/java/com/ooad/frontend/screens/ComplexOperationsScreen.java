package com.ooad.frontend.screens;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.ooad.frontend.R;

public class ComplexOperationsScreen extends CommonScreenElements {

    public Button btnImg,btnDot,btnEqu,btnSub,btnAdd,btnC,btnInv,btnDiv,btnMul;
    public ComplexOperationsScreen(){
        super(R.layout.activity_complex);

    }
    @Override
    protected void addOnclickListenersForButtonDelegation() {

    }

    @Override
    protected void onCreateDelegation() {

        Button button = (Button) findViewById(R.id.selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric = new Intent(ComplexOperationsScreen.this, TrignoScreen.class);
                startActivity(trignometric);
            }

        });

        btnImg=(Button)findViewById(R.id.btn_img);
        btnDot=(Button)findViewById(R.id.btn_dot);
        btnEqu=(Button)findViewById(R.id.btn_equ);
        btnSub=(Button)findViewById(R.id.btn_sub);
        btnAdd=(Button)findViewById(R.id.btn_add);
        btnC=(Button)findViewById(R.id.btn_c);
        btnInv=(Button)findViewById(R.id.btn_inv);
        btnDiv=(Button)findViewById(R.id.btn_div);
        btnMul=(Button)findViewById(R.id.btn_mul);
    }
}
