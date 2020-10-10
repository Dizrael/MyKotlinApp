package ru.dizraelapps.mykotlinapp.ui.note

import kotlinx.coroutines.launch
import ru.dizraelapps.mykotlinapp.data.NotesRepository
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewModel

class NoteViewModel(val notesRepository: NotesRepository) : BaseViewModel<NoteData>() {
    private var currentNote: Note? = null

    fun saveNote(note: Note) {
        currentNote = note
    }

    fun loadNote(noteId: String) = launch {
        try {
            notesRepository.getNoteById(noteId)?.let {
                setData(NoteData(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    override fun onCleared() {
        launch {
            currentNote?.let {
                notesRepository.saveNote(it)
            }
        }
    }

    fun deleteNote() = launch {
        try {
            currentNote?.let {
                notesRepository.deleteNote(it.id)
                setData(NoteData(isDeleted = true))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

}