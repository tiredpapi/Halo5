package com.tiredpapi.halo5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;

public class UpdateServiceRecordActivity extends AppCompatActivity {

    private String data;

    public void buttonUpdateServiceRecordService(View view) {
        Log.i(Constant.LOG_TAG, "Updating Service Record RAW");


        ServiceRecordAsyncTask serviceRecordTombav = new ServiceRecordAsyncTask();
        ServiceRecordAsyncTask serviceRecordBorec = new ServiceRecordAsyncTask();
        ServiceRecordAsyncTask serviceRecordLimited = new ServiceRecordAsyncTask();


        try {
            data = serviceRecordTombav.execute("tombav").get();
            MainActivity.rootRef.child("Service Record Raw").child("tombav").setValue(data);

            data = serviceRecordBorec.execute("borec z prahy").get();
            MainActivity.rootRef.child("Service Record Raw").child("borec z prahy").setValue(data);

            data = serviceRecordLimited.execute("limitedoregon9").get();
            MainActivity.rootRef.child("Service Record Raw").child("limitedoregon9").setValue(data);


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
    }
}
