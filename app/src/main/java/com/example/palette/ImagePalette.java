package com.example.palette;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.palette.graphics.Palette;

public class ImagePalette extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_palette);

        // Configurar transiciones de entrada y salida
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new android.transition.Fade());
            getWindow().setExitTransition(new android.transition.Slide());
        }

        // Obtén la imagen seleccionada del Intent
        int selectedImage = getIntent().getIntExtra("image_resource", 0);

        // Configura la imagen en el ImageView
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(selectedImage);

        // Configura el nombre de transición compartida para el ImageView
        String transitionName = getString(R.string.shared_image_transition_name);
        ViewCompat.setTransitionName(imageView, transitionName);

        // Extrae el bitmap de la imagen del ImageView
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        // Crea una instancia de Palette desde el bitmap
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                // Configura el color vibrant sobre la ToolBar
                int colorVibrant = palette.getVibrantColor(getResources().getColor(R.color.colorDefaultVibrant));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(colorVibrant));

                // Configura el color dark vibrant sobre la StatusBar
                int colorDarkVibrant = palette.getDarkVibrantColor(getResources().getColor(R.color.colorDefaultDarkVibrant));
                getWindow().setStatusBarColor(colorDarkVibrant);

                // Configura el resto de colores sobre diferentes TextViews
                TextView lightVibrantTextView = findViewById(R.id.lightVibrantTextView);
                lightVibrantTextView.setBackgroundColor(palette.getLightVibrantColor(getResources().getColor(R.color.colorDefaultLightVibrant)));

                TextView mutedTextView = findViewById(R.id.mutedTextView);
                mutedTextView.setBackgroundColor(palette.getMutedColor(getResources().getColor(R.color.colorDefaultMuted)));

                TextView darkMutedTextView = findViewById(R.id.darkMutedTextView);
                darkMutedTextView.setBackgroundColor(palette.getDarkMutedColor(getResources().getColor(R.color.colorDefaultDarkMuted)));

                // Puedes seguir configurando otros colores según tus necesidades
            }
        });
    }
}
