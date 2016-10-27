package com.tiredpapi.halo5;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

public class ActivityUpdate extends AppCompatActivity {

    private String data;
    private TextView textView;
    private Spinner spinner;

    public void buttonUpdateGamesRecordStats(View view) {
        ActivityMain.rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tombavData = dataSnapshot.child("Games Record Raw").child("tombav").getValue().toString();
                String borecData = dataSnapshot.child("Games Record Raw").child("borec z prahy").getValue().toString();
                String limitedData = dataSnapshot.child("Games Record Raw").child("limitedoregon9").getValue().toString();

                try {
                    JsonGamesRecord tombavJson = new JsonGamesRecord(tombavData);
                    JsonGamesRecord borecJson = new JsonGamesRecord(borecData);
                    JsonGamesRecord limitedJson = new JsonGamesRecord(limitedData);

                    ActivityMain.rootRef.child("Games Record Stats").child("tombav").setValue(tombavJson.getHashMap());
                    ActivityMain.rootRef.child("Games Record Stats").child("borec z prahy").setValue(borecJson.getHashMap());
                    ActivityMain.rootRef.child("Games Record Stats").child("limitedoregon9").setValue(limitedJson.getHashMap());

                } catch (Exception e) {
                    Log.w(Constant.LOG_TAG, e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(Constant.LOG_TAG, databaseError.toString());
            }
        });
    }

    public void buttonUpdateGamesRecordRaw(View view) {
        int count = 10;

        try {
            count = Integer.parseInt(spinner.getSelectedItem().toString());

        } catch(Exception e) {
            Log.w(Constant.LOG_TAG, e);
        }
        String data;

        GetDataAsyncTask gameRecordTombav = new GetDataAsyncTask();
        GetDataAsyncTask gameRecordBorec = new GetDataAsyncTask();
        GetDataAsyncTask gameRecordLimited = new GetDataAsyncTask();

        String urlTombav = "https://www.haloapi.com/stats/h5/players/tombav/matches?count=" + count;
        String urlBorec = "https://www.haloapi.com/stats/h5/players/borec z prahy/matches?count=" + count;
        String urlLimited = "https://www.haloapi.com/stats/h5/players/limitedoregon9/matches?count=" + count;



        try {
            data = gameRecordTombav.execute(urlTombav).get();
            ActivityMain.rootRef.child("Games Record Raw").child("tombav").setValue(data);

            data = gameRecordBorec.execute(urlBorec).get();
            ActivityMain.rootRef.child("Games Record Raw").child("borec z prahy").setValue(data);

            data = gameRecordLimited.execute(urlLimited).get();
            ActivityMain.rootRef.child("Games Record Raw").child("limitedoregon9").setValue(data);

            textView.setText("Update game record finished");

        } catch (Exception e) {
            Log.w(Constant.LOG_TAG, e);
        }
    }

    public void buttonUpdateServiceRecordStats(View view) {
        ActivityMain.rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tombavData = dataSnapshot.child("Service Record Raw").child("tombav").getValue().toString();
                String borecData = dataSnapshot.child("Service Record Raw").child("borec z prahy").getValue().toString();
                String limitedData = dataSnapshot.child("Service Record Raw").child("limitedoregon9").getValue().toString();

                try {
                    JsonServiceRecord tombavJson = new JsonServiceRecord(tombavData);
                    JsonServiceRecord borecJson = new JsonServiceRecord(borecData);
                    JsonServiceRecord limitedJson = new JsonServiceRecord(limitedData);

                    ActivityMain.rootRef.child("Service Record Stats").child("tombav").setValue(tombavJson.getHashMap());
                    ActivityMain.rootRef.child("Service Record Stats").child("limitedoregon9").setValue(limitedJson.getHashMap());
                    ActivityMain.rootRef.child("Service Record Stats").child("borec z prahy").setValue(borecJson.getHashMap());

                } catch(Exception e) {
                    Log.w(Constant.LOG_TAG, e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(Constant.LOG_TAG, databaseError.toString());
            }
        });
    }

    public void buttonUpdateServiceRecordService(View view) {
        Log.i(Constant.LOG_TAG, "Updating Service Record RAW");

        GetDataAsyncTask serviceRecordTombav = new GetDataAsyncTask();
        GetDataAsyncTask serviceRecordBorec = new GetDataAsyncTask();
        GetDataAsyncTask serviceRecordLimited = new GetDataAsyncTask();

        String url = "https://www.haloapi.com/stats/h5/servicerecords/arena?players=";

        try {
            data = serviceRecordTombav.execute(url + "tombav").get();
            ActivityMain.rootRef.child("Service Record Raw").child("tombav").setValue(data);

            data = serviceRecordBorec.execute(url + "borec z prahy").get();
            ActivityMain.rootRef.child("Service Record Raw").child("borec z prahy").setValue(data);

            data = serviceRecordLimited.execute(url + "limitedoregon9").get();
            ActivityMain.rootRef.child("Service Record Raw").child("limitedoregon9").setValue(data);

            textView.setText("Update service record finished");

        } catch (InterruptedException e) {
            Log.w(Constant.LOG_TAG, e);
        } catch (ExecutionException e) {
            Log.w(Constant.LOG_TAG, e);
        }
    }

    public void buttonBack(View view) {
        Intent i = new Intent(this, ActivityMain.class);
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service_record);

        textView = (TextView) findViewById(R.id.textViewServiceRecord);

        spinner = (Spinner) findViewById(R.id.spinner);
        Integer[] items = new Integer[]{10,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,50,100,200,400};

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }
}
