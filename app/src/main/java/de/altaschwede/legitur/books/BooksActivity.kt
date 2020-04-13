package de.altaschwede.legitur.books

import alta_schwede.de.legitur.R
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.altaschwede.legitur.books.db.Book
import de.altaschwede.legitur.books.db.DbWorkerThread


class BooksActivity : AppCompatActivity() {

    private lateinit var mDbWorkerThread: DbWorkerThread
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: BooksAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var model: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_list)

        viewManager = LinearLayoutManager(this)
        viewAdapter = BooksAdapter(listOf("trt", "gdsfs"))


        recyclerView = findViewById<RecyclerView>(R.id.books).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.application)).get(BooksViewModel::class.java)
        model.getAllPosts()?.observe(this, Observer {
            t -> viewAdapter.setData(t.map { it -> it.name })
        })
    }


    fun saveBook(view: View) {
        val editText = findViewById<EditText>(R.id.editText)
        val message = editText.text.toString()
        val book = Book(null, message)

        model.savePost(book)
    }

}
