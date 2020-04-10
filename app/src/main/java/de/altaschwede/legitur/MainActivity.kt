package de.altaschwede.legitur

import alta_schwede.de.legitur.R
import de.altaschwede.legitur.db.BookDto
import de.altaschwede.legitur.db.BookDatabase
import de.altaschwede.legitur.db.DbWorkerThread
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private var mDb: BookDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mDb = BookDatabase.getInstance(this)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(arrayOf("trt", "gdsfs"))

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }

    fun saveBook(view: View) {
        val editText = findViewById<EditText>(R.id.editText)
        val message = editText.text.toString()
        val book = BookDto()
        book.name = message

        //bindDataWithUi(book)

        insertBookDb(bookDto = book)

    }

    private fun insertBookDb(bookDto: BookDto) {
        val task = Runnable { mDb?.bookDao()?.insert(bookDto) }
        mDbWorkerThread.postTask(task)
    }
}
