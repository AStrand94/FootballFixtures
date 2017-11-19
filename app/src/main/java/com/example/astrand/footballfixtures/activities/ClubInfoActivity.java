package com.example.astrand.footballfixtures.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;
import com.example.astrand.footballfixtures.FavoriteClubHelper;
import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.entities.Club;
import com.example.astrand.footballfixtures.rest_service.FootballRestClientHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ClubInfoActivity extends AppCompatActivity {

    SVGImageView clubLogo;
    TextView clubnameLong, clubnameShort;
    Switch favouriteSwitch;
    private String selfLink;
    private Club club;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_club_info);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar_club));

        clubLogo = (SVGImageView) findViewById(R.id.image_club_logo);
        clubLogo.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        clubnameLong = (TextView) findViewById(R.id.club_name_long_text);
        clubnameShort = (TextView) findViewById(R.id.club_name_short_text);
        favouriteSwitch = (Switch) findViewById(R.id.favourite_switch);

        initListeners();

        if(getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (savedInstanceState != null){
            selfLink = savedInstanceState.getString("selfLink");
        }else {
            selfLink = intent.getStringExtra("selfLink");
        }

        favouriteSwitch.setChecked(FavoriteClubHelper.isFavorite(this,selfLink));

        createClubView();
    }

    private void initListeners(){
        favouriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FavoriteClubHelper.updateFavoriteClub(ClubInfoActivity.this,isChecked,selfLink);
            }
        });
    }

    private void createClubView() {

        if (selfLink == null) throw new IllegalArgumentException("No argument provided for selfLink");

        FootballRestClientHelper.get(selfLink,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,final JSONObject response) {
                createClubView(Club.create(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                error();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                error();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                error();
            }
        });
    }

    private void error(){
        String errTxt = getString(R.string.error_try_again);
        clubnameLong.setText(errTxt);
        clubnameShort.setText(errTxt);
    }

    private void createClubView(Club club){
        this.club = club;

        clubnameLong.setText(club.getClubName());
        clubnameShort.setText(club.getClubCode());

        String crestUrl = club.getCrestUrl();
        if(crestUrl != null && !crestUrl.isEmpty()) {

            if (crestUrl.endsWith(".svg")){
                loadCrestImageSVG(crestUrl);
            }else if (crestUrl.endsWith(".png")){
                loadCrestImagePNG(crestUrl);
            }
        }
    }

    private void loadCrestImagePNG(String crestUrl) {
        Picasso.with(getApplicationContext()).load(crestUrl).into(clubLogo);
    }

    private void loadCrestImageSVG(String imageUrl){
        FootballRestClientHelper.get(imageUrl, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                loadCrestImageFromSvg(responseString);
            }
        });
    }

    private void loadCrestImageFromSvg(String svg){
        try {
            SVG image = SVG.getFromString(svg);
            clubLogo.setSVG(image);
            //Drawable drawable = new PictureDrawable(image.renderToPicture());
            //updateImageView(drawable);
        }catch (SVGParseException e ){
            Log.d("ClubInfoActivity:","Error parsing SVG: " + e.toString());
            e.printStackTrace();
        }
    }

    private void updateImageView(Drawable drawable){
        clubLogo.setImageDrawable(drawable);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selfLink",selfLink);
    }
}
