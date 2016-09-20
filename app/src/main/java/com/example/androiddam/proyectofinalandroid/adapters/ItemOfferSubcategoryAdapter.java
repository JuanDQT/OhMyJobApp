package com.example.androiddam.proyectofinalandroid.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.AddOfferActivity;
import com.example.androiddam.proyectofinalandroid.model.ItemOfferSubcategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;

/**
 * Created by Juan on 04/09/2016.
 */
public class ItemOfferSubcategoryAdapter extends RecyclerView.Adapter<ItemOfferSubcategoryAdapter.ViewHolder>{
    private ArrayList<ItemOfferSubcategoryModel> itemOfferSubcategories;
    private Activity activity;
    private AlertDialog alertDialog;

    public ItemOfferSubcategoryAdapter(ArrayList<ItemOfferSubcategoryModel> itemOfferSubcategories, Activity activity) {
        this.itemOfferSubcategories = itemOfferSubcategories;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.label_item_subcategory, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_name.setText(itemOfferSubcategories.get(position).getName());
        holder.tv_id.setText(String.valueOf(itemOfferSubcategories.get(position).getId()));
        //

        Picasso.with(activity).load(itemOfferSubcategories.get(position).getUrl()).transform(new VignetteFilterTransformation(activity)).into(holder.iv_url);
        //
        holder.cv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopUp(Integer.parseInt(holder.tv_id.getText().toString()));
            }
        });

    }



    @Override
    public int getItemCount() {
        return itemOfferSubcategories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv_item;
        TextView tv_id;
        TextView tv_name;
        ImageView iv_url;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_item = (CardView) itemView.findViewById(R.id.cv_item);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_url = (ImageView) itemView.findViewById(R.id.iv_url);
        }
    }

    public void mostrarPopUp(final int idCategory) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Nombre para la oferta");
        alertDialogBuilder.setMessage("Introduce un titulo breve y corto");

        final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_add_offer, null);
        final EditText etNameOffer = (EditText) view.findViewById(R.id.et_name_offer);
        etNameOffer.setHint("Ej: Canguro a domicilio");
        //alertDialog.setView(etNameOffer);
        alertDialogBuilder.setView(view);
        //alertDialog.setIcon(R.drawable.key);

        alertDialogBuilder.setPositiveButton("CONTINUAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name_offer = etNameOffer.getText().toString();
                        Toast.makeText(activity, "NAME OFFER: "+ name_offer, Toast.LENGTH_SHORT).show();
                        ((AddOfferActivity) view.getContext()).goSelectTypeJob(etNameOffer.getText().toString(), idCategory);

                    }
                });

        alertDialogBuilder.setNegativeButton("CANCELAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        etNameOffer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if ( !charSequence.toString().isEmpty() &&charSequence.toString().length()>5)
                    alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                else
                    alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);


                Log.d("NORMAL", charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        openKeyboard(alertDialog);
    }

    private void openKeyboard(AlertDialog alertDialog){
        alertDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
