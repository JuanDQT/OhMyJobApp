package com.example.androiddam.proyectofinalandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.SolicitudesAdapter;
import com.example.androiddam.proyectofinalandroid.model.Solicitud;

import java.util.ArrayList;
import java.util.List;

public class Solicitudes_fr extends Fragment {

    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fr_solicitudes, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        //recyclerView.setHasFixedSize(true);

        List<Solicitud> solicitudList = new ArrayList<Solicitud>();

        solicitudList.add(new Solicitud("http://lorempixel.com/200/200/", "Juan"));
        solicitudList.add(new Solicitud("http://lorempixel.com/200/200/", "Pepe"));
        solicitudList.add(new Solicitud("http://lorempixel.com/200/200/", "Dario"));
        solicitudList.add(new Solicitud("http://lorempixel.com/200/200/", "Sergio"));

        SolicitudesAdapter solicitudesAdapter = new SolicitudesAdapter(solicitudList);



        recyclerView.setAdapter(solicitudesAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


        //Toast.makeText(getActivity(), "Tamany "+ solicitudesAdapter.getItemCount(), Toast.LENGTH_SHORT).show();


        return view;
    }
}
