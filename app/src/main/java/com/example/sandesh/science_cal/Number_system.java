package com.example.sandesh.science_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Number_system extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_system);
        Button button=(Button)findViewById(R.id.select_btn_tri);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric =new Intent(Number_system.this, Trigonometric.class);
                startActivity(trignometric);
            }});
    }
}
