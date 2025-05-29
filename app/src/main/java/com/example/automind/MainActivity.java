package com.example.media_consumo_ultimo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvResultado;
    private TextView tvMediaGeral;
    private EditText etKmAnterior, etKmAtual, etLitros, etPreco;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "ConsumoPrefs";
    private static final String KEY_DIST  = "totalDistancia";
    private static final String KEY_LIT   = "totalLitros";
    private static final String KEY_CUSTO = "totalCusto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa views
        TextView tvData = findViewById(R.id.tvData);
        etKmAnterior = findViewById(R.id.etKmAnterior);
        etKmAtual    = findViewById(R.id.etKmAtual);
        etLitros     = findViewById(R.id.etLitros);
        etPreco      = findViewById(R.id.etPreco);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        tvResultado  = findViewById(R.id.tvResultado);
        tvMediaGeral = findViewById(R.id.tvMediaGeral);

        // Botões de navegação e limpeza
        findViewById(R.id.btnSobre).setOnClickListener(v ->
                startActivity(new Intent(this, AboutActivity.class))
        );
        findViewById(R.id.btnAjuda).setOnClickListener(v ->
                startActivity(new Intent(this, AjudaActivity.class))
        );
        findViewById(R.id.btnLimparTela).setOnClickListener(v -> {
            etKmAnterior.setText("");
            etKmAtual.setText("");
            etLitros.setText("");
            etPreco.setText("");
            tvResultado.setText("");
            tvMediaGeral.setText(getString(R.string.media_geral_placeholder));
        });
        findViewById(R.id.btnTrocaOleo).setOnClickListener(v -> {
            startActivity(new Intent(this, TrocaOleoActivity.class));
        });


        // Exibe data atual
        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);
        String hoje = String.format(Locale.getDefault(), "%02d/%02d/%04d", dia, mes, ano);
        tvData.setText(getString(R.string.tv_data_format, hoje));

        // SharedPreferences e média geral
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        atualizaMediaGeral();

        // Listener de cálculo
        btnCalcular.setOnClickListener(this::onCalcularClicked);
    }

    private void onCalcularClicked(android.view.View v) {
        String sKmAnt = etKmAnterior.getText().toString().trim();
        String sKmAt  = etKmAtual.getText().toString().trim();
        String sLit   = etLitros.getText().toString().trim();
        String sPreco = etPreco.getText().toString().trim();

        if (sKmAnt.isEmpty() || sKmAt.isEmpty() || sLit.isEmpty() || sPreco.isEmpty()) {
            Toast.makeText(this,
                    getString(R.string.erro_preencher_campos),
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        double kmAnt, kmAt, litros, preco;
        try {
            kmAnt  = Double.parseDouble(sKmAnt);
            kmAt   = Double.parseDouble(sKmAt);
            litros = Double.parseDouble(sLit);
            preco  = Double.parseDouble(sPreco);
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    getString(R.string.erro_valores_invalidos),
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (kmAt <= kmAnt || litros <= 0 || preco <= 0) {
            Toast.makeText(this,
                    getString(R.string.erro_checar_valores),
                    Toast.LENGTH_LONG
            ).show();
            return;
        }

        double distancia    = kmAt - kmAnt;
        double consumoMedio = distancia / litros;
        double custoTotal   = litros * preco;
        double custoPorKm   = custoTotal / distancia;

        // Exibe resultado
        tvResultado.setText(
                getString(R.string.resultado_format,
                        distancia, consumoMedio, custoTotal, custoPorKm)
        );

        // Atualiza SharedPreferences
        float totalDist = prefs.getFloat(KEY_DIST,  0f) + (float) distancia;
        float totalLit  = prefs.getFloat(KEY_LIT,   0f) + (float) litros;
        float totalC    = prefs.getFloat(KEY_CUSTO, 0f) + (float) custoTotal;
        prefs.edit()
                .putFloat(KEY_DIST,  totalDist)
                .putFloat(KEY_LIT,   totalLit)
                .putFloat(KEY_CUSTO, totalC)
                .apply();

        // Atualiza média e limpa apenas os campos de km/litro/preço
        atualizaMediaGeral();
        limparCampos();
    }

    private void atualizaMediaGeral() {
        float totalDist = prefs.getFloat(KEY_DIST,  0f);
        float totalLit  = prefs.getFloat(KEY_LIT,   0f);
        float totalC    = prefs.getFloat(KEY_CUSTO, 0f);

        if (totalLit > 0 && totalDist > 0) {
            double mediaConsumo = totalDist / totalLit;
            double mediaCustoKm = totalC / totalDist;
            tvMediaGeral.setText(
                    getString(R.string.media_geral_format,
                            mediaConsumo, mediaCustoKm)
            );
        } else {
            tvMediaGeral.setText(getString(R.string.sem_media));
        }
    }

    private void limparCampos() {
        etKmAnterior.setText("");
        etKmAtual.setText("");
        etLitros.setText("");
        etPreco.setText("");
        etKmAnterior.requestFocus();
    }
}
