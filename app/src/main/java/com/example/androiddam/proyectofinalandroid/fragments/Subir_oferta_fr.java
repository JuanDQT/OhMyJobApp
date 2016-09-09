package com.example.androiddam.proyectofinalandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.androiddam.proyectofinalandroid.R;
import com.labo.kaji.fragmentanimations.MoveAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class Subir_oferta_fr extends Fragment {
    private Button btn_left;
    private Button btn_right;
    static int posicion;

    public Subir_oferta_fr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fr_subir_oferta, container, false);

        btn_left = (Button) view.findViewById(R.id.left);
        btn_right = (Button) view.findViewById(R.id.right);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicion = 0;
                getFragmentManager().beginTransaction().add(R.id.content, new IZQUIERDO()).commit();
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicion = 1;
                getFragmentManager().beginTransaction().add(R.id.content, new SelectTypeJobFragment()).commit();
            }
        });

        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (posicion==0)
        return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);
        else
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
    }

}
