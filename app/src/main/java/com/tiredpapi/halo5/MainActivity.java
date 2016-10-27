package com.tiredpapi.halo5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView textViewInfo;
    TextView textViewTombav;
    TextView textViewBorec;
    TextView textViewLimited;

    public static DatabaseReference rootRef;
    private String info = "Games:\nCsrPercentil:\nTotalKills:\nTotalAssist:\nTotalDeaths:\nKill/g:\nAss/g:\nDeath/g:\n"+
            "GamesWon:\nGamesLost:\nGamesWon%:\nTotalHeadShot:\nTotalHead%:\n";

    private String lastGame = "Last Games:\n";

    public void buttonUpdateServiceRecordMain(View view) {
        Intent i = new Intent(this, UpdateServiceRecordActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        textViewBorec = (TextView) findViewById(R.id.textViewBorec);
        textViewLimited = (TextView) findViewById(R.id.textViewLimited);
        textViewTombav = (TextView) findViewById(R.id.textViewTombav);

        textViewInfo.setText(info + lastGame);

        rootRef = FirebaseDatabase.getInstance().getReferenceFromUrl(Constant.FIREBASE_URL);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    ServiceRecordStats tombavS = new ServiceRecordStats(dataSnapshot.child("Service Record Stats").child("tombav").getValue().toString());
                    ServiceRecordStats borecS = new ServiceRecordStats(dataSnapshot.child("Service Record Stats").child("borec z prahy").getValue().toString());
                    ServiceRecordStats limitedS = new ServiceRecordStats(dataSnapshot.child("Service Record Stats").child("limitedoregon9").getValue().toString());

                    String tommbav = "tombav\n" + tombavS.getStatsString();
                    String borec = "borec\n" + borecS.getStatsString();
                    String limited = "limited\n" + limitedS.getStatsString();

                    textViewTombav.setText(tommbav);
                    textViewLimited.setText(limited);
                    textViewBorec.setText(borec);

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
}
