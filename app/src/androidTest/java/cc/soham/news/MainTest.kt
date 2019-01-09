package cc.soham.news

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainTest {
    var mainIdlingResource: MainIdlingResource? = null
    lateinit var mainViewModel: MainViewModel
    lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun registerIdlingResource() {

    }

    @Test
    fun itemsLoaded_clickOnItem_opensProperly() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity {
            mainViewModel = it.mainViewModel
            mainIdlingResource = mainViewModel.mainIdlingResource

            IdlingRegistry.getInstance().register(mainIdlingResource)
        }

        Espresso.onView(ViewMatchers.withId(R.id.activity_main_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition<androidx.recyclerview.widget.RecyclerView.ViewHolder>(0, click()))
        Espresso.onView(ViewMatchers.withId(R.id.activity_main_recyclerview)).check(doesNotExist())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mainIdlingResource)
    }
}