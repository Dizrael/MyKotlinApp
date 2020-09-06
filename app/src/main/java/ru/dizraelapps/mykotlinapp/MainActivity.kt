package ru.dizraelapps.mykotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val counterTextView: TextView = findViewById(R.id.main_textview_counter)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getViewState().observe(this, Observer {
            val button: Button = findViewById(R.id.button_counter)
            button.setOnClickListener {
                viewModel.updateViewState()
                val text: String = viewModel.getViewState().value.toString()
                counterTextView.setText(text)
                Log.d("BUTTON CLICK", text)
            }
        })

    }
}