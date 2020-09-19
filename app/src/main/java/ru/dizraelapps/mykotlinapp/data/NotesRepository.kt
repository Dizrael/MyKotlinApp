package ru.dizraelapps.mykotlinapp.data

import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.provider.DataProvider
import ru.dizraelapps.mykotlinapp.data.provider.FirestoreProvider

object NotesRepository{

    private val dataProvider: DataProvider = FirestoreProvider()

    fun getNotes() = dataProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = dataProvider.saveNote(note)
    fun getNoteById(id: String) = dataProvider.getNoteById(id)

}