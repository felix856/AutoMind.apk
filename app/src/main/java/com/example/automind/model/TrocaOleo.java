
// TrocaOleo.java (modelo de entidade)
        package com.example.media_consumo_ultimo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.util.Objects;

@Entity(tableName = "troca_oleo")
public class TrocaOleo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String data;
    private int odometro;
    private double custo;
    private String observacoes;

    public TrocaOleo(@NonNull String data, int odometro, double custo, String observacoes) {
        this.data = data;
        this.odometro = odometro;
        this.custo = custo;
        this.observacoes = observacoes;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getData() {
        return data;
    }

    public void setData(@NonNull String data) {
        this.data = data;
    }

    public int getOdometro() {
        return odometro;
    }

    public void setOdometro(int odometro) {
        this.odometro = odometro;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrocaOleo that = (TrocaOleo) o;
        return id == that.id && odometro == that.odometro
                && Double.compare(that.custo, custo) == 0
                && Objects.equals(data, that.data)
                && Objects.equals(observacoes, that.observacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, odometro, custo, observacoes);
    }
}
