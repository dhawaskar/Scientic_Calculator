/*
Authors: Sandesh Dhawaskar Sathyanarayana,Hasil Sharma and Gautham Kashim
Purpose: Last screen activity on the android application and performs the Matrix operations
*/

package com.example.sandesh.science_cal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by sandesh on 4/21/2019.
 */

public class MatrixOperations extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrixoperations);
        //Button to move to the original screen
        Button button=(Button)findViewById(R.id.selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric =new Intent(MatrixOperations.this, TrignoScreen.class);
                startActivity(trignometric);
            }});
    }
}
