package com.example.astrand.footballfixtures.entities.cl_entities;

import com.example.astrand.footballfixtures.entities.Fixture;

import org.json.JSONException;
import org.json.JSONObject;


public class ChampionsLeagueStanding implements ChampionsLeagueIF {

    private String group;

    private int rank;

    private String team;

    private int teamId;

    private int playedGames;

    private String crestURI;

    private int points;

    private int goals;

    private int goalsAgainst;

    private int goalsDifference;

    public String getGroup() {
        return group;
    }

    public int getRank() {
        return rank;
    }

    public String getTeam() {
        return team;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public String getCrestURI() {
        return crestURI;
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

    public int getGoalsDifference() {
        return goalsDifference;
    }

    public static ChampionsLeagueStanding create(JSONObject jsonObject){
        try{
            return Creator.create(jsonObject);
        }catch (JSONException e){
            return null;
        }
    }

    private static class Creator{

        static ChampionsLeagueStanding create(JSONObject jsonObject) throws JSONException{
            ChampionsLeagueStanding cl = new ChampionsLeagueStanding();

            if (jsonObject.has("group")) cl.group = jsonObject.getString("group");
            if (jsonObject.has("rank")) cl.rank = jsonObject.getInt("rank");
            if (jsonObject.has("team")) cl.team = jsonObject.getString("team");
            if (jsonObject.has("playedGames")) cl.playedGames = jsonObject.getInt("playedGames");
            if (jsonObject.has("crestURI")) cl.crestURI = jsonObject.getString("crestURI");
            if (jsonObject.has("points")) cl.points = jsonObject.getInt("points");
            if (jsonObject.has("goals")) cl.goals = jsonObject.getInt("goals");
            if (jsonObject.has("goalsAgainst")) cl.goalsAgainst = jsonObject.getInt("goalsAgainst");
            if (jsonObject.has("goalDifference")) cl.goalsDifference = jsonObject.getInt("goalDifference");

            return cl;

        }

    }
}
