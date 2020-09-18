package ru.dizraelapps.mykotlinapp.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    var id: String = "",
    var title: String = "",
    var text: String = "",
    var color: Color = Color.WHITE,
    var lastChanged: Date = Date()
 ) : Parcelable {

    enum class Color {
        WHITE,
        YELLOW,
        BLUE,
        ORANGE,
        RED,
        GREEN,
        MAGENTA
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (javaClass != other?.javaClass) return false

        other as Note
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + lastChanged.hashCode()
        return result
    }
}