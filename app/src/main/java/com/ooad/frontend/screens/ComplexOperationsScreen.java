package com.ooad.frontend.screens;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.ooad.frontend.R;

public class ComplexOperationsScreen extends CommonScreenElements {

    private Button button;
    public ComplexOperationsScreen(){
        super(R.layout.activity_complex);

    }
    @Override
    protected void addOnclickListenersForButtonDelegation() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric =new Intent(ComplexOperationsScreen.this, TrignoScreen.class);
                startActivity(trignometric);
            }});
    }

    @Override
    protected void onCreateDelegation() {
        button=(Button)findViewById(R.id.selection);
    }
}
