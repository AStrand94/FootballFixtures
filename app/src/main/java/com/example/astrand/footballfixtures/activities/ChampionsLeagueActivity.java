package com.example.astrand.footballfixtures.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.example.astrand.footballfixtures.rest_service.HttpErrorHandler;
import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.adapters.ChampionsLeagueAdapter;
import com.example.astrand.footballfixtures.entities.cl_entities.ChampionsLeague;
import com.example.astrand.footballfixtures.rest_service.FootballRestClientHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ChampionsLeagueActivity extends AppCompatActivity {

    private int competitionId;
    private ListView listView;

    private JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, final JSONArray response) {
            Log.d("ChampionsLeagueActivity", "not supposed to happen?");
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            populateList(ChampionsLeague.create(response));
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            HttpErrorHandler.handle(ChampionsLeagueActivity.this,throwable);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            HttpErrorHandler.handle(ChampionsLeagueActivity.this,throwable);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            HttpErrorHandler.handle(ChampionsLeagueActivity.this,throwable);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.competitionId = getIntent().getIntExtra("id",464);

        setContentView(R.layout.activity_cl);

        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar_cl));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Champions League");
        listView = (ListView) findViewById(R.id.cl_listview);

        startPopulateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
    }

    private void startPopulateList() {
        FootballRestClientHelper.getLeagueTable(competitionId,jsonHttpResponseHandler);
    }

    private void populateList(ChampionsLeague championsLeague) {
        listView.setAdapter(new ChampionsLeagueAdapter(this, R.layout.list_item_league_table,championsLeague.getChampionsLeagueIFs()));
    }
}
