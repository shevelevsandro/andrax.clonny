package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

import com.thecrackertechnology.dragonterminal.bridge.Bridge;

import me.ibrahimsn.particle.ParticleView;

public class Dco_Wireless_Hacking extends Activity {

    ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.dco_wireless_hacking);

        particleView = findViewById(R.id.particleView_dco_wireless);


        CardView cardviewvmp = findViewById(R.id.card_view_vmp);
        CardView cardviewaircrack = findViewById(R.id.card_view_aircrack);
        CardView cardviewcowpatty = findViewById(R.id.card_view_cowpatty);
        CardView cardviewmdk3 = findViewById(R.id.card_view_mdk3);
        CardView cardviewmdk4 = findViewById(R.id.card_view_mdk4);
        CardView cardviewbully = findViewById(R.id.card_view_bully);
        CardView cardviewreaver = findViewById(R.id.card_view_reaver);
        CardView cardviewhostapd = findViewById(R.id.card_view_hostapd);
        CardView cardviewhostapd_wpe = findViewById(R.id.card_view_hostapd_wpe);
        CardView cardviewbluesnarfer = findViewById(R.id.card_view_bluesnarfer);
        CardView cardviewcrackle = findViewById(R.id.card_view_crackle);
        CardView cardviewwifiarp = findViewById(R.id.card_view_wifiarp);
        CardView cardviewwifidns = findViewById(R.id.card_view_wifidns);
        CardView cardviewwifiping = findViewById(R.id.card_view_wifiping);
        CardView cardviewwifitap = findViewById(R.id.card_view_wifitap);
        CardView cardvieweapmd5pass = findViewById(R.id.card_view_eapmd5pass);
        CardView cardviewpixiewps = findViewById(R.id.card_view_pixiewps);
        CardView cardviewwifite2 = findViewById(R.id.card_view_wifite2);

        cardviewvmp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dco_Wireless_Hacking.this,VmpActivity.class);
                startActivity(intent);
            }
        });

        cardviewaircrack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("aircrack-ng");

            }
        });

        /**
         *
         * Help me, i'm dying...
         *
         **/

        cardviewcowpatty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("cowpatty");

            }
        });

        cardviewmdk3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("mdk3");

            }
        });

        cardviewmdk4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("mdk4");

            }
        });

        cardviewbully.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("bully");

            }
        });

        cardviewreaver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("reaver");

            }
        });

        cardviewhostapd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("hostapd -h");

            }
        });

        cardviewhostapd_wpe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("hostapd-wpe -hh");

            }
        });

        cardviewbluesnarfer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("bluesnarfer");

            }
        });

        cardviewcrackle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("crackle");

            }
        });

        cardviewwifiarp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("wifiarp");

            }
        });

        cardviewwifidns.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("wifidns");

            }
        });

        cardviewwifiping.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("wifiping");

            }
        });

        cardviewwifitap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("wifitap");

            }
        });

        cardvieweapmd5pass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("eapmd5pass");

            }
        });

        cardviewpixiewps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("pixiewps");

            }
        });

        cardviewwifite2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("wifite");

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
