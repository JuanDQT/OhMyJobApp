package com.example.androiddam.proyectofinalandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.MensajeAdapter;
import com.example.androiddam.proyectofinalandroid.model.Mensaje;

import java.util.ArrayList;
import java.util.List;

public class Mensajes_fr extends Fragment {

    private ListView lv_mensajes;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fr_mensajes, container, false);

        lv_mensajes = (ListView)view.findViewById(R.id.lv_mensajes);

        List<Mensaje> mensajes = new ArrayList<>();
        mensajes.add(new Mensaje("Damian", "Necesito tu ayuda mañana!", "16:04"));
        mensajes.add(new Mensaje("Julio", "Mañana te aviso", "21:36"));
        mensajes.add(new Mensaje("Cristina", "¿Podrias destaparme las tuberias?", "00:56"));
        mensajes.add(new Mensaje("Elias", "Necesito que repares la tv de mi bar", "11:22"));

        ArrayAdapter adapter = new MensajeAdapter(getActivity(), R.layout.label_mensaje,mensajes);
        lv_mensajes.setAdapter(adapter);


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        return view;
    }
}
