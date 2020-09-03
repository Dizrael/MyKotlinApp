package ru.dizraelapps.mykotlinapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Model {

    private val modelLiveData = MutableLiveData<String>()
    private var counter: Int = 0

    private fun raiseCounter(): Int{
       return counter++
    }

    fun getCounter(): LiveData<String>{
        modelLiveData.value = raiseCounter().toString()
        Log.d("MODEL", modelLiveData.value.toString())
        return modelLiveData
    }

}