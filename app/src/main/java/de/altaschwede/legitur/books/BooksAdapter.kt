package de.altaschwede.legitur.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.altaschwede.legitur.R

class BooksAdapter(private var bookTitles: List<String> = listOf()) :
        RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    //class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    // stores and recycles views as they are scrolled off screen
    inner class BooksViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var bookTitlesView: TextView = itemView.findViewById(R.id.bookName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            //if (mClickListener != null) mClickListener.onItemClick(view, adapterPosition)
        }
    }

    fun setData(newData: List<String>) {
        this.bookTitles = newData
        notifyDataSetChanged()
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BooksViewHolder {


        // create a new view
        val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.book_row, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return BooksViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: BooksViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.bookTitlesView.text = bookTitles[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = bookTitles.size
}
