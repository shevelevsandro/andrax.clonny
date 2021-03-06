package com.thecrackertechnology.andrax;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRTutorial;
    private AdapterTutorial mAdapter;
    private ProgressBar progressBarTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBarTutorial = (ProgressBar)findViewById(R.id.progressTutorial);

        new AsyncGetTutorials().execute();



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tutorials, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();


        if (id == R.id.action_submit) {

            AlertDialog.Builder builder = new AlertDialog.Builder(TutorialActivity.this);
            builder.setTitle("Submit Tutorial");
            builder.setMessage("If you have a channel on youtube, website, blog... about computer security or hacking send your tutorial and we will help spread your work\n\nOnly tutorials or articles about ANDRAX!");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setCancelable(false);

            String positiveText = "SUBMIT";
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String mailto = "mailto:weidsom@thecrackertechnology.com" + "?subject=" + Uri.encode("Submit Tutorial");

                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                            emailIntent.setData(Uri.parse(mailto));

                            try {
                                startActivity(emailIntent);
                            } catch (ActivityNotFoundException e) {

                            }

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

        }

        return super.onOptionsItemSelected(item);
    }

    private class AsyncGetTutorials extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread


        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("https://andrax.thecrackertechnology.com/tutorials.json");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            progressBarTutorial.setVisibility(View.INVISIBLE);
            progressBarTutorial.setVisibility(View.INVISIBLE);

            hacktutorials(result);

        }


        public void hacktutorials(String myresult) {

            List<DataTutorial> data=new ArrayList<>();

            try {

                JSONArray jArray = new JSONArray(myresult);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++) {

                    JSONObject json_data = jArray.getJSONObject(i);
                    DataTutorial tutorialloaddata = new DataTutorial();
                    tutorialloaddata.TutorialImage= json_data.getString("tutorialimg");
                    tutorialloaddata.TutorialName= json_data.getString("tutorialname");
                    tutorialloaddata.Tutorialdesc= json_data.getString("tutorialdesc");
                    tutorialloaddata.Tutoriallink= json_data.getString("tutoriallink");
                    data.add(tutorialloaddata);

                }

                // Setup and Handover data to recyclerview
                //LinearLayoutManager layoutManager = new LinearLayoutManager(VideosActivity.this, LinearLayoutManager.HORIZONTAL, false);

                mRTutorial = (RecyclerView)findViewById(R.id.tutorialrecycle);
                mAdapter = new AdapterTutorial(TutorialActivity.this, data);
                mRTutorial.setAdapter(mAdapter);
                mRTutorial.setLayoutManager(new GridLayoutManager(TutorialActivity.this, 2));

                //mRTutorial.setLayoutManager(new LinearLayoutManager(TutorialActivity.this, LinearLayoutManager.VERTICAL, false));

            } catch (JSONException e) {
                Toast.makeText(TutorialActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }
}


