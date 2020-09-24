package ru.dizraelapps.mykotlinapp.ui.note

import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null): BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}