package ru.dizraelapps.mykotlinapp.ui.splash

import kotlinx.coroutines.launch
import ru.dizraelapps.mykotlinapp.data.NotesRepository
import ru.dizraelapps.mykotlinapp.data.errors.NoAuthException
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewModel

class SplashViewModel(private val notesRepository: NotesRepository) :BaseViewModel<Boolean?>(){
    fun requestUser() = launch {
        notesRepository.getCurrentUser()?.let {
            setData(true)
        } ?: setError(NoAuthException())
    }
}