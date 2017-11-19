package com.example.astrand.footballfixtures.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.activities.MainActivity;
import com.example.astrand.footballfixtures.adapters.FixtureAdapter;
import com.example.astrand.footballfixtures.entities.Fixture;
import com.example.astrand.footballfixtures.rest_service.FootballRestClientHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;


public class TodaysFixturesFragment extends ListFragment {

    private static final String todaysFixturesLink = "http://api.football-data.org/v1/fixtures?timeFrame=n1";

    final JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("NEI");
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
                        populateList(response);
                    }
                });
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Log.d("LeagueTableActivity", "EXCEPTION OCCURRED: " + throwable.toString());
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.d("LeagueTableActivity", "EXCEPTION OCCURRED: " + throwable.toString());
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            Log.d("LeagueTableActivity", "EXCEPTION OCCURRED: " + throwable.toString());
        }
    };

    private void populateList(JSONObject response) {
        List<Fixture> fixtures = Fixture.create(response);
        if (fixtures != null) {
            FixtureAdapter adapter = new FixtureAdapter(getContext(), fixtures);
            setListAdapter(adapter);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof MainActivity) ((MainActivity)getActivity()).setToolbarTitle(getString(R.string.today_fixtures_title));
        startPopulateList();
    }

    private void startPopulateList() {
        FootballRestClientHelper.getTodaysFixtures(jsonHttpResponseHandler);
    }
}
