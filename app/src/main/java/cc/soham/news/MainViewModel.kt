package cc.soham.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cc.soham.news.networking.NewsRepository
import cc.soham.news.storage.StorageProvider
import javax.inject.Inject

open class MainViewModel @Inject constructor(application: Application, private val newsRepository: NewsRepository?, private val storageProvider: StorageProvider?) : AndroidViewModel(application) {
    private var articlesState: MutableLiveData<ArticlesState> = MutableLiveData()
    private var newsSource: String = "espn"
    private var sortBy: String = "top"
    var mainIdlingResource: MainIdlingResource? = null

    private val articlesStateObserver: Observer<ArticlesState> = Observer {
        when (it) {
            is ArticlesState.LoadingState -> handleArticleStateChangeToLoading(it)
            is ArticlesState.ErrorState -> handleArticleStateChangeToError(it)
            is ArticlesState.NoArticles -> handleArticleStateChangeToNoArticles(it)
            is ArticlesState.ArticlesFromLocalStorage -> handleArticleStateChangeToLocalStorage(it)
            is ArticlesState.ArticlesFromNetwork -> handleArticleStateChangeToNetwork(it)
        }
    }

    fun handleArticleStateChangeToLoading(loadingState: ArticlesState.LoadingState) {}
    fun handleArticleStateChangeToError(errorState: ArticlesState.ErrorState) {}
    fun handleArticleStateChangeToNoArticles(noArticles: ArticlesState.NoArticles) {}
    fun handleArticleStateChangeToLocalStorage(articlesFromLocalStorage: ArticlesState.ArticlesFromLocalStorage) {}
    fun handleArticleStateChangeToNetwork(articlesFromNetwork: ArticlesState.ArticlesFromNetwork) {
        if (articlesFromNetwork.articles.isNotEmpty()) {
            storageProvider?.store(articlesFromNetwork.articles)
        }
    }

    fun getArticlesState(): MutableLiveData<ArticlesState> {
        if (articlesState.value == null) {
            // if we haven't updated the articles state, get it from the repository and observe it
            articlesState = newsRepository!!.getNewsArticles(newsSource, sortBy)
            mainIdlingResource = MainIdlingResource(articlesState)
            observeArticlesState()
        }
        return articlesState
    }

    fun observeArticlesState() {
        articlesState.observeForever(articlesStateObserver)
    }

    override fun onCleared() {
        articlesState.removeObserver(articlesStateObserver)
        super.onCleared()
    }
}