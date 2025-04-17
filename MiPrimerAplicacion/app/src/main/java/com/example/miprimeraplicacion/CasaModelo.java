package com.example.miprimeraplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import android.graphics.drawable.GradientDrawable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class CasaModelo extends AppCompatActivity {

    private boolean pantallaCasaModeloAbierta;

    private boolean cuarto_encendido = false;
    private boolean baño_encendido = false;
    private boolean sala_encendido = false;

    private boolean pgarage_abierta = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pantallaCasaModeloAbierta = false;
        Intent intent = new Intent(CasaModelo.this, PrincipalActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.casa_modelo);


        //Cada vez que se abre la pantalla de CasaModelo se indica en el boolean como true
        pantallaCasaModeloAbierta = true;



        Button botonluzcuarto = findViewById(R.id.cuarto);
        Button botonluzbaño = findViewById(R.id.baño);
        Button botonluzsala = findViewById(R.id.sala);
        Button botonpgarage = findViewById(R.id.pgarage);
        Button botonregresarprincipal = findViewById(R.id.regresarprincipal);

        botonluzcuarto.setOnClickListener(view -> {

            if(!cuarto_encendido) {
                botonluzcuarto.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonluzcuarto.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }
            String messagecuarto = cuarto_encendido ? "LED2_OFF" : "LED2_ON";
            String messageSendcuarto = "func: luzcuarto" + messagecuarto;
            Socket.sendMessage(messageSendcuarto);
            cuarto_encendido = !cuarto_encendido;
        });

        botonluzbaño.setOnClickListener(view -> {

            if(!baño_encendido) {
                botonluzbaño.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonluzbaño.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagebaño = baño_encendido ? "LED3_OFF" : "LED3_ON";
            String messageSendbaño = "func: luzbaño" + messagebaño;
            Socket.sendMessage(messageSendbaño);
            baño_encendido = !baño_encendido;
        });

        botonluzsala.setOnClickListener(view -> {

            if(!sala_encendido) {
                botonluzsala.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonluzsala.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagesala = sala_encendido ? "LED1_OFF" : "LED1_ON";
            String messageSendsala = "func: luzsala" + messagesala;
            Socket.sendMessage(messageSendsala);
            sala_encendido = !sala_encendido;
        });

        botonpgarage.setOnClickListener(view -> {

            if(!pgarage_abierta) {
                botonpgarage.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonpgarage.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagepgarage = pgarage_abierta ? "motor_CLOSE" : "motor_OPEN";
            String messageSendpgarage = "func: " + messagepgarage;
            Socket.sendMessage(messageSendpgarage);
            pgarage_abierta = !pgarage_abierta;
        });


        botonregresarprincipal.setOnClickListener(view -> { // mapeo del boton exit
            pantallaCasaModeloAbierta = false;
            Intent intent = new Intent(CasaModelo.this, PrincipalActivity.class);
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

