package ru.dizraelapps.mykotlinapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import ru.dizraelapps.mykotlinapp.R
import ru.dizraelapps.mykotlinapp.data.entity.Note

class MainActivityTest {
    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java, true, false)

    private val model: MainViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<MainViewState>()
    private val testNotes = listOf(
        Note("id1", "title1", "text1"),
        Note("id2", "title2", "text2"),
        Note("id3", "title3", "text3"),
        Note("id4", "title4", "text4"),
        Note("id5", "title5", "text5")
    )

    @Before
    fun setup() {
        clearAllMocks()
        loadKoinModules(
            listOf(
                module {
                    viewModel { model }
                }
            )
        )

        every { model.getViewState() } returns viewStateLiveData
        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(MainViewState(notes = testNotes))
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun check_data_is_displayed() {
        onView(withId(R.id.recycler_view_notes)).perform(
            scrollToPosition<NotesRecyclerViewAdapter.ViewHolder>(1)
        )
        onView(withText(testNotes[1].text)).check(matches(isDisplayed()))
    }
}