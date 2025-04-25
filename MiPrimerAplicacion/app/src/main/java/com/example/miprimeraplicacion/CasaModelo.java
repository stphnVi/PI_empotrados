package com.example.miprimeraplicacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import androidx.appcompat.app.AppCompatActivity;


public class CasaModelo extends AppCompatActivity {

    private boolean pantallaCasaModeloAbierta;
    private boolean cuartoC1_encendido = false;
    private boolean cuartoC2_encendido = false;
    private boolean comedor_encendido = false;
    private boolean sala_encendido = false;
    private boolean cocina_encendido = false;
    private boolean puertaP_abierta = false;
    private boolean puertaC1_abierta = false;
    private boolean puertaC2_abierta = false;
    private boolean puerta_cocina_abierta = false;
    private boolean puerta_principal_abierta = false;

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

        //----------------------------MANTENES ESTADO DE LOS BOTONES

        //LEDS
        SharedPreferences prefs = getSharedPreferences("estado_botones", MODE_PRIVATE);
        sala_encendido = prefs.getBoolean("sala_encendido", false);
        cocina_encendido = prefs.getBoolean("cocina_encendido", false);
        cuartoC1_encendido = prefs.getBoolean("cuartoC1_encendido", false);
        cuartoC2_encendido = prefs.getBoolean("cuartoC2_encendido", false);
        comedor_encendido = prefs.getBoolean("comedor_encendido", false);


        //PUERTAS
        puertaP_abierta = prefs.getBoolean("puertaP_abierta", false);
        puertaC1_abierta = prefs.getBoolean("puertaC1_abierta", false);
        puertaC2_abierta = prefs.getBoolean("puertaC2_abierta", false);
        puerta_cocina_abierta = prefs.getBoolean("puerta_cocina_abierta", false);
        puerta_principal_abierta = prefs.getBoolean("puerta_principal_abierta", false);

        //----------------------------LOS BOTONES

        //BOTONES DE LUCES
        Button botonluzC1 = findViewById(R.id.LuzC1);
        Button botonluzC2 = findViewById(R.id.LuzC2);
        Button botonluzCocina = findViewById(R.id.LuzCocina);
        Button botonluzSala = findViewById(R.id.LuzSala);
        Button botonluzCome = findViewById(R.id.LuzCome);

        //BOTONES DE PUERTAS
        View PuertaP = findViewById(R.id.PuertaPatio);
        View PuertaC1 = findViewById(R.id.PuertaC1);
        View PuertaC2 = findViewById(R.id.PuertaC2);
        View PuertaPrin = findViewById(R.id.PuertaPrincipal);

        //BOTONES PARA ACTUALIZAR VERSION PUERTAS Y
        //BOTON PARA ENCENDER TODAS LAS LUCES

        Button TodasLucesOn = findViewById(R.id.Encender);
        Button TodasLucesOff = findViewById(R.id.Apagar);
        Button CheckPuertas = findViewById(R.id.Puertas);

        Button botonregresarprincipal = findViewById(R.id.regresarprincipal);

        //Para los botones de circulo
        GradientDrawable BotonSalafondo = (GradientDrawable) botonluzSala.getBackground();
        GradientDrawable BotonCocinafondo = (GradientDrawable) botonluzCocina.getBackground();
        GradientDrawable BotonC1fondo = (GradientDrawable) botonluzC1.getBackground();
        GradientDrawable BotonC2fondo = (GradientDrawable) botonluzC2.getBackground();
        GradientDrawable BotonComefondo = (GradientDrawable) botonluzCome.getBackground();

        // Aplicar color de fondo según el estado

        //LEDS
        BotonSalafondo.setColor(ContextCompat.getColor(this, sala_encendido ? R.color.encendido : R.color.apagado));
        BotonCocinafondo.setColor(ContextCompat.getColor(this, cocina_encendido ? R.color.encendido : R.color.apagado));
        BotonC1fondo.setColor(ContextCompat.getColor(this, cuartoC1_encendido ? R.color.encendido : R.color.apagado));
        BotonC2fondo.setColor(ContextCompat.getColor(this, cuartoC2_encendido ? R.color.encendido : R.color.apagado));
        BotonComefondo.setColor(ContextCompat.getColor(this, comedor_encendido ? R.color.encendido : R.color.apagado));

        //PUERTAS

        PuertaP.setBackgroundColor(ContextCompat.getColor(this, puertaP_abierta ? R.color.encendido : R.color.apagado));
        PuertaC1.setBackgroundColor(ContextCompat.getColor(this, puertaC1_abierta ? R.color.encendido : R.color.apagado));
        PuertaC2.setBackgroundColor(ContextCompat.getColor(this, puertaC2_abierta ? R.color.encendido : R.color.apagado));
        PuertaPrin.setBackgroundColor(ContextCompat.getColor(this, puerta_principal_abierta ? R.color.encendido : R.color.apagado));






        //------------------------------------------------------------------LEDS

