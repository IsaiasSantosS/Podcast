package br.ufpe.cin.android.podcast

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpe.cin.android.podcast.data.Episodio
import br.ufpe.cin.android.podcast.data.PodcastDB
import br.ufpe.cin.android.podcast.databinding.ActivityMainBinding
import br.ufpe.cin.android.podcast.repository.EpisodeRepository
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var listaPodcast: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var parser: Parser
    private lateinit var preferences: SharedPreferences
    private var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private val viewModelEpisode: EpisodeViewModel by viewModels() {
        val dao = EpisodeRepository(PodcastDB.getInstance(this).dao())
        EpisodeViewModelFactory(dao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configuração do parser da Biblioteca RssParse
        parser = Parser.Builder()
            .context(this)
            .cacheExpirationMillis(24L * 60L * 60L * 100L)
            .build()

        preferences = PreferenceManager.getDefaultSharedPreferences(this)

        //Configuração do RecycleView
        listaPodcast = binding.listaPodcast
        listaPodcast.layoutManager = LinearLayoutManager(this)
    }

    //Quando alterar o link na pereferences atualizar a lista de podcast
    override fun onStart() {

        //Configuração do para buscar url do feed na preferences
        var url = preferences.getString("rssfeed", getString(R.string.link_inicial))


        //Carregando os dados por um view model
        if (url != null) {
            //Atualizar lista
            salvarListaPodcast(url)

            //Gerar o adapter para o recyclerView
            val adapter = EpisodesAdapter(this@MainActivity.layoutInflater) { episodio: Episodio ->
                partItemClicked(episodio)
            }
            listaPodcast.adapter = adapter
            viewModelEpisode.episodes.observe(
                this,
                Observer {
                    adapter.submitList(it.toList())
                }
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
    private fun partItemClicked(episodio: Episodio) {
        var intent = Intent(
            this,
            EpisodeDetailActivity::class.java
        )
            .putExtra("imagem", episodio.linkImagem)
            .putExtra("descricao", episodio.descricao)
            .putExtra("link", episodio.linkEpisodio)
            .putExtra("titulo", episodio.titulo)

        startActivity(intent)
    }

    //Pegar dados da url e pecorre lista de artigos e salvar no banco de dados
    private fun salvarListaPodcast(url: String) {
        viewModelEpisode.remover()
        coroutineScope.launch {
            withContext(Dispatchers.Default) {
                var channel = parser.getChannel(url)
                channel.articles.forEach {
                    viewModelEpisode.inserir(
                        Episodio(
                            it.link.toString(),
                            it.title.toString(),
                            it.description.toString(),
                            it.sourceUrl.toString(),
                            it.pubDate.toString(),
                            it.image.toString()
                        )
                    )
                }
            }
        }
    }

    /*
    Link de podcast para teste
    Alura: https://hipsters.tech/category/podcast/feed/
    Do zero ao topo: https://www.spreaker.com/show/3549440/episodes/feed
     */
}