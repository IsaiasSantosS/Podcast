package br.ufpe.cin.android.podcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.ufpe.cin.android.podcast.data.Episodio
import br.ufpe.cin.android.podcast.repository.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Classe que faz a entre o DB e as ação Solicitada
class EpisodeViewModel(val episodeRepository: EpisodeRepository) : ViewModel() {
    val episodes = episodeRepository.listaEpisodes

    fun inserir(episodio: Episodio) {
        viewModelScope.launch(Dispatchers.IO) {
            episodeRepository.inserir(episodio)
        }
    }

    fun remover(){
        viewModelScope.launch(Dispatchers.IO) {
            episodeRepository.remover()
        }
    }
}

//Classe de Factory que é exigidar para rodar o viewModel da DB
class EpisodeViewModelFactory(private val episodeRepository: EpisodeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EpisodeViewModel::class.java)) {
            return EpisodeViewModel(episodeRepository) as T
        }
        throw IllegalArgumentException("Arquivo de viewModel não encontrado.")
    }

}