package com.example.androiddam.proyectofinalandroid.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androiddam.proyectofinalandroid.R;
import com.example.androiddam.proyectofinalandroid.activities.OfferShowActivity;
import com.example.androiddam.proyectofinalandroid.model.Oferta;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Juan on 22/03/2016.
 */

public class OfertaAdapter extends RecyclerView.Adapter<OfertaAdapter.ViewHolder> {

    public List<Oferta> ofertaList;
    private final Activity context;


    public OfertaAdapter(ArrayList<Oferta> ofertaList, Activity context) {
        this.ofertaList = ofertaList;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_oferta, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_name.setText(ofertaList.get(position).getName());
        if (ofertaList.get(position).getPrice() == 0) {
            holder.tv_price.setText("A convenir");
            holder.tv_price.setTextSize(20);
        } else {
            holder.tv_price.setText(String.valueOf(ofertaList.get(position).getPrice()) + "€");
            holder.tv_price.setTextSize(30);
        }

        holder.tv_id.setText(String.valueOf(ofertaList.get(position).getId()));


        if (ofertaList.get(position).getPic_profile().length() > 0) {
            Picasso.with(context).load(ofertaList.get(position).getPic_profile()).memoryPolicy(MemoryPolicy.NO_STORE).transform(new CircleTransform()).into(holder.civ_profile);
        } else
            holder.civ_profile.setImageResource(R.drawable.new_user);

        if (ofertaList.get(position).getRating() != null || ofertaList.get(position).getRating() > 0) {

            for (int i = 0; i < ofertaList.get(position).getRating(); i++) {
                holder.stars[i].setVisibility(View.VISIBLE);
            }

            if (ofertaList.get(position).getRating() == 0) {
                holder.stars[0].setVisibility(View.VISIBLE);
            }
        }

        Picasso.with(context).load(ofertaList.get(position).getUrl_category()).into(holder.iv_background);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LE PASAMOS EL NOMBRE DE LA CATEGORIA, NUM ESTRELLAS, PRECIO, FOTO PERFIL
                Intent i = new Intent(v.getContext(), OfferShowActivity.class);
                Bundle b = new Bundle();
                b.putString("id_offer", holder.tv_id.getText().toString());


/*                    Transition fade = new Fade();
                    fade.excludeTarget(android.R.id.statusBarBackground, true);
                    fade.excludeTarget(android.R.id.navigationBarBackground, true);
                    context.getWindow().setExitTransition(fade);
                    context.getWindow().setEnterTransition(fade);*/


                Bitmap mbitmap_header = ((BitmapDrawable) holder.iv_background.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mbitmap_header.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();


                ///i.putExtra("img_header", byteArray);

                i.putExtra("img_header", byteArray);
                i.putExtras(b);
                    /*
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                            context, new Pair(tv_name, "tn_name_offer"),
                            new Pair(civ_profile, "tn_image_profile"));
                    */


                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(
                        context, new Pair(holder.iv_background, "tn_header"));
                //

                context.startActivity(i, activityOptions.toBundle());
            }
        });
    }


    @Override
    public int getItemCount() {
        return ofertaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_price;
        TextView tv_id;
        ImageView iv_background;
        CircleImageView civ_profile;
        ImageView[] stars;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            iv_background = (ImageView) itemView.findViewById(R.id.iv_background);
            civ_profile = (CircleImageView) itemView.findViewById(R.id.civ_profile);

            stars = new ImageView[5];
            stars[0] = (ImageView) itemView.findViewById(R.id.iv_1);
            stars[1] = (ImageView) itemView.findViewById(R.id.iv_2);
            stars[2] = (ImageView) itemView.findViewById(R.id.iv_3);
            stars[3] = (ImageView) itemView.findViewById(R.id.iv_4);
            stars[4] = (ImageView) itemView.findViewById(R.id.iv_5);

        }
    }

}
