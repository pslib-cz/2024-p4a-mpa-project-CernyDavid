package com.example.bookdbapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.bookdbapp.*

class BookDetailActivity : AppCompatActivity() {

    private lateinit var bookDao: BookDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        val bookId = intent.getLongExtra("BOOK_ID", -1L)
        if (bookId == -1L) {
            finish()
            return
        }

        bookDao = BookDatabase.getDatabase(this).bookDao()

        val titleView: TextView = findViewById(R.id.text_title)
        val detailsView: TextView = findViewById(R.id.text_details)
        val deleteButton: Button = findViewById(R.id.deleteButton)
        val backButton: Button = findViewById(R.id.backButton)

        lifecycleScope.launch {
            val bookWithAuthors: BookWithAuthors? = withContext(Dispatchers.IO) {
                bookDao.getSingleBookWithAuthors(bookId)
            }

            backButton.setOnClickListener {
                finish()
            }

            bookWithAuthors?.let {
                titleView.text = it.book.title
                detailsView.text = "ISBN: ${it.book.isbn}\nAuthors:\n${
                    it.authors.joinToString("\n") { author -> author.name }
                }"

                deleteButton.setOnClickListener {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            bookDao.deleteBookWithAuthors(bookWithAuthors.book.id)
                        }
                        finish()
                    }
                }
            } ?: finish()
        }
    }
}