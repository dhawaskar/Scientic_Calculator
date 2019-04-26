package com.ooad.frontend.screens;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.ooad.backend.data.types.ComplexElem;
import com.ooad.frontend.R;
import com.ooad.frontend.button.behavior.DefaultButtonBehavior;
import com.ooad.frontend.button.behavior.complex.context.ComplexContext;
import com.ooad.frontend.button.behavior.complex.context.ComplexDefaultButtonBehavior;
import com.ooad.frontend.misc.Utils;

import org.apache.commons.math3.complex.Complex;

import java.util.regex.Pattern;

public class ComplexOperationsScreen extends CommonScreenElements {

    private ComplexContext ctx;
    public Button btnComma, btnDot, btnEqu, btnSub, btnAdd, btnC, btnInv, btnDiv, btnMul;

    public ComplexOperationsScreen() {
        super(R.layout.activity_complex);
        ctx = new ComplexContext();


    }


    private ComplexElem helper(String str, String opt){
        String[] cmp1Cmp2 = str.split(Pattern.quote(opt));

        Double[] real_img1 = Utils.stringToDoublerArray(cmp1Cmp2[0]);
        Double[] real_img2 = Utils.stringToDoublerArray(cmp1Cmp2[1]);
        ComplexElem c1 = new ComplexElem(real_img1[0], real_img1[1]);
        ComplexElem c2 = new ComplexElem(real_img2[0], real_img2[1]);
        ComplexElem ans = null;

        if (opt == "+"){
            ans = (ComplexElem) c1.add(c2);
        } else if (opt == "*"){
            ans = (ComplexElem) c1.mul(c2);
        } else if (opt == "/"){
            ans = (ComplexElem) c1.div(c2);
        } else if (opt == "-"){
            ans = (ComplexElem) c1.add(c2.negate());
        }

        return ans;
    }

    @Override
    protected void addOnclickListenersForButtonDelegation() {
        btnComma.setOnClickListener(new ComplexDefaultButtonBehavior(txtOutput, btnComma, ctx));
        btnDot.setOnClickListener(new ComplexDefaultButtonBehavior(txtOutput, btnDot, ctx));
        btnSub.setOnClickListener(new ComplexDefaultButtonBehavior(txtOutput, btnSub, ctx));
        btnAdd.setOnClickListener(new ComplexDefaultButtonBehavior(txtOutput, btnAdd, ctx));
        btnMul.setOnClickListener(new ComplexDefaultButtonBehavior(txtOutput, btnMul, ctx));
        btnDiv.setOnClickListener(new ComplexDefaultButtonBehavior(txtOutput, btnDiv, ctx));
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOutput.setText("");
            }
        });

        btnEqu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String exp = txtOutput.getText().toString();
                ComplexElem ans = null;
                if (ctx.isAddOpt()){
                    ctx.setAddOpt(false);
                    ans = helper(exp, "+");
                } else if (ctx.isSubOpt()){
                    ctx.setSubOpt(false);
                    ans = helper(exp, "-");
                } else if (ctx.isMulOpt()){
                    ctx.setMulOpt(false);
                    ans = helper(exp, "*");
                } else if (ctx.isDivOpt()){
                    ctx.setDivOpt(false);
                    ans = helper(exp, "/");
                }

                txtOutput.setText(String.format("%.4f, %.4f", ans.getReal().decimal, ans.getImag().decimal));

            }
        });

        btnInv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String exp = txtOutput.getText().toString();
                Double[] real_img = Utils.stringToDoublerArray(exp);
                ComplexElem cmp = new ComplexElem(real_img[0], real_img[1]);
                ComplexElem cmp_inv = cmp.inv();
                // TODO:
                txtOutput.setText(String.format("%.4f, %.4f", cmp_inv.getReal().decimal, cmp_inv.getImag().decimal));
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
                Intent trignometric = new Intent(ComplexOperationsScreen.this, TrignoScreen.class);
                startActivity(trignometric);
            }

        });

        btnComma = findViewById(R.id.btn_comma);
        btnDot = findViewById(R.id.btn_dot);
        btnEqu = findViewById(R.id.btn_equ);
        btnSub = findViewById(R.id.btn_sub);
        btnAdd = findViewById(R.id.btn_add);
        btnC = findViewById(R.id.btn_c);
        btnInv = findViewById(R.id.btn_inv);
        btnDiv = findViewById(R.id.btn_div);
        btnMul = findViewById(R.id.btn_mul);
    }
}
