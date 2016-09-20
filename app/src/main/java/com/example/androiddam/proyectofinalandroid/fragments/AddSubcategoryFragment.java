package com.example.androiddam.proyectofinalandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.ItemOfferSubcategoryAdapter;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;
import com.example.androiddam.proyectofinalandroid.model.ItemOfferSubcategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubcategoryFragment extends Fragment {
    private RecyclerView rvItems;
    private SearchView svSearch;
    public static int category_id;
    private static final String URL = "http://proyecto-dam.esy.es/php/getSubCategoriesByIdV2.php";
    private ItemOfferSubcategoryAdapter itemOfferSubcategoryAdapter;
    private ArrayList<ItemOfferSubcategoryModel> items;
    //private ArrayList<ItemOfferSubcategoryModel> items_search;

    public AddSubcategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_subcategory, container, false);
        //
        Bundle bundle = getArguments();
        this.category_id = bundle.getInt("id_category_parent");
        //Toast.makeText(getActivity(), "CATEGORY IDZ: " + String.valueOf(category_id), Toast.LENGTH_SHORT).show();
        //
        rvItems = (RecyclerView) view.findViewById(R.id.rv_items);
        svSearch = (SearchView) view.findViewById(R.id.sv_search);


        cargarItems();

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<ItemOfferSubcategoryModel> options = new ArrayList<>();

                Iterator<ItemOfferSubcategoryModel> itemOfferSubcategoryModelIterator = items.iterator();

                while (itemOfferSubcategoryModelIterator.hasNext()) {
                    ItemOfferSubcategoryModel item = itemOfferSubcategoryModelIterator.next();
                    if (item.getName().toUpperCase().contains(newText.toUpperCase())) {
                        options.add(item);
                        Log.d("KEY", "contiene: " + item.getName());
                    }

                }

                Log.d("KEY", "TAMANO: " + itemOfferSubcategoryAdapter.getItemCount());

                itemOfferSubcategoryAdapter = new ItemOfferSubcategoryAdapter(options, getActivity());
                rvItems.setAdapter(itemOfferSubcategoryAdapter);

                Log.e("KEY", newText);
                return false;
            }
        });


        return view;
    }

    private void cargarItems() {

        items = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("INFO", response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ItemOfferSubcategoryModel itemService = new ItemOfferSubcategoryModel(jsonObject.getInt("id_category"), jsonObject.getString("name_category"), jsonObject.getString("img")) ;
                        items.add(itemService );
                    }
                    //items_search = new ArrayList<>(items);
                    itemOfferSubcategoryAdapter = new ItemOfferSubcategoryAdapter(items, getActivity());
                    //Log.d("KEY", "TAMANO ITEM SEARCH: " + items_search.size());

                    rvItems.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvItems.setAdapter(itemOfferSubcategoryAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("INFO", error.getStackTrace().toString());

            }
        }){
            @Override
            protected Map getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_category", String.valueOf(category_id));

                return params;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


}