package br.ufpe.cin.android.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){

    private val _articles = MutableLiveData<MutableList<Article>>()
    val articles: LiveData<MutableList<Article>> = _articles

    fun fetchUrl(parser: Parser, url: String){
        viewModelScope.launch {
           var channel = parser.getChannel(url)
            _articles.postValue(channel.articles)
        }
    }

}