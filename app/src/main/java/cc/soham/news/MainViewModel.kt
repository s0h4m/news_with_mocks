package cc.soham.news

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import cc.soham.news.networking.NewsRepository
import cc.soham.news.networking.RealNewsRepository
import cc.soham.news.storage.StorageProvider
import cc.soham.news.storage.TemporaryStorageProvider

open class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var articlesState: MutableLiveData<ArticlesState> = MutableLiveData()
    private var newsSource: String = "espn"
    private var sortBy: String = "top"
    private var newsRepository: NewsRepository? = null
    private var storageProvider: StorageProvider? = null

    private val articlesStateObserver: Observer<ArticlesState> = Observer {
        temp()
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

    fun temp() {

    }

    fun setNewsRepository(newsRepository: NewsRepository) {
        this.newsRepository = newsRepository
    }

    fun setStorageProvider(storageProvider: StorageProvider) {
        this.storageProvider = storageProvider
    }

    fun getArticlesState(): MutableLiveData<ArticlesState> {
        if (newsRepository == null) {
            // initialise a news repository
            newsRepository = RealNewsRepository()
        }
        if (storageProvider == null) {
            // initialise a storage provider
            storageProvider = TemporaryStorageProvider()
        }
        if (articlesState.value == null) {
            // if we haven't updated the articles state, get it from the repository and observe it
            articlesState = newsRepository!!.getNewsArticles(newsSource, sortBy)
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