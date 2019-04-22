/*
Authors: Sandesh Dhawaskar Sathyanarayana,Hasil Sharma and Gautham Kashim
Purpose: First screen activity on the android application and performs the Trigonometric operations
*/

package com.example.sandesh.science_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NumberSystem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_system);
        //Button to move to the original screen
        Button button=(Button)findViewById(R.id.selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric =new Intent(NumberSystem.this, Trigonometric.class);
                startActivity(trignometric);
            }});
    }
}
