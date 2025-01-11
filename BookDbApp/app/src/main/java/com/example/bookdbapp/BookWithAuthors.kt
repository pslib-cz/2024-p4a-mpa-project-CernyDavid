package com.example.bookdbapp

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BookWithAuthors(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "bookId",
        entityColumn = "authorId",
        associateBy = Junction(BookAuthorCrossRef::class)
    )
    val authors: List<Author>
)