package com.example.astrand.footballfixtures.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.fragments.FixturesFragment;


public class ClubFixturesActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    FixturesFragment fixturesFragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_layout);

        String link = getIntent().getStringExtra("fixtures_link");
        if (link == null || link.isEmpty())
            throw new Resources.NotFoundException("No link found");

        frameLayout = (FrameLayout) findViewById(R.id.basic_frame);
        toolbar = (Toolbar) findViewById(R.id.basic_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startFragment(link);
    }

    private void startFragment(String link) {
        fixturesFragment = new FixturesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fixtures_link",link);
        fixturesFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.basic_frame,fixturesFragment).commit();
    }


}
