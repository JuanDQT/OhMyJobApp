package com.example.androiddam.proyectofinalandroid.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.PeopleContactAdapter;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;
import com.example.androiddam.proyectofinalandroid.widgets.CircularAnim;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jaouan.revealator.Revealator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OfferShowActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final String OFFER_URL = "http://proyecto-dam.esy.es/php/getSingleOffer.php";

    public static final String OFFER_KEY = "user_id";

    private int user_id;

    private JSONObject jsonOffer;
    private double lat;
    private double lng;
    private String id_offer;
    private String phone;
    private double user_rating;


    private LinearLayout ll_call_user;
    private LinearLayout ll_profile_user;
    private LinearLayout ll_fav_user;

    private TextView tvOfferTitle;
    private TextView tvOfferPrice;
    private TextView tvOfferDescription;
    private ImageView[] stars;
    private TextView tv_offer_name;
    private TextView tv_fav;
    private TextView tv_position;

    //private FloatingActionButton fab;
    private FloatingActionButton fab_map;

    private ImageView iv_header;
    private ImageView iv_fav;

    public static final String STATEKEY_THE_AWESOME_VIEW_IS_VISIBLE = "the_awesome_view_is_visible";
    private View mapView;
    private ImageView ivHideMap;
    private RecyclerView rv_people;
    private ArrayList<Integer> listPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_show);
        MapsInitializer.initialize(this);


        id_offer = getIntent().getExtras().getString("id_offer");

        tvOfferTitle = (TextView) findViewById(R.id.tw_offer_name);
        tvOfferDescription = (TextView) findViewById(R.id.tv_description);
        tvOfferPrice = (TextView) findViewById(R.id.tv_price);
        tv_offer_name = (TextView) findViewById(R.id.tv_offer_name);
        iv_header = (ImageView) findViewById(R.id.iv_header);

        ll_profile_user = (LinearLayout) findViewById(R.id.profile_view);
        ll_call_user = (LinearLayout) findViewById(R.id.ll_call_user);
        ll_fav_user = (LinearLayout) findViewById(R.id.ll_fav_user);
        tv_position = (TextView) findViewById(R.id.tv_position);

        byte[] byteArray = getIntent().getByteArrayExtra("img_header");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);



        iv_header.setImageBitmap(bmp);

        stars = new ImageView[5];
        stars[0] = (ImageView) findViewById(R.id.iv_1);
        stars[1] = (ImageView) findViewById(R.id.iv_2);
        stars[2] = (ImageView) findViewById(R.id.iv_3);
        stars[3] = (ImageView) findViewById(R.id.iv_4);
        stars[4] = (ImageView) findViewById(R.id.iv_5);

        fab_map = (FloatingActionButton) findViewById(R.id.fab_map);
        iv_fav = (ImageView) findViewById(R.id.iv_fav);
        tv_fav = (TextView) findViewById(R.id.tv_fav);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapOffer);
        mapFragment.getMapAsync(this);

        mapView = findViewById(R.id.map_view);
        rv_people = (RecyclerView) findViewById(R.id.rv_people);

        cargarDummyPeople();


        fab_map.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Revealator.reveal(mapView)
                        .from(fab_map)
                        .withCurvedTranslation()
                        .withChildsAnimation()
                        //.withDelayBetweenChildAnimation(...)
                        //.withChildAnimationDuration(...)
                        //.withTranslateDuration(...)
                        //.withRevealDuration(...)
                        //.withEndAction(...)
                        .start();

                ivHideMap.setVisibility(View.VISIBLE);

                //Log.i("ESTADO", "BANANERO GARKA");
                //Toast.makeText(OfferShowActivity.this, "peluca sape", Toast.LENGTH_SHORT).show();
                if (!jsonOffer.isNull("lat") && !jsonOffer.isNull("lng")) {
                    //Toast.makeText(OfferShowActivity.this, "ENTRE GARKA", Toast.LENGTH_SHORT).show();


                    try {
                        lat = Double.valueOf(jsonOffer.getString("lat"));
                        lng = Double.valueOf(jsonOffer.getString("lng"));

                        LatLng latlng = new LatLng(lat, lng);


                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14));
                        mMap.addMarker(new MarkerOptions().position(latlng).title("Posición"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }


        });

        ivHideMap = (ImageView) findViewById(R.id.iv_hide_map);
        ivHideMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Revealator.unreveal(mapView)
                        .to(fab_map)
                        .withCurvedTranslation()
                        .withUnrevealDuration(500)
