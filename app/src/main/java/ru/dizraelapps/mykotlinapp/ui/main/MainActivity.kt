package ru.dizraelapps.mykotlinapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.dizraelapps.mykotlinapp.R
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.ui.base.BaseActivity
import ru.dizraelapps.mykotlinapp.ui.note.NoteActivity

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val layoutRes: Int = R.layout.activity_main

    private lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recycler_view_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRecyclerViewAdapter {
            NoteActivity.start(this, it.id)
        }

        recycler_view_notes.adapter = adapter

        fab.setOnClickListener{
            NoteActivity.start(this)
        }

    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}