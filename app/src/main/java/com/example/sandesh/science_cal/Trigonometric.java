package com.example.sandesh.science_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Trigonometric extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String choices[]={"Trigonometry","Number System","Combinatorics","Matrix Operations"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigonometric);
//        Button button=(Button)findViewById(R.id.select_btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent number_system =new Intent(Trigonometric.this, Number_system.class);
//                startActivity(number_system);
//            }});
        Spinner selection_screen=(Spinner)findViewById(R.id.selection_screen);
        selection_screen.setOnItemSelectedListener(this);
        ArrayAdapter comb=new ArrayAdapter(this,android.R.layout.simple_spinner_item,choices);
        comb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selection_screen.setAdapter(comb);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), choices[position], Toast.LENGTH_LONG).show();
        if(choices[position]=="Number System"){
            finish();
            Intent number_system =new Intent(Trigonometric.this, Number_system.class);
            startActivity(number_system);
        }
        else if(choices[position]=="Combinatorics"){
            finish();
            Intent number_system =new Intent(Trigonometric.this, Combinatorics.class);
            startActivity(number_system);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }
}
