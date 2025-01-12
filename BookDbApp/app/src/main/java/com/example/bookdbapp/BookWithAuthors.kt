package com.example.bookdbapp

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BookWithAuthors(
    @Embedded val book: Book,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(BookAuthorCrossRef::class, parentColumn = "bookId", entityColumn = "authorId")
    )
    val authors: List<Author>
)
