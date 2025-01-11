package com.example.bookdbapp

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AuthorWithBooks(
    @Embedded val author: Author,
    @Relation(
        parentColumn = "authorId",
        entityColumn = "bookId",
        associateBy = Junction(BookAuthorCrossRef::class)
    )
    val books: List<Book>
)