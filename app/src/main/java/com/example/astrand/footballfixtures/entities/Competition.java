package com.example.astrand.footballfixtures.entities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Competition {

    private int id;

    private String caption;

    private String league;

    private String year;

    private int currentMatchday;

    private int numberOfMatchDays;

    private int numberOfTeams;

    private int numberOfGames;

    private Date lastUpdated;

    private String selfLink;

    private String teamsLink;

    private String fixturesLink;

    private String leagueTableLink;

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public String getLeague() {
        return league;
    }

    public String getYear() {
        return year;
    }

    public int getCurrentMatchday() {
        return currentMatchday;
    }

    public int getNumberOfMatchDays() {
        return numberOfMatchDays;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public String getTeamsLink() {
        return teamsLink;
    }

    public String getFixturesLink() {
        return fixturesLink;
    }

    public String getLeagueTableLink() {
        return leagueTableLink;
    }

    public static List<Competition> create(JSONArray jsonArray){
        return Creator.create(jsonArray);
    }

    private static class Creator{

        static List<Competition> create(JSONArray jsonArray){
            List<Competition> list = new ArrayList<>();

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

        static Competition create(JSONObject jsonObject) throws JSONException{

            Competition competition = new Competition();

            if (jsonObject.has("id"))
                competition.id = jsonObject.getInt("id");

            if (jsonObject.has("caption"))
                competition.caption = jsonObject.getString("caption");

            if (jsonObject.has("league"))
                competition.league = jsonObject.getString("league");

            if (jsonObject.has("year"))
                competition.year = jsonObject.getString("year");

            if (jsonObject.has("currentMatchday"))
                competition.currentMatchday = jsonObject.getInt("currentMatchday");

            if (jsonObject.has("numberOfMatchdays"))
                competition.numberOfMatchDays = jsonObject.getInt("numberOfMatchdays");

            if (jsonObject.has("numberOfTeams"))
                competition.numberOfTeams = jsonObject.getInt("numberOfTeams");

            if (jsonObject.has("numberOfGames"))
                competition.numberOfGames = jsonObject.getInt("numberOfGames");

            try {
                if (jsonObject.has("lastUpdated"))
                    competition.lastUpdated = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMAN).parse(jsonObject.getString("lastUpdated"));//GERMAN OK ??
            }catch (ParseException e){
                e.printStackTrace();
            }

            if (jsonObject.has("_links")){

                JSONObject links = jsonObject.getJSONObject("_links");

                if (links.has("self"))
                    competition.selfLink = links.getString("self");

                if (links.has("teams"))
                    competition.teamsLink = links.getString("teams");

                if (links.has("fixtures"))
                    competition.fixturesLink = links.getString("fixtures");

                if (links.has("leagueTable"))
                    competition.leagueTableLink = links.getString("leagueTable");
            }

            return competition;
        }

    }
}
