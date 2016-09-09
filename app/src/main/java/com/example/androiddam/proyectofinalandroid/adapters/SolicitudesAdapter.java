package com.example.androiddam.proyectofinalandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.model.Solicitud;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Juan on 27/05/2016.
 */
public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.SolicitudViewHolder> {

    private List<Solicitud> solicitudList;
    private int label;

    public SolicitudesAdapter(List<Solicitud> solicitudList) {
        label = R.layout.label_solicitud;
        this.solicitudList = solicitudList;
    }

    @Override
    public SolicitudViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_solicitud, parent, false);
        SolicitudViewHolder solicitudViewHolder = new SolicitudViewHolder(view);
        return solicitudViewHolder;
    }

    @Override
    public void onBindViewHolder(SolicitudViewHolder holder, int position) {
        Solicitud solicitud = solicitudList.get(position);
        holder.bindSolicitud(solicitud);
    }


    public static class SolicitudViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        CircleImageView civ;

        public SolicitudViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            civ = (CircleImageView) itemView.findViewById(R.id.url_user);
        }

        public void bindSolicitud(Solicitud solicitud) {
            tv_name.setText(solicitud.getTv_user_name());
            Picasso.with(itemView.getContext()).load(solicitud.getUrl_user()).into(civ);
        }


    }


    @Override
    public int getItemCount() {
        return solicitudList.size();
    }



}
