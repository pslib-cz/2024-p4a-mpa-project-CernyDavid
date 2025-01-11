package com.example.bookdbapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true) val bookId: Long = 0,
    val title: String
)