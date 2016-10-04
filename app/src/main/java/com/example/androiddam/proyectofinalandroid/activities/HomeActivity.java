package com.example.androiddam.proyectofinalandroid.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.OfertaAdapter;
import com.example.androiddam.proyectofinalandroid.adapters.OptionMenuAdapter;
import com.example.androiddam.proyectofinalandroid.controllers.MySingleton;
import com.example.androiddam.proyectofinalandroid.fragments.Avatar_fr;
import com.example.androiddam.proyectofinalandroid.fragments.Contact_fr;
import com.example.androiddam.proyectofinalandroid.fragments.Mensajes_fr;
import com.example.androiddam.proyectofinalandroid.model.ItemService;
import com.example.androiddam.proyectofinalandroid.model.Oferta;
import com.example.androiddam.proyectofinalandroid.model.OptionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private MaterialSearchView msvBuscador;
    private SwipeRefreshLayout srl_reload;
    private OfertaAdapter ofertaAdapter;
    private List<Fragment> fragmentOptions;
    private int fragment_position;
    private MenuItem m_acces;
    FragmentManager fragmentManager;
    private Fragment currentFragment;
    private Toolbar toolbar;
    private ProgressBar pbLoading;


    private ImageView iv_blur_profile;

    private RelativeLayout ll_blur;
    private CircleImageView circleImageView;
    //public static final String URL_OFFERS = "http://virtual309.ies-sabadell.cat/virtual309/proyectofinal/getOffers.php";
    public static final String URL_OFFERS = "http://proyecto-dam.esy.es/php/getOffers.php";
    //private String UPLOAD_URL ="http://virtual309.ies-sabadell.cat/virtual309/proyectofinal/masterUpdate.php";
    private String UPLOAD_URL = "http://proyecto-dam.esy.es/php/masterUpdate.php";

    private String SEARCH_URL = "http://proyecto-dam.esy.es/php/searchView.php";

    private static final String CATEGORIES_URL = "http://proyecto-dam.esy.es/php/getCategoriesV2.php";

    ActionBarDrawerToggle toggle;

    //BITMAP NAVI VIEW
    public Bitmap bitmap;
    TextView tv_name;
    private String url_img;

    private ArrayList<Oferta> ofertaList;

    private int PICK_IMAGE_REQUEST = 1;
    private int OPEN_MAPS_REQUEST = 2;
    private int RELOAD_ACTIVITY = 3;

    String adressLine;
    String locality;


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
    private double lat;

    private double lng;

    public static ArrayList<ItemService> itemServices;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_test,menu);
        super.onCreateOptionsMenu(menu);
        Log.e("LOG", "OnCreateOptionMenu");

        getMenuInflater().inflate(R.menu.menu_top, menu);

        //m_acces = menu.findItem(R.id.m_access);

        MenuItem item = menu.findItem(R.id.action_search);
        msvBuscador.setMenuItem(item);


        //Toast.makeText(HomeActivity.this, "CARAJO 1", Toast.LENGTH_SHORT).show();
