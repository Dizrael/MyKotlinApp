package ru.dizraelapps.mykotlinapp.ui.note

import androidx.lifecycle.ViewModel
import ru.dizraelapps.mykotlinapp.data.NotesRepository
import ru.dizraelapps.mykotlinapp.data.entity.Note

class NoteViewModel: ViewModel() {

    private var currentNote: Note? = null

    fun saveNote(note:Note){
        currentNote = note
    }

    override fun onCleared() {
        currentNote?.let {
            NotesRepository.saveNote(it)
        }
    }

}