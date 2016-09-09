package com.example.androiddam.proyectofinalandroid.activities;




import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androiddam.proyectofinalandroid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddWorkActivity extends AppCompatActivity implements View.OnClickListener {

    //public static final String CATEGORIES_URL = "http://virtual309.ies-sabadell.cat/virtual309/proyectofinal/getCategories.php";
    public static final String CATEGORIES_URL = "http://proyecto-dam.esy.es/php/getCategories.php";
    //public static final String REGISTER_URL = "http://virtual309.ies-sabadell.cat/virtual309/proyectofinal/uploadOffer.php";
    public static final String REGISTER_URL = "http://proyecto-dam.esy.es/php/uploadOffer.php";

    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_PRICE = "price";
    public static final String KEY_ZIPCODE = "zipcode";
    public static final String KEY_ADRESSLINE = "adressLine";
    public static final String KEY_LOCALITY = "locality";
    public static final String KEY_ID_USER = "id_user";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";


    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextPrice;
    private Spinner spinnerCategory;
    private Spinner spinnerSubCategory;
    private EditText editTextSubcategory;
    private ArrayList<String> categoriesArr = new ArrayList<String>();
    private ArrayList<String> subCategoriesArr = new ArrayList<String>();
    private JSONArray categories;

    String adressLine;
    String locality;
    private EditText editTextZipcode;




    private Button btnUpload;

    private String name;
    private String description;
    private String price;
    private String id_user;
    private String category;
    private String zipcode;
    private String user_json;
    private String customSubCategory;
    private int newCategory;
    private String categoryParen;

    private double lng;
    private double lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);

        editTextName = (EditText) findViewById(R.id.et_work_name);
        editTextDescription = (EditText) findViewById(R.id.et_work_description);
        editTextPrice = (EditText) findViewById(R.id.et_work_Hprice);
        editTextZipcode = (EditText) findViewById(R.id.et_work_zipcode);
        editTextSubcategory = (EditText) findViewById(R.id.et_sub_category);

        btnUpload = (Button) findViewById(R.id.btn_uploadOffer);

        categoriesArr.add("Selecciona categoria");


        spinnerCategory = (Spinner) findViewById(R.id.sp_category);
        spinnerSubCategory = (Spinner) findViewById(R.id.sp_sub_category);

        spinnerSubCategory.setVisibility(View.GONE);

        btnUpload.setOnClickListener(this);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(categoriesRequest);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String name = spinnerCategory.getSelectedItem().toString();

                subCategoriesArr.clear();
                try {
                    JSONObject subCategoriesObject = categories.getJSONObject(position - 1);
                    JSONArray subCategoriesArray = subCategoriesObject.getJSONArray("sub_array");

                    subCategoriesArr.add("Selecciona subcategoría");
                    for (int i = 0; i < subCategoriesArray.length(); i++) {
                        String subCategoriesValue = subCategoriesArray.getJSONObject(i).getString("name_sub_category");
                        subCategoriesArr.add(subCategoriesValue);

                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddWorkActivity.this, R.layout.spinner_item_white, subCategoriesArr);
                    //specify the layout to appear list items
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    //data bind adapter with both spinners)
                    if (!name.toLowerCase().equals("otros")) {
                        spinnerSubCategory.setAdapter(arrayAdapter);
                        spinnerSubCategory.setVisibility(View.VISIBLE);
                        editTextSubcategory.setVisibility(View.GONE);
                        //Toast.makeText(AddWorkActivity.this, String.valueOf(spinnerSubCategory.getCount()), Toast.LENGTH_SHORT).show();
                    } else {
                        editTextSubcategory.setVisibility(View.VISIBLE);
                        spinnerSubCategory.setVisibility(View.GONE);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    StringRequest categoriesRequest = new StringRequest(Request.Method.POST, CATEGORIES_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        //Do it with this it will work
                        categories = new JSONArray(response);
                        for (int i = 0; i < categories.length(); i++) {
                            String categoriesValue = categories.getJSONObject(i).getString("name_category");

                            categoriesArr.add(categoriesValue);
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddWorkActivity.this, R.layout.spinner_item_white, categoriesArr);
                        //specify the layout to appear list items
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //data bind adapter with both spinners
                        spinnerCategory.setAdapter(arrayAdapter);


                    } catch (JSONException e) {
                        //EN CASO DE NO RECIBIR JSON CON ESE USER EXISTENTE-->
                    }


                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(AddWorkActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {

    };


    public void addOffer() {

        String customSubCategoryOut;
        categoryParen = spinnerCategory.getSelectedItem().toString();
        newCategory = 0;
        name = editTextName.getText().toString().trim();
        description = editTextDescription.getText().toString().trim();
        price = editTextPrice.getText().toString().trim();




        if (spinnerSubCategory.getVisibility() != View.GONE)
            category = spinnerSubCategory.getSelectedItem().toString();
        else {
            customSubCategory = editTextSubcategory.getText().toString().trim().toLowerCase();
            customSubCategoryOut = customSubCategory.substring(0, 1).toUpperCase() + customSubCategory.substring(1);
            if (!customSubCategoryOut.equals("")) {
                category = customSubCategoryOut;
                newCategory = 1;
            } else {
                editTextSubcategory.setError("Introduce una categoría");
            }
        }


        zipcode = editTextZipcode.getText().toString().trim();
        user_json = MainActivity.get_json(this);
        id_user = MainActivity.getJsonValue(user_json, "id");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Integer id_offer;
                        Integer id_category;
                        String name_category;
                        //Toast.makeText(AddWorkActivity.this, response, Toast.LENGTH_LONG).show();

                        if (response.equals("faliure")) {
                            //Toast.makeText(AddWorkActivity.this, response, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                //Do it with this it will work
                                JSONObject json_offer = new JSONObject(response);

                                id_offer = json_offer.getInt("id_offer");
                                id_category = json_offer.getInt("id_category");
                                name_category = json_offer.getString("category_name");


/*                                Intent intent = new Intent(getApplicationContext(), AddTagActivity.class);
                                intent.putExtra("id_offer", id_offer);
                                intent.putExtra("id_category", id_category);
                                intent.putExtra("name_category", name_category);
                                startActivity(intent);*/
                                finish();


                            } catch (JSONException e) {
                                //Toast.makeText(AddWorkActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                            }


                        }


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
                map.put(KEY_DESCRIPTION, description);
                map.put(KEY_CATEGORY, category);
                map.put(KEY_NAME, name);
                map.put(KEY_PRICE, price);
                map.put(KEY_ZIPCODE, zipcode);
                map.put(KEY_ID_USER, id_user);
                map.put("new_category", String.valueOf(newCategory));
                map.put("category_parent", categoryParen);
                map.put(KEY_ADRESSLINE,adressLine);
                map.put(KEY_LOCALITY,locality);
                map.put(KEY_LONGITUDE,String.valueOf(lng));
                map.put(KEY_LATITUDE,String.valueOf(lat));



                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        v.getId();
        EditText[] fields = new EditText[]{
                editTextName,
                editTextDescription,
                editTextPrice,
                editTextZipcode,
        };
        EditText failed = validateEt(fields);
        if (failed != null) {
            failed.setError("Rellena este campo");
        } else if (spinnerCategory.getSelectedItemPosition() == 0) {
            //Toast.makeText(AddWorkActivity.this, "Selecciona categoría", Toast.LENGTH_LONG).show();
        } else {
            addOffer();
        }

    }

    private EditText validateEt(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return currentField;
            }
        }
        return null;
    }

    public void openMap(View v) {
        Intent i = new Intent(getApplicationContext(),SetLocationActivity.class);
        startActivityForResult(i, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Comprobamos si el resultado de la segunda actividad es "RESULT_CANCELED".
        if (resultCode == RESULT_CANCELED) {
            // Si es así mostramos mensaje de cancelado por pantalla.
            //Toast.makeText(this, "Resultado cancelado", Toast.LENGTH_SHORT).show();
        } else {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            Bundle extras = data.getExtras();
            adressLine = extras.getString("line");
            locality = extras.getString("locality");
            zipcode = extras.getString("zipcode");
            lat = extras.getDouble("lat");
            lng = extras.getDouble("lng");

            editTextZipcode.setText(adressLine);


        }
    }



}
