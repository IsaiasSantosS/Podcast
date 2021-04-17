package br.ufpe.cin.android.podcast

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.ufpe.cin.android.podcast.databinding.ActivityEpisodeDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

//Tela de detalhes do episodio
class EpisodeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEpisodeDetailBinding
    private var coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
    private lateinit var bmp: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dadosExtraIntent = intent
        var imagemPoscast = dadosExtraIntent.getStringExtra("imagem")
        var descricaoPoscast = dadosExtraIntent.getStringExtra("descricao")
        var tituloPoscast = dadosExtraIntent.getStringExtra("titulo")
        var linkPoscast = dadosExtraIntent.getStringExtra("link")

        binding.txtTitulo.text = tituloPoscast
        binding.txtDescricaoPodcast.text = descricaoPoscast

        //buscar imagem
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val url = URL(imagemPoscast)
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            }
            binding.imgPodcast.setImageBitmap(bmp)
        }

        binding.btnLinkPodcast.setOnClickListener {
            var i = Intent()
            i.action = Intent.ACTION_VIEW
            i.data = Uri.parse(linkPoscast)
            i.addCategory(Intent.CATEGORY_DEFAULT)
            i.addCategory(Intent.CATEGORY_BROWSABLE)

            startActivity(i)
        }
    }
}