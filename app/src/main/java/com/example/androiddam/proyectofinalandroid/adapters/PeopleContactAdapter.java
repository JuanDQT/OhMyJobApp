package com.example.androiddam.proyectofinalandroid.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androiddam.proyectofinalandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Juan on 20/08/2016.
 */
public class PeopleContactAdapter extends RecyclerView.Adapter<PeopleContactAdapter.ViewHolder> {
    private ArrayList<Integer> listPeople;
    private Activity activity;

    public PeopleContactAdapter(ArrayList<Integer> listPeople, Activity activity) {
        this.listPeople = listPeople;
        this.activity = activity;
    }

    @Override
    public PeopleContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.label_people_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(activity).load(listPeople.get(position)).into(holder.circleImageView);

    }

    @Override
    public int getItemCount() {
        return listPeople.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.civ_pic);
        }
    }
}
