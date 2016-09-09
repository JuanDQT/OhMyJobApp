package com.example.androiddam.proyectofinalandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.AddOfferActivity;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOfferFinish extends Fragment implements View.OnClickListener {

    private EditText etDescription;
    private TextView tvYears;
    //DAYS
    private LinearLayout llLu;
    private LinearLayout llMa;
    private LinearLayout llMi;
    private LinearLayout llJu;
    private LinearLayout llVi;
    private LinearLayout llSa;
    private LinearLayout llDo;

    private SeekBar sbExperience;

    private Button btnPush;

    private Set<Integer> dayList;

    public AddOfferFinish() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_offer_finish, container, false);
        dayList = new HashSet<>();
        etDescription = (EditText) view.findViewById(R.id.et_description);
        llLu = (LinearLayout) view.findViewById(R.id.ll_lu);
        llMa = (LinearLayout) view.findViewById(R.id.ll_ma);
        llMi = (LinearLayout) view.findViewById(R.id.ll_mi);
        llJu = (LinearLayout) view.findViewById(R.id.ll_ju);
        llVi = (LinearLayout) view.findViewById(R.id.ll_vi);
        llSa = (LinearLayout) view.findViewById(R.id.ll_sa);
        llDo = (LinearLayout) view.findViewById(R.id.ll_do);
        btnPush = (Button) view.findViewById(R.id.btn_push);
        tvYears = (TextView) view.findViewById(R.id.tv_years);
        sbExperience = (SeekBar) view.findViewById(R.id.sb_experience);
        llLu.setOnClickListener(this);
        llMa.setOnClickListener(this);
        llMi.setOnClickListener(this);
        llJu.setOnClickListener(this);
        llVi.setOnClickListener(this);
        llSa.setOnClickListener(this);
        llDo.setOnClickListener(this);
        btnPush.setOnClickListener(this);

        sbExperience.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("NORMAL","ONSTART: " + i);
                tvYears.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Log.d("MOVE", "HPLI SELECTYPE...");

        if (AddOfferActivity.derecha) {
            AddOfferActivity.derecha = false;
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
        } else if (AddOfferActivity.izquierda) {
            AddOfferActivity.izquierda = false;
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);

        }

        return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_lu:
            case R.id.ll_ma:
            case R.id.ll_mi:
            case R.id.ll_ju:
            case R.id.ll_vi:
            case R.id.ll_sa:
            case R.id.ll_do:
                addToDayList(view);
                break;
            case R.id.btn_push:
                Log.d("NORMAL", "DAYS: " + dayList);
                showPupUp();
                break;
        }

    }

    private void showPupUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Desea");


    }

    private void addToDayList(View view) {
        LinearLayout linearLayout = (LinearLayout)view;
        TextView tvDayId = (TextView) ((LinearLayout) view).getChildAt(1);
        int dayId = Integer.parseInt(tvDayId.getText().toString());
        Toast.makeText(getActivity(), "ID: "+ dayId, Toast.LENGTH_SHORT).show();
        if (!dayList.contains(dayId)){
            dayList.add(dayId);
            linearLayout.setBackgroundColor( getResources().getColor(R.color.colorAccent));
        }
        else{
            linearLayout.setBackgroundResource( R.drawable.border_ll_day);
            dayList.remove(dayId);
        }

    }
}
