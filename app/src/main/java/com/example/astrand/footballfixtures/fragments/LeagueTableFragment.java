package com.example.astrand.footballfixtures.fragments;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.activities.ClubInfoActivity;
import com.example.astrand.footballfixtures.adapters.LeagueTableAdapter;
import com.example.astrand.footballfixtures.entities.LeagueTable;
import com.example.astrand.footballfixtures.entities.Standing;
import com.example.astrand.footballfixtures.rest_service.FootballRestClientHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LeagueTableFragment extends ListFragment {

    private int matchday;
    private boolean listCreated = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!listCreated) startPopulateList(getArguments().getInt("id"));
    }

    private void startPopulateList(int id) {
        FootballRestClientHelper.getLeagueTable(id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateList(Standing.create(response));
                            listCreated = true;
                        }
                    });
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,final JSONObject response) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateList(LeagueTable.create(response));
                            listCreated = true;
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("LeagueTableActivity","EXCEPTION OCCURRED: " + throwable.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("LeagueTableActivity","EXCEPTION OCCURRED: " + throwable.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("LeagueTableActivity","EXCEPTION OCCURRED: " + throwable.toString());
            }
        });
    }

    private void populateList(LeagueTable leagueTable){
        getActivity().setTitle(leagueTable.getLeagueCaption());
        matchday = leagueTable.getMatchDay();
        populateList(leagueTable.getStandings());
    }

    private void populateList(List<Standing> standings) {
        LeagueTableAdapter adapter = new LeagueTableAdapter(getActivity(), R.layout.activity_league_table,standings);
        setListAdapter(adapter);
        getListView().setDivider(getActivity().getResources().getDrawable(android.R.color.black));
        getListView().setDividerHeight(1);
    }

    public int getMatchday() {
        return matchday;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Standing standing = (Standing)getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), ClubInfoActivity.class);
        intent.putExtra("selfLink",standing.getTeamLink());

        getActivity().startActivity(intent);

    }
}
