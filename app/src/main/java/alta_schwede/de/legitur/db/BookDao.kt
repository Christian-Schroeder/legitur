package alta_schwede.de.legitur.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface WeatherDataDao {

    @Query("SELECT * FROM books")
    fun getAll(): List<Book>

    @Insert(onConflict = REPLACE)
    fun insert(book: Book)

    @Query("DELETE FROM books")
    fun deleteAll()
}
