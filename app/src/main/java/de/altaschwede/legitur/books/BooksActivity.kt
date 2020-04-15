package de.altaschwede.legitur.books

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.altaschwede.legitur.R
import de.altaschwede.legitur.books.db.Book
import de.altaschwede.legitur.sort.SortActivity


class BooksActivity : AppCompatActivity() {

    private lateinit var books: RecyclerView
    private lateinit var booksAdapter: BooksAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var booksViewModel: BooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.books)

        viewManager = LinearLayoutManager(this)
        booksAdapter = BooksAdapter()


        books = findViewById<RecyclerView>(R.id.books).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = booksAdapter
        }

        booksViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.application)).get(BooksViewModel::class.java)
        booksViewModel.getAllPosts()?.observe(this, Observer { t ->
            booksAdapter.setData(t.map { it -> it.title })
        })

        // Attaching the layout to the toolbar object
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.open_sort -> {
                openSort()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openSort(){
        startActivity(Intent(this, SortActivity::class.java))
    }

    fun saveBook(view: View) {
        val title = findViewById<EditText>(R.id.title)
        val editTitle = title.text.toString()
        if (editTitle != "") {
            val book = Book(title = editTitle)
            booksViewModel.savePost(book)
            title.text.clear()
        }
    }


}
