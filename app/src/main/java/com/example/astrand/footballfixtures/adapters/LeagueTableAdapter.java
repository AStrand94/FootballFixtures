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
import com.example.astrand.footballfixtures.entities.Standing;

import java.util.List;



public class LeagueTableAdapter extends ArrayAdapter<Standing> {

    public LeagueTableAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Standing> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Standing standing = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_league_table,parent,false);
        }

        //if (position % 2 == 0) convertView.setBackgroundColor(Color.parseColor("#f6f6f6"));

        TextView positionText = convertView.findViewById(R.id.standing_pos_textview);
        TextView nameText = convertView.findViewById(R.id.standing_name_textview);
        TextView statText = convertView.findViewById(R.id.standing_stat_textview);

        positionText.setText(getPositionString(standing.getPosition()));
        nameText.setText(standing.getTeamName());
        statText.setText(getStatString(
                standing.getPlayedGames(),
                standing.getWins(),
                standing.getDraws(),
                standing.getLosses(),
                standing.getPoints()
        ));

        return convertView;
    }


    private String getPositionString(int pos){ //MAX 5
        return pos + ". ";
    }

    private String getStatString(int pl, int w, int d, int l, int p){//MAX 15
        return pl + (pl < 10 ? "   " : "  ") +
                w + (w < 10 ? "  " : ' ') +
                d + (d < 10 ? "  " : ' ') +
                l + (l < 10 ? "    " : "   ") +
                p + (p < 10 ? "  " : ' ');
    }
}
