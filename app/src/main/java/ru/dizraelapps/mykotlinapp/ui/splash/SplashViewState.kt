package ru.dizraelapps.mykotlinapp.ui.splash

import ru.dizraelapps.mykotlinapp.ui.base.BaseViewState

class SplashViewState(authenticated: Boolean? = null, error: Throwable? = null): BaseViewState<Boolean?>(authenticated,error)