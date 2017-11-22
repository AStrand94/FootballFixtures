package com.example.astrand.footballfixtures.activities;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.fragments.ClubInfoFragment;
import com.example.astrand.footballfixtures.fragments.FixturesFragment;


public class ClubFixturesActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    FixturesFragment fixturesFragment;
    ClubInfoFragment clubInfoFragment;
    Toolbar toolbar;
    private TabLayout tabLayout;
    private String selfLink;
    private String fixturesLink;
    private String clubName;

    private static final String FIXTURES_LINK = "fixtures_link";
    private static final String SELF_LINK = "self_link";

    private static final int TAB_FIXTURES = 0;
    private static final int TAB_INFO = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_layout);

        fixturesLink = getIntent().getStringExtra("fixtures_link");
        selfLink = getIntent().getStringExtra("self_link");

        frameLayout = (FrameLayout) findViewById(R.id.basic_frame);
        toolbar = (Toolbar) findViewById(R.id.basic_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.club_info_tab_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addListeners();
        clubName = getIntent().getStringExtra("club_name");
        setTitle(clubName);

        if (fixturesLink == null){
            startClubInfoFragment(selfLink);
        }else {
            startFixturesFragment(fixturesLink);
        }
    }

    private void addListeners(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment fragment = getFragmentManager().findFragmentById(R.id.basic_frame);

                switch (tab.getPosition()){
                    case TAB_FIXTURES:
                        if (fragment == null || !(fragment instanceof FixturesFragment)){
                            startFixturesFragment(fixturesLink);
                        }
                        break;
                    case TAB_INFO:
                        if (fragment == null || !(fragment instanceof ClubInfoFragment)){
                            startClubInfoFragment(selfLink);
                        }
                        break;
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {} // not used

            @Override public void onTabReselected(TabLayout.Tab tab) {} //not used
        });
    }

    private void startFixturesFragment(String link) {
        tabLayout.getTabAt(TAB_FIXTURES).select();
        fixturesFragment = getFixturesFragment();
        fixturesFragment.setArguments(getBundle(FIXTURES_LINK,link));
        getFragmentManager().beginTransaction().replace(R.id.basic_frame,fixturesFragment).commit();
    }

    private void startClubInfoFragment(String link){
        tabLayout.getTabAt(TAB_INFO).select();
        clubInfoFragment = getClubInfoFragment();
        clubInfoFragment.setArguments(getBundle(SELF_LINK,link));
        getFragmentManager().beginTransaction().replace(R.id.basic_frame,clubInfoFragment).commit();
    }

    private Bundle getBundle(String key, String link){
        Bundle bundle = new Bundle();
        bundle.putString(key,link);
        return bundle;
    }

    private FixturesFragment getFixturesFragment(){
        if (fixturesFragment == null) fixturesFragment = new FixturesFragment();

        return fixturesFragment;
    }

    private ClubInfoFragment getClubInfoFragment(){
        if (clubInfoFragment == null) clubInfoFragment = new ClubInfoFragment();

        return clubInfoFragment;
    }

    public void setFixturesLink(String link){
        fixturesLink = link;
    }

}
