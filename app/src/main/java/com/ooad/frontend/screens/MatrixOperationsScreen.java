/*
Authors: Sandesh Dhawaskar Sathyanarayana,Hasil Sharma and Gautham Kashim
Purpose: Last screen activity on the android application and performs the Matrix operations
*/

package com.ooad.frontend.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ooad.frontend.R;
import com.ooad.frontend.button.behavior.matrix.MatrixAdd;
import com.ooad.frontend.button.behavior.matrix.MatrixCom;
import com.ooad.frontend.button.behavior.matrix.MatrixDet;
import com.ooad.frontend.button.behavior.matrix.MatrixEqu;
import com.ooad.frontend.button.behavior.matrix.MatrixInv;
import com.ooad.frontend.button.behavior.matrix.MatrixMul;
import com.ooad.frontend.button.behavior.matrix.MatrixSize;
import com.ooad.frontend.button.behavior.matrix.MatrixTrp;
import com.ooad.frontend.button.behavior.matrix.context.MatrixContext;

/**
 * Created by sandesh on 4/21/2019.
 */

public class MatrixOperationsScreen extends CommonScreenElements {

    Button btnSize, btnTrp, btnInv, btnC, btnAdd, btnDet, btnMul, btnEqu, btnCom;
    protected EditText txtOutput;
    private MatrixContext ctx;

    public MatrixOperationsScreen() {
        super(R.layout.activity_matrixoperations);
        ctx = new MatrixContext();
    }

    @Override
    protected void addOnclickListenersForButtonDelegation() {
        btnCom.setOnClickListener(new MatrixCom(txtOutput, btnCom, ctx));
        btnSize.setOnClickListener(new MatrixSize(txtOutput, btnSize, ctx));
        btnEqu.setOnClickListener(new MatrixEqu(txtOutput, btnEqu, ctx));
        btnInv.setOnClickListener(new MatrixInv(txtOutput, btnInv, ctx));
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOutput.setText("");
            }
        });

        btnAdd.setOnClickListener(new MatrixAdd(txtOutput, btnAdd, ctx));
        btnMul.setOnClickListener(new MatrixMul(txtOutput, btnMul, ctx));
        btnDet.setOnClickListener(new MatrixDet(txtOutput, btnDet, ctx));

        btnTrp.setOnClickListener(new MatrixTrp(txtOutput, btnTrp, ctx));

    }

    @Override
    protected void onCreateDelegation() {
        Button button = (Button) findViewById(R.id.selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric = new Intent(MatrixOperationsScreen.this, TrignoScreen.class);
                startActivity(trignometric);
            }

        });


        txtOutput = findViewById(R.id.editText);

        btnSize = findViewById(R.id.btn_size);
        btnTrp = findViewById(R.id.btn_trp);
        btnInv = findViewById(R.id.btn_inv);
        btnC = findViewById(R.id.btn_c);
        btnAdd = findViewById(R.id.btn_add);
        btnDet = findViewById(R.id.btn_det);
        btnMul = findViewById(R.id.btn_mul);
        btnEqu = findViewById(R.id.btn_eq);
        btnCom = findViewById(R.id.btn_com);

    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_matrixoperations);
//        //Button to move to the original screen
//        Button button = (Button) findViewById(R.id.selection);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                Intent trignometric = new Intent(MatrixOperationsScreen.this, TrignoScreen.class);
//                startActivity(trignometric);
//            }
//        });
//
//    }
}
