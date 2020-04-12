package de.altaschwede.legitur.books

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.altaschwede.legitur.books.db.Book
import de.altaschwede.legitur.books.db.BooksDao
import de.altaschwede.legitur.books.db.BooksDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class BooksViewModel(application: Application) : AndroidViewModel(application) {
    private val booksDao: BooksDao? = BooksDatabase.getInstance(application)?.booksDao()
    private val executorService: ExecutorService? = Executors.newSingleThreadExecutor()

    fun getAllPosts(): LiveData<List<Book>>? {
        return booksDao?.getAll()
    }

    fun savePost(post: Book) {
        executorService!!.execute { booksDao?.insert(post) }
    }

}