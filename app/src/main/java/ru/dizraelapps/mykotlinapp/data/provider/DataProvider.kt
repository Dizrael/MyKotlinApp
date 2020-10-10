package ru.dizraelapps.mykotlinapp.data.provider

import kotlinx.coroutines.channels.ReceiveChannel
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.entity.User
import ru.dizraelapps.mykotlinapp.data.model.NoteResult

interface DataProvider {
    fun subscribeToAllNotes() : ReceiveChannel<NoteResult>

    suspend fun getCurrentUser() : User?
    suspend fun saveNote(note: Note): Note
    suspend fun getNoteById(id: String): Note?
    suspend fun deleteNote(id: String)
}