package br.ufpe.cin.android.podcast


import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.ufpe.cin.android.podcast.databinding.ItemfeedBinding
import com.prof.rssparser.Article

class EpisodesAdapter(
    private val articles: MutableList<Article>,
    var inflater: LayoutInflater,
    val clickListener: (Article) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemfeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var title = binding.itemTitle
        var datePublish = binding.itemDate
        var viewCard = binding.root

        fun bind(article: Article, clickListener: (Article) -> Unit) {
            title.text = article.title.toString().subSequence(0, 25)
            datePublish.text = article.pubDate
            viewCard.setOnClickListener { clickListener(article) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesAdapter.ViewHolder {
        var binding = ItemfeedBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodesAdapter.ViewHolder, position: Int) {
        holder.bind(articles[position], clickListener)
    }

    override fun getItemCount(): Int {
        return articles.count()
    }
}
