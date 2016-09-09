package com.example.androiddam.proyectofinalandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androiddam.proyectofinalandroid.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SetLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private Button mBtnFind;
    private GoogleMap mMap;
    private EditText etPlace;
    private String locality;
    private String zipcode;
    private String adressLine;
    private boolean hasMarker = false;

    private List<Address> addresses;

    private Geocoder geocoder;

    private double lat;
    private double lng;

    private FloatingActionButton fab;

    private void hideSoftKeyboard(){
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPlace.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        geocoder = new Geocoder(getApplicationContext());

        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Getting reference to the find button
        mBtnFind = (Button) findViewById(R.id.btn_show);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Getting reference to EditText
        etPlace = (EditText) findViewById(R.id.et_place);

        etPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    etPlace.setText("");
                }
            }
        });




        // Setting click event listener for the find button
        mBtnFind.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Getting the place entered
                String location = etPlace.getText().toString();
                addresses = null;
                hideSoftKeyboard();

                if (location == null || location.equals("")) {
                    //Toast.makeText(getBaseContext(), "No has indicado lugar", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    addresses = geocoder.getFromLocationName(location, 1);
                }catch (IOException e){
                    e.printStackTrace();
                }

                if (addresses.get(0) == null) {
                    //Toast.makeText(getBaseContext(), "No has indicado lugar", Toast.LENGTH_SHORT).show();
                }else {
                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(1)));
                    hasMarker = true;
                    //Toast.makeText(getBaseContext(),String.valueOf(address.getLongitude()), Toast.LENGTH_SHORT).show();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                    updateAdress(addresses.get(0).getPostalCode(),addresses.get(0).getLocality(),addresses.get(0).getAddressLine(1),
                            latLng.latitude,latLng.longitude);
                }

            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng cat = new LatLng(41.42,2.08524);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cat,11));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getAddressLine(1)));
                hasMarker = true;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                updateAdress(addresses.get(0).getPostalCode(),addresses.get(0).getLocality(),addresses.get(0).getAddressLine(1),
                        latLng.latitude,latLng.longitude);

                etPlace.setText(addresses.get(0).getAddressLine(1));
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (hasMarker){
                    System.out.println("SAPEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                    Toast.makeText(SetLocationActivity.this, "VAMONOS SEÑOR!", Toast.LENGTH_SHORT).show();
                    Intent i = getIntent();
                    i.putExtra("lat",lat);
                    i.putExtra("lng",lng);
                    i.putExtra("zipcode",zipcode);
                    i.putExtra("line",adressLine);
                    i.putExtra("locality",locality);

                    setResult(RESULT_OK, i);
                }else {
                    //Toast.makeText(getApplicationContext(),"No has añadido localización",Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(),"No has añadido localización",Toast.LENGTH_LONG).show();
                }



                finish();
            }
        });

    }


    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }catch(Exception e){
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }
    /** A class, to download Places from Geocoding webservice */
    private class DownloadTask extends AsyncTask<String, Integer, String>{

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){


        }


    }
    private void updateAdress(String zipcode, String locality, String line, double lat, double lng){

        this.locality = locality;
        this.zipcode = zipcode;
        this.adressLine = line;
        this.lat = lat;
        this.lng = lng;
    }


}



