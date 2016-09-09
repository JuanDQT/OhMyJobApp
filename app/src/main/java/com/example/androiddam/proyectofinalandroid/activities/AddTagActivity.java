package com.example.androiddam.proyectofinalandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.adapters.TagAdapter;
import com.example.androiddam.proyectofinalandroid.model.Tag;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddTagActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAGS_URL = "http://virtual309.ies-sabadell.cat/virtual309/proyectofinal/getTags.php";
    public static final String UPLOAD_TAG_URL = "http://virtual309.ies-sabadell.cat/virtual309/proyectofinal/uploadTags.php";

    private int id_offer;
    private int id_category;
    private String name_category;

    private Button bntAddTag;

    private GridView checkListView;

    private JSONArray tags;

    private TagAdapter adapter;

    private ArrayList<Tag> arrayTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        Bundle extras = getIntent().getExtras();
        id_offer = extras.getInt("id_offer");
        id_category = extras.getInt("id_category");
        name_category = extras.getString("name_category");

        checkListView = (GridView) findViewById(R.id.listTag);

        bntAddTag = (Button) findViewById(R.id.btn_add_tag);

        bntAddTag.setOnClickListener(this);

        StringRequest TagsRequest = new StringRequest(Request.Method.POST, TAGS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Do it with this it will work
                            tags = new JSONArray(response);
                            arrayTags = new ArrayList<>();

                            for (int i = 0; i < tags.length(); i++)
                            {

                                String tagName = tags.getJSONObject(i).getString("tag_name");
                                Integer tagId = tags.getJSONObject(i).getInt("tag_id");

                                Tag tag = new Tag(tagId,tagName);

                                arrayTags.add(tag);

                            }

                            adapter = new TagAdapter(getApplicationContext(), arrayTags);
                            checkListView.setAdapter(adapter);


                        } catch (JSONException e) {
                            //EN CASO DE NO RECIBIR JSON CON ESE USER EXISTENTE-->

                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(AddTagActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("id_category",Integer.toString(id_category));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(TagsRequest);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_tag:
                addTag();
                break;
        }
    }



    public void addTag() {

        ArrayList<Integer> arrayIdTag = new ArrayList<>();
        for (int i=0; i <arrayTags.size(); i++) {
            if(checkListView.getChildAt(i).findViewById(R.id.checkBoxTag) != null){

                CheckBox cBox=(CheckBox)checkListView.getChildAt(i).findViewById(R.id.checkBoxTag);

                if(cBox.isChecked()){
                    arrayIdTag.add(adapter.getTagId(i));
                }
            }
        }
        if(arrayIdTag.size()<1) {
            bntAddTag.setError("No has añadido tags");
        }else{
            bntAddTag.setError("");
            requestAddTag(arrayIdTag);
        }


    }

    public void requestAddTag(ArrayList<Integer> arrayIdTag) {
        final JSONArray mJSONArray = new JSONArray(Arrays.asList(arrayIdTag));


        StringRequest UploadTagsRequest = new StringRequest(Request.Method.POST, UPLOAD_TAG_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(AddTagActivity.this, response, Toast.LENGTH_LONG).show();
                    }




                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(AddTagActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("jsonTags",mJSONArray.toString());
                map.put("id_offer",String.valueOf(id_offer));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(UploadTagsRequest);

    }
}
