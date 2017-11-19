package com.example.astrand.footballfixtures.activities;

import android.app.Fragment;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.fragments.CompetitionFragment;
import com.example.astrand.footballfixtures.fragments.FavoriteClubsFragment;
import com.example.astrand.footballfixtures.fragments.TodaysFixturesFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView toggleDrawerImage;
    TextView toolbarTitle;
    static CompetitionFragment competitionFragment;
    static FavoriteClubsFragment favoriteClubsFragment;
    static TodaysFixturesFragment todaysFixturesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);setTitle(null);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        toggleDrawerImage = (ImageView) findViewById(R.id.toggleImage);
        navigationView.setNavigationItemSelectedListener(this);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title_textview);

        startFragment(getCompetitionFragment());

        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        toggleDrawerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        navigationView.setCheckedItem(item.getGroupId());
        item.setChecked(true);
        //setTitle(item.getTitle());

        switch (item.getItemId()){
            case R.id.item_competition:
                startFragment(getCompetitionFragment());
                break;
            case R.id.favorite_clubs:
                startFragment(getFavoriteClubsFragment());
                break;
            case R.id.todays_fixtures:
                startFragment(getTodaysFixturesFragment());

        }
        return false;
    }

    private TodaysFixturesFragment getTodaysFixturesFragment() {
        if (todaysFixturesFragment == null){
            todaysFixturesFragment = new TodaysFixturesFragment();
        }

        return todaysFixturesFragment;
    }

    private CompetitionFragment getCompetitionFragment(){
        if (competitionFragment == null){
            competitionFragment = new CompetitionFragment();
        }

        return competitionFragment;
    }

    private FavoriteClubsFragment getFavoriteClubsFragment(){
        if (favoriteClubsFragment == null){
            favoriteClubsFragment = new FavoriteClubsFragment();
        }
        return favoriteClubsFragment;
    }

    private void startFragment(Fragment fragment){

        if (fragment.isAdded()) {
            getFragmentManager().beginTransaction().show(fragment).commit();
        }else {
            getFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            drawerLayout.closeDrawers();
        }
    }

    public void setToolbarTitle(String title){
        if (toolbarTitle != null) //debug purposes
            toolbarTitle.setText(title);
    }
}
