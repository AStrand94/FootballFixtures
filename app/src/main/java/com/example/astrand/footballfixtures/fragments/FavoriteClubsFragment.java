package com.example.astrand.footballfixtures.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.example.astrand.footballfixtures.rest_service.HttpErrorHandler;
import com.example.astrand.footballfixtures.helpers.FavoriteClubHelper;
import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.activities.ClubFixturesActivity;
import com.example.astrand.footballfixtures.activities.MainActivity;
import com.example.astrand.footballfixtures.adapters.FavoriteClubsAdapter;
import com.example.astrand.footballfixtures.entities.Club;
import com.example.astrand.footballfixtures.rest_service.FootballRestClientHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.Header;


public class FavoriteClubsFragment extends ListFragment {

    private static Set<Club> favoriteClubs = new HashSet<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof MainActivity) ((MainActivity)getActivity()).setToolbarTitle(getString(R.string.favorite_clubs_title));
        Set<String> favorites = FavoriteClubHelper.getAllFavorites(getContext());

        if (favorites.isEmpty()){
            //TODO: Handle empty list
        }else if(equals(favorites,favoriteClubs)){
            populateList();
        }else{
            favoriteClubs.clear();
            startPopulateList(favorites);
        }
    }

    private void startPopulateList(final Set<String> favorites){
        for (String s : favorites){

            FootballRestClientHelper.get(s, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    favoriteClubs.add(Club.create(response));

                    if (favoriteClubs.size() == favorites.size()) populateList();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    super.onSuccess(statusCode, headers, responseString);
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
            });
        }
    }

    private void populateList() {
        setListAdapter(new FavoriteClubsAdapter(getContext(),
                R.layout.list_item_competition,
                Arrays.asList(favoriteClubs.toArray(new Club[favoriteClubs.size()]))));

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Club club = (Club)getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(),ClubFixturesActivity.class);
        intent.putExtra("fixtures_link",club.getFixturesLink());
        intent.putExtra("self_link",club.getSelfLink());
        intent.putExtra("club_name",club.getClubName());
        getActivity().startActivity(intent);
    }

    private boolean equals(Set<String> links, Set<Club> clubs){

        for (Club club : clubs){
            if (!links.contains(club.getSelfLink())){
                return false;
            }
        }

        return links.size() == clubs.size();
    }
}
