package com.tiredpapi.halo5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

public class DisplayServiceRecordActivity extends AppCompatActivity {
    private TextView textView;
    private String limitedData;
    private String borecData;
    private String tombavData;


    String names = "Players:   Borec   |   Tombav   |  Limited";

    private void getJsonData() throws JSONException {
        DisplayServiceRecordJSON tombavJson = new DisplayServiceRecordJSON(tombavData);
        DisplayServiceRecordJSON limitedJson = new DisplayServiceRecordJSON(limitedData);
        DisplayServiceRecordJSON borecJson = new DisplayServiceRecordJSON(borecData);

        String names = "Players:   Borec   |   Tombav   |  Limited \n " +
                "Kills: " + borecJson.getKills() + "  |  "  + tombavJson.getKills() + "  |  "  + limitedJson.getKills() + "\n" +
                "Assist: " + borecJson.getAssist() + "  |  "  + tombavJson.getAssist() + "  |  "  + limitedJson.getAssist() + "\n" +
                "Deaths: " + borecJson.getDeaths() + "  |  "  + tombavJson.getDeaths() + "  |  "  + limitedJson.getLost() + "\n" +
                "Games Won: " + borecJson.getWon() + "  |  "  + tombavJson.getWon() + "  |  "  + limitedJson.getWon() + "\n" +
                "Games Lost: " + borecJson.getLost() + "  |  "  + tombavJson.getLost() + "  |  "  + limitedJson.getLost();

        textView.setText(names);
    }

    public void buttonBackDisplayServiceRecord(View view) {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_service_record);

        textView = (TextView) findViewById(R.id.textViewStats);


        MainActivity.rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                limitedData = dataSnapshot.child("Service Record Raw").child("limitedoregon9").getValue().toString();
                borecData = dataSnapshot.child("Service Record Raw").child("borec z prahy").getValue().toString();
                tombavData = dataSnapshot.child("Service Record Raw").child("tombav").getValue().toString();

                try {
                    getJsonData();
                } catch (JSONException e) {
                    Log.w(Constant.LOG_TAG, e);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(Constant.LOG_TAG, "Database error: ", databaseError.toException());
            }
        });
    }
}
