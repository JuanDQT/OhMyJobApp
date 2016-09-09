package com.example.androiddam.proyectofinalandroid.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Mi_Perfil_fr extends Fragment implements View.OnClickListener{

    private Spinner sp_formacion;
    private EditText et_descripcion;
    private List<String> formaciones;
    private Activity activity;

    private ImageView iv_add;

    private boolean sp_first_time = true;

    public static final String CATEGORIES_URL = "http://proyecto-dam.esy.es/php/getNameCategories.php";
    public static final String SUBCATEGORIES_URL = "http://proyecto-dam.esy.es/php/getSubCategoriesById.php";

    private List<String> categoriesList;
    private ArrayAdapter<String> adapter_categories;

    public Mi_Perfil_fr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_mi__perfil, container, false);
        this.activity = getActivity();
        sp_formacion = (Spinner) view.findViewById(R.id.sp_formacion);
        et_descripcion = (EditText) view.findViewById(R.id.et_descripcion);
        iv_add = (ImageView) view.findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);


        categoriesList = new ArrayList<>();

        formaciones = new ArrayList<>();
        formaciones.add("Primaria");
        formaciones.add("ESO");
        formaciones.add("Bachillerato");
        formaciones.add("Ciclo medio");
        formaciones.add("Ciclo superior");
        formaciones.add("Universitario");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, formaciones);
        sp_formacion.setAdapter(adapter);


        return  view;
    }


    @Override
    public void onClick(View v) {
        //Object id = v.getId();
        System.out.println("STEP 1");

        switch (v.getId()) {
            case R.id.iv_add:
                System.out.println("PEPE");
                System.out.println("STEP 2");
                mostrarPopUp();
                break;
        }
    }

    private void mostrarPopUp() {
        LayoutInflater li = LayoutInflater.from(getActivity());

        View promptsView = li.inflate(R.layout.my_dialog_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setView(promptsView);

        // set dialog message

        alertDialogBuilder.setTitle("Seleccione sus intereses:");
        //alertDialogBuilder.setIcon(R.drawable.ic_launcher);
        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        final Spinner sp_category= (Spinner) promptsView.findViewById(R.id.sp_category);
        final Spinner sp_subcategory= (Spinner) promptsView.findViewById(R.id.sp_subcategory);

        //final List<String> categoriesList = new ArrayList();
        //final ArrayAdapter<String> ca_adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_white, categoriesList);

        //CARGAR CATEGORIAS


        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String seleccionado = sp_category.getSelectedItem().toString();
                final ArrayList<String> subCategoriesArr = new ArrayList<String>();

                System.out.println("SNAPE SABEPEE");

                if (!sp_first_time) {
                    System.out.println("COLORADO GARKA");

                    //Toast.makeText(getActivity(), "SELEC: "+ seleccionado, Toast.LENGTH_SHORT).show();
                    //
                    JsonArrayRequest json_get_categories = new JsonArrayRequest(Request.Method.POST, CATEGORIES_URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i=0;i<response.length();i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String name = jsonObject.getString("name_category");
                                    categoriesList.add(name);
                                }

                                adapter_categories = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, categoriesList);
                                sp_category.setAdapter(adapter_categories);

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
                            return super.getParams();
                        }
                    };

                    MySingleton.getInstance(getActivity()).addToRequestQueue(json_get_categories);
                    Toast.makeText(getActivity(), "ADAPTER SIZE: "+ adapter_categories.getCount(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), "LIST CATEGORIES SIZE: "+ categoriesList.size(), Toast.LENGTH_SHORT).show();


                    //

                }
                    sp_first_time = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // show it
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);

    }



}
