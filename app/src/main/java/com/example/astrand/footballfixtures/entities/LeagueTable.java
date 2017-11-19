package com.example.astrand.footballfixtures.entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LeagueTable {

    private String leagueCaption;

    private int matchDay;

    private List<Standing> standings;

    public void setLeagueCaption(String leagueCaption) {
        this.leagueCaption = leagueCaption;
    }

    public void setStandings(List<Standing> standings) {
        this.standings = standings;
    }

    public String getLeagueCaption() {
        return leagueCaption;
    }

    public List<Standing> getStandings() {
        return standings;
    }

    public int getMatchDay() {
        return matchDay;
    }

    public static LeagueTable create(JSONObject jsonObject){
        if (jsonObject.has("standing")){
            LeagueTable leagueTable = null;
            try {
                leagueTable = new LeagueTable();
                leagueTable.leagueCaption = jsonObject.getString("leagueCaption");
                leagueTable.matchDay = jsonObject.getInt("matchday");
                leagueTable.standings = Standing.create(jsonObject.getJSONArray("standing"));
            }catch (JSONException e){
                e.printStackTrace();
            }
            return leagueTable;
        }

        return null;
    }
}
