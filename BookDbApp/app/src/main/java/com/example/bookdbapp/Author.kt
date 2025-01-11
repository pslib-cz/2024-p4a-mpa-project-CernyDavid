package com.example.bookdbapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Author(
    @PrimaryKey(autoGenerate = true) val authorId: Long = 0,
    val name: String
)