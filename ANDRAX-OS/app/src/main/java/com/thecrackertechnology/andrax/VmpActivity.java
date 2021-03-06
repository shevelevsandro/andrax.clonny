package com.thecrackertechnology.andrax;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.lang.reflect.Method;


public class VmpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStart, mCancel;
    private final String TAG = "HotSpot";
    private EditText mSSID, mPassword;
    private String mUserInput, mSecurityPref;
    private RadioGroup mRadioGrp;
    private RadioButton mRadioBtn;

    /**
     *
     * Help me, i'm dying...
     *
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vmp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStart = (Button) findViewById(R.id.start);
        mCancel = (Button) findViewById(R.id.cancel);
        mSSID = (EditText) findViewById(R.id.ssid);
        mPassword = (EditText) findViewById(R.id.password);
        mRadioGrp = (RadioGroup) findViewById(R.id.rdgrp);
        mStart.setOnClickListener(this);
        mCancel.setOnClickListener(this);


    }

    public void onClick(View v) {

        if (v == mStart) {
            mRadioBtn = (RadioButton) findViewById(mRadioGrp.getCheckedRadioButtonId());
            mSecurityPref = mRadioBtn.getText().toString();
            initConnection(this);

        } else if (v == mCancel) {
            mSSID.setText("");
            mPassword.setText("");

        }

    }

    private void initConnection(final Context context) {

        WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }

        WifiConfiguration mWifiConfig = new WifiConfiguration();
        mWifiConfig.SSID = mSSID.getText().toString();
        mWifiConfig.preSharedKey = mPassword.getText().toString();
        mWifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        mWifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        mWifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        mWifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        mWifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        mWifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        mWifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        if (mSecurityPref.equals("Open")) {
            mWifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            Toast.makeText(this, "Security: Open",
                    Toast.LENGTH_LONG).show();
        } else if (mSecurityPref.equals("WPA-PSK")){
            mWifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            Toast.makeText(this, "Security: WPA/PSK", Toast.LENGTH_LONG).show();

        }


        try {
            /* Here we set Wi-Fi hotspot with the configuration defined above */

            Method setAPEnable = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            boolean apStatus = (Boolean) setAPEnable.invoke(mWifiManager, mWifiConfig, true);
 /* Check if the Wi-Fi hotspot was successfully enabled */
            Method isApEnabled = mWifiManager.getClass().getMethod("isWifiApEnabled");
            while (!(Boolean) isApEnabled.invoke(mWifiManager)) {
            }
            ;
            Method getWifiApStateMethod = mWifiManager.getClass().getMethod("getWifiApState");
            int apState = (Integer) getWifiApStateMethod.invoke(mWifiManager);
            Method getWifiApConfigurationMethod = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            mWifiConfig = (WifiConfiguration) getWifiApConfigurationMethod.invoke(mWifiManager);

        } catch (Exception e) {
            Log.e(this.getClass().toString(), "", e);
        }

    }



}
