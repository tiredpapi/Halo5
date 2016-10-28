package com.tiredpapi.halo5;

/**
 * Created by michal on 10/25/2016.
 **/

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class GetDataAsyncTask extends AsyncTask<String, Void, String> {
    private String data = "";
    private Activity activity;
    private ProgressBar pb;

    @Override
    protected String doInBackground(String... string) {
        try {
            Log.i(Constant.LOG_TAG, "Service record async task started");

            URL url = new URL(string[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestProperty("Ocp-Apim-Subscription-Key", "4fed4b5bf1e340cca547c949305f8e9a");
            httpURLConnection.connect();

            switch(httpURLConnection.getResponseCode()) {
                case 401:
                    Log.w(Constant.LOG_TAG, "Authentication error");
                    return null;

                case 200:
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    int charData = inputStreamReader.read();

                    while(charData > -1) {
                        data = data + (char) charData;
                        charData = inputStreamReader.read();
                    }

                    Log.i(Constant.LOG_TAG, "Service record async task finished");

                    return data;

                default:
                    return null;
            }

        } catch (IOException e) {
            Log.w(Constant.LOG_TAG, e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(View.VISIBLE);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        ProgressBar pb = (ProgressBar) activity.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
    }
}