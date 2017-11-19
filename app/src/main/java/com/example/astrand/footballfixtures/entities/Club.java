package com.example.astrand.footballfixtures.entities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Club implements Serializable {

    private String clubName;

    private String shortClubName;

    private String clubCode;

    private String crestUrl;

    private String selfLink;

    private String fixturesLink;

    private String playersLink;

    public String getSelfLink() {
        return selfLink;
    }

    public String getFixturesLink() {
        return fixturesLink;
    }

    public String getPlayersLink() {
        return playersLink;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getShortClubName() {
        return shortClubName;
    }

    public void setShortClubName(String shortClubName) {
        this.shortClubName = shortClubName;
    }

    public String getClubCode() {
        return clubCode;
    }

    public void setClubCode(String clubCode) {
        this.clubCode = clubCode;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public void setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
    }

    public static Club create(JSONObject jsonObject){
        try{
            return Creator.create(jsonObject);
        }catch (JSONException e){
            return null;
        }
    }

    private static class Creator {

        static Club create(JSONObject jsonObject) throws JSONException{
            Club club = new Club();

            if (jsonObject.has("name"))
                club.clubName = jsonObject.getString("name");

            if (jsonObject.has("code"))
                club.clubCode = jsonObject.getString("code");

            if (jsonObject.has("shortName"))
                club.shortClubName = jsonObject.getString("shortName");

            if (jsonObject.has("crestUrl"))
                club.crestUrl = jsonObject.getString("crestUrl");

            if (jsonObject.has("_links")){
                JSONObject links = jsonObject.getJSONObject("_links");

                if (links.has("self"))
                    club.selfLink = links.getJSONObject("self").getString("href");

                if (links.has("fixtures"))
                    club.fixturesLink = links.getJSONObject("fixtures").getString("href");

                if (links.has("players"))
                    club.playersLink = links.getJSONObject("players").getString("href");

            }

            return club;
        }
    }
}
