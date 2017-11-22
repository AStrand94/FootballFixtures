package com.example.astrand.footballfixtures.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.astrand.footballfixtures.rest_service.HttpErrorHandler;
import com.example.astrand.footballfixtures.adapters.FixtureAdapter;
import com.example.astrand.footballfixtures.entities.Fixture;
import com.example.astrand.footballfixtures.rest_service.FootballRestClientHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class FixturesFragment extends ListFragment {

    boolean listCreated = false;
    int leagueId, matchday;
    String fixturesLink;

    private Map<Integer,List<Fixture>> matchdayMap;

    public FixturesFragment(){
        matchdayMap = new HashMap<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!listCreated && getArguments() != null && (getArguments().containsKey("id") || getArguments().containsKey("matchday"))){
            leagueId = getArguments().getInt("id");
            matchday = getArguments().getInt("matchday");
            startPopulateList(leagueId, matchday);
        }else if (getArguments().containsKey("fixtures_link")){
            fixturesLink = getArguments().getString("fixtures_link");
            startPopulateList(fixturesLink);
        }
    }

    private void startPopulateList(String link){
        FootballRestClientHelper.get(link,jsonHttpResponseHandler);
    }

    private void startPopulateList(int id, final int matchday) {
        if (!matchdayMap.containsKey(matchday)) {
            FootballRestClientHelper.getFixtures(id, matchday, jsonHttpResponseHandler);
        }else{
            populateList(matchdayMap.get(matchday));
        }
    }

    private void populateList(List<Fixture> standings) {
        FixtureAdapter adapter = new FixtureAdapter(getActivity(),standings);
        setListAdapter(adapter);
        getListView().setDivider(getActivity().getResources().getDrawable(android.R.color.black));
        getListView().setDividerHeight(1);
    }


    public void updateMatchday(int matchday){
        this.matchday = matchday;
        startPopulateList(leagueId,matchday);
    }

    final JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("NEI");
                    listCreated = true;
                    //populateList(Fixture.create(response));
                }
            });
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Fixture> result = Fixture.create(response);
                        if (!matchdayMap.containsKey(matchday))
                            matchdayMap.put(matchday, result);
                        populateList(result);
                        listCreated = true;
                    }
                });
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            HttpErrorHandler.handle(getActivity(),throwable);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            HttpErrorHandler.handle(getActivity(),throwable);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            HttpErrorHandler.handle(getActivity(),throwable);
        }
    };
}
