package com.example.media_consumo_ultimo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.media_consumo_ultimo.adapter.OilChangeAdapter;
import com.example.media_consumo_ultimo.database.AppDatabase;
import com.example.media_consumo_ultimo.model.TrocaOleo;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OilChangeHistoryActivity extends AppCompatActivity {
    private OilChangeAdapter adapter;
    private Button buttonClearHistory, buttonBack;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oil_change_history);

        executor = Executors.newSingleThreadExecutor();

        // RecyclerView
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OilChangeAdapter();
        rv.setAdapter(adapter);

        // Botão Limpar Histórico
        buttonClearHistory = findViewById(R.id.buttonClearHistory);
        buttonClearHistory.setOnClickListener(v -> clearHistory());

        // Botão Voltar
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v ->
                startActivity(new Intent(this, TrocaOleoActivity.class)));

        // Observa LiveData do banco
        AppDatabase db = AppDatabase.getInstance(this);
        db.trocaOleoDao()
                .getAllLive()
                .observe(this, new Observer<List<TrocaOleo>>() {
                    @Override
                    public void onChanged(List<TrocaOleo> trocas) {
                        adapter.submitList(trocas);
                    }
                });
    }

    private void clearHistory() {
        executor.execute(() -> {
            AppDatabase.getInstance(this).trocaOleoDao().deleteAll();
            runOnUiThread(() ->
                    Toast.makeText(
                            OilChangeHistoryActivity.this,
                            "Histórico limpo com sucesso",
                            Toast.LENGTH_SHORT
                    ).show()
            );
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
