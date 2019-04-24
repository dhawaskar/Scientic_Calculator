package com.example.sandesh.science_cal;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ComplexOperations extends CommonScreenElements {

    private Button button;
    public ComplexOperations(){
        super(R.layout.activity_complex);

    }
    @Override
    protected void addOnclickListenersForButtonDelegation() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric =new Intent(ComplexOperations.this, TrignoScreen.class);
                startActivity(trignometric);
            }});
    }

    @Override
    protected void onCreateDelegation() {
        button=(Button)findViewById(R.id.selection);
    }
}
