package br.ufpe.cin.android.podcast.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EpisodioDAO {

    @Insert
    suspend fun inserir(episodio: Episodio)

    @Query("DELETE FROM episodios")
    suspend fun remover()

    @Query("SELECT * FROM episodios")
    fun carregarTodos() : LiveData<List<Episodio>>
}