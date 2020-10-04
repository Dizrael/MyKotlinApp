package ru.dizraelapps.mykotlinapp.ui.note

import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewState

class NoteData(val isDeleted: Boolean = false, val note: Note? = null)