package cc.soham.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

class MainIdlingResource : IdlingResource {
    private val NAME = "MainIdlingResource"

    var articlesState: MutableLiveData<ArticlesState>? = null
    var isIdle: AtomicBoolean = AtomicBoolean(false)
    var callback: IdlingResource.ResourceCallback? = null

    constructor(articlesState: MutableLiveData<ArticlesState>?) {
        this.articlesState = articlesState
        this.articlesState?.observeForever(articlesStateObserver)
    }

    private val articlesStateObserver: Observer<ArticlesState> = Observer {
        when (it) {
            is ArticlesState.LoadingState -> setIdleState(false)
            else -> setIdleState(true)
        }
    }

    fun setIdleState(idle: Boolean) {
        isIdle = AtomicBoolean(idle)
        if (isIdle.get()) {
            articlesState?.removeObserver(articlesStateObserver)
            articlesState = null
            callback?.onTransitionToIdle()
        }
    }

    override fun getName(): String = NAME

    override fun isIdleNow(): Boolean = isIdle.get()

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }
}