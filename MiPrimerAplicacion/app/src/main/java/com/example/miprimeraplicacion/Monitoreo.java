package com.example.miprimeraplicacion;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class Monitoreo extends AppCompatActivity {

    private LinearLayout layoutPrincipal;
    // Lista interna de URLs de imágenes
    private boolean pantallaCasaModeloAbierta;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // Esto cierra todas las actividades en la pila
    }
    private final List<String> listaUrls = new ArrayList<String>() {{
        add("http://192.168.18.111:8080/image.jpg");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoreo);
        pantallaCasaModeloAbierta = true;

        Button botonregresarprincipal = findViewById(R.id.regresarprincipal);
        layoutPrincipal = findViewById(R.id.layoutPrincipal);

        // Initial image load
        loadImages();

        // Start periodic refresh
        startImageRefresh();

        botonregresarprincipal.setOnClickListener(view -> {
            pantallaCasaModeloAbierta = false;
            finish(); // Just finish current activity instead of starting new one
        });

        new Thread(() -> {
            while (pantallaCasaModeloAbierta) {
                if (Socket.message != null) {
                    procesarMensaje();
                }
            }
        }).start();
    }


    /**
     * Se procesan los mensajes del servidor
     * SIEMPRE al final de cada if poner "Socket.message=null"
     */
    private void procesarMensaje(){
        runOnUiThread(() -> {
            String message = com.example.miprimeraplicacion.Socket.message;

            if ("Inclinación detectada (HIGH)".equals(message)) {
                // Mostrar error
                Toast.makeText(this, "Hay un sismo", Toast.LENGTH_SHORT).show();
                Socket.message = null;
            }
            // Remove the automatic navigation for "1" response
            else {
                // Just clear the message for other cases
                Socket.message = null;
            }
        });
    }

    private void loadImages() {
        for (String urlImagen : listaUrls) {
            // Add timestamp to URL to prevent caching
            String timestampedUrl = urlImagen + "?t=" + System.currentTimeMillis();

            ImageView imagenCasa = new ImageView(this);
            imagenCasa.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 500
            ));

            Picasso.get()
                    .load(timestampedUrl)
                    .into(imagenCasa);

            // Clear existing views and add new ones
            layoutPrincipal.removeAllViews();
            layoutPrincipal.addView(imagenCasa);
        }
    }

    // Call this periodically to refresh the image
    private void startImageRefresh() {
        final Handler handler = new Handler();
        final int delay = 2000; // Refresh every 2 seconds

        handler.postDelayed(new Runnable() {
            public void run() {
                if (pantallaCasaModeloAbierta) {
                    loadImages();
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);
    }
}
