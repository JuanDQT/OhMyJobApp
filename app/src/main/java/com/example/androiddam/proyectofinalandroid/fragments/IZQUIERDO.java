package com.example.androiddam.proyectofinalandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.AddOfferActivity;
import com.labo.kaji.fragmentanimations.MoveAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class IZQUIERDO extends Fragment {


    public IZQUIERDO() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_izquierdo, container, false);
        return view;
    }



    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Toast.makeText(getActivity(), "IZQUIERDO", Toast.LENGTH_SHORT).show();

        if (AddOfferActivity.izquierda){
            AddOfferActivity.izquierda = false;
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);

        }
        else if (AddOfferActivity.derecha){
            AddOfferActivity.derecha = false;
            return MoveAnimation.create(MoveAnimation.UP, enter, 500);
        }

        return null;
    }



}
