package com.example.astrand.footballfixtures.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.astrand.footballfixtures.R;

import com.example.astrand.footballfixtures.fragments.FixturesFragment;
import com.example.astrand.footballfixtures.fragments.LeagueTableFragment;



public class LeagueTableActivity extends AppCompatActivity {

    private TabLayout tableTab;
    private Button navBtnNeg, navBtnPos;
    private TextView matchdayCounterText;

    private int leagueID;
    private int matchday = - 1;
    private LeagueTableFragment leagueTableFragment = null;
    private FixturesFragment fixturesFragment = null;

    private final static int POS_TABLE_TAB = 0;
    private final static int POS_FIXTURE_TAB = 1;
    private static int lastLeagueId = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);

        if( savedInstanceState != null){
            leagueID = savedInstanceState.getInt("id");
            lastLeagueId = leagueID;

        }else if(getIntent().getExtras() != null) {
            int id = getIntent().getIntExtra("id", -1);

            if (id == -1) {
                return;
            }
            leagueID = id;
            lastLeagueId = leagueID;
        }else if(lastLeagueId > -1){
            leagueID = lastLeagueId;
        }else{
            throw new IllegalStateException("No leagueId found!");
        }
        startLeagueTableFragment(leagueID);

        setContentView(R.layout.activity_league_table);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar_league_table));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tableTab = (TabLayout) findViewById(R.id.tab_league_table);
        navBtnNeg = (Button)findViewById(R.id.matchday_nav_btn_1);
        navBtnPos = (Button)findViewById(R.id.matchday_nav_btn_2);
        matchdayCounterText = (TextView)findViewById(R.id.matchday_nav_count);


        setListeners();
        setNavBtnVisibility(false);
    }

    private void setListeners(){
        tableTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = getFragmentManager().findFragmentById(R.id.league_table_content);

                switch (tab.getPosition()){
                    case POS_TABLE_TAB:
                        if (fragment == null || !(fragment instanceof LeagueTableFragment)){
                            startLeagueTableFragment(leagueID);
                            setNavBtnVisibility(false);
                        }
                        break;
                    case POS_FIXTURE_TAB:
                        if (fragment == null || !(fragment instanceof FixturesFragment)){
                            LeagueTableFragment leagueTableFragment = (LeagueTableFragment) fragment;
                            int matchday = leagueTableFragment.getMatchday();
                            setMatchday(matchday);
                            startFixtureFragment(leagueID,matchday);
                            setNavBtnVisibility(true);
                        }
                        break;
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {} // not used

            @Override public void onTabReselected(TabLayout.Tab tab) {} //not used
        });

        navBtnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fixturesFragment != null){
                    setMatchday(++matchday);
                    fixturesFragment.updateMatchday(matchday);
                }
            }
        });

        navBtnNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMatchday(--matchday);
                fixturesFragment.updateMatchday(matchday);
            }
        });


    }

    private void startLeagueTableFragment(int id){
        if (leagueTableFragment == null) {
            leagueTableFragment = new LeagueTableFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        leagueTableFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.league_table_content,leagueTableFragment).commit();
    }

    private void startFixtureFragment(int leagueID, int matchday){
        Bundle bundle = new Bundle();
        bundle.putInt("id",leagueID);
        bundle.putInt("matchday",matchday);
        getFixturesFragment().setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.league_table_content,getFixturesFragment()).commit();
    }

    public void setMatchday(int matchday){
        this.matchday = matchday;
        matchdayCounterText.setText(getString(R.string.matchday) + " " + matchday);
    }

    private void setNavBtnVisibility(boolean visible){
        int vis = visible ? View.VISIBLE : View.GONE;
        navBtnNeg.setVisibility(vis);
        navBtnPos.setVisibility(vis);
        matchdayCounterText.setVisibility(vis);
    }

    private FixturesFragment getFixturesFragment(){
        if(fixturesFragment == null){
            fixturesFragment = new FixturesFragment();
        }

        return fixturesFragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("id",leagueID);
    }
}
