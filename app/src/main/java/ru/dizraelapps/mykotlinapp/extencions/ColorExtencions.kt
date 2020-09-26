package ru.dizraelapps.mykotlinapp.extencions

import android.content.Context
import androidx.core.content.ContextCompat
import ru.dizraelapps.mykotlinapp.R
import ru.dizraelapps.mykotlinapp.data.entity.Note

inline fun Note.Color.getColorInt(context: Context) = ContextCompat.getColor(context, getColorRes(context))

inline fun Note.Color.getColorRes(context: Context) = when (this) {
    Note.Color.WHITE -> R.color.white
    Note.Color.ORANGE -> R.color.orange
    Note.Color.YELLOW -> R.color.yellow
    Note.Color.RED -> R.color.red
    Note.Color.BLUE -> R.color.blue
    Note.Color.GREEN -> R.color.green
    Note.Color.MAGENTA -> R.color.magenta
}