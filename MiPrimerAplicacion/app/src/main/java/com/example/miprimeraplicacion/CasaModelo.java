package com.example.miprimeraplicacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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
        Button botonPuertaP = findViewById(R.id.PuertaPatio);
        Button botonPuertaC1 = findViewById(R.id.PuertaC1);
        Button botonPuertaC2 = findViewById(R.id.PuertaC2);
        Button botonPuertaCocina = findViewById(R.id.PuertaCocina);
        Button botonPuertaPrin = findViewById(R.id.PuertaPrincipal);

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

        botonPuertaP.setBackgroundColor(ContextCompat.getColor(this, puertaP_abierta ? R.color.encendido : R.color.apagado));
        botonPuertaC1.setBackgroundColor(ContextCompat.getColor(this, puertaC1_abierta ? R.color.encendido : R.color.apagado));
        botonPuertaC2.setBackgroundColor(ContextCompat.getColor(this, puertaC2_abierta ? R.color.encendido : R.color.apagado));
        botonPuertaCocina.setBackgroundColor(ContextCompat.getColor(this, puerta_cocina_abierta ? R.color.encendido : R.color.apagado));
        botonPuertaPrin.setBackgroundColor(ContextCompat.getColor(this, puerta_principal_abierta ? R.color.encendido : R.color.apagado));






        //------------------------------------------------------------------LEDS

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

        botonPuertaP.setOnClickListener(view -> {

            if(!puertaP_abierta) {
                botonPuertaP.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonPuertaP.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagesala = puertaP_abierta ?  "motor_CLOSE" : "motor_OPEN";
            String messageSendsala = "func: Patio" + messagesala;
            Socket.sendMessage(messageSendsala);
            puertaP_abierta = !puertaP_abierta;
            guardarEstado("puertaP_abierta", puertaP_abierta);
        });

        botonPuertaPrin.setOnClickListener(view -> {

            if(!puerta_principal_abierta) {
                botonPuertaPrin.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonPuertaPrin.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagesala = puerta_principal_abierta ?  "motor_CLOSE" : "motor_OPEN";
            String messageSendsala = "func: Principal" + messagesala;
            Socket.sendMessage(messageSendsala);
            puerta_principal_abierta = !puerta_principal_abierta;
            guardarEstado("puerta_principal_abierta", puerta_principal_abierta);
        });

        botonPuertaC1.setOnClickListener(view -> {

            if(!puertaC1_abierta) {
                botonPuertaC1.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonPuertaC1.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagepgarage = puertaC1_abierta ? "motor_CLOSE" : "motor_OPEN";
            String messageSendpgarage = "func: PuertaC1" + messagepgarage;
            Socket.sendMessage(messageSendpgarage);
            puertaC1_abierta = !puertaC1_abierta;
            guardarEstado("puertaC1_abierta", puertaC1_abierta);
        });

        botonPuertaC2.setOnClickListener(view -> {

            if(!puertaC2_abierta) {
                botonPuertaC2.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonPuertaC2.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagepgarage = puertaC2_abierta ? "motor_CLOSE" : "motor_OPEN";
            String messageSendpgarage = "func: PuertaC2" + messagepgarage;
            Socket.sendMessage(messageSendpgarage);
            puertaC2_abierta = !puertaC2_abierta;
            guardarEstado("puertaC2_abierta", puertaC2_abierta);

        });

        botonPuertaCocina.setOnClickListener(view -> {

            if(!puerta_cocina_abierta) {
                botonPuertaCocina.setBackgroundColor(ContextCompat.getColor(this, R.color.encendido));
            }else{
                botonPuertaCocina.setBackgroundColor(ContextCompat.getColor(this, R.color.apagado));

            }

            String messagepgarage = puerta_cocina_abierta ? "motor_CLOSE" : "motor_OPEN";
            String messageSendpgarage = "func: PuertaCocina" + messagepgarage;
            Socket.sendMessage(messageSendpgarage);
            puerta_cocina_abierta = !puerta_cocina_abierta;
            guardarEstado("puerta_cocina_abierta", puerta_cocina_abierta);
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
            String message = Socket.message;
            if ("ledOff".equals(message)) {
                // Acción exitosa, actualizar UI
                //BotonSalafondo.setColor(ContextCompat.getColor(this, R.color.encendido));
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

