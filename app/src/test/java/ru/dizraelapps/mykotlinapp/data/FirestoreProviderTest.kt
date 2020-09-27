package ru.dizraelapps.mykotlinapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.errors.NoAuthException
import ru.dizraelapps.mykotlinapp.data.model.NoteResult
import ru.dizraelapps.mykotlinapp.data.provider.FirestoreProvider
import java.lang.Exception
import java.lang.NullPointerException

class FirestoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockUser = mockk<FirebaseUser>()
    private val mockResultCollection = mockk<CollectionReference>()

    private val provider: FirestoreProvider = FirestoreProvider(mockAuth, mockDb)

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    @Before
    fun setup() {
        clearAllMocks()
        every { mockUser.uid } returns ""
        every { mockAuth.currentUser } returns mockUser
        every {
            mockDb.collection(any()).document(any()).collection(any())
        } returns mockResultCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]
    }

    @Test
    fun `should throw NoAuthException if not auth`() {
        var result: Any? = null
        every { mockAuth.currentUser } returns null

        provider.subscribeToAllNotes().observeForever {
            result = (it as NoteResult.Error).error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `saveNote calls set`() {
        val mockDocumentReferences = mockk<DocumentReference>()
        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReferences
        provider.saveNote(testNotes[0])
        verify(exactly = 1) { mockDocumentReferences.set(testNotes[0]) }
    }

    @Test
    fun `saveNote return success note`() {
        var result: NoteResult? = null
        val mockDocumentReference = mockk<DocumentReference>()

        val slot = slot<OnSuccessListener<Void>>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.set(testNotes[0]).addOnSuccessListener(capture(slot)) } returns mockk()

        provider.saveNote(testNotes[0]).observeForever {
            result = it
        }

        slot.captured.onSuccess(null)

        assertEquals(testNotes[0], (result as NoteResult.Success<*>).data)
    }

    @Test
    fun `subscribeToAllNotes return notes`() {
        var result: List<Note>? = null
        val mockDocumentSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockDocumentSnapshot.documents } returns listOf(
            mockDocument1,
            mockDocument2,
            mockDocument3
        )
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()

        provider.subscribeToAllNotes().observeForever {
            result = (it as NoteResult.Success<List<Note>>).data
        }

        slot.captured.onEvent(mockDocumentSnapshot, null)
        assertEquals(result, testNotes)
    }

    @Test
    fun `subscribeToAllNotes return error`() {
        var result: Throwable? = null
        val testError = mockk<FirebaseFirestoreException>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()

        provider.subscribeToAllNotes().observeForever {
            result = (it as NoteResult.Error).error
        }

        slot.captured.onEvent(null, testError)
        assertEquals(testError, result)
    }

    @Test
    fun `deleteNote calls delete`() {
        val mockDocumentReferences = mockk<DocumentReference>()
        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReferences
        provider.deleteNote(testNotes[0].id)
        verify(exactly = 1) { mockDocumentReferences.delete() }
    }

    @Test
    fun `deleteNote return success null`() {
        var result: NoteResult? = null
        val mockDocumentReference = mockk<DocumentReference>()

        val slot = slot<OnSuccessListener<Void>>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.delete().addOnSuccessListener(capture(slot)) } returns mockk()

        provider.deleteNote(testNotes[0].id).observeForever {
            result = it
        }

        slot.captured.onSuccess(null)
        println(testNotes[0])
        assertEquals(NoteResult.Success<Note?>(null), result)
    }

    @Test
    fun `getNoteById return note`(){
        var result: NoteResult? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<DocumentSnapshot>>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.get().addOnSuccessListener(capture(slot)) } returns mockk()

        provider.getNoteById(testNotes[0].id).observeForever{
            result = it
        }

        slot.captured.onSuccess(mockDocument1)
        assertEquals((result as NoteResult.Success<Note>).data, testNotes[0])

    }

//    TODO("Не понял, как прокинуть OnFailure(e). Тест падает с lateinit captured на 189 строке")
    @Test
    fun `getNoteById return error`(){
        var result: NoteResult? = null
        val testError = Exception("error")
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnFailureListener>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        every { mockDocumentReference.get().addOnFailureListener(capture(slot)) } returns mockk()

        provider.getNoteById(testNotes[0].id).observeForever{
            result = it
        }

        slot.captured.onFailure(testError)

        assertEquals((result as NoteResult.Error).error, testError)
    }

}