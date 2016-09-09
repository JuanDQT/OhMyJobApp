package com.example.androiddam.proyectofinalandroid.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.ContactoAdapter;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;
import com.example.androiddam.proyectofinalandroid.model.Contacto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_fr extends Fragment {

    RecyclerView recyclerView;
    private static final String CONTACTS_URL = "http://proyecto-dam.esy.es/php/getContacts.php";
    public static final String DELETE_CONTACT_URL = "http://proyecto-dam.esy.es/php/deleteContact.php";
    public static Activity activity;

    private static ArrayList<Contacto> contactoList;
    private static ContactoAdapter contactoAdapter;

    public Contact_fr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_contact, container, false);
        this.activity = getActivity();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_ofertas);
        recyclerView.setHasFixedSize(false);

        contactoList = new ArrayList<>();

        JsonArrayRequest jsonGetContacts = new JsonArrayRequest(Request.Method.POST, CONTACTS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("OK");

                for (int i = 0; i< response.length(); i++) {

                    try {
                        JSONObject object = response.getJSONObject(i);
                        contactoList.add(new Contacto(object.getInt("id_contacto"), object.getInt("accepted"), object.getString("name"), object.getString("lastname"), object.getString("img_path")));
                        System.out.println("ID CONT: "+ object.getInt("id_contacto") + " NAME: "+ object.getString("name") + " LASTNAME: "+ object.getString("lastname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                contactoAdapter = new ContactoAdapter(contactoList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false));
                recyclerView.setAdapter(contactoAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<>();
                params.put("id", String.valueOf("1"));

                return params;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonGetContacts);



        return view;

    }

    public static void removeItem(int position) {
        position--;


        System.out.println("***POSITION: "+ position + " de lista size: "+ contactoList.size());
        //System.out.println("pos 0: "+ contactoList.get(0));
        //System.out.println("pos 0: "+ contactoList.get(1));

        //System.out.println("POSITION : "+ position);
        //Toast.makeText( activity, "SAPE " + (position) , Toast.LENGTH_SHORT).show();

        if (position == -1) {
            position = 0;
        }

        contactoList.remove(position);
        contactoAdapter.notifyItemRemoved(position);
        contactoAdapter.notifyItemRangeChanged(position, contactoList.size());
    }



}
