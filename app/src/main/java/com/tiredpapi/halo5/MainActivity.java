package com.tiredpapi.halo5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static DatabaseReference rootRef;

    public void buttonDisplayServiceRecord(View view) {
        Intent i = new Intent(this, DisplayServiceRecordActivity.class);
        startActivity(i);
    }

    public void buttonUpdateServiceRecordMain(View view) {
        Intent i = new Intent(this, UpdateServiceRecordActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootRef = FirebaseDatabase.getInstance().getReferenceFromUrl(Constant.FIREBASE_URL);
    }
}
