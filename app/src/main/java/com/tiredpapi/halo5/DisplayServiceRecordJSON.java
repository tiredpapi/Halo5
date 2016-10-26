package com.tiredpapi.halo5;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michal on 10/26/2016.
 */
public class DisplayServiceRecordJSON {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private JSONArray jsonArray1;
    private JSONObject jsonObject1;
    private JSONObject jsonObject2;

    public DisplayServiceRecordJSON(String data) throws JSONException {
        this.jsonObject = new JSONObject(data);
        this.jsonArray = jsonObject.getJSONArray("Results");
        this.jsonObject1 = new JSONObject(jsonArray.getJSONObject(0).getString("Result"));
        this.jsonObject2 = new JSONObject(jsonObject1.getString("ArenaStats"));
        this.jsonArray1 = jsonObject2.getJSONArray("ArenaPlaylistStats");
    }

    public String getKills() throws JSONException {
        return jsonArray1.getJSONObject(1).getString("TotalKills");
    };

    public String getAssist() throws JSONException {
        return jsonArray1.getJSONObject(1).getString("TotalAssists");
    };

    public String getDeaths() throws JSONException {
        return jsonArray1.getJSONObject(1).getString("TotalDeaths");
    };

    public String getWon() throws JSONException {
        return jsonArray1.getJSONObject(1).getString("TotalGamesWon");
    };

    public String getLost() throws JSONException {
        return jsonArray1.getJSONObject(1).getString("TotalGamesLost");
    };
}
