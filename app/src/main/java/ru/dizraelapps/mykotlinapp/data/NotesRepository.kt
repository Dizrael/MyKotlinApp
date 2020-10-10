package ru.dizraelapps.mykotlinapp.data

import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.provider.DataProvider

class NotesRepository(private val dataProvider: DataProvider){
    fun getNotes() = dataProvider.subscribeToAllNotes()

    suspend fun getCurrentUser() = dataProvider.getCurrentUser()
    suspend fun saveNote(note: Note) = dataProvider.saveNote(note)
    suspend fun getNoteById(id: String) = dataProvider.getNoteById(id)
    suspend fun deleteNote(id: String) = dataProvider.deleteNote(id)
}