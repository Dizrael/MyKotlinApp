package ru.dizraelapps.mykotlinapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.dizraelapps.mykotlinapp.data.entity.Note
import java.util.*

object NotesRepository{

    private val noteLiveData = MutableLiveData<List<Note>>()

    private val notes = mutableListOf(
        Note(
            UUID.randomUUID().toString(),
            "1я заметка",
            "Текст 1й заметки",
            Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),
            "2я заметка",
            "Текст 2й заметки",
            Note.Color.YELLOW
        ),
        Note(
            UUID.randomUUID().toString(),
            "3я заметка",
            "Текст 3й заметки",
            Note.Color.BLUE
        ),
        Note(
            UUID.randomUUID().toString(),
            "4я заметка",
            "Текст 4й заметки",
            Note.Color.ORANGE
        ),
        Note(
            UUID.randomUUID().toString(),
            "5я заметка",
            "Текст 5й заметки",
            Note.Color.GREEN
        ),
        Note(
            UUID.randomUUID().toString(),
            "6я заметка",
            "Текст 6й заметки",
            Note.Color.RED
        )
    )

    init {
        noteLiveData.value = notes
    }

    fun getNotes(): LiveData<List<Note>>{
        return noteLiveData
    }

    fun saveNote(note:Note){
        addOrReplace(note)
        noteLiveData.value = notes
    }

    private fun addOrReplace(note: Note){
        for (i in 0 until notes.size){
            if(notes[i] == note){
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}