package com.example.astrand.footballfixtures.entities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private String name;

    private String code;

    private String shortName;

    private String crestURL;

    private String selfLink;

    private String fixturesLink;

    private String playersLink;

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getShortName() {
        return shortName;
    }

    public String getCrestURL() {
        return crestURL;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public String getFixturesLink() {
        return fixturesLink;
    }

    public String getPlayersLink() {
        return playersLink;
    }

    public static List<Team> create(JSONArray jsonArray){
        return Creator.create(jsonArray);
    }

    private static class Creator{

        static List<Team> create(JSONArray jsonArray){
            List<Team> list = new ArrayList<>();

            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    Object o = jsonArray.get(i);
                    if (o instanceof JSONObject) {
                        list.add(
                                create((JSONObject) o)
                        );
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            return list;
        }

        static Team create(JSONObject jsonObject) throws JSONException{

            Team team = new Team();

            if (jsonObject.has("name"))
                team.name = jsonObject.getString("name");

            if (jsonObject.has("code"))
                team.code = jsonObject.getString("code");

            if (jsonObject.has("shortName"))
                team.shortName = jsonObject.getString("shortName");

            if (jsonObject.has("creatUrl"))
                team.crestURL = jsonObject.getString("creatUrl");

            if (jsonObject.has("_links")){

                JSONObject links = jsonObject.getJSONObject("_links");

                if (links.has("self"))
                    team.selfLink = links.getString("self");

                if (links.has("fixtures"))
                    team.fixturesLink = links.getString("fixtures");

                if (links.has("players"))
                    team.playersLink = links.getString("players");

            }

            return team;
        }
    }
}
