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
import com.example.astrand.footballfixtures.entities.Club;
import com.example.astrand.footballfixtures.entities.Fixture;

import java.util.List;


public class FavoriteClubsAdapter extends ArrayAdapter<Club> {

    public FavoriteClubsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Club> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Club club = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_competition,parent,false);
        }

        if (position % 2 == 0) convertView.setBackgroundColor(Color.LTGRAY);

        TextView textView = convertView.findViewById(R.id.competition_caption);

        if (club != null) {
            textView.setText(club.getClubName() != null ? club.getClubName() : "");
        }else{
            textView.setText(getContext().getString(R.string.error));
        }


        return convertView;
    }
}
