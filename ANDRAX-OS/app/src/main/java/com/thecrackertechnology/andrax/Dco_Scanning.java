package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

import com.thecrackertechnology.dragonterminal.bridge.Bridge;

import me.ibrahimsn.particle.ParticleView;

public class Dco_Scanning extends Activity {

    ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);



        setContentView(R.layout.dco_scanning);

        particleView = findViewById(R.id.particleView_dco_scanning);

        CardView cardviewnmap = findViewById(R.id.card_view_nmap);
        CardView cardviewmasscan = findViewById(R.id.card_view_masscan);
        CardView cardviewraccoon = findViewById(R.id.card_view_raccoon);
        CardView cardviewsslscan = findViewById(R.id.card_view_sslscan);
        CardView cardviewamap = findViewById(R.id.card_view_amap);
        CardView cardviewbraa = findViewById(R.id.card_view_braa);
        CardView cardviewsshauditor = findViewById(R.id.card_view_ssh_auditor);
        CardView cardviewrapidscan = findViewById(R.id.card_view_rapidscan);
        CardView cardviewreconspider = findViewById(R.id.card_view_reconspider);
        CardView cardviewa2sv = findViewById(R.id.card_view_a2sv);
        CardView cardviewpysslscan = findViewById(R.id.card_view_pysslscan);
        CardView cardviewvault = findViewById(R.id.card_view_vault);
        CardView cardviewtldscanner = findViewById(R.id.card_view_tld_scanner);
        CardView cardviewnbtscan = findViewById(R.id.card_view_nbtscan);
        CardView cardviewikescan = findViewById(R.id.card_view_ikescan);
        CardView cardvieweyes = findViewById(R.id.card_view_eyes);


        cardviewnmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("nmap");

            }
        });

        cardviewmasscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("masscan --help");

            }
        });

        cardviewraccoon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("raccoon --help");

            }
        });

        cardviewsslscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("sslscan");

            }
        });

        cardviewamap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("amap");

            }
        });

        cardviewbraa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("braa");

            }
        });

        cardviewsshauditor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("ssh-auditor");

            }
        });

        cardviewrapidscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("rapidscan --help");

            }
        });

        /**
         *
         * Help me, i'm dying...
         *
         **/

        cardviewreconspider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("reconspider");

            }
        });

        cardviewa2sv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("a2sv");

            }
        });

        cardviewpysslscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("pysslscan -h");

            }
        });

        cardviewtldscanner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("tld_scanner");

            }
        });

        cardviewvault.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("vault");

            }
        });

        cardviewnbtscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("nbtscan");

            }
        });

        cardviewikescan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("ike-scan");

            }
        });

        cardvieweyes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("eyes");

            }
        });

    }

    public void run_hack_cmd(String cmd) {

        Intent intent = Bridge.createExecuteIntent(cmd);
        startActivity(intent);

    }

    @Override
    public void onPause() {
        particleView.pause();
        super.onPause();
        finish();
    }
}
