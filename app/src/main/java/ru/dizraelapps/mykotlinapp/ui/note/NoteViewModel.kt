package ru.dizraelapps.mykotlinapp.ui.note

import androidx.lifecycle.ViewModel
import ru.dizraelapps.mykotlinapp.data.NotesRepository
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.model.NoteResult
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewModel
import ru.dizraelapps.mykotlinapp.ui.main.MainViewState

class NoteViewModel: BaseViewModel<Note?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var currentNote: Note? = null

    fun saveNote(note:Note){
        currentNote = note
    }

    fun loadNote(noteId: String){
        NotesRepository.getNoteById(noteId).observeForever {result ->
            result ?: return@observeForever
            when(result){
                is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(note = result.data as? Note)
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }

    override fun onCleared() {
        currentNote?.let {
            NotesRepository.saveNote(it)
        }
    }

}