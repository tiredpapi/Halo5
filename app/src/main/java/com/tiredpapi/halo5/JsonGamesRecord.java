package com.tiredpapi.halo5;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michal on 10/27/2016.
 */
public class JsonGamesRecord {
    private int totalGames;
    private int totalKills;
    private int totalAssist;
    private int totalDeath;


    public JsonGamesRecord(String data) {
        totalKills = 0;
        totalAssist = 0;
        totalDeath = 0;

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("Results");
            JSONArray jsonArrayPlayers;

            totalGames = jsonArray.length();

            for(int i = 0; i < jsonArray.length(); i++) {
                if(jsonArray.getJSONObject(i).getString("HopperId").contains("892189e9-d712-4bdb-afa7-1ccab43fbed4")) {
                    jsonArrayPlayers = new JSONArray(jsonArray.getJSONObject(i).getString("Players"));
                    totalKills = totalKills + Integer.parseInt(jsonArrayPlayers.getJSONObject(0).getString("TotalKills"));
                    totalDeath= totalDeath + Integer.parseInt(jsonArrayPlayers.getJSONObject(0).getString("TotalDeaths"));
                    totalAssist = totalAssist + Integer.parseInt(jsonArrayPlayers.getJSONObject(0).getString("TotalAssists"));

                } else {
                    Log.i(Constant.LOG_TAG, "Hopper ID does not exist " + jsonArray.getJSONObject(i).getString("HopperId"));
                }
            }
        } catch(Exception e) {
            Log.w(Constant.LOG_TAG, e);
        }
    }

    public Map getHashMap() {
        Map map = new HashMap<>();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        map.put("TotalGames", totalGames);
        map.put("TotalKills", totalKills);
        map.put("TotalAssist", totalAssist);
        map.put("TotalDeaths", totalDeath);
        map.put("KillPerGame", df.format((float) totalKills / (float) totalGames));
        map.put("AssistPerGame", df.format((float) totalAssist / (float) totalGames));
        map.put("DeathsPerGame", df.format((float) totalDeath / (float) totalGames));

        return map;
    }
}
