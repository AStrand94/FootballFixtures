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
import com.example.astrand.footballfixtures.entities.cl_entities.ChampionsLeagueGroup;
import com.example.astrand.footballfixtures.entities.cl_entities.ChampionsLeagueIF;
import com.example.astrand.footballfixtures.entities.cl_entities.ChampionsLeagueStanding;

import java.util.List;

public class ChampionsLeagueAdapter extends ArrayAdapter<ChampionsLeagueIF> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT = 1;

    private static final int WHITE;
    private static final int GREY;

    static {
        WHITE = Color.parseColor("#ffffff");
        GREY = Color.parseColor("#696969");
    }

    private List<ChampionsLeagueIF> list;
    public ChampionsLeagueAdapter(@NonNull Context context, @LayoutRes int resource, List<ChampionsLeagueIF> list) {
        super(context, resource,list);
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ChampionsLeagueIF championsLeagueIF = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_league_table, parent, false);
        }

        TextView positionText = convertView.findViewById(R.id.standing_pos_textview);
        TextView nameText = convertView.findViewById(R.id.standing_name_textview);
        TextView statText = convertView.findViewById(R.id.standing_stat_textview);

        if (championsLeagueIF instanceof ChampionsLeagueGroup){
            convertView.setClickable(false);
            ChampionsLeagueGroup group = (ChampionsLeagueGroup)championsLeagueIF;

            //positionText.setWidth(200);
            //positionText.setTextSize(17);
            convertView.setBackgroundColor(GREY);

            positionText.setText("");
            nameText.setText("Group " + group.getGroup());
            statText.setText("Points");

            return convertView;
        }else {
            ChampionsLeagueStanding standing = (ChampionsLeagueStanding)championsLeagueIF;

            convertView.setBackgroundColor(WHITE);
            //positionText.setWidth(60);
            //positionText.setTextSize(20);

            //Noe galt med API'et, så viser posisjon '0' for alle.
            positionText.setText(getPositionString(standing.getRank()));
            nameText.setText(standing.getTeam());
            statText.setText("  " + Integer.toString(standing.getPoints()));

            return convertView;
        }
    }

    private String getPositionString(int pos){ //MAX 5
        return pos + ". ";
    }

    @Override
    public int getPosition(@Nullable ChampionsLeagueIF item) {
        if (list.contains(item)) return list.indexOf(item);
        else return -1;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0) return TYPE_HEADER;
        else return TYPE_CONTENT;
    }


}
