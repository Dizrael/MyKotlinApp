package ru.dizraelapps.mykotlinapp.data

import ru.dizraelapps.mykotlinapp.data.entity.Note

object NotesRepository{
    private val notes: List<Note> = listOf(
        Note(
            "1я заметка",
            "Текст 1й заметки",
            0xffEE4444.toInt()
        ),
        Note(
            "2я заметка",
            "Текст 2й заметки",
            0xffDE6BA8.toInt()
        ),
        Note(
            "3я заметка",
            "Текст 3й заметки",
            0xffA0F0F6.toInt()
        ),
        Note(
            "4я заметка",
            "Текст 4й заметки",
            0xff35F65C.toInt()
        ),
        Note(
            "5я заметка",
            "Текст 5й заметки",
            0xffF6F635.toInt()
        ),
        Note(
            "6я заметка",
            "Текст 6й заметки",
            0xff5979F7.toInt()
        )
    )

    fun getNotes(): List<Note>{
        return notes
    }
}