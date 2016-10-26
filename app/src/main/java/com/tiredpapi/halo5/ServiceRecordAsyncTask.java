package com.tiredpapi.halo5;

/**
 * Created by michal on 10/25/2016.
 **/






import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class ServiceRecordAsyncTask extends AsyncTask<String, Void, String> {
    private String data = "";

    @Override
    protected String doInBackground(String... string) {
        try {
            Log.i(Constant.LOG_TAG, "Service record async task started");

            URL url = new URL("https://www.haloapi.com/stats/h5/servicerecords/arena?players=" + string[0]);
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

                    return data.toString();

                default:
                    return null;
            }


        } catch (MalformedURLException e) {
            Log.w(Constant.LOG_TAG, e);
        } catch (IOException e) {
            Log.w(Constant.LOG_TAG, e);
        }

        return null;
    }
}