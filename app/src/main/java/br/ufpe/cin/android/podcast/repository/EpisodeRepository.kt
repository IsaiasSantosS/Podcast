package br.ufpe.cin.android.podcast.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import br.ufpe.cin.android.podcast.data.Episodio
import br.ufpe.cin.android.podcast.data.EpisodioDAO

//Repository para função de manipulação do banco de dados
class EpisodeRepository(private val dao: EpisodioDAO) {
    val listaEpisodes : LiveData<List<Episodio>> = dao.carregarTodos()

    @WorkerThread
    suspend fun inserir(episodio: Episodio) {
        dao.inserir(episodio)
    }

    @WorkerThread
    suspend fun remover() {
        dao.remover()
    }
}