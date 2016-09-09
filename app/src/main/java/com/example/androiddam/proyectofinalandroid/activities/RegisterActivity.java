package com.example.androiddam.proyectofinalandroid.activities;

        import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androiddam.proyectofinalandroid.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String REGISTER_URL = "http://proyecto-dam.esy.es/php/register.php";

    public static final String KEY_EMAIl="email";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_PHONE="phone";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private EditText editTextPhone;

    private Button btnRegister;

    private String email;
    private String password;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = (EditText) findViewById(R.id.et_R_email);
        editTextPassword = (EditText) findViewById(R.id.et_password);
        editTextPasswordConfirm = (EditText) findViewById(R.id.et_password_repeat);
        editTextPhone = (EditText) findViewById(R.id.et_phoneNumber);

        btnRegister = (Button) findViewById(R.id.bnt_registry);
        btnRegister.setOnClickListener(this);
    }



    public void userLogin(){

        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        phone = String.valueOf(editTextPhone.getText());


        if (email.contains("@")){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("existingMail")){
                                editTextEmail.setError("El correo electrónico ya existe");
                                editTextEmail.setText("");
                            }else if(response.equals("existingPhone")){
                                editTextPhone.setError("El número de teléfono ya existe");
                                editTextPhone.setText("");
                            }else{
                                finish();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put(KEY_EMAIl,email);
                    map.put(KEY_PASSWORD,password);
                    map.put(KEY_PHONE,phone);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else{
            editTextEmail.setError("Introduce un correo valido");
            //Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_edit_text);
            //editTextEmail.startAnimation(shake);

        }



    }



    @Override
    public void onClick(View v) {
        if (editTextPassword.getText().toString().equals(editTextPasswordConfirm.getText().toString())) {
            userLogin();
        }else{
            editTextPassword.setError("El password no coincide");
            editTextPasswordConfirm.setError("El password no coincide");
        }


    }
}
