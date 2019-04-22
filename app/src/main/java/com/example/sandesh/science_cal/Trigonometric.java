package com.example.sandesh.science_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Trigonometric extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String choices[]={"TRIGNOMETRY","Number System","Combinatorics","Matrix Operations"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigonometric);
        Spinner selection_screen=(Spinner)findViewById(R.id.selection_screen);
        selection_screen.setOnItemSelectedListener(this);
        ArrayAdapter comb=new ArrayAdapter(this,android.R.layout.simple_spinner_item,choices);
        comb.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selection_screen.setPrompt("Select Operation");
        selection_screen.setAdapter(comb);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), choices[position], Toast.LENGTH_LONG).show();
        if(choices[position]=="Number System"){
            finish();
            Intent number_system =new Intent(Trigonometric.this,NumberSystem.class);
            startActivity(number_system);
        }
        else if(choices[position]=="Combinatorics"){
            finish();
            Intent combination =new Intent(Trigonometric.this, Combinatorics.class);
            startActivity(combination);
        }
        else if(choices[position]=="Matrix Operations"){
            finish();
            Intent matrixoperations =new Intent(Trigonometric.this, MatrixOperations.class);
            startActivity(matrixoperations);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }
}
