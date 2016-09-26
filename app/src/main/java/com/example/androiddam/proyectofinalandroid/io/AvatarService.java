package com.example.androiddam.proyectofinalandroid.io;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juan on 25/09/2016.
 */

public class AvatarService {
    public static final String URL = "http://proyecto-dam.esy.es/php/getAvatarProfile.php";
    private static Avatar avatar;

    public static Avatar getNewAvatar(Context context, final int idUser){
        Log.d("USERPROFILE", "idddd: " + idUser);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    avatar = new Avatar(jsonObject.getString("name"), jsonObject.getString("lastname"), jsonObject.getString("birthdate"), jsonObject.getString("birthdate"));
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
                params.put("user_id", String.valueOf(idUser));
                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

        return avatar;

    }

    public static class Avatar{
        private String name;
        private String lastname;
        private String birthdate;
        private String imgPath;

        public Avatar(String name, String lastname, String birthdate, String imgPath) {
            this.name = name;
            this.lastname = lastname;
            this.birthdate = birthdate;
            this.imgPath = imgPath;
        }

        @Override
        public String toString() {
            return "Avatar{" +
                    "name='" + name + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", birthdate='" + birthdate + '\'' +
                    ", imgPath='" + imgPath + '\'' +
                    '}';
        }
    }
}
