package com.example.bookdbapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class BookListAdapter(
    private val onBookClick: (BookWithAuthors) -> Unit
) : ListAdapter<BookWithAuthors, BookListAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return BookViewHolder(view, onBookClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BookViewHolder(
        view: View,
        private val onBookClick: (BookWithAuthors) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(android.R.id.text1)
        private val details: TextView = view.findViewById(android.R.id.text2)

        fun bind(bookWithAuthors: BookWithAuthors) {
            title.text = bookWithAuthors.book.title
            details.text = "ISBN: ${bookWithAuthors.book.isbn}, Authors: ${
                bookWithAuthors.authors.joinToString(" & ") { it.name }
            }"

            itemView.setOnClickListener {
                onBookClick(bookWithAuthors)
            }
        }
    }

    class BookDiffCallback : DiffUtil.ItemCallback<BookWithAuthors>() {
        override fun areItemsTheSame(oldItem: BookWithAuthors, newItem: BookWithAuthors): Boolean {
            return oldItem.book.id == newItem.book.id
        }

        override fun areContentsTheSame(oldItem: BookWithAuthors, newItem: BookWithAuthors): Boolean {
            return oldItem == newItem
        }
    }
}