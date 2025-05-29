package com.example.media_consumo_ultimo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.media_consumo_ultimo.database.AppDatabase;
import com.example.media_consumo_ultimo.model.TrocaOleo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrocaOleoActivity extends AppCompatActivity {
    private EditText editData, editOdometro, editCusto, editObs;
    private Button buttonSalvar, buttonVerHistorico,buttonTelaPrincipal;
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_oleo);

        // Vincula as views
        editData           = findViewById(R.id.editData);
        editOdometro       = findViewById(R.id.editOdometro);
        editCusto          = findViewById(R.id.editCusto);
        editObs            = findViewById(R.id.editObs);
        buttonSalvar       = findViewById(R.id.buttonSalvar);
        buttonVerHistorico = findViewById(R.id.buttonVerHistorico);
        buttonTelaPrincipal = findViewById(R.id.buttonTelaPrincipal);

        // Executor para rodar DB fora da UI
        executor = Executors.newSingleThreadExecutor();

        // Clique em Salvar
        buttonSalvar.setOnClickListener(v -> saveTroca());

        // Clique em Ver Histórico
        buttonVerHistorico.setOnClickListener(v ->
                startActivity(new Intent(TrocaOleoActivity.this, OilChangeHistoryActivity.class))
        );
        // Clique em Tela Principal
        buttonTelaPrincipal.setOnClickListener(v ->
                startActivity(new Intent(TrocaOleoActivity.this, MainActivity.class))
        );
    }

    private void saveTroca() {
        String data        = editData.getText().toString().trim();
        String odoStr      = editOdometro.getText().toString().trim();
        String custoStr    = editCusto.getText().toString().trim();
        String observacoes = editObs.getText().toString().trim();

        // Validação simples
        if (data.isEmpty() || odoStr.isEmpty() || custoStr.isEmpty()) {
            editData.setError("Preencha todos os campos obrigatórios");
            return;
        }

        int odometro = Integer.parseInt(odoStr);
        double custo = Double.parseDouble(custoStr);
        TrocaOleo troca = new TrocaOleo(data, odometro, custo, observacoes);

        executor.execute(() -> {
            AppDatabase.getInstance(this)
                    .trocaOleoDao()
                    .insert(troca);
            // Após inserir, fecha a Activity (volta pra main)
            runOnUiThread(this::finish);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
