package com.thecrackertechnology.andrax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;

import com.thecrackertechnology.dragonterminal.bridge.Bridge;

import me.ibrahimsn.particle.ParticleView;

public class Dco_network_hacking extends Activity {

    ParticleView particleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.dco_network_hacking);

        particleView = findViewById(R.id.particleView_dco_network);

        CardView cardviewarpspoof = findViewById(R.id.card_view_arpspoof);
        CardView cardviewnetmask = findViewById(R.id.card_view_netmask);
        CardView cardviewresponder = findViewById(R.id.card_view_responder);
        CardView cardviewbettercap = findViewById(R.id.card_view_bettercap);
        CardView cardviewmitmproxy = findViewById(R.id.card_view_mitmproxy);
        CardView cardviewsocat = findViewById(R.id.card_view_socat);
        CardView cardviewgodoh = findViewById(R.id.card_view_godoh);
        CardView cardviewdns2tcp = findViewById(R.id.card_view_dns2tcp);
        CardView cardviewfragroute = findViewById(R.id.card_view_fragroute);
        CardView cardviewudp2raw = findViewById(R.id.card_view_udp2raw);
        CardView cardviewdns2proxy = findViewById(R.id.card_view_dns2proxy);
        CardView cardviewtshark = findViewById(R.id.card_view_tshark);
        CardView cardviewp0f = findViewById(R.id.card_view_p0f);
        CardView cardviewkillrouter6 = findViewById(R.id.card_view_killrouter6);
        CardView cardviewdetectsniffer6 = findViewById(R.id.card_view_detctsniffer6);
        CardView cardviewfakeadvertise6 = findViewById(R.id.card_view_fakeadvertise6);
        CardView cardviewfakedhcps6 = findViewById(R.id.card_view_fakedhcps6);
        CardView cardviewfakedns6d = findViewById(R.id.card_view_fakedns6d);
        CardView cardviewfakednsupdate6 = findViewById(R.id.card_view_fakednsupdate6);
        CardView cardviewfakemld26 = findViewById(R.id.card_view_fakemld26);
        CardView cardviewfakemld6 = findViewById(R.id.card_view_fakemld6);
        CardView cardviewfakemldrouter6 = findViewById(R.id.card_view_fakemldrouter6);
        CardView cardviewfakerouter26 = findViewById(R.id.card_view_fakerouter26);
        CardView cardviewfakerouter6 = findViewById(R.id.card_view_fakerouter6);
        CardView cardviewfakesolicitate6 = findViewById(R.id.card_view_fakesolicitate6);
        CardView cardviewimplementation6 = findViewById(R.id.card_view_implementation6);
        CardView cardviewparasite6 = findViewById(R.id.card_view_parasite6);
        CardView cardviewredir6 = findViewById(R.id.card_view_redir6);
        CardView cardviewsmurf6 = findViewById(R.id.card_view_smurf6);
        CardView cardviewdelorean = findViewById(R.id.card_view_delorean);
        CardView cardviewsmbmap = findViewById(R.id.card_view_smbmap);
        CardView cardviewfiked = findViewById(R.id.card_view_fiked);
        CardView cardviewdhcping = findViewById(R.id.card_view_dhcping);

        cardviewnetmask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("netmask --help");

            }
        });

        cardviewarpspoof.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("arpspoof");

            }
        });

        cardviewresponder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("responder");

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

        cardviewsocat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("socat -h");

            }
        });

        cardviewgodoh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("godoh");

            }
        });


        cardviewdns2tcp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("dns2tcpc");

            }
        });



        cardviewfragroute.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fragroute");

            }
        });

        cardviewudp2raw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("udp2raw");

            }
        });

        cardviewdns2proxy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("dns2proxy -h");

            }
        });



        /**
         *
         * Help me, i'm dying...
         *
         **/

        cardviewtshark.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("tshark --help");

            }
        });

        cardviewp0f.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("p0f");

            }
        });

        cardviewkillrouter6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("kill_router6");

            }
        });

        cardviewdetectsniffer6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("detect_sniffer6");

            }
        });

        cardviewfakeadvertise6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_advertise6");

            }
        });

        cardviewfakedhcps6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_dhcps6");

            }
        });

        cardviewfakedns6d.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_dns6d");

            }
        });

        cardviewfakednsupdate6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_dnsupdate6");

            }
        });

        cardviewfakemld26.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_mld26");

            }
        });

        cardviewfakemld6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_mld6");

            }
        });

        cardviewfakemldrouter6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_mldrouter6");

            }
        });

        cardviewfakerouter26.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_router26");

            }
        });

        cardviewfakerouter6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_router6");

            }
        });

        cardviewfakesolicitate6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fake_solicitate6");

            }
        });

        cardviewimplementation6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("implementation6");

            }
        });

        cardviewparasite6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("parasite6");

            }
        });

        cardviewredir6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("redir6");

            }
        });

        cardviewsmurf6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("smurf6");

            }
        });

        cardviewdelorean.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("delorean -h");

            }
        });

        cardviewsmbmap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("smbmap");

            }
        });

        cardviewfiked.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("fiked");

            }
        });

        cardviewdhcping.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                run_hack_cmd("dhcping");

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
