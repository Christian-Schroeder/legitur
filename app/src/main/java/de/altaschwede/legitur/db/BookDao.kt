package de.altaschwede.legitur.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface WeatherDataDao {

    @Query("SELECT * FROM books")
    fun getAll(): List<BookDto>

    @Insert(onConflict = REPLACE)
    fun insert(bookDto: BookDto)

    @Query("DELETE FROM books")
    fun deleteAll()
}
