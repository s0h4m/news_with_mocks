package cc.soham.news

import cc.soham.news.model.Article

sealed class ArticlesState {
    class InvalidState(): ArticlesState()
    class LoadingState(): ArticlesState()
    class ErrorState(val throwable: Throwable?): ArticlesState()
    class NoArticles(): ArticlesState()
    class ArticlesFromLocalStorage(val articles: List<Article>): ArticlesState()
    class ArticlesFromNetwork(val articles: List<Article>): ArticlesState()
}