        TodasLucesOn.setOnClickListener(view -> {
            BotonComefondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            BotonSalafondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            BotonCocinafondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            BotonC1fondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            BotonC2fondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            String messageSendcuarto = "TodasON";
            Socket.sendMessage(messageSendcuarto);
            guardarEstado("comedor_encendido", true);
            guardarEstado("sala_encendido", true);
            guardarEstado("cocina_encendido", true);
            guardarEstado("cuartoC1_encendido", true);
            guardarEstado("cuartoC2_encendido", true);
            comedor_encendido = true;
            sala_encendido = true;
            cocina_encendido = true;
            cuartoC1_encendido = true;
            cuartoC2_encendido = true;

        });


        TodasLucesOff.setOnClickListener(view -> {
            BotonComefondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            BotonSalafondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            BotonCocinafondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            BotonC1fondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            BotonC2fondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            String messageSendcuarto = "TodasOFF";
            Socket.sendMessage(messageSendcuarto);
            guardarEstado("comedor_encendido", false);
            guardarEstado("sala_encendido", false);
            guardarEstado("cocina_encendido", false);
            guardarEstado("cuartoC1_encendido", false);
            guardarEstado("cuartoC2_encendido", false);
            comedor_encendido = false;
            sala_encendido = false;
            cocina_encendido = false;
            cuartoC1_encendido = false;
            cuartoC2_encendido = false;

        });

        botonluzCome.setOnClickListener(view -> {

            if (!comedor_encendido) {
                BotonComefondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            } else {
                BotonComefondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            }

            String messagecuarto = comedor_encendido ? "LED2_OFF" : "LED2_ON";
            String messageSendcuarto = "func: LuzCome" + messagecuarto;
            Socket.sendMessage(messageSendcuarto);

            // Cambiar estado y guardar
            comedor_encendido = !comedor_encendido;
            guardarEstado("comedor_encendido", comedor_encendido);
        });

        botonluzSala.setOnClickListener(view -> {

            if (!sala_encendido) {
                BotonSalafondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            } else {
                BotonSalafondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            }

            String messagecuarto = sala_encendido ? "LED2_OFF" : "LED2_ON";
            String messageSendcuarto = "func: LuzSala" + messagecuarto;
            Socket.sendMessage(messageSendcuarto);

            // Cambiar estado y guardar
            sala_encendido = !sala_encendido;
            guardarEstado("sala_encendido", sala_encendido);
        });


        botonluzCocina.setOnClickListener(view -> {

            if(!cocina_encendido) {
                BotonCocinafondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            } else {
                BotonCocinafondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            }
            String messagecuarto = cocina_encendido ? "LED2_OFF" : "LED2_ON";
            String messageSendcuarto = "func: luzCocina" + messagecuarto;
            Socket.sendMessage(messageSendcuarto);
            cocina_encendido = !cocina_encendido;

            guardarEstado("cocina_encendido", cocina_encendido);
        });


        botonluzC1.setOnClickListener(view -> {

            if(!cuartoC1_encendido) {
                BotonC1fondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            } else {
                BotonC1fondo.setColor(ContextCompat.getColor(this, R.color.apagado));
            }
            String messagecuarto = cuartoC1_encendido ? "LED2_OFF" : "LED2_ON";
            String messageSendcuarto = "func: LuzC1" + messagecuarto;
            Socket.sendMessage(messageSendcuarto);
            cuartoC1_encendido = !cuartoC1_encendido;
            guardarEstado("cuartoC1_encendido", cuartoC1_encendido);
        });

        botonluzC2.setOnClickListener(view -> {

            if(!cuartoC2_encendido) {
                BotonC2fondo.setColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                BotonC2fondo.setColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagebaño = cuartoC2_encendido ? "LED3_OFF" : "LED3_ON";
            String messageSendbaño = "func: LuzC2" + messagebaño;
            Socket.sendMessage(messageSendbaño);
            cuartoC2_encendido = !cuartoC2_encendido;
            guardarEstado("cuartoC2_encendido", cuartoC2_encendido);
        });



        //------------------------------------------------------------------PUERTAS



        CheckPuertas.setOnClickListener(view -> {
            String messageSendsala = "CheckPuerta";
            Socket.sendMessage(messageSendsala);
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


    //mantener el estado de la página

    private void guardarEstado(String clave, boolean valor) {
        getSharedPreferences("estado_botones", MODE_PRIVATE)
                .edit()
                .putBoolean(clave, valor)
                .apply();
    }


    /**
     * Se procesan los mensajes del servidor
     * SIEMPRE al final de cada if poner "Socket.message=null"
     */
    private void procesarMensaje(){

        runOnUiThread(() -> {
            //String message = Socket.message;
            String message = com.example.miprimeraplicacion.Socket.message;
            if ("-1".equals(message)) {
                //PuertaP.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
                AlertDialog.Builder builder = new AlertDialog.Builder(CasaModelo.this);
                builder.setTitle("error")
                        .setMessage("error en el servidor")
                        .create()
                        .show();
                Socket.message = null;
            } else {
                // Manejar la lista para actualizar
                Socket.message = null;
            }


        });
    }
}

