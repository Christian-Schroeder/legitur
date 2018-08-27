package alta_schwede.de.legitur

import alta_schwede.de.legitur.db.Book
import alta_schwede.de.legitur.db.BookDatabase
import alta_schwede.de.legitur.db.DbWorkerThread
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private var mDb: BookDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mDb = BookDatabase.getInstance(this)
    }

    fun saveBook(view: View){
        val editText = findViewById<EditText>(R.id.editText)
        val message = editText.text.toString()
        val book = Book()
        book.name = message

        //bindDataWithUi(book)

        insertBookDb(book = book)

    }

    private fun insertBookDb(book: Book) {
        val task = Runnable { mDb?.bookDao()?.insert(book) }
        mDbWorkerThread.postTask(task)
    }
}
