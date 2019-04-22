/*
Authors: Sandesh Dhawaskar Sathyanarayana,Hasil Sharma and Gautham Kashim
Purpose: First screen activity on the android application and performs the Trigonometric operations
*/

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

/*
This class basically handles the activity of the Trigonometric screen and uses the
back end code to perform all the trigonometric operations
 */
public class Trigonometric extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //Four screen choices given to the user
    String choices[]={"TRIGNOMETRY","Number System","Combinatorics","Matrix Operations"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigonometric);
        //Spinner to move between the screen
        Spinner selection_screen=(Spinner)findViewById(R.id.selection_screen);
        selection_screen.setOnItemSelectedListener(this);
        //Creating the drop down menue
        ArrayAdapter comb=new ArrayAdapter(this,android.R.layout.simple_spinner_item,choices);
        comb.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selection_screen.setPrompt("Select Operation");
        selection_screen.setAdapter(comb);
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), choices[position], Toast.LENGTH_LONG).show();
        //To go to Number system Screen
        if(choices[position]=="Number System"){
            finish();
            Intent number_system =new Intent(Trigonometric.this,NumberSystem.class);
            startActivity(number_system);
        }
        //To go to Combinatorial screen
        else if(choices[position]=="Combinatorics"){
            finish();
            Intent combination =new Intent(Trigonometric.this, Combinatorics.class);
            startActivity(combination);
        }
        //To go to Matrix operation screen
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
