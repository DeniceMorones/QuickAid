package com.example.quickaid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

public class OfflineActivity extends AppCompatActivity {

    private static final String TAG = "OfflineActivity";


    private final int[] PREVENCION_PDFS = {
            R.raw.pdf_prevencion_1, // Archivo 1
            R.raw.pdf_prevencion_2, // Archivo 2
            R.raw.pdf_prevencion_3  // Archivo 3
    };

    private final int[] AUXILIOS_PDFS = {
            R.raw.pdf_auxilios_1, // Archivo 1
            R.raw.pdf_auxilios_2, // Archivo 2
            R.raw.pdf_auxilios_3  // Archivo 3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_offline);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout_offline), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupNavigationAndEmergencyCall();


        Button btnDescargarPrevencion = findViewById(R.id.btn_descargar_prevencion);
        Button btnDescargarAuxilios = findViewById(R.id.btn_descargar_auxilios);

        btnDescargarPrevencion.setOnClickListener(v -> {
            downloadMultiplePdfs(PREVENCION_PDFS, "prevencion");
        });

        btnDescargarAuxilios.setOnClickListener(v -> {
            downloadMultiplePdfs(AUXILIOS_PDFS, "auxilios");
        });
    }


    private void downloadMultiplePdfs(int[] pdfResourceIds, String folderName) {
        int successfulCopies = 0;
        File lastCopiedFile = null;

        File targetDir = new File(getExternalFilesDir(null), folderName);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        for (int resourceId : pdfResourceIds) {

            String resourceName = getResources().getResourceEntryName(resourceId);
            String targetFileName = resourceName + ".pdf";

            File destinationFile = new File(targetDir, targetFileName);

            if (copyRawResourceToFile(this, resourceId, destinationFile)) {
                successfulCopies++;
                lastCopiedFile = destinationFile;
            }
        }

        if (successfulCopies > 0) {
            String message = successfulCopies + " archivos de " + folderName + " descargados en su almacenamiento local.";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            viewPdfFile(lastCopiedFile);
        } else {
            Toast.makeText(this, "Error al descargar los archivos. Verifique los recursos.", Toast.LENGTH_LONG).show();
        }
    }



    private boolean copyRawResourceToFile(Context context, int resourceId, File destinationFile) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = context.getResources().openRawResource(resourceId);
            outputStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error copiando el archivo PDF: " + destinationFile.getName(), e);
            return false;
        } finally {
            try {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                Log.e(TAG, "Error cerrando streams", e);
            }
        }
    }


    private void viewPdfFile(File pdfFile) {
        if (pdfFile != null && pdfFile.exists()) {
            try {
                Uri contentUri = FileProvider.getUriForFile(
                        this,
                        "com.example.quickaid.fileprovider", // Debe coincidir con el valor en AndroidManifest.xml
                        pdfFile);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(contentUri, "application/pdf");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(Intent.createChooser(intent, "Abrir PDF con..."));
            } catch (Exception e) {
                Toast.makeText(this, "No se encontró una aplicación para abrir PDFs.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error al intentar abrir el PDF", e);
            }
        }
    }

    private void setupNavigationAndEmergencyCall() {
        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());

        Button btnOfflineEmergencia = findViewById(R.id.btn_offline_emergencia);
        CardView cardOfflineEmergencia = findViewById(R.id.card_offline_emergencia);

        View.OnClickListener emergencyCallListener = v -> {
            String numeroEmergencia = "911";
            Intent intentLlamada = new Intent(Intent.ACTION_DIAL);
            intentLlamada.setData(Uri.parse("tel:" + numeroEmergencia));
            startActivity(intentLlamada);
        };

        btnOfflineEmergencia.setOnClickListener(emergencyCallListener);
        cardOfflineEmergencia.setOnClickListener(emergencyCallListener);

        Button infografiasButton = findViewById(R.id.btn_ver_infografias);
        infografiasButton.setOnClickListener(v -> {
            Intent intentInfografias = new Intent(OfflineActivity.this, InfografiasActivity.class);
            startActivity(intentInfografias);

            Toast.makeText(OfflineActivity.this, "Navegando a Infografias", Toast.LENGTH_SHORT).show();
        });
    }
}