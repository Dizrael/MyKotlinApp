package ru.dizraelapps.mykotlinapp.ui.note

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import io.mockk.*
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.not
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
import ru.dizraelapps.mykotlinapp.extencions.getColorInt

class NoteActivityTest {
    @get:Rule
    val activityTestRule = IntentsTestRule(NoteActivity::class.java, true, false)

    private val viewModel: NoteViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<NoteViewState>()
    private val testNote = Note("id1", "title1", "text1")

    @Before
    fun setup() {
        clearAllMocks()
        loadKoinModules(
            listOf(
                module {
                    viewModel { viewModel }
                }
            )
        )

        every { viewModel.getViewState() } returns viewStateLiveData
        every { viewModel.loadNote(any()) } just runs
        every { viewModel.saveNote(any()) } just runs
        every { viewModel.deleteNote() } just runs

        Intent().apply {
            putExtra(NoteActivity::class.java.name + "extra.NOTE_ID", testNote.id)
        }.let {
            activityTestRule.launchActivity(it)
        }
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun should_show_color_picker(){
        onView(withId(R.id.palette)).perform(click())
        onView(withId(R.id.colorPicker)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun should_hide_color_picker(){
        onView(withId(R.id.palette)).perform(click()).perform(click())
        onView(withId(R.id.colorPicker)).check(matches(not(isDisplayed())))
    }

    @Test
    fun should_set_toolbar_color() {
        onView(withId(R.id.palette)).perform(click())
        onView(withTagKey(Note.Color.YELLOW.getColorInt(activityTestRule.activity))).perform(click()).perform(click())

        val colorInt = Note.Color.YELLOW.getColorInt(activityTestRule.activity)

        onView(withId(R.id.toolbar)).check{ view, _ ->
            assertTrue("toolbar background color doesn't match",
                (view.background as? ColorDrawable)?.color == colorInt)
        }
    }

    }