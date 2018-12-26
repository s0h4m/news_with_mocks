package cc.soham.news.networking

import androidx.lifecycle.MutableLiveData
import cc.soham.news.ArticlesState
import cc.soham.news.model.Article
import cc.soham.news.model.GetArticlesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RealNewsRepository : NewsRepository {
    override fun getNewsArticles(source: String, sortBy: String): MutableLiveData<ArticlesState> {
        // at the beginning initialise to a loading state
        val articlesState: MutableLiveData<ArticlesState> = MutableLiveData<ArticlesState>().apply {
            value = ArticlesState.LoadingState()
        }
        // make the network call
        NewsAPI.getApi().getArticles(source, sortBy).enqueue(object : Callback<GetArticlesResponse> {
            override fun onResponse(call: Call<GetArticlesResponse>?, response: Response<GetArticlesResponse>?) {
                // set the state appropriately
                if (response != null) {
                    articlesState.value = ArticlesState.ArticlesFromNetwork(response.body().articles)
                } else {
                    articlesState.value = ArticlesState.NoArticles()
                }
            }

            override fun onFailure(call: Call<GetArticlesResponse>?, t: Throwable?) {
                // set the error state
                articlesState.value = ArticlesState.ErrorState(t)
            }
        })
        return articlesState
    }
}

class FakeNewsRepository(var source: String, var sortBy: String): NewsRepository {
    val articlesState: MutableLiveData<ArticlesState> = MutableLiveData<ArticlesState>().apply {
        value = ArticlesState.LoadingState()
    }

    override fun getNewsArticles(source: String, sortBy: String): MutableLiveData<ArticlesState> {
        // at the beginning initialise to a loading state
        this.source = source
        this.sortBy = sortBy
        return articlesState
    }

    fun changeStateToLoading() {
        articlesState.value = ArticlesState.LoadingState()
    }

    fun changeStateToError(throwable: Throwable?) {
        articlesState.value = ArticlesState.ErrorState(throwable)
    }

    fun changeStateToNoArticles() {
        articlesState.value = ArticlesState.NoArticles()
    }

    fun changeStateToNetworkBasedArticles(articles: List<Article>) {
        articlesState.value = ArticlesState.ArticlesFromNetwork(articles)
    }
}