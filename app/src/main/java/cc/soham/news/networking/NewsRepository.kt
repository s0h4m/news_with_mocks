package cc.soham.news.networking

import android.arch.lifecycle.MutableLiveData
import cc.soham.news.ArticlesState

interface NewsRepository {
    fun getNewsArticles(source: String, sortBy: String): MutableLiveData<ArticlesState>
}