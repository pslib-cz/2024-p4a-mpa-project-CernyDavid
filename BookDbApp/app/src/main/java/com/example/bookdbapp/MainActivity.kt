package com.example.bookdbapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val viewModel: BookViewModel by viewModels {
        BookViewModelFactory(BookDatabase.getDatabase(this).bookDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBar = findViewById<EditText>(R.id.searchBar)
        val bookList = findViewById<RecyclerView>(R.id.bookList)
        val addBookButton = findViewById<Button>(R.id.addBookButton)

        val adapter = BookListAdapter()
        bookList.layoutManager = LinearLayoutManager(this)
        bookList.adapter = adapter

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        lifecycleScope.launch {
            viewModel.books.collect { books ->
                adapter.submitList(books)
            }
        }

        addBookButton.setOnClickListener {
            startActivity(Intent(this, AddBookActivity::class.java))
        }
    }
}