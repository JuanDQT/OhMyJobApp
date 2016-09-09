package com.example.androiddam.proyectofinalandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.model.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edu-Dam on 19/05/2016.
 */
public class TagAdapter extends ArrayAdapter<Tag> {
    private List<Boolean> checkboxState;
    public TagAdapter(Context context, ArrayList<Tag> tags) {
        super(context, 0, tags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tag tag = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_tag, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvTag);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBoxTag);
        // Populate the data into the template view using the data object
        tvName.setText(tag.name);
        // Return the completed view to render on screen
        return convertView;
    }


    public int getTagId(int position){
        Tag tag = getItem(position);
        return tag.getId();
    }
}