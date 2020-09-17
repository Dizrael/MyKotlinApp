package ru.dizraelapps.mykotlinapp.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_note.*
import ru.dizraelapps.mykotlinapp.R
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity: BaseActivity<Note?, NoteViewState>() {

    companion object{
        private const val NOTE_KEY = "note"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).apply {
            putExtra(NOTE_KEY, noteId)
            context.startActivity(this)
        }
    }

    override val layoutRes: Int = R.layout.activity_note
    private var note : Note? = null
    override val viewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }
    private val textWatcher = object : TextWatcher{
        override fun afterTextChanged(p0: Editable?) {
            saveNote()
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(NOTE_KEY)

        noteId?.let {
            viewModel.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note)
            initView()
        }
    }

    override fun renderData(data: Note?) {
        this.note = data
        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.lastChanged)
        } ?: getString(R.string.new_note)

        initView()
    }

    private fun initView(){
        et_title.removeTextChangedListener(textWatcher)
        et_body.removeTextChangedListener(textWatcher)

        note?.let {
            et_title.setTextKeepState(it.title)
            et_body.setTextKeepState(it.text)
            val color = when(it.color){
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.BLUE -> R.color.blue
                Note.Color.ORANGE -> R.color.orange
                Note.Color.RED -> R.color.red
                Note.Color.GREEN -> R.color.green
                Note.Color.MAGENTA -> R.color.magenta
            }
            toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, color, null))
        }

        et_title.addTextChangedListener(textWatcher)
        et_body.addTextChangedListener(textWatcher)
    }

    private fun saveNote(){
        et_title.text?.let{
            if (it.length < 0) return
        } ?: return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())

        note?.let { viewModel.saveNote(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)

    }

}