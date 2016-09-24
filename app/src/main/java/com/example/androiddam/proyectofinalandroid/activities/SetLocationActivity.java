package com.example.androiddam.proyectofinalandroid.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddam.proyectofinalandroid.R;
import com.google.android.gms.maps.CameraUpdate;
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

    private boolean enablePopUp = false;

    private LocationManager locationManager;

    private FloatingActionButton fabMyLocation;

    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPlace.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        geocoder = new Geocoder(this);

        fabMyLocation = (FloatingActionButton) findViewById(R.id.fab_my_location);

        fabMyLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                enablePopUp = true;
                Toast.makeText(SetLocationActivity.this, "Localizando...", Toast.LENGTH_SHORT).show();

                Log.d("SETLOCATION", "0");
                final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(SetLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SetLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1000, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);

                        Log.d("SETLOCATION", "1");

                        try {
                            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            updateAdress(addresses.get(0).getPostalCode(), addresses.get(0).getLocality(), addresses.get(0).getAddressLine(1),
                                    latLng.latitude, latLng.longitude);

                            Log.d("SETLOCATION", "XLAT: " + lat);
                            Log.d("SETLOCATION", "XLNG: " + lng);
                            Log.d("SETLOCATION", "XLOCATION: " + location);
                            Log.d("SETLOCATION", "XZIP: " + zipcode);
                            Log.d("SETLOCATION", "XADDRESS: " + adressLine);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mMap.animateCamera(cameraUpdate);
                        if (ActivityCompat.checkSelfPermission(SetLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SetLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            Log.d("SETLOCATION", "1");

                            try {
                                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                                updateAdress(addresses.get(0).getPostalCode(), addresses.get(0).getLocality(), addresses.get(0).getAddressLine(1),
                                        latLng.latitude, latLng.longitude);

                                Log.d("SETLOCATION", "LAT: " + lat);
                                Log.d("SETLOCATION", "LNG: " + lng);
                                Log.d("SETLOCATION", "LOCATION: " + location);
                                Log.d("SETLOCATION", "ZIP: " + zipcode);
                                Log.d("SETLOCATION", "ADDRESS: " + adressLine);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            return;
                        }
                        locationManager.removeUpdates(this);
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                }); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
                //mMap.getUiSettings().enabl
            }
        });

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
                if (!hasFocus) {
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
                //addresses = null;
                hideSoftKeyboard();

                if (location == null || location.equals("")) {
                    Toast.makeText(SetLocationActivity.this, "No has indicado lugar", Toast.LENGTH_SHORT).show();
                    return;
                }

                enablePopUp = true;
                setMyLocation(location);

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng cat = new LatLng(41.42, 2.08524);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cat, 11));
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

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                enablePopUp = true;
                setMyLocation(latLng);

                etPlace.setText(addresses.get(0).getAddressLine(1));
            }
        });

        //CAMARA SE DETIENE
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                Log.d("SETLOCATION", "CAMARA DETENIDA");
                if (enablePopUp) {
                    showPopUp();
                    enablePopUp = false;
                }
            }
        });


    }

    private void showPopUp() {
        Log.d("SETLOCATION", "LAT: " + lat);
        Log.d("SETLOCATION", "LNG: " + lng);
        Log.d("SETLOCATION", "ADDRESSLINE: " + addresses);
        Log.d("SETLOCATION", "LOCALITY: " + locality);
        Log.d("SETLOCATION", "ZIPCODE: " + zipcode);

        final AlertDialog.Builder alertdialog_builder = new AlertDialog.Builder(this);
        alertdialog_builder.setTitle("Ubicación");
        alertdialog_builder.setMessage("¿Es correcta tu ubicación?");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_location_ok, null);
        TextView tvLocation = (TextView) view.findViewById(R.id.tv_location);

        tvLocation.setText(addresses.get(0).getAddressLine(1) + "");
        Log.d("SETLOCATION", "[LLEGA]LAT: " + lat + "\tLONG: " + lng + "\tZIPCODE: " + zipcode + "\tLOCALITY: " + locality);


        //
        //CONSEGUIR CP Y LOCALIDAD COMO LO AHCE ADDRES
        //

        alertdialog_builder.setView(view);

        alertdialog_builder.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("SETLOCATION", "LAT: " + lat + "\tLONG: " + lng + "\tZIPCODE: " + zipcode + "\tLOCALITY: " + locality);
                Intent intent = getIntent();
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("zipcode", zipcode);
                //intent.putExtra("line",adressLine);
                intent.putExtra("location", locality);
                //Toast.makeText(SetLocationActivity.this, "LAT: "+ lat + "\tLONG: "+ lng + "\tZIPCODE: "+ zipcode + "\tLOCALITY: "+ locality, Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK, intent);
                finish();

            }
        });

        alertdialog_builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        alertdialog_builder.show();

    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
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
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }


    /**
     * A class, to download Places from Geocoding webservice
     */
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {


        }


    }

    private void updateAdress(String zipcode, String locality, String line, double lat, double lng) {
        Log.d("SETLOCATION", "ME HE EJECUTADO");
        this.locality = locality;
        if (zipcode == null)
            this.zipcode = "0";
        else
            this.zipcode = zipcode;
        this.adressLine = line;
        this.lat = lat;
        this.lng = lng;
    }

    public void setMyLocation(LatLng latLng) {
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("NORMAL", "0");
        if (addresses.get(0) == null) {
            Toast.makeText(SetLocationActivity.this, "No has indicado lugar[NULL]", Toast.LENGTH_SHORT).show();
            Log.d("SETLOCATION", "NO HAS INDICADO EL LUGAR[NULL]");
        } else {
            mMap.clear();
            Log.d("NORMAL", "SIMPLE CLICK");

            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMap.addMarker(new MarkerOptions().position(latLng).title(addresses.get(0).getAddressLine(1)));
            hasMarker = true;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
            updateAdress(addresses.get(0).getPostalCode(), addresses.get(0).getLocality(), addresses.get(0).getAddressLine(1), latLng.latitude, latLng.longitude);
        }
    }

    public void setMyLocation(String location) {
        Log.d("SETLOCATION", "STRING");
        try {
            addresses = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.d("LATLNG", "0");
        if (addresses.get(0) == null) {
            Toast.makeText(SetLocationActivity.this, "No has indicado lugar[NULL]", Toast.LENGTH_SHORT).show();
            Log.d("SETLOCATION", "NO HAS INDICADO EL LUGAR[NULL]");
        } else {
            Log.d("SETLOCATION", "ELSE");
            Address address = addresses.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(1)));
            hasMarker = true;
            //Toast.makeText(getBaseContext(),String.valueOf(address.getLongitude()), Toast.LENGTH_SHORT).show();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
            updateAdress(addresses.get(0).getPostalCode(), addresses.get(0).getLocality(), addresses.get(0).getAddressLine(1),
                    latLng.latitude, latLng.longitude);
        }
    }

}



