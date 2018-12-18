package cc.soham.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_recyclerview.setLayoutManager(LinearLayoutManager(this));

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getArticlesState().observe(this, Observer {
            when (it) {
                is ArticlesState.LoadingState -> handleArticleStateChangeToLoading(it)
                is ArticlesState.ErrorState -> handleArticleStateChangeToError(it)
                is ArticlesState.NoArticles -> handleArticleStateChangeToNoArticles(it)
                is ArticlesState.ArticlesFromLocalStorage -> handleArticleStateChangeToLocalStorage(it)
                is ArticlesState.ArticlesFromNetwork -> handleArticleStateChangeToNetwork(it)
            }
        })
    }

    fun handleArticleStateChangeToLoading(loadingState: ArticlesState.LoadingState) {
        activity_main_progressbar?.visibility = View.VISIBLE
        Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
    }
    fun handleArticleStateChangeToError(errorState: ArticlesState.ErrorState) {
        activity_main_progressbar?.visibility = View.GONE
        Toast.makeText(this@MainActivity, "Error received", Toast.LENGTH_SHORT).show()
    }

    fun handleArticleStateChangeToNoArticles(noArticles: ArticlesState.NoArticles) {}
    fun handleArticleStateChangeToLocalStorage(articlesFromLocalStorage: ArticlesState.ArticlesFromLocalStorage) {}

    fun handleArticleStateChangeToNetwork(articlesFromNetwork: ArticlesState.ArticlesFromNetwork) {
        activity_main_progressbar.setVisibility(View.GONE)
        showNewsApiSnack()
        Toast.makeText(this@MainActivity, "Response received", Toast.LENGTH_SHORT).show()
        activity_main_recyclerview.setAdapter(HomeNewsAdapter(articlesFromNetwork.articles))
    }

    private fun showNewsApiSnack() {
        Snackbar.make(activity_main, "Powered by NewsApi.org", Snackbar.LENGTH_LONG)
                .setAction("Visit") { loadNewsApiWebsite() }.show()
    }

    private fun loadNewsApiWebsite() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://newsapi.org")))
    }
}