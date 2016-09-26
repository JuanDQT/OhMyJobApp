package com.example.androiddam.proyectofinalandroid.fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.HomeActivity;
import com.example.androiddam.proyectofinalandroid.activities.MainActivity;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Avatar_fr extends Fragment {
    public static final String URL = "http://proyecto-dam.esy.es/php/getAvatarProfile.php";

    public static CircleImageView circleImageView;
    private static View view;
    public static Bitmap bitmap;

    //
    // EDU
    public static final String KEY_NAME="name";
    public static final String KEY_LASTNAME="lastname";
    public static final String KEY_DATE="datebirth";
    public static final String KEY_USER="id_user";


    private static EditText editTextName;
    private static EditText editTextLastname;
    private static DatePicker datePickerBirth;

    private LinearLayout llAvatar;

    private Button btnGuardar;

    private String name;
    private String lastname;
    private int year;
    private int month;
    private int day;
    private String dateBirth;
    private String id_user;
    private String user_json;
    //

    public Avatar_fr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.avatar_fr, container, false);
        circleImageView = (CircleImageView) view.findViewById(R.id.img_profile);
        llAvatar = (LinearLayout) view.findViewById(R.id.ll_avatar);
        btnGuardar = (Button) view.findViewById(R.id.btn_guardar);

        llAvatar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        //

        setProperties(HomeActivity.getUserId(getContext())+ "");
        Log.d("USERPROFILE", "before");
        //Log.d("USERPROFILE", AvatarService.getNewAvatar(getActivity(), Integer.valueOf(MainActivity.getJsonValue(user_json, "id"))).toString() + "");

        //
        String img_path = getArguments().getString("img_path");
        String b_username = getArguments().getString("username");
        String b_lastname = getArguments().getString("lastname");
        String b_birthdate = getArguments().getString("birthdate");

        Log.d("USERPROFILE", "dia: " + day + " mes: " + month + " year: " + year);


        System.out.println("RUTA RECIBIDA ES: "+ img_path);

        if (img_path == null || img_path.isEmpty()) {
            Picasso.with(getActivity()).load(R.drawable.new_user).memoryPolicy(MemoryPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_STORE)
                    .error(R.drawable.new_user)
                    .into(circleImageView);
        }else{
            Picasso.with(getActivity()).load(img_path).memoryPolicy(MemoryPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_STORE)
                    .error(R.drawable.new_user)
                    .into(circleImageView);
        }

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getActivity(),"Subir foto de perfil","Seleccione o borre la foto de perfil");
            }
        });

        //EDU
        user_json = MainActivity.get_json(getActivity());
        id_user = MainActivity.getJsonValue(user_json, "id");

        editTextName = (EditText) view.findViewById(R.id.et_R_name);
        editTextLastname = (EditText) view.findViewById(R.id.et_R_lastname);
        datePickerBirth = (DatePicker) view.findViewById(R.id.dP_date);

/*        actualizarDatePicker(datePickerBirth,b_birthdate);

        editTextName.setText(b_username);
        editTextLastname.setText(b_lastname);*/

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getContext()).updateProfile();
                getActivity().getSupportFragmentManager().beginTransaction().remove(Avatar_fr.this).commit();


            }
        });

        return view;
    }

    private void actualizarDatePicker(DatePicker datePickerBirth, String birthdate) {
        Integer year = Integer.valueOf(birthdate.substring(0, 4));
        Integer month = Integer.valueOf(birthdate.substring(5, 7));
        //Integer month = Integer.valueOf(birthdate.substring(5, 7)) - 1;
        Integer day = Integer.valueOf(birthdate.substring(8, 10));
        Log.d("USERPROFILE", "dia: " + day + " mes: " + month + " year: " + year);
        //System.out.println("dia: "+ day + " mes: "+ month + " year: "+ year);
        datePickerBirth.updateDate(year,month,day);
    }

    public void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Seleccionar foto", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((HomeActivity)getActivity()).getImage();
            }
        });

        builder.show();
    }

    //RECIBIMOS LA IMAGEN Y ACTUALIZAMOS NUESTRO CIRCLE VIEW
    public void updatePic(Bitmap bitmap){
        this.bitmap = bitmap;

/*        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_avatar);
        linearLayout.setLayerType(View.LAYER_TYPE_SOFTWARE, null);*/

        circleImageView.setImageBitmap(bitmap);


    }

    public static String getTVName(){
        return editTextName.getText().toString();
    }

    public static String getTVLastName(){
        return editTextLastname.getText().toString();
    }

    public static String getDPBirthDate(){
        String data = datePickerBirth.getYear() + "-" + datePickerBirth.getMonth() + "-" + datePickerBirth.getDayOfMonth();
        Log.d("USERPROFILE", "getdb: " + data);
        return data;
    }

    public static void setTVName(){
        if (editTextName != null) {
            editTextName.setText("");
        }
    }




    public void setProperties(final String idUser){
        Log.d("USERPROFILE", "idddd: " + idUser);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    actualizarDatePicker(datePickerBirth,jsonObject.getString("birthdate"));
                    editTextName.setText(jsonObject.getString("name"));
                    editTextLastname.setText(jsonObject.getString("lastname"));

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
                params.put("user_id", idUser);
                return params;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }



}
