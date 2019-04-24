package com.example.sandesh.science_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Combinatorics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combinatorics);
        //Button to move to the original screen
        Button button=(Button)findViewById(R.id.selection);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent trignometric =new Intent(Combinatorics.this, TrignoScreen.class);
                startActivity(trignometric);
            }});
    }
}
