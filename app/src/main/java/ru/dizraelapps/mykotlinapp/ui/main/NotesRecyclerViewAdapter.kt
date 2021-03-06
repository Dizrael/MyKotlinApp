package ru.dizraelapps.mykotlinapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import ru.dizraelapps.mykotlinapp.R
import ru.dizraelapps.mykotlinapp.data.entity.Note
import ru.dizraelapps.mykotlinapp.extencions.getColorInt

class NotesRecyclerViewAdapter(val onItemClick: ((Note) -> Unit)?= null): RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind (note: Note) = with(itemView){
            rv_title.text = note.title
            rv_text.text = note.text

            val color = when(note.color){
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.BLUE -> R.color.blue
                Note.Color.ORANGE -> R.color.orange
                Note.Color.RED -> R.color.red
                Note.Color.GREEN -> R.color.green
                Note.Color.MAGENTA -> R.color.magenta
            }

            setBackgroundColor(note.color.getColorInt(context))

            itemView.setOnClickListener {
                onItemClick?.invoke(note)
            }
        }
    }
}