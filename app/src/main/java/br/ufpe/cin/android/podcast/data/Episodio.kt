package br.ufpe.cin.android.podcast.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Classe que representa o a tabela salva no BD
@Entity(tableName = "episodios")
data class Episodio(
    @PrimaryKey
    val linkEpisodio: String,
    val titulo: String,
    val descricao: String,
    val linkArquivo: String,
    val dataPublicacao: String,
    val linkImagem: String
) {
    override fun toString(): String {
        return "$titulo ($dataPublicacao) => $descricao"
    }
}