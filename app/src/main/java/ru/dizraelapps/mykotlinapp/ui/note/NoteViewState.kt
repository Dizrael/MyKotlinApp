package ru.dizraelapps.mykotlinapp.ui.note

import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null): BaseViewState<Note?>(note, error) {
}