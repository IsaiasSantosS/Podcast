package br.ufpe.cin.android.podcast


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.ufpe.cin.android.podcast.data.Episodio
import br.ufpe.cin.android.podcast.databinding.ItemfeedBinding

//Classe para carregar os dados para o recyclerView
class EpisodesAdapter(
    var inflater: LayoutInflater,
    val clickListener: (Episodio) -> Unit
) :
    ListAdapter<Episodio, EpisodesAdapter.ViewHolder>(EpisodioDiffer) {

    inner class ViewHolder(private val binding: ItemfeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var title = binding.itemTitle
        var datePublish = binding.itemDate
        var viewCard = binding.root

        fun bind(episodio: Episodio, clickListener: (Episodio) -> Unit) {
            title.text = episodio.titulo
            datePublish.text = episodio.dataPublicacao
            viewCard.setOnClickListener { clickListener(episodio) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesAdapter.ViewHolder {
        var binding = ItemfeedBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    private object EpisodioDiffer : DiffUtil.ItemCallback<Episodio>() {
        override fun areItemsTheSame(oldItem: Episodio, newItem: Episodio): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Episodio, newItem: Episodio): Boolean {
            return oldItem === newItem
        }

    }
}
