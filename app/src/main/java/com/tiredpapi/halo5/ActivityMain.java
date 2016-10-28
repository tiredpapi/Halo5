package com.tiredpapi.halo5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityMain extends AppCompatActivity {
    private TextView textViewTombav;
    private TextView textViewBorec;
    private TextView textViewLimited;

    public static DatabaseReference rootRef;

    public void buttonUpdateServiceRecordMain(View view) {
        Intent i = new Intent(this, ActivityUpdate.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String info = "\nGames:\nCsrPercentil:\nTotalKills:\nTotalAssist:\nTotalDeaths:\nKill/g:\nAss/g:\nDeath/g:\n" +
                "GamesWon:\nGamesLost:\nGamesWon%:\nTotalHeadShot:\nTotalHead%:\n";

        String lastGame = "-\nLast Games:\nTotal Kills:\nTotal Assist:\nTotal Death\n" +
                "Kill/g:\nAssist/g:\nDeaths/g:\n";

        String trends = "-\nTrends:\nKills:\nAssist:\nDeaths:\n";

        TextView textViewInfo = (TextView) findViewById(R.id.textViewInfo);
        textViewBorec = (TextView) findViewById(R.id.textViewBorec);
        textViewLimited = (TextView) findViewById(R.id.textViewLimited);
        textViewTombav = (TextView) findViewById(R.id.textViewTombav);

        textViewInfo.setTextSize(18);
        textViewInfo.setText(info + lastGame + trends);

        rootRef = FirebaseDatabase.getInstance().getReferenceFromUrl(Constant.FIREBASE_URL);

        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

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
                    String borec = "borec\n" + borecS.getStatsString() + "-\n" + borecG.getStatsSting() + "-\n\n" + borecT.getStats();
                    String limited = "limited\n" + limitedS.getStatsString() + "-\n" + limitedG.getStatsSting() + "-\n\n" + limitedT.getStats();

                    textViewBorec.setTextSize(18);
                    textViewTombav.setTextSize(18);
                    textViewLimited.setTextSize(18);

                    textViewTombav.setText(tommbav);
                    textViewLimited.setText(limited);
                    textViewBorec.setText(borec);

                    pb.setVisibility(View.GONE);

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
