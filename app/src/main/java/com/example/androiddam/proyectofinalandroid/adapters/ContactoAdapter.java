package com.example.androiddam.proyectofinalandroid.adapters;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.UserProfile;
import com.example.androiddam.proyectofinalandroid.fragments.Contact_fr;
import com.example.androiddam.proyectofinalandroid.model.Contacto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Juan on 28/05/2016.
 */
public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactViewHolder> {

    private final List<Contacto> contactoList;
    private int label;
    private static int position;

    public ContactoAdapter(ArrayList<Contacto> contactoList) {
        this.contactoList = contactoList;
        this.label = R.layout.label_contacto;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_contacto, parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contacto contacto = contactoList.get(position);
        holder.bindContact(contacto);
        this.position = position;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_id;
        CircleImageView civ_user;

        public ContactViewHolder(final View itemView) {
            super(itemView);



            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            civ_user = (CircleImageView) itemView.findViewById(R.id.url_user);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("***IDDDDDDDDDDD: "+ tv_id.getText().toString());
                    Intent intent = new Intent(itemView.getContext(), UserProfile.class);
                    Bundle bundle = new Bundle();
                    System.out.println("TE PASO ID: "+ tv_id.getText().toString());
                    bundle.putInt("id", Integer.valueOf(tv_id.getText().toString()));
                    intent.putExtras(bundle);
                    //startActivity(intent);
                    itemView.getContext().startActivity(intent);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    final TextView temp_id = (TextView) v.findViewById(R.id.tv_id);
                    TextView temp_name = (TextView) v.findViewById(R.id.tv_name);

                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Eliminar contacto")
                            .setMessage("¿Borrar a " + temp_name.getText().toString())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(v.getContext(), "SAPE " + temp_id.getText().toString(), Toast.LENGTH_SHORT).show();

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Contact_fr.DELETE_CONTACT_URL, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //Contact_fr.removeItem();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("id", String.valueOf("1"));
                                            params.put("id_contact", String.valueOf("26"));

                                            return params;
                                        }
                                    };
                                    //MySingleton.getInstance(v.getContext()).addToRequestQueue(stringRequest);
                                    Contact_fr.removeItem(position);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    return false;
                }
            });

/*            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView temp_id = (TextView) v.findViewById(R.id.tv_id);
                    System.out.println("***id*** "+ temp_id.getText().toString().toString());
                }
            });*/

        }

        public void bindContact(Contacto contacto) {
            tv_name.setText(contacto.getName());
            Picasso.with(itemView.getContext()).load(contacto.getImg_path()).error(R.drawable.new_user).into(civ_user);
            tv_id.setText(String.valueOf(contacto.getId_contact()));

        }
    }


    @Override
    public int getItemCount() {
        return contactoList.size();
    }


}
