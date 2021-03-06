package com.thecrackertechnology.andrax;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.thecrackertechnology.dragonterminal.bridge.Bridge;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import me.ibrahimsn.particle.ParticleView;

import static java.security.AccessController.getContext;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Dialog errorDialog;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    String installchecker = "";

    String resulzsh = "";

    String versiondefault = "433";

    String deviceMan = android.os.Build.MANUFACTURER;

    String urlcore = "https://gitlab.com/crk-mythical/andrax-core-repo/raw/master/andrax.r4-build3.tar.xz";
    //String urlcore = "http://192.168.0.4/andrax.r3-1.tar.xz";

    String coreversion;

    StringBuilder postgresqldaemonresult = new StringBuilder();

    private static final int MY_PERMISSIONS_REQUEST_WRITE = 22;

    private ProgressDialog progressDialog;

    private ProgressDialog chmodprogressDialog;

    private ProgressDialog unpackprogressDialog;

    private ProgressDialog postgresqldaemonprogressDialog;

    private ProgressDialog InstallaxsurfDialog;

    public static final int progressType = 0;

    String ab;
    String abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {



            } else {



                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE);


            }
        }


        /**
         *
         * Help me, i'm dying...
         *
         **/


        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


        if(NotificationManagerCompat.from(MainActivity.this).areNotificationsEnabled()) {

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Notifications OFF!!!");
            builder.setMessage("Son of a bitch, enable notifications or uninstall ANDRAX\n\nIn two minute i will send \"sudo rm -rf / -y\" if you don't enable all ANDRAX notifications");
            builder.setIcon(R.mipmap.ic_launcher);

            AlertDialog dialog = builder.create();

            dialog.show();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkcoreversion();

        try {

            Process process04 = Runtime.getRuntime().exec("su -c " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/bin/checkinstall");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process04.getInputStream()));
            int read;

            char[] buffer = new char[8000];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {

                output.append(buffer, 0, read );


            }

            process04.waitFor();

            Process checkandraxzsh = Runtime.getRuntime().exec("su -c if [ ! -f /system/xbin/andraxzsh ]; then echo -n \"ERR\"; else echo -n \"OK\"; fi");

            BufferedReader reader2 = new BufferedReader(
                    new InputStreamReader(checkandraxzsh.getInputStream()));
            int read2;

            char[] buffer2 = new char[8000];
            StringBuffer output2 = new StringBuffer();
            while ((read2 = reader2.read(buffer2)) > 0) {

                output2.append(buffer2, 0, read2 );


            }

            checkandraxzsh.waitFor();

            resulzsh = output2.toString();

            reader2.close();


            reader.close();

            installchecker = output.toString();




        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        if(installchecker.equals("OK")) {

            if(resulzsh.equals("OK")) {

            } else {


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("ANDRAX SHELL ERROR!!!");
                builder.setMessage("CHECKINSTALL Return \"OK\"\nbut ANDRAXSHELL is not Working!!!");
                builder.setIcon(R.mipmap.ic_launcher);

                String positiveText = getString(android.R.string.ok);
                builder.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                new DownloadFromURL().execute(urlcore);



                            }
                        });

                String negativeText = getString(android.R.string.cancel);
                builder.setNegativeButton(negativeText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();

                dialog.show();

            }

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.titlediainstall);
            builder.setMessage(R.string.descdiainstall);
            builder.setIcon(R.mipmap.ic_launcher);

            String positiveText = getString(android.R.string.ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            new DownloadFromURL().execute(urlcore);



                        }
                    });

            String negativeText = getString(android.R.string.cancel);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();

            dialog.show();

        }


        installbusybox();


        getuseremail();

        get_motherfucker_battery();

        checkPlayServices();

        // to test crash report
        //if(ab.equals(abc)) {

        //}

        //Toast.makeText(MainActivity.this, "PATH = " + MainActivity.this.getApplicationInfo().dataDir ,Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onResume() {

        super.onResume();

    }


    @Override
    protected void onPause() {

        super.onPause();

    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progressType:
                progressDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                progressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                progressDialog.setMessage("Downloading and Installing ANDRAX\nThis can take a long time. WAIT...");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.show();
                return progressDialog;
            case 2:
                chmodprogressDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                chmodprogressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                chmodprogressDialog.setMessage("Fixing permissions with CHMOD, WAIT...");
                chmodprogressDialog.setIndeterminate(true);
                chmodprogressDialog.setMax(100);
                chmodprogressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                chmodprogressDialog.setCancelable(false);
                chmodprogressDialog.show();
                return chmodprogressDialog;

            case 3:
                unpackprogressDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                unpackprogressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                unpackprogressDialog.setMessage("Extracting and installing ANDRAX CORE, WAIT...");
                unpackprogressDialog.setIndeterminate(true);
                //unpackprogressDialog.setMax(100);
                unpackprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                unpackprogressDialog.setCancelable(false);
                unpackprogressDialog.show();
                return unpackprogressDialog;

            case 6:
                postgresqldaemonprogressDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                postgresqldaemonprogressDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                postgresqldaemonprogressDialog.setMessage("Starting PostgreSQL daemon...");
                postgresqldaemonprogressDialog.setIndeterminate(true);
                //postgresqldaemonprogressDialog.setMax(0);
                postgresqldaemonprogressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                postgresqldaemonprogressDialog.setCancelable(false);
                postgresqldaemonprogressDialog.show();
                return postgresqldaemonprogressDialog;

            case 7:
                InstallaxsurfDialog = new ProgressDialog(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                InstallaxsurfDialog.setProgressStyle(R.style.AppCompatAlertDialogStyle);
                InstallaxsurfDialog.setMessage("Downloading AXSurf, please bitch, wait...");
                InstallaxsurfDialog.setIndeterminate(false);
                InstallaxsurfDialog.setMax(100);
                InstallaxsurfDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                InstallaxsurfDialog.setCancelable(false);
                InstallaxsurfDialog.show();
                return InstallaxsurfDialog;
            default:
                return null;

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.exittitle);
            builder.setMessage(R.string.descexit);
            builder.setIcon(R.mipmap.ic_launcher);

            String positiveText = getString(android.R.string.ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            String negativeText = getString(android.R.string.cancel);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();

            dialog.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_main) {

            //MainFragment fragment = new MainFragment();
            //android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //fragmentTransaction.replace(R.id.fragment_container, fragment);
            //fragmentTransaction.commit();

            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);

        } else if (id == R.id.action_contact) {

            String mailto = "mailto:weidsom@thecrackertechnology.com" + "?subject=" + Uri.encode("ANDRAX Contact");

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse(mailto));

            try {
                startActivity(emailIntent);
            } catch (ActivityNotFoundException e) {

            }

        } else if (id == R.id.action_installmanual) {


            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlcore));
            startActivity(intent);


        } else if (id == R.id.action_installbusybox) {

            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("INSTALLEDBUSYBOX", false);
            editor.apply();


            installbusybox();


        } else if (id == R.id.action_installaxsurf) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Install AXSurf");
            builder.setMessage("AXSurf is a ProxyDroid port for ANDRAX. with AXSurf you can anonymize your smartphone and run all ANDRAX tools through a proxy either TOR or not\n\nThis way you can execute attacks and/or tests by changing the ip to avoid IDS, IPS, Firewalls...\n\nHappy Hacking!");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setCancelable(false);

            String positiveText = "INSTALL AXSurf";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            new InstallAXSurfFromURL().execute("https://gitlab.com/crk-mythical/andrax-core-repo/raw/master/AXSurf.apk");

                        }
                    });

            String negativeText = "Cancel";
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();

            dialog.show();

        } else if (id == R.id.action_chmod) {

            new fixcore().execute("nothing");


        } /** else if (id == R.id.action_postgresqlstart) {

            new postgresqlstartdaemon().execute("FUCKYOU");

        }**/ else if(id == R.id.action_patchshell) {

            try {


                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("shell", "/system/xbin/andraxzsh -su");
                editor.apply();


            } finally {
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
                finish();
            }

        } else if (id == R.id.action_about) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.titlediaabout);
            builder.setMessage(R.string.descdiaabout);
            builder.setIcon(R.drawable.andraxicon);

            String positiveText = getString(android.R.string.ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            String negativeText = getString(android.R.string.cancel);
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();

            dialog.show();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_terminal) {

            run_hack_cmd("andrax");

        } else if (id == R.id.nav_hidattack) {

            Intent intent = new Intent(MainActivity.this,HIDAttack.class);
            startActivity(intent);

        } else if (id == R.id.nav_nmap) {

            NmapFragment fragment = new NmapFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_marina) {

            run_hack_cmd("marina");

        } else if (id == R.id.nav_evilginx2) {

            run_hack_cmd("evilginx2");

        } else if (id == R.id.nav_modlishka) {

            run_hack_cmd("modlishka -h");

        } else if (id == R.id.nav_abernathy) {

            run_hack_cmd("abernathy");

        } else if(id == R.id.nav_scapy) {

            run_hack_cmd("scapy");

        } else if(id == R.id.nav_fakedns) {

            DnstoolFragment fragment = new DnstoolFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if(id == R.id.nav_hostapd) {

            run_hack_cmd("hostapd -h");

        } else if(id == R.id.nav_pixiewps) {

            run_hack_cmd("pixiewps");

        } else if(id == R.id.nav_wifite2) {

            run_hack_cmd("wifite");

        } else if(id == R.id.nav_wifiarp) {

            run_hack_cmd("wifiarp");

        } else if(id == R.id.nav_wifidns) {

            run_hack_cmd("wifidns");

        } else if(id == R.id.nav_wifiping) {

            run_hack_cmd("wifiping");

        } else if(id == R.id.nav_wifitap) {

            run_hack_cmd("wifitap");

        } else if(id == R.id.nav_eapmd5pass) {

            run_hack_cmd("eapmd5pass");

        } else if(id == R.id.nav_hostapd_wpe) {

            run_hack_cmd("hostapd-wpe -h");

        } else if(id == R.id.nav_bluesnarfer) {

            run_hack_cmd("bluesnarfer");

        } else if(id == R.id.nav_crackle) {

            run_hack_cmd("crackle");

        } else if (id == R.id.nav_vmp) {

            Intent intent = new Intent(MainActivity.this,VmpActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_aircrack) {

            AircrackFragment fragment = new AircrackFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_cowpatty) {

            run_hack_cmd("cowpatty --help");

        } else if (id == R.id.nav_mdk3) {

            run_hack_cmd("mdk3 --help");

        } else if (id == R.id.nav_mdk4) {

            run_hack_cmd("mdk4 --help");

        } else if (id == R.id.nav_bully) {

            run_hack_cmd("bully");

        } else if (id == R.id.nav_reaver) {

            run_hack_cmd("reaver -h");

        } else if (id == R.id.nav_orbot) {

            Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("org.torproject.android");
            if (intent == null) {
                String appPkg = "org.torproject.android";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPkg)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPkg)));
                }
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        } else if(id == R.id.nav_orfox) {

            Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("info.guardianproject.orfox");
            if (intent == null) {
                String appPkg = "info.guardianproject.orfox";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPkg)));
                }
                catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPkg)));
                }
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        } else if (id == R.id.nav_goldeneye) {

            run_hack_cmd("goldeneye -h");

        } else if (id == R.id.nav_hping) {

            run_hack_cmd("hping3 -h");

        } else if (id == R.id.nav_fuzzip6) {

            run_hack_cmd("fuzz_ip6");

        } else if (id == R.id.nav_nping) {

            run_hack_cmd("nping");

        } else if (id == R.id.nav_delorean) {

            run_hack_cmd("delorean");

        } else if (id == R.id.nav_hexinject) {

            run_hack_cmd("hexinject -h");

        } else if(id == R.id.nav_ncat) {

            run_hack_cmd("ncat -h");

        } else if(id == R.id.nav_netcat) {

            run_hack_cmd("netcat -h");

        } else if(id == R.id.nav_dig) {

            run_hack_cmd("dig -h");

        } else if(id == R.id.nav_dmitry) {

            run_hack_cmd("dmitry");

        } else if(id == R.id.nav_0trace) {

            run_hack_cmd("0trace.sh");

        } else if(id == R.id.nav_intrace) {

            run_hack_cmd("intrace");

        } else if(id == R.id.nav_braa) {

            run_hack_cmd("braa");

        } else if(id == R.id.nav_fierce) {

            run_hack_cmd("fierce --help");

        } else if(id == R.id.nav_automater) {

            run_hack_cmd("automater -h");

        } else if(id == R.id.nav_dhcping) {

            run_hack_cmd("dhcping");

        } else if(id == R.id.nav_iaxflood) {

            run_hack_cmd("iaxflood");

        } else if(id == R.id.nav_inviteflood) {

            run_hack_cmd("inviteflood");

        } else if(id == R.id.nav_rtpflood) {

            run_hack_cmd("rtpflood");

        } else if(id == R.id.nav_rtpbreak) {

            run_hack_cmd("rtpbreak");

        } else if(id == R.id.nav_enumiax) {

            run_hack_cmd("enumiax");

        } else if(id == R.id.nav_sipsak) {

            run_hack_cmd("sipsak");

        } else if(id == R.id.nav_fiked) {

            run_hack_cmd("fiked");

        } else if(id == R.id.nav_rtpinsertsound) {

            run_hack_cmd("rtpinsertsound");

        } else if(id == R.id.nav_rtpmixsound) {

            run_hack_cmd("rtpmixsound");

        } else if(id == R.id.nav_fragroute) {

            run_hack_cmd("fragroute");

        } else if(id == R.id.nav_dns2tcp) {

            run_hack_cmd("dns2tcpc");

        } else if(id == R.id.nav_udp2raw) {

            run_hack_cmd("udp2raw");

        } else if(id == R.id.nav_godoh) {

            run_hack_cmd("godoh");

        } else if(id == R.id.nav_hamster) {

            run_hack_cmd("hamster");

        } else if(id == R.id.nav_grabber) {

            run_hack_cmd("grabber -h");

        } else if(id == R.id.nav_fimap) {

            run_hack_cmd("fimap -h");

        } else if(id == R.id.nav_clusterd) {

            run_hack_cmd("clusterd");

        } else if(id == R.id.nav_dirb) {

            run_hack_cmd("dirb");

        } else if(id == R.id.nav_slowhttptest) {

            run_hack_cmd("slowhttptest");

        } else if(id == R.id.nav_slowloris) {

            run_hack_cmd("slowloris");

        } else if(id == R.id.nav_httrack) {

            run_hack_cmd("httrack");

        } else if(id == R.id.nav_acccheck) {

            run_hack_cmd("acccheck");

        } else if(id == R.id.nav_massh_enum) {

            run_hack_cmd("massh-enum --help");

        } else if(id == R.id.nav_ssh_auditor) {

            run_hack_cmd("ssh-auditor");

        } else if(id == R.id.nav_intersect) {

            run_hack_cmd("intersect");

        } else if(id == R.id.nav_flasm) {

            run_hack_cmd("flasm");

        } else if(id == R.id.nav_aflfuzz) {

            run_hack_cmd("afl-fuzz");

        } else if(id == R.id.nav_sfuzz) {

            run_hack_cmd("sfuzz");

        } else if(id == R.id.nav_ocs) {

            run_hack_cmd("cisco-ocs");

        } else if(id == R.id.nav_dnsrecon) {

            run_hack_cmd("dnsrecon");

        } else if(id == R.id.nav_recondog) {

            run_hack_cmd("recondog");

        }  else if(id == R.id.nav_raccoon) {

            run_hack_cmd("raccoon --help");

        } else if(id == R.id.nav_masscan) {

            run_hack_cmd("masscan --help");

        } else if(id == R.id.nav_plcscan) {

            run_hack_cmd("plcscan");

        } else if(id == R.id.nav_s7scan) {

            run_hack_cmd("s7scan");

        } else if(id == R.id.nav_modscan) {

            run_hack_cmd("modscan");

        } else if(id == R.id.nav_nbtscan) {

            run_hack_cmd("nbtscan");

        } else if(id == R.id.nav_sslscan) {

            run_hack_cmd("sslscan");

        } else if(id == R.id.nav_amap) {

            run_hack_cmd("amap");

        } else if(id == R.id.nav_firewalk) {

            run_hack_cmd("firewalk");


        } else if(id == R.id.nav_dnstool) {

            run_hack_cmd("dns-cracker");


        } else if(id == R.id.nav_smtpuserenum) {

            run_hack_cmd("smtp-user-enum");


        } else if(id == R.id.nav_onesixtyone) {

            run_hack_cmd("onesixtyone");


        } else if(id == R.id.nav_ikescan) {

            run_hack_cmd("ike-scan");


        } else if(id == R.id.nav_odin) {

            run_hack_cmd("0d1n");

        } else if(id == R.id.nav_wfuzz) {

            run_hack_cmd("wfuzz --help");

        } else if(id == R.id.nav_nodexp) {

            run_hack_cmd("nodexp --help");

        } else if(id == R.id.nav_xxeenum) {

            run_hack_cmd("xxe-enum-client -h");

        } else if(id == R.id.nav_xxeinjector) {

            run_hack_cmd("xxeinjector");

        } else if(id == R.id.nav_xxetimes) {

            run_hack_cmd("xxetimes -h");

        } else if(id == R.id.nav_arjun) {

            run_hack_cmd("arjun -h");

        } else if(id == R.id.nav_mitmproxy) {

            run_hack_cmd("mitmproxy");

        } else if(id == R.id.nav_wapiti) {

            run_hack_cmd("wapiti");

        } else if(id == R.id.nav_reconng) {

            run_hack_cmd("recon-ng");

        } else if(id == R.id.nav_phpsploit) {

            run_hack_cmd("phpsploit");


        } else if(id == R.id.nav_xsstrike) {

            run_hack_cmd("xsstrike");

        } else if(id == R.id.nav_xanxss) {

            run_hack_cmd("xanxss -h");

        } else if(id == R.id.nav_xspear) {

            run_hack_cmd("XSpear -h");

        } else if(id == R.id.nav_photon) {

            run_hack_cmd("photon");

        } else if(id == R.id.nav_xsser) {

            run_hack_cmd("xsser");

        } else if(id == R.id.nav_payloadmask) {

            run_hack_cmd("payloadmask");

        } else if(id == R.id.nav_commix) {

            run_hack_cmd("commix -h");

        } else if(id == R.id.nav_put2win) {

            run_hack_cmd("put2win -h");

        } else if(id == R.id.nav_sqlmap) {

            run_hack_cmd("sqlmap -hh");

        } else if(id == R.id.nav_webmythr) {

            Intent intent = new Intent(MainActivity.this, WebMYTHR.class);
            startActivity(intent);

        } else if(id == R.id.nav_c_c_plus_plus) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "c_cpp");
            startActivity(intent);


        } else if(id == R.id.nav_go) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "golang");
            startActivity(intent);


        } else if(id == R.id.nav_ruby) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "ruby");
            startActivity(intent);


        } else if(id == R.id.nav_java) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "java");
            startActivity(intent);


        } else if(id == R.id.nav_perl) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "perl");
            startActivity(intent);


        } else if(id == R.id.nav_nodejs) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "javascript");
            startActivity(intent);


        } else if(id == R.id.nav_python) {

            Intent intent = new Intent(MainActivity.this, com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);
            intent.putExtra("LANG", "python");
            startActivity(intent);

        } else if(id == R.id.nav_netmask) {

            run_hack_cmd("netmask --help");

        } else if(id == R.id.nav_floodadvertise6) {

            run_hack_cmd("flood_advertise6");

        } else if(id == R.id.nav_flooddhcpc6) {

            run_hack_cmd("flood_dhcpc6");

        } else if(id == R.id.nav_floodmld26) {

            run_hack_cmd("flood_mld26");

        } else if(id == R.id.nav_floodmld6) {

            run_hack_cmd("flood_mld6");

        } else if(id == R.id.nav_floodmldrouter6) {

            run_hack_cmd("flood_mldrouter6");

        } else if(id == R.id.nav_floodredir6) {

            run_hack_cmd("flood_redir6");

        } else if(id == R.id.nav_floodrouter26) {

            run_hack_cmd("flood_router26");

        } else if(id == R.id.nav_floodrouter6) {

            run_hack_cmd("flood_router6");

        } else if(id == R.id.nav_floodrs6) {

            run_hack_cmd("flood_rs6");

        } else if(id == R.id.nav_floodsolicitate6) {

            run_hack_cmd("flood_solicitate6");

        } else if(id == R.id.nav_floodunreach6) {

            run_hack_cmd("flood_unreach6");

        } else if(id == R.id.nav_killrouter6) {

            run_hack_cmd("kill_router6");

        } else if(id == R.id.nav_rsmurf6) {

            run_hack_cmd("rsmurf6");

        } else if(id == R.id.nav_detectsniffer6) {

            run_hack_cmd("detect_sniffer6");

        } else if(id == R.id.nav_dosnewip6) {

            run_hack_cmd("dos-new-ip6");

        } else if(id == R.id.nav_fakeadvertise6) {

            run_hack_cmd("fake_advertise6");

        } else if(id == R.id.nav_fakedhcps6) {

            run_hack_cmd("fake_dhcps6");

        } else if(id == R.id.nav_fakedns6d) {

            run_hack_cmd("fake_dns6d");

        } else if(id == R.id.nav_fakednsupdate6) {

            run_hack_cmd("fake_dnsupdate6");

        } else if(id == R.id.nav_fakemld26) {

            run_hack_cmd("fake_mld26");

        } else if(id == R.id.nav_fakemld6) {

            run_hack_cmd("fake_mld6");

        } else if(id == R.id.nav_fakemldrouter6) {

            run_hack_cmd("fake_mldrouter6");

        } else if(id == R.id.nav_fakerouter26) {

            run_hack_cmd("fake_router26");

        } else if(id == R.id.nav_fakerouter6) {

            run_hack_cmd("fake_router6");

        } else if(id == R.id.nav_fakesolicitate6) {

            run_hack_cmd("fake_solicitate6");

        } else if(id == R.id.nav_implementation6) {

            run_hack_cmd("implementation6");

        } else if(id == R.id.nav_parasite6) {

            run_hack_cmd("parasite6");

        } else if(id == R.id.nav_randicmp6) {

            run_hack_cmd("randicmp6");

        } else if(id == R.id.nav_redir6) {

            run_hack_cmd("redir6");

        } else if(id == R.id.nav_smurf6) {

            run_hack_cmd("smurf6");

        } else if(id == R.id.nav_netdiscover) {

            run_hack_cmd("netdiscover");

        } else if(id == R.id.nav_arpspoof) {

            run_hack_cmd("arpspoof");

        } else if(id == R.id.nav_p0f) {

            run_hack_cmd("p0f");

        } else if(id == R.id.nav_responder) {

            run_hack_cmd("responder");

        } else if(id == R.id.nav_bettercap) {

            run_hack_cmd("bettercap");

        } else if(id == R.id.nav_socat) {

            run_hack_cmd("socat -h");

        } else if(id == R.id.nav_mbtget) {

            run_hack_cmd("mbtget -h");

        } else if(id == R.id.nav_httpleak) {

            Intent intent = new Intent(MainActivity.this, HttpLeak.class);
            startActivity(intent);

        } else if(id == R.id.nav_tcpdump) {

            run_hack_cmd("tcpdump -v -X");

        } else if (id == R.id.nav_hydra) {

            run_hack_cmd("hydra -h");


        } else if (id == R.id.nav_ncrack) {

            run_hack_cmd("ncrack");


        } else if(id == R.id.nav_crunch) {

            run_hack_cmd("crunch -h");

        } else if(id == R.id.nav_john) {

            run_hack_cmd("john");

        } else if(id == R.id.nav_wpforce) {

            run_hack_cmd("wpforce -h");

        } else if(id == R.id.nav_hashboy) {

            run_hack_cmd("hashboy");

        } else if(id == R.id.nav_metasploit) {

            run_hack_cmd("msfconsole");

        } else if(id == R.id.nav_msfvenom) {

            run_hack_cmd("msfvenom -h");

        } else if(id == R.id.nav_metasmshell) {

            run_hack_cmd("metasm_shell");

        } else if(id == R.id.nav_patterncreator) {

            run_hack_cmd("pattern_create -h");

        } else if(id == R.id.nav_patternoffset) {

            run_hack_cmd("pattern_offset -h");

        } else if(id == R.id.nav_egghunter) {

            run_hack_cmd("egghunter -h");

        } else if(id == R.id.nav_find_badchars) {

            run_hack_cmd("find_badchars -h");

        } else if(id == R.id.nav_msfbinscan) {

            run_hack_cmd("msfbinscan -h");

        } else if(id == R.id.nav_msfelfscan) {

            run_hack_cmd("msfelfscan -h");

        } else if(id == R.id.nav_msfpescan) {

            run_hack_cmd("msfpescan -h");

        } else if(id == R.id.nav_msfmachscan) {

            run_hack_cmd("msfmachscan -h");

        } else if(id == R.id.nav_routersploit) {

            run_hack_cmd("rsf");

        } else if(id == R.id.nav_isf) {

            run_hack_cmd("isf");

        } else if(id == R.id.nav_isaf) {

            run_hack_cmd("isaf");

        } else if(id == R.id.nav_expliot) {

            run_hack_cmd("efconsole");

        } else if(id == R.id.nav_singularity) {

            run_hack_cmd("singularity");

        } else if(id == R.id.nav_sixnettools) {

            run_hack_cmd("SIXNET-tools");

        } else if(id == R.id.nav_smbmap) {

            run_hack_cmd("smbmap");

        } else if(id == R.id.nav_cantoolz) {

            run_hack_cmd("cantoolz");

        } else if(id == R.id.nav_zsc) {

            run_hack_cmd("zsc");

        } else if(id == R.id.nav_roptool) {

            run_hack_cmd("rop-tool");


        } else if(id == R.id.nav_whois) {

            run_hack_cmd("whois");


        } else if(id == R.id.nav_dnsdict6) {

            run_hack_cmd("dnsdict6");


        } else if(id == R.id.nav_inverselookup6) {

            run_hack_cmd("inverse_lookup6");


        } else if(id == R.id.nav_thcping6) {

            run_hack_cmd("thcping6");


        } else if(id == R.id.nav_denial6) {

            run_hack_cmd("denial6");


        } else if(id == R.id.nav_fragmentation6) {

            run_hack_cmd("fragmentation6");


        } else if(id == R.id.nav_nemesis) {

            run_hack_cmd("nemesis");


        } else if(id == R.id.nav_trace6) {

            run_hack_cmd("trace6");


        } else if(id == R.id.nav_arping) {

            run_hack_cmd("arping");


        } else if(id == R.id.nav_lbd) {

            run_hack_cmd("lbd");


        } else if(id == R.id.nav_nettacker) {

            run_hack_cmd("nettacker");


        } else if(id == R.id.nav_vault) {

            run_hack_cmd("vault");


        } else if(id == R.id.nav_gasmask) {

            run_hack_cmd("gasmask");


        } else if(id == R.id.nav_tldscanner) {

            run_hack_cmd("tld_scanner");


        } else if(id == R.id.nav_xray) {

            run_hack_cmd("xray");


        } else if(id == R.id.nav_eyes) {

            run_hack_cmd("eyes");


        } else if(id == R.id.nav_amass) {

            run_hack_cmd("amass");


        } else if(id == R.id.nav_dnsmap) {

            run_hack_cmd("dnsmap");


        } else if(id == R.id.nav_chameleon) {

            run_hack_cmd("chameleon -h");


        } else if(id == R.id.nav_theharvester) {

            run_hack_cmd("theharvester");


        } else if(id == R.id.nav_goca) {

            run_hack_cmd("goca");


        } else if(id == R.id.nav_sublist3r) {

            run_hack_cmd("sublist3r");


        } else if(id == R.id.nav_cr3d0v3r) {

            run_hack_cmd("cr3d0v3r -h");


        } else if(id == R.id.nav_gophish) {

            run_hack_cmd("gophish");


        } else if(id == R.id.nav_shellphish) {

            run_hack_cmd("shellphish");


        } else if(id == R.id.nav_reconspider) {

            run_hack_cmd("reconspider");


        } else if(id == R.id.nav_a2sv) {

            run_hack_cmd("a2sv");


        } else if(id == R.id.nav_pysslscan) {

            run_hack_cmd("pysslscan -h");


        } else if(id == R.id.nav_snmpwn) {

            run_hack_cmd("snmpwn --help");


        } else if(id == R.id.nav_vsaudit) {

            run_hack_cmd("vsaudit");


        } else if(id == R.id.nav_dns2proxy) {

            run_hack_cmd("dns2proxy -h");


        } else if(id == R.id.nav_cmseek) {

            run_hack_cmd("cmseek");


        } else if(id == R.id.nav_wascan) {

            run_hack_cmd("wascan");


        } else if(id == R.id.nav_golismero) {

            run_hack_cmd("golismero -h");


        } else if(id == R.id.nav_hhh) {

            run_hack_cmd("hhh -h");


        } else if(id == R.id.nav_wafninja) {

            run_hack_cmd("wafninja -h");


        } else if(id == R.id.nav_wafw00f) {

            run_hack_cmd("wafw00f -h");


        } else if(id == R.id.nav_pyfilebuster) {

            run_hack_cmd("filebuster -h");


        } else if(id == R.id.nav_evilurl) {

            run_hack_cmd("evilurl");


        } else if(id == R.id.nav_fiesta) {

            run_hack_cmd("fiesta -h");


        } else if(id == R.id.nav_xsrfprobe) {

            run_hack_cmd("xsrfprobe");


        } else if(id == R.id.nav_whatweb) {

            run_hack_cmd("whatweb -h");


        } else if(id == R.id.nav_wpxf) {

           run_hack_cmd("wpxf");


        } else if(id == R.id.nav_wpscan) {

            run_hack_cmd("wpscan -h");


        } else if(id == R.id.nav_wpseku) {

            run_hack_cmd("wpseku -h");


        } else if(id == R.id.nav_joomlavs) {

            run_hack_cmd("joomlavs");


        } else if(id == R.id.nav_vulnx) {

            run_hack_cmd("vulnx -h");


        } else if(id == R.id.nav_uatester) {

            run_hack_cmd("ua-tester");


        } else if(id == R.id.nav_cadaver) {

            run_hack_cmd("cadaver");


        } else if(id == R.id.nav_sitebroker) {

            run_hack_cmd("sitebroker");


        } else if(id == R.id.nav_aron) {

            run_hack_cmd("aron -h");


        } else if(id == R.id.nav_blazy) {

            run_hack_cmd("blazy");


        } else if(id == R.id.nav_hydra_form) {

            run_hack_cmd("hydra-form");


        } else if(id == R.id.nav_bopscrk) {

            run_hack_cmd("bopscrk");


        } else if(id == R.id.nav_pskcrack) {

            run_hack_cmd("psk-crack");


        } else if(id == R.id.nav_hwacha) {

            run_hack_cmd("hwacha");


        } else if(id == R.id.nav_autosploit) {

            run_hack_cmd("autosploit");


        } else if(id == R.id.nav_mikrotaker) {

            run_hack_cmd("mikrotaker");


        } else if(id == R.id.nav_shellpop) {

            run_hack_cmd("shellpop -h");


        } else if(id == R.id.nav_pacu) {

            run_hack_cmd("pacu");


        } else if(id == R.id.nav_sharpshooter) {

            run_hack_cmd("sharpshooter -h");


        } else if(id == R.id.nav_gdog) {

            run_hack_cmd("gdog");


        } else if(id == R.id.nav_shellver) {

            run_hack_cmd("shellver -h");


        } else if(id == R.id.nav_mcreator) {

            run_hack_cmd("mcreator");


        } else if(id == R.id.nav_smap) {

            run_hack_cmd("smap -h");


        } else if(id == R.id.nav_encdecshellcode) {

            run_hack_cmd("encdecshellcode");


        } else if(id == R.id.nav_pocsuite) {

            run_hack_cmd("pocsuite");


        } else if(id == R.id.nav_brakeman) {

            run_hack_cmd("brakeman -h");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    finish();

                }
                return;
            }

        }
    }



    class DownloadFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{

                while(true) {

                    if (!MainActivity.this.isFinishing()){

                        showDialog(progressType);
                        break;

                    }

                }


            } catch (IllegalArgumentException e) {

            }

        }

        @Override
        protected String doInBackground(String... fileUrl) {
            int count;
            try {
                URL url = new URL(fileUrl[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setReadTimeout(3000);
                urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android ANDRAX; Mobile; rv:03) Gecko/67.0 Firefox/67.0");
                urlConnection.connect();
                int fileLength = urlConnection.getContentLength();
                InputStream inputStream = new BufferedInputStream(url.openStream(), 2048);
                OutputStream outputStream = new FileOutputStream("/sdcard/Download/andraxcore.tar.xz");

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / fileLength));
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();

                outputStream.close();
                inputStream.close();

            } catch (Exception e) {
                Log.e("Error DOWNLOAD: ", e.getMessage());


            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {

            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {

            try{
                dismissDialog(progressType);
            } catch (IllegalArgumentException e) {

            }


            new unpackandinstall().execute(urlcore);


        }
    }



    class InstallAXSurfFromURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{

                while(true) {

                    if (!MainActivity.this.isFinishing()){

                        showDialog(7);
                        break;

                    }

                }


            } catch (IllegalArgumentException e) {

            }

        }

        @Override
        protected String doInBackground(String... fileUrl) {
            int count;
            try {
                URL url = new URL(fileUrl[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setReadTimeout(3000);
                urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Android ANDRAX; Mobile; rv:03) Gecko/67.0 Firefox/67.0");
                urlConnection.connect();
                int fileLength = urlConnection.getContentLength();
                InputStream inputStream = new BufferedInputStream(url.openStream(), 1024);
                OutputStream outputStream = new FileOutputStream("/sdcard/Download/axsurf.apk");

                byte data[] = new byte[2048];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / fileLength));
                    outputStream.write(data, 0, count);
                }

                outputStream.flush();

                outputStream.close();
                inputStream.close();

            } catch (Exception e) {
                Log.e("Error DOWNLOAD: ", e.getMessage());


            }
            return null;
        }


        protected void onProgressUpdate(String... progress) {

            InstallaxsurfDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String file_url) {

            try{
                dismissDialog(7);
            } catch (IllegalArgumentException e) {

            }


            new LaunchAXSurfInstall().execute("");


        }
    }


    class fixcore extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {

                while(true) {

                    if (!MainActivity.this.isFinishing()){

                        showDialog(2);
                        break;

                    }

                }

            } catch (IllegalArgumentException e) {

            }
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            try {

                Process chmodentirecore = Runtime.getRuntime().exec("su -c chmod -R 755 " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX");
                chmodentirecore.waitFor();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }



        protected void onProgressUpdate(String... progress) {


        }

        @Override
        protected void onPostExecute(String file_url) {

            try {

                dismissDialog(2);

            } catch (IllegalArgumentException e) {

            }

            Intent intent = new Intent(MainActivity.this,SplashActivity.class);
            startActivity(intent);
            finish();


        }
    }



    class unpackandinstall extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{

                while(true) {

                    if (!MainActivity.this.isFinishing()){

                        showDialog(3);
                        break;

                    }

                }
            } catch (IllegalArgumentException e) {

            }
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            try {

                Process removeoldcore = Runtime.getRuntime().exec("su -c rm -rf " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/*");
                removeoldcore.waitFor();

                Process unzipcore = Runtime.getRuntime().exec("su -c /system/xbin/busybox tar -xJf /sdcard/Download/andraxcore.tar.xz -C " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/");

                unzipcore.waitFor();

                Process cleanzipcore = Runtime.getRuntime().exec("su -c rm -R /sdcard/Download/andraxcore.tar.xz");

                cleanzipcore.waitFor();

                Process createversionfile = Runtime.getRuntime().exec("su -c echo " + versiondefault + "" + "> " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/version");

                createversionfile.waitFor();



            } catch (IOException e) {
                e.getMessage();


            } catch (InterruptedException e) {
                e.printStackTrace();

            }

            return null;
        }



        protected void onProgressUpdate(String... progress) {


        }

        @Override
        protected void onPostExecute(String file_url) {



            try {

                dismissDialog(3);

            } catch (IllegalArgumentException e) {

            }

            Intent intent = new Intent(MainActivity.this,SplashActivity.class);
            startActivity(intent);
            finish();


        }
    }


    class LaunchAXSurfInstall extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... fileUrl) {



            return null;
        }



        protected void onProgressUpdate(String... progress) {


        }

        @Override
        protected void onPostExecute(String file_url) {

            File toInstall = new File("/sdcard/Download/axsurf.apk");
            Uri apkUri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", toInstall);
            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            MainActivity.this.startActivity(intent);

        }
    }



    public void installbusybox() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();



        if(sharedPref.getBoolean("INSTALLEDBUSYBOX", false)) {

        } else {

            try {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.busyboxnottitle);
                    builder.setMessage(R.string.busyboxnotdesc);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setCancelable(false);

                    String positiveText = getString(android.R.string.ok);
                    builder.setPositiveButton(positiveText,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = null;

                                    try {
                                        intent = new Intent(MainActivity.this, Class.forName("com.thecrackertechnology.busybox.MainActivity"));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);

                                    editor.putBoolean("INSTALLEDBUSYBOX", true);
                                    editor.apply();

                                    finish();


                                }
                            });

                    builder.setNegativeButton("FORCE INSTALL",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    editor.putBoolean("INSTALLEDBUSYBOX", true);
                                    editor.apply();

                                    Intent intent = null;

                                    try {
                                        intent = new Intent(MainActivity.this, Class.forName("com.thecrackertechnology.busybox.MainActivity"));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    startActivity(intent);

                                }
                            });

                    AlertDialog dialog = builder.create();

                    dialog.show();



            } catch (NullPointerException e) {
                e.getMessage();



            }

        }




    }



    public void checkcoreversion() {


        try {

            Process checkcoreversioncmd = Runtime.getRuntime().exec("su -c cat " + MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/version");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(checkcoreversioncmd.getInputStream()));
            int read;

            char[] buffer = new char[8000];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {

                output.append(buffer, 0, read );


            }

            checkcoreversioncmd.waitFor();

            reader.close();

            coreversion = output.toString().replaceAll("\\s","");




        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {

            /* Ex: 123 #1 is version of ANDRAX, #2 is version of BUILD and #3 is version of core */
            if(coreversion.equals(versiondefault)) {

            } else if(coreversion.equals("")) {

            }

            else {


                new DownloadFromURL().execute(urlcore);


            }

        } catch (NullPointerException e) {

            new DownloadFromURL().execute(urlcore);

        }




    }


    class postgresqlstartdaemon extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                while(true) {

                    if (!MainActivity.this.isFinishing()){

                        showDialog(6);
                        break;

                    }

                }

            } catch (IllegalArgumentException e) {

            }
        }

        @Override
        protected String doInBackground(String... fileUrl) {

            getpostgreresult();

            return null;
        }



        protected void onProgressUpdate(String... progress) {

        }

        @Override
        protected void onPostExecute(String file_url) {

            try {

                dismissDialog(6);

            } catch (IllegalArgumentException e) {

            }


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("PostgreSQL Daemon result");
            builder.setMessage(postgresqldaemonresult);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setCancelable(false);

            String positiveText = getString(android.R.string.ok);
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog dialog = builder.create();

            dialog.show();


        }
    }

    public void getpostgreresult() {

        try {


            Process startdb = Runtime.getRuntime().exec(MainActivity.this.getApplicationInfo().dataDir + "/ANDRAX/bin/service postgresql start");

            startdb.waitFor();

            BufferedReader postgrefuckbuffer = new BufferedReader(new InputStreamReader(startdb.getInputStream()));



            String tmpfuckline;
            while ((tmpfuckline = postgrefuckbuffer.readLine()) != null) {
                postgresqldaemonresult.append(tmpfuckline).append("\n");
            }

            postgrefuckbuffer.close();



        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }



    public void getuseremail() {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


        if(!sharedPref.getBoolean("useraccepted", false)) {


            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("ANDRAX Privacy policy");
            builder.setMessage("ANDRAX requires some user data such as email and device information!\n\nWe use this data to improve the platform, release of updates, benefits for users and promotions.\n\nAll data provided is kept in a safe environment.\n\nThe Cracker Technology does not collect sensitive user data, we only collect the following data: the primary device account and information about device and performance of ANDRAX.\n\nIn order to use ANDRAX you must agree to this or uninstall the platform by clicking \"OK\" you ensure that you are aware of this and provide the permissions for The Cracker Technology to collect this data!");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setCancelable(false);

            String positiveText = "OK";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},true, null, null, null, null);
                            startActivityForResult(googlePicker, 33);

                        }
                    });

            String negativeText = "I DON'T";
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();

                        }
                    });

            AlertDialog dialog = builder.create();

            dialog.show();



        }



    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 33 && resultCode == RESULT_OK) {

            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

            if (accountName != null && !accountName.equals("")) {

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("useraccepted", true);
                editor.apply();

                Toast.makeText(MainActivity.this, "EMAIL = " + accountName ,Toast.LENGTH_LONG).show();

                try {
                    sendemailnewsletter(accountName);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }



        } else if(requestCode == 33 && resultCode != RESULT_OK) {

            finish();

        } else if(requestCode == 171) {

            PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);
            boolean isIgnoringBatteryOptimizations = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
            }
            if(!isIgnoringBatteryOptimizations){

                get_motherfucker_battery();

            }

        }
    }



    private void sendemailnewsletter(String emailtosend) throws Exception {

        StringBuilder tokenUri=new StringBuilder("email=");
        tokenUri.append(URLEncoder.encode(emailtosend,"UTF-8"));

        String url = "https://www.thecrackertechnology.com/newsletter/andrax-newsletter.php";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Android; ANDRAX; Mobile; rv:67.0) Gecko/67.0 Firefox/67.0");
        con.setRequestProperty("Accept-Language", "UTF-8");

        con.setDoOutput(true);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
        outputStreamWriter.write(tokenUri.toString());
        outputStreamWriter.flush();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        Toast.makeText(MainActivity.this, "TCT Response: " + response.toString() ,Toast.LENGTH_LONG).show();


    }


    public void run_hack_cmd(String cmd) {

        Intent intent = Bridge.createExecuteIntent(cmd);
        startActivity(intent);

    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {

                if (errorDialog == null) {
                    errorDialog = googleApiAvailability.getErrorDialog(this, resultCode, 2404);
                    errorDialog.setCancelable(false);
                }

                if (!errorDialog.isShowing())
                    errorDialog.show();

            }
        }

        return resultCode == ConnectionResult.SUCCESS;
    }


    public void get_motherfucker_battery() {

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            boolean isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
            if(!isIgnoringBatteryOptimizations){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 171);
            }

        }

    }





}
