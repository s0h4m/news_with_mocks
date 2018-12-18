package cc.soham.news

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import cc.soham.news.model.Article
import cc.soham.news.networking.NewsRepository
import cc.soham.news.storage.StorageProvider
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.never
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ViewModelTest {
    @Mock
    lateinit var application: Application

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var newsRepository: NewsRepository

    @Mock
    lateinit var storageProvider: StorageProvider

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @Captor
    lateinit var argumentCaptor: ArgumentCaptor<List<Article>>

    @Before
    fun setup() {
        mainViewModel = MainViewModel(application)
        mainViewModel.setNewsRepository(newsRepository)
        mainViewModel.setStorageProvider(storageProvider)
    }

    @Test
    fun viewModelArticleState_validNetworkResponse_storesProperly() {
        // arrange
        var articlesState: MutableLiveData<ArticlesState> = MutableLiveData()
        Mockito.`when`(newsRepository.getNewsArticles(Mockito.anyString(), Mockito.anyString())).thenReturn(articlesState)
        var arrayOfArticles = arrayListOf(Article(), Article())

        // act
        mainViewModel.getArticlesState()
        articlesState.value = ArticlesState.ArticlesFromNetwork(arrayOfArticles)

        // assert
        Mockito.verify(storageProvider).store(capture(argumentCaptor))
        argumentCaptor.value.size.shouldEqualTo(2)
    }

    @Test
    fun viewModelArticleState_errorState_doesNotStore() {
        // arrange
        var articlesState: MutableLiveData<ArticlesState> = MutableLiveData()
        Mockito.`when`(newsRepository.getNewsArticles(Mockito.anyString(), Mockito.anyString())).thenReturn(articlesState)
        val throwable = Throwable()

        // act
        mainViewModel.getArticlesState()
        articlesState.value = ArticlesState.ErrorState(throwable)

        // assert
        Mockito.verify(storageProvider, never()).store(Mockito.anyList())
    }
}