package com.example.astrand.footballfixtures.rest_service;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class FootballRestClientHelper {

    private static final String BASE_URL = "http://api.football-data.org/v1";
    private static final String COMPETITION_URL = "/competitions";
    private static final String LEAGUE_TABLE_URL = "/leagueTable";
    private static final String FIXTURE_URL = "/fixtures";
    private static final String TEAM_URL = "/teams";
    private static final String todaysFixturesLink = "http://api.football-data.org/v1/fixtures?timeFrame=n1";


    private static final RequestParams params = new RequestParams();

    private FootballRestClientHelper(){}

    public static void getTodaysFixtures(JsonHttpResponseHandler jsonHttpResponseHandler){
        get(todaysFixturesLink,jsonHttpResponseHandler);
    }

    public static void getCompetitions(JsonHttpResponseHandler jsonHttpResponseHandler){
        RestClient.get(getCompetitionUrl(), params, jsonHttpResponseHandler);
    }

    public static void getLeagueTable(int id, JsonHttpResponseHandler jsonHttpResponseHandler){
        RestClient.get(getLeagueTableURL(id), params, jsonHttpResponseHandler);
    }

    public static void getFixtures(int leagueId, int matchday, JsonHttpResponseHandler jsonHttpResponseHandler){
        RestClient.get(getFixturesUrl(leagueId,matchday),params,jsonHttpResponseHandler);
    }

    public static void getClub(int clubId, JsonHttpResponseHandler jsonHttpResponseHandler){
        RestClient.get(getClubInfoUrl(clubId),params,jsonHttpResponseHandler);
    }

    public static void get(String url, AsyncHttpResponseHandler asyncHttpResponseHandler){
        RestClient.get(url, params, asyncHttpResponseHandler);
    }

    private static String getLeagueTableURL(int id){
        return BASE_URL + COMPETITION_URL + "/" + id + LEAGUE_TABLE_URL;
    }

    private static String getCompetitionUrl(){
        return BASE_URL + COMPETITION_URL;
    }

    private static String getFixturesUrl(int leagueId, int matchday){
        return getCompetitionUrl() + "/" + leagueId + FIXTURE_URL +  "?matchday=" + matchday;
    }

    private static String getClubInfoUrl(int clubId){
        return BASE_URL + TEAM_URL + "/" + clubId;
    }
}
