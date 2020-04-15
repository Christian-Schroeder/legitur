package de.altaschwede.legitur.books.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun booksDao(): BooksDao

    companion object {
        private var INSTANCE: BooksDatabase? = null

        fun getInstance(context: Context): BooksDatabase? {
            if (INSTANCE == null) {
                synchronized(BooksDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            BooksDatabase::class.java, "books.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}