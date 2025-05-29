package com.example.media_consumo_ultimo;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_about);

        Button btnLinkedIn = findViewById(R.id.btnLinkedIn);

        btnLinkedIn.setMovementMethod(LinkMovementMethod.getInstance());
        btnLinkedIn.setOnClickListener(v -> {
            String url = getString(R.string.sobre_linkedin_url);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());
    }
}
