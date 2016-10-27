package com.tiredpapi.halo5;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michal on 10/27/2016.
 */
public class StringGamesRecord {
    private JSONObject jsonObject;

    public StringGamesRecord(String data) {
        try {
            this.jsonObject = new JSONObject(data);

        } catch (JSONException e) {
            Log.w(Constant.LOG_TAG, e);
        }
    }

    public String getStatsSting() throws JSONException {
        String s = jsonObject.getString("TotalGames") + "\n" +
                jsonObject.getString("TotalKills") + "\n" +
                jsonObject.getString("TotalAssist") + "\n" +
                jsonObject.getString("TotalDeaths") + "\n" +
                jsonObject.getString("KillPerGame") + "\n" +
                jsonObject.getString("AssistPerGame") + "\n" +
                jsonObject.getString("DeathsPerGame") + "\n";

        return s;
    }
}
