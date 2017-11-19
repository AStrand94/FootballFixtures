package com.example.astrand.footballfixtures.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.entities.Fixture;
import com.example.astrand.footballfixtures.entities.Result;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class FixtureAdapter extends ArrayAdapter<Fixture> {

    private final static int resource = R.layout.list_item_fixture;

    private static final String TIMED = "TIMED";
    private static final String IN_PLAY = "IN_PLAY";
    private static final String FINISHED = "FINISHED";

    public FixtureAdapter(@NonNull Context context, @NonNull List<Fixture> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Fixture fixture = getItem(position);


        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }


        if (fixture != null) {
            //if (position % 2 == 0) convertView.setBackgroundColor(Color.LTGRAY);

            TextView homeText = convertView.findViewById(R.id.fixture_home_team_textview);
            TextView resultText = convertView.findViewById(R.id.fixture_result_textview);
            TextView awayText = convertView.findViewById(R.id.fixture_away_team_textview);
            TextView dateText = convertView.findViewById(R.id.fixture_date_textview);
            TextView statusText = convertView.findViewById(R.id.status_text_view);

            homeText.setText(fixture.getHomeTeamName());
            resultText.setText(getResult(fixture));
            awayText.setText(fixture.getAwayTeamName());
            dateText.setText(new SimpleDateFormat("dd.MM.yy HH:mm", Locale.US).format(fixture.getDate()));
            statusText.setText(getStatus(fixture,getContext()));
        }

        return convertView;
    }

    private String getResult(Fixture fixture) {
        if (fixture.getResult() != null) {
            Result result = fixture.getResult();

            return result.getGoalsHomeTeam() + " - " + result.getGoalsAwayTeam();
        }else{
            return " - ";
            //return new SimpleDateFormat("dd.MM.yy HH:mm").format(fixture.getDate());
        }
    }

    private String getStatus(Fixture fixture, Context context){
        String status = fixture.getStatus();

        if (status.equals(IN_PLAY)) return context.getString(R.string.in_play);
        else if (status.equals(FINISHED)) return context.getString(R.string.finished);
        else return "";
    }
}
