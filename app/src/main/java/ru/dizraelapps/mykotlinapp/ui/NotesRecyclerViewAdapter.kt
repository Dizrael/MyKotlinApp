package ru.dizraelapps.mykotlinapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import ru.dizraelapps.mykotlinapp.R
import ru.dizraelapps.mykotlinapp.data.entity.Note

class NotesRecyclerViewAdapter: RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false))

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind (note: Note) = with(itemView){
            rv_title.text = note.title
            rv_text.text = note.text
            setBackgroundColor(note.color)
        }

    }
}