package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.thecrackertechnology.dragonterminal.bridge.Bridge;

public class TOPTools extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toptools);


        CardView cardviewnmap = findViewById(R.id.card_view_nmap);
        CardView cardviewmetasploit = findViewById(R.id.card_view_metasploit);
        CardView cardviewaircrack = findViewById(R.id.card_view_aircrack);
        CardView cardviewroutersploit = findViewById(R.id.card_view_routersploit);
        CardView cardviewbettercap = findViewById(R.id.card_view_bettercap);
        CardView cardviewmitmproxy = findViewById(R.id.card_view_mitmproxy);
        CardView cardviewgophish = findViewById(R.id.card_view_gophish);
        CardView cardviewrapidscan = findViewById(R.id.card_view_rapidscan);
        CardView cardviewscapy = findViewById(R.id.card_view_scapy);
        CardView cardviewevilginx2 = findViewById(R.id.card_view_evilginx2);



        cardviewnmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("nmap");

            }
        });

        /**
         *
         * Help me, i'm dying...
         *
         **/

        cardviewmetasploit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("msfconsole");

            }
        });

        cardviewaircrack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("aircrack-ng");

            }
        });

        cardviewroutersploit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("rsf");

            }
        });

        cardviewbettercap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("bettercap");

            }
        });

        cardviewmitmproxy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("mitmproxy");

            }
        });

        cardviewgophish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("gophish");

            }
        });

        cardviewrapidscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("rapidscan --help");

            }
        });

        cardviewscapy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("scapy");

            }
        });

        cardviewevilginx2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("evilginx2");

            }
        });

    }

    public void run_hack_cmd(String cmd) {

        Intent intent = Bridge.createExecuteIntent(cmd);
        startActivity(intent);

    }



}
