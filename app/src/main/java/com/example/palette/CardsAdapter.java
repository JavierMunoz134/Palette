package com.example.palette;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.TarjViewHolder> {

    private ArrayList<Tarjeta> items;
    private View.OnClickListener onClickListener;

    public CardsAdapter(ArrayList<Tarjeta> items) {
        this.items = items;
    }

    public void setOnClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static class TarjViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;

        public TarjViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.image1);
        }
    }

    @Override
    public TarjViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cards, viewGroup, false);
        return new TarjViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TarjViewHolder viewHolder, int pos) {
        Tarjeta item = items.get(pos);
        Context context = viewHolder.itemView.getContext();

        viewHolder.imagen.setImageResource(item.getImagen());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Configurar la transición compartida
                String transitionName = context.getString(R.string.shared_image_transition_name);

                // Configurar la transición compartida solo si la versión de Android es Lollipop (5.0) o superior
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) context,
                            new Pair<>(viewHolder.imagen, transitionName)
                    );

                    // Iniciar la actividad ImagePalette con la información necesaria y las opciones de transición
                    Intent intent = new Intent(context, ImagePalette.class);
                    intent.putExtra("image_resource", item.getImagen());
                    context.startActivity(intent, options.toBundle());
                } else {
                    // Si la versión de Android es inferior a Lollipop, simplemente iniciar la actividad sin transición
                    Intent intent = new Intent(context, ImagePalette.class);
                    intent.putExtra("image_resource", item.getImagen());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
