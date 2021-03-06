package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

import com.thecrackertechnology.dragonterminal.bridge.Bridge;

import me.ibrahimsn.particle.ParticleView;

public class Dco_stress_testing extends Activity {

    ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.dco_stress_testing);

        particleView = findViewById(R.id.particleView_dco_stress);

        CardView cardviewfuzzip6 = findViewById(R.id.card_view_fuzzip6);
        CardView cardviewdenial6 = findViewById(R.id.card_view_denial6);
        CardView cardviewflooddhcpc6 = findViewById(R.id.card_view_flooddhcpc6);
        CardView cardviewfloodadvertise6 = findViewById(R.id.card_view_floodadvertise6);
        CardView cardviewfloodmld6 = findViewById(R.id.card_view_floodmld6);
        CardView cardviewfloodmld26 = findViewById(R.id.card_view_floodmld26);
        CardView cardviewfloodmldrouter6 = findViewById(R.id.card_view_floodmldrouter6);
        CardView cardviewfloodredir6 = findViewById(R.id.card_view_floodredir6);
        CardView cardviewfloodrouter6 = findViewById(R.id.card_view_floodrouter6);
        CardView cardviewfloodrouter26 = findViewById(R.id.card_view_floodrouter26);
        CardView cardviewfloodrs6 = findViewById(R.id.card_view_floodrs6);
        CardView cardviewfloodsolicitate6 = findViewById(R.id.card_view_floodsolicitate6);
        CardView cardviewfloodunreach6 = findViewById(R.id.card_view_floodunreach6);
        CardView cardviewrsmurf6 = findViewById(R.id.card_view_rsmurf6);
        CardView cardviewdosnewip6 = findViewById(R.id.card_view_dosnewip6);
        CardView cardviewrandicmp6 = findViewById(R.id.card_view_randicmp6);
        CardView cardviewslowhttptest = findViewById(R.id.card_view_slowhttptest);
        CardView cardviewslowloris = findViewById(R.id.card_view_slowloris);
        CardView cardviewgoldeneye = findViewById(R.id.card_view_goldeneye);



        cardviewfuzzip6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fuzz_ip6");

            }
        });

        cardviewdenial6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("denial6");

            }
        });

        cardviewflooddhcpc6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_dhcpc6");

            }
        });

        cardviewfloodadvertise6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_advertise6");

            }
        });

        cardviewfloodmld6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_mld6");

            }
        });

        cardviewfloodmld26.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_mld26");

            }
        });

        cardviewfloodmldrouter6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_mldrouter6");

            }
        });

        cardviewfloodredir6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_redir6");

            }
        });

        cardviewfloodrouter6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_router6");

            }
        });

        cardviewfloodrouter26.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_router26");

            }
        });

        cardviewfloodrs6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_rs6");

            }
        });

        cardviewfloodsolicitate6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_solicitate6");

            }
        });

        cardviewfloodunreach6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("flood_unreach6");

            }
        });

        cardviewrsmurf6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("rsmurf6");

            }
        });

        cardviewdosnewip6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("dos-new-ip6");

            }
        });

        cardviewrandicmp6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("randicmp6");

            }
        });

        cardviewslowhttptest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("slowhttptest");

            }
        });

        cardviewslowloris.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("slowloris");

            }
        });

        cardviewgoldeneye.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("goldeneye");

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
