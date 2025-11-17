package com.example.quickaid;

import android.content.Context;
import android.content.Intent; // Importar Intent
import android.net.Uri; // Importar Uri
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Importar CardView
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class AlertsActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private LinearLayout dotsLayout;
    private List<AlertItem> alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alerts);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout_alerts), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());


        Button btnEmergency = findViewById(R.id.btn_emergencia);
        CardView cardEmergency = findViewById(R.id.card_emergencia);


        View.OnClickListener emergencyCallListener = v -> {
            String numeroEmergencia = "911";

            /
            Intent intentLlamada = new Intent(Intent.ACTION_DIAL);
            intentLlamada.setData(Uri.parse("tel:" + numeroEmergencia));

            startActivity(intentLlamada);
        };


        btnEmergency.setOnClickListener(emergencyCallListener);


        cardEmergency.setOnClickListener(emergencyCallListener);



        alerts = getAlertsData();

        viewPager2 = findViewById(R.id.view_pager_alerts);
        dotsLayout = findViewById(R.id.layout_dots);

        AlertsAdapter adapter = new AlertsAdapter(alerts);
        viewPager2.setAdapter(adapter);

        setupPageIndicators(alerts.size());

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                selectDot(position);
            }
        });
    }

    private List<AlertItem> getAlertsData() {
        List<AlertItem> alerts = new ArrayList<>();

        List<String> tipsCalor = new ArrayList<>();
        tipsCalor.add("Mantente hidratado");
        tipsCalor.add("Usa gorra, lentes y ropa ligera");
        tipsCalor.add("Evita salir entre 11 a.m. y 4 p.m.");
        tipsCalor.add("Busca sombra o espacios frescos");
        alerts.add(new AlertItem(
                "Olas de Calor",
                R.drawable.sun_gradient,
                R.drawable.sun_icon,
                tipsCalor
        ));

        List<String> tipsFrio = new ArrayList<>();
        tipsFrio.add("Abrígate bien (capas)");
        tipsFrio.add("Evita cambios bruscos de temperatura");
        tipsFrio.add("Consume líquidos calientes");
        tipsFrio.add("Vacúnate contra la influenza");
        alerts.add(new AlertItem(
                "Frentes Fríos",
                R.drawable.snow_gradient,
                R.drawable.snow_icon,
                tipsFrio
        ));

        return alerts;
    }

    private void setupPageIndicators(int count) {
        if (dotsLayout.getChildCount() > 0) {
            dotsLayout.removeAllViews();
        }

        ImageView[] dots = new ImageView[count];
        for (int i = 0; i < count; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_bullet));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dotsLayout.addView(dots[i], params);
        }
        if (count > 0) {
            selectDot(0);
        }
    }

    private void selectDot(int position) {
        int count = dotsLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView dot = (ImageView) dotsLayout.getChildAt(i);

            if (i == position) {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_bullet)); // Asume que existe dot_bullet_selected
            } else {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_bullet)); // Asume que existe dot_bullet_unselected
            }
        }
    }

    private static class AlertItem {
        String title;
        int backgroundResId;
        int iconResId;
        List<String> tips;

        public AlertItem(String title, int backgroundResId, int iconResId, List<String> tips) {
            this.title = title;
            this.backgroundResId = backgroundResId;
            this.iconResId = iconResId;
            this.tips = tips;
        }
    }

    private static class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.AlertViewHolder> {

        private final List<AlertItem> alerts;

        public AlertsAdapter(List<AlertItem> alerts) {
            this.alerts = alerts;
        }

        @NonNull
        @Override
        public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alert_card, parent, false);
            return new AlertViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
            AlertItem alert = alerts.get(position);

            holder.title.setText(alert.title);
            holder.icon.setImageResource(alert.iconResId);

            holder.contentLayout.setBackgroundResource(alert.backgroundResId);

            holder.bulletContainer.removeAllViews();
            Context context = holder.itemView.getContext();
            int whiteColor = ContextCompat.getColor(context, R.color.white);

            for (String tip : alert.tips) {
                TextView bulletText = new TextView(context);
                bulletText.setText(tip);
                bulletText.setTextColor(whiteColor);
                bulletText.setTextSize(14);
                // Nota: Asumo que R.drawable.dot_bullet es el punto/bullet.
                bulletText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dot_bullet, 0, 0, 0);
                bulletText.setCompoundDrawablePadding(16);
                bulletText.setPadding(0, 0, 0, 8);
                holder.bulletContainer.addView(bulletText);
            }
        }

        @Override
        public int getItemCount() {
            return alerts.size();
        }

        static class AlertViewHolder extends RecyclerView.ViewHolder {
            TextView title;
            ImageView icon;
            ViewGroup contentLayout;
            LinearLayout bulletContainer;

            public AlertViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.alert_title);
                icon = itemView.findViewById(R.id.alert_icon);
                contentLayout = itemView.findViewById(R.id.alert_card_content);
                bulletContainer = itemView.findViewById(R.id.alert_bullet_container);
            }
        }
    }
}