package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

import com.thecrackertechnology.dragonterminal.bridge.Bridge;

import me.ibrahimsn.particle.ParticleView;

public class Dco_ics_scada_iot extends Activity {

    ParticleView particleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.dco_ics_scada_iot);

        particleView = findViewById(R.id.particleView_dco_ics);

        CardView cardviewplcscan = findViewById(R.id.card_view_plcscan);
        CardView cardviews7scan = findViewById(R.id.card_view_s7scan);
        CardView cardviewmodscan = findViewById(R.id.card_view_modscan);
        CardView cardviewmbtget = findViewById(R.id.card_view_mbtget);
        CardView cardviewisaf = findViewById(R.id.card_view_isaf);
        CardView cardviewisf = findViewById(R.id.card_view_isf);
        CardView cardviewexpliot = findViewById(R.id.card_view_expliot);
        CardView cardviewsixnettools = findViewById(R.id.card_view_sixnettools);


        cardviewplcscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("plcscan");

            }
        });

        cardviews7scan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("s7scan");

            }
        });

        cardviewmodscan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("modscan");

            }
        });

        cardviewmbtget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("mbtget -h");

            }
        });

        cardviewisaf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("isaf");

            }
        });

        cardviewisf.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("isf");

            }
        });

        cardviewexpliot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("efconsole");

            }
        });

        cardviewsixnettools.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("SIXNET-tools");

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
