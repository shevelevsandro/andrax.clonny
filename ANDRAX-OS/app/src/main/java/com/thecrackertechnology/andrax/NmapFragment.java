package com.thecrackertechnology.andrax;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.thecrackertechnology.dragonterminal.bridge.Bridge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NmapFragment extends Fragment  implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public String item;

    public String spinner_value;

    public String scanstr;

    public String spinneritem;

    public String nmapstylesave;
    public String vulners;
    public String dnsbrute;



    Switch SwitchSTYLE;
    Switch SwitchVULNERS;
    Switch SwitchDNSBrute;



    public NmapFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_nmap, container, false);



        Button btrscan = (Button) rootView.findViewById(R.id.btnscan);

        final EditText editText = (EditText) rootView.findViewById(R.id.editTexthostscan);



        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnernmap);


        spinner.setOnItemSelectedListener(this);



        List<String> scanmodes = new ArrayList<String>();
        scanmodes.add("Intense Steath: -sS -A -Pn");
        scanmodes.add("TCP SYN: -sS");
        scanmodes.add("TCP Connect: -sT");
        scanmodes.add("TCP ACK: -sA");
        scanmodes.add("UDP: -sU");
        scanmodes.add("ALL Ports: -p1-65535");
        scanmodes.add("ALL Ports SYN: -sS -p1-65535");
        scanmodes.add("ALL Ports Connect: -sT -p1-65535");
        scanmodes.add("Top 50 Ports: --top-ports 50");
        scanmodes.add("Steath Version: -sS -sV");
        scanmodes.add("Os fingerprint: -O");
        scanmodes.add("Os fingerprint SYN: -sS -O");


        vulners = "";
        dnsbrute = "";
        nmapstylesave = "";

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, scanmodes);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(dataAdapter);

        SwitchSTYLE = (Switch) rootView.findViewById(R.id.switchstylesheet);

        SwitchSTYLE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    nmapstylesave = "--stylesheet=/sdcard/nmap.xsl -oX /sdcard/nmapscan.xml";
                    try {
                        Process process = Runtime.getRuntime().exec("cp -Rf /data/data/com.thecrackertechnology.andrax/ANDRAX/nmap/nmap.xsl /sdcard/");

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error! Can't move nmap stylesheet", Toast.LENGTH_LONG).show();
                    }

                } else {

                    nmapstylesave = "";

                }

            }
        });


        SwitchVULNERS= (Switch) rootView.findViewById(R.id.SwitchVulners);

        SwitchVULNERS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                     vulners = "vulners,";

                } else {

                    vulners = "";

                }

            }
        });

        SwitchDNSBrute= (Switch) rootView.findViewById(R.id.SwitchDNSbrute);

        SwitchDNSBrute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    dnsbrute = "dns-brute,";

                } else {

                    dnsbrute = "";

                }

            }
        });




        btrscan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                scanstr = editText.getText().toString();

                startscan(scanstr);

            }
        });


        return rootView;



    }






        public void onClick(View v) {

    }


    @Override
    public void onPause() {

        super.onPause();
    }


    @Override
    public void onResume() {

        super.onResume();

    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        spinneritem = item;

        Toast.makeText(parent.getContext(), "Scan mode: " + item, Toast.LENGTH_SHORT).show();


    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void startscan(String scanstr) {
        String scanmode = "";

        switch (spinneritem) {
            case "Intense Steath: -sS -A -Pn":
                scanmode = "-sS -A -Pn";
                break;
            case "TCP SYN: -sS":
                scanmode = "-sS";
                break;
            case "TCP Connect: -sT":
                scanmode = "-sT";
                break;
            case "TCP ACK: -sA":
                scanmode = "-sA";
                break;
            case "UDP: -sU":
                scanmode = "-sU";
                break;
            case "ALL Ports: -p1-65535":
                scanmode = "-p1-65535";
                break;
            case "ALL Ports SYN: -sS -p1-65535":
                scanmode = "-sS -p1-65535";
                break;
            case "ALL Ports Connect: -sT -p1-65535":
                scanmode = "-sT -p1-65535";
                break;
            case "Top 50 Ports: --top-ports 50":
                scanmode = "--top-ports 50";
                break;
            case "Steath Version: -sS -sV":
                scanmode = "-sS -sV";
                break;
            case "Os fingerprint: -O":
                scanmode = "-O";
                break;
            case "Os fingerprint SYN: -sS -O":
                scanmode = "-sS -O";
                break;
        }

        run_hack_cmd("nmap " + scanstr + " " + scanmode + " " + " " + nmapstylesave + " --script=" + vulners + dnsbrute);

    }

    public void run_hack_cmd(String cmd) {

        Intent intent = Bridge.createExecuteIntent(cmd);
        startActivity(intent);

    }



}
