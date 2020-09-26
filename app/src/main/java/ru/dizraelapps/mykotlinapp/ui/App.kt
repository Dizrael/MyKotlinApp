package ru.dizraelapps.mykotlinapp.ui

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.dizraelapps.mykotlinapp.di.appModule
import ru.dizraelapps.mykotlinapp.di.mainModule
import ru.dizraelapps.mykotlinapp.di.noteModule
import ru.dizraelapps.mykotlinapp.di.splashModule

class App : Application() {

    companion object{
        lateinit var instance : App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@App)
            modules(appModule, splashModule, mainModule, noteModule)
        }
    }
}