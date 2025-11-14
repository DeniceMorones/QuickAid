package com.example.quickaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button btnLeerMasPrevenir = findViewById(R.id.btn_leer_mas_prevenir);

        btnLeerMasPrevenir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PreventActivity.class);
                startActivity(intent);
            }
        });


        ConstraintLayout layoutOffline = findViewById(R.id.layout_offline_card);

        layoutOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OfflineActivity.class);
                startActivity(intent);
            }
        });

        Button btnLeerMasProcedimientos = findViewById(R.id.btn_leer_mas_procedimientos);

        btnLeerMasProcedimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProcedimientosActivity.class);
                startActivity(intent);
            }
        });

        ConstraintLayout layoutAlertas = findViewById(R.id.layout_alertas_card);

        layoutAlertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AlertsActivity.class);
                startActivity(intent);
            }
        });
    }
}