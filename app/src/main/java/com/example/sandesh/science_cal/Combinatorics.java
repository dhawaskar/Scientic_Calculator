package com.example.sandesh.science_cal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Combinatorics extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String choices[]={"Trigonometry","Number_System","Combinatorics"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combinatorics);
        Spinner selection_screen_comb=(Spinner)findViewById(R.id.selection_screen_comb);
        selection_screen_comb.setOnItemSelectedListener(this);
        ArrayAdapter comb=new ArrayAdapter(this,android.R.layout.simple_spinner_item,choices);
        comb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selection_screen_comb.setAdapter(comb);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), choices[position], Toast.LENGTH_LONG).show();
        if(choices[position]=="Number_System"){
            finish();
            Intent number_system =new Intent(Combinatorics.this, Number_system.class);
            startActivity(number_system);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub
    }
}
