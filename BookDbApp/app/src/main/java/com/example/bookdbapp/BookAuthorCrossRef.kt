package com.example.bookdbapp

import androidx.room.Entity

@Entity(primaryKeys = ["bookId", "authorId"])
data class BookAuthorCrossRef(
    val bookId: Long,
    val authorId: Long
)
