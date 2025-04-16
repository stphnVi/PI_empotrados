package com.example.miprimeraplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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




        botoncasamodelo.setOnClickListener(view -> { // mapeo del boton casa modelo
            Intent intent = new Intent(PrincipalActivity.this, CasaModelo.class);
            startActivity(intent);
        });




    }
}
