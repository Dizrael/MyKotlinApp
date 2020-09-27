package ru.dizraelapps.mykotlinapp.ui.note


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.*
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.dizraelapps.mykotlinapp.data.NotesRepository
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.model.NoteResult
import java.lang.NullPointerException

class NoteViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()
    private val noteLiveData = MutableLiveData<NoteResult>()
    private val testNote = Note("1")

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup(){
        clearAllMocks()
        every { mockRepository.getNoteById(testNote.id) } returns noteLiveData
        every { mockRepository.deleteNote(testNote.id) } returns noteLiveData
        viewModel = NoteViewModel(mockRepository)
    }

    @Test
    fun `loadNote should return note`(){
        var result: NoteViewState.Data? = null
        val testData = NoteViewState.Data(false, testNote)
        viewModel.getViewState().observeForever{
            result = it?.data
        }
        viewModel.loadNote(testNote.id)
        noteLiveData.value = NoteResult.Success(testNote)
        assertEquals(testData, result)
    }

    @Test
    fun `loadNote should return error`(){
        var result: Throwable? = null
        val testError = Throwable("error")
        viewModel.getViewState().observeForever{
            result = it?.error
        }
        viewModel.loadNote(testNote.id)
        noteLiveData.value = NoteResult.Error(testError)
        assertEquals(result, testError)
    }

    @Test
    fun `deleteNote should return NoteData with isDeleted`(){
        var result: NoteViewState.Data? = null
        val testData = NoteViewState.Data(true, null)
        viewModel.getViewState().observeForever{
            result = it?.data
        }

        viewModel.saveNote(testNote)
        viewModel.deleteNote()
        noteLiveData.value = NoteResult.Success(null)
        assertEquals(testData, result)
    }

    @Test
    fun `deleteNote should return error`(){
        var result: Throwable? = null
        val testError = Throwable("error")
        viewModel.getViewState().observeForever{
            result = it?.error
        }
        viewModel.saveNote(testNote)
        viewModel.deleteNote()
        noteLiveData.value = NoteResult.Error(testError)
        assertEquals(result, testError)
    }

}