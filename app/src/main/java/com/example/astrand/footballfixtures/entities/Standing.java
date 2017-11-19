package com.example.astrand.footballfixtures.entities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Standing {

    private int position;

    private String teamName;

    private String crestUrl;

    private int playedGames;

    private int points;

    private int goals;

    private int goalsAgainst;

    private int goalDifference;

    private int wins;

    private int draws;

    private int losses;

    private String teamLink;

    public int getPosition() {
        return position;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public int getPoints() {
        return points;
    }

    public int getGoals() {
        return goals;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public String getTeamLink() {
        return teamLink;
    }

    public static List<Standing> create(JSONArray jsonArray){
        return Creator.create(jsonArray);
    }

    public static List<Standing> create(JSONObject jsonObject){
        try {
            if (jsonObject.has("standing")) return create(jsonObject.getJSONArray("standing"));
            else return null;
        }catch (JSONException e){
            return null;
        }
    }

    private static class Creator{

        static List<Standing> create(JSONArray jsonArray){
            List<Standing> list = new ArrayList<>();

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

        static Standing create(JSONObject jsonObject) throws JSONException{

            Standing standing = new Standing();

            if (jsonObject.has("position"))
                standing.position = jsonObject.getInt("position");

            if (jsonObject.has("teamName"))
                standing.teamName = jsonObject.getString("teamName");

            if (jsonObject.has("crestURI"))
                standing.crestUrl = jsonObject.getString("crestURI");

            if (jsonObject.has("playedGames"))
                standing.playedGames = jsonObject.getInt("playedGames");

            if (jsonObject.has("points"))
                standing.points = jsonObject.getInt("points");

            if (jsonObject.has("goals"))
                standing.goals = jsonObject.getInt("goals");

            if (jsonObject.has("goalsAgainst"))
                standing.goalsAgainst = jsonObject.getInt("goalsAgainst");

            if (jsonObject.has("goalDifference"))
                standing.goalDifference = jsonObject.getInt("goalDifference");

            if (jsonObject.has("wins"))
                standing.wins = jsonObject.getInt("wins");

            if (jsonObject.has("draws"))
                standing.draws = jsonObject.getInt("draws");

            if (jsonObject.has("losses"))
                standing.losses = jsonObject.getInt("losses");

            if (jsonObject.has("_links")){

                JSONObject links = jsonObject.getJSONObject("_links");

                if (links.has("team"))
                    standing.teamLink = links.getJSONObject("team").getString("href");
            }

            return standing;
        }
    }
}
