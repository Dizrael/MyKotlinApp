package ru.dizraelapps.mykotlinapp.ui.main

import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null): BaseViewState<List<Note>?>(notes, error)  {
}