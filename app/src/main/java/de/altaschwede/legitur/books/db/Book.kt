package de.altaschwede.legitur.books.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(@PrimaryKey(autoGenerate = true) var id: Long? = null,
                @ColumnInfo(name = "name") var title: String,
                @ColumnInfo(name = "position") var position: Int = 0
)