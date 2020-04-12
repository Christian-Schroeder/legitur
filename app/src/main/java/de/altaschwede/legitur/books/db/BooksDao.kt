package de.altaschwede.legitur.books.db


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface BooksDao {

    @Query("SELECT * FROM books")
    fun getAll(): LiveData<List<Book>>

    @Insert(onConflict = REPLACE)
    fun insert(book: Book)

    @Query("DELETE FROM books")
    fun deleteAll()
}
