package com.example.quickaid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class InfografiasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infografias);

        ImageButton btnBack = findViewById(R.id.btn_back_infografias);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        CardView cardRcp = findViewById(R.id.card_infografia_rcp);
        CardView cardQuemaduras = findViewById(R.id.card_infografia_quemaduras);
        CardView cardAtragantamiento = findViewById(R.id.card_infografia_atragantamiento);
        CardView cardPicaduras = findViewById(R.id.card_infografia_picaduras);
        CardView cardHogar = findViewById(R.id.card_infografia_hogar);
        CardView cardVial = findViewById(R.id.card_infografia_vial);




        if (cardRcp != null) {
            cardRcp.setOnClickListener(v -> mostrarInfografia("Reanimación Cardiopulmonar (RCP)", "ic_rcp"));
        }

        if (cardQuemaduras != null) {
            cardQuemaduras.setOnClickListener(v -> mostrarInfografia("Manejo de Quemaduras y Heridas", "ic_quemadura"));
        }

        if (cardAtragantamiento != null) {
            cardAtragantamiento.setOnClickListener(v -> mostrarInfografia("Maniobra de Heimlich", "ic_atragantamiento"));
        }

        if (cardPicaduras != null) {
            cardPicaduras.setOnClickListener(v -> mostrarInfografia("Picaduras e Intoxicaciones", "ic_picadura"));
        }

        if (cardHogar != null) {
            cardHogar.setOnClickListener(v -> mostrarInfografia("Prevención en el Hogar", "ic_hogar"));
        }

        if (cardVial != null) {
            cardVial.setOnClickListener(v -> mostrarInfografia("Seguridad Vial", "ic_trafico"));
        }
    }


    private void mostrarInfografia(String tema, String infografiaId) {
        Intent intent = new Intent(InfografiasActivity.this, ImageDetailActivity.class);

        intent.putExtra(ImageDetailActivity.EXTRA_TEMA, tema);
        intent.putExtra(ImageDetailActivity.EXTRA_INFOGRAFIA_ID, infografiaId);

        startActivity(intent);
    }
}