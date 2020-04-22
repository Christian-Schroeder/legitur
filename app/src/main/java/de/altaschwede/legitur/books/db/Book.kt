package de.altaschwede.legitur.books.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(@PrimaryKey(autoGenerate = true) val id: Long? = null,
                @ColumnInfo(name = "name") val title: String,
                @ColumnInfo(name = "position") val position: Int = 0,
                @ColumnInfo(name = "round") val round: Int = 0
) : Comparable<Book> {
    override fun compareTo(other: Book): Int {
        if (this.round > other.round) return -1
        if (this.round < other.round) return 1
        return 0
    }
}