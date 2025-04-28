package com.example.miprimeraplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity {

    private EditText nicknameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;
    private Button cancelButton;
    private boolean registrationInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        // Initialize views
        initViews();

        // Set up button click listeners
        setupClickListeners();

        // Listen for server responses when registration is in progress
        setupServerListener();
    }

    private void initViews() {
        nicknameEditText = findViewById(R.id.nicknameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.cancelButton);
    }

    private void setupClickListeners() {
        // Register button click handler
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    sendRegistrationToServer();
                }
            }
        });

        // Cancel button click handler
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupServerListener() {
        registrationInProgress = true; // Set this to true BEFORE starting the thread

        new Thread(() -> {
            while (registrationInProgress) {
                // Escuchar continuamente los mensajes del servidor
                if (Socket.message != null) {
                    processServerResponse();
                    try {
                        Thread.sleep(100); // Give time for UI updates
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void processServerResponse() {
        runOnUiThread(() -> {
            if (Socket.message != null) {
                String message = Socket.message;
                System.out.println("Response received: " + message); // Debug log

                // The server sends just "True" not "True..."
                if (message.trim().equals("True")) {
                    // Registration successful
                    Toast.makeText(RegistroActivity.this,
                            "Usuario registrado exitosamente",
                            Toast.LENGTH_LONG).show();

                    // Clear message and redirect to login
                    Socket.message = null;
                    registrationInProgress = false;

                    Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Registration failed or other message
                    Toast.makeText(RegistroActivity.this,
                            "Respuesta del servidor: " + message,
                            Toast.LENGTH_LONG).show();
                    Socket.message = null;
                    registrationInProgress = false;
                }
            }
        });
    }

    private void sendRegistrationToServer() {
        String username = nicknameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        try {
            // Create JSON object with user data
            JSONObject userData = new JSONObject();
            userData.put("func", "register");
            userData.put("username", username);
            userData.put("password", password);



            // Send JSON string to server
            Socket.sendMessage(userData.toString());

            Toast.makeText(RegistroActivity.this,
                    "Enviando solicitud de registro...",
                    Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            Toast.makeText(RegistroActivity.this,
                    "Error al crear el mensaje de registro: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        // Validate username
        String username = nicknameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            nicknameEditText.setError("El nombre de usuario es obligatorio");
            valid = false;
        } else {
            nicknameEditText.setError(null);
        }

        // Validate password
        String password = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("La contraseña es obligatoria");
            valid = false;
        } else if (password.length() < 6) {
            passwordEditText.setError("La contraseña debe tener al menos 6 caracteres");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        // Validate confirm password
        String confirmPassword = confirmPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setError("Debe confirmar la contraseña");
            valid = false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Las contraseñas no coinciden");
            valid = false;
        } else {
            confirmPasswordEditText.setError(null);
        }

        return valid;
    }

}