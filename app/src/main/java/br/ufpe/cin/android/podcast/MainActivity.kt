package br.ufpe.cin.android.podcast

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpe.cin.android.podcast.databinding.ActivityMainBinding
import com.prof.rssparser.Article
import com.prof.rssparser.Parser


class MainActivity : AppCompatActivity() {
    private lateinit var listaPodcast: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var parser: Parser

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuração do parser da Biblioteca RssParse
        parser = Parser.Builder()
            .context(this)
            .cacheExpirationMillis(24L * 60L * 60L * 100L)
            .build()

        //Configuração da listagem para um RecycleView
        listaPodcast = binding.listaPodcast
        listaPodcast.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        //Configuração do para salvar url do feed na preferences
        val rssFeedDefault = getString(R.string.link_inicial)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        var url = preferences.getString("rssfeed", rssFeedDefault)

        //Carregando os dados por um view model
        if (url != null) {
            viewModel.fetchUrl(parser, url)

            viewModel.articles.observe(this,
                Observer<MutableList<Article>> { articles ->
                    val adapter = EpisodesAdapter(articles,
                        this@MainActivity.layoutInflater
                    ) { article: Article -> partItemClicked(article) }

                    listaPodcast.adapter = adapter }
            )
        }
        super.onStart()
    }

    //Função cria o menu na actionBar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Ação de quando clicar menu
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.imEditarPoscast -> {
            startActivity(Intent(this, PreferenciasActivity::class.java))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /*  Função com a ação de quando clicar em alguns cardview
        fonte: https://www.andreasjakl.com/recyclerview-kotlin-style-click-listener-android/
     */
    private fun partItemClicked(article: Article) {
        var intent = Intent(
            this,
            EpisodeDetailActivity::class.java
        )
            .putExtra("imagem", article.image)
            .putExtra("descricao", article.description)
            .putExtra("link", article.link)
            .putExtra("titulo", article.title)

        startActivity(intent)
    }
}