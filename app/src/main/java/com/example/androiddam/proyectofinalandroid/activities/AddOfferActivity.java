package com.example.androiddam.proyectofinalandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.fragments.AddOfferFragment;
import com.example.androiddam.proyectofinalandroid.fragments.AddSubcategoryFragment;
import com.example.androiddam.proyectofinalandroid.fragments.SelectTypeJobFragment;

public class AddOfferActivity extends AppCompatActivity {
    public static int posicion = 0;
    public static boolean izquierda = false;
    public static boolean derecha = false;
    public static boolean init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer_activity);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new AddOfferFragment()).commit();
        init = true;
    }


    public void goLookUpSubcategory(int id){
        posicion++;
        izquierda = true;
        Toast.makeText(AddOfferActivity.this, "GOLOOK ID: "+ id, Toast.LENGTH_SHORT).show();
        AddSubcategoryFragment addSubcategoryFragment = new AddSubcategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id_category_parent", id);
        addSubcategoryFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, addSubcategoryFragment).commit();
    }

    public void goSelectTypeJob(){
        posicion++;
        izquierda = true;
        SelectTypeJobFragment selectTypeJobFragment = new SelectTypeJobFragment();
        //Bundle bundle = new Bundle();
        //addSubcategoryFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.content, selectTypeJobFragment).commit();

    }


    @Override
    public void onBackPressed() {
        posicion--;
        if (posicion==1){
            AddSubcategoryFragment addSubcategoryFragment = new AddSubcategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id_category_parent", AddSubcategoryFragment.category_id);
            addSubcategoryFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.content, addSubcategoryFragment).commit();

        }
        if (posicion==0)
            getSupportFragmentManager().beginTransaction().add(R.id.content, new AddOfferFragment()).commit();
        if (posicion==-1)
            finish();
    }

    @Override
    protected void onDestroy() {
        izquierda = false;
        derecha = false;
        posicion = 0;
        init=false;
        super.onDestroy();
    }
}
