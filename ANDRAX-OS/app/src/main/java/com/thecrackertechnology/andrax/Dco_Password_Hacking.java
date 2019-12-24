package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

import com.thecrackertechnology.dragonterminal.bridge.Bridge;

import me.ibrahimsn.particle.ParticleView;

public class Dco_Password_Hacking extends Activity {

    ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);



        setContentView(R.layout.dco_password_hacking);

        particleView = findViewById(R.id.particleView_dco_password);

        CardView cardviewhydra = findViewById(R.id.card_view_hydra);
        CardView cardviewncrack = findViewById(R.id.card_view_ncrack);
        CardView cardviewjohn = findViewById(R.id.card_view_john);
        CardView cardviewhashboy = findViewById(R.id.card_view_hashboy);
        CardView cardviewcrunch = findViewById(R.id.card_view_crunch);
        CardView cardviewmassh = findViewById(R.id.card_view_massh);
        CardView cardviewacccheck = findViewById(R.id.card_view_acccheck);
        CardView cardviewsshauditor = findViewById(R.id.card_view_ssh_auditor);
        CardView cardviewbopscrk = findViewById(R.id.card_view_bopscrk);
        CardView cardviewpskcrack = findViewById(R.id.card_view_pskcrack);
        CardView cardviewcr3d0v3r = findViewById(R.id.card_view_cr3d0v3r);


        cardviewhydra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("hydra");

            }
        });

        cardviewncrack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("ncrack");

            }
        });

        cardviewjohn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("john");

            }
        });

        cardviewhashboy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("hashboy");

            }
        });

        /**
         *
         * Help me, i'm dying...
         *
         **/

        cardviewcrunch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("crunch");

            }
        });

        cardviewmassh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("massh-enum --help");

            }
        });

        cardviewacccheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("acccheck");

            }
        });


        cardviewsshauditor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("ssh-auditor");

            }
        });

        cardviewbopscrk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("bopscrk");

            }
        });

        cardviewpskcrack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("psk-crack");

            }
        });

        cardviewcr3d0v3r.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("cr3d0v3r -h");

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
