package ru.dizraelapps.mykotlinapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.dizraelapps.mykotlinapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        recycler_view_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRecyclerViewAdapter()
        recycler_view_notes.adapter = adapter

        viewModel.getViewState().observe(this, Observer {value ->
            value?.let { adapter.notes = it.notes }
        })

    }
}