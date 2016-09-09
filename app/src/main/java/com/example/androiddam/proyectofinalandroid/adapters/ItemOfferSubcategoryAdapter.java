package com.example.androiddam.proyectofinalandroid.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(itemOfferSubcategories.get(position).getName());
        holder.tv_id.setText(String.valueOf(itemOfferSubcategories.get(position).getId()));
        //

        Picasso.with(activity).load(itemOfferSubcategories.get(position).getUrl()).transform(new VignetteFilterTransformation(activity)).into(holder.iv_url);
        //
        holder.cv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarPopUp();
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

    public void mostrarPopUp() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Nombre para la oferta");
        alertDialog.setMessage("Introduce un titulo breve y corto");

        final View view = LayoutInflater.from(activity).inflate(R.layout.dialog_add_offer, null);
        final EditText etNameOffer = (EditText) view.findViewById(R.id.et_name_offer);
        //alertDialog.setView(etNameOffer);
        alertDialog.setView(view);
        //alertDialog.setIcon(R.drawable.key);

        alertDialog.setPositiveButton("CONTINUAR",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name_offer = etNameOffer.getText().toString();
                        Toast.makeText(activity, "NAME OFFER: "+ name_offer, Toast.LENGTH_SHORT).show();
                        ((AddOfferActivity) view.getContext()).goSelectTypeJob();

                    }
                });

        alertDialog.setNegativeButton("CANDEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}
