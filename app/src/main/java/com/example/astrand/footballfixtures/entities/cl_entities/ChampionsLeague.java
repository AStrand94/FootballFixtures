package com.example.astrand.footballfixtures.entities.cl_entities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChampionsLeague {

    private final static String[] GROUPS = {"A","B","C","D","E","F","G","H"};

    private List<ChampionsLeagueIF> championsLeagueIFs;

    private String caption;

    private int matchday;

    public List<ChampionsLeagueIF> getChampionsLeagueIFs() {
        return championsLeagueIFs;
    }

    public void setChampionsLeagueIFs(List<ChampionsLeagueIF> championsLeagueIFs) {
        this.championsLeagueIFs = championsLeagueIFs;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

    public static ChampionsLeague create(JSONObject jsonObject){

        try{
            return Creator.createCL(jsonObject);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private static class Creator{

        static ChampionsLeague createCL(JSONObject jsonObject) throws JSONException{
            if (!jsonObject.has("standings")) throw new JSONException("Invalid JsonObject");
            ChampionsLeague cl = new ChampionsLeague();

            if (jsonObject.has("leagueCaption"))
                cl.caption = jsonObject.getString("leagueCaption");

            if (jsonObject.has("matchday"))
                cl.matchday = jsonObject.getInt("matchday");

            JSONObject groups = jsonObject.getJSONObject("standings");

            List<ChampionsLeagueIF> list = new ArrayList<>();

            for (String s : GROUPS){
                ChampionsLeagueGroup championsLeagueGroup = new ChampionsLeagueGroup();
                championsLeagueGroup.setGroup(s);
                list.add(championsLeagueGroup);

                JSONArray teamsInGroup = groups.getJSONArray(s);

                for (int i = 0; i < teamsInGroup.length(); i++){
                    ChampionsLeagueStanding standing = ChampionsLeagueStanding.create(teamsInGroup.getJSONObject(i));
                    if (standing != null) list.add(standing);
                }
            }

            cl.setChampionsLeagueIFs(list);

            return cl;
        }
    }
}
