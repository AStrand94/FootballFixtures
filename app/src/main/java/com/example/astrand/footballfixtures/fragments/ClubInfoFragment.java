package com.example.astrand.footballfixtures.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;
import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.activities.ClubFixturesActivity;
import com.example.astrand.footballfixtures.entities.Club;
import com.example.astrand.footballfixtures.helpers.FavoriteClubHelper;
import com.example.astrand.footballfixtures.rest_service.FootballRestClientHelper;
import com.example.astrand.footballfixtures.rest_service.HttpErrorHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ClubInfoFragment extends Fragment {

    SVGImageView clubLogo;
    TextView clubnameLong, clubnameShort;
    Switch favouriteSwitch;
    private String selfLink;
    private Club club;
    View currView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (currView != null) return currView;
        View view = inflater.inflate(R.layout.club_info_fragment,null,false);

        clubLogo = view.findViewById(R.id.image_club_logo);
        clubLogo.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        clubnameLong = view.findViewById(R.id.club_name_long_text);
        clubnameShort = view.findViewById(R.id.club_name_short_text);
        favouriteSwitch = view.findViewById(R.id.favourite_switch);

        selfLink = getArguments().getString("self_link");
        favouriteSwitch.setChecked(FavoriteClubHelper.isFavorite(getContext(),selfLink));
        initListeners();
        createClubView();


        currView = view;
        return view;

    }

    private void initListeners(){
        favouriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FavoriteClubHelper.updateFavoriteClub(getContext(),isChecked,selfLink);
            }
        });
    }

    private void createClubView() {

        if (selfLink == null) throw new IllegalArgumentException("No argument provided for selfLink");

        FootballRestClientHelper.get(selfLink,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {
                createClubView(Club.create(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                HttpErrorHandler.handle(getActivity(),throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                HttpErrorHandler.handle(getActivity(),throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                HttpErrorHandler.handle(getActivity(),throwable);
            }
        });
    }

    private void createClubView(Club club){

        updateActivityFixturesLink(club);

        this.club = club;
        //getActivity().setTitle(club.getClubName());

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

    private void updateActivityFixturesLink(Club club) {
        if (getActivity() instanceof ClubFixturesActivity){
            ((ClubFixturesActivity)getActivity()).setFixturesLink(club.getFixturesLink());
        }
    }

    private void loadCrestImagePNG(String crestUrl) {
        Picasso.with(getContext()).load(crestUrl).into(clubLogo);
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
        }catch (SVGParseException e ){
            Log.d("ClubInfoActivity:","Error parsing SVG: " + e.toString());
            e.printStackTrace();
        }
    }

    private void updateImageView(Drawable drawable){
        clubLogo.setImageDrawable(drawable);
    }
}
