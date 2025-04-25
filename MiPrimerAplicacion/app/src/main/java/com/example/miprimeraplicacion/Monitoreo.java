package com.example.miprimeraplicacion;

import android.os.Bundle;
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
        add("http://192.168.18.42:8080/image.png");
        add("http://192.168.18.42:8080/image1.png");
        add("http://192.168.18.42:8080/image2.png");
        add("http://192.168.18.42:8080/image2.png");
        add("http://192.168.18.42:8080/image2.png");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoreo);
        pantallaCasaModeloAbierta = true;

        Button botonregresarprincipal = findViewById(R.id.regresarprincipal);
        layoutPrincipal = findViewById(R.id.layoutPrincipal);

        for (String urlImagen : listaUrls) {
            LinearLayout fotoLayout = new LinearLayout(this);
            fotoLayout.setOrientation(LinearLayout.VERTICAL);
            fotoLayout.setPadding(16, 16, 16, 16);
            fotoLayout.setGravity(Gravity.CENTER_VERTICAL);

            ImageView imagenCasa = new ImageView(this);
            imagenCasa.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 500
            ));

            Picasso.get()
                    .load(urlImagen)
                    .into(imagenCasa);

            fotoLayout.addView(imagenCasa);
            layoutPrincipal.addView(fotoLayout);

            View separator = new View(this);
            separator.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 20
            ));
            layoutPrincipal.addView(separator);
        }

        botonregresarprincipal.setOnClickListener(view -> { // mapeo del boton exit
            pantallaCasaModeloAbierta = false;
            Intent intent = new Intent(Monitoreo.this, PrincipalActivity.class);
            startActivity(intent);

        });


        new Thread(() -> {
            while (pantallaCasaModeloAbierta) {
                // Escuchar continuamente los mensajes del servidor
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
            String message = Socket.message;
            if ("Llama detectada!".equals(message)) {
                // Acción exitosa, actualizar UI
                Toast.makeText(this, "Hay un incendio", Toast.LENGTH_SHORT).show();
                Socket.message = null;
            } else if ("Inclinación detectada (HIGH)".equals(message)) {
                // Mostrar error
                Toast.makeText(this, "Hay un sismo", Toast.LENGTH_SHORT).show();
                Socket.message = null;
            } else {
                // Manejar otros casos
                Socket.message = null;
            }


        });
    }
}
