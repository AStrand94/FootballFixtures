package com.example.astrand.footballfixtures.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.activities.ChampionsLeagueActivity;
import com.example.astrand.footballfixtures.activities.LeagueTableActivity;
import com.example.astrand.footballfixtures.activities.MainActivity;
import com.example.astrand.footballfixtures.adapters.CompetitionAdapter;
import com.example.astrand.footballfixtures.entities.Competition;
import com.example.astrand.footballfixtures.rest_service.FootballRestClientHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class CompetitionFragment extends ListFragment {

    List<Competition> competitions;
    private boolean listCreated = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (competitions != null){
            populateList(competitions);
        }else if (savedInstanceState != null && savedInstanceState.containsKey("competitions") && listCreated){
            populateList((List<Competition>) savedInstanceState.getSerializable("competitions"));
        }else {
            startPopulateList();
        }

        if (getActivity() instanceof MainActivity) ((MainActivity)getActivity()).setToolbarTitle(getString(R.string.competitions_title));
    }

    private void startPopulateList() {
        FootballRestClientHelper.getCompetitions(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateList(Competition.create(response));
                            listCreated = true;
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("CompetitionFragment","EXCEPTION OCCURRED: " + throwable.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("CompetitionFragment","EXCEPTION OCCURRED: " + throwable.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("CompetitionFragment","EXCEPTION OCCURRED: " + throwable.toString());
            }
        });
    }

    private void populateList(List<Competition> competitions){
        this.competitions = competitions;
        CompetitionAdapter adapter = new CompetitionAdapter(getContext(), R.layout.list_item_competition,competitions);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Competition competition = (Competition) getListAdapter().getItem(position);
        int competitionId = competition.getId();
        Intent intent;

        if (competition.getLeague().equals("CL")){
            intent = new Intent(getActivity(), ChampionsLeagueActivity.class);
            intent.putExtra("id", competitionId);
        }else {
            intent = new Intent(getActivity(), LeagueTableActivity.class);
            intent.putExtra("id", competitionId);

        }
        startActivity(intent);
    }
}
