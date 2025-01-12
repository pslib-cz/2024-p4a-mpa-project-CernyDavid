package com.example.bookdbapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.bookdbapp.*

class AddBookActivity : AppCompatActivity() {

    private val viewModel: BookViewModel by viewModels {
        BookViewModelFactory(BookDatabase.getDatabase(this).bookDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        val bookTitle = findViewById<EditText>(R.id.bookTitle)
        val bookIsbn = findViewById<EditText>(R.id.bookIsbn)
        val authorName = findViewById<EditText>(R.id.authorName)
        val saveBookButton = findViewById<Button>(R.id.saveBookButton)
        val backButton = findViewById<Button>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        saveBookButton.setOnClickListener {
            val title = bookTitle.text.toString().trim()
            val isbn = bookIsbn.text.toString().trim()
            val authors = authorName.text.toString().trim()

            if (title.isEmpty() || isbn.isEmpty() || authors.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val authorNames = authors.split(",").map { it.trim() }.filter { it.isNotEmpty() }

            lifecycleScope.launch {
                val bookId = viewModel.addBook(title, isbn)

                for (authorName in authorNames) {
                    val authorId = viewModel.addAuthor(authorName)
                    viewModel.addBookAuthorCrossRef(bookId, authorId)
                }

                Toast.makeText(this@AddBookActivity, "Book Added", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}