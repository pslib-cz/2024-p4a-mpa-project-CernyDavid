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

        saveBookButton.setOnClickListener {
            val title = bookTitle.text.toString().trim()
            val isbn = bookIsbn.text.toString().trim()
            val author = authorName.text.toString().trim()

            if (title.isEmpty() || isbn.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val bookId = viewModel.addBook(title, isbn)
                val authorId = viewModel.addAuthor(author)
                viewModel.addBookAuthorCrossRef(bookId, authorId)
                Toast.makeText(this@AddBookActivity, "Book Added", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}