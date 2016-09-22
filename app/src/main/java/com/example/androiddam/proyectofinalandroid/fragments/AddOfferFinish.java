package com.example.androiddam.proyectofinalandroid.fragments;


import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.AddOfferActivity;
import com.example.androiddam.proyectofinalandroid.activities.HomeActivity;
import com.example.androiddam.proyectofinalandroid.activities.SetLocationActivity;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;
import com.example.androiddam.proyectofinalandroid.widgets.CircularAnim;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    private Button btnLocation;

    private Set<Integer> dayList;

    public static int categoryId;
    public static String nameOffer;
    public static int price = 0;
    public static int typeService;

    public static final String REGISTER_URL = "http://proyecto-dam.esy.es/php/uploadOfferV2.php";

    public static final String KEY_ID_USER = "user_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ZIPCODE = "zipcode";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_DAYS = "days";

    private String location;
    private String zipcode;
    private double lat;
    private double lng;
    private int experience = 0;
    private String userId;

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
        btnLocation = (Button) view.findViewById(R.id.btn_location);
        btnLocation = (Button) view.findViewById(R.id.btn_location);
        llLu.setOnClickListener(this);
        llMa.setOnClickListener(this);
        llMi.setOnClickListener(this);
        llJu.setOnClickListener(this);
        llVi.setOnClickListener(this);
        llSa.setOnClickListener(this);
        llDo.setOnClickListener(this);
        btnPush.setOnClickListener(this);
        btnLocation.setOnClickListener(this);

        Bundle bundle = getArguments();
        this.categoryId = bundle.getInt("idCategory");
        this.nameOffer = bundle.getString("nameOffer");
        this.price = bundle.getInt("price");
        this.typeService = bundle.getInt("typeService");

        //Toast.makeText(getActivity(), "NAME OFFER: "+ nameOffer + "\tID CATEGORY: "+ categoryId + "\tPRCICE: "+ price + "\tTYPE_SERVICE: "+ typeService, Toast.LENGTH_SHORT).show();


        sbExperience.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.d("SETLOCATION","ONSTART: " + i);
                experience = i;
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
                Log.d("SETLOCATION", dayList + "");
                break;
            case R.id.btn_location:
                final Intent intent = new Intent(getActivity(), SetLocationActivity.class);
                CircularAnim.hide(btnLocation)
                        .onAnimationEndListener(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                CircularAnim.fullActivity(getActivity(), btnLocation).colorOrImageRes(R.color.colorAccent)
                                        .go(new CircularAnim.OnAnimationEndListener() {
                                            @Override
                                            public void onAnimationEnd() {
                                                startActivityForResult(intent,1);
                                                //finish();
                                            }
                                        });
                            }
                        }).go();
                break;
            case R.id.btn_push:
                //Log.d("NORMAL", "DAYS: " + dayList);
                showPupUp();
                break;
        }

    }

    private void showPupUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Deseo mostrar");

        View view =  LayoutInflater.from(getActivity()).inflate(R.layout.dialog_setup_offer, null);
        builder.setView(view);

        builder.setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uploadOffer();
                ((AddOfferActivity) getContext()).goFinishFragment();

            }
        });

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();

    }

    private void uploadOffer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("NORMAL", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(AddWorkActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_ID_USER, String.valueOf(HomeActivity.getUserId(getContext())));
                map.put(KEY_DESCRIPTION, etDescription.getText().toString());
                map.put(KEY_CATEGORY, String.valueOf(categoryId));
                map.put(KEY_NAME, nameOffer);
                map.put(KEY_PRICE, String.valueOf(price));
                map.put(KEY_ZIPCODE, zipcode);
                map.put(KEY_LOCATION, location);
                map.put(KEY_LONGITUDE, String.valueOf(lng));
                map.put(KEY_LATITUDE, String.valueOf(lat));
                map.put(KEY_EXPERIENCE, String.valueOf(experience));


                return map;
            }
        };

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void addToDayList(View view) {
        LinearLayout linearLayout = (LinearLayout)view;
        TextView tvDayId = (TextView) ((LinearLayout) view).getChildAt(1);
        int dayId = Integer.parseInt(tvDayId.getText().toString());
        //Toast.makeText(getActivity(), "ID: "+ dayId, Toast.LENGTH_SHORT).show();
        if (!dayList.contains(dayId)){
            dayList.add(dayId);
            linearLayout.setBackgroundColor( getResources().getColor(R.color.colorAccent));
        }
        else{
            linearLayout.setBackgroundResource( R.drawable.border_ll_day);
            dayList.remove(dayId);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == 0) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            //Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT).show();
        } else {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            Bundle extras = data.getExtras();
            //adressLine = extras.getString("line");
            location = extras.getString("location");
            zipcode = extras.getString("zipcode");
            lat = extras.getDouble("lat");
            lng = extras.getDouble("lng");

            Log.d("SETLOCATION", "[LLEGA]LAT: "+ lat + "\tLONG: "+ lng + "\tZIPCODE: "+ zipcode + "\tLOCALITY: "+ location);
            btnLocation.setVisibility(View.GONE);
            btnPush.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (location == null) {
            btnLocation.setVisibility(View.VISIBLE);
        }
    }
}
