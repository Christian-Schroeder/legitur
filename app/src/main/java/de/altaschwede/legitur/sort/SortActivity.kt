package de.altaschwede.legitur.sort

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import de.altaschwede.legitur.R
import de.altaschwede.legitur.books.BooksViewModel
import de.altaschwede.legitur.books.db.Book
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SortActivity : AppCompatActivity() {

    private lateinit var model: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sort)

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.application)).get(BooksViewModel::class.java)

        GlobalScope.launch { startSorting() }
    }

    private suspend fun startSorting() {
        val allPosts = model.getAllPostsDirect()
        for (b: Book in allPosts!!) {
            println(b.title)
        }
    }


    fun saveBook(@Suppress("UNUSED_PARAMETER") view: View) {
        val editText = findViewById<EditText>(R.id.title)
        val message = editText.text.toString()
        val book = Book(null, message)

        model.savePost(book)
    }

}
