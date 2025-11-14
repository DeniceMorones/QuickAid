package com.example.quickaid;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout; // Necesario para acceder a los layouts incluidos

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProcedimientosActivity extends AppCompatActivity {

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


        setupGridItem(R.id.procedimiento_rcp, R.drawable.icon_rcp, "RCP");
        setupGridItem(R.id.procedimiento_hemorragias, R.drawable.icon_hemorragias, "Control de\nhemorragias");
        setupGridItem(R.id.procedimiento_heimlich, R.drawable.icon_heimlich, "Maniobra de\nHeimlich");
        setupGridItem(R.id.procedimiento_fracturas, R.drawable.icon_fracturas, "Fracturas y\nesguinces");
        setupGridItem(R.id.procedimiento_quemaduras, R.drawable.icon_quemaduras, "Quemaduras");
        setupGridItem(R.id.procedimiento_otros, R.drawable.icon_otros, "Otros casos");
    }


    private void setupGridItem(int includeLayoutId, int drawableId, String textContent) {
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

            includedLayout.setOnClickListener(v -> {
                // Aquí iría la lógica de navegación a la actividad de detalle de cada procedimiento
                // Por ejemplo: Intent intent = new Intent(this, DetailActivity.class);
                // intent.putExtra("PROCEDIMIENTO_NOMBRE", textContent);
                // startActivity(intent);
            });
        }
    }
}