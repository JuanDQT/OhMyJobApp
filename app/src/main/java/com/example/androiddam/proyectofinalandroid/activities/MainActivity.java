package com.example.androiddam.proyectofinalandroid.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;
import com.example.androiddam.proyectofinalandroid.widgets.CircularAnim;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*
    PREFERENCE SHARED: http://androidopentutorials.com/android-sharedpreferences-tutorial-and-example/
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOGIN_URL = "http://proyecto-dam.esy.es/php/login.php";
    //public static final String LOGIN_URL = "http://virtual309.ies-sabadell.cat/virtual309/proyectofinal/login.php";
    private static String email;
    private static String password;

    private TextInputEditText tietEmail;
    private TextInputEditText tietPassword;

    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;

    private TextView tvLogin;
    private Button btn_login;
    private ProgressBar pb_login;

    public static final String PREFS_NAME = "JSON_VALUE";
    public static final String PREFS_KEY = "JSON_KEY";


    //ALMACENA EL JSON
    public static void set_json(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(PREFS_KEY, text);
        editor.commit();
    }

    //DEVUELVE EL JSON
    public static String get_json(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, null);
        return text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (get_json(getApplicationContext()) != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        } else {

            tietPassword = (TextInputEditText) findViewById(R.id.et_password);
            tietEmail = (TextInputEditText) findViewById(R.id.et_email);
            tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
            tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

            pb_login = (ProgressBar) findViewById(R.id.pg_login);

            btn_login = (Button) findViewById(R.id.btn_login);
            btn_login.setOnClickListener(this);

            tvLogin = (TextView) findViewById(R.id.tv_register);
            tvLogin.setOnClickListener(this);

            tietEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    tilEmail.setError(null);

                }
            });

            tietPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    tilPassword.setError(null);

                }
            });


        }



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                userLogin();
                break;
            case R.id.tv_register:
                registerUser();
                break;
        }

    }

    private void userLogin() {
        btn_login.setVisibility(View.GONE);
        pb_login.setVisibility(View.VISIBLE);

        email = tietEmail.getText().toString().trim();
        password = tietPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("RESPUESTA: " + response);

                //EN CASO QUE NO DE ERROR
                if (!response.trim().equals("failure")) {
                    tilEmail.setError(null);
                    tilPassword.setError(null);
                    openProfile();
                    //GUARDAMOS EL JSON
                    set_json(getApplicationContext(), response);

                } else {
                    tilEmail.setError("Usuario o contraseña incorrectos");
                    tilPassword.setError("Usuario o contraseña incorrectos");

                    tietPassword.setText("");

                    pb_login.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Hay problemas con la red. Vuelvelo a intentarlo", Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void registerUser() {

        final Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

        CircularAnim.hide(tvLogin)
                .onAnimationEndListener(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        CircularAnim.fullActivity(MainActivity.this, tvLogin).colorOrImageRes(R.color.colorAccent)
                                .go(new CircularAnim.OnAnimationEndListener() {
                                    @Override
                                    public void onAnimationEnd() {
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                    }
                }).go();
    }


    private void openProfile() {
        CircularAnim.fullActivity(MainActivity.this, pb_login).colorOrImageRes(R.color.colorAccent)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    }
                });

    }

    public static String getJsonValue(String json, String key) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String result = jsonObject.getString(key);
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}