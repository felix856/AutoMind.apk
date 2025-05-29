package com.example.media_consumo_ultimo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.media_consumo_ultimo.model.TrocaOleo;
import java.util.List;

@Dao
public interface TrocaOleoDao {

    @Insert
    void insert(TrocaOleo troca);

    // Query ajustada para o nome correto da tabela e coluna
    @Query("SELECT * FROM troca_oleo ORDER BY data DESC")
    LiveData<List<TrocaOleo>> getAllLive();

    @Query("SELECT * FROM troca_oleo ORDER BY data DESC")
    List<TrocaOleo> getAll(); // opcional
    @Query("DELETE FROM troca_oleo")
    void deleteAll();
}
