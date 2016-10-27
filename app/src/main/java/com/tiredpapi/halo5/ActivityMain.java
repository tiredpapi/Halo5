package com.tiredpapi.halo5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMain extends AppCompatActivity {
    TextView textViewInfo;
    TextView textViewTombav;
    TextView textViewBorec;
    TextView textViewLimited;

    public static DatabaseReference rootRef;
    private String info = "Games:\nCsrPercentil:\nTotalKills:\nTotalAssist:\nTotalDeaths:\nKill/g:\nAss/g:\nDeath/g:\n"+
            "GamesWon:\nGamesLost:\nGamesWon%:\nTotalHeadShot:\nTotalHead%:\n";

    private String lastGame = "-\nLast Games:\nTotal Kills:\nTotal Assist:\nTotal Death\n" +
            "Kill/g:\nAssist/g:\nDeaths/g:\n";

    private String trends = "-\nTrends:\nKills:\nAssist:\nDeaths:\n";

    public void buttonUpdateServiceRecordMain(View view) {
        Intent i = new Intent(this, ActivityUpdate.class);
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

        textViewInfo.setTextSize(20);
        textViewInfo.setText(info + lastGame + trends);

        rootRef = FirebaseDatabase.getInstance().getReferenceFromUrl(Constant.FIREBASE_URL);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    StringServiceRecord tombavS = new StringServiceRecord(dataSnapshot.child("Service Record Stats").child("tombav").getValue().toString());
                    StringServiceRecord borecS = new StringServiceRecord(dataSnapshot.child("Service Record Stats").child("borec z prahy").getValue().toString());
                    StringServiceRecord limitedS = new StringServiceRecord(dataSnapshot.child("Service Record Stats").child("limitedoregon9").getValue().toString());

                    StringGamesRecord tombavG = new StringGamesRecord(dataSnapshot.child("Games Record Stats").child("tombav").getValue().toString());
                    StringGamesRecord borecG = new StringGamesRecord(dataSnapshot.child("Games Record Stats").child("borec z prahy").getValue().toString());
                    StringGamesRecord limitedG = new StringGamesRecord(dataSnapshot.child("Games Record Stats").child("limitedoregon9").getValue().toString());

                    StringTrendsRecord tombavT = new StringTrendsRecord(dataSnapshot.child("Service Record Stats").child("tombav").getValue().toString(),
                            dataSnapshot.child("Games Record Stats").child("tombav").getValue().toString());
                    StringTrendsRecord borecT = new StringTrendsRecord(dataSnapshot.child("Service Record Stats").child("borec z prahy").getValue().toString(),
                            dataSnapshot.child("Games Record Stats").child("borec z prahy").getValue().toString());
                    StringTrendsRecord limitedT = new StringTrendsRecord(dataSnapshot.child("Service Record Stats").child("limitedoregon9").getValue().toString(),
                            dataSnapshot.child("Games Record Stats").child("limitedoregon9").getValue().toString());


                    String tommbav = "tombav\n" + tombavS.getStatsString() + "-\n" + tombavG.getStatsSting() + "-\n\n" + tombavT.getStats();
                    String borec = "borec\n" + borecS.getStatsString() + "-\n" + borecG.getStatsSting()+ "-\n\n" + borecT.getStats();
                    String limited = "limited\n" + limitedS.getStatsString() + "-\n" + limitedG.getStatsSting()+ "-\n\n" + limitedT.getStats();

                    textViewBorec.setTextSize(20);
                    textViewTombav.setTextSize(20);
                    textViewLimited.setTextSize(20);

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
