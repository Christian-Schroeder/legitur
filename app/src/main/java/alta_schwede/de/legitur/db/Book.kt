package alta_schwede.de.legitur.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "books")
data class Book(@PrimaryKey(autoGenerate = true) var id: Long?,
                @ColumnInfo(name = "name") var name: String
) {
    constructor() : this(null, "")
}