package com.example.quickaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProcedimientosActivity extends AppCompatActivity {

    // --- Constantes de URL para los procedimientos de primeros auxilios ---
    private static final String URL_RCP = "https://www.youtube.com/watch?v=wvKxIlDsLW4";
    private static final String URL_HEMORRAGIAS = "https://www.youtube.com/watch?v=Bno4pGX4FcE";
    private static final String URL_HEIMLICH = "https://www.youtube.com/watch?v=lsrrkUnf_JM";
    private static final String URL_FRACTURAS = "https://www.youtube.com/watch?v=4C6584cGuLQ";
    private static final String URL_QUEMADURAS = "https://www.youtube.com/watch?v=8Ez1-DXOhD0";
    private static final String URL_OTROS_CASOS = "https://www.youtube.com/watch?v=WOuPi1UI-FA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_procedimientos);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout_procedimientos), (v, insets) -> {
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

        // --- LÓGICA DE LLAMADA DE EMERGENCIA ---
        CardView cardOfflineEmergencia = findViewById(R.id.card_offline_emergencia);
        Button btnOfflineEmergencia = findViewById(R.id.btn_offline_emergencia);

        View.OnClickListener emergencyCallListener = v -> {
            String numeroEmergencia = "911";

            // Crea un Intent con la acción ACTION_DIAL
            Intent intentLlamada = new Intent(Intent.ACTION_DIAL);
            intentLlamada.setData(Uri.parse("tel:" + numeroEmergencia));

            startActivity(intentLlamada);
        };

        // Asignar el listener al botón "TOCA EL BOTÓN"
        if (btnOfflineEmergencia != null) {
            btnOfflineEmergencia.setOnClickListener(emergencyCallListener);
        }

        // Asignar el listener a la CardView contenedora
        if (cardOfflineEmergencia != null) {
            cardOfflineEmergencia.setOnClickListener(emergencyCallListener);
        }
        // --- FIN LÓGICA DE LLAMADA DE EMERGENCIA ---


        // --- Configuración de los ítems de la parrilla con sus URL ---
        setupGridItem(R.id.procedimiento_rcp, R.drawable.icon_rcp, "RCP", URL_RCP);
        setupGridItem(R.id.procedimiento_hemorragias, R.drawable.icon_hemorragias, "Control de\nhemorragias", URL_HEMORRAGIAS);
        setupGridItem(R.id.procedimiento_heimlich, R.drawable.icon_heimlich, "Maniobra de\nHeimlich", URL_HEIMLICH);
        setupGridItem(R.id.procedimiento_fracturas, R.drawable.icon_fracturas, "Fracturas y\nesguinces", URL_FRACTURAS);
        setupGridItem(R.id.procedimiento_quemaduras, R.drawable.icon_quemaduras, "Quemaduras", URL_QUEMADURAS);
        setupGridItem(R.id.procedimiento_otros, R.drawable.icon_otros, "Otros casos", URL_OTROS_CASOS);
    }


    /**
     * Configura el ítem de la parrilla y asigna el OnClickListener para abrir la URL.
     * @param includeLayoutId ID del layout incluido (ej. R.id.procedimiento_rcp)
     * @param drawableId ID del recurso drawable para el icono.
     * @param textContent Texto a mostrar en el ítem.
     * @param url Enlace web a abrir al hacer clic.
     */
    private void setupGridItem(int includeLayoutId, int drawableId, String textContent, String url) {
        View includedLayout = findViewById(includeLayoutId);

        if (includedLayout instanceof LinearLayout) {

            TextView textView = includedLayout.findViewById(R.id.text_procedimiento);
            if (textView != null) {
                textView.setText(textContent);
            }

            ImageView imageView = includedLayout.findViewById(R.id.icon_procedimiento);
            if (imageView != null) {
                imageView.setImageResource(drawableId);
            }

            // Asigna el OnClickListener para abrir el enlace
            includedLayout.setOnClickListener(v -> openUrl(url));
        }
    }

    /**
     * Abre un enlace web en el navegador predeterminado del dispositivo.
     * @param url La URL que se desea abrir.
     */
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