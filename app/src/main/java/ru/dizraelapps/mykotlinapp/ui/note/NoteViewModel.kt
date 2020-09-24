package ru.dizraelapps.mykotlinapp.ui.note

import androidx.lifecycle.ViewModel
import ru.dizraelapps.mykotlinapp.data.NotesRepository
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.data.model.NoteResult
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewModel
import ru.dizraelapps.mykotlinapp.ui.main.MainViewState

class NoteViewModel(val notesRepository: NotesRepository): BaseViewModel<NoteViewState.Data, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var currentNote: Note? = null

    fun saveNote(note:Note){
        currentNote = note
    }

    fun loadNote(noteId: String){
        notesRepository.getNoteById(noteId).observeForever {result ->
            result ?: return@observeForever
            when(result){is NoteResult.Success<*> -> {
                currentNote = result.data as? Note
                viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = currentNote))
            }
                is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
            }
        }
    }

    override fun onCleared() {
        currentNote?.let {
            notesRepository.saveNote(it)
        }
    }

    fun deleteNote(){
        currentNote?.let {
            notesRepository.deleteNote(it.id).observeForever {result ->
                result ?: return@observeForever
                currentNote = null
                when (result) {
                    is NoteResult.Success<*> -> viewStateLiveData.value = NoteViewState(NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> viewStateLiveData.value = NoteViewState(error = result.error)
                }
            }
        }
    }

}