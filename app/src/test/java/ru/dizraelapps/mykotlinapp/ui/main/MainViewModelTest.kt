package ru.dizraelapps.mykotlinapp.ui.main


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.dizraelapps.mykotlinapp.data.NotesRepository
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.errors.NoAuthException
import ru.dizraelapps.mykotlinapp.data.model.NoteResult
import ru.dizraelapps.mykotlinapp.data.provider.FirestoreProvider
import ru.dizraelapps.mykotlinapp.ui.main.MainViewModel
import java.lang.Exception
import java.lang.NullPointerException

class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<NotesRepository>()
    private val notesLiveData = MutableLiveData<NoteResult>()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup(){
        clearAllMocks()
        every { mockRepository.getNotes() } returns notesLiveData
        viewModel = MainViewModel(mockRepository)
    }

    @Test
    fun `should call getNotes ones`(){
        verify(exactly = 1) { mockRepository.getNotes() }
    }

    @Test
    fun `should return notes`(){
        var result: List<Note>? = null
        val testData = listOf(Note("1"), Note("2"))

        viewModel.getViewState().observeForever{
            result = it.data
        }
        notesLiveData.value = NoteResult.Success(testData)
        assertEquals(result, testData)
    }

    @Test
    fun `should return error`(){
        var result: Throwable? = null
        val testError = Throwable("error")

        viewModel.getViewState().observeForever{
            result = it.error
        }
        notesLiveData.value = NoteResult.Error(testError)
        assertEquals(result, testError)
    }

    @Test
    fun `should remove observer`(){
        viewModel.onCleared()
        assertFalse(notesLiveData.hasObservers())
    }

}