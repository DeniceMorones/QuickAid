package com.example.quickaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PreventActivity extends AppCompatActivity {

    private static final String URL_PROMOCION_SALUD = "https://www.paho.org/es/temas/promocion-salud";
    private static final String URL_PREVENCION_ENT = "https://www.youtube.com/watch?v=gWK_l2nIEyQ";
    private static final String URL_CONTROL_INFECCIONES = "https://www.paho.org/es/temas/prevencion-control-infecciones";

    private static final String URL_ALERTAS = "https://medlineplus.gov/spanish/ency/article/001927.htm";
    private static final String URL_PREVENCION_ACCIDENTES = "https://www.youtube.com/watch?v=AyBEtVTstNA";
    private static final String URL_HABITOS_SALUDABLES = "https://www.imss.gob.mx/sites/all/statics/salud/guias_salud/cartera-alimentacion.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_prevent);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout_aprender), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnPromocionSalud = findViewById(R.id.btn_ver_mas_salud);
        Button btnPrevencionEnt = findViewById(R.id.btn_ver_mas_ent);
        Button btnControlInfecciones = findViewById(R.id.btn_ver_mas_infecciones);

        if (btnPromocionSalud != null) {
            btnPromocionSalud.setOnClickListener(v -> openUrl(URL_PROMOCION_SALUD));
        }

        if (btnPrevencionEnt != null) {
            btnPrevencionEnt.setOnClickListener(v -> openUrl(URL_PREVENCION_ENT));
        }

        if (btnControlInfecciones != null) {
            btnControlInfecciones.setOnClickListener(v -> openUrl(URL_CONTROL_INFECCIONES));
        }

        CardView cardAlertas = findViewById(R.id.card_alertas_banner);
        if (cardAlertas != null) {
            cardAlertas.setOnClickListener(v -> openUrl(URL_ALERTAS));
        }

        CardView cardAccidentes = findViewById(R.id.card_prevencion_accidentes);
        if (cardAccidentes != null) {
            cardAccidentes.setOnClickListener(v -> openUrl(URL_PREVENCION_ACCIDENTES));
        }

        CardView cardHabitos = findViewById(R.id.card_habitos_saludables);
        if (cardHabitos != null) {
            cardHabitos.setOnClickListener(v -> openUrl(URL_HABITOS_SALUDABLES));
        }

    }


    private void openUrl(String url) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (Exception e) {
            // Manejar la excepción si no hay un navegador disponible.
            e.printStackTrace();
        }
    }
}