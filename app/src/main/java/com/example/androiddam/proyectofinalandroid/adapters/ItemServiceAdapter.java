package com.example.androiddam.proyectofinalandroid.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.AddOfferActivity;
import com.example.androiddam.proyectofinalandroid.model.ItemService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Juan on 02/09/2016.
 */
public class ItemServiceAdapter extends RecyclerView.Adapter<ItemServiceAdapter.ViewHolder> {
    private ArrayList<ItemService> itemServices;
    private Activity activity;

    public ItemServiceAdapter(ArrayList<ItemService> itemServices, Activity activity) {
        this.itemServices = itemServices;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.label_item_offer, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_id.setText(String.valueOf(itemServices.get(position).getId()));
        holder.tv_name.setText(itemServices.get(position).getName());
        Picasso.with(activity).load(itemServices.get(position).getImg()).into(holder.iv_icon);
        holder.flView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddOfferActivity) view.getContext()).goLookUpSubcategory(Integer.valueOf(holder.tv_id.getText().toString()));

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemServices.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id;
        TextView tv_name;
        ImageView iv_icon;
        FrameLayout flView;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            flView = (FrameLayout) itemView.findViewById(R.id.fl_view);
        }
    }
}
