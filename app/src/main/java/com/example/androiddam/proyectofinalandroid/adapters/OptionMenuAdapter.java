package com.example.androiddam.proyectofinalandroid.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.model.OptionMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan on 21/05/2016.
 */
public class OptionMenuAdapter extends ArrayAdapter<OptionMenu>{
    Activity activity;
    List<OptionMenu> optionMenuList;
    int label;

    public OptionMenuAdapter(Activity activity, ArrayList<OptionMenu> optionMenus, int resource) {
        super(activity, resource);
        this.activity = activity;
        this.optionMenuList = optionMenus;
        this.label = resource;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if (view==null){
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(label, null);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.iv_icon.setBackgroundResource(optionMenuList.get(position).getIcon());
        viewHolder.tv_title.setText(optionMenuList.get(position).getTitle());
        if (optionMenuList.get(position).getExtra() > 0) {

            viewHolder.tv_count.setText(String.valueOf(optionMenuList.get(position).getExtra()));
            viewHolder.tv_count.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public int getCount() {
        return optionMenuList.size();
    }

    class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_count;

        public ViewHolder(View view) {
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_title = (TextView) view.findViewById(R.id.tv_name);
            tv_count = (TextView) view.findViewById(R.id.tv_count);
        }
    }


}

