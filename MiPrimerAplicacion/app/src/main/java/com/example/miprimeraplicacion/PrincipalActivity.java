package com.example.miprimeraplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // Esto cierra todas las actividades en la pila
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        ImageButton botoncasamodelo = findViewById(R.id.casamodelo);
        ImageButton botonmonitoreo = findViewById(R.id.monitoreo);

        botoncasamodelo.setOnClickListener(view -> { // mapeo del boton casa modelo
            Intent intent = new Intent(PrincipalActivity.this, CasaModelo.class);
            startActivity(intent);
        });

        botonmonitoreo.setOnClickListener(view -> { // mapeo del boton monitoreo
            // Create the JSON object with the message to send to the server
            JSONObject messageData = new JSONObject();
            try {
                messageData.put("func", "capture_image"); // Add the "Capture image" string
                Socket.sendMessage(messageData.toString()); // Send message to the server
                Toast.makeText(PrincipalActivity.this, "Enviando solicitud de captura...", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Toast.makeText(PrincipalActivity.this, "Error al enviar la solicitud: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            // After sending the message, start the Monitoreo activity
            Intent intent = new Intent(PrincipalActivity.this, Monitoreo.class);
            startActivity(intent);
        });
    }
}
