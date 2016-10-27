package com.tiredpapi.halo5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;

public class UpdateServiceRecordActivity extends AppCompatActivity {

    private String data;
    private TextView textView;

    public void buttonUpdateServiceRecordStats(View view) {
        MainActivity.rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tombavData = dataSnapshot.child("Service Record Raw").child("tombav").getValue().toString();
                String borecData = dataSnapshot.child("Service Record Raw").child("borec z prahy").getValue().toString();
                String limitedData = dataSnapshot.child("Service Record Raw").child("limitedoregon9").getValue().toString();

                try {
                    DisplayServiceRecordJSON tombavJson = new DisplayServiceRecordJSON(tombavData);
                    DisplayServiceRecordJSON borecJson = new DisplayServiceRecordJSON(borecData);
                    DisplayServiceRecordJSON limitedJson = new DisplayServiceRecordJSON(limitedData);

                    MainActivity.rootRef.child("Service Record Stats").child("tombav").setValue(tombavJson.getHashMap());
                    MainActivity.rootRef.child("Service Record Stats").child("limitedoregon9").setValue(limitedJson.getHashMap());
                    MainActivity.rootRef.child("Service Record Stats").child("borec z prahy").setValue(borecJson.getHashMap());

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

        ServiceRecordAsyncTask serviceRecordTombav = new ServiceRecordAsyncTask();
        ServiceRecordAsyncTask serviceRecordBorec = new ServiceRecordAsyncTask();
        ServiceRecordAsyncTask serviceRecordLimited = new ServiceRecordAsyncTask();

        String url = "https://www.haloapi.com/stats/h5/servicerecords/arena?players=";

        try {
            data = serviceRecordTombav.execute(url + "tombav").get();
            MainActivity.rootRef.child("Service Record Raw").child("tombav").setValue(data);

            data = serviceRecordBorec.execute(url + "borec z prahy").get();
            MainActivity.rootRef.child("Service Record Raw").child("borec z prahy").setValue(data);

            data = serviceRecordLimited.execute(url + "limitedoregon9").get();
            MainActivity.rootRef.child("Service Record Raw").child("limitedoregon9").setValue(data);

            textView.setText("Update service record finished");

        } catch (InterruptedException e) {
            Log.w(Constant.LOG_TAG, e);
        } catch (ExecutionException e) {
            Log.w(Constant.LOG_TAG, e);
        }
    }

    public void buttonBack(View view) {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service_record);

        textView = (TextView) findViewById(R.id.textViewServiceRecord);

    }
}
