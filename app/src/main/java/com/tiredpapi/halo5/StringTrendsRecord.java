package com.tiredpapi.halo5;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by michal on 10/27/2016.
 */
public class StringTrendsRecord {
    private JSONObject jsonObjectAll;
    private JSONObject jsonObjectSingel;

    public StringTrendsRecord(String all, String singel) {
        try {
            this.jsonObjectAll = new JSONObject(all);
            this.jsonObjectSingel = new JSONObject(singel);
        } catch (Exception e) {
            Log.w(Constant.LOG_TAG, e);
        }
    }

    public String getStats() throws JSONException {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        String kill =  df.format(Float.valueOf(jsonObjectSingel.getString("KillPerGame")) - Float.valueOf(jsonObjectAll.getString("KillPerGame")));
        String assist = df.format(Float.valueOf(jsonObjectSingel.getString("AssistPerGame")) - Float.valueOf(jsonObjectAll.getString("AsistPerGame")));
        String death = df.format(Float.valueOf(jsonObjectAll.getString("DeathsPerGame")) - Float.valueOf(jsonObjectSingel.getString("DeathsPerGame")));

        return kill + "\n" + assist + "\n" + death;
    }


}
