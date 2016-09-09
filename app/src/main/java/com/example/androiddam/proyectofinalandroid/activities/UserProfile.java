package com.example.androiddam.proyectofinalandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.CircleTransform;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class UserProfile extends Activity {
    private ImageView iv_fondo;
    private CircleImageView civ_pic;
    private TextView tv_contacts;
    private TextView tv_puntos;
    private TextView tv_description;
    private TextView tv_names;

    private final String URL_PROFILE = "http://proyecto-dam.esy.es/php/getUserProfileById.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);



        final Bundle bundle = getIntent().getExtras();
        System.out.println("BUNDLE RECOGIDO: "+ bundle.getInt("id"));

        iv_fondo = (ImageView) findViewById(R.id.iv_fondo);
        civ_pic = (CircleImageView) findViewById(R.id.civ_pic);
        tv_contacts = (TextView) findViewById(R.id.tv_contacts);
        tv_puntos = (TextView) findViewById(R.id.tv_puntos);
        tv_description = (TextView)findViewById(R.id.tv_description);
        tv_names = (TextView) findViewById(R.id.tv_names);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String name = jsonObject.getString("name");
                    String lastname = jsonObject.getString("lastname");
                    String img_path = jsonObject.getString("img_path");
                    String description = jsonObject.getString("description");
                    Integer contacts = jsonObject.getInt("contacts");
                    Integer media = jsonObject.getInt("media");

                    //asignamos names
                    tv_names.setText(name + " " + lastname);
                    //asignamos imagen de fondo y circulo
                    if (img_path.equals("null") || img_path.isEmpty()) {
                        Picasso.with(getApplicationContext()).load(R.drawable.new_user).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new BlurTransformation(getApplicationContext(),25)).into(iv_fondo);
                        Picasso.with(getApplicationContext()).load(R.drawable.new_user).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new CircleTransform()).into(civ_pic);

                    }else {
                        Picasso.with(getApplicationContext()).load(img_path).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new BlurTransformation(getApplicationContext(),25)).into(iv_fondo);
                        Picasso.with(getApplicationContext()).load(img_path).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new CircleTransform()).into(civ_pic);
                    }
                    //asignamos la descripcion al texto
                    tv_description.setText(description);
                    //asignamos la cantidad de cotnactos que tiene
                    tv_contacts.setText(String.valueOf(contacts));
                    //asignamos los puntos de este user
                    tv_puntos.setText(String.valueOf(media));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(bundle.getInt("id")));
                return params;
            }
        };


        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


}
