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
            sort(async.await()?.toMutableList())
        }
    }

    private suspend fun sort(books: MutableList<Book>?) {
        if (books != null) {
            //randomize books
            val reversed = books.shuffled().sorted().reversed()
            val first = reversed[0]
            val second = reversed[1]
            val third = reversed[2]
            val compareBooks = compareBooks(first, second, third)

            val mutableListOf = mutableListOf(
                    first.copy(position = first.position + (if (compareBooks == 0) 2 else -1), round = first.round + 1).save(),
                    second.copy(position = second.position + (if (compareBooks == 1) 2 else -1), round = second.round + 1).save(),
                    third.copy(position = third.position + (if (compareBooks == 2) 2 else -1), round = third.round + 1).save())
            mutableListOf.addAll(reversed.takeLast(reversed.size - 3))
            sort(mutableListOf)

            //sort by durchgang and then position
            //show the last three from the list
            //let user select one (selected position plus 2, the others minus 1 each, start again)
            //can be done forever -> show progress bar?
        }
    }

    private suspend fun compareBooks(o1: Book, o2: Book, o3: Book): Int {
        return suspendCoroutine { continuation ->
            val first = findViewById<Button>(R.id.textView)
            val second = findViewById<Button>(R.id.textView2)
            val third = findViewById<Button>(R.id.textView3)
            runOnUiThread {
                first.text = o1.title
                second.text = o2.title
                third.text = o3.title
            }
            first.setOnClickListener { continuation.resume(0) }
            second.setOnClickListener { continuation.resume(1) }
            third.setOnClickListener { continuation.resume(2) }
        }
    }


    private fun Book.save(): Book {
        println(this)
        model.savePost(this)
        return this
    }

}
