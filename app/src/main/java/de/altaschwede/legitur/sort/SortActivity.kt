package de.altaschwede.legitur.sort

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import de.altaschwede.legitur.R
import de.altaschwede.legitur.books.BooksViewModel
import de.altaschwede.legitur.books.db.Book
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class SortActivity : AppCompatActivity() {

    private lateinit var model: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sort)

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.application)).get(BooksViewModel::class.java)

        val async = GlobalScope.async {
            model.getAllPostsDirect()
        }
        GlobalScope.launch {
            sort(async.await())
        }
    }

    private suspend fun sort(books: List<Book>?) {
        if (books != null) {
            for ((n, _) in books.withIndex()) {
                for (i in n - 1 downTo 0) {
                    val o1 = books[i]
                    val o2 = books[i + 1]
                    if (compareBooks(o1, o2) == 1) {
                        //A.swap(i, i+1)
                        saveBook(o1.copy(position = o1.position + 1))
                        saveBook(o2.copy(position = o2.position - 1))
                    } // Ende if
                }
            }
        }
    }

    private suspend fun compareBooks(o1: Book, o2: Book): Int {
        return suspendCoroutine { continuation ->
            val first = findViewById<Button>(R.id.textView)
            val second = findViewById<Button>(R.id.textView2)
            runOnUiThread {
                first.text = o1.title
                second.text = o2.title
            }
            first.setOnClickListener { continuation.resume(1) }
            second.setOnClickListener { continuation.resume(-1) }
        }
    }


    private fun saveBook(book: Book) {
        println(book)
        model.savePost(book)
    }

}