//                        .withTranslateDuration(...)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                //fab.show();
                            }
                        })
                        .start();
                ivHideMap.setVisibility(View.GONE);

            }
        });

        if (savedInstanceState != null && savedInstanceState.getBoolean(STATEKEY_THE_AWESOME_VIEW_IS_VISIBLE)) {
            mapView.setVisibility(View.VISIBLE);
            fab_map.setVisibility(View.INVISIBLE);
        }

        MySingleton.getInstance(this).addToRequestQueue(offerRequest);

        ll_profile_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CircularAnim.hide(ll_profile_user)
                        .onAnimationEndListener(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                CircularAnim.fullActivity(OfferShowActivity.this, ll_profile_user).colorOrImageRes(R.color.colorPrimary)
                                        .go(new CircularAnim.OnAnimationEndListener() {
                                            @Override
                                            public void onAnimationEnd() {
                                                Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                                                Bundle bundle = new Bundle();
                                                intent.putExtras(bundle);
                                                bundle.putInt("id", user_id);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        });
                            }
                        }).go();
                //
            }
        });

        //TELEPHONE
        ll_call_user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone));
                //callIntent.setData(Uri.parse("tel:"+phone));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                }
                startActivity(callIntent);

            }
        });

        ll_fav_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewAnimatedChange(OfferShowActivity.this, iv_fav, R.drawable.fav_icon_on);
                tv_fav.setText("GUARDADO");
                tv_fav.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
    }

    private void cargarDummyPeople() {
        listPeople = new ArrayList<>();
        listPeople.add(R.drawable.p1);
        listPeople.add(R.drawable.p2);
        listPeople.add(R.drawable.p4);
        listPeople.add(R.drawable.p2);
        listPeople.add(R.drawable.p1);
        listPeople.add(R.drawable.p4);
        listPeople.add(R.drawable.p2);

        PeopleContactAdapter peopleContactAdapter = new PeopleContactAdapter(listPeople, this);
        rv_people.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_people.setAdapter(peopleContactAdapter);
    }


    StringRequest offerRequest = new StringRequest(Request.Method.POST, OFFER_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        jsonOffer = new JSONObject(response);

                        tv_offer_name.setText(jsonOffer.getString("name_offer"));


                        //
                        user_id = jsonOffer.getInt("user_id");
                        tvOfferTitle.setText(jsonOffer.getString("name_category"));
                        phone = jsonOffer.getString("phone");
                        tvOfferPrice.setText(jsonOffer.getString("price") + "€");
                        tvOfferDescription.setText(jsonOffer.getString("description"));
                        user_rating = Double.parseDouble(jsonOffer.getString("rating"));
                        for (int i = 0; i < user_rating; i++) {
                            stars[i].setVisibility(View.VISIBLE);
                        }

                        if (user_rating == 0) {
                            stars[0].setVisibility(View.VISIBLE);
                        }

/*                        if (!jsonOffer.isNull("lat") && !jsonOffer.isNull("lng")) {


                            lat = Double.valueOf(jsonOffer.getString("lat"));
                            lng = Double.valueOf(jsonOffer.getString("lng"));

                            LatLng latlng = new LatLng(lat, lng);


                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 14));
                            mMap.addMarker(new MarkerOptions().position(latlng).title(jsonOffer.getString("adress_line")));


                        }*/


                    } catch (JSONException e) {

                    }


                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(OfferShowActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<String, String>();
            map.put(OFFER_KEY, id_offer);
            return map;
        }




    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng cat = new LatLng(41.44, 2.08524);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cat, 11));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATEKEY_THE_AWESOME_VIEW_IS_VISIBLE, mapView.getVisibility() == View.VISIBLE);
    }

    @Override
    public void onBackPressed() {

        if (mapView.getVisibility() == View.VISIBLE) {
            Revealator.unreveal(mapView)
                    .to(fab_map)
                    .withCurvedTranslation()
                    .withUnrevealDuration(500)
//                        .withTranslateDuration(...)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            //fab.show();
                        }
                    })
                    .start();
            ivHideMap.setVisibility(View.GONE);
        } else {
            super.onBackPressed();

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        ll_profile_user.setVisibility(View.VISIBLE);

    }

    public static void imageViewAnimatedChange(Context c, final ImageView v, final @DrawableRes int new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //v.setImageBitmap(new_image);
                v.setImageResource(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }

}






