package ru.dizraelapps.mykotlinapp.ui.splash

import ru.dizraelapps.mykotlinapp.data.NotesRepository
import ru.dizraelapps.mykotlinapp.data.errors.NoAuthException
import ru.dizraelapps.mykotlinapp.ui.base.BaseViewModel

class SplashViewModel :BaseViewModel<Boolean?, SplashViewState>(){
    fun requestUser(){

//        TODO("обработать отписку от обзервера")
        NotesRepository.getCurrentUser().observeForever{
            viewStateLiveData.value = if (it != null) {
                SplashViewState(authenticated = true)
            }else{
                SplashViewState(error = NoAuthException())
            }
        }
    }
}