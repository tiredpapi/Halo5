package com.tiredpapi.halo5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public void checkFirebase(View view) {
        Map<String, Objects> names = new HashMap<>();
        names.put("Name", "Michal");
        names.put("SureName", "Tomas");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(Constant.LOG_TAG, "onCreate started");

        FirebaseDatabase fb = new FirebaseDatabase(Constant.FIREBASE_URL);
    }
}
