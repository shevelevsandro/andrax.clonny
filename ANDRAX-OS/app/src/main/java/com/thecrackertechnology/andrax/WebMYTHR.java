package com.thecrackertechnology.andrax;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WebMYTHR extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public String item;

    public String spinner_value;

    public String hoststr;

    public String spinneritem;

    public String boxtrue;

    /**
     *
     * Help me, i'm dying...
     *
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_mythr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boxtrue = "no";

        Button btrhack = (Button) findViewById(R.id.buttonhackit);

        final EditText editText = (EditText) findViewById(R.id.editTextmainwebmythr);




        Spinner spinner = (Spinner) findViewById(R.id.spinnerwebmythrmode);




        spinner.setOnItemSelectedListener(this);


        List<String> attackmodes = new ArrayList<String>();
        attackmodes.add("POST FUZZER");
        attackmodes.add("DIR SCANNER");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, attackmodes);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(dataAdapter);



        btrhack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                hoststr = editText.getText().toString();

                startattack(hoststr);

            }
        });


    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();


        switch(view.getId()) {
            case R.id.checkBoxhttpswebmythr:
                if (checked){
                    boxtrue = "yes";
                }

                else{
                    boxtrue = "no";
                    break;
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        spinneritem = item;


        Toast.makeText(parent.getContext(), "ATTACK Mode: " + item, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void startattack(String attackhost) {

        switch (spinneritem) {
            case "POST FUZZER":
                Intent intent = new Intent(WebMYTHR.this, POSTFuzzer.class);
                intent.putExtra("HOSTKEY", "http://" + attackhost);
                startActivity(intent);
                break;
            case "DIR SCANNER":
                Intent intent2 = new Intent(WebMYTHR.this, DirScanner.class);
                if(boxtrue.equals("yes")) {
                    intent2.putExtra("HOSTKEY", "https://" + attackhost);
                } else if(boxtrue.equals("no")) {
                    intent2.putExtra("HOSTKEY", "http://" + attackhost);
                }
                startActivity(intent2);
                break;

        }


    }

}