/*        if (MainActivity.get_json(getApplicationContext()) == null) {
            m_acces.setVisible(true);
            //m_ok.setVisible(false);
        } else {
            //m_ok.setVisible(false);
            m_acces.setVisible(false);
        }*/
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e("LOG", "HOLI");

        toolbar = (Toolbar) findViewById(R.id.mi_toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_person_white_24dp));

        fragmentOptions = new ArrayList<>();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView) findViewById(R.id.rv_ofertas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        pbLoading = (ProgressBar) findViewById(R.id.pbLoagingOffers);
        recyclerView.setVisibility(View.INVISIBLE);

        ofertaList = new ArrayList<>();

        System.out.println("LECHERO");

        if (MainActivity.get_json(getApplicationContext()) != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);

            //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {


                //OCULTAR MENU CUANDO YA SE HA CERRADO EL MENU SLIDER
                @Override
                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    //Toast.makeText(Test.this, "CLOSEEEED", Toast.LENGTH_SHORT).show();
                }

                //OCULTAR MENU CUANDO YA SE HA ABIERTO
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    //super.onDrawerSlide(drawerView, 0); // this disables the arrow @ completed state
                    //Toast.makeText(Test.this, "OPEENNED", Toast.LENGTH_SHORT).show();
                }

                //OCULTAR TECLADO CUANDO SE DESPLAZA EL MENU BAR
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, 0); // this disables the animationToast

                    //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    //imm.hideSoftInputFromWindow(msvBuscador.getWindowToken(), 0);


                }
            };


            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();


            //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.nav_view);
            circleImageView = (CircleImageView) findViewById(R.id.profile_image);


            JSONObject json = null;
            String r_name = null;
            String lastname = null;
            String r_lastname = "";
            url_img = null;


            try {

                ListView lv_menu = (ListView) findViewById(R.id.lv_menu);
                //ListView lv_menu = (ListView) linearLayout.getRootView().findViewById(R.id.lv_menu);
                ArrayList<OptionMenu> options = new ArrayList<>();
                //options.add(new OptionMenu(R.drawable.profile, "Mi perfil", 0));
                options.add(new OptionMenu(R.drawable.upload, "Subir oferta", 0));
                options.add(new OptionMenu(R.drawable.contacts, "Contactos", 0));
                options.add(new OptionMenu(R.drawable.message, "Mensajes", 3));
                //options.add(new OptionMenu(R.drawable.settings, "Configuración", 0));
                options.add(new OptionMenu(R.drawable.sign_out, "Cerrar sesión", 0));

                OptionMenuAdapter optionMenuAdapter = new OptionMenuAdapter(this, options, R.layout.label_option);

                lv_menu.setAdapter(optionMenuAdapter);

                lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("POSITION: " + position);
                        System.out.println("SOY UNA IPCION PRESIONADA");
                        fragment_position = position;
                        switch (position) {
                            case 0:
                                //currentFragment = new AddWorkActivity();
                                //cambiarFragment(currentFragment);
                                Intent i = new Intent(getApplicationContext(), AddOfferActivity.class);
                                startActivity(i);
                                break;
                            case 1:
                                currentFragment = new Contact_fr();
                                //cambiarFragment(currentFragment);
                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                break;
                            case 2:
                                currentFragment = new Mensajes_fr();
                                //cambiarFragment(currentFragment);
                                getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                break;
                            case 3:
                                url_img = "";
                                Avatar_fr.setTVName();
                                System.out.println("CERRAR SESION:");
                                bitmap = null;
                                MainActivity.set_json(getApplicationContext(), null);
                                System.out.println("TIENES XML BORRADO: " + MainActivity.get_json(getApplicationContext()));

                                //finish();
                                finish();
                                startActivity(getIntent());

                                break;
                        }


                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);

                    }
                });

                //Toast.makeText(HomeActivity.this, "COUNT: "+ optionMenuAdapter.getCount(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(HomeActivity.this, "EN LISTA: "+options.size(), Toast.LENGTH_SHORT).show();
                //

                json = new JSONObject(MainActivity.get_json(this));
                String name = json.getString("name");
                r_name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
                lastname = json.getString("lastname");

                if (!lastname.isEmpty() && lastname != null) {
                    r_lastname = lastname.substring(0, 1).toUpperCase() + lastname.substring(1).toLowerCase();
                } else
                    r_lastname = "";


                url_img = json.getString("img_path").replaceAll("\\\\", "/");
                System.out.println("RUTA CORRECTA: " + url_img);

                tv_name = (TextView) findViewById(R.id.tv_name);
                //tv_name = (TextView) linearLayout.getRootView().findViewById(R.id.tv_name);
                tv_name.setText(r_name);

                TextView tv_rating = (TextView) findViewById(R.id.tv_rating);
                //TextView tv_rating = (TextView) linearLayout.getRootView().findViewById(R.id.tv_rating);
                tv_rating.setText(json.getInt("ptotal") + " puntos");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (circleImageView == null) {
                System.out.println("NULO");
            }

            ll_blur = (RelativeLayout) findViewById(R.id.ll_blur);
            //ll_blur = (RelativeLayout) linearLayout.getRootView().findViewById(R.id.ll_blur);

            //iv_blur_profile = (ImageView) ll_blur.getChildAt(0).findViewById(R.id.iv_blur);
            iv_blur_profile = (ImageView) findViewById(R.id.iv_blur);

            if (url_img == null || url_img.isEmpty()) {
                Picasso.with(getApplicationContext()).load(R.drawable.new_user).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new BlurTransformation(getApplicationContext(), 25))
                        .error(R.drawable.new_user).into(iv_blur_profile);


                Picasso.with(getApplicationContext()).load(R.drawable.new_user).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .error(R.drawable.new_user)
                        .into(circleImageView);
            } else {
                Picasso.with(getApplicationContext()).load(url_img).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new BlurTransformation(getApplicationContext(), 25))
                        .error(R.drawable.new_user).into(iv_blur_profile);


                Picasso.with(getApplicationContext()).load(url_img).memoryPolicy(MemoryPolicy.NO_CACHE).into(circleImageView);

            }

        } else
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        cargarOfertas();

        System.out.println("PASO 1.4");


        srl_reload = (SwipeRefreshLayout) this.findViewById(R.id.srl_reload);
        srl_reload.setColorSchemeResources(R.color.colorAccent);
        srl_reload.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                msvBuscador.setQuery("",false);
                msvBuscador.clearFocus();
                onBackPressed();
                cargarOfertas();
                srl_reload.setRefreshing(false);
            }

        });


        msvBuscador = (MaterialSearchView) findViewById(R.id.msv_buscador);
        msvBuscador.setVoiceSearch(true); //or false

        msvBuscador.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("NORMAL", "ENTER");

                //System.out.println("TEXTSUBMIT: "+ query);
                //enter
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                recyclerView.setVisibility(View.GONE);
                pbLoading.setVisibility(View.VISIBLE);

                Log.d("SEARCHVIEW", "CAMBIA A: " + newText);
                ofertaList.clear();
                Log.d("SEARCHVIEW", "ESTE MIDE: " + ofertaList.size());
                recyclerView.setAdapter(null);


                if (newText.isEmpty() || newText.length() == 0) {
                    //ofertaList.clear();
                    //recyclerView.setAdapter(null);
                    //VOLVEMOS A CARGAR LA LISTA DE OFERTAS
                    JsonArrayRequest defaultSearch = new JsonArrayRequest(Request.Method.POST, SEARCH_URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            pbLoading.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            Log.d("SEARCHVIEW", "LLEGO RESPONSE");
                            Log.d("SEARCHVIEW", "" + response);
                            Log.d("SEARCHVIEW", "" + "**MIDE**" + ofertaList.size());
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    String img_path = jsonObject.getString("img_path");
                                    Oferta oferta = new Oferta(jsonObject.getInt("id_offer"), jsonObject.getInt("price"), jsonObject.getString("name_category"), img_path, jsonObject.getInt("rating"), jsonObject.getString("img"));

                                    ofertaList.add(oferta);
                                    Log.d("SEARCHVIEW", oferta.toString());
                                }

                                System.out.println("ESTE MIDE: " + ofertaList.size());
                                ofertaAdapter = new OfertaAdapter(ofertaList, HomeActivity.this);//CASTEARLO
                                recyclerView.setAdapter(ofertaAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("user", e.getMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(defaultSearch);
                } else {
                    Log.d("SEARCHVIEW", "ELSE");
                    ofertaList.clear();
                    Log.d("SEARCHVIEW", "EESTE MIDE: " + ofertaList.size());
                    recyclerView.setAdapter(null);

                    recyclerView.setVisibility(View.GONE);
                    pbLoading.setVisibility(View.VISIBLE);
                    //
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, SEARCH_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pbLoading.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String img_path = jsonObject.getString("img_path");
                                    //Log.d("SEARCHVIEW", "");

                                    Oferta oferta = new Oferta(jsonObject.getInt("id_offer"), jsonObject.getInt("price"), jsonObject.getString("name_category"), img_path, jsonObject.getInt("rating"), jsonObject.getString("img"));

                                    ofertaList.add(oferta);
                                    Log.d("SEARCHVIEW", oferta.toString());
                                    Log.d("SEARCHVIEW", "*");
                                }
                                Log.d("SEARCHVIEW", "AFTERBCL");
                                Log.d("SEARCHVIEW", "AEESTE MIDE: " + ofertaList.size());
                                ofertaAdapter = new OfertaAdapter(ofertaList, HomeActivity.this);//CASTEARLO
                                recyclerView.setAdapter(ofertaAdapter);
                                Log.d("SEARCHVIEW", "AEESTE MIDE: " + ofertaList.size());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(HomeActivity.this, "Hubo un problema de conexión", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new Hashtable<>();
                            params.put("word", newText);

                            Log.d("SEARCHVIEW", "BUSCAR: " + newText);
                            return params;
                        }
                    };

                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

                }
                //


                return false;
            }
        });


        if (MainActivity.get_json(getApplicationContext()) != null) {

            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toolbar.setVisibility(View.GONE);
                    msvBuscador.setVisibility(View.GONE);

                    //Toast.makeText(HomeActivity.this, "EL PELUCA SAPEE", Toast.LENGTH_SHORT).show();
                    //m_ok.setVisible(true);
                    drawerLayout.closeDrawers();
                    currentFragment = new Avatar_fr();

                    String url_imagen = "";
                    try {
                        JSONObject json = new JSONObject(MainActivity.get_json(getApplicationContext()));
                        url_imagen = json.getString("img_path");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Bundle b = new Bundle();
                    b.putString("username", getUsername());
                    b.putString("lastname", getLastname());
                    b.putString("img_path", url_imagen);
                    b.putString("birthdate", getBirthdate());
                    currentFragment.setArguments(b);
                    cambiarFragment(currentFragment);
                }
            });

        }

        //PRECARGAMOS LA LISTA, PARA LA ACTIVITY SUBIR OFERTA
        itemServices = cargarCategorias();

    }

    private ArrayList<ItemService> cargarCategorias() {
        final ArrayList<ItemService> itemServices = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, CATEGORIES_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ItemService itemService = new ItemService(jsonObject.getInt("id_category"), jsonObject.getString("name_category"), jsonObject.getString("img"));
                        itemServices.add(itemService);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }


        });

        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);

        return itemServices;
    }


    private void cargarOfertas() {
        pbLoading.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(null);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL_OFFERS, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                recyclerView.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);

                Log.d("CARGAR", "RESPONSE: " + response.toString());
                ofertaList.clear();


                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        //String img_path = jsonObject.getString("img_path");

                        ofertaList.add(new Oferta(jsonObject.getInt("id_offer"), jsonObject.getInt("price"), jsonObject.getString("name_category"), jsonObject.getString("img_path"), jsonObject.getInt("rating"), jsonObject.getString("img")));

                    }

                    System.out.println("ESTE MIDE: " + ofertaList.size());
                    ofertaAdapter = new OfertaAdapter(ofertaList, HomeActivity.this);//CASTEARLO
                    recyclerView.setAdapter(ofertaAdapter);
                    ofertaAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("TAMAÑO INSIDE: " + ofertaList.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("NO HAY CONEXION");
                System.out.println(error.toString());
            }


        });

        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);

    }

    public void cambiarFragment(Fragment fragment) {
        //menu.setGroupVisible(R.id.m_inbox,false);
        Log.d("PROFILE", "ENTRE CAMBIAR FRAGMENT");

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.center_fr, fragment)
                .commit();


    }

    @Override
    public void onBackPressed() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        System.out.println("ATRAAAAAAAAAAAAAS");
        toolbar.setVisibility(View.VISIBLE);
        msvBuscador.setVisibility(View.VISIBLE);
        if (currentFragment != null) {

            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .hide(currentFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    .commit();
            //m_ok.setVisible(false);
            currentFragment = null;
            System.out.println("POSICION A LIMPIAR: " + fragment_position);

            Log.d("USERPROFILE", "resume");
        } else {
            currentFragment = null;
            System.out.println("ACTUALEMTEN NO TIENES FRAGMENTS DELANTE!!");

        }

        if (msvBuscador.isSearchOpen()) {
            msvBuscador.closeSearch();
        } else {
            super.onBackPressed();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, RELOAD_ACTIVITY);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public final void getImage() {
        startActivityForResult(Intent.createChooser(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), "Choose an image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    msvBuscador.setQuery(searchWrd, false);
                }
            }

            return;
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                System.out.println("ON RESULT");
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Log.i("BITMAP RESULT", bitmap.toString());

                if (bitmap.getWidth() > 250 || bitmap.getHeight() > 250) {
                    bitmap = resizeImage(bitmap);
                }

                ((Avatar_fr) currentFragment).updatePic(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == OPEN_MAPS_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // De lo contrario, recogemos el resultado de la segunda actividad.
            Bundle extras = data.getExtras();
            adressLine = extras.getString("line");
            locality = extras.getString("locality");
            zipcode = extras.getString("zipcode");
            lat = extras.getDouble("lat");
            lng = extras.getDouble("lng");

            //editTextZipcode.setText(adressLine);

        } else if (resultCode == RESULT_OK && requestCode == RELOAD_ACTIVITY) {
            finish();
            startActivity(getIntent());
        }


    }

    protected void updateDrawerImage() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.cont_civ);
        linearLayout.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


        Avatar_fr.bitmap = bitmap;

        tv_name.setText(Avatar_fr.getTVName());


        if (bitmap != null) {
            //Toast.maqeText(HomeActivity.this, "TE BLURREO EL FONDO", Toast.LENGTH_SHORT).show();
            circleImageView.setImageBitmap(bitmap);
            bitmap = new BlurTransformation(this, 25).transform(bitmap);
            iv_blur_profile = (ImageView) ll_blur.getRootView().findViewById(R.id.iv_blur);
            iv_blur_profile.setImageBitmap(bitmap);

        }


    }

    public static int getUserId(Context c) {
        JSONObject json = null;
        int user_id = 0;
        try {
            json = new JSONObject(MainActivity.get_json(c));
            user_id = json.getInt("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user_id;
    }

    protected String getUsername() {
        JSONObject json = null;
        String user_name = "";
        try {
            json = new JSONObject(MainActivity.get_json(getApplicationContext()));
            user_name = json.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user_name;
    }

    protected String getLastname() {
        JSONObject json = null;
        String user_name = "";
        try {
            json = new JSONObject(MainActivity.get_json(getApplicationContext()));
            user_name = json.getString("lastname");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user_name;
    }

    protected String getBirthdate() {
        JSONObject json = null;
        String birthdate = "";
        try {
            json = new JSONObject(MainActivity.get_json(getApplicationContext()));
            birthdate = json.getString("birthdate");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("USERPROFILE", "GET BIRTHDATE: " + birthdate);
        return birthdate;
    }

    //SUBE IMAGEN AL SERVIDOR Y ACTUALIZA LA BBDD
    public void updateProfile() {
        toolbar.setVisibility(View.VISIBLE);
        msvBuscador.setVisibility(View.VISIBLE);


        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Actualizando perfil", "Espere por favor...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        loading.dismiss();
                        Log.d("IMAGE", "UPLOAD SUCCESFULL");
                        Log.d("IMAGE", s);


                        //Toast.makeText(HomeActivity.this, Avatar_fr.getTVName(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        //Toast.makeText(HomeActivity.this, "Problemas de conexión, vuelvalo a intentar", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Getting Image Name
                String image = "";
                if (Avatar_fr.bitmap == null) {
                    Log.d("IMAGE", "NO HAS CAMBIADO LA IMAGEN");
                } else {
                    Log.d("IMAGE", "HAS CAMBIADO EL FONDO");
                    image = getStringImage(bitmap);
                    Avatar_fr.bitmap = null;
                }
                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                JSONObject json = null;
                int id_user = 0;
                try {
                    json = new JSONObject(MainActivity.get_json(getApplicationContext()));
                    id_user = json.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Adding parameters
                params.put("id", String.valueOf(id_user));

                params.put("image", image);
                //parametros edu
                params.put("name", Avatar_fr.getTVName());
                params.put("lastname", Avatar_fr.getTVLastName());
                params.put("birthDate", Avatar_fr.getDPBirthDate());


                return params;
            }
        };

        //Creating a Request Queue
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        url_img = null;
        updateDrawerImage();
        Avatar_fr.bitmap = null;
        bitmap = null;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        System.out.println("WIDT IMG: " + bmp.getWidth());
        System.out.println("HEIGHT IMG: " + bmp.getHeight());


        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.d("IMAGE", encodedImage);
        return encodedImage;
    }

    protected Bitmap resizeImage(Bitmap bmp) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, bmp.getWidth(), bmp.getHeight()), new RectF(0, 0, 250, 250), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
    }

    @Override

    protected void onResume() {
        super.onResume();

    }


}
