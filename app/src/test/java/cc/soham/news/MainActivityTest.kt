package cc.soham.news

import android.os.Build
import androidx.lifecycle.MutableLiveData
import cc.soham.news.model.Article
import kotlinx.android.synthetic.main.activity_main.*
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O], packageName = "cc.soham.news")
class MainActivityTest : KoinTest {
    @Mock
    lateinit var mainViewModel: MainViewModel

    // create our own definition of dependencies in a unit-test environment
    val koinModule = module() {
        viewModel(override = true) { mainViewModel }
    }

    @Before
    fun setup() {
        // initialise mocks
        MockitoAnnotations.initMocks(this)
        // override the default koin modules
        loadKoinModules(listOf(koinModule))
    }

    @Test
    fun base_activity_shouldNotBeNull() {
        // create a mock state that we can return from the viewmodel
        var articlesState: MutableLiveData<ArticlesState> = MutableLiveData()
        var arrayOfArticles = listOf(Article(), Article())
        articlesState.value = ArticlesState.ArticlesFromNetwork(arrayOfArticles)
        Mockito.`when`(mainViewModel.getArticlesState()).thenReturn(articlesState)

        // start the activity
        var mainActivity: MainActivity = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .resume()
                .start()
                .visible()
                .get()

        // the recyclerview adapter should not be null
        mainActivity.activity_main_recyclerview.adapter shouldNotBe null
        // the number of items should be two
        mainActivity.activity_main_recyclerview.adapter!!.itemCount shouldEqualTo 2
    }

    @Test
    fun base_activity_shouldNotBeNull1() {
        // create a mock state that we can return from the viewmodel
        var articlesState: MutableLiveData<ArticlesState> = MutableLiveData()
        var arrayOfArticles = listOf(Article(), Article())
        articlesState.value = ArticlesState.ArticlesFromNetwork(arrayOfArticles)
        Mockito.`when`(mainViewModel.getArticlesState()).thenReturn(articlesState)

        // start the activity
        var mainActivity: MainActivity = Robolectric.buildActivity(MainActivity::class.java)
                .create()
                .resume()
                .start()
                .visible()
                .get()

        // the recyclerview adapter should not be null
        mainActivity.activity_main_recyclerview.adapter shouldNotBe null
        // the number of items should be two
        mainActivity.activity_main_recyclerview.adapter!!.itemCount shouldEqualTo 2
    }

    @After
    fun shutDown() {
        stopKoin()
    }
}