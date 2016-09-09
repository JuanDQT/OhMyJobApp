package com.example.androiddam.proyectofinalandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.AddOfferActivity;
import com.labo.kaji.fragmentanimations.MoveAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTypeJobFragment extends Fragment {

    private ImageView ivServicio;
    private ImageView ivHoras;
    private ImageView ivConvenir;

    public SelectTypeJobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_type_job, container, false);

        ivServicio = (ImageView) view.findViewById(R.id.iv_servicio);
        ivHoras = (ImageView) view.findViewById(R.id.iv_horas);
        ivConvenir = (ImageView) view.findViewById(R.id.iv_convenir);

/*
        Animation grow_animation_reference = AnimationUtils.loadAnimation(getActivity(), R.anim.grow_animation);
        AnimationSet growAnimation = new AnimationSet(true);
        growAnimation.addAnimation(grow_animation_reference);

        ivServicio.startActionMode(growAnimation);
        ivHoras.startActionMode(a);
        ivConvenir.startActionMode(a);
*/


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        return view;
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Log.d("MOVE", "HPLI SELECTYPE...");

        if (AddOfferActivity.derecha){
            AddOfferActivity.derecha = false;
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
        }
        else if (AddOfferActivity.izquierda){
            AddOfferActivity.izquierda = false;
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);

        }

        return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
    }


}
