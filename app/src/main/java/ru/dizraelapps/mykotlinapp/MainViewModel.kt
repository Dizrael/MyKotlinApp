package ru.dizraelapps.mykotlinapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val model: Model = Model()
    private val viewStateLiveData = MutableLiveData<String>()

    init {
        viewStateLiveData.value = "Hello"
    }

    fun getViewState(): LiveData<String> = viewStateLiveData

    fun updateViewState(){
        viewStateLiveData.value = "Hello " + model.getCounter().value + "!"
    }
}