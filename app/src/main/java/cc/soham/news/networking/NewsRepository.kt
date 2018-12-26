package cc.soham.news.networking

import androidx.lifecycle.MutableLiveData
import cc.soham.news.ArticlesState

interface NewsRepository {
    fun getNewsArticles(source: String, sortBy: String): MutableLiveData<ArticlesState>
}