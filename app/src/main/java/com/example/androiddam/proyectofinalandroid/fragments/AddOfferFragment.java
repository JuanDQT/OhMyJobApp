package com.example.androiddam.proyectofinalandroid.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.AddOfferActivity;
import com.example.androiddam.proyectofinalandroid.activities.HomeActivity;
import com.example.androiddam.proyectofinalandroid.adapters.ItemServiceAdapter;
import com.example.androiddam.proyectofinalandroid.model.ItemService;
import com.labo.kaji.fragmentanimations.MoveAnimation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOfferFragment extends Fragment {

    private RecyclerView rvAddOffer;
    private static Context context;
    private static final String URL = "http://proyecto-dam.esy.es/php/getCategoriesV2.php";

    public AddOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_offer, container, false);

        context = view.getContext();
        rvAddOffer = (RecyclerView) view.findViewById(R.id.rv_add_offer);
        ArrayList<ItemService> itemServices = HomeActivity.itemServices;


        ItemServiceAdapter itemServiceAdapter = new ItemServiceAdapter(itemServices, getActivity());

        rvAddOffer.setLayoutManager(new GridLayoutManager(getActivity(),2));
        rvAddOffer.setAdapter(itemServiceAdapter);



        return view;
    }

/*    private ArrayList<ItemService> cargarItems() {
        final ArrayList<ItemService> itemServices = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;

                    for (int i = 0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ItemService itemService = new ItemService(jsonObject.getInt("id_category"), jsonObject.getString("name_category"), jsonObject.getString("img")) ;
                        itemServices.add(itemService );
                        Log.e("VOLLEY",itemService.toString());
                    }

                    ItemServiceAdapter itemServiceAdapter = new ItemServiceAdapter(itemServices, getActivity());

                    rvAddOffer.setLayoutManager(new GridLayoutManager(getActivity(),2));
                    rvAddOffer.setAdapter(itemServiceAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);

        return itemServices;
    }*/


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Log.d("MOVE", "HOLI DESDE AddOfferFragment");
        if (AddOfferActivity.izquierda) {
            Log.d("MOVE", "DERECHA A IZQUIERDA");
            AddOfferActivity.izquierda = false;
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);
        }
        if (AddOfferActivity.init) {
            Log.d("MOVE", "NOTHING");
            AddOfferActivity.init = false;
            return null;
        }
        Log.d("MOVE", "IZQUIERDA A DERECHA");
        return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
    }

/*    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Toast.makeText(getActivity(), "HOLI DESDE AddOfferFragment", Toast.LENGTH_SHORT).show();
        if (AddOfferActivity.izquierda) {
            AddOfferActivity.izquierda = false;
            return MoveAnimation.create(MoveAnimation.LEFT, enter, 500);
        }
        if (AddOfferActivity.init) {
            AddOfferActivity.init = false;
            return null;
        }

        return MoveAnimation.create(MoveAnimation.RIGHT, enter, 500);
    }*/




}
