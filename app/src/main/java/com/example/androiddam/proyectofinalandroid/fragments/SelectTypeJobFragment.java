package com.example.androiddam.proyectofinalandroid.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.AddOfferActivity;
import com.labo.kaji.fragmentanimations.MoveAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTypeJobFragment extends Fragment implements View.OnClickListener{

    private ImageView ivServicio;
    private ImageView ivHoras;
    private ImageView ivConvenir;
    AlertDialog alertDialog;

    public static int categoryId;
    public static String nameOffer;
    public static int price = 0;

    private static final int PRICE_SERVICE = 0;
    private static final int PRICE_HOURS = 1;
    private static final int PRICE_UNKNOWN = 2;

    public SelectTypeJobFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_type_job, container, false);

        ivServicio = (ImageView) view.findViewById(R.id.iv_servicio);
        ivHoras = (ImageView) view.findViewById(R.id.iv_horas);
        ivConvenir = (ImageView) view.findViewById(R.id.iv_convenir);

        ivServicio.setOnClickListener(this);
        ivHoras.setOnClickListener(this);
        ivConvenir.setOnClickListener(this);


        Bundle bundle = getArguments();

        this.categoryId = bundle.getInt("idCategory");
        this.nameOffer = bundle.getString("nameOffer");



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


    public void setPricePopUp(String customDescrtiption, final int typeOffer){
        final AlertDialog.Builder alertdialog_builder = new AlertDialog.Builder(getActivity());
        alertdialog_builder.setTitle("Precio");
        alertdialog_builder.setMessage(customDescrtiption);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_offer,null);
        final EditText etPrice = (EditText) view.findViewById(R.id.et_name_offer);
        etPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
        alertdialog_builder.setView(view);

        alertdialog_builder.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                price = Integer.parseInt(etPrice.getText().toString());
                ((AddOfferActivity) getContext()).goFinishUploadNewJob(nameOffer, categoryId, price, typeOffer);
            }
        });

        alertdialog_builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                price = 0;
            }
        });

        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if ( !charSequence.toString().isEmpty() &&Integer.valueOf(charSequence.toString())>0){
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    if (charSequence.toString().length()>4){
                        etPrice.setFocusable(false);
                    }else{
                        etPrice.setFocusableInTouchMode(true);
                    }

                }
                else
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        alertDialog = alertdialog_builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        openKeyboard(alertDialog);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_servicio:
                setPricePopUp("Introduce el precio que deseas cobrar por el servicio", PRICE_SERVICE);
                break;
            case R.id.iv_horas:
                setPricePopUp("Introduce el precio que deseas cobrar por hora",PRICE_HOURS );
                break;
            case R.id.iv_convenir:
                price = 0;
                ((AddOfferActivity) getContext()).goFinishUploadNewJob(nameOffer, categoryId, 0, PRICE_UNKNOWN);
                break;

        }

        //Toast.makeText(getActivity(), "NEXT. PRICE:  " + price, Toast.LENGTH_SHORT).show();

    }

    private void openKeyboard(AlertDialog alertDialog){
        alertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
