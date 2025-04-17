package com.example.miprimeraplicacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.PrintWriter;
import java.util.Scanner;
import android.os.Handler;
import android.os.Looper;

// Recordar que dar los permisos del HW para utilizar los componentes por ejemplo la red
// Esto se hace en el archivo AndroidManifest

/**
 * Creado por Jason Leitón Jiménez para guía del curso de Principio de Modelado
 * Esta clase es la que permite enviar y recibir mensajes desde y hacia el
 * servidor en python
 * Recordar que la gui está en res
 */
public class MainActivity extends AppCompatActivity {

    private EditText editTextMessage;
    //private TextView textViewChat;

    private EditText editTextPassword;
    private ImageButton imageButtonShowHidePassword;
    private boolean isPasswordVisible = false; // Flag para manejar el estado de visibilidad
    private boolean pantallaInicioSesionAbierta;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // Esto cierra todas las actividades en la pila
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMessage = findViewById(R.id.editTextMessage);
        // textViewChat = findViewById(R.id.textViewChat);
        Button buttonSend = findViewById(R.id.buttonSend);
       // Button buttonExit = findViewById(R.id.buttonExit);
        //Button buttonForgetPassword = findViewById(R.id.buttonForgot);

        //Cada vez que se abre la pantalla de inicio de sesion se indica en el boolean
        pantallaInicioSesionAbierta = true;

        // Configuración para olvidar contraseña


        // Inicializar elementos de la contraseña
        editTextPassword = findViewById(R.id.editTextTextPassword);
        imageButtonShowHidePassword = findViewById(R.id.imageButton3);

        imageButtonShowHidePassword.setOnClickListener(view -> {
            if (isPasswordVisible) {
                // Cambiar a "ocultar" contraseña
                editTextPassword.setInputType(
                        android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imageButtonShowHidePassword.setImageResource(R.drawable.ojo);
            } else {

                editTextPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT
                        | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                imageButtonShowHidePassword.setImageResource(R.drawable.botonojo);
            }
            isPasswordVisible = !isPasswordVisible; // Alternar estado
        });

        // Se inicia el socket solamente la primera vez que se abre la aplicacion
        if (com.example.miprimeraplicacion.Socket.sistemaInit == false) {
            com.example.miprimeraplicacion.Socket.sistemaInit = true;
            new Thread(() -> {
                try {
                    com.example.miprimeraplicacion.Socket.iniciarSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        //Se escuchan los mensajes unicamente cuando la pantalla de inicio de sesion esta abierta
        new Thread(() -> {
            while (pantallaInicioSesionAbierta == true) {
                // Escuchar continuamente los mensajes del servidor
                if (com.example.miprimeraplicacion.Socket.message != null) {
                    procesarMensaje();
                    //textViewChat.append("Servidor: " + message + "\n");
                }
            }

        }).start();


        buttonSend.setOnClickListener(view -> {
            String userEmail = ((EditText) findViewById(R.id.editTextMessage)).getText().toString();
            String password = ((EditText) findViewById(R.id.editTextTextPassword)).getText().toString();


            // Validar campos vacíos
            if (userEmail.isEmpty()) {
                marcarCampoTemporalmente(editTextMessage);
            }

            if (password.isEmpty()) {
                marcarCampoTemporalmente(editTextPassword);
            }

            // Continuar con la lógica solo si ambos campos están llenos
            if (!userEmail.isEmpty() && !password.isEmpty()) {
                String messageSend = "func: login, " + "userEmail: " + userEmail + ", password: " + password;
                Socket.sendMessage(messageSend);
            }
        });


    }


    // Método para cambiar el fondo de un campo a rojo temporalmente
    private void marcarCampoTemporalmente(EditText editText) {
        // Cambiar el fondo a rojo
        editText.setBackgroundResource(android.R.color.holo_red_light);

        // Restaurar el fondo blanco después de 3 segundos
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            editText.setBackgroundResource(android.R.color.white); // Reestablecer el color blanco
        }, 2000); // 2000 ms = 2 segundos
    }

    private void procesarMensaje(){

        runOnUiThread(() -> {
            if (com.example.miprimeraplicacion.Socket.message != null) {
                String message = com.example.miprimeraplicacion.Socket.message;
                if ("1".equals(message)) {
                    // Abrir nueva ventana si el mensaje es "1"
                    pantallaInicioSesionAbierta = false;
                    Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    Socket.message = null;
                } else if ("0".equals(message)) {
                    // Mostrar mensaje de credenciales incorrectas
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error de autenticación")
                            .setMessage("Credenciales incorrectas. Por favor, intenta de nuevo.")
                            .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                            .create()
                            .show();
                    Socket.message = null;

                } else {
                    // Manejar otros mensajes si es necesario
                    Socket.message = null;
                }
            }
            else {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (Socket.out != null)
                Socket.out.close();
            if (Socket.in != null)
                Socket.in.close();
            if (Socket.socket != null)
                Socket.socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}