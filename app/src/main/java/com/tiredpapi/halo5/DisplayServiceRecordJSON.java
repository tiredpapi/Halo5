package com.tiredpapi.halo5;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michal on 10/26/2016.
 */
public class DisplayServiceRecordJSON {
    private JSONObject jsonObjectData;
    private JSONArray jsonArrayResults;
    private JSONObject jsonObjectResult;
    private JSONObject jsonObjectArenaStats;
    private JSONArray jsonArrayArenaPlaylistStats;

    private String csrPercentil;
    private String totalGamesCompleted;
    private String totalKills;
    private String totalAssist;
    private String totalDeaths;
    private String totalGamesWon;
    private String totalGamesLost;
    private String totalHeadShots;

    public DisplayServiceRecordJSON(String data) throws JSONException {
        this.jsonObjectData = new JSONObject(data);
        this.jsonArrayResults = jsonObjectData.getJSONArray("Results");
        this.jsonObjectResult = new JSONObject(jsonArrayResults.getJSONObject(0).getString("Result"));
        this.jsonObjectArenaStats = new JSONObject(jsonObjectResult.getString("ArenaStats"));
        this.jsonArrayArenaPlaylistStats = jsonObjectArenaStats.getJSONArray("ArenaPlaylistStats");

        for(int i = 0; i < jsonArrayArenaPlaylistStats.length(); i++) {
            if(jsonArrayArenaPlaylistStats.getJSONObject(i).getString("PlaylistId").contains("892189e9-d712-4bdb-afa7-1ccab43fbed4")) {
                this.totalGamesCompleted = jsonArrayArenaPlaylistStats.getJSONObject(i).getString("TotalGamesCompleted");
                this.csrPercentil = jsonArrayArenaPlaylistStats.getJSONObject(i).getString("CsrPercentile");
                this.totalKills = jsonArrayArenaPlaylistStats.getJSONObject(i).getString("TotalKills");
                this.totalAssist = jsonArrayArenaPlaylistStats.getJSONObject(i).getString("TotalAssists");
                this.totalDeaths = jsonArrayArenaPlaylistStats.getJSONObject(i).getString("TotalDeaths");
                this.totalGamesWon = jsonArrayArenaPlaylistStats.getJSONObject(i).getString("TotalGamesWon");
                this.totalGamesLost = jsonArrayArenaPlaylistStats.getJSONObject(i).getString("TotalGamesLost");
                this.totalHeadShots = jsonArrayArenaPlaylistStats.getJSONObject(i).getString("TotalHeadshots");
            }
        }
    }

    public Map getHashMap() {
        Map map = new HashMap<>();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);


        map.put("GamesCompleted", this.totalGamesCompleted);
        map.put("csrPercentil", this.csrPercentil);
        map.put("TotalKills", this.totalKills);
        map.put("TotalAssists", this.totalAssist);
        map.put("TotalDeaths", this.totalDeaths);
        map.put("KillPerGame", df.format((float)(Integer.parseInt(this.totalKills) / (float) Integer.parseInt(this.totalGamesCompleted))));
        map.put("AsistPerGame",df.format((float) Integer.parseInt(this.totalAssist) / (float) Integer.parseInt(this.totalGamesCompleted)));
        map.put("DeathsPerGame", df.format((float) Integer.parseInt(this.totalDeaths) /  (float) Integer.parseInt(this.totalGamesCompleted)));
        map.put("TotalGamesWon", this.totalGamesWon);
        map.put("TotalGamesLost", this.totalGamesLost);
        map.put("TotalGamesWon%", df.format((float) Integer.parseInt(this.totalGamesWon) /  (float) Integer.parseInt(this.totalGamesCompleted) * 100));
        map.put("TotalHeadShot", this.totalHeadShots);
        map.put("HeadPerGame", df.format((float) Integer.parseInt(this.totalHeadShots) / (float) Integer.parseInt(this.totalGamesCompleted)));

        return map;
    }
}
