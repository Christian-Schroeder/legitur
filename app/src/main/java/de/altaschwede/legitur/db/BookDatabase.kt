package de.altaschwede.legitur.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(BookDto::class), version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): WeatherDataDao

    companion object {
        private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase? {
            if (INSTANCE == null) {
                synchronized(BookDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase::class.java, "books.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}