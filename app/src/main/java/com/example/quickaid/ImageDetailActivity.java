package com.example.quickaid;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ImageDetailActivity extends AppCompatActivity {

    public static final String EXTRA_INFOGRAFIA_ID = "infografia_id";
    public static final String EXTRA_TEMA = "tema_infografia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        String infografiaId = getIntent().getStringExtra(EXTRA_INFOGRAFIA_ID);
        String tema = getIntent().getStringExtra(EXTRA_TEMA);

        ImageView imageView = findViewById(R.id.infografia_image_view);
        ImageButton closeButton = findViewById(R.id.btn_close_detail);

        closeButton.setOnClickListener(v -> finish());


        if (infografiaId != null && !infografiaId.isEmpty()) {
            int imageResId = getResources().getIdentifier(infografiaId, "drawable", getPackageName());

            if (imageResId != 0) {
                imageView.setImageResource(imageResId);
                if (tema != null) {
                    Toast.makeText(this, "Infografía: " + tema, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error de recurso: El archivo '" + infografiaId + "' no se encuentra.", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Error: No se especificó el recurso de la infografía.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}