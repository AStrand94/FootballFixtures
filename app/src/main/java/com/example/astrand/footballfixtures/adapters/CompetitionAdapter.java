package com.example.astrand.footballfixtures.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.astrand.footballfixtures.R;
import com.example.astrand.footballfixtures.entities.Competition;

import java.util.List;


public class CompetitionAdapter extends ArrayAdapter<Competition> {

    public CompetitionAdapter(Context context, int viewResource, List<Competition> entityList){
        super(context,viewResource,entityList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Competition competition = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_competition,parent,false);
        }

        //if (position % 2 == 0) convertView.setBackgroundColor(Color.LTGRAY);
        //else convertView.setBackgroundColor(Color.WHITE);

        TextView textView = convertView.findViewById(R.id.competition_caption);

        textView.setText(competition.getCaption() != null ? competition.getCaption() : "");


        return convertView;
    }
}
