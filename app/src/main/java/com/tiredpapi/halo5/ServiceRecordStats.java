package com.tiredpapi.halo5;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michal on 10/27/2016.
 */
public class ServiceRecordStats {
    private JSONObject jsonObject;

    public ServiceRecordStats(String data) {
        try {
            this.jsonObject = new JSONObject(data);

        } catch (JSONException e) {
           Log.w(Constant.LOG_TAG, e);
        }
    }

   public String getStatsString() throws JSONException {
        String s = jsonObject.getString("csrPercentil").toString() + "\n" +
                jsonObject.getString("TotalKills").toString() + "\n" +
                jsonObject.getString("TotalAssists").toString() + "\n" +
                jsonObject.getString("TotalDeaths").toString() + "\n" +
                jsonObject.getString("KillPerGame").toString() + "\n" +
                jsonObject.getString("AsistPerGame").toString() + "\n" +
                jsonObject.getString("DeathsPerGame").toString() + "\n" +
                jsonObject.getString("TotalGamesWon").toString() + "\n" +
                jsonObject.getString("TotalGamesLost").toString() + "\n" +
                jsonObject.getString("TotalGamesWon%").toString() + "\n" +
                jsonObject.getString("TotalHeadShot").toString() + "\n" +
                jsonObject.getString("HeadPerGame").toString() + "\n";

        return s;
    }
}
