package com.example.androiddam.proyectofinalandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.model.Mensaje;

import java.util.List;

/**
 * Created by Juan on 27/05/2016.
 */
public class MensajeAdapter extends ArrayAdapter<Mensaje> {
    private Context context;
    private List<Mensaje> mensajeList;
    private int label;

    public MensajeAdapter(Context context, int resource, List<Mensaje> mensajeList) {
        super(context, resource,mensajeList);
        this.context = context;
        this.mensajeList = mensajeList;
        this.label = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(label, null);

            viewHolder = new ViewHolder();
            viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tv_hour = (TextView) view.findViewById(R.id.tv_hour);
            viewHolder.tv_message = (TextView) view.findViewById(R.id.tv_message);


            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_name.setText(mensajeList.get(position).getName());
        viewHolder.tv_message.setText(mensajeList.get(position).getMessage());
        viewHolder.tv_hour.setText(mensajeList.get(position).getHour());

        return view;
    }

    class ViewHolder{
        private TextView tv_name;
        private TextView tv_hour;
        private TextView tv_message;
    }


}
