package ru.dizraelapps.mykotlinapp.data.provider

import androidx.lifecycle.LiveData
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.entity.User
import ru.dizraelapps.mykotlinapp.data.model.NoteResult

interface DataProvider {
    fun getCurrentUser() : LiveData<User?>
    fun subscribeToAllNotes() : LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>

}