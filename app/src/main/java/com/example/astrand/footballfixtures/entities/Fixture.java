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

public class Fixture {

    private Date date;

    private String status;

    private int matchday;

    private String homeTeamName;

    private String awayTeamName;

    private Result result;

    private String selfLink;

    private String competitionLink;

    private String homeTeamLink;

    private String awayTeamLink;

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getMatchday() {
        return matchday;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public Result getResult() {
        return result;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public String getCompetitionLink() {
        return competitionLink;
    }

    public String getHomeTeamLink() {
        return homeTeamLink;
    }

    public String getAwayTeamLink() {
        return awayTeamLink;
    }

    public static List<Fixture> create(JSONObject jsonObject){
        if (jsonObject.has("fixtures")){
            try {
                return Creator.create(jsonObject.getJSONArray("fixtures"));
            }catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    private static class Creator{

        static List<Fixture> create(JSONArray jsonArray){
            List<Fixture> list = new ArrayList<>();

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

        static Fixture create(JSONObject jsonObject) throws JSONException{
            Fixture fixture = new Fixture();

            try {
                if (jsonObject.has("date")) {
                    fixture.date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMAN).parse(jsonObject.getString("date"));
                }
            }catch (ParseException pe){
                pe.printStackTrace();
            }

            if (jsonObject.has("status"))
                fixture.status = jsonObject.getString("status");

            if(jsonObject.has("matchday"))
                fixture.matchday = jsonObject.getInt("matchday");

            if (jsonObject.has("homeTeamName"))
                fixture.homeTeamName = jsonObject.getString("homeTeamName");

            if (jsonObject.has("awayTeamName"))
                fixture.awayTeamName = jsonObject.getString("awayTeamName");

            if (jsonObject.has("result") && !jsonObject.getJSONObject("result").isNull("goalsHomeTeam") && !jsonObject.getJSONObject("result").isNull("goalsAwayTeam")){

                JSONObject results = jsonObject.getJSONObject("result");

                fixture.result = new Result(results.getInt("goalsHomeTeam"), results.getInt("goalsAwayTeam"));

                if (results.has("halfTime") && results.getJSONObject("halfTime").has("goalsHomeTeam") && results.getJSONObject("halfTime").has("goalsAwayTeam")) {
                    fixture.result.setGoalsHomeTeamHalfTime(results.getJSONObject("halfTime").getInt("goalsHomeTeam"));
                    fixture.result.setGoalsAwayTeamHalfTime(results.getJSONObject("halfTime").getInt("goalsAwayTeam"));
                }
            }

            if (jsonObject.has("_links")){

                JSONObject links = jsonObject.getJSONObject("_links");

                if (links.has("self"))
                    fixture.selfLink = links.getString("self");

                if (links.has("competition"))
                    fixture.competitionLink = links.getString("competition");

                if (links.has("homeTeam"))
                    fixture.homeTeamLink = links.getString("homeTeam");

                if (links.has("awayTeam"))
                    fixture.awayTeamLink = links.getString("awayTeam");
            }

            return fixture;
        }

    }
}
