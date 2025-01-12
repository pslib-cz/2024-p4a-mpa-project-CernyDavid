package com.example.bookdbapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class BookViewModel(private val bookDao: BookDao) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    val books = searchQuery.flatMapLatest { query ->
        if (query.isEmpty()) {
            bookDao.getBooksWithAuthors()
        } else {
            bookDao.searchBooks(query)
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    suspend fun addBook(title: String, isbn: String): Long {
        return bookDao.insertBook(Book(title = title, isbn = isbn))
    }

    suspend fun addAuthor(name: String): Long {
        return bookDao.insertAuthor(Author(name = name))
    }

    suspend fun addBookAuthorCrossRef(bookId: Long, authorId: Long) {
        bookDao.insertBookAuthorCrossRef(BookAuthorCrossRef(bookId, authorId))
    }
}

class BookViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookViewModel(bookDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}