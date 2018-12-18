package cc.soham.news.storage

import cc.soham.news.model.Article

interface StorageProvider {
    fun store(articles: List<Article>)
    fun retrieve(): List<Article>
}

class TemporaryStorageProvider: StorageProvider {
    override fun store(articles: List<Article>) {

    }

    override fun retrieve(): List<Article> {
        return ArrayList()
    }
